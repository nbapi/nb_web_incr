package com.elong.nb.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class IncrOrder implements Serializable{
	private static final long serialVersionUID = -9161402823822245042L;
	private BigInteger IncrID;
	private Date  ChangeTime;
	private Integer  OrderId;
	private String  Status;
	private Date ArrivalDate;
	private Date DepartureDate;
	private double TotalPrice;
	private int NumberOfRooms;
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
	public Integer getOrderId() {
		return OrderId;
	}
	public void setOrderId(Integer orderId) {
		OrderId = orderId;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public Date getArrivalDate() {
		return ArrivalDate;
	}
	public void setArrivalDate(Date arrivalDate) {
		ArrivalDate = arrivalDate;
	}
	public Date getDepartureDate() {
		return DepartureDate;
	}
	public void setDepartureDate(Date departureDate) {
		DepartureDate = departureDate;
	}
	public double getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}
	public int getNumberOfRooms() {
		return NumberOfRooms;
	}
	public void setNumberOfRooms(int numberOfRooms) {
		NumberOfRooms = numberOfRooms;
	}
	
}
