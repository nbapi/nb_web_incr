package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.IncrStateDao;
import com.elong.nb.model.IncrState;
import com.elong.nb.service.IIncrStateService;

@Service
public class IncrStateService implements IIncrStateService{

	@Resource
	private IncrStateDao dao;

	public IncrState getLastIncrState(Date lastTime) {
		return dao.getLastIncrState(lastTime);
	}

	public List<IncrState> getIncrStates(BigInteger lastId) {
		return dao.getIncrStates(lastId);
	}
}
