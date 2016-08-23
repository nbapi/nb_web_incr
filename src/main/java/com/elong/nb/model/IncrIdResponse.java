/**   
 * @(#)IncrIdResponse.java	2016年8月19日	下午3:56:05	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

/**
 * 增量ID响应Model
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午3:56:05   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrIdResponse {

	/** 
	 * 最后更新ID
	 *
	 * BigInteger IncrIdResponse.java LastId
	 */
	@SerializedName(value = "LastId", alternate = "lastId")
	private BigInteger lastId;

	/**   
	 * 得到lastId的值   
	 *   
	 * @return lastId的值
	 */
	public BigInteger getLastId() {
		return lastId;
	}

	/**
	 * 设置lastId的值
	 *   
	 * @param lastId 被设置的值
	 */
	public void setLastId(BigInteger lastId) {
		this.lastId = lastId;
	}

}
