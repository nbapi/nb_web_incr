/**   
 * @(#)IncrInventoryDao.java	2016年8月19日	上午10:41:27	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.elong.nb.db.DataSource;
import com.elong.nb.model.bean.IncrInventory;

/**
 * 库存增量数据组件
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午2:06:51   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public interface IncrInventoryDao {

	/** 
	 * 获取最大IncrID的库存增量
	 *
	 * @param dataSource
	 * @param subTableName
	 * @return
	 */
	public IncrInventory getLastIncrInventory(@DataSource("dataSource") String dataSource, @Param("subTableName") String subTableName,
			@Param("params") Map<String, Object> paramMap);

	/** 
	 * 获取大于指定lastTime的最早发生变化的库存增量
	 *
	 * @param dataSource
	 * @param subTableName
	 * @param lastTime
	 * @return
	 */
	public IncrInventory getOneIncrInventory(@DataSource("dataSource") String dataSource, @Param("subTableName") String subTableName,
			@Param("params") Map<String, Object> paramMap);

	/** 
	 * 获取大于指定lastId的maxRecordCount条库存增量
	 *
	 * @param dataSource
	 * @param subTableName
	 * @param paramMap
	 * @return
	 */
	public List<IncrInventory> getIncrInventories(@DataSource("dataSource") String dataSource, @Param("subTableName") String subTableName,
			@Param("params") Map<String, Object> paramMap);

}
