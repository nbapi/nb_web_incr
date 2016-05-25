package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.IncrStateDao;
import com.elong.nb.model.IncrState;
import com.elong.nb.service.IIncrStateService;

@Service
public class IncrStateService implements IIncrStateService {

	@Resource
	private IncrStateDao dao;

	public IncrState getLastIncrState(Date lastTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastTime", lastTime);
		IncrState incrState = dao.getLastIncrState(paramMap);
		if (incrState == null) {
			incrState = dao.getLastIncrState(new HashMap<String, Object>());
		}
		return incrState;
	}

	public List<IncrState> getIncrStates(BigInteger lastId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastId", lastId);
		return dao.getIncrStates(paramMap);
	}
}
