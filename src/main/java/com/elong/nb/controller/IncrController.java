package com.elong.nb.controller;

import java.io.IOException;
import java.math.BigInteger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.elong.nb.common.gson.GsonUtil;
import com.elong.nb.common.model.EnumOrderType;
import com.elong.nb.common.model.RestRequest;
import com.elong.nb.common.model.RestResponse;
import com.elong.nb.common.util.CommonsUtil;
import com.elong.nb.common.util.ValidateUtil;
import com.elong.nb.model.IncrHotel;
import com.elong.nb.model.IncrIdRequest;
import com.elong.nb.model.IncrIdResponse;
import com.elong.nb.model.IncrInventory;
import com.elong.nb.model.IncrOrder;
import com.elong.nb.model.IncrOrderResponse;
import com.elong.nb.model.IncrRate;
import com.elong.nb.model.IncrRequest;
import com.elong.nb.model.IncrState;
import com.elong.nb.model.IncrStateResponse;
import com.elong.nb.service.IIncrHotelService;
import com.elong.nb.service.IIncrInventoryService;
import com.elong.nb.service.IIncrOrderService;
import com.elong.nb.service.IIncrRateService;
import com.elong.nb.service.IIncrStateService;

@Controller
public class IncrController {

	@Resource
	protected IIncrOrderService incrOrderService;
	@Resource
	protected IIncrHotelService incrHotelService;
	@Resource
	protected IIncrStateService incrStateService;
	@Resource
	protected IIncrRateService incrRateService;
	@Resource
	protected IIncrInventoryService incrInventoryService;

	private String lastId = CommonsUtil.CONFIG_PROVIDAR
			.getProperty("NotNull.restRequest.Request.LastId");

	private String incrtype = CommonsUtil.CONFIG_PROVIDAR
			.getProperty("NotNull.restRequest.Request.IncrType");

	private String lastTime = CommonsUtil.CONFIG_PROVIDAR
			.getProperty("NotNull.restRequest.Request.LastTime");

