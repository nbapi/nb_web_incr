/**   
 * @(#)SubmeterTableCalculate.java	2017年4月26日	下午5:13:49	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.elong.nb.model.RowRangeInfo;
import com.elong.nb.model.ShardingInfo;
import com.elong.nb.model.enums.SubmeterConst;
import com.elong.nb.util.ShardingUtils;

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

	private static final BigDecimal DENOMINATOR = new BigDecimal(10);// TODO

	/** 
	 * 获取id所在段末尾id
	 *
	 * @param id
	 * @return
	 */
	public long getSegmentEndId(long id) {
		BigDecimal numerator = new BigDecimal(id);
		BigDecimal value = numerator.divide(DENOMINATOR, 3, RoundingMode.CEILING);
		return (long) Math.ceil(value.doubleValue()) * 10;// TODO
	}

	/** 
	 * 获取id所在段起始id 
	 *
	 * @param id
	 * @return
	 */
	public long getSegmentBeginId(long id) {
		BigDecimal numerator = new BigDecimal(id);
		BigDecimal value = numerator.divide(DENOMINATOR, 3, RoundingMode.CEILING);
		return (long) Math.floor(value.doubleValue()) * 10;// TODO
	}

	/** 
	 * 获取id对应分片数据源 
	 *
	 * @param selectedShardId
	 * @return
	 */
	public String getSelectedDataSource(long id) {
		int selectedShardId = getSelectedShardId(id);
		// 获取命中分片信息
		Map<Integer, ShardingInfo> shardInfoMap = ShardingUtils.SHARDINFO_MAP;
		ShardingInfo shardingInfo = shardInfoMap.get(selectedShardId);
		if (shardingInfo == null) {
			throw new IllegalStateException("selectedShardId = " + selectedShardId + ",getSelectedShard is null!");
		}
		String dataSource = shardingInfo.getDataSource();
		if (StringUtils.isEmpty(dataSource)) {
			throw new IllegalStateException("selectedShardId = " + selectedShardId + ",getSelectedDataSource is null!");
		}
		return dataSource;
	}

	/** 
	 * 获取指定id所在行范围的分片数量 
	 *
	 * @param id
	 * @return
	 */
	private int getShardCount(long id) {
		// 确定id属于的行范围
		RowRangeInfo rowRangeInfo = null;
		List<RowRangeInfo> rowRangeInfoList = ShardingUtils.ROWRANGEINFO_LIST;
		for (RowRangeInfo row : rowRangeInfoList) {
			if (id < row.getBeginId().longValue() || id > row.getEndId().longValue())
				continue;
			rowRangeInfo = row;
			break;
		}
		if (rowRangeInfo == null) {
			throw new IllegalStateException("id = " + id + ",getRowRangeInfo is null!");
		}
		// 计算所属分片编号
		String shardIdStr = rowRangeInfo.getShardIds();
		if (StringUtils.isEmpty(shardIdStr)) {
			throw new IllegalStateException("id = " + id + ",shardIds is null or empty!");
		}
		String[] shardIds = StringUtils.split(shardIdStr, ",", -1);
		return shardIds.length;
	}

	/** 
	 * 获取id对应的分片编号
	 *
	 * @param id
	 * @return
	 */
	public int getSelectedShardId(long id) {
		int shardCount = getShardCount(id);
		int selectedShardId = (int) ((id - 1) / 10) % shardCount + 1;
		return selectedShardId;
	}

	/** 
	 * 获取id对应的分表
	 *
	 * @param id
	 * @return
	 */
	public String getSelectedSubTable(String tablePrefix, long id) {
		int shardCount = getShardCount(id);
		int submeterRowCount = SubmeterConst.PER_SUBMETER_ROW_COUNT;
		submeterRowCount = 100;// TODO
		return tablePrefix + "_" + (int) Math.ceil(id * 1.0 / (shardCount * submeterRowCount));
	}

}
