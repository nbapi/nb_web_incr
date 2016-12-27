/**   
 * @(#)INoticeService.java	2016年8月9日	下午6:35:59	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.service;

/**
 * 通知接口
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月9日 下午6:35:59   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */
public interface INoticeService {

	/** 
	 * 发送通知信息
	 * 
	 * @param title
	 * @param content
	 */
	public void sendMessage(String title, String content);

}
