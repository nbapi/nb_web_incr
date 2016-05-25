package com.elong.nb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.IncrRateDao;
import com.elong.nb.model.IncrRate;
import com.elong.nb.service.IIncrRateService;

@Service
public class IncrRateService implements IIncrRateService {

	@Resource
	private IncrRateDao dao;

	public IncrRate getLastIncrRate(Date lastTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastTime", lastTime);
		IncrRate incrRate = dao.getLastIncrRate(paramMap);
		if (incrRate == null) {
			incrRate = dao.getLastIncrRate(new HashMap<String, Object>());
		}
		return incrRate;
	}

}
