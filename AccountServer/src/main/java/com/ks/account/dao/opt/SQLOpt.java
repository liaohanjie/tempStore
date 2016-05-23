package com.ks.account.dao.opt;
/**
 * 数据库操作符
 * @author ks
 */
public abstract class SQLOpt {
	/**等于 =*/
	public static final byte EQUAL = 1;
	/**加上 +*/
	public static final byte PULS = 2;
	/**减去 -*/
	public static final byte MINUS = 3;
	/**乘以 ×*/
	public static final byte MULTIPLY = 4;
	/**除以 ／**/
	public static final byte DIVIDED = 5;
}
