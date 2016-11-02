/**   
 * @(#)HttpClientUtils.java	2016年8月9日	上午11:10:24	   
 *     
 * Copyrights (C) 2016艺龙旅行网保留所有权利
 */
package com.elong.nb.util;

import java.beans.IntrospectionException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.CodingErrorAction;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.xml.sax.SAXException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

/**
 * HttpClient工具类
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2016年8月9日 上午11:10:24   user     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		user 
 * @version		1.0  
 * @since		JDK1.7
 */
public class HttpClientUtils {

	private static final Log logger = LogFactory.getLog(HttpClientUtils.class);

	protected Gson gson = new GsonBuilder().setPrettyPrinting().create();

	public String toFormatJson(String jsonStr) {
		return gson.toJson(new JsonParser().parse(jsonStr));
	}

	public void formatSaveJson(String jsonStr, String savePath) throws IOException, SAXException, IntrospectionException {
		System.out.println(jsonStr);
		String format_json = toFormatJson(jsonStr);
		System.out.println(format_json);
	}

	protected static CloseableHttpClient client = generateHttpClient();
	
	public static String post(String path, String params) throws Exception {
		HttpURLConnection httpConn = null;
		BufferedReader in = null;
		PrintWriter out = null;
		try {
			URL url = new URL(path);
			httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setRequestMethod("POST");
			httpConn.setDoInput(true);
			httpConn.setDoOutput(true);
			out = new PrintWriter(httpConn.getOutputStream());
			out.println(params);
			out.flush();
			if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				StringBuffer content = new StringBuffer();
				String tempStr = "";
				in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
				while ((tempStr = in.readLine()) != null) {
					content.append(tempStr);
				}
				return content.toString();
			} else {
				throw new IllegalStateException("Post 请求出错！");
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
			httpConn.disconnect();
		}
	}

	public static String httpPost(String reqUrl, String reqData) throws Exception {
		return httpPost(reqUrl, reqData, "application/json");
	}

	public static String httpPost(String reqUrl, String reqData, String contentType) throws Exception {
		long startTime = System.currentTimeMillis();
		URI uri = new URI(reqUrl);
		HttpPost httpPost = new HttpPost(uri);
		contentType = StringUtils.isEmpty(contentType) ? "application/json" : contentType;
		httpPost.addHeader("Content-Type", contentType);
		httpPost.setEntity(new StringEntity(reqData, "UTF-8"));
		System.out.println(httpPost.getRequestLine());
		CloseableHttpResponse response = client.execute(httpPost);
		InputStream is = response.getEntity().getContent();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] b = new byte[1024];
		int len = -1;
		while ((len = is.read(b)) != -1) {
			outputStream.write(b, 0, len);
		}
		is.close();
		outputStream.close();
		response.close();
		long endTime = System.currentTimeMillis();
		logger.info("httpPost,cost time: " + (endTime - startTime));
		return new String(outputStream.toByteArray());
	}

	public static String httpGet(String reqUrl) throws Exception {
		URI uri = new URI(reqUrl);
		HttpGet httpGet = new HttpGet(uri);
		CloseableHttpResponse response = client.execute(httpGet);
		InputStream is = response.getEntity().getContent();
		if (is.available() == 0)
			return null;
		byte[] b = new byte[is.available()];
		is.read(b);
		response.close();
		return new String(b);
	}

	private static CloseableHttpClient generateHttpClient() {
		Registry<ConnectionSocketFactory> socketFactory = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE).build();
		PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactory);
		SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).setSoKeepAlive(true).build();
		connManager.setDefaultSocketConfig(socketConfig);
		ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8)
				.setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).build();
		connManager.setDefaultConnectionConfig(connectionConfig);
		connManager.setMaxTotal(65535);
		connManager.setDefaultMaxPerRoute(65535);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(20000)
				.setConnectionRequestTimeout(20000).setCookieSpec(CookieSpecs.IGNORE_COOKIES).build();
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig)
				.build();
		return httpClient;
	}

}
