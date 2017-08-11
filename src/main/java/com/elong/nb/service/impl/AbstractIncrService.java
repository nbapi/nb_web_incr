/**   
 * @(#)AbstractIncrService.java	2016年8月19日	上午10:45:15	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.common.model.RestRequest;
import com.elong.nb.common.model.RestResponse;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrIdRequest;
import com.elong.nb.model.IncrIdResponse;
import com.elong.nb.model.IncrRequest;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.enums.EnumIncrType;
import com.elong.nb.service.IIncrService;
import com.elong.nb.util.IncrConst;

/**
 * 增量汇总接口实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 上午10:45:15   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public abstract class AbstractIncrService<T> implements IIncrService<T> {

	private static final Log logger = LogFactory.getLog(AbstractIncrService.class);

	/** 
	 * 获取增量数据
	 *
	 * @param restRequest
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrService#getIncrDatas(com.elong.nb.common.model.RestRequest)    
	 */
	@Override
	public RestResponse<IncrResponse<T>> getIncrDatas(RestRequest<IncrRequest> restRequest) {
		// 基本检查
		if (restRequest == null || restRequest.getRequest() == null) {
			logger.error("getIncrDatas error,due to the parameter 'restRequest' is null or the real request is null.");
			throw new IncrException("getIncrDatas error,due to the parameter 'restRequest' is null or the real request is null.");
		}
		if (restRequest.getRequest().getLastId() == null) {
			logger.error("getIncrDatas error,due to 'lastId' which belongs to the parameter 'restRequest' is null.");
			throw new IncrException("getIncrDatas error,due to 'lastId' which belongs to the parameter 'restRequest' is null.");
		}
		RestResponse<IncrResponse<T>> restResponse = new RestResponse<IncrResponse<T>>(restRequest.getGuid());
		IncrResponse<T> incrResponse = createIncrResponse();

		// 构建参数
		long lastId = restRequest.getRequest().getLastId().longValue();
		int maxRecordCount = restRequest.getRequest().getCount();
		maxRecordCount = (maxRecordCount == 0 || maxRecordCount > 1000) ? 1000 : maxRecordCount;
		logger.info("getIncrDatas params,lastId = " + lastId + ",maxRecordCount = " + maxRecordCount + ",guid = " + restRequest.getGuid());

		// 获取增量数据
		List<T> incrDatas = getIncrDatas(lastId, maxRecordCount, restRequest.getProxyInfo());
		logger.info("getIncrDatas result,result count = " + (incrDatas == null ? 0 : incrDatas.size()) + ",guid = " + restRequest.getGuid());
		incrResponse.setList(incrDatas);
		restResponse.setResult(incrResponse);
		return restResponse;
	}

	/** 
	 * 创建增量响应模型
	 *
	 * @return
	 */
	protected abstract IncrResponse<T> createIncrResponse();

	/** 
	 * 获取增量数据
	 *
	 * @param params
	 * @return
	 */
	protected abstract List<T> getIncrDatas(long lastId, int maxRecordCount, ProxyAccount proxyAccount);

	/** 
	 * 获取最后的更新ID
	 *
	 * @param restRequest
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrService#getLastId(com.elong.nb.common.model.RestRequest)    
	 */
	@Override
	public RestResponse<IncrIdResponse> getLastId(RestRequest<IncrIdRequest> restRequest) {
		// 基本检查
		if (restRequest == null || restRequest.getRequest() == null) {
			logger.error("getLastId error,due to the parameter 'restRequest' is null or the real request is null.");
			throw new IncrException("getLastId error,due to the parameter 'restRequest' is null or the real request is null.");
		}
		Date lastTime = restRequest.getRequest().getLastTime();
		if (lastTime == null) {
			logger.error("getLastId error,due to 'lastTime' which belongs to parameter 'restRequest' is null.");
			throw new IncrException("getLastId error,due to 'lastTime' which belongs to parameter 'restRequest' is null.");
		}
		EnumIncrType incrType = restRequest.getRequest().getIncrType();
		if (incrType == null) {
			logger.error("getLastId error,due to 'incrType' which belongs to parameter 'restRequest' is null.");
			throw new IncrException("getLastId error,due to 'incrType' which belongs to parameter 'restRequest' is null.");
		}

		// 构建参数
		logger.info("getLastId params,lastTime = " + lastTime + ",incrType = " + incrType.getValue() + ",guid = " + restRequest.getGuid());
		RestResponse<IncrIdResponse> restResponse = new RestResponse<IncrIdResponse>(restRequest.getGuid());
		IncrIdResponse incrIdResponse = new IncrIdResponse();

		// 获取数据
		BigInteger lastId = getLastId(lastTime, restRequest.getProxyInfo());
		incrIdResponse.setLastId(lastId == null ? IncrConst.bigIntegerNegativeOne : lastId);
		logger.info("getLastId result,lastId = " + incrIdResponse.getLastId() + ",guid = " + restRequest.getGuid());
		restResponse.setResult(incrIdResponse);
		return restResponse;
	}

	/** 
	 * 获取最后的更新ID 
	 *
	 * @param lastTime
	 * @param proxyAccount
	 * @return
	 */
	protected abstract BigInteger getLastId(Date lastTime, ProxyAccount proxyAccount);

}
