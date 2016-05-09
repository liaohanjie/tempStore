package com.ks.access;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库操作符
 * @author ks
 */
public class SQLOpt {
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
	
	private Map<String, Byte> fieldOpts = new HashMap<String, Byte>();
	
	public void putFieldOpt(String fname, byte opt){
		fieldOpts.put(fname, opt);
	}
	
	public Map<String, Byte> getfieldOpts(){
		return fieldOpts;
	}
}
