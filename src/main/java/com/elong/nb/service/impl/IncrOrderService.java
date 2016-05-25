package com.elong.nb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

	public IncrOrder getIncrOrders(Date lastTime, EnumOrderType orderType,
			String proxyId, Integer orderFrom, int maxRecordCount) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("maxRecordCount", maxRecordCount);
		if (orderType == EnumOrderType.OrderFrom) {
			paramMap.put("orderFrom", orderFrom);
		} else {
			paramMap.put("proxyId", proxyId);
		}
		if (lastTime.getYear() + 1900 >= 2015) {
			paramMap.put("lastTime", lastTime);
		}
		return dao.getIncrOrders(paramMap);
	}
}
