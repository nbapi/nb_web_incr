/**   
 * @(#)AbstractSubmeterService.java	2017年4月18日	上午11:31:04	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.common.util.CommonsUtil;
import com.elong.nb.model.bean.Idable;
import com.elong.nb.model.enums.SubmeterConst;
import com.elong.nb.submeter.service.ISubmeterService;

/**
 * 分表服务实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月18日 上午11:31:04   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */

public abstract class AbstractSubmeterService<T extends Idable> implements ISubmeterService<T> {

	private static final Logger logger = Logger.getLogger("SubmeterLogger");

	@Resource
	private SubmeterTableCache submeterTableCache;

	/** 
	 * 获取大于指定lastId的maxRecordCount条增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @return 
	 *
	 * @see com.elong.nb.service.ISubmeterService#getIncrDataList(long, int)    
	 */
	@Override
	public List<T> getIncrDataList(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		String tablePrefix = getTablePrefix();
		List<String> subTableNameList = submeterTableCache.queryNoEmptySubTableList(tablePrefix, false);
		if (subTableNameList == null || subTableNameList.size() == 0)
			return Collections.emptyList();

		int submeterRowCount = SubmeterConst.PER_SUBMETER_ROW_COUNT;
		String configValue = CommonsUtil.CONFIG_PROVIDAR.getProperty("ImpulseSenderFromRedisTest");
		if (StringUtils.isNotEmpty(configValue)) {
			submeterRowCount = 100;
		}
		long tableNumber = (int) Math.ceil(lastId * 1.0 / submeterRowCount);
		String selectTableName = tablePrefix + "_" + tableNumber;
		int fromIndex = subTableNameList.indexOf(selectTableName);
		fromIndex = (fromIndex == -1) ? 0 : fromIndex;
		List<String> selectSubTableList = subTableNameList.subList(fromIndex, subTableNameList.size());

		List<T> resultList = new ArrayList<T>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ID", lastId);
		for (String subTableName : selectSubTableList) {
			if (StringUtils.isEmpty(subTableName))
				continue;
			params.put("maxRecordCount", maxRecordCount);
			params.put("lastTime", getLastTimeAfterDelay());
			List<T> subList = getIncrDataList(subTableName, params, proxyAccount);
			if (subList == null || subList.size() == 0)
				continue;
			resultList.addAll(subList);
			logger.info("subTableName = " + subTableName + ",getIncrDataList params = " + params + ",result size = " + subList.size());
			if (subList.size() >= maxRecordCount)
				break;
			maxRecordCount = maxRecordCount - subList.size();
		}
		return resultList;
	}

	/** 
	 * 获取最大IncrID的增量
	 *
	 * @return 
	 *
	 * @see com.elong.nb.submeter.service.ISubmeterService#getLastIncrInventory()    
	 */
	@Override
	public T getLastIncrData() {
		String tablePrefix = getTablePrefix();
		List<String> subTableNameList = submeterTableCache.queryNoEmptySubTableList(tablePrefix, true);
		if (subTableNameList == null || subTableNameList.size() == 0)
			return null;

		subTableNameList.remove(tablePrefix);
		for (String subTableName : subTableNameList) {
			if (StringUtils.isEmpty(subTableName))
				continue;
			T result = getLastIncrData(subTableName);
			logger.info("subTableName = " + subTableName + ",getLastIncrData,result  = " + result);
			if (result == null)
				continue;
			return result;
		}
		return null;
	}

	/** 
	 * 获取大于指定lastTime的最早发生变化的增量 
	 *
	 * @param lastTime
	 * @return 
	 *
	 * @see com.elong.nb.submeter.service.ISubmeterService#getOneIncrInventory(java.util.Date)    
	 */
	@Override
	public T getOneIncrData(Date lastTime) {
		String tablePrefix = getTablePrefix();
		List<String> subTableNameList = submeterTableCache.queryNoEmptySubTableList(tablePrefix, false);
		if (subTableNameList == null || subTableNameList.size() == 0)
			return null;

		subTableNameList.remove(tablePrefix);
		for (String subTableName : subTableNameList) {
			if (StringUtils.isEmpty(subTableName))
				continue;
			T result = getOneIncrData(subTableName, lastTime);
			logger.info("subTableName = " + subTableName + ",getLastIncrData lastTime = " + lastTime + ",result  = " + result);
			if (result == null)
				continue;
			return result;
		}
		return null;
	}

	/** 
	 * 获取指定分表最大IncrID的增量 
	 *
	 * @param subTableName
	 * @param lastTime
	 * @return
	 */
	protected abstract T getOneIncrData(String subTableName, Date lastTime);

	/** 
	 * 获取指定分表大于指定lastTime的最早发生变化的增量
	 *
	 * @param subTableName
	 * @return
	 */
	protected abstract T getLastIncrData(String subTableName);

	/** 
	 * 获取分表数据
	 *
	 * @param subTableName
	 * @param params
	 * @return
	 */
	protected abstract List<T> getIncrDataList(String subTableName, Map<String, Object> params, ProxyAccount proxyAccount);

	/** 
	 * 获取数据延时时间（子类选择覆盖）
	 *
	 * @return
	 */
	protected Date getLastTimeAfterDelay() {
		return new Date();
	}

}
