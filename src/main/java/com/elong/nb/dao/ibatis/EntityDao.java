package com.elong.nb.dao.ibatis;

import java.util.List;

public interface EntityDao<T> {
	
	T get(String id);

	List<T> getAll();

	void save(Object o);

	int removeById(String id);
}
