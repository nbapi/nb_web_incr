package com.elong.nb.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class IncrRate implements Serializable{
	private static final long serialVersionUID = 4054356847210074032L;
	private BigInteger IncrID;
	private Date ChangeTime;
	
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
	
}
