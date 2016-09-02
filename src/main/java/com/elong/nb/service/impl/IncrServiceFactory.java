/**   
 * @(#)IncrServiceFactory.java	2016年8月22日	下午5:55:58	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.elong.nb.model.enums.EnumIncrType;
import com.elong.nb.service.IIncrService;

/**
 * 增量服务工厂
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月22日 下午5:55:58   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Component
public class IncrServiceFactory implements InitializingBean {

	@Resource
	private IncrInventoryService incrInventoryService;

	@Resource
	private IncrHotelService incrHotelService;

	@Resource
	private IncrOrderService incrOrderService;

	@Resource
	private IncrRateService incrRateService;

	@Resource
	private IncrStateService incrStateService;

	private Map<EnumIncrType, IIncrService<?>> map = new HashMap<EnumIncrType, IIncrService<?>>();

	/** 
	 * 根据增量类型获取增量服务
	 *
	 * @param incrType
	 * @return
	 */
	public IIncrService<?> getIIncrService(EnumIncrType incrType) {
		if (incrType == null) {
			return null;
		}
		if (!map.containsKey(incrType)) {
			return null;
		}
		return map.get(incrType);
	}

	/** 
	 * 根据请求路径获取增量服务
	 *
	 * @param getIncrPath
	 * @return
	 */
	public IIncrService<?> getIIncrService(String getIncrPath) {
		if (StringUtils.isEmpty(getIncrPath)) {
			return null;
		}
		if (StringUtils.equalsIgnoreCase("getIncrState", getIncrPath)) {
			return map.get(EnumIncrType.State);
		} else if (StringUtils.equalsIgnoreCase("getIncrRates", getIncrPath)) {
			return map.get(EnumIncrType.Rate);
		} else if (StringUtils.equalsIgnoreCase("getIncrOrders", getIncrPath)) {
			return map.get(EnumIncrType.Order);
		} else if (StringUtils.equalsIgnoreCase("getIncrHotel", getIncrPath)) {
			return map.get(EnumIncrType.Data);
		} else if (StringUtils.equalsIgnoreCase("getIncrInventories", getIncrPath)) {
			return map.get(EnumIncrType.Inventory);
		}
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		map.put(EnumIncrType.Inventory, incrInventoryService);
		map.put(EnumIncrType.Data, incrHotelService);
		map.put(EnumIncrType.Order, incrOrderService);
		map.put(EnumIncrType.Rate, incrRateService);
		map.put(EnumIncrType.State, incrStateService);
	}

}
