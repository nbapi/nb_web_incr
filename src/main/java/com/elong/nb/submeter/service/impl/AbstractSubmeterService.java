/**   
 * @(#)AbstractSubmeterService.java	2017年4月18日	上午11:31:04	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.model.bean.Idable;
import com.elong.nb.submeter.service.IImpulseSenderService;
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
	private SubmeterTableCalculate submeterTableCalculate;

	@Resource
	private IImpulseSenderService impulseSenderService;

	/** 
	 * 获取大于指定lastId的maxRecordCount条增量 
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.submeter.service.ISubmeterService#getIncrDataList(long, int, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	public List<T> getIncrDataList(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		// 获取id所在分片datasource
		String dataSource = submeterTableCalculate.getSelectedDataSource(lastId);
		// 获取id所在分表表名
		String tablePrefix = getTablePrefix();
		String subTableName = submeterTableCalculate.getSelectedSubTable(tablePrefix, lastId);
		// 获取id所在段段尾id
		long segmentEndId = submeterTableCalculate.getSegmentEndId(lastId);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ID", lastId);
		params.put("segmentEndId", segmentEndId);
		params.put("maxRecordCount", maxRecordCount);
		params.put("lastTime", getLastTimeAfterDelay());
		List<T> subList = getIncrDataList(dataSource, subTableName, params, proxyAccount);
		logger.info("getIncrDataList,dataSource = " + dataSource + ",subTableName = " + subTableName + ",params = " + params
				+ ",resultListSize = " + subList.size());

		List<T> resultList = new ArrayList<T>();
		resultList.addAll(subList);
		long maxId = impulseSenderService.curId(tablePrefix + "_ID");
		long nextSegmentBeginId = segmentEndId + 1;
		// id所在段返回数据不够，并且后面段有数据，则继续查下一个段
		if (maxRecordCount > subList.size() && nextSegmentBeginId < maxId) {
			maxRecordCount = maxRecordCount - subList.size();
			List<T> remainList = getIncrDataList(nextSegmentBeginId, maxRecordCount, proxyAccount);
			resultList.addAll(remainList);
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
		long maxId = impulseSenderService.curId(tablePrefix + "_ID");
		return getLastIncrData(maxId);
	}

	/** 
	 * 按段递归查询最后一条数据 
	 *
	 * @param trigger
	 * @param maxId
	 * @return
	 */
	private T getLastIncrData(long maxId) {
		String tablePrefix = getTablePrefix();
		// 获取id所在分片datasource
		String dataSource = submeterTableCalculate.getSelectedDataSource(maxId);
		// 获取id所在分表表名
		String subTableName = submeterTableCalculate.getSelectedSubTable(tablePrefix, maxId);
		// 获取id所在段段尾id
		long segmentBeginId = submeterTableCalculate.getSegmentBeginId(maxId);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("segmentBeginId", segmentBeginId);
		T result = getLastIncrData(dataSource, subTableName, params);
		logger.info("getLastIncrData,dataSource = " + dataSource + ",subTableName = " + subTableName + ",params= " + params + ",result  = "
				+ result);
		if (result == null && segmentBeginId > 1) {
			long previousSegmentEndId = segmentBeginId - 1;
			return getLastIncrData(previousSegmentEndId);
		}
		return result;
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
		long maxId = impulseSenderService.curId(tablePrefix + "_ID");
		return getOneIncrData(lastTime, maxId);
	}

	/** 
	 * 按段递归查询小于指定时间的最后一条数据 
	 *
	 * @param lastTime
	 * @param maxId
	 * @return
	 */
	private T getOneIncrData(Date lastTime, long maxId) {
		String tablePrefix = getTablePrefix();
		// 获取id所在分片datasource
		String dataSource = submeterTableCalculate.getSelectedDataSource(maxId);
		// 获取id所在分表表名
		String subTableName = submeterTableCalculate.getSelectedSubTable(tablePrefix, maxId);
		// 获取id所在段段首id
		long segmentBeginId = submeterTableCalculate.getSegmentBeginId(maxId);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("segmentBeginId", segmentBeginId);
		params.put("lastTime", lastTime);
		T result = getOneIncrData(dataSource, subTableName, params);
		logger.info("getOneIncrData,dataSource = " + dataSource + ",subTableName = " + subTableName + ",params= " + params
				+ ",result  = " + result);
		if (result == null && segmentBeginId > 1) {
			long previousSegmentEndId = segmentBeginId - 1;
			return getOneIncrData(lastTime, previousSegmentEndId);
		}
		return result;
	}

	/** 
	 * 获取指定分表最大IncrID的增量 
	 *
	 * @param subTableName
	 * @param lastTime
	 * @return
	 */
	protected abstract T getOneIncrData(String dataSource, String subTableName, Map<String, Object> params);

	/** 
	 * 获取指定分表大于指定lastTime的最早发生变化的增量
	 *
	 * @param subTableName
	 * @return
	 */
	protected abstract T getLastIncrData(String dataSource, String subTableName, Map<String, Object> params);

	/** 
	 * 获取分表数据
	 *
	 * @param dataSource
	 * @param subTableName
	 * @param params
	 * @param proxyAccount
	 * @return
	 */
	protected abstract List<T> getIncrDataList(String dataSource, String subTableName, Map<String, Object> params, ProxyAccount proxyAccount);

	/** 
	 * 获取数据延时时间（子类选择覆盖）
	 *
	 * @return
	 */
	protected Date getLastTimeAfterDelay() {
		return new Date();
	}

}
