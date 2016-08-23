/**   
 * @(#)EnumStateType.java	2016年8月19日	下午4:20:09	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.enums;

/**
 * 状态类型Enum
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午4:20:09   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public enum EnumStateType {

	HotelId(1), HotelCode(2), RoomId(3), RoomTypeId(4), RatePlanId(5), RatePlanPolicy(6);

	private int intValue;
	private static java.util.HashMap<Integer, EnumStateType> mappings;

	private synchronized static java.util.HashMap<Integer, EnumStateType> getMappings() {
		if (mappings == null) {
			mappings = new java.util.HashMap<Integer, EnumStateType>();
		}
		return mappings;
	}

	private EnumStateType(int value) {
		intValue = value;
		EnumStateType.getMappings().put(value, this);
	}

	public int getValue() {
		return intValue;
	}

	public static EnumStateType forValue(int value) {
		return getMappings().get(value);
	}

}
