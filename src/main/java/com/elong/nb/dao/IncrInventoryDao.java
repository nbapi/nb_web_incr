package com.elong.nb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.ibatis.IBatisEntityDao;
import com.elong.nb.model.IncrInventory;

@Service
public class IncrInventoryDao extends IBatisEntityDao<IncrInventory> {

	public IncrInventory getLastIncrInventory(Date lastTime) {
		Map paramMap = new HashMap();
		paramMap.put("lastTime", lastTime);
		IncrInventory incrInventory = sqlSessionTemplate.selectOne(
				"getLastIncrInventory", paramMap);
		if (incrInventory == null) {
			incrInventory = sqlSessionTemplate
					.selectOne("getLastIncrInventory");
		}
		return incrInventory;
	}
}
