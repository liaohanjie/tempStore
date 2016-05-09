/**
 * 
 */
package com.ks.exceptions;

import com.ks.exception.EngineException;

/**
 * @author living.li
 * @date  2015年5月19日 下午5:45:14
 *
 *
 */
public class AccountException extends EngineException {
	
	/**订单不存在*/
	public final static int code_order_not_exist=10001;
	/**订单是是空的*/
	public final static int code_order_is_null=10002;
	
	public final static int code_order_not_match_status=10002;
	
	public final static int code_server_no_found=10003;
	/**没有找到mall*/
	public final static int code_mall_no_found=10004;
	/**
	 * @param code
	 * @param message
	 */
	public AccountException(int code, String message) {
		
		super(code, message);
	}
	/** */
	private static final long serialVersionUID = 1L;

}
