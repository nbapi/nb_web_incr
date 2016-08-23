/**   
 * @(#)IncrInventoryResponse.java	2016年8月23日	下午2:13:02	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * 库存增量响应模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月23日 下午2:13:02   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrInventoryResponse implements IncrResponse<IncrInventory> {

	@SerializedName(value = "Inventories", alternate = "inventories")
	private List<IncrInventory> inventories;

	@Override
	public List<IncrInventory> getList() {
		return inventories;
	}

	@Override
	public void setList(List<IncrInventory> list) {
		this.inventories = list;
	}

}
