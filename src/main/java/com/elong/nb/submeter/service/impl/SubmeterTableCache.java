/**   
 * @(#)SubmeterTableCache.java	2017年4月26日	下午5:13:49	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

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

	private RedisManager redisManager = RedisManager.getInstance("redis_job", "redis_job");

	@Resource
	private SubmeterTableDao submeterTableDao;

	/** 
	 * redis分布式锁cachekey	
	 *
	 * ICacheKey SubmeterTableCache.java lockCacheKey
	 */
	private static final ICacheKey lockCacheKey = RedisManager.getCacheKey(SubmeterConst.SUMETER_REDIS_LOCK_KEY);

	/** 
	 * 上次缓存更新时间
	 *
	 * long SubmeterTableCache.java lastChangeTime
	 */
	private long lastChangeTime = 0l;

	/** 
	 * 查询指定tablePrefix的所有非空分表
	 *
	 * @param tablePrefix
	 * @param isDesc
	 * @return
	 */
	public List<String> queryNoEmptySubTableList(String tablePrefix, boolean isDesc) {
		ICacheKey cacheKey = RedisManager.getCacheKey(tablePrefix + ".Submeter.TableNames");
		List<String> subTableNameList = redisManager.pull(cacheKey);
		long currentTime = System.currentTimeMillis();
		// 缓存中获取到list默认降序的，根据isDesc决定是否倒序，直接返回
		if (!CollectionUtils.isEmpty(subTableNameList) && (currentTime - lastChangeTime) <= 3 * 60 * 1000) {
			if (!isDesc) {
				Collections.reverse(subTableNameList);
			}
			return subTableNameList;
		}
		lastChangeTime = currentTime;
		// 数据库获取到表名list
		subTableNameList = submeterTableDao.queryNoEmptySubTableList(tablePrefix + "%", isDesc);
		if (CollectionUtils.isEmpty(subTableNameList))
			return Collections.emptyList();

		// 由于存入redis是要符合先进后出，所以如果倒序list，需要反序
		if (isDesc) {
			Collections.reverse(subTableNameList);
		}

		String source = "UUID = " + UUID.randomUUID().toString() + ",定时从数据库一次查询所有非空表名更新缓存";
		long lockTime = lock(source);
		try {
			// 清除老数据
			redisManager.del(cacheKey);
			// 存入redis
			for (String subTableName : subTableNameList) {
				redisManager.lpush(cacheKey, subTableName.getBytes());
				redisManager.ltrim(cacheKey, 0, SubmeterConst.NOEMPTY_SUMETER_COUNT_IN_REDIS);
			}
		} finally {
			unlock(source, lockTime);
		}
		// redis重新获取
		subTableNameList = redisManager.pull(cacheKey);
		if (CollectionUtils.isEmpty(subTableNameList))
			return Collections.emptyList();

		// 根据isDesc决定是否倒序，返回
		if (!isDesc) {
			Collections.reverse(subTableNameList);
		}
		return subTableNameList;
	}

	private long lock(String source) {
		while (redisManager.setnx(lockCacheKey, "lock") == 0) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		logger.info("lock successfully.invoke position = " + source);
		return System.currentTimeMillis();
	}

	private void unlock(String source, long lockTime) {
		redisManager.del(lockCacheKey);
		logger.info("lock time = " + (System.currentTimeMillis() - lockTime) + ",unlock successfully.invoke position = " + source);
	}

}
