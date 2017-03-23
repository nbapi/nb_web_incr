package com.elong.nb.util;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalUtil {

	private static ThreadLocal<Map<String, Object>> threadLocal = new InheritableThreadLocal<Map<String, Object>>() {
		public Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		}
	};

	public static void set(String key, Object value) {
		Map<String, Object> map = threadLocal.get();
		map.put(key, value);
		threadLocal.set(map);
	}

	public static Object get(String key) {
		return threadLocal.get().get(key);
	}

}
