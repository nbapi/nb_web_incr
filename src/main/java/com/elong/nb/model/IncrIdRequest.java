package com.elong.nb.model;

import java.util.Date;

import org.springframework.validation.Errors;

import com.elong.nb.common.model.ProxyAccount;

public class IncrIdRequest {
	private Date LastTime;
	private EnumIncrType IncrType;
	public Date getLastTime() {
		return LastTime;
	}
	public void setLastTime(Date lastTime) {
		LastTime = lastTime;
	}
	public EnumIncrType getIncrType() {
		return IncrType;
	}
	public void setIncrType(EnumIncrType incrType) {
		IncrType = incrType;
	}
	public void validate(Errors errors,ProxyAccount proxyAccount) {
		if(LastTime == null){
			errors.rejectValue("Request.LastTime","NotNull");
			return;
		}
		if(IncrType == null){
			errors.rejectValue("Request.IncrType","NotNull");
			return;
		}
	}
}
