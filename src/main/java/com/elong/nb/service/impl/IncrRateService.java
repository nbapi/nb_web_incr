package com.elong.nb.service.impl;

import java.util.Date;

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
		return dao.getLastIncrRate(lastTime);
	}

}
