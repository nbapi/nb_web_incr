package com.elong.nb.dao;

import java.util.Map;

import com.elong.nb.db.DataSource;
import com.elong.nb.model.IncrInventory;

@DataSource("read_datasource")
public interface IncrInventoryDao {

	public IncrInventory getLastIncrInventory(Map<String, Object> params);
	
}
