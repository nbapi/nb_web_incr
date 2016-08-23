/**   
 * @(#)IncrStateService.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.elong.nb.dao.IncrStateDao;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.IncrState;
import com.elong.nb.model.IncrStateResponse;
import com.elong.nb.service.IIncrStateService;
import com.elong.nb.util.IncrConst;

/**
 * 状态增量接口实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午2:15:55   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class IncrStateService extends AbstractIncrService<IncrState> implements IIncrStateService {

	private static final Log logger = LogFactory.getLog(IncrStateService.class);

	@Resource
	private IncrStateDao incrStateDao;

	/** 
	 * 获取最大IncrID的状态增量 
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrStateService#getLastIncrState()    
	 */
	@Override
	public IncrState getLastIncrState() {
		try {
			return incrStateDao.getLastIncrState();
		} catch (Exception e) {
			logger.error("getLastIncrState error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastTime的最早发生变化的状态增量
	 *
	 * @param lastTime
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrStateService#getOneIncrState(java.util.Date)    
	 */
	@Override
	public IncrState getOneIncrState(Date lastTime) {
		if (lastTime == null) {
			logger.error("getOneIncrState error,due to the parameter 'lastTime' is null.");
			throw new IncrException("getOneIncrState error,due to the parameter 'lastTime' is null.");
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lastTime", lastTime);
			return incrStateDao.getOneIncrState(paramMap);
		} catch (Exception e) {
			logger.error("getOneIncrState error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastId的maxRecordCount条状态增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrStateService#getIncrStates(long, int)    
	 */
	@Override
	public List<IncrState> getIncrStates(long lastId, int maxRecordCount) {
		if (lastId == 0) {
			logger.error("getIncrStates error,due to the parameter 'lastId' is 0.");
			throw new IncrException("getIncrInventories error,due to the parameter 'lastId' is 0.");
		}
		if (maxRecordCount == 0) {
			logger.error("getIncrStates error,due to the parameter 'maxRecordCount' is 0.");
			throw new IncrException("getIncrInventories error,due to the parameter 'maxRecordCount' is 0.");
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lastId", lastId);
			paramMap.put("maxRecordCount", maxRecordCount);
			return incrStateDao.getIncrStates(paramMap);
		} catch (Exception e) {
			logger.error("getIncrStates error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取状态增量数据
	 *
	 * @param params
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getIncrDatas(java.util.Map)    
	 */
	@Override
	protected List<IncrState> getIncrDatas(Map<String, Object> params) {
		if (params == null || !params.containsKey("lastId") || !params.containsKey("maxRecordCount")) {
			throw new IncrException(
					"the map 'params' is requeried,and the map 'params' must contain the key ['lastId' or 'maxRecordCount'].");
		}
		Object lastIdObj = params.get("lastId");
		Object maxRecordCountObj = params.get("maxRecordCount");
		if (lastIdObj == null || maxRecordCountObj == null) {
			throw new IncrException(
					"the parameter['lastId' or 'maxRecordCount']  which belongs to the map 'params' must not be null.");
		}
		long lastId = (long) params.get("lastId");
		int maxRecordCount = (int) params.get("maxRecordCount");

		// TODO type=6问题
		return getIncrStates(lastId, maxRecordCount);
	}

	/** 
	 * 获取状态增量最后更新ID
	 *
	 * @param params
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getLastId(java.util.Map)    
	 */
	@Override
	protected BigInteger getLastId(Map<String, Object> params) {
		if (params == null || !params.containsKey("lastTime")) {
			throw new IncrException("the map 'params' is requeried,and the map 'params' must contain the key 'lastTime'.");
		}
		Object lastTimeObj = params.get("lastTime");
		if (lastTimeObj == null) {
			throw new IncrException("the parameter 'lastTime' which belongs to the map 'params' must not be null.");
		}
		if (!(lastTimeObj instanceof Date)) {
			throw new IncrException("the parameter 'lastTime' which belongs to the map 'params' must be date type.");
		}
		Date lastTime = (Date) params.get("lastTime");
		IncrState incrState = getOneIncrState(lastTime);
		if (incrState == null) {
			incrState = getLastIncrState();
		}
		return incrState == null ? IncrConst.bigIntegerNegativeOne : incrState.getIncrID();
	}

	/** 
	 * 创建增量响应模型
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#createIncrResponse()    
	 */
	@Override
	protected IncrResponse<IncrState> createIncrResponse() {
		return new IncrStateResponse();
	}

}
