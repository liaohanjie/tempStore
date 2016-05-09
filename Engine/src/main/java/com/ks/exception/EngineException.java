package com.ks.exception;

import java.io.Serializable;

/**
 * @author living.li
 * @date  2014年10月21日 下午8:19:14
 *
 *
*/
public class EngineException extends RuntimeException implements Serializable {

	private static final long serialVersionUID = 1L;
	public  static final int CODE_SYSTEM_EXCETION=100000;
	public  static final int CODE_NO_CLIENT=100001;
	public  static final int CODE_SYSTEM_REQUIRED=100002;
	/**错误码*/
	private int code;

	public EngineException(int code,String message) {
		super("["+code+"]:"+message);
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	
	
	
	
}


