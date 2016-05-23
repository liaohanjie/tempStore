package com.living.web.servlet.sdk;

import java.util.HashMap;
import java.util.Map;

/**
 * SDK
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月17日
 */
public class SdkManager {
	
	private static Map<Object, SdkHandler> mapHandler = new HashMap<>();
	
	static {
		add("uc-a", new UcSdkHandler());
	}
	
	public static void add(Object key, SdkHandler handler){
		if (mapHandler.containsKey(key)) {
			throw new RuntimeException("sdk key is duplicate. key=" + key );
		}
		mapHandler.put(key, handler);
	}
	
	public static SdkHandler get(Object key){
		return mapHandler.get(key);
	}

}
