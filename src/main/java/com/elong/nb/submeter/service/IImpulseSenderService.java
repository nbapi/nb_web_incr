/**   
 * @(#)IImpulseSenderService.java	2017年4月18日	上午11:24:09	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service;

/**
 * 发号器接口
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月18日 上午11:24:09   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IImpulseSenderService {
	
	/** 
	 * 当前id
	 *
	 * @param key
	 * @return
	 */
	public long curId(String key);

}
