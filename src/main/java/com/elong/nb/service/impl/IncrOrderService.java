/**   
 * @(#)IncrOrderService.java	2016年8月19日	下午5:14:25	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.elong.nb.IncrQueryStatistic;
import com.elong.nb.common.model.EnumOrderType;
import com.elong.nb.common.util.CommonsUtil;
import com.elong.nb.dao.IncrOrderDao;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrOrderResponse;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.bean.IncrOrder;
import com.elong.nb.model.enums.EnumIncrType;
import com.elong.nb.model.enums.EnumPayStatus;
import com.elong.nb.service.IIncrOrderService;
import com.elong.nb.util.DateHandlerUtils;
import com.elong.nb.util.IncrConst;

/**
 * 订单增量接口实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午5:16:44   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class IncrOrderService extends AbstractIncrService<IncrOrder> implements IIncrOrderService {

	private static final Log logger = LogFactory.getLog(IncrOrderService.class);
	
	protected static final Logger minitorLogger = Logger.getLogger("MinitorLogger");

	@Resource
	private IncrOrderDao incrOrderDao;

	/** 
	 * 获取最大IncrID的订单增量
	 *
	 * @param orderType
	 * @param proxyId
	 * @param orderFrom
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrOrderService#getLastIncrOrder(com.elong.nb.common.model.EnumOrderType, java.lang.String, java.lang.Integer)    
	 */
	@Override
	public IncrOrder getLastIncrOrder(EnumOrderType orderType, String proxyId, Integer orderFrom) {
		if (orderType == null) {
			logger.error("getLastIncrOrder error,due to the parameter 'orderType' is null.");
			throw new IncrException("getLastIncrOrder error,due to the parameter 'orderType' is null.");
		}
		if (StringUtils.isEmpty(proxyId)) {
			logger.error("getLastIncrOrder error,due to the parameter 'proxyId' is null.");
			throw new IncrException("getLastIncrOrder error,due to the parameter 'proxyId' is null.");
		}
		if (orderFrom == null) {
			logger.error("getLastIncrOrder error,due to the parameter 'orderFrom' is null.");
			throw new IncrException("getLastIncrOrder error,due to the parameter 'orderFrom' is null.");
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (orderType == EnumOrderType.OrderFrom) {
				paramMap.put("orderFrom", orderFrom);
			} else {
				paramMap.put("proxyId", proxyId);
			}
			return incrOrderDao.getLastIncrOrder(paramMap);
		} catch (Exception e) {
			logger.error("getLastIncrOrder error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastTime的最早发生变化的订单增量
	 *
	 * @param lastTime
	 * @param orderType
	 * @param proxyId
	 * @param orderFrom
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrOrderService#getOneIncrOrder(java.util.Date, com.elong.nb.common.model.EnumOrderType, java.lang.String, java.lang.Integer)    
	 */
	@Override
	public IncrOrder getOneIncrOrder(Date lastTime, EnumOrderType orderType, String proxyId, Integer orderFrom) {
		if (lastTime == null) {
			logger.error("getOneIncrOrder error,due to the parameter 'lastTime' is null.");
			throw new IncrException("getOneIncrOrder error,due to the parameter 'lastTime' is null.");
		}
		if (orderType == null) {
			logger.error("getOneIncrOrder error,due to the parameter 'orderType' is null.");
			throw new IncrException("getOneIncrOrder error,due to the parameter 'orderType' is null.");
		}
		if (StringUtils.isEmpty(proxyId)) {
			logger.error("getOneIncrOrder error,due to the parameter 'proxyId' is null.");
			throw new IncrException("getOneIncrOrder error,due to the parameter 'proxyId' is null.");
		}
		if (orderFrom == null) {
			logger.error("getOneIncrOrder error,due to the parameter 'orderFrom' is null.");
			throw new IncrException("getOneIncrOrder error,due to the parameter 'orderFrom' is null.");
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (orderType == EnumOrderType.OrderFrom) {
				paramMap.put("orderFrom", orderFrom);
			} else {
				paramMap.put("proxyId", proxyId);
			}
			paramMap.put("lastTime", lastTime);
			return incrOrderDao.getOneIncrOrder(paramMap);
		} catch (Exception e) {
			logger.error("getOneIncrOrder error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastId的maxRecordCount条订单增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @param orderType
	 * @param proxyId
	 * @param orderFrom
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrOrderService#getIncrOrders(long, int, com.elong.nb.common.model.EnumOrderType, java.lang.String, java.lang.Integer)    
	 */
	@Override
	public List<IncrOrder> getIncrOrders(long lastId, int maxRecordCount, EnumOrderType orderType, String proxyId, Integer orderFrom) {
		// if (lastId == 0) {
		// logger.error("getIncrOrders error,due to the parameter 'lastId' is 0.");
		// throw new IncrException("getIncrOrders error,due to the parameter 'lastId' is 0.");
		// }
		if (maxRecordCount == 0) {
			logger.error("getIncrOrders error,due to the parameter 'maxRecordCount' is 0.");
			throw new IncrException("getIncrOrders error,due to the parameter 'maxRecordCount' is 0.");
		}
		if (orderType == null) {
			logger.error("getIncrOrders error,due to the parameter 'orderType' is null.");
			throw new IncrException("getIncrOrders error,due to the parameter 'orderType' is null.");
		}
		if (StringUtils.isEmpty(proxyId)) {
			logger.error("getIncrOrders error,due to the parameter 'proxyId' is null.");
			throw new IncrException("getIncrOrders error,due to the parameter 'proxyId' is null.");
		}
		if (orderFrom == null) {
			logger.error("getIncrOrders error,due to the parameter 'orderFrom' is null.");
			throw new IncrException("getIncrOrders error,due to the parameter 'orderFrom' is null.");
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if (orderType == EnumOrderType.OrderFrom) {
				paramMap.put("orderFrom", orderFrom);
			} else {
				paramMap.put("proxyId", proxyId);
			}
			paramMap.put("lastId", lastId);
			paramMap.put("maxRecordCount", maxRecordCount);

			String delayMinute = CommonsUtil.CONFIG_PROVIDAR.getProperty("IncrOrder.getIncrOrders.delayMinutes");
			delayMinute = StringUtils.isEmpty(delayMinute) ? "-1" : StringUtils.trim(delayMinute);
			int offset = -1;
			try {
				offset = Integer.valueOf(delayMinute);
			} catch (NumberFormatException e) {
				offset = -1;
			}
			paramMap.put("lastTime", DateHandlerUtils.getOffsetDate(Calendar.MINUTE, offset));
			List<IncrOrder> incrOrders = incrOrderDao.getIncrOrders(paramMap);
			IncrQueryStatistic incrQueryStatistic = new IncrQueryStatistic();
			incrQueryStatistic.setBusiness_type("nbincrquery");
			incrQueryStatistic.setLog_time(DateHandlerUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			incrQueryStatistic.setIncrType(EnumIncrType.Order.name());
			incrQueryStatistic.setProxyId(orderFrom + "");
			if (CollectionUtils.isEmpty(incrOrders)){
				incrQueryStatistic.setQueryTime(DateHandlerUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				incrQueryStatistic.setEmptyStatus(true);
				return Collections.emptyList();
			}
			for (IncrOrder incrOrder : incrOrders) {
				if (incrOrder == null)
					continue;
				incrOrder.setPayStatus(EnumPayStatus.forValue(incrOrder.getPayStatus()).getPayStatus());
			}
			incrQueryStatistic.setQueryTime(DateHandlerUtils.formatDate(incrOrders.get(0).getChangeTime(), "yyyy-MM-dd HH:mm:ss"));
			incrQueryStatistic.setEmptyStatus(false);
			minitorLogger.info(JSON.toJSONString(incrQueryStatistic));
			return incrOrders;
		} catch (Exception e) {
			logger.error("getIncrOrders error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取订单增量数据
	 *
	 * @param params
	 * @return
	 */
	@Override
	protected List<IncrOrder> getIncrDatas(Map<String, Object> params) {
		if (params == null || !params.containsKey("lastId") || !params.containsKey("maxRecordCount")
				|| !params.containsKey("searchOrderType")) {
			throw new IncrException(
					"the map 'params' is requeried,and the map 'params' must contain the key ['lastId' or 'maxRecordCount' or 'searchOrderType'].");
		}
		if (!params.containsKey("orderFrom") && !params.containsKey("proxyId")) {
			throw new IncrException("the map 'params' must contain the key ['orderFrom' or 'proxyId'] at least.");
		}
		if (params.get("lastId") == null || params.get("maxRecordCount") == null) {
			throw new IncrException("the parameter['lastId' or 'maxRecordCount']  which belongs to the map 'params' must not be null.");
		}
		if (params.containsKey("orderFrom") && params.get("orderFrom") == null) {
			throw new IncrException("the parameter['orderFrom']  which belongs to the map 'params' must not be null.");
		}
		if (params.containsKey("proxyId") && params.get("proxyId") == null) {
			throw new IncrException("the parameter['proxyId']  which belongs to the map 'params' must not be null.");
		}

		long lastId = (long) params.get("lastId");
		int maxRecordCount = (int) params.get("maxRecordCount");
		EnumOrderType searchOrderType = (EnumOrderType) params.get("searchOrderType");
		String proxyId = (String) params.get("proxyId");
		Integer orderFrom = (Integer) params.get("orderFrom");

		return getIncrOrders(lastId, maxRecordCount, searchOrderType, proxyId, orderFrom);
	}

	/** 
	 * 获取订单增量最后更新ID
	 *
	 * @param params
	 * @return
	 */
	@Override
	protected BigInteger getLastId(Map<String, Object> params) {
		if (params == null || !params.containsKey("lastTime") || !params.containsKey("searchOrderType")) {
			throw new IncrException(
					"the map 'params' is requeried,and the map 'params' must contain the key ['lastTime' or 'searchOrderType'].");
		}
		Object lastTimeObj = params.get("lastTime");
		if (lastTimeObj == null) {
			throw new IncrException("the parameter 'lastTime' which belongs to the map 'params' must not be null.");
		}
		if (!(lastTimeObj instanceof Date)) {
			throw new IncrException("the parameter 'lastTime' which belongs to the map 'params' must be date type.");
		}
		if (!params.containsKey("orderFrom") && !params.containsKey("proxyId")) {
			throw new IncrException("the map 'params' must contain the key ['orderFrom' or 'proxyId'] at least.");
		}
		if (params.containsKey("orderFrom") && params.get("orderFrom") == null) {
			throw new IncrException("the parameter['orderFrom']  which belongs to the map 'params' must not be null.");
		}
		if (params.containsKey("proxyId") && params.get("proxyId") == null) {
			throw new IncrException("the parameter['proxyId']  which belongs to the map 'params' must not be null.");
		}

		Date lastTime = (Date) params.get("lastTime");
		EnumOrderType searchOrderType = (EnumOrderType) params.get("searchOrderType");
		String proxyId = (String) params.get("proxyId");
		Integer orderFrom = (Integer) params.get("orderFrom");
		IncrOrder incrOrder = getOneIncrOrder(lastTime, searchOrderType, proxyId, orderFrom);
		if (incrOrder == null) {
			incrOrder = getLastIncrOrder(searchOrderType, proxyId, orderFrom);
		}
		return incrOrder == null ? IncrConst.bigIntegerNegativeOne : incrOrder.getIncrID();
	}

	/** 
	 * 创建增量响应模型
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#createIncrResponse()    
	 */
	@Override
	protected IncrResponse<IncrOrder> createIncrResponse() {
		return new IncrOrderResponse();
	}

}
