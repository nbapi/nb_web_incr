/**   
 * @(#)IncrOrderResponse.java	2016年8月23日	下午2:17:00	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 订单增量响应模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月23日 下午2:17:00   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrOrderResponse implements IncrResponse<IncrOrder> {

	@SerializedName(value = "Orders", alternate = "orders")
	private List<IncrOrder> orders;

	@Override
	public List<IncrOrder> getList() {
		return orders;
	}

	@Override
	public void setList(List<IncrOrder> list) {
		this.orders = list;
	}

}
