package com.elong.nb.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.elong.nb.model.IncrState;

public interface IIncrStateService {

	public IncrState getLastIncrState(Date lastTime);

	public List<IncrState> getIncrStates(BigInteger lastId);

}
