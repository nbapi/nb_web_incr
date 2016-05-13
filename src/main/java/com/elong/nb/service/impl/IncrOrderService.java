package com.elong.nb.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.common.model.EnumOrderType;
import com.elong.nb.dao.IncrOrderDao;
import com.elong.nb.model.IncrOrder;
import com.elong.nb.service.IIncrOrderService;

@Service
public class IncrOrderService implements IIncrOrderService {

	@Resource
	private IncrOrderDao dao;

	public IncrOrder getLastIncrOrder(Date lastTime, EnumOrderType orderType,
			String proxyId, Integer orderFrom) {
		return dao.getLastIncrOrder(lastTime, orderType, proxyId, orderFrom);
	}

	public IncrOrder getIncrOrders(Date lastTime, EnumOrderType orderType,
			String proxyId, Integer orderFrom, int maxRecordCount) {
		return dao.getIncrOrders(lastTime, orderType, proxyId, orderFrom,
				maxRecordCount);
	}
}
