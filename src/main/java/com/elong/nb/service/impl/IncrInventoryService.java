/**   
 * @(#)IncrInventoryService.java	2016年8月19日	上午10:41:27	   
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

import com.elong.nb.dao.IncrInventoryDao;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrInventory;
import com.elong.nb.model.IncrInventoryResponse;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.service.IIncrInventoryService;
import com.elong.nb.util.IncrConst;

/**
 * 增量库存接口实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 上午11:09:13   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class IncrInventoryService extends AbstractIncrService<IncrInventory> implements IIncrInventoryService {

	private static final Log logger = LogFactory.getLog(IncrInventoryService.class);

	@Resource
	private IncrInventoryDao incrInventoryDao;

	/** 
	 * 获取最大IncrID的库存增量
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrInventoryService#getLastIncrInventory()    
	 */
	@Override
	public IncrInventory getLastIncrInventory() {
		try {
			return incrInventoryDao.getLastIncrInventory();
		} catch (Exception e) {
			logger.error("getLastIncrInventory error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastTime的最早发生变化的库存增量
	 *
	 * @param lastTime
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrInventoryService#getOneIncrInventory(java.util.Date)    
	 */
	@Override
	public IncrInventory getOneIncrInventory(Date lastTime) {
		if (lastTime == null) {
			logger.error("getOneIncrInventory error,due to the parameter 'lastTime' is null.");
			throw new IncrException("getOneIncrInventory error,due to the parameter 'lastTime' is null.");
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lastTime", lastTime);
			return incrInventoryDao.getOneIncrInventory(paramMap);
		} catch (Exception e) {
			logger.error("getOneIncrInventory error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastId的maxRecordCount条库存增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrInventoryService#getIncrInventories(long, int)    
	 */
	@Override
	public List<IncrInventory> getIncrInventories(long lastId, int maxRecordCount) {
		if (lastId == 0) {
			logger.error("getIncrInventories error,due to the parameter 'lastId' is 0.");
			throw new IncrException("getIncrInventories error,due to the parameter 'lastId' is 0.");
		}
		if (maxRecordCount == 0) {
			logger.error("getIncrInventories error,due to the parameter 'maxRecordCount' is 0.");
			throw new IncrException("getIncrInventories error,due to the parameter 'maxRecordCount' is 0.");
		}
		try {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("lastId", lastId);
			paramMap.put("maxRecordCount", maxRecordCount);
			return incrInventoryDao.getIncrInventories(paramMap);
		} catch (Exception e) {
			logger.error("getIncrInventories error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取增量数据
	 *
	 * @param params
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getIncrDatas(java.util.Map)    
	 */
	@Override
	protected List<IncrInventory> getIncrDatas(Map<String, Object> params) {
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

		// TODO 黑名单处理
		return getIncrInventories(lastId, maxRecordCount);
	}

	/** 
	 * 获取最后的更新ID
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
		IncrInventory incrInventory = getOneIncrInventory(lastTime);
		if (incrInventory == null) {
			incrInventory = getLastIncrInventory();
		}
		return incrInventory == null ? IncrConst.bigIntegerNegativeOne : incrInventory.getIncrID();
	}

	/** 
	 * 创建增量响应模型
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#createIncrResponse()    
	 */
	@Override
	protected IncrResponse<IncrInventory> createIncrResponse() {
		return new IncrInventoryResponse();
	}

}
