package com.elong.nb.checklist;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.elong.nb.common.checklist.Constants;
import com.elong.nb.common.checklist.EnumNBLogType;
import com.elong.nb.common.checklist.NBActionLogHelper;
import com.elong.nb.util.ThreadLocalUtil;

public class ChecklistAspect {

	public static final String ELONG_REQUEST_STARTTIME = "elongRequestStartTime";

	/**
	 * 之前
	 * 
	 * @param point
	 * @throws Throwable
	 */
	public void handlerLogBefore(JoinPoint point) {
		try {
			ThreadLocalUtil.set(point.toString() + "_" + ELONG_REQUEST_STARTTIME, System.currentTimeMillis());
			RequestAttributes request = RequestContextHolder.getRequestAttributes();
			// 调用链新起的线程，拦截方法会在此返回
			if (request == null)
				return;
			// 1、Controller 2、调用链只有一个线程的所有拦截方法
			Object guid = request.getAttribute(Constants.ELONG_REQUEST_REQUESTGUID, ServletRequestAttributes.SCOPE_REQUEST);
			//
			if (guid == null) {// Controller请求回到此处
				guid = UUID.randomUUID().toString();
				request.setAttribute(Constants.ELONG_REQUEST_REQUESTGUID, guid, ServletRequestAttributes.SCOPE_REQUEST);
				ThreadLocalUtil.set(Constants.ELONG_REQUEST_REQUESTGUID, guid);

				Object[] args = point.getArgs();
				for (Object arg : args) {
					if (arg == null)
						continue;
					if (arg instanceof HttpServletRequest) {
						ThreadLocalUtil.set(Constants.ELONG_REQUEST_USERNAME, ((HttpServletRequest) arg).getHeader("userName"));
						break;
					}
				}
			}
		} catch (Exception e) {// 异常吃掉，避免影响主业务
		}
	}

	/**
	 * 之后
	 * 
	 * @param point
	 * @throws Throwable
	 */
	public void handlerLogAfter(JoinPoint point, Object returnValue) {
		try {
			String classFullName = ClassUtils.getShortClassName(point.getSignature().getDeclaringTypeName());
			String methodName = point.getSignature().getName();
			long start = (Long) ThreadLocalUtil.get(point.toString() + "_" + ELONG_REQUEST_STARTTIME);
			float useTime = System.currentTimeMillis() - start;
			Object guid = ThreadLocalUtil.get(Constants.ELONG_REQUEST_REQUESTGUID);
			if (guid == null) {
				guid = UUID.randomUUID().toString();
			}

			Object userName = ThreadLocalUtil.get(Constants.ELONG_REQUEST_USERNAME);
			String userNameStr = userName == null ? null : (String) userName;

			int code = 0;
			RequestAttributes request = RequestContextHolder.getRequestAttributes();
			String businessCode = (String) request.getAttribute(Constants.ELONG_RESPONSE_CODE, ServletRequestAttributes.SCOPE_REQUEST);
			if (businessCode != null && !businessCode.equals("0")) {
				code = 1;
			}

			String result = null;
			if (returnValue instanceof ResponseEntity) {
				@SuppressWarnings("unchecked")
				ResponseEntity<byte[]> resp = (ResponseEntity<byte[]>) returnValue;
				result = new String(resp.getBody());
				NBActionLogHelper.businessLog(guid.toString(), true, methodName, classFullName, null, useTime, code, businessCode, result,
						(String) (request.getAttribute(Constants.ELONG_REQUEST_JSON, ServletRequestAttributes.SCOPE_REQUEST)), userNameStr,
						EnumNBLogType.OUTER_CONTROLLER);
			} else {
				NBActionLogHelper.businessLog(guid.toString(), true, methodName, classFullName, null, useTime, code, businessCode, result,
						JSON.toJSONString(point.getArgs()), userNameStr, EnumNBLogType.DAO);
			}
		} catch (Exception e) {// 异常吃掉，避免影响主业务
		}
	}

	public void handlerLogThrowing(JoinPoint point, Object throwing) {
		try {
			String classFullName = ClassUtils.getShortClassName(point.getSignature().getDeclaringTypeName());
			String methodName = point.getSignature().getName();
			long start = (Long) ThreadLocalUtil.get(point.toString() + "_" + ELONG_REQUEST_STARTTIME);
			float useTime = System.currentTimeMillis() - start;
			Object guid = ThreadLocalUtil.get(Constants.ELONG_REQUEST_REQUESTGUID);
			if (guid == null) {
				guid = UUID.randomUUID().toString();
			}
			Object userName = ThreadLocalUtil.get(Constants.ELONG_REQUEST_USERNAME);
			String userNameStr = userName == null ? null : (String) userName;
			Exception e = null;
			if (throwing instanceof Exception) {
				e = (Exception) throwing;
			}
			if (StringUtils.contains(classFullName, "Controller")) {
				RequestAttributes request = RequestContextHolder.getRequestAttributes();
				NBActionLogHelper.businessLog((String) guid, false, methodName, classFullName, e, useTime, -1, e.getMessage(), null,
						(String) (request.getAttribute(Constants.ELONG_REQUEST_JSON, ServletRequestAttributes.SCOPE_REQUEST)), userNameStr,
						EnumNBLogType.OUTER_CONTROLLER);
			} else {
				NBActionLogHelper.businessLog((String) guid, false, methodName, classFullName, e, useTime, -1, e.getMessage(), null,
						JSON.toJSONString(point.getArgs()), userNameStr, EnumNBLogType.DAO);
			}
		} catch (Exception e) {// 异常吃掉，避免影响主业务
		}
	}

}
