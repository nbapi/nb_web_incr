/**   
 * @(#)ShardingUtils.java	2017年11月10日	下午6:54:33	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.elong.nb.common.util.CommonsUtil;
import com.elong.nb.model.RowRangeInfo;
import com.elong.nb.model.ShardingInfo;

/**
 * 分片信息加载
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年11月10日 下午6:54:33   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
public class ShardingUtils {

	private static final Log logger = LogFactory.getLog(ShardingUtils.class);

	public static final List<RowRangeInfo> ROWRANGEINFO_LIST = loadRowRangeInfoList();

	public static final Map<Integer, ShardingInfo> SHARDINFO_MAP = loadShardingInfoMap();

	/** 
	 * 加载行范围信息
	 *
	 * @return
	 */
	private static List<RowRangeInfo> loadRowRangeInfoList() {
		InputStream is = null;
		try {
			is = CommonsUtil.class.getClassLoader().getResourceAsStream("conf/custom/env/rowrange.properties");
			if (is != null) {
				String text = IOUtils.toString(is);
				return JSON.parseArray(text, RowRangeInfo.class);
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return Collections.emptyList();
	}

	/** 
	 * 加载分片信息 
	 *
	 * @return
	 */
	private static Map<Integer, ShardingInfo> loadShardingInfoMap() {
		InputStream is = null;
		try {
			is = CommonsUtil.class.getClassLoader().getResourceAsStream("conf/custom/env/shardinfo.properties");
			if (is != null) {
				String text = IOUtils.toString(is);
				List<ShardingInfo> list = JSON.parseArray(text, ShardingInfo.class);
				if (list == null || list.size() == 0)
					return Collections.emptyMap();
				Map<Integer, ShardingInfo> shardingInfoMap = new HashMap<Integer, ShardingInfo>();
				for (ShardingInfo shardingInfo : list) {
					shardingInfoMap.put(shardingInfo.getShardId(), shardingInfo);
				}
				return shardingInfoMap;
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(is);
		}
		return Collections.emptyMap();
	}

}
