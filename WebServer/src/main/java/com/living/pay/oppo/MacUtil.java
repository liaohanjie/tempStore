package com.living.pay.oppo;
import org.apache.commons.codec.digest.HmacUtils;

  
public class MacUtil {     
	
	public static String hamcSha1(String key , String valueToDigest) {
		return HmacUtils.hmacSha1Hex(key, valueToDigest);
	}
}   