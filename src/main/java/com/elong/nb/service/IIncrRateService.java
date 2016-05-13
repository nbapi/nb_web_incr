package com.elong.nb.service;

import java.util.Date;

import com.elong.nb.model.IncrRate;

public interface IIncrRateService {

	public IncrRate getLastIncrRate(Date lastTime);

}
