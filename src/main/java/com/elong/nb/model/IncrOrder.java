/**   
 * @(#)IncrOrder.java	2016年8月19日	下午4:00:13	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * 订单增量模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午4:00:13   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrOrder implements Serializable {

	/** 
	 * 序列化ID
	 *
	 * long IncrOrder.java serialVersionUID
	 */
	private static final long serialVersionUID = -9161402823822245042L;

	/** 
	 * 增量ID
	 *
	 * BigInteger IncrOrder.java incrID
	 */
	@SerializedName(value = "IncrID", alternate = "incrID")
	private BigInteger incrID;

	/** 
	 * 变化时间
	 *
	 * Date IncrOrder.java changeTime
	 */
	@SerializedName(value = "ChangeTime", alternate = "changeTime")
	private Date changeTime;

	/** 
	 * 订单ID
	 *
	 * Integer IncrOrder.java orderId
	 */
	@SerializedName(value = "OrderId", alternate = "orderId")
	private Integer orderId;

	/** 
	 * 订单状态
	 *
	 * String IncrOrder.java status
	 */
	@SerializedName(value = "Status", alternate = "status")
	private String status;

	/** 
	 * 入住日期
	 *
	 * Date IncrOrder.java arrivalDate
	 */
	@SerializedName(value = "ArrivalDate", alternate = "arrivalDate")
	private Date arrivalDate;

	/** 
	 * 离店日期
	 *
	 * Date IncrOrder.java departureDate
	 */
	@SerializedName(value = "DepartureDate", alternate = "departureDate")
	private Date departureDate;

	/** 
	 * 总价
	 *
	 * double IncrOrder.java totalPrice
	 */
	@SerializedName(value = "TotalPrice", alternate = "totalPrice")
	private double totalPrice;

	/** 
	 * 房间数量
	 *
	 * int IncrOrder.java numberOfRooms
	 */
	@SerializedName(value = "NumberOfRooms", alternate = "numberOfRooms")
	private int numberOfRooms;

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
	 * 得到orderId的值   
	 *   
	 * @return orderId的值
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * 设置orderId的值
	 *   
	 * @param orderId 被设置的值
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**   
	 * 得到status的值   
	 *   
	 * @return status的值
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置status的值
	 *   
	 * @param status 被设置的值
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**   
	 * 得到arrivalDate的值   
	 *   
	 * @return arrivalDate的值
	 */
	public Date getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * 设置arrivalDate的值
	 *   
	 * @param arrivalDate 被设置的值
	 */
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**   
	 * 得到departureDate的值   
	 *   
	 * @return departureDate的值
	 */
	public Date getDepartureDate() {
		return departureDate;
	}

	/**
	 * 设置departureDate的值
	 *   
	 * @param departureDate 被设置的值
	 */
	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	/**   
	 * 得到totalPrice的值   
	 *   
	 * @return totalPrice的值
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * 设置totalPrice的值
	 *   
	 * @param totalPrice 被设置的值
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**   
	 * 得到numberOfRooms的值   
	 *   
	 * @return numberOfRooms的值
	 */
	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	/**
	 * 设置numberOfRooms的值
	 *   
	 * @param numberOfRooms 被设置的值
	 */
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

}
