package com.elong.nb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.ibatis.IBatisEntityDao;
import com.elong.nb.model.IncrHotel;

@Service
public class IncrHotelDao extends IBatisEntityDao<IncrHotel> {

	public IncrHotel getLastIncrHotel(Date lastTime) {
		Map paramMap = new HashMap();
		paramMap.put("lastTime", lastTime);
		IncrHotel incrHotel = sqlSessionTemplate.selectOne("getLastIncrHotel",
				paramMap);
		if (incrHotel == null) {
			incrHotel = sqlSessionTemplate.selectOne("getLastIncrHotel");
		}
		return incrHotel;
	}
}
