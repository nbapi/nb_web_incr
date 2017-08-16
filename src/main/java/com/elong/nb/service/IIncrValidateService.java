/**   
 * @(#)IIncrValidateService.java	2016年8月19日	上午10:26:28	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service;

import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.common.model.RestRequest;
import com.elong.nb.model.IncrIdRequest;
import com.elong.nb.model.IncrRequest;

/**
 * 增量校验接口
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 上午10:26:28   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IIncrValidateService {

	/** 
	 * 增量请求参数校验
	 *
	 * @param restRequest
	 * @return
	 */
	public String validateIncrRequest(RestRequest<IncrRequest> restRequest, ProxyAccount proxyAccount);

	/** 
	 * 增量请求LastId校验
	 *
	 * @param restRequest
	 * @return
	 */
	public String validateIncrIdRequest(RestRequest<IncrIdRequest> restRequest, ProxyAccount proxyAccount);

}
