/**   
 * @(#)IncrRateService.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.common.util.SafeConvertUtils;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrRateResponse;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.bean.IncrRate;
import com.elong.nb.rule.agent.SettlementPriceRuleCommon;
import com.elong.nb.rule.agent.enums.EnumSystem;
import com.elong.nb.rule.agent.model.RateWithRule;
import com.elong.nb.service.IIncrRateService;
import com.elong.nb.submeter.service.impl.IncrRateSubmeterService;
import com.elong.nb.util.IncrConst;

/**
 * 房价增量接口实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午2:03:01   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class IncrRateService extends AbstractIncrService<IncrRate> implements IIncrRateService {

	private static final Log logger = LogFactory.getLog(IncrRateService.class);

	@Resource(name = "incrRateSubmeterService")
	private IncrRateSubmeterService incrRateSubmeterService;

	/** 
	 * 获取最大IncrID的房价增量
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrRateService#getLastIncrRate()    
	 */
	@Override
	public IncrRate getLastIncrRate() {
		try {
			return incrRateSubmeterService.getLastIncrData();
		} catch (Exception e) {
			logger.error("getLastIncrRate error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastTime的最早发生变化的房价增量
	 *
	 * @param lastTime
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrRateService#getOneIncrRate(java.util.Date)    
	 */
	@Override
	public IncrRate getOneIncrRate(Date lastTime) {
		if (lastTime == null) {
			logger.error("getOneIncrRate error,due to the parameter 'lastTime' is null.");
			throw new IncrException("getOneIncrRate error,due to the parameter 'lastTime' is null.");
		}
		try {
			return incrRateSubmeterService.getOneIncrData(lastTime);
		} catch (Exception e) {
			logger.error("getOneIncrRate error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastId的maxRecordCount条房价增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrRateService#getIncrRates(long, int)    
	 */
	@Override
	public List<IncrRate> getIncrRates(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		if (maxRecordCount == 0) {
			logger.error("getIncrRates error,due to the parameter 'maxRecordCount' is 0.");
			throw new IncrException("getIncrRates error,due to the parameter 'maxRecordCount' is 0.");
		}
		try {
			return incrRateSubmeterService.getIncrDataList(lastId, maxRecordCount, proxyAccount);
		} catch (Exception e) {
			logger.error("getIncrRates error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取价格增量数据 
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getIncrDatas(long, int, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	protected List<IncrRate> getIncrDatas(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		List<IncrRate> beforeRates = getIncrRates(lastId, maxRecordCount, proxyAccount);
		logCollectService.writeIncrRateLog(proxyAccount, beforeRates);
		// 价格小数点
		return demicalHandler(proxyAccount, beforeRates);
	}

	/** 
	 * 价格小数点处理
	 * 异常不捕获
	 *
	 * @param proxyInfo
	 * @param beforeRates
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrRateService#demicalHandler(com.elong.nb.common.model.ProxyAccount, java.util.List)    
	 */
	@Override
	public List<IncrRate> demicalHandler(ProxyAccount proxyInfo, List<IncrRate> beforeRates) {
		if (beforeRates == null || beforeRates.size() == 0) {
			return Collections.emptyList();
		}
		List<String> hotelCodeList = new ArrayList<String>();
		for (IncrRate item : beforeRates) {
			if (item == null || StringUtils.isEmpty(item.getHotelCode()))
				continue;
			hotelCodeList.add(item.getHotelCode());
		}
		SettlementPriceRuleCommon settlementPriceRuleCommon = new SettlementPriceRuleCommon(proxyInfo, hotelCodeList, EnumSystem.Incr);
		// 价格小数点
		List<IncrRate> afterRates = new ArrayList<IncrRate>();
		for (IncrRate item : beforeRates) {
			if (item == null)
				continue;
			if (proxyInfo.getLowestProfitPercent() > 0) {
				double profitPercent = (item.getMember() - item.getWeekendCost()) / item.getMember();
				if (profitPercent * 100 < proxyInfo.getLowestProfitPercent()) {
					continue;
				}
			}
			item.setMember(SafeConvertUtils.toIntegerPrice(item.getMember(), proxyInfo.getIntegerPriceType()));
			item.setWeekend(SafeConvertUtils.toIntegerPrice(item.getWeekend(), proxyInfo.getIntegerPriceType()));

			List<RateWithRule> memberCostRuleList = settlementPriceRuleCommon.getSettlementPrice(item.getMemberCost(), item.getMember(),
					item.getHotelCode(), item.getStartDate(), item.getEndDate());
			List<RateWithRule> weekCostRuleList = settlementPriceRuleCommon.getSettlementPrice(item.getWeekendCost(), item.getWeekend(),
					item.getHotelCode(), item.getStartDate(), item.getEndDate());
			if (memberCostRuleList != null && memberCostRuleList.size() > 0) {
				try {
					for (int idx = 0; idx < memberCostRuleList.size(); idx++) {
						RateWithRule memberCostRule = memberCostRuleList.get(idx);
						IncrRate incrRate = (IncrRate) item.clone();
						incrRate.setStartDate(memberCostRule.getStartDate());
						incrRate.setEndDate(memberCostRule.getEndDate());
						incrRate.setMemberCost(memberCostRule.getCost());
						RateWithRule weekCostRule = weekCostRuleList.get(idx);
						incrRate.setWeekendCost(weekCostRule.getCost());
						afterRates.add(incrRate);
					}
				} catch (CloneNotSupportedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return afterRates;
	}

	/** 
	 * 创建增量响应模型
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#createIncrResponse()    
	 */
	@Override
	protected IncrResponse<IncrRate> createIncrResponse() {
		return new IncrRateResponse();
	}

	/** 
	 * 获取房价增量最后的更新ID
	 *
	 * @param lastTime
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getLastId(java.util.Date, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	protected BigInteger getLastId(Date lastTime, ProxyAccount proxyAccount) {
		IncrRate incrRate = getOneIncrRate(lastTime);
		if (incrRate == null) {
			incrRate = getLastIncrRate();
		}
		return incrRate == null ? IncrConst.bigIntegerNegativeOne : BigInteger.valueOf(incrRate.getID());
	}

}
