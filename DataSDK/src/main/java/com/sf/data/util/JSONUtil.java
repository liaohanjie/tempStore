package com.sf.data.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 * JSON工具
 * @author ks
 * 
 */
public final class JSONUtil {
	private static final ObjectMapper mapper = new ObjectMapper();

	/**
	 * 将对象解析成JSON
	 * 
	 * @param o 要解析成JSON的对象
	 * @return 解析成JSON后的字符串
	 */
	public static String toJson(Object o) {
		try {
			return mapper.writeValueAsString(o);
		} catch (Exception e) {
			throw new RuntimeException("Object to JSON ERROR：" + o,e);
		}
	}

	/**
	 * JSON解析成对象
	 * 
	 * @param json 要解析的JSON
	 * @param typeRef 解析成对象的Class
	 * @return 解析后的对象
	 */
	public static <T> T toObject(String json, TypeReference<T> typeRef) {
		try {
			return mapper.readValue(json, typeRef);
		} catch (Exception e) {
			throw new RuntimeException("JSON to Object ERROR："+json,e);
		}
	}
	/**
	 * JSON解析成对象
	 * 
	 * @param json 要解析的JSON
	 * @param clazz 解析成对象的Class
	 * @return 解析后的对象
	 */
	public static <T>T toObject(String json,Class<T> clazz){
		try {
			return mapper.readValue(json, clazz);
		} catch(Exception e){
			throw new RuntimeException("JSON to Object ERROR："+json,e);
		}
	}
}
