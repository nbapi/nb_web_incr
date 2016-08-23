/**   
 * @(#)IncrRateResponse.java	2016年8月23日	下午2:18:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 房价增量响应模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月23日 下午2:18:27   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrRateResponse implements IncrResponse<IncrRate> {

	@SerializedName(value = "Rates", alternate = "rates")
	private List<IncrRate> rates;

	@Override
	public List<IncrRate> getList() {
		return rates;
	}

	@Override
	public void setList(List<IncrRate> list) {
		this.rates = list;
	}

}
