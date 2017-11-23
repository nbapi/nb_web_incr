/**   
 * @(#)IncrOrderService.java	2016年8月19日	下午5:14:25	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.elong.nb.common.model.EnumOrderType;
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.common.util.CommonsUtil;
import com.elong.nb.dao.IncrOrderDao;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrOrderResponse;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.bean.IncrOrder;
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

			String delayTime = CommonsUtil.CONFIG_PROVIDAR.getProperty("IncrOrder.getIncrOrders.delayTimes");
			delayTime = StringUtils.isEmpty(delayTime) ? "-60" : StringUtils.trim(delayTime);
			int offset = -60;
			try {
				offset = Integer.valueOf(delayTime);
			} catch (NumberFormatException e) {
				offset = -60;
			}
			paramMap.put("lastTime", DateHandlerUtils.getOffsetDate(Calendar.SECOND, offset));
			List<IncrOrder> incrOrders = incrOrderDao.getIncrOrders(paramMap);
			for (IncrOrder incrOrder : incrOrders) {
				if (incrOrder == null)
					continue;
				incrOrder.setPayStatus(EnumPayStatus.forValue(incrOrder.getPayStatus()).getPayStatus());
			}
			return incrOrders;
		} catch (Exception e) {
			logger.error("getIncrOrders error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取订单增量数据
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getIncrDatas(long, int, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	protected List<IncrOrder> getIncrDatas(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		EnumOrderType searchOrderType = proxyAccount.getSearchOrderType();
		String proxyId = proxyAccount.getProxyId();
		Integer orderFrom = proxyAccount.getOrderFrom();
		List<IncrOrder> incrList = getIncrOrders(lastId, maxRecordCount, searchOrderType, proxyId, orderFrom);
		logCollectService.writeIncrOrderLog(proxyAccount, incrList);
		return incrList;
	}

	/** 
	 * 获取订单增量最后更新ID 
	 *
	 * @param lastTime
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getLastId(java.util.Date, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	protected BigInteger getLastId(Date lastTime, ProxyAccount proxyAccount) {
		EnumOrderType searchOrderType = proxyAccount.getSearchOrderType();
		String proxyId = proxyAccount.getProxyId();
		Integer orderFrom = proxyAccount.getOrderFrom();
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
