package com.elong.nb.dao;

import java.util.List;
import java.util.Map;

import com.elong.nb.db.DataSource;
import com.elong.nb.model.IncrState;

@DataSource("read_datasource")
public interface IncrStateDao {

	public IncrState getLastIncrState(Map<String, Object> paramMap);

	public List<IncrState> getIncrStates(Map<String, Object> paramMap);

}
