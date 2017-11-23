/**   
 * @(#)LogCollectServiceImpl.java	2017年11月23日	上午10:29:36	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.elong.nb.IncrQueryStatistic;
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.model.bean.IncrHotel;
import com.elong.nb.model.bean.IncrInventory;
import com.elong.nb.model.bean.IncrOrder;
import com.elong.nb.model.bean.IncrRate;
import com.elong.nb.model.bean.IncrState;
import com.elong.nb.model.enums.EnumIncrType;
import com.elong.nb.service.ILogCollectService;
import com.elong.nb.util.DateHandlerUtils;

/**
 * 业务监控日志收集实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年11月23日 上午10:29:36   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class LogCollectServiceImpl implements ILogCollectService {

	private final Logger minitorLogger = Logger.getLogger("MinitorLogger");

	private static final String BUSINESS_TYPE = "nbincrquery";

	@Override
	public void writeIncrOrderLog(ProxyAccount proxyAccount, List<IncrOrder> incrList) {
		Date changeTime = CollectionUtils.isEmpty(incrList) ? null : incrList.get(0).getChangeTime();
		writeBusinessLog(proxyAccount.getOrderFrom(), incrList, EnumIncrType.Order.name(), changeTime);
	}

	@Override
	public void writeIncrHotelLog(ProxyAccount proxyAccount, List<IncrHotel> incrList) {
		Date changeTime = CollectionUtils.isEmpty(incrList) ? null : incrList.get(0).getChangeTime();
		writeBusinessLog(proxyAccount.getOrderFrom(), incrList, EnumIncrType.Data.name(), changeTime);
	}

	@Override
	public void writeIncrInvLog(ProxyAccount proxyAccount, List<IncrInventory> incrList) {
		Date changeTime = CollectionUtils.isEmpty(incrList) ? null : incrList.get(0).getChangeTime();
		writeBusinessLog(proxyAccount.getOrderFrom(), incrList, EnumIncrType.Inventory.name(), changeTime);
	}

	@Override
	public void writeIncrRateLog(ProxyAccount proxyAccount, List<IncrRate> incrList) {
		Date changeTime = CollectionUtils.isEmpty(incrList) ? null : incrList.get(0).getChangeTime();
		writeBusinessLog(proxyAccount.getOrderFrom(), incrList, EnumIncrType.Rate.name(), changeTime);
	}

	@Override
	public void writeIncrStateLog(ProxyAccount proxyAccount, List<IncrState> incrList) {
		Date changeTime = CollectionUtils.isEmpty(incrList) ? null : incrList.get(0).getChangeTime();
		writeBusinessLog(proxyAccount.getOrderFrom(), incrList, EnumIncrType.State.name(), changeTime);
	}

	private void writeBusinessLog(int orderFrom, List<?> incrList, String incrType, Date changeTime) {
		String logTime = DateHandlerUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		IncrQueryStatistic incrQueryStatistic = new IncrQueryStatistic();
		incrQueryStatistic.setBusiness_type(BUSINESS_TYPE + "_" + incrType);
		incrQueryStatistic.setLog_time(logTime);
		incrQueryStatistic.setProxyId(orderFrom + "");
		if (CollectionUtils.isEmpty(incrList)) {
			incrQueryStatistic.setQueryTime(logTime);
			incrQueryStatistic.setEmptyStatus(true);
		} else {
			incrQueryStatistic.setQueryTime(DateHandlerUtils.formatDate(changeTime, "yyyy-MM-dd HH:mm:ss"));
			incrQueryStatistic.setEmptyStatus(false);
		}
		minitorLogger.info(JSON.toJSONString(incrQueryStatistic));
	}

}
