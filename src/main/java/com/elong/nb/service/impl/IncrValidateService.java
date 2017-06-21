/**   
 * @(#)IncrValidateService.java	2016年8月19日	上午10:29:19	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service.impl;

import org.springframework.stereotype.Service;

import com.elong.nb.common.model.ErrorCode;
import com.elong.nb.common.model.RestRequest;
import com.elong.nb.common.util.ValidateUtil;
import com.elong.nb.model.IncrIdRequest;
import com.elong.nb.model.IncrRequest;
import com.elong.nb.service.IIncrValidateService;

/**
 * 增量校验接口实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 上午10:29:19   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class IncrValidateService implements IIncrValidateService {

	/** 
	 * 增量请求参数校验
	 *
	 * @param restRequest
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrValidateService#validateIncrRequest(com.elong.nb.common.model.RestRequest)    
	 */
	@Override
	public String validateIncrRequest(RestRequest<IncrRequest> restRequest) {
		StringBuffer sb = new StringBuffer();
		sb.append(ValidateUtil.validateRestRequest(restRequest));
		IncrRequest incrRequest = restRequest.getRequest();
		if (incrRequest == null)
			return sb.toString();
		if (incrRequest.getLastId() == null) {
			sb.append(ErrorCode.Incr_LastIdRequired);
		}
		return sb.toString();
	}

	/** 
	 * 增量请求LastId校验
	 *
	 * @param restRequest
	 * @return 
	 *
	 * @see com.elong.nb.service.IIncrValidateService#validateIncrIdRequest(com.elong.nb.common.model.RestRequest)    
	 */
	@Override
	public String validateIncrIdRequest(RestRequest<IncrIdRequest> restRequest) {
		StringBuffer sb = new StringBuffer();
		sb.append(ValidateUtil.validateRestRequest(restRequest));
		IncrIdRequest incrIdRequest = restRequest.getRequest();
		if (incrIdRequest == null)
			return sb.toString();
		if (incrIdRequest.getIncrType() == null) {
			sb.append(ErrorCode.Incr_IncrTypeRequired);
		}
		if (incrIdRequest.getLastTime() == null) {
			sb.append(ErrorCode.Incr_LastTimeRequired);
		}
		return sb.toString();
	}

}
