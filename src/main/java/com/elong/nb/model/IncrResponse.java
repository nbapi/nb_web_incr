/**   
 * @(#)IncrResponse.java	2016年8月22日	下午4:42:34	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.List;

/**
 * 增量响应模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月22日 下午4:42:34   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IncrResponse<T> {

	/**   
	 * 得到list的值   
	 *   
	 * @return list的值
	 */
	public List<T> getList();

	/**
	 * 设置list的值
	 *   
	 * @param list 被设置的值
	 */
	public void setList(List<T> list);

}
