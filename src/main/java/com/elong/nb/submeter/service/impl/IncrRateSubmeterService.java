/**   
 * @(#)IncrRateSubmeterService.java	2017年8月24日	上午10:42:49	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.common.model.EnumSellChannel;
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.dao.IncrRateDao;
import com.elong.nb.model.bean.IncrRate;

/**
 * IncrRate分表实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年8月24日 上午10:42:49   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service(value = "incrRateSubmeterService")
public class IncrRateSubmeterService extends AbstractSubmeterService<IncrRate> {

	@Resource
	private IncrRateDao incrRateDao;

	@Override
	public String getTablePrefix() {
		return "IncrRate";
	}

	@Override
	protected IncrRate getOneIncrData(String subTableName, Date lastTime) {
		return incrRateDao.getOneIncrRate(subTableName, lastTime);
	}

	@Override
	protected IncrRate getLastIncrData(String subTableName) {
		return incrRateDao.getLastIncrRate(subTableName);
	}

	@Override
	protected List<IncrRate> getIncrDataList(String subTableName, Map<String, Object> params, ProxyAccount proxyAccount) {
		if (EnumSellChannel.B == proxyAccount.getSellChannel()) {
			params.put("Channel", 0);
		}
		return incrRateDao.getIncrRates(subTableName, params);
	}

}
