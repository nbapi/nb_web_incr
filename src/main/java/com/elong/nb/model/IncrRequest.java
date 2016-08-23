/**   
 * @(#)IncrRequest.java	2016年8月19日	下午4:14:59	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.math.BigInteger;

import com.google.gson.annotations.SerializedName;

/**
 * 增量请求模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午4:14:59   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrRequest {

	/** 
	 * 最后更新增量ID
	 *
	 * BigInteger IncrRequest.java lastId
	 */
	@SerializedName(value = "LastId", alternate = "lastId")
	private BigInteger lastId;

	/** 
	 * 请求数量
	 *
	 * int IncrRequest.java count
	 */
	@SerializedName(value = "Count", alternate = "count")
	private int count = 1000;

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

	/**   
	 * 得到count的值   
	 *   
	 * @return count的值
	 */
	public int getCount() {
		return count;
	}

	/**
	 * 设置count的值
	 *   
	 * @param count 被设置的值
	 */
	public void setCount(int count) {
		this.count = count;
	}

}
