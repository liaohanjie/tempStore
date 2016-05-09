package com.ks.rpc;
/**
 * 断线操作
 * @author ks
 */
public interface ConnectClosed {
	/**
	 * 断线之后执行的操作
	 * @throws Exception 
	 */
	void exec() throws Exception;
}
