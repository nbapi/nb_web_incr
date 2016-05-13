package com.elong.nb.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.IncrHotelDao;
import com.elong.nb.model.IncrHotel;
import com.elong.nb.service.IIncrHotelService;

@Service
public class IncrHotelService implements IIncrHotelService {

	@Resource
	private IncrHotelDao dao;
	
	public IncrHotel getLastIncrHotel(Date lastTime) {
		return dao.getLastIncrHotel(lastTime);
	}
}
