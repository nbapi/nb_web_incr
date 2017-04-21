/**   
 * @(#)IncrHotelSubmeterService.java	2017年4月21日	下午5:17:37	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.elong.nb.dao.IncrHotelDao;
import com.elong.nb.model.bean.IncrHotel;

/**
 * IncrHotel分表实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月21日 下午5:17:37   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service(value = "incrHotelSubmeterService")
public class IncrHotelSubmeterService extends AbstractSubmeterService<IncrHotel> {

	@Resource
	private IncrHotelDao incrHotelDao;

	@Override
	public String getTablePrefix() {
		return "IncrHotel";
	}

	@Override
	protected IncrHotel getOneIncrData(String subTableName, Date lastTime) {
		return incrHotelDao.getOneIncrHotel(subTableName, lastTime);
	}

	@Override
	protected IncrHotel getLastIncrData(String subTableName) {
		return incrHotelDao.getLastIncrHotel(subTableName);
	}

	@Override
	protected List<IncrHotel> getIncrDataList(String subTableName, Map<String, Object> params) {
		return incrHotelDao.getIncrHotels(subTableName, params);
	}

}