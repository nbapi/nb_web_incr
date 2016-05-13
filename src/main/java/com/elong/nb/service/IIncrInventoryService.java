package com.elong.nb.service;

import java.util.Date;

import com.elong.nb.model.IncrInventory;

public interface IIncrInventoryService {

	public IncrInventory getLastIncrInventory(Date lastTime);

}
