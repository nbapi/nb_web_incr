/**   
 * @(#)Rate.java	2016年8月23日	下午3:37:20	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * 价格模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月23日 下午3:37:20   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class Rate {

	/** 
	 * 酒店ID
	 *
	 * String Rate.java HotelID
	 */
	@SerializedName(value = "HotelID", alternate = "hotelID")
	private String HotelID;

	/** 
	 * s酒店ID
	 *
	 * String Rate.java HotelCode
	 */
	@SerializedName(value = "HotelCode", alternate = "hotelCode")
	private String HotelCode;

	/** 
	 * 房型ID
	 *
	 * String Rate.java RoomTypeId
	 */
	@SerializedName(value = "RoomTypeId", alternate = "roomTypeId")
	private String RoomTypeId;

	/** 
	 * RatePlan
	 *
	 * int Rate.java RateplanId
	 */
	@SerializedName(value = "RateplanId", alternate = "rateplanId")
	private int RateplanId;

	/** 
	 * 开始时间
	 *
	 * Date Rate.java StartDate
	 */
	@SerializedName(value = "StartDate", alternate = "startDate")
	private Date StartDate;

	/** 
	 * 结束时间
	 *
	 * Date Rate.java EndDate
	 */
	@SerializedName(value = "EndDate", alternate = "endDate")
	private Date EndDate;

	/** 
	 * 状态
	 *
	 * boolean Rate.java Status
	 */
	@SerializedName(value = "Status", alternate = "status")
	private boolean Status;

	/** 
	 * 平日卖价
	 *
	 * Double Rate.java Member
	 */
	@SerializedName(value = "Member", alternate = "member")
	private Double Member;

	/** 
	 * 周末卖价
	 *
	 * Double Rate.java Weekend
	 */
	@SerializedName(value = "Weekend", alternate = "weekend")
	private Double Weekend;

	/** 
	 * 平日底价
	 *
	 * Double Rate.java MemberCost
	 */
	@SerializedName(value = "MemberCost", alternate = "memberCost")
	private Double MemberCost;

	/** 
	 * 周末底价
	 *
	 * Double Rate.java WeekendCost
	 */
	@SerializedName(value = "WeekendCost", alternate = "weekendCost")
	private Double WeekendCost;

	/** 
	 * 加床价 v1.01新增
	 *
	 * Double Rate.java AddBed
	 */
	@SerializedName(value = "AddBed", alternate = "addBed")
	private Double AddBed;

	/** 
	 * 房价ID(v1.08新增)
	 *
	 * long Rate.java PriceID
	 */
	@SerializedName(value = "PriceID", alternate = "priceID")
	private long PriceID;

	/** 
	 * 货币代码(v1.08新增)
	 *
	 * String Rate.java CurrencyCode
	 */
	@SerializedName(value = "CurrencyCode", alternate = "currencyCode")
	private String CurrencyCode;

	/**   
	 * 得到hotelID的值   
	 *   
	 * @return hotelID的值
	 */
	public String getHotelID() {
		return HotelID;
	}

	/**
	 * 设置hotelID的值
	 *   
	 * @param hotelID 被设置的值
	 */
	public void setHotelID(String hotelID) {
		HotelID = hotelID;
	}

	/**   
	 * 得到hotelCode的值   
	 *   
	 * @return hotelCode的值
	 */
	public String getHotelCode() {
		return HotelCode;
	}

	/**
	 * 设置hotelCode的值
	 *   
	 * @param hotelCode 被设置的值
	 */
	public void setHotelCode(String hotelCode) {
		HotelCode = hotelCode;
	}

	/**   
	 * 得到roomTypeId的值   
	 *   
	 * @return roomTypeId的值
	 */
	public String getRoomTypeId() {
		return RoomTypeId;
	}

	/**
	 * 设置roomTypeId的值
	 *   
	 * @param roomTypeId 被设置的值
	 */
	public void setRoomTypeId(String roomTypeId) {
		RoomTypeId = roomTypeId;
	}

	/**   
	 * 得到rateplanId的值   
	 *   
	 * @return rateplanId的值
	 */
	public int getRateplanId() {
		return RateplanId;
	}

	/**
	 * 设置rateplanId的值
	 *   
	 * @param rateplanId 被设置的值
	 */
	public void setRateplanId(int rateplanId) {
		RateplanId = rateplanId;
	}

	/**   
	 * 得到startDate的值   
	 *   
	 * @return startDate的值
	 */
	public Date getStartDate() {
		return StartDate;
	}

	/**
	 * 设置startDate的值
	 *   
	 * @param startDate 被设置的值
	 */
	public void setStartDate(Date startDate) {
		StartDate = startDate;
	}

	/**   
	 * 得到endDate的值   
	 *   
	 * @return endDate的值
	 */
	public Date getEndDate() {
		return EndDate;
	}

	/**
	 * 设置endDate的值
	 *   
	 * @param endDate 被设置的值
	 */
	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	/**   
	 * 得到status的值   
	 *   
	 * @return status的值
	 */
	public boolean isStatus() {
		return Status;
	}

	/**
	 * 设置status的值
	 *   
	 * @param status 被设置的值
	 */
	public void setStatus(boolean status) {
		Status = status;
	}

	/**   
	 * 得到member的值   
	 *   
	 * @return member的值
	 */
	public Double getMember() {
		return Member;
	}

	/**
	 * 设置member的值
	 *   
	 * @param member 被设置的值
	 */
	public void setMember(Double member) {
		Member = member;
	}

	/**   
	 * 得到weekend的值   
	 *   
	 * @return weekend的值
	 */
	public Double getWeekend() {
		return Weekend;
	}

	/**
	 * 设置weekend的值
	 *   
	 * @param weekend 被设置的值
	 */
	public void setWeekend(Double weekend) {
		Weekend = weekend;
	}

	/**   
	 * 得到memberCost的值   
	 *   
	 * @return memberCost的值
	 */
	public Double getMemberCost() {
		return MemberCost;
	}

	/**
	 * 设置memberCost的值
	 *   
	 * @param memberCost 被设置的值
	 */
	public void setMemberCost(Double memberCost) {
		MemberCost = memberCost;
	}

	/**   
	 * 得到weekendCost的值   
	 *   
	 * @return weekendCost的值
	 */
	public Double getWeekendCost() {
		return WeekendCost;
	}

	/**
	 * 设置weekendCost的值
	 *   
	 * @param weekendCost 被设置的值
	 */
	public void setWeekendCost(Double weekendCost) {
		WeekendCost = weekendCost;
	}

	/**   
	 * 得到addBed的值   
	 *   
	 * @return addBed的值
	 */
	public Double getAddBed() {
		return AddBed;
	}

	/**
	 * 设置addBed的值
	 *   
	 * @param addBed 被设置的值
	 */
	public void setAddBed(Double addBed) {
		AddBed = addBed;
	}

	/**   
	 * 得到priceID的值   
	 *   
	 * @return priceID的值
	 */
	public long getPriceID() {
		return PriceID;
	}

	/**
	 * 设置priceID的值
	 *   
	 * @param priceID 被设置的值
	 */
	public void setPriceID(long priceID) {
		PriceID = priceID;
	}

	/**   
	 * 得到currencyCode的值   
	 *   
	 * @return currencyCode的值
	 */
	public String getCurrencyCode() {
		return CurrencyCode;
	}

	/**
	 * 设置currencyCode的值
	 *   
	 * @param currencyCode 被设置的值
	 */
	public void setCurrencyCode(String currencyCode) {
		CurrencyCode = currencyCode;
	}

}
