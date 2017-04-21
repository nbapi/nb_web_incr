/**   
 * @(#)ISubmeterService.java	2017年4月18日	上午11:29:31	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service;

import java.util.Date;
import java.util.List;

/**
 * 分表服务接口
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月18日 上午11:29:31   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface ISubmeterService<T> {

	/** 
	 * 获取分表前缀 
	 *
	 * @return
	 */
	public String getTablePrefix();

	/** 
	 * 获取大于指定lastId的maxRecordCount条增量
	 *
	 * @param lastId
	 * @param maxRecordCount
	 * @return
	 */
	public List<T> getIncrDataList(long lastId, int maxRecordCount);

	/** 
	 * 获取最大IncrID的增量
	 *
	 * @return
	 */
	public T getLastIncrData();

	/** 
	 * 获取大于指定lastTime的最早发生变化的增量
	 *
	 * @param paramMap
	 * @return
	 */
	public T getOneIncrData(Date lastTime);

}
