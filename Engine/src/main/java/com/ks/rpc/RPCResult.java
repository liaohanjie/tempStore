package com.ks.rpc;

import java.io.Serializable;
/**
 * 远程调用结果
 * @author ks
 *
 */
public class RPCResult implements Serializable {

	private static final long serialVersionUID = 6054919648719825653L;
	/**编号*/
	private long id;
	/**结果*/
	private Object result;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
