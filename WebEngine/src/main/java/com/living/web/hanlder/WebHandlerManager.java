/**
 * 
 */
package com.living.web.hanlder;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.ks.exception.EngineException;
import com.ks.logger.LoggerFactory;
import com.living.web.core.HandlerRe;
import com.living.web.core.Rename;
import com.living.web.core.WebContext;

/**
 * @author living.li
 * @date 2013-9-2 下午11:26:38
 * 
 */
public class WebHandlerManager  {
	private Logger log=LoggerFactory.get(WebHandlerManager.class);
	private Map<String,Method> methdCache=new ConcurrentHashMap<String, Method>();
	private Map<String, BaseHandler> serviceCache=new HashMap<String, BaseHandler>();
	
	static final  class Instance{
		static final WebHandlerManager manager=new WebHandlerManager();		
	}
	public final  static  WebHandlerManager get(){
		return Instance.manager;
	}
	public void registHandler(BaseHandler hanlder) {
		String serviceName=hanlder.getClass().getSimpleName();		
		HandlerRe rename=hanlder.getClass().getAnnotation(HandlerRe.class);
		if(rename!=null){
			serviceName=rename.value();
		}
		if(serviceCache.containsKey(serviceName)){
			throw new IllegalArgumentException("handler with name:"+serviceName
					+" already exists.");
		}
		log.info("regist handler :"+hanlder.getClass().getName());
		serviceCache.put(serviceName, hanlder);
		addMethodCache(serviceName,hanlder.getClass());		
	}
	
	private void addMethodCache(String serviceName,Class<?> serverClass){
		for(Method method:serverClass.getDeclaredMethods()){
			if(method.getModifiers()!=Modifier.PUBLIC){
				continue;
			}			
			Class<?>[] paramTypes=method.getParameterTypes();
			if(paramTypes.length!=1||paramTypes[0]!=WebContext.class){
				continue;
			}
			String methodName=method.getName();
			Rename rename=method.getAnnotation(Rename.class);
			if(rename!=null){
				methodName=rename.value();
			}
			String key=serviceName+"."+methodName;
			methdCache.put(key, method);
			log.info("cache method : "+ key);
		}
	}
	
	public Object invokeHanlder(String requestId, WebContext webContext) 
	throws Exception{		
		String serviceId=requestId.substring(0,requestId.indexOf("."));		
		Method method=methdCache.get(requestId);
		BaseHandler obj=serviceCache.get(serviceId);
		if(obj==null){
			throw new  EngineException(EngineException.CODE_SYSTEM_EXCETION,"handler no found "+serviceId);
		}
		if(method==null){
			throw new  EngineException(EngineException.CODE_SYSTEM_EXCETION,"handler method no found "+requestId);
		}
		return  method.invoke(obj,webContext);
	} 
}
