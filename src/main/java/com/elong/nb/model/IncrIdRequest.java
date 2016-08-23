/**   
 * @(#)IncrIdRequest.java	2016年8月19日	下午3:53:40	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.elong.nb.enums.EnumIncrType;
import com.google.gson.annotations.SerializedName;

/**
 * 增量ID请求Model
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午3:53:40   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrIdRequest {

	/** 
	 * 最后变化时间
	 *
	 * Date IncrIdRequest.java lastTime
	 */
	@SerializedName(value = "LastTime", alternate = "lastTime")
	@JSONField(name = "LastTime", format = "yyyy-MM-dd HH:mm:ss")
	private Date lastTime;

	/** 
	 * 增量类型
	 *
	 * EnumIncrType IncrIdRequest.java incrType
	 */
	@SerializedName(value = "IncrType", alternate = "incrType")
	private EnumIncrType incrType;

	/**   
	 * 得到lastTime的值   
	 *   
	 * @return lastTime的值
	 */
	public Date getLastTime() {
		return lastTime;
	}

	/**
	 * 设置lastTime的值
	 *   
	 * @param lastTime 被设置的值
	 */
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	/**   
	 * 得到incrType的值   
	 *   
	 * @return incrType的值
	 */
	public EnumIncrType getIncrType() {
		return incrType;
	}

	/**
	 * 设置incrType的值
	 *   
	 * @param incrType 被设置的值
	 */
	public void setIncrType(EnumIncrType incrType) {
		this.incrType = incrType;
	}

}
