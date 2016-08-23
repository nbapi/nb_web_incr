/**   
 * @(#)EnumIncrType.java	2016年8月19日	下午3:50:22	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.enums;

/**
 * 增量类型Enum
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午3:50:22   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public enum EnumIncrType {

	Inventory(0), Rate(1), Order(2), State(3), Data(4);

	private int intValue;
	private static java.util.HashMap<Integer, EnumIncrType> mappings;

	private synchronized static java.util.HashMap<Integer, EnumIncrType> getMappings() {
		if (mappings == null) {
			mappings = new java.util.HashMap<Integer, EnumIncrType>();
		}
		return mappings;
	}

	private EnumIncrType(int value) {
		intValue = value;
		EnumIncrType.getMappings().put(value, this);
	}

	public int getValue() {
		return intValue;
	}

	public static EnumIncrType forValue(int value) {
		return getMappings().get(value);
	}

}
