/**   
 * @(#)Inventory.java	2016年8月23日	下午4:41:59	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * 库存模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月23日 下午4:41:59   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class Inventory {

	/** 
	 * 酒店ID(MHotelID)
	 *
	 * String Inventory.java HotelID
	 */
	@SerializedName(value = "HotelID", alternate = "hotelID")
	public String HotelID;

	/** 
	 * 房型ID
	 *
	 * String Inventory.java RoomTypeID
	 */
	@SerializedName(value = "RoomTypeId", alternate = "roomTypeId")
	public String RoomTypeID;

	/** 
	 * 酒店编码(SHotelID)
	 *
	 * String Inventory.java HotelCode
	 */
	@SerializedName(value = "HotelCode", alternate = "hotelCode")
	public String HotelCode;

	/** 
	 * 库存时间
	 *
	 * Date Inventory.java AvailableDate
	 */
	@SerializedName(value = "Date", alternate = "date")
	public Date AvailableDate;

	/** 
	 * 库存状态
	 *
	 * boolean Inventory.java Status
	 */
	@SerializedName(value = "Status", alternate = "status")
	public boolean Status;

	/** 
	 * 库存数量
	 *
	 * int Inventory.java AvailableAmount
	 */
	@SerializedName(value = "Amount", alternate = "amount")
	public int AvailableAmount;

	/** 
	 * 是否超售 0---可超售，1—不可超售
	 *
	 * int Inventory.java OverBooking
	 */
	@SerializedName(value = "OverBooking", alternate = "overBooking")
	public int OverBooking;

	/** 
	 * 可用开始日期
	 *
	 * Date Inventory.java StartDate
	 */
	@SerializedName(value = "StartDate", alternate = "startDate")
	public Date StartDate;

	/** 
	 * 可用结束日期
	 *
	 * Date Inventory.java EndDate
	 */
	@SerializedName(value = "EndDate", alternate = "endDate")
	public Date EndDate;

	/** 
	 * 可用开始时间, 格式： HH:mm:ss
	 *
	 * String Inventory.java StartTime
	 */
	@SerializedName(value = "StartTime", alternate = "startTime")
	public String StartTime;

	/** 
	 * 可用结束时间, 格式： HH:mm:ss
	 *
	 * String Inventory.java EndTime
	 */
	@SerializedName(value = "EndTime", alternate = "endTime")
	public String EndTime;

	/** 
	 * 是否即时确认
	 *
	 * boolean Inventory.java IsInstantConfirm
	 */
	@SerializedName(value = "IsInstantConfirm", alternate = "isInstantConfirm")
	public boolean IsInstantConfirm;

	/** 
	 * 即时确认）开始时间
	 *
	 * String Inventory.java IC_BeginTime
	 */
	@SerializedName(value = "IC_BeginTime", alternate = "iC_BeginTime")
	public String IC_BeginTime;

	/** 
	 * （即时确认）结束时间
	 *
	 * String Inventory.java IC_EndTime
	 */
	@SerializedName(value = "IC_EndTime", alternate = "iC_EndTime")
	public String IC_EndTime;

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
	 * 得到roomTypeID的值   
	 *   
	 * @return roomTypeID的值
	 */
	public String getRoomTypeID() {
		return RoomTypeID;
	}

	/**
	 * 设置roomTypeID的值
	 *   
	 * @param roomTypeID 被设置的值
	 */
	public void setRoomTypeID(String roomTypeID) {
		RoomTypeID = roomTypeID;
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
	 * 得到availableDate的值   
	 *   
	 * @return availableDate的值
	 */
	public Date getAvailableDate() {
		return AvailableDate;
	}

	/**
	 * 设置availableDate的值
	 *   
	 * @param availableDate 被设置的值
	 */
	public void setAvailableDate(Date availableDate) {
		AvailableDate = availableDate;
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
	 * 得到availableAmount的值   
	 *   
	 * @return availableAmount的值
	 */
	public int getAvailableAmount() {
		return AvailableAmount;
	}

	/**
	 * 设置availableAmount的值
	 *   
	 * @param availableAmount 被设置的值
	 */
	public void setAvailableAmount(int availableAmount) {
		AvailableAmount = availableAmount;
	}

	/**   
	 * 得到overBooking的值   
	 *   
	 * @return overBooking的值
	 */
	public int getOverBooking() {
		return OverBooking;
	}

	/**
	 * 设置overBooking的值
	 *   
	 * @param overBooking 被设置的值
	 */
	public void setOverBooking(int overBooking) {
		OverBooking = overBooking;
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
	 * 得到startTime的值   
	 *   
	 * @return startTime的值
	 */
	public String getStartTime() {
		return StartTime;
	}

	/**
	 * 设置startTime的值
	 *   
	 * @param startTime 被设置的值
	 */
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	/**   
	 * 得到endTime的值   
	 *   
	 * @return endTime的值
	 */
	public String getEndTime() {
		return EndTime;
	}

	/**
	 * 设置endTime的值
	 *   
	 * @param endTime 被设置的值
	 */
	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	/**   
	 * 得到isInstantConfirm的值   
	 *   
	 * @return isInstantConfirm的值
	 */
	public boolean isIsInstantConfirm() {
		return IsInstantConfirm;
	}

	/**
	 * 设置isInstantConfirm的值
	 *   
	 * @param isInstantConfirm 被设置的值
	 */
	public void setIsInstantConfirm(boolean isInstantConfirm) {
		IsInstantConfirm = isInstantConfirm;
	}

	/**   
	 * 得到iC_BeginTime的值   
	 *   
	 * @return iC_BeginTime的值
	 */
	public String getIC_BeginTime() {
		return IC_BeginTime;
	}

	/**
	 * 设置iC_BeginTime的值
	 *   
	 * @param iC_BeginTime 被设置的值
	 */
	public void setIC_BeginTime(String iC_BeginTime) {
		IC_BeginTime = iC_BeginTime;
	}

	/**   
	 * 得到iC_EndTime的值   
	 *   
	 * @return iC_EndTime的值
	 */
	public String getIC_EndTime() {
		return IC_EndTime;
	}

	/**
	 * 设置iC_EndTime的值
	 *   
	 * @param iC_EndTime 被设置的值
	 */
	public void setIC_EndTime(String iC_EndTime) {
		IC_EndTime = iC_EndTime;
	}

}
