package com.elong.nb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.ibatis.IBatisEntityDao;
import com.elong.nb.model.IncrRate;

@Service
public class IncrRateDao extends IBatisEntityDao<IncrRate> {

	public IncrRate getLastIncrRate(Date lastTime) {
		Map paramMap = new HashMap();
		paramMap.put("lastTime", lastTime);
		IncrRate incrRate = sqlSessionTemplate.selectOne("getLastIncrRate",
				paramMap);
		if (incrRate == null) {
			incrRate = sqlSessionTemplate.selectOne("getLastIncrRate");
		}
		return incrRate;
	}
}