	@RequestMapping(value = "/api/Hotel/GetIncrState", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getIncrState(HttpServletRequest request)
			throws IOException {
		RestRequest<IncrRequest> restRequest = GsonUtil.toReq(request,
				IncrRequest.class);
		/**
		 * 数据校验
		 */
		String rst = validateIncrRequest(restRequest);
		if (StringUtils.isNotBlank(rst)) {
			RestResponse<IncrStateResponse> response = new RestResponse<IncrStateResponse>(
					restRequest.getGuid());
			response.setCode(rst);
			return new ResponseEntity(GsonUtil.toJson(response,
					restRequest.getVersion()).getBytes(), HttpStatus.OK);
		}

		RestResponse<IncrStateResponse> restResponse = new RestResponse<IncrStateResponse>(
				restRequest.getGuid());
		IncrStateResponse response = new IncrStateResponse();
		response.setStates(incrStateService.getIncrStates(restRequest
				.getRequest().getLastId()));
		restResponse.setResult(response);
		return new ResponseEntity(GsonUtil.toJson(restResponse,
				restRequest.getVersion()).getBytes(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/Hotel/GetIncrOrders", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getIncrOrders(HttpServletRequest request)
			throws IOException {
		RestRequest<IncrRequest> restRequest = GsonUtil.toReq(request,
				IncrRequest.class);
		/**
		 * 数据校验
		 */
		String rst = validateIncrRequest(restRequest);
		if (StringUtils.isNotBlank(rst)) {
			RestResponse<IncrOrderResponse> response = new RestResponse<IncrOrderResponse>(
					restRequest.getGuid());
			response.setCode(rst);
			return new ResponseEntity(GsonUtil.toJson(response,
					restRequest.getVersion()).getBytes(), HttpStatus.OK);
		}

		RestResponse<IncrOrderResponse> restResponse = new RestResponse<IncrOrderResponse>(
				restRequest.getGuid());
		IncrOrderResponse response = new IncrOrderResponse();
		restResponse.setResult(response);
		return new ResponseEntity(GsonUtil.toJson(restResponse,
				restRequest.getVersion()).getBytes(), HttpStatus.OK);
	}

	@RequestMapping(value = "/api/Hotel/GetLastId", method = RequestMethod.POST)
	public ResponseEntity<byte[]> getLastId(HttpServletRequest request)
			throws IOException {
		RestRequest<IncrIdRequest> restRequest = GsonUtil.toReq(request,
				IncrIdRequest.class);
		/**
		 * 数据校验
		 */
		String rst = validateIncrIdRequest(restRequest);
		if (StringUtils.isNotBlank(rst)) {
			RestResponse<IncrIdResponse> response = new RestResponse<IncrIdResponse>(
					restRequest.getGuid());
			response.setCode(rst);
			return new ResponseEntity(GsonUtil.toJson(response,
					restRequest.getVersion()).getBytes(), HttpStatus.OK);
		}

		RestResponse<IncrIdResponse> restResponse = new RestResponse<IncrIdResponse>(
				restRequest.getGuid());
		restResponse.setResult(new IncrIdResponse());
		switch (restRequest.getRequest().getIncrType()) {
		case Inventory:
			IncrInventory incrInventory = incrInventoryService
					.getLastIncrInventory(restRequest.getRequest()
							.getLastTime());
			if (incrInventory != null)
				restResponse.getResult().setLastId(incrInventory.getIncrID());
			else
				restResponse.getResult().setLastId(new BigInteger("-1"));
			break;
		case Order:
			EnumOrderType searchOrderType = restRequest.getProxyInfo()
					.getSearchOrderType();
			String proxyId = restRequest.getProxyInfo().getProxyId();
			Integer orderFrom = restRequest.getProxyInfo().getOrderFrom();

			IncrOrder incrOrder = incrOrderService.getIncrOrders(restRequest
					.getRequest().getLastTime(), searchOrderType, proxyId,
					orderFrom, 1000);

			if (incrOrder != null)
				restResponse.getResult().setLastId(incrOrder.getIncrID());
			else
				restResponse.getResult().setLastId(new BigInteger("-1"));
			break;
		case Rate:
			IncrRate incrRate = incrRateService.getLastIncrRate(restRequest
					.getRequest().getLastTime());
			if (incrRate != null)
				restResponse.getResult().setLastId(incrRate.getIncrID());
			else
				restResponse.getResult().setLastId(new BigInteger("-1"));
			break;
		case State:
			IncrState incrState = incrStateService.getLastIncrState(restRequest
					.getRequest().getLastTime());
			if (incrState != null)
				restResponse.getResult().setLastId(incrState.getIncrID());
			else
				restResponse.getResult().setLastId(new BigInteger("-1"));
			break;
		case Data:
			IncrHotel incrHotel = incrHotelService.getLastIncrHotel(restRequest
					.getRequest().getLastTime());
			if (incrHotel != null)
				restResponse.getResult().setLastId(incrHotel.getIncrID());
			else
				restResponse.getResult().setLastId(new BigInteger("-1"));
			break;
		default:
			restResponse.getResult().setLastId(new BigInteger("-1"));
			break;
		}
		return new ResponseEntity(GsonUtil.toJson(restResponse,
				restRequest.getVersion()).getBytes(), HttpStatus.OK);
	}

	private String validateIncrRequest(RestRequest<IncrRequest> req) {
		StringBuffer sb = new StringBuffer(
				ValidateUtil.validateRestRequest(req));
		if (req.getRequest().getLastId() == null)
			sb.append(lastId);
		return sb.toString();
	}

	private String validateIncrIdRequest(RestRequest<IncrIdRequest> req) {
		StringBuffer sb = new StringBuffer(
				ValidateUtil.validateRestRequest(req));
		if (req.getRequest().getIncrType() == null)
			sb.append(incrtype);
		if (req.getRequest().getLastTime() == null)
			sb.append(lastTime);
		return sb.toString();
	}
}
