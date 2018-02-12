/**   
 * @(#)IIncrOrderService.java	2016年8月19日	下午5:14:25	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service;

import java.util.Date;
import java.util.List;

import com.elong.nb.common.model.EnumOrderType;
import com.elong.nb.model.bean.IncrOrder;

/**
 * 订单增量接口
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午5:14:25   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IIncrOrderService {

	/** 
	 * 获取最大IncrID的订单增量
	 *
	 * @param orderType
	 * @param proxyId
	 * @param orderFrom
	 * @return
	 */
	public IncrOrder getLastIncrOrder(EnumOrderType orderType, String proxyId, Integer orderFrom);

	/** 
	 * 获取大于指定lastTime的最早发生变化的订单增量
	 *
	 * @param lastTime
	 * @param orderType
	 * @param proxyId
	 * @param orderFrom
	 * @return
	 */
	public IncrOrder getOneIncrOrder(Date lastTime, EnumOrderType orderType, String proxyId, Integer orderFrom);

	/** 
	 * 获取大于指定lastId的maxRecordCount条订单增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @param orderType
	 * @param proxyId
	 * @param orderFrom
	 * @return
	 */
	public List<IncrOrder> getIncrOrders(long lastId, int maxRecordCount, EnumOrderType orderType, String proxyId, Integer orderFrom);

}
