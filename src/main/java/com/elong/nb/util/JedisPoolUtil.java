package com.elong.nb.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import com.elong.nb.cache.ConfigFactory;

public class JedisPoolUtil {

	private static JedisSentinelPool pool = null;

	private static JedisPoolConfig loadPoolConfig(Properties resources) {
		JedisPoolConfig config = new JedisPoolConfig();
		Map<String, String> prop = new HashMap<String, String>();
		try {
			if (resources != null) {
				Enumeration<?> en = resources.propertyNames();
				while (en.hasMoreElements()) {
					String name = (String) en.nextElement();
					if (name.startsWith("redis.pool.")) {
						prop.put(name.substring(11), resources.getProperty(name));
					}
				}
			}
			config.setMaxTotal(prop.get("maxActive") == null ? 50 : Integer.valueOf(prop.get("maxActive")));
			config.setMaxIdle(prop.get("maxIdle") == null ? 5 : Integer.valueOf(prop.get("maxIdle")));
			config.setMaxWaitMillis(prop.get("maxWait") == null ? 5000 : Integer.valueOf(prop.get("maxWait")));
			config.setTestOnBorrow(prop.get("testOnBorrow") == null ? true : Boolean.valueOf(prop.get("testOnBorrow")));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return config;
	}

	/** 
	 * 创建连接池 
	 * 
	 */
	private static void createJedisPool(String fileName) {
		// 建立连接池配置参数
		Properties prop = ConfigFactory.getRedisConfig(fileName);
		JedisPoolConfig config = loadPoolConfig(prop);

		String masterName = prop.getProperty("redis.masterName");
		String sentinelstr = prop.getProperty("redis.sentinels");

		Set<String> sentinels = new HashSet<String>();
		String[] sentinelArray = StringUtils.split(sentinelstr, ";");
		if (sentinelArray != null && sentinelArray.length > 0) {
			for (String sentinel : sentinelArray) {
				if (StringUtils.isEmpty(sentinel))
					continue;
				sentinels.add(sentinel);
			}
		}
		pool = new JedisSentinelPool(masterName, sentinels, config);
	}

	/** 
	 * 在多线程环境同步初始化 
	 */
	private static synchronized void poolInit(String fileName) {
		if (pool == null) {
			createJedisPool(fileName);
		}
	}

	/** 
	 * 获取一个jedis 对象 
	 * 
	 * @return 
	 */
	public static Jedis getJedis(String fileName) {
		if (pool == null) {
			poolInit(fileName);
		}
		return pool.getResource();
	}

	/**
	 * 释放一个连接
	 *
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public static void returnRes(Jedis jedis) {
		pool.returnResource(jedis);
	}

}
