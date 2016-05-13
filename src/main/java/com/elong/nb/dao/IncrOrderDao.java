package com.elong.nb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.elong.nb.common.model.EnumOrderType;
import com.elong.nb.dao.ibatis.IBatisEntityDao;
import com.elong.nb.model.IncrOrder;


@Service
public class IncrOrderDao extends IBatisEntityDao<IncrOrder> {

	public IncrOrder getLastIncrOrder(Date lastTime, EnumOrderType orderType,
			String proxyId, Integer orderFrom) {
		Map paramMap = new HashMap();
		paramMap.put("maxRecordCount", 1);
		if (orderType == EnumOrderType.OrderFrom) {
			paramMap.put("orderFrom", orderFrom);
		} else {
			paramMap.put("proxyId", proxyId);
		}
		if (lastTime.getYear() + 1900 >= 2015) {
			paramMap.put("lastTime", lastTime);
		}
		return sqlSessionTemplate.selectOne("getLastIncrOrder", paramMap);
	}

	public IncrOrder getIncrOrders(Date lastTime, EnumOrderType orderType,
			String proxyId, Integer orderFrom, int maxRecordCount) {
		Map paramMap = new HashMap();
		paramMap.put("maxRecordCount", maxRecordCount);
		if (orderType == EnumOrderType.OrderFrom) {
			paramMap.put("orderFrom", orderFrom);
		} else {
			paramMap.put("proxyId", proxyId);
		}
		if (lastTime.getYear() + 1900 >= 2015) {
			paramMap.put("lastTime", lastTime);
		}
		return sqlSessionTemplate.selectOne("getLastIncrOrder", paramMap);
	}
}
