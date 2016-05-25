package com.elong.nb.dao;

import java.util.Map;

import com.elong.nb.db.DataSource;
import com.elong.nb.model.IncrOrder;

@DataSource("read_datasource")
public interface IncrOrderDao {

	public IncrOrder getIncrOrders(Map<String, Object> paramMap);

}
