package com.ks.cache;

import java.util.Map;

/**
 * 对象字段map
 * @author ks
 *
 */
public interface ObjectFieldMap<T> {
	/**
	 * 对象转成map
	 * @param o 要转成map的对象
	 */
	public Map<String,String> objectToMap(T o);
}
