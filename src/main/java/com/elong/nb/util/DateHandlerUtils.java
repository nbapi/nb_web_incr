/**   
 * @(#)DateHandlerUtils.java	2016年9月20日	下午5:03:37	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年9月20日 下午5:03:37   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class DateHandlerUtils {

	/** 
	 * 获取指定类型偏移值后的日期
	 *
	 * @param date
	 * @param type
	 * @param value
	 * @param pattern
	 * @return
	 */
	public static String getOffsetDateStr(Date date, int type, int value, String pattern) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(type, value);
		return formatDate(calendar.getTime(), pattern);
	}

	/** 
	 * 获取指定类型偏移值后的日期
	 *
	 * @param type Calendar.HOUR 等等
	 * @param value 偏移值
	 * @return
	 */
	public static Date getOffsetDate(int type, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(type, value);
		return calendar.getTime();
	}

	/** 
	 * 格式化日期 
	 *
	 * @param date
	 * @param pattern
	 * @return 返回字符串
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	/**   
	 * 得到dBExpireDate的值   
	 *   
	 * @return dBExpireDate的值
	 */
	public static Date getDBExpireDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -30);
		return calendar.getTime();
	}

	/**   
	 * 得到cacheExpireDate的值   
	 *   
	 * @return cacheExpireDate的值
	 */
	public static Date getCacheExpireDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, -30);
		return calendar.getTime();
	}

}
