/**   
 * @(#)ShardingInfo.java	2017年11月10日	下午4:18:39	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 分片信息模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年11月10日 下午4:18:39   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
/**
 * (类型功能说明描述)
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年11月13日 下午3:49:16   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class ShardingInfo {

	/** 
	 * 分片id	
	 *
	 * int ShardingInfo.java shardId
	 */
	private int shardId;

	/** 
	 * 数据源	
	 *
	 * String ShardingInfo.java dataSource
	 */
	private String dataSource;

	/** 
	 * 分片排序号	
	 *
	 * int ShardingInfo.java orderby
	 */
	private int orderby;

	/** 
	 * 创建时间	
	 *
	 * long ShardingInfo.java _timestamp
	 */
	private long _timestamp;

	/** 
	 * 分片名称	
	 *
	 * String ShardingInfo.java shardName
	 */
	private String shardName;

	/** 
	 * 是否可用
	 *
	 * boolean ShardingInfo.java status
	 */
	private boolean status;

	/**   
	 * 得到shardId的值   
	 *   
	 * @return shardId的值
	 */
	public int getShardId() {
		return shardId;
	}

	/**
	 * 设置shardId的值
	 *   
	 * @param shardId 被设置的值
	 */
	public void setShardId(int shardId) {
		this.shardId = shardId;
	}

	/**   
	 * 得到dataSource的值   
	 *   
	 * @return dataSource的值
	 */
	public String getDataSource() {
		return dataSource;
	}

	/**
	 * 设置dataSource的值
	 *   
	 * @param dataSource 被设置的值
	 */
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	/**   
	 * 得到orderby的值   
	 *   
	 * @return orderby的值
	 */
	public int getOrderby() {
		return orderby;
	}

	/**
	 * 设置orderby的值
	 *   
	 * @param orderby 被设置的值
	 */
	public void setOrderby(int orderby) {
		this.orderby = orderby;
	}

	/**   
	 * 得到_timestamp的值   
	 *   
	 * @return _timestamp的值
	 */
	public long get_timestamp() {
		return _timestamp;
	}

	/**
	 * 设置_timestamp的值
	 *   
	 * @param _timestamp 被设置的值
	 */
	public void set_timestamp(long _timestamp) {
		this._timestamp = _timestamp;
	}

	/**   
	 * 得到shardName的值   
	 *   
	 * @return shardName的值
	 */
	public String getShardName() {
		return shardName;
	}

	/**
	 * 设置shardName的值
	 *   
	 * @param shardName 被设置的值
	 */
	public void setShardName(String shardName) {
		this.shardName = shardName;
	}

	/**   
	 * 得到status的值   
	 *   
	 * @return status的值
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * 设置status的值
	 *   
	 * @param status 被设置的值
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	public static void main(String[] args) {
		List<ShardingInfo> shardingInfoList = new ArrayList<ShardingInfo>();
		ShardingInfo shardingInfo = new ShardingInfo();
		shardingInfo.setShardId(1);
		shardingInfo.setDataSource("dataSource_nbincr_write1");
		shardingInfo.setOrderby(1);
		shardingInfoList.add(shardingInfo);
		shardingInfo = new ShardingInfo();
		shardingInfo.setDataSource("dataSource_nbincr_write2");
		shardingInfo.setOrderby(2);
		shardingInfoList.add(shardingInfo);
		shardingInfo = new ShardingInfo();
		shardingInfo.setDataSource("dataSource_nbincr_write3");
		shardingInfo.setOrderby(3);
		shardingInfoList.add(shardingInfo);
		System.out.println(JSON.toJSONString(shardingInfoList));
	}

}
