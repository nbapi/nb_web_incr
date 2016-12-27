/**   
 * @(#)IIncrService.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service;

import com.elong.nb.common.model.RestRequest;
import com.elong.nb.common.model.RestResponse;
import com.elong.nb.model.IncrIdRequest;
import com.elong.nb.model.IncrIdResponse;
import com.elong.nb.model.IncrRequest;
import com.elong.nb.model.IncrResponse;

/**
 * 增量汇总接口
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午3:46:38   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IIncrService<T> {

	/** 
	 * 获取增量数据
	 *
	 * @param restRequest
	 * @return
	 */
	public RestResponse<IncrResponse<T>> getIncrDatas(RestRequest<IncrRequest> restRequest);

	/** 
	 * 获取最后的更新ID
	 *
	 * @param restRequest
	 * @return
	 */
	public RestResponse<IncrIdResponse> getLastId(RestRequest<IncrIdRequest> restRequest);

}
