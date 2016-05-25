package com.elong.nb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.IncrInventoryDao;
import com.elong.nb.model.IncrInventory;
import com.elong.nb.service.IIncrInventoryService;

@Service
public class IncrInventoryService implements IIncrInventoryService {

	@Resource
	private IncrInventoryDao dao;

	public IncrInventory getLastIncrInventory(Date lastTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastTime", lastTime);

		IncrInventory incrInventory = dao.getLastIncrInventory(paramMap);
		if (incrInventory == null)
			incrInventory = dao
					.getLastIncrInventory(new HashMap<String, Object>());

		return incrInventory;
	}
}
