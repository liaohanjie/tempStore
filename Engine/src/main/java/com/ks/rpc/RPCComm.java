package com.ks.rpc;

import java.io.Serializable;
/**
 * 远程调用命令
 * @author ks
 */
public class RPCComm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**调用超时时间5000毫秒*/
	public static final long TIME_OUT_TIME=5000L;
	
	/**调用id*/
	private long id;
	/**方法名*/
	private String methodName;
	/**类名*/
	private String className;
	/**参数类型*/
	private Class<?>[] argsClass;
	/**参数*/
	private Object[] args;
	/**返回值*/
	private Object returnValue;
	/**调用开始时间*/
	private long startTime;
	/**是否同步调用*/
	private boolean async;
	/**调用锁*/
	private transient RPCLock lock;

	public boolean isAsync() {
		return async;
	}
	public void setAsync(boolean async) {
		this.async = async;
	}
	public RPCLock getLock() {
		return lock;
	}
	public void setLock(RPCLock lock) {
		this.lock = lock;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public Object[] getArgs() {
		return args;
	}
	public void setArgs(Object[] args) {
		this.args = args;
	}
	public Object getReturnValue() {
		return returnValue;
	}
	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}
	public Class<?>[] getArgsClass() {
		return argsClass;
	}
	public void setArgsClass(Class<?>[] argsClass) {
		this.argsClass = argsClass;
	}
	
}