/**   
 * @(#)IIncrHotelService.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service;

import java.util.Date;
import java.util.List;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.model.bean.IncrHotel;

/**
 * 酒店增量接口
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午2:22:43   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IIncrHotelService {

	/** 
	 * 获取最大IncrID的酒店增量
	 *
	 * @return
	 */
	public IncrHotel getLastIncrHotel();

	/** 
	 * 获取大于指定lastTime的最早发生变化的酒店增量
	 *
	 * @param lastTime
	 * @return
	 */
	public IncrHotel getOneIncrHotel(Date lastTime);

	/** 
	 * 获取大于指定lastId的maxRecordCount条酒店增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @return
	 */
	public List<IncrHotel> getIncrHotels(long lastId, int maxRecordCount,ProxyAccount proxyAccount);

}
