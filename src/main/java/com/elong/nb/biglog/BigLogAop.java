/**   
 * @(#)BigLogAop.java	2016年8月24日 下午2:28:56	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.biglog;

import org.apache.commons.lang3.ClassUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.elong.nb.common.biglog.BigLog;
import com.elong.nb.common.biglog.Constants;

/**
 * 增量Service层切面
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月24日 下午2:28:56   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Component
@Aspect
public class BigLogAop {

	public static final String ELONG_INCR_REQUEST_STARTTIME = "incrRequestStartTime";

	private static Logger logger = LogManager.getLogger("biglog");

	/**
	 * 之前
	 * 
	 * @param point
	 * @throws Throwable
	 */
	@Before("execution(* com.elong.nb.service.impl.*.*(..))")
	public void handlerLogBefore(JoinPoint point) {
		RequestAttributes request = RequestContextHolder.getRequestAttributes();
		request.setAttribute(ELONG_INCR_REQUEST_STARTTIME, System.currentTimeMillis(), ServletRequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 之后
	 * 
	 * @param point
	 * @throws Throwable
	 */
	@AfterReturning(pointcut = "execution(* com.elong.nb.service.impl.*.*(..))", returning = "returnValue")
	public void handlerLogAfter(JoinPoint point, Object returnValue) {
		RequestAttributes request = RequestContextHolder.getRequestAttributes();
		String handlerMethodName = ClassUtils.getShortClassName(point.getSignature().getDeclaringTypeName()) + "."
				+ point.getSignature().getName();
		long start = (long) request.getAttribute(ELONG_INCR_REQUEST_STARTTIME, ServletRequestAttributes.SCOPE_REQUEST);
		String useTime = String.valueOf(System.currentTimeMillis() - start);

		BigLog log = new BigLog();
		log.setAppName("Service");
		log.setTraceId((String) (request.getAttribute(Constants.ELONG_REQUEST_TRACEID, ServletRequestAttributes.SCOPE_REQUEST)));
		log.setSpan("1.1.1");
		log.setServiceName(handlerMethodName);
		log.setElapsedTime(useTime);
		try {
			log.setResponseBody(returnValue == null ? null : returnValue.toString());
		} catch (Exception e) {
		}

		Object guid = request.getAttribute(Constants.ELONG_REQUEST_REQUESTGUID, ServletRequestAttributes.SCOPE_REQUEST);
		if (guid != null) {
			log.setUserLogType((String) guid);
		}
		logger.info(log.toString());
	}

	@AfterThrowing(pointcut = "execution(* com.elong.nb.service.impl.*.*(..))", throwing = "throwing")
	public void handlerLogAfterException(JoinPoint point, Object throwing) {
		RequestAttributes request = RequestContextHolder.getRequestAttributes();
		String handlerMethodName = ClassUtils.getShortClassName(point.getSignature().getDeclaringTypeName()) + "."
				+ point.getSignature().getName();
		long start = (long) request.getAttribute(ELONG_INCR_REQUEST_STARTTIME, ServletRequestAttributes.SCOPE_REQUEST);
		String useTime = String.valueOf(System.currentTimeMillis() - start);

		BigLog log = new BigLog();
		log.setAppName("Service");
		log.setTraceId((String) (request.getAttribute(Constants.ELONG_REQUEST_TRACEID, ServletRequestAttributes.SCOPE_REQUEST)));
		log.setSpan("1.1.1");
		log.setServiceName(handlerMethodName);
		log.setElapsedTime(useTime);
		if (throwing != null) {
			Throwable t = (Throwable) throwing;
			log.setBusinessErrorCode("1");
			log.setResponseBody(t.getMessage());
			log.setException(t);
		}

		Object guid = request.getAttribute(Constants.ELONG_REQUEST_REQUESTGUID, ServletRequestAttributes.SCOPE_REQUEST);
		if (guid != null) {
			log.setUserLogType((String) guid);
		}
		logger.info(log.toString());
	}

}
