/**   
 * @(#)SubmeterTableDao.java	2017年4月11日	上午11:33:49	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.elong.nb.db.DataSource;

/**
 * 检查、创建、查询分表
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月11日 上午11:33:49   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@DataSource("dataSource_nbsubmeter_write")
public interface SubmeterTableDao {

	/** 
	 * 查询指定前缀的非空分表
	 *
	 * @param tablePrefix
	 * @param isDesc
	 * @return
	 */
	public List<String> queryNoEmptySubTableList(@Param("tablePrefix") String tablePrefix, @Param("isDesc") boolean isDesc);

	/** 
	 * 查询指定前缀的分表
	 *
	 * @param tablePrefix
	 * @param maxRecordCount
	 * @return
	 */
	public List<Map<String,Object>> queryAllSubTableList(@Param("tablePrefix") String tablePrefix, @Param("maxRecordCount") int maxRecordCount);

}
