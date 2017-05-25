/**   
 * @(#)IncrRateDao.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.dao;

import java.util.List;
import java.util.Map;

import com.elong.nb.db.DataSource;
import com.elong.nb.model.bean.IncrRate;

/**
 * 房价增量数据组件
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午2:06:17   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@DataSource("dataSource_nbhotelincr_read")
public interface IncrRateDao {

	/** 
	 * 获取最大IncrID的房价增量
	 *
	 * @return
	 */
	public IncrRate getLastIncrRate();

	/** 
	 * 获取大于指定lastTime的最早发生变化的房价增量
	 *
	 * @param paramMap
	 * @return
	 */
	public IncrRate getOneIncrRate(Map<String, Object> paramMap);

	/** 
	 * 获取大于指定lastId的maxRecordCount条房价增量
	 *
	 * @param paramMap
	 * @return
	 */
	public List<IncrRate> getIncrRates(Map<String, Object> paramMap);

}
