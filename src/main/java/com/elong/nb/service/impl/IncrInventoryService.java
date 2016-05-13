package com.elong.nb.service.impl;

import java.util.Date;

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
		return dao.getLastIncrInventory(lastTime);
	}
}
