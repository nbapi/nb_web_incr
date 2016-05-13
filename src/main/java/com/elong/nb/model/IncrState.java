package com.elong.nb.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.google.gson.annotations.Until;

public class IncrState implements Serializable{
	private static final long serialVersionUID = -3286669616692742103L;
	private BigInteger IncrID;
	private Date  ChangeTime;
	private String  HotelId;
	private String  HotelCode;
	private String  RoomId;
	private String  RoomTypeId;
	private String  RatePlanId;
	private Integer  Status;
	private Boolean StatusForJson;
	private Integer StateType;
	@Until(1.17)
	private String Name;
	public BigInteger getIncrID() {
		return IncrID;
	}
	public void setIncrID(BigInteger incrID) {
		IncrID = incrID;
	}
	public Date getChangeTime() {
		return ChangeTime;
	}
	public void setChangeTime(Date changeTime) {
		ChangeTime = changeTime;
	}
	public String getHotelId() {
		return HotelId;
	}
	public void setHotelId(String hotelId) {
		HotelId = hotelId;
	}
	public String getHotelCode() {
		return HotelCode;
	}
	public void setHotelCode(String hotelCode) {
		HotelCode = hotelCode;
	}
	public String getRoomId() {
		return RoomId;
	}
	public void setRoomId(String roomId) {
		RoomId = roomId;
	}
	public String getRoomTypeId() {
		return RoomTypeId;
	}
	public void setRoomTypeId(String roomTypeId) {
		RoomTypeId = roomTypeId;
	}
	public String getRatePlanId() {
		return RatePlanId;
	}
	public void setRatePlanId(String ratePlanId) {
		RatePlanId = ratePlanId;
	}
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	public Boolean getStatusForJson() {
		return StatusForJson;
	}
	public void setStatusForJson(Boolean statusForJson) {
		StatusForJson = statusForJson;
	}
	public Integer getStateType() {
		return StateType;
	}
	public void setStateType(Integer stateType) {
		StateType = stateType;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	
}
