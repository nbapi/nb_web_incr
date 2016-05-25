package com.elong.nb.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lastTime", lastTime);
		
		IncrHotel incrHotel = dao.getLastIncrHotel(paramMap);
		if (incrHotel == null) {
			incrHotel = dao.getLastIncrHotel(new HashMap<String, Object>());
		}
		return incrHotel;
	}
}
