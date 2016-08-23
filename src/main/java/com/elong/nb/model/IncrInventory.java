/**   
 * @(#)IncrInventory.java	2016年8月19日	下午3:57:19	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.google.gson.annotations.SerializedName;

/**
 * 库存增量模型
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午3:57:19   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrInventory extends Inventory implements Serializable {

	/** 
	 * 序列化ID
	 *
	 * long IncrInventory.java serialVersionUID
	 */
	private static final long serialVersionUID = -4730186368031451275L;

	/** 
	 * 增量ID
	 *
	 * BigInteger IncrHotel.java IncrID
	 */
	@SerializedName(value = "IncrID", alternate = "incrID")
	private BigInteger incrID;

	/** 
	 * 变化时间
	 *
	 * Date IncrHotel.java ChangeTime
	 */
	@SerializedName(value = "ChangeTime", alternate = "changeTime")
	private Date changeTime;

	/**   
	 * 得到incrID的值   
	 *   
	 * @return incrID的值
	 */
	public BigInteger getIncrID() {
		return incrID;
	}

	/**
	 * 设置incrID的值
	 *   
	 * @param incrID 被设置的值
	 */
	public void setIncrID(BigInteger incrID) {
		this.incrID = incrID;
	}

	/**   
	 * 得到changeTime的值   
	 *   
	 * @return changeTime的值
	 */
	public Date getChangeTime() {
		return changeTime;
	}

	/**
	 * 设置changeTime的值
	 *   
	 * @param changeTime 被设置的值
	 */
	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

}
