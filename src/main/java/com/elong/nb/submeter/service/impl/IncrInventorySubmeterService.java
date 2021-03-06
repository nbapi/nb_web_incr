/**   
 * @(#)IncrInventorySubmeterService.java	2017年4月21日	下午5:15:29	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.dao.IncrInventoryDao;
import com.elong.nb.model.bean.IncrInventory;

/**
 * IncrInventory分表实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月21日 下午5:15:29   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service(value = "incrInventorySubmeterService")
public class IncrInventorySubmeterService extends AbstractSubmeterService<IncrInventory> {

	@Resource
	private IncrInventoryDao incrInventoryDao;

	@Override
	public String getTablePrefix() {
		return "IncrInventory";
	}

	@Override
	protected IncrInventory getOneIncrData(String dataSource, String subTableName, Map<String, Object> params) {
		return incrInventoryDao.getOneIncrInventory(dataSource, subTableName, params);
	}

	@Override
	protected IncrInventory getLastIncrData(String dataSource, String subTableName, Map<String, Object> params) {
		return incrInventoryDao.getLastIncrInventory(dataSource, subTableName, params);
	}

	@Override
	protected List<IncrInventory> getIncrDataList(String dataSource, String subTableName, Map<String, Object> params,
			ProxyAccount proxyAccount) {
//		params.put("SellChannel", proxyAccount.getSellChannel().getValue());TODO 临时注释掉
//		if (proxyAccount.isIsOnlyStraight()) {
//			params.put("IsStraint", 1);// 1为直签，2为非直签，0为未知
//		}
		return incrInventoryDao.getIncrInventories(dataSource, subTableName, params);
	}

}
