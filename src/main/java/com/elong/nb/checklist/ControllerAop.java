package com.elong.nb.checklist;

import java.util.UUID;

import org.apache.commons.lang3.ClassUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.elong.nb.common.checklist.Constants;
import com.elong.springmvc_enhance.utilities.ActionLogHelper;

@Component
@Aspect
public class ControllerAop {

	public static final String ELONG_REQUEST_STARTTIME = "elongRequestStartTime";

	// ------------------------controller begin---------------------------
	/**
	 * 之前
	 * 
	 * @param point
	 * @throws Throwable
	 */
	@Before("execution(public * com.elong.nb.controller.*.*(..))")
	public void handlerLogBefore(JoinPoint point) {
		RequestAttributes request = RequestContextHolder.getRequestAttributes();
		request.setAttribute(ELONG_REQUEST_STARTTIME,
				System.currentTimeMillis(),
				ServletRequestAttributes.SCOPE_REQUEST);
	}

	/**
	 * 之后
	 * 
	 * @param point
	 * @throws Throwable
	 */
	@AfterReturning(pointcut = "execution(public * com.elong.nb.controller.*.*(..))", returning = "returnValue")
	public void handlerLogAfter(JoinPoint point, Object returnValue) {
		RequestAttributes request = RequestContextHolder.getRequestAttributes();
		String classFullName = ClassUtils.getShortClassName(point
				.getSignature().getDeclaringTypeName());
		String methodName = point.getSignature().getName();
		long start = (Long) request.getAttribute(ELONG_REQUEST_STARTTIME,
				ServletRequestAttributes.SCOPE_REQUEST);
		float useTime = System.currentTimeMillis() - start;
		Object guid = request.getAttribute(Constants.ELONG_REQUEST_REQUESTGUID,
				ServletRequestAttributes.SCOPE_REQUEST);
		if (guid == null)
			guid = UUID.randomUUID();
		String result = null;
		if (returnValue instanceof String) {
			result = (String) returnValue;
		} else {
			@SuppressWarnings("unchecked")
			ResponseEntity<byte[]> resp = (ResponseEntity<byte[]>) returnValue;
			result = new String(resp.getBody());
		}

		ActionLogHelper.businessLog((String) guid, true, methodName,
				classFullName, null, useTime, 0, null, result,
				(String) (request.getAttribute(Constants.ELONG_REQUEST_JSON,
						ServletRequestAttributes.SCOPE_REQUEST)));
	}

	@AfterThrowing(pointcut = "execution(public * com.elong.nb.controller.*.*(..))", throwing = "throwing")
	public void handlerLogThrowing(JoinPoint point, Object throwing) {
		RequestAttributes request = RequestContextHolder.getRequestAttributes();
		String classFullName = ClassUtils.getShortClassName(point
				.getSignature().getDeclaringTypeName());
		String methodName = point.getSignature().getName();
		long start = (Long) request.getAttribute(ELONG_REQUEST_STARTTIME,
				ServletRequestAttributes.SCOPE_REQUEST);
		float useTime = System.currentTimeMillis() - start;
		Object guid = request.getAttribute(Constants.ELONG_REQUEST_REQUESTGUID,
				ServletRequestAttributes.SCOPE_REQUEST);
		if (guid == null)
			guid = UUID.randomUUID();
		Exception e = (Exception) throwing;

		ActionLogHelper.businessLog((String) guid, false, methodName,
				classFullName, e, useTime, -1, e.getMessage(), null,
				(String) (request.getAttribute(Constants.ELONG_REQUEST_JSON,
						ServletRequestAttributes.SCOPE_REQUEST)));
	}

	// ----------------------------controller end-------------------------------
}
