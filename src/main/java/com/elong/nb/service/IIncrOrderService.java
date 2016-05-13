package com.elong.nb.service;

import java.util.Date;

import com.elong.nb.common.model.EnumOrderType;
import com.elong.nb.model.IncrOrder;

public interface IIncrOrderService {

	public IncrOrder getLastIncrOrder(Date lastTime, EnumOrderType orderType,
			String proxyId, Integer orderFrom);

	public IncrOrder getIncrOrders(Date lastTime, EnumOrderType orderType,
			String proxyId, Integer orderFrom, int maxRecordCount);
	
}
