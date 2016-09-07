package com.elong.nb.test;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;

import com.elong.nb.model.IncrIdRequest;
import com.elong.nb.model.IncrRequest;
import com.elong.nb.model.enums.EnumIncrType;
import com.elong.nb.util.HttpClientUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class IncrTestCase {

	@Test
	public void test() throws Exception {

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

		// 11、getIncrState,2、getIncrRates,3、getIncrOrders,4、getIncrHotel,55、getIncrInventories,6、GetLastId
		String reqUrl = "http://localhost:8080/nb_web_incr/api/Hotel/getIncrState";//本地
		reqUrl = "http://nbapi-incr.vip.elong.com/OpenApiWeb/api/Hotel/getIncrOrders";//java线上
		reqUrl = "http://bhb-bapiweb1.ab.elong.com/OpenApiWeb/api/Hotel/getIncrOrders";//.net线上

		IncrIdRequest incrIdRequest = new IncrIdRequest();
		incrIdRequest.setIncrType(EnumIncrType.Order);
		incrIdRequest.setLastTime(DateUtils.add(new Date(), Calendar.DATE, -10));

		IncrRequest incrRequest = new IncrRequest();
		incrRequest.setLastId(new BigInteger("281477748585701"));
		incrRequest.setCount(3);

		String reqData = "{\"Local\":\"zh_CN\",\"Request\":"
				+ gson.toJson(incrRequest)
				+ ",\"Version\":1.11,\"Guid\":\"bff01c27-3a10-4f3f-bcba-b21ece0ec23b\",\"ProxyInfo\":{\"UserName\":\"a7523bcefcd67075d9914b7497b2c76a\",\"AppKey\":\"9d730cf6e2f5bc1fef5e536516fddb8b\",\"UserGroup\":\"0\",\"ProxyId\":\"A06\",\"CardNo\":\"2000000004068522160\",\"OrderFrom\":\"50\",\"MemberLevel\":\"1\",\"SellChannel\":4,\"BookingChannel\":2,\"SearchOrderType\":\"1\",\"OrderContactType\":1,\"IsFilterSEMHotel\":false,\"EnabledPrepaySettlment\":false,\"PrepaySettlementRate\":\"0\",\"EnabledVirtualCardForPrepay\":false,\"EnabledVirtualCardForGuarantee\":false,\"EnabledSpecialRate\":false,\"EnabledInstantConfirm\":false,\"EnabledInvoiceRole\":false,\"EnabledElongNoteReadRole\":false,\"EnabledCouponReadRole\":false,\"EnabledPrepayProducts\":false,\"Supplier\":null,\"ClientIP\":\"192.168.99.153\",\"EnabledCommentReadRole\":false,\"CommentUserKey\":null,\"EnableReturnAgentcyRateCost\":false,\"EnabledCouponRole\":false,\"LowestProfitPercent\":0,\"InvoiceMode\":0,\"InvoiceType\":0,\"AgencyCommisionLevel\":1,\"PrepayCommisionLevel\":1,\"IntegerPriceType\":0,\"IsOnlyStraight\":false}}";

		String contentType = "application/json";
		try {
			System.out.println(reqData);
			String result = HttpClientUtils.httpPost(reqUrl, reqData, contentType);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
