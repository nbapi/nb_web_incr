/**   
 * @(#)SubmeterTableCache.java	2017年4月26日	下午5:13:49	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.elong.nb.cache.ICacheKey;
import com.elong.nb.cache.RedisManager;
import com.elong.nb.dao.SubmeterTableDao;
import com.elong.nb.model.enums.SubmeterConst;

/**
 * 表元数据信息缓存
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月26日 下午5:13:49   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Repository
public class SubmeterTableCache {

	private static final Logger logger = Logger.getLogger("SubmeterLogger");

	private RedisManager redisManager = RedisManager.getInstance("redis_shared", "redis_shared");

	@Resource
	private SubmeterTableDao submeterTableDao;

	/** 
	 * 上次缓存更新时间
	 *
	 * long SubmeterTableCache.java lastChangeTime
	 */
	private volatile long lastChangeTime = 0l;

	/** 
	 * 查询指定tablePrefix的所有非空分表
	 *
	 * @param tablePrefix
	 * @param isDesc
	 * @return
	 */
	public List<String> queryNoEmptySubTableList(String tablePrefix, boolean isDesc) {
		ICacheKey tablesCacheKey = RedisManager.getCacheKey(MessageFormat
				.format(SubmeterConst.SUBMETER_NOEMPTY_TABLENAMES_KEY, tablePrefix));
		List<String> subTableNameList = redisManager.pull(tablesCacheKey);

		// 缓存中获取到list升序的，根据isDesc决定是否倒序，直接返回
		long currentTime = System.currentTimeMillis();
		if (subTableNameList != null && subTableNameList.size() > 0 && (currentTime - lastChangeTime) <= 10 * 60 * 1000) {
			if (isDesc) {
				Collections.reverse(subTableNameList);
			}
			return subTableNameList;
		}

		// 数据库获取到list降序的，根据isDesc决定是否倒序，直接返回
		subTableNameList = submeterTableDao.queryNoEmptySubTableList(tablePrefix + "%");
		// 刷新redis数据
		refresh(tablePrefix, subTableNameList);
		lastChangeTime = currentTime;
		if (!isDesc) {
			Collections.reverse(subTableNameList);
		}
		return subTableNameList;
	}

	/** 
	 * 指定tablePrefix的非空分表存入redis
	 *
	 * @param tablePrefix
	 * @param newTableNames
	 */
	private void refresh(String tablePrefix, List<String> subTableNameList) {
		ICacheKey lockCacheKey = RedisManager.getCacheKey(MessageFormat.format(SubmeterConst.SUBMETER_REDIS_LOCK_KEY, tablePrefix));
		String source = "UUID = " + UUID.randomUUID().toString() + ",refresh noempty tablenames from db into redis";
		long lockTime = lock(lockCacheKey, source);
		try {
			ICacheKey tablesCacheKey = RedisManager.getCacheKey(MessageFormat.format(SubmeterConst.SUBMETER_NOEMPTY_TABLENAMES_KEY,
					tablePrefix));
			redisManager.del(tablesCacheKey);
			for (String subTableName : subTableNameList) {
				redisManager.lpush(tablesCacheKey, subTableName.getBytes());
				redisManager.ltrim(tablesCacheKey, 0, SubmeterConst.NOEMPTY_SUMETER_COUNT_IN_REDIS);
			}
		} finally {
			unlock(lockCacheKey, source, lockTime);
		}
	}

	/** 
	 * 不完善，后续改为调统一分布式锁服务 
	 *
	 * @param source
	 * @return
	 */
	private long lock(ICacheKey lockCacheKey, String source) {
		while (redisManager.setnx(lockCacheKey, "lock") == 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		logger.info("lock successfully.invoke position = " + source);
		return System.currentTimeMillis();
	}

	private void unlock(ICacheKey lockCacheKey, String source, long lockTime) {
		redisManager.del(lockCacheKey);
		logger.info("lock time = " + (System.currentTimeMillis() - lockTime) + ",unlock successfully.invoke position = " + source);
	}

}
