/**   
 * @(#)RowRangeInfo.java	2017年11月10日	下午4:47:20	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 行范围 对应 存储分片
 * eg：1-10000 存储于分片1、2、3；
 * 10001-20000存储于分片1、2、3、4
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年11月10日 下午4:47:20   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class RowRangeInfo {

	/** 
	 * 编号	
	 *
	 * int RowRangeInfo.java rangeId
	 */
	private int rangeId;

	/** 
	 * 开始id	
	 *
	 * BigInteger RowRangeInfo.java beginId
	 */
	private BigInteger beginId;

	/** 
	 * 截止id	
	 *
	 * BigInteger RowRangeInfo.java endId
	 */
	private BigInteger endId;

	/** 
	 * 存储使用的分片id，多个以逗号分割	
	 *
	 * String RowRangeInfo.java shardIds
	 */
	private String shardIds;

	/**   
	 * 得到rangeId的值   
	 *   
	 * @return rangeId的值
	 */
	public int getRangeId() {
		return rangeId;
	}

	/**
	 * 设置rangeId的值
	 *   
	 * @param rangeId 被设置的值
	 */
	public void setRangeId(int rangeId) {
		this.rangeId = rangeId;
	}

	/**   
	 * 得到beginId的值   
	 *   
	 * @return beginId的值
	 */
	public BigInteger getBeginId() {
		return beginId;
	}

	/**
	 * 设置beginId的值
	 *   
	 * @param beginId 被设置的值
	 */
	public void setBeginId(BigInteger beginId) {
		this.beginId = beginId;
	}

	/**   
	 * 得到endId的值   
	 *   
	 * @return endId的值
	 */
	public BigInteger getEndId() {
		return endId;
	}

	/**
	 * 设置endId的值
	 *   
	 * @param endId 被设置的值
	 */
	public void setEndId(BigInteger endId) {
		this.endId = endId;
	}

	/**   
	 * 得到shardIds的值   
	 *   
	 * @return shardIds的值
	 */
	public String getShardIds() {
		return shardIds;
	}

	/**
	 * 设置shardIds的值
	 *   
	 * @param shardIds 被设置的值
	 */
	public void setShardIds(String shardIds) {
		this.shardIds = shardIds;
	}
	
	public static void main(String[] args){
		List<RowRangeInfo> rowRangeInfoList = new ArrayList<RowRangeInfo>();
		RowRangeInfo rowRangeInfo = new RowRangeInfo();
		rowRangeInfo.setRangeId(1);
		rowRangeInfo.setBeginId(new BigInteger("1"));
		rowRangeInfo.setEndId(new BigInteger("10000"));
		rowRangeInfo.setShardIds("1,2,3");
		rowRangeInfoList.add(rowRangeInfo);
		rowRangeInfo = new RowRangeInfo();
		rowRangeInfo.setRangeId(2);
		rowRangeInfo.setBeginId(new BigInteger("10001"));
		rowRangeInfo.setEndId(new BigInteger("20000"));
		rowRangeInfo.setShardIds("1,2,3,4");
		rowRangeInfoList.add(rowRangeInfo);
		System.out.println(JSON.toJSONString(rowRangeInfoList));
	}

}
