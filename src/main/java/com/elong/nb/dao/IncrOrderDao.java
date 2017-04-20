/**   
 * @(#)IncrOrderDao.java	2016年8月19日	下午5:22:50	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.dao;

import java.util.List;
import java.util.Map;

import com.elong.nb.db.DataSource;
import com.elong.nb.model.bean.IncrOrder;

/**
 * 订单增量数据组件
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午5:22:50   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@DataSource("incrorderReadDataSource")
public interface IncrOrderDao {

	/** 
	 * 获取最大IncrID的订单增量
	 *
	 * @param paramMap
	 * @return
	 */
	public IncrOrder getLastIncrOrder(Map<String, Object> paramMap);

	/** 
	 * 获取大于指定lastTime的最早发生变化的订单增量
	 *
	 * @param paramMap
	 * @return
	 */
	public IncrOrder getOneIncrOrder(Map<String, Object> paramMap);

	/** 
	 * 获取大于指定lastId的maxRecordCount条订单增量
	 *
	 * @param paramMap
	 * @return
	 */
	public List<IncrOrder> getIncrOrders(Map<String, Object> paramMap);

}
