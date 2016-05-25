package com.elong.nb.dao;

import java.util.Map;

import com.elong.nb.db.DataSource;
import com.elong.nb.model.IncrRate;

@DataSource("read_datasource")
public interface IncrRateDao {

	public IncrRate getLastIncrRate(Map<String, Object> paramMap);
	
}
