/**   
 * @(#)IncrInventoryService.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrInventoryResponse;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.bean.IncrInventory;
import com.elong.nb.service.IIncrInventoryService;
import com.elong.nb.submeter.service.ISubmeterService;
import com.elong.nb.util.IncrConst;

/**
 * 增量库存接口实现
 *
 * <p>
 * 修改历史: <br>
 * 修改日期 修改人员 版本 修改内容<br>
 * -------------------------------------------------<br>
 * 2016年8月19日 上午11:09:13 user 1.0 初始化创建<br>
 * </p>
 *
 * @author suht
 * @version 1.0
 * @since JDK1.7
 */
@Service
public class IncrInventoryService extends AbstractIncrService<IncrInventory> implements IIncrInventoryService {

	private static final Log logger = LogFactory.getLog(IncrInventoryService.class);

	@Resource(name = "incrInventorySubmeterService")
	private ISubmeterService<IncrInventory> incrInventorySubmeterService;

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
			return incrInventorySubmeterService.getLastIncrData();
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
			return incrInventorySubmeterService.getOneIncrData(lastTime);
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
	 * @see com.elong.nb.service.IIncrInventoryService#getIncrInventories(long,
	 *      int)
	 */
	@Override
	public List<IncrInventory> getIncrInventories(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		if (maxRecordCount == 0) {
			logger.error("getIncrInventories error,due to the parameter 'maxRecordCount' is 0.");
			throw new IncrException("getIncrInventories error,due to the parameter 'maxRecordCount' is 0.");
		}
		try {
			return incrInventorySubmeterService.getIncrDataList(lastId, maxRecordCount, proxyAccount);
		} catch (Exception e) {
			logger.error("getIncrInventories error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取库存增量数据
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getIncrDatas(long, int, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	protected List<IncrInventory> getIncrDatas(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		return getIncrInventories(lastId, maxRecordCount, proxyAccount);
	}

	/** 
	 * 获取库存增量最后的更新ID 
	 *
	 * @param lastTime
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getLastId(java.util.Date, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	protected BigInteger getLastId(Date lastTime, ProxyAccount proxyAccount) {
		IncrInventory incrInventory = getOneIncrInventory(lastTime);
		if (incrInventory == null) {
			incrInventory = getLastIncrInventory();
		}
		return incrInventory == null ? IncrConst.bigIntegerNegativeOne : BigInteger.valueOf(incrInventory.getID());
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
