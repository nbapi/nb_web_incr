/**   
 * @(#)IncrStateResponse.java	2016年8月23日	下午2:19:17	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 状态增量响应模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月23日 下午2:19:17   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrStateResponse implements IncrResponse<IncrState> {

	@SerializedName(value = "States", alternate = "states")
	private List<IncrState> states;

	@Override
	public List<IncrState> getList() {
		return states;
	}

	@Override
	public void setList(List<IncrState> list) {
		this.states = list;
	}

}
