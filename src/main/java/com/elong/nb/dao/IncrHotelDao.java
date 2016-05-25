package com.elong.nb.dao;

import java.util.Map;

import com.elong.nb.db.DataSource;
import com.elong.nb.model.IncrHotel;

@DataSource("read_datasource")
public interface IncrHotelDao {

	public IncrHotel getLastIncrHotel(Map<String, Object> param);
	
}
