package com.ks.cache;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ks.util.PackageUtil;

/**
 * Jedis Bean
 * @author Administrator
 *
 */
public class JedisBeanManager {

	@SuppressWarnings("rawtypes")
	public static Map<Class, JedisRowMapper> mapperCache = new HashMap<Class, JedisRowMapper>();
	@SuppressWarnings("rawtypes")
	public static Map<Class, ObjectFieldMap> objMapperCache = new HashMap<Class, ObjectFieldMap>();
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void init(String packagename) throws Exception{
		List<String> classnames = PackageUtil.getClassName(packagename, true);
		String typestart = "class ";
		for(String classname : classnames){
			Class clazz = Class.forName(classname);
			ParameterizedType pt = (ParameterizedType)clazz.getGenericInterfaces()[0];
			Type type = pt.getActualTypeArguments()[0];
			String str = type.toString();
			String oclassname = str.substring(str.indexOf(typestart) + typestart.length(), str.length());
			Class oclazz = Class.forName(oclassname);
			if(ObjectFieldMap.class.isAssignableFrom(clazz)){
				objMapperCache.put(oclazz, (ObjectFieldMap)clazz.newInstance());
			}else if(JedisRowMapper.class.isAssignableFrom(clazz)){
				mapperCache.put(oclazz, (JedisRowMapper)clazz.newInstance());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T>JedisRowMapper<T> getRowMapper(Class<T> clazz){
		return (JedisRowMapper<T>)mapperCache.get(clazz);
	}
	
	@SuppressWarnings("unchecked")
	public static <T>ObjectFieldMap<T> getObjMapper(Class<T> clazz){
		return (ObjectFieldMap<T>)objMapperCache.get(clazz);
	}
}
