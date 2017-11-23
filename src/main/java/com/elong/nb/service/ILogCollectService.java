/**   
 * @(#)ILogCollectService.java	2017年11月23日	上午10:24:24	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.service;

import java.util.List;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.model.bean.IncrHotel;
import com.elong.nb.model.bean.IncrInventory;
import com.elong.nb.model.bean.IncrOrder;
import com.elong.nb.model.bean.IncrRate;
import com.elong.nb.model.bean.IncrState;

/**
 * 业务监控日志收集
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年11月23日 上午10:24:24   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface ILogCollectService {

	/** 
	 * 订单增量监控日志收集 
	 *
	 * @param proxyAccount
	 * @param incrList
	 */
	public void writeIncrOrderLog(ProxyAccount proxyAccount, List<IncrOrder> incrList);

	/** 
	 * 酒店增量监控日志收集 
	 *
	 * @param proxyAccount
	 * @param incrList
	 */
	public void writeIncrHotelLog(ProxyAccount proxyAccount, List<IncrHotel> incrList);

	/** 
	 * 库存增量监控日志收集
	 *
	 * @param proxyAccount
	 * @param incrList
	 */
	public void writeIncrInvLog(ProxyAccount proxyAccount, List<IncrInventory> incrList);

	/** 
	 * 价格增量监控日志收集
	 *
	 * @param proxyAccount
	 * @param incrList
	 */
	public void writeIncrRateLog(ProxyAccount proxyAccount, List<IncrRate> incrList);

	/** 
	 * 状态增量监控日志收集
	 *
	 * @param proxyAccount
	 * @param incrList
	 */
	public void writeIncrStateLog(ProxyAccount proxyAccount, List<IncrState> incrList);

}
