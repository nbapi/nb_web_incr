/**   
 * @(#)IncrState.java	2016年8月19日	下午4:06:00	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Until;

/**
 * 状态增量模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午4:06:00   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrState implements Serializable {

	/** 
	 * 序列化ID
	 *
	 * long IncrState.java serialVersionUID
	 */
	private static final long serialVersionUID = -3286669616692742103L;

	/** 
	 * 增量ID
	 *
	 * BigInteger IncrState.java incrID
	 */
	@SerializedName(value = "IncrID", alternate = "incrID")
	private BigInteger incrID;

	/** 
	 * 变化时间
	 *
	 * Date IncrState.java changeTime
	 */
	@SerializedName(value = "ChangeTime", alternate = "changeTime")
	private Date changeTime;

	/** 
	 * 酒店ID
	 *
	 * String IncrState.java hotelId
	 */
	private String hotelId;

	/** 
	 * s酒店ID
	 *
	 * String IncrState.java hotelCode
	 */
	private String hotelCode;

	/** 
	 * (变量说明描述)	
	 *
	 * String IncrState.java roomId
	 */
	private String roomId;

	/** 
	 * 房型ID
	 *
	 * String IncrState.java roomTypeId
	 */
	private String roomTypeId;

	/** 
	 * (变量说明描述)	
	 *
	 * String IncrState.java ratePlanId
	 */
	private String ratePlanId;

	/** 
	 * (变量说明描述)	
	 *
	 * Integer IncrState.java status
	 */
	private Integer status;

	/** 
	 * (变量说明描述)	
	 *
	 * Boolean IncrState.java statusForJson
	 */
	private Boolean statusForJson;

	/** 
	 * (变量说明描述)	
	 *
	 * Integer IncrState.java stateType
	 */
	private Integer stateType;

	/** 
	 * 记录HotelId, RoomId, RatePlanId变化对应的名称
	 *
	 * String IncrState.java name
	 */
	@Until(1.17)
	private String name;

	/**   
	 * 得到incrID的值   
	 *   
	 * @return incrID的值
	 */
	public BigInteger getIncrID() {
		return incrID;
	}

	/**
	 * 设置incrID的值
	 *   
	 * @param incrID 被设置的值
	 */
	public void setIncrID(BigInteger incrID) {
		this.incrID = incrID;
	}

	/**   
	 * 得到changeTime的值   
	 *   
	 * @return changeTime的值
	 */
	public Date getChangeTime() {
		return changeTime;
	}

	/**
	 * 设置changeTime的值
	 *   
	 * @param changeTime 被设置的值
	 */
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	/**   
	 * 得到hotelId的值   
	 *   
	 * @return hotelId的值
	 */
	public String getHotelId() {
		return hotelId;
	}

	/**
	 * 设置hotelId的值
	 *   
	 * @param hotelId 被设置的值
	 */
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	/**   
	 * 得到hotelCode的值   
	 *   
	 * @return hotelCode的值
	 */
	public String getHotelCode() {
		return hotelCode;
	}

	/**
	 * 设置hotelCode的值
	 *   
	 * @param hotelCode 被设置的值
	 */
	public void setHotelCode(String hotelCode) {
		this.hotelCode = hotelCode;
	}

	/**   
	 * 得到roomId的值   
	 *   
	 * @return roomId的值
	 */
	public String getRoomId() {
		return roomId;
	}

	/**
	 * 设置roomId的值
	 *   
	 * @param roomId 被设置的值
	 */
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	/**   
	 * 得到roomTypeId的值   
	 *   
	 * @return roomTypeId的值
	 */
	public String getRoomTypeId() {
		return roomTypeId;
	}

	/**
	 * 设置roomTypeId的值
	 *   
	 * @param roomTypeId 被设置的值
	 */
	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	/**   
	 * 得到ratePlanId的值   
	 *   
	 * @return ratePlanId的值
	 */
	public String getRatePlanId() {
		return ratePlanId;
	}

	/**
	 * 设置ratePlanId的值
	 *   
	 * @param ratePlanId 被设置的值
	 */
	public void setRatePlanId(String ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	/**   
	 * 得到status的值   
	 *   
	 * @return status的值
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 设置status的值
	 *   
	 * @param status 被设置的值
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**   
	 * 得到statusForJson的值   
	 *   
	 * @return statusForJson的值
	 */
	public Boolean getStatusForJson() {
		return statusForJson;
	}

	/**
	 * 设置statusForJson的值
	 *   
	 * @param statusForJson 被设置的值
	 */
	public void setStatusForJson(Boolean statusForJson) {
		this.statusForJson = statusForJson;
	}

	/**   
	 * 得到stateType的值   
	 *   
	 * @return stateType的值
	 */
	public Integer getStateType() {
		return stateType;
	}

	/**
	 * 设置stateType的值
	 *   
	 * @param stateType 被设置的值
	 */
	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	/**   
	 * 得到name的值   
	 *   
	 * @return name的值
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name的值
	 *   
	 * @param name 被设置的值
	 */
	public void setName(String name) {
		this.name = name;
	}

}
