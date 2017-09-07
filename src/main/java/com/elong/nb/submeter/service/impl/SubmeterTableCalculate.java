/**   
 * @(#)SubmeterTableCalculate.java	2017年4月26日	下午5:13:49	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.elong.nb.common.util.CommonsUtil;
import com.elong.nb.model.enums.SubmeterConst;

/**
 * 计算获取表名
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月26日 下午5:13:49   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Repository
public class SubmeterTableCalculate {

	/** 
	 * 获取末尾10张or实际张非空分表
	 *
	 * @param lastId
	 * @param maxId
	 * @param tablePrefix
	 * @param isAsc 是否升序
	 * @return
	 */
	public List<String> querySubTableNameList(long lastId, long maxId, String tablePrefix, boolean isAsc) {
		List<String> subTableNameList = new ArrayList<String>();
		long beginTableNumber = getSelectedSubTableNumber(lastId);
		long lastTableNumber = getSelectedSubTableNumber(maxId);
		long cycleCount = lastTableNumber - beginTableNumber + 1;
		cycleCount = cycleCount > 10 ? 10 : cycleCount;
		for (int i = 0; i < cycleCount; i++) {
			String subTableName = tablePrefix + "_" + (lastTableNumber--);
			subTableNameList.add(subTableName);
		}
		if (isAsc) {
			Collections.reverse(subTableNameList);
		}
		return subTableNameList;
	}

	/** 
	 * 获取id对应分表序号 
	 *
	 * @param lastId
	 * @return
	 */
	public long getSelectedSubTableNumber(long id) {
		int submeterRowCount = SubmeterConst.PER_SUBMETER_ROW_COUNT;
		String configValue = CommonsUtil.CONFIG_PROVIDAR.getProperty("ImpulseSenderFromRedisTest");
		if (StringUtils.isNotEmpty(configValue)) {
			submeterRowCount = 100;
		}
		return (int) Math.ceil(id * 1.0 / submeterRowCount);
	}

}
