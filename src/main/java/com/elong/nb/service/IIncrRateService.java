/**   
 * @(#)IIncrRateService.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service;

import java.util.Date;
import java.util.List;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.model.IncrRate;

/**
 * 房价增量接口
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午1:33:56   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IIncrRateService {

	/** 
	 * 价格小数点处理
	 *
	 * @param proxyInfo
	 * @param beforeRates
	 * @return
	 */
	public List<IncrRate> demicalHandler(ProxyAccount proxyInfo, List<IncrRate> beforeRates);

	/** 
	 * 获取最大IncrID的房价增量
	 *
	 * @return
	 */
	public IncrRate getLastIncrRate();

	/** 
	 * 获取大于指定lastTime的最早发生变化的房价增量
	 *
	 * @param lastTime
	 * @return
	 */
	public IncrRate getOneIncrRate(Date lastTime);

	/** 
	 * 获取大于指定lastId的maxRecordCount条房价增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @return
	 */
	public List<IncrRate> getIncrRates(long lastId, int maxRecordCount);

}
