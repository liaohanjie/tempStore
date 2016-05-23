package com.ks.account.service;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.app.Application;
import com.ks.logger.LoggerFactory;
import com.ks.util.PackageUtil;

/**
 * 服务工厂
 * @author ks
 *
 */
public final class ServiceFactory {
	
	private static final Logger logger = LoggerFactory.get(ServiceFactory.class);
	
	private static Map<Class<?> , Object> serviceMap = new HashMap<Class<?>, Object>();
	
	static{
		List<String> classes = PackageUtil.getClassName("com.ks.account.service.impl", false);
		for(String clazz : classes){
			try{
				Class<?> cla = Application.getClassLoader().loadClass(clazz);
				BaseService service = (BaseService) cla.newInstance();
				serviceMap.put(cla.getInterfaces()[0],
						Proxy.newProxyInstance(Application.getClassLoader(),
								cla.getInterfaces(),new ServiceHandler(service)));
				logger.info("create service : " + clazz);
			}catch (ClassNotFoundException e) {
				logger.warn("service not found : "+ clazz);
			} catch (InstantiationException e) {
				logger.info("service can not create : "+ clazz);
			} catch (IllegalAccessException e) {
				logger.info("service can not def : "+ clazz);
			}
		}
	}
	/**
	 * 获取service 
	 * @param clazz 这个service的clazz
	 * @return service
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getService(Class<T> clazz){
		return (T) serviceMap.get(clazz);
	}
}
