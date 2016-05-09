package com.ks.logic.service;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.ks.access.DataSourceUtils;
import com.ks.access.Transaction;
import com.ks.cache.JedisUtils;
import com.ks.timer.TimerController;
/**
 * 服务代理
 * @author ks
 *
 */
public class ServiceHandler implements InvocationHandler {
	private BaseService service;
	public ServiceHandler(BaseService service){
		this.service = service;
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		DataSourceUtils.setAutoCommit(!method.isAnnotationPresent(Transaction.class));
		Object result = null;
		try{
			result = method.invoke(service, args);
			DataSourceUtils.commit();
			JedisUtils.exec();
			TimerController.execEvents();
		}catch (Exception e) {
			DataSourceUtils.rollback();
			JedisUtils.discard();
			TimerController.clearEvents();
			throw e;
		}finally{
			DataSourceUtils.releaseConnection();
			JedisUtils.returnJedis();
		}
		return result;
	}
}
