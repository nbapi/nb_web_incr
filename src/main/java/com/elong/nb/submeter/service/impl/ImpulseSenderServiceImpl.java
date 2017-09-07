/**   
 * @(#)ImpulseSenderServiceImpl.java	2017年4月18日	上午11:25:17	   
 *     
 * Copyrights (C) 2017艺龙旅行网保留所有权利
 */
package com.elong.nb.submeter.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.elong.nb.submeter.service.IImpulseSenderService;
import com.elong.nb.util.JedisPoolUtil;

/**
 * 发号器接口实现
 *
 * <p>
 * 修改历史:											<br>  
 * 修改日期    		修改人员   	版本	 		修改内容<br>  
 * -------------------------------------------------<br>  
 * 2017年4月18日 上午11:25:17   suht     1.0    	初始化创建<br>
 * </p> 
 *
 * @author		suht  
 * @version		1.0  
 * @since		JDK1.7
 */
@Service
public class ImpulseSenderServiceImpl implements IImpulseSenderService {

	private static final String REDIS_SENTINEL_CONFIG = "redis_sentinel";

	/** 
	 * 当前id
	 *
	 * @param key
	 * @return 
	 *
	 * @see com.elong.nb.service.IImpulseSenderService#curId(java.lang.String)    
	 */
	@Override
	public long curId(String key) {
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("ImpulseSender curId must not be null parameter['key']");
		}
		Jedis jedis = JedisPoolUtil.getJedis(REDIS_SENTINEL_CONFIG);
		String idStr = jedis.get(key);
		JedisPoolUtil.returnRes(jedis);
		return Long.valueOf(idStr);
	}

}
