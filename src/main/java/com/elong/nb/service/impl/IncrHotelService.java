/**   
 * @(#)IncrHotelService.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrHotelResponse;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.bean.IncrHotel;
import com.elong.nb.service.IIncrHotelService;
import com.elong.nb.submeter.service.ISubmeterService;
import com.elong.nb.util.IncrConst;

/**
 * 酒店增量接口实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午2:24:41   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class IncrHotelService extends AbstractIncrService<IncrHotel> implements IIncrHotelService {

	private static final Log logger = LogFactory.getLog(IncrHotelService.class);

	@Resource(name = "incrHotelSubmeterService")
	private ISubmeterService<IncrHotel> incrHotelSubmeterService;

	/** 
	 * 获取最大IncrID的酒店增量
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrHotelService#getLastIncrHotel()    
	 */
	@Override
	public IncrHotel getLastIncrHotel() {
		try {
			return incrHotelSubmeterService.getLastIncrData();
		} catch (Exception e) {
			logger.error("getLastIncrHotel error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastTime的最早发生变化的酒店增量
	 *
	 * @param lastTime
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrHotelService#getOneIncrHotel(java.util.Date)    
	 */
	@Override
	public IncrHotel getOneIncrHotel(Date lastTime) {
		if (lastTime == null) {
			logger.error("getOneIncrHotel error,due to the parameter 'lastTime' is null.");
			throw new IncrException("getOneIncrHotel error,due to the parameter 'lastTime' is null.");
		}
		try {
			return incrHotelSubmeterService.getOneIncrData(lastTime);
		} catch (Exception e) {
			logger.error("getOneIncrHotel error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取大于指定lastId的maxRecordCount条酒店增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrHotelService#getIncrHotels(long, int)    
	 */
	@Override
	public List<IncrHotel> getIncrHotels(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		if (maxRecordCount == 0) {
			logger.error("getIncrHotels error,due to the parameter 'maxRecordCount' is 0.");
			throw new IncrException("getIncrHotels error,due to the parameter 'maxRecordCount' is 0.");
		}
		try {
			List<IncrHotel> incrHotelList = incrHotelSubmeterService.getIncrDataList(lastId, maxRecordCount, proxyAccount);
			if (incrHotelList == null || incrHotelList.size() == 0)
				return Collections.emptyList();
			for (IncrHotel incrHotel : incrHotelList) {
				if (incrHotel == null || StringUtils.isEmpty(incrHotel.getHotelID()))
					continue;
				incrHotel.setHotelID(StringUtils.trim(incrHotel.getHotelID()));
			}
			return incrHotelList;
		} catch (Exception e) {
			logger.error("getIncrHotels error,due to " + e.getMessage(), e);
			throw new IllegalStateException(e.getMessage());
		}
	}

	/** 
	 * 获取酒店增量数据
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getIncrDatas(long, int, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	protected List<IncrHotel> getIncrDatas(long lastId, int maxRecordCount, ProxyAccount proxyAccount) {
		return getIncrHotels(lastId, maxRecordCount, proxyAccount);
	}

	/** 
	 * 获取酒店增量最后的更新ID
	 *
	 * @param lastTime
	 * @param proxyAccount
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#getLastId(java.util.Date, com.elong.nb.common.model.ProxyAccount)    
	 */
	@Override
	protected BigInteger getLastId(Date lastTime, ProxyAccount proxyAccount) {
		IncrHotel incrHotel = getOneIncrHotel(lastTime);
		if (incrHotel == null) {
			incrHotel = getLastIncrHotel();
		}
		return incrHotel == null ? IncrConst.bigIntegerNegativeOne : BigInteger.valueOf(incrHotel.getID());
	}

	/** 
	 * 创建增量响应模型
	 *
	 * @return 
	 *
	 * @see com.elong.nb.service.impl.AbstractIncrService#createIncrResponse()    
	 */
	@Override
	protected IncrResponse<IncrHotel> createIncrResponse() {
		return new IncrHotelResponse();
	}

}
