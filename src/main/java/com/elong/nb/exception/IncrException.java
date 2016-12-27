/**   
 * @(#)IncrException.java	2016年8月22日	上午11:27:11	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.exception;

/**
 * 库存增量Exception
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月22日 上午11:27:11   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class IncrException extends RuntimeException {

	/** 
	 * 序列化ID	
	 *
	 * long IncrVentoryException.java serialVersionUID
	 */
	private static final long serialVersionUID = -6363209377982352659L;

	/**   
	 *      
	 */
	public IncrException() {
		super();
	}

	/**   
	 *   
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace   
	 */
	public IncrException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**   
	 *   
	 * @param message
	 * @param cause   
	 */
	public IncrException(String message, Throwable cause) {
		super(message, cause);
	}

	/**   
	 *   
	 * @param message   
	 */
	public IncrException(String message) {
		super(message);
	}

	/**   
	 *   
	 * @param cause   
	 */
	public IncrException(Throwable cause) {
		super(cause);
	}

}
