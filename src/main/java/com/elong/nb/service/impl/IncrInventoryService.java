/**   
 * @(#)IncrInventoryService.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.elong.nb.dao.IncrInventoryDao;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrInventoryResponse;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.InventoryBlackListRuleRealRequest;
import com.elong.nb.model.InventoryBlackListRuleRealResponse;
import com.elong.nb.model.InventoryRuleHitCheckRealRequest;
import com.elong.nb.model.InventoryRuleHitCheckRealResponse;
import com.elong.nb.model.InventoryRuleHitCheckSoaRequest;
import com.elong.nb.model.InventoryRuleHitCheckSoaResponse;
import com.elong.nb.model.InventoryRuleSoaRequst;
import com.elong.nb.model.InventoryRuleSoaResponse;
import com.elong.nb.model.RuleInventoryRequest;
import com.elong.nb.model.RuleInventoryResponse;
import com.elong.nb.model.bean.IncrInventory;
import com.elong.nb.service.IIncrInventoryService;
import com.elong.nb.util.HttpClientUtils;
import com.elong.nb.util.IncrConst;
import com.elong.springmvc_enhance.utilities.PropertiesHelper;

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
	 * @see com.elong.nb.service.IIncrInventoryService#getIncrInventories(long,
	 *      int)
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
		if (params == null || !params.containsKey("lastId") || !params.containsKey("maxRecordCount") || !params.containsKey("orderFrom")) {
			throw new IncrException(
					"the map 'params' is requeried,and the map 'params' must contain the key ['lastId' or 'maxRecordCount' or 'orderFrom'].");
		}

		Object lastIdObj = params.get("lastId");
		Object maxRecordCountObj = params.get("maxRecordCount");
		if (lastIdObj == null || maxRecordCountObj == null) {
			throw new IncrException("the parameter['lastId' or 'maxRecordCount']  which belongs to the map 'params' must not be null.");
		}
		long lastId = (long) params.get("lastId");
		int maxRecordCount = (int) params.get("maxRecordCount");
		List<IncrInventory> incrInventories = getIncrInventories(lastId, maxRecordCount);

		if (params.containsKey("orderFrom") && params.get("orderFrom") == null) {
			throw new IncrException("the parameter['orderFrom']  which belongs to the map 'params' must not be null.");
		}

		Integer orderFrom = (Integer) params.get("orderFrom");
		// 获取需要进行黑名单处理的数据
		List<IncrInventory> needCheckList = checkBlackListRule(incrInventories, orderFrom);
		// 移除掉需要处理的数据，剩下不需要处理的数据
		incrInventories.removeAll(needCheckList);
		// 进行黑名单处理
		needCheckList = doHandlerBlackListRule(needCheckList, orderFrom);
		// 加入黑名单处理完数据
		incrInventories.addAll(needCheckList);
		return incrInventories;
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

	/** 
	 * 库存黑名单处理
	 *
	 * @param incrInventories
	 * @param orderFrom
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrInventoryService#doHandlerBlackListRule(java.util.List, java.lang.Integer)    
	 */
	@Override
	public List<IncrInventory> doHandlerBlackListRule(List<IncrInventory> incrInventories, Integer orderFrom) {
		if (incrInventories == null || incrInventories.size() == 0)
			return Collections.emptyList();

		Map<String, IncrInventory> incrInventoryMap = new HashMap<String, IncrInventory>();
		List<RuleInventoryRequest> ruleInventoryRequests = new ArrayList<RuleInventoryRequest>();
		for (IncrInventory incrInventory : incrInventories) {
			if (incrInventory == null)
				continue;
			String ruleKey = UUID.randomUUID().toString();
			RuleInventoryRequest ruleInventory = new RuleInventoryRequest();
			ruleInventory.setRuleKey(ruleKey);
			ruleInventory.setAvailableDate(incrInventory.getAvailableDate());
			ruleInventory.setEndDate(incrInventory.getEndDate());
			ruleInventory.setHotelCode(incrInventory.getHotelCode());
			ruleInventory.setHotelID(incrInventory.getHotelID());
			ruleInventory.setOverBooking(incrInventory.getOverBooking());
			ruleInventory.setRoomTypeID(incrInventory.getRoomTypeID());
			ruleInventory.setStartDate(incrInventory.getStartDate());
			ruleInventoryRequests.add(ruleInventory);
			incrInventoryMap.put(ruleKey, incrInventory);
		}
		logger.info("before doHandlerBlackListRule,incrInventories size = " + incrInventories.size());

		// 构建库存黑名单接口参数
		InventoryBlackListRuleRealRequest ruleRequest = new InventoryBlackListRuleRealRequest();
		ruleRequest.setInventorys(ruleInventoryRequests);
		ruleRequest.setNeedInstantConfirm(false);
		ruleRequest.setNightlyRate(false);
		ruleRequest.setOrderFrom(orderFrom);
		InventoryRuleSoaRequst ruleSoaReq = new InventoryRuleSoaRequst();
		ruleSoaReq.setRealRequest(ruleRequest);
		ruleSoaReq.setFrom("getIncrInventories");
		ruleSoaReq.setLogId(UUID.randomUUID().toString());
		String reqData = JSON.toJSONString(ruleSoaReq);

		// 库存黑名单接口调用
		String result = null;
		try {
			String reqUrl = PropertiesHelper.getEnvProperties("Inventory.BlackList.Rule.Url", "config").toString();
			logger.info("doHandlerBlackListRule,httpPost reqUrl = " + reqUrl);
			logger.info("doHandlerBlackListRule,httpPost reqData = " + reqData);
			result = HttpClientUtils.httpPost(reqUrl, reqData, "application/json");
			logger.info("doHandlerBlackListRule,httpPost result = " + result);
		} catch (Exception e) {
			logger.error("doHandlerBlackListRule,httpPost error = " + e.getMessage(), e);
			throw new IllegalStateException("doHandlerBlackListRule,httpPost error = " + e.getMessage());
		}

		InventoryRuleSoaResponse ruleSoaResponse = JSON.parseObject(result, InventoryRuleSoaResponse.class);
		if (ruleSoaResponse == null || StringUtils.equals("-1", ruleSoaResponse.getResponseCode())) {
			throw new IllegalStateException("doHandlerBlackListRule,httpPost error,due to responseCode = "
					+ ruleSoaResponse.getResponseCode());
		}
		List<RuleInventoryResponse> ruleInventoryResponses = null;
		InventoryBlackListRuleRealResponse ruleRealResponse = ruleSoaResponse.getRealResponse();
		if (ruleRealResponse == null || ruleRealResponse.getInventorys() == null || ruleRealResponse.getInventorys().size() == 0) {
			logger.info("doHandlerBlackListRule,has no ruleResponse.");
			ruleInventoryResponses = Collections.emptyList();
		}
		ruleInventoryResponses = ruleRealResponse.getInventorys();

		// 补回增量部分数据
		List<IncrInventory> incrInventoryList = new ArrayList<IncrInventory>();
		if (ruleInventoryResponses != null && ruleInventoryResponses.size() > 0) {
			for (RuleInventoryResponse ruleInventory : ruleInventoryResponses) {
				if (ruleInventory == null || StringUtils.isEmpty(ruleInventory.getRuleKey()))
					continue;

				IncrInventory incrInventory = incrInventoryMap.get(ruleInventory.getRuleKey());
				incrInventory.setEndDate(ruleInventory.getEndDate());
				incrInventory.setOverBooking(ruleInventory.getOverBooking());
				incrInventory.setStartDate(ruleInventory.getStartDate());
				incrInventoryList.add(incrInventory);
			}
		}
		logger.info("after doHandlerBlackListRule,incrInventories size = " + incrInventoryList.size());
		return incrInventoryList;
	}

	/** 
	 * 检查库存黑名单
	 *
	 * @param incrInventories
	 * @param orderFrom
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrInventoryService#checkBlackListRule(java.util.List, java.lang.Integer)    
	 */
	@Override
	public List<IncrInventory> checkBlackListRule(List<IncrInventory> incrInventories, Integer orderFrom) {
		if (incrInventories == null || incrInventories.size() == 0)
			return Collections.emptyList();

		// 构建请求参数
		InventoryRuleHitCheckRealRequest checkRealReq = new InventoryRuleHitCheckRealRequest();
		Map<String, List<String>> hotelMap = new HashMap<String, List<String>>();
		for (IncrInventory incrInventory : incrInventories) {
			if (incrInventory == null || StringUtils.isEmpty(incrInventory.getHotelID())
					|| StringUtils.isEmpty(incrInventory.getHotelCode()))
				continue;
			String hotelID = incrInventory.getHotelID();
			List<String> hotelCodeList = hotelMap.get(hotelID);
			if (hotelCodeList == null) {
				hotelCodeList = new ArrayList<String>();
			}
			hotelCodeList.add(incrInventory.getHotelCode());
			hotelMap.put(hotelID, hotelCodeList);
		}
		checkRealReq.setHotelMap(hotelMap);
		checkRealReq.setNeedInstantConfirm(false);
		checkRealReq.setOrderFrom(orderFrom);
		InventoryRuleHitCheckSoaRequest checkSoaReq = new InventoryRuleHitCheckSoaRequest();
		checkSoaReq.setRealRequest(checkRealReq);
		checkSoaReq.setFrom("getIncrInventories");
		checkSoaReq.setLogId(UUID.randomUUID().toString());
		String checkReqData = JSON.toJSONString(checkSoaReq);

		// 接口调用
		String checkResult = null;
		try {
			String checkHotelCodeUrl = PropertiesHelper.getEnvProperties("Inventory.BlackList.Rule.checkHotelCodeUrl", "config").toString();
			logger.info("checkBlackListRule,httpPost checkHotelCodeUrl = " + checkHotelCodeUrl);
			logger.info("checkBlackListRule,httpPost checkReqData = " + checkReqData);
			checkResult = HttpClientUtils.httpPost(checkHotelCodeUrl, checkReqData, "application/json");
			logger.info("checkBlackListRule,httpPost checkResult = " + checkResult);
		} catch (Exception e) {
			logger.error("checkBlackListRule,httpPost check error = " + e.getMessage(), e);
			throw new IllegalStateException("checkBlackListRule,httpPost check error = " + e.getMessage());
		}

		InventoryRuleHitCheckSoaResponse checkSoaResponse = JSON.parseObject(checkResult, InventoryRuleHitCheckSoaResponse.class);
		if (checkSoaResponse == null || StringUtils.equals("-1", checkSoaResponse.getResponseCode())) {
			throw new IllegalStateException("checkBlackListRule,httpPost check error,due to responseCode = "
					+ checkSoaResponse.getResponseCode());
		}

		List<String> needBlackListRuleCodes = null;
		InventoryRuleHitCheckRealResponse checkRealResponse = checkSoaResponse.getRealResponse();
		if (checkRealResponse == null || checkRealResponse.getNeedBlackListRuleCodes() == null
				|| checkRealResponse.getNeedBlackListRuleCodes().size() == 0) {
			logger.info("checkBlackListRule,has no checkRealResponse.");
			needBlackListRuleCodes = Collections.emptyList();
		}
		needBlackListRuleCodes = checkRealResponse.getNeedBlackListRuleCodes();

		// 需要黑名单检查的数据
		List<IncrInventory> needCheckList = new ArrayList<IncrInventory>();
		if (needBlackListRuleCodes != null && needBlackListRuleCodes.size() > 0) {
			for (IncrInventory incrInventory : incrInventories) {
				if (incrInventory == null || StringUtils.isEmpty(incrInventory.getHotelCode()))
					continue;
				if (!needBlackListRuleCodes.contains(incrInventory.getHotelCode()))
					continue;
				needCheckList.add(incrInventory);
			}
		}
		return needCheckList;
	}

}
