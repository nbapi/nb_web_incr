package com.elong.nb.dao;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.ibatis.IBatisEntityDao;
import com.elong.nb.model.IncrState;

@Service
public class IncrStateDao extends IBatisEntityDao<IncrState> {
	public IncrState getLastIncrState(Date lastTime) {
		Map paramMap = new HashMap();
		paramMap.put("lastTime", lastTime);
		IncrState incrState = sqlSessionTemplate.selectOne("getLastIncrState",
				paramMap);
		if (incrState == null) {
			incrState = sqlSessionTemplate.selectOne("getLastIncrState");
		}
		return incrState;
	}

	public List<IncrState> getIncrStates(BigInteger lastId) {
		Map paramMap = new HashMap();
		paramMap.put("lastId", lastId);
		return sqlSessionTemplate.selectList("getIncrStates", paramMap);
	}
}
