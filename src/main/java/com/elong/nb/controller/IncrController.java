/**   
 * @(#)IncrController.java	2016年8月19日	下午3:17:19	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.controller;

import java.io.IOException;
import java.text.MessageFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elong.nb.UserServiceAgent;
import com.elong.nb.common.gson.GsonUtil;
import com.elong.nb.common.model.ErrorCode;
import com.elong.nb.common.model.ProxyAccount;
import com.elong.nb.common.model.RestRequest;
import com.elong.nb.common.model.RestResponse;
import com.elong.nb.exception.IncrException;
import com.elong.nb.model.IncrIdRequest;
import com.elong.nb.model.IncrIdResponse;
import com.elong.nb.model.IncrRequest;
import com.elong.nb.model.IncrResponse;
import com.elong.nb.model.enums.EnumIncrType;
import com.elong.nb.service.IIncrService;
import com.elong.nb.service.IIncrValidateService;
import com.elong.nb.service.impl.IncrServiceFactory;

/**
 * 增量Controller
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月19日 下午3:17:19   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Controller
public class IncrController {

	private static final Log logger = LogFactory.getLog(IncrController.class);

	@Resource
	private IIncrValidateService incrValidateService;

	@Resource
	private IncrServiceFactory incrServiceFactory;

	/** 
	 * 获取状态增量
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/Hotel/GetIncrState", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getIncrState(HttpServletRequest request) throws IOException {
		return getIncrDatas("getIncrState", request);
	}

	/** 
	 * 获取房价增量
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/Hotel/GetIncrRates", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getIncrRates(HttpServletRequest request) throws IOException {
		return getIncrDatas("getIncrRates", request);
	}

	/** 
	 * 获取订单增量
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/Hotel/GetIncrOrders", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getIncrOrders(HttpServletRequest request) throws IOException {
		return getIncrDatas("getIncrOrders", request);
	}

	/** 
	 * 获取酒店增量
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/Hotel/GetIncrHotel", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getIncrHotel(HttpServletRequest request) throws IOException {
		return getIncrDatas("getIncrHotel", request);
	}

	/** 
	 * 获取库存增量
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/Hotel/GetIncrInventories", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getIncrInventories(HttpServletRequest request) throws IOException {
		return getIncrDatas("getIncrInventories", request);
	}

	/** 
	 * 获取最后的更新ID
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/api/Hotel/GetLastId", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getLastId(HttpServletRequest request) throws IOException {
		RestRequest<IncrIdRequest> restRequest = GsonUtil.toReq(request, IncrIdRequest.class, null);
		Double version = restRequest.getVersion() == null ? 0d : restRequest.getVersion();
		try {
			String userName = request.getHeader("userName");
			ProxyAccount proxyAccount = UserServiceAgent.findProxyByUsername(userName);

			// 校验请求参数
			String rst = incrValidateService.validateIncrIdRequest(restRequest);
			logger.info("getLastId,checkMessage,result = " + rst);
			RestResponse<IncrIdResponse> restResponse = new RestResponse<IncrIdResponse>(restRequest.getGuid());
			if (StringUtils.isNotBlank(rst)) {
				restResponse.setCode(rst);
				return new ResponseEntity<byte[]>(GsonUtil.toJson(restResponse, version).getBytes(), HttpStatus.OK);
			}
			// 获取响应数据
			EnumIncrType incrType = restRequest.getRequest().getIncrType();
			restResponse = incrServiceFactory.getIIncrService(incrType).getLastId(restRequest, proxyAccount);
			logger.info("getLastId,responseCode = " + restResponse.getCode());
			// 返回响应数据
			return new ResponseEntity<byte[]>(GsonUtil.toJson(restResponse, version).getBytes(), HttpStatus.OK);
		} catch (IncrException e) {
			// 返回业务异常
			logger.error("getLastId,business error = " + e.getMessage(), e);
			RestResponse<IncrIdResponse> restResponse = new RestResponse<IncrIdResponse>(restRequest.getGuid());
			restResponse.setCode(MessageFormat.format(ErrorCode.Incr_Exception, new Object[] { "getLastId", e.getMessage() }));
			return new ResponseEntity<byte[]>(GsonUtil.toJson(restResponse, version).getBytes(), HttpStatus.OK);
		} catch (Exception e) {
			// 返回系统异常
			logger.error("getLastId,system error = " + e.getMessage(), e);
			RestResponse<IncrIdResponse> restResponse = new RestResponse<IncrIdResponse>(restRequest.getGuid());
			restResponse.setCode(MessageFormat.format(ErrorCode.Incr_Exception, new Object[] { "getLastId", e.getMessage() }));
			return new ResponseEntity<byte[]>(GsonUtil.toJson(restResponse, version).getBytes(), HttpStatus.OK);
		}
	}

	/** 
	 * 获取增量数据
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ResponseEntity<byte[]> getIncrDatas(String getIncrDatas, HttpServletRequest request) throws IOException {
		RestRequest<IncrRequest> restRequest = GsonUtil.toReq(request, IncrRequest.class, null);
		Double version = restRequest.getVersion() == null ? 0d : restRequest.getVersion();
		try {
			String userName = request.getHeader("userName");
			ProxyAccount proxyAccount = UserServiceAgent.findProxyByUsername(userName);

			// 校验请求参数
			String rst = incrValidateService.validateIncrRequest(restRequest);
			logger.info(getIncrDatas + ",checkMessage,result = " + rst);
			RestResponse<IncrResponse> restResponse = new RestResponse<IncrResponse>(restRequest.getGuid());
			if (StringUtils.isNotBlank(rst)) {
				restResponse.setCode(rst);
				return new ResponseEntity<byte[]>(GsonUtil.toJson(restResponse, version).getBytes(), HttpStatus.OK);
			}
			// 获取响应数据
			IIncrService incrService = incrServiceFactory.getIIncrService(getIncrDatas);
			restResponse = incrService.getIncrDatas(restRequest, proxyAccount);
			logger.info(getIncrDatas + ",responseCode = " + restResponse.getCode());
			// 返回响应数据
			return new ResponseEntity<byte[]>(GsonUtil.toJson(restResponse, version).getBytes(), HttpStatus.OK);
		} catch (IncrException e) {
			// 返回业务异常
			logger.error(getIncrDatas + ",business error = " + e.getMessage(), e);
			RestResponse<IncrResponse> restResponse = new RestResponse<IncrResponse>(restRequest.getGuid());
			restResponse.setCode(MessageFormat.format(ErrorCode.Incr_Exception, new Object[] { getIncrDatas, e.getMessage() }));
			return new ResponseEntity<byte[]>(GsonUtil.toJson(restResponse, version).getBytes(), HttpStatus.OK);
		} catch (Exception e) {
			// 返回系统异常
			logger.error(getIncrDatas + ",system error = " + e.getMessage(), e);
			RestResponse<IncrResponse> restResponse = new RestResponse<IncrResponse>(restRequest.getGuid());
			restResponse.setCode(MessageFormat.format(ErrorCode.Incr_Exception, new Object[] { getIncrDatas, e.getMessage() }));
			return new ResponseEntity<byte[]>(GsonUtil.toJson(restResponse, version).getBytes(), HttpStatus.OK);
		}
	}

}
