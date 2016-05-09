package com.ks.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;

/**
 * md5工具
 * @author ks.wu
 *
 */
public final class MD5Util {
	
	private static final Logger logger = LoggerFactory.get(MD5Util.class);
	
	/**
	 * 获得MD5字符串
	 * @param code 字符串
	 * @return 加密后的串
	 */
	public static final String decode(String code){
		MessageDigest messageDigest = null;
		try {
			messageDigest =  MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(code.getBytes("UTF-8"));
		}catch (UnsupportedEncodingException e) {  
			logger.error("",e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("",e);
		}
		byte[] byteArray = messageDigest.digest();  
		StringBuffer md5StrBuff = new StringBuffer();  
		for (int i = 0; i < byteArray.length; i++) {              
		    if (Integer.toHexString(0xFF & byteArray[i]).length() == 1){
		        md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
		    }else{
		        md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		    }
		}
	    return md5StrBuff.toString();  
	}
	
	public static final byte[]  encodeBytes(String code){
		MessageDigest messageDigest = null;
		try {
			messageDigest =  MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(code.getBytes("UTF-8"));
		}catch (UnsupportedEncodingException e) {  
			logger.error("",e);
		} catch (NoSuchAlgorithmException e) {
			logger.error("",e);
		}
		return messageDigest.digest();
	}
	
	public static final byte[]  encodeBytes(byte[] bytes){
		MessageDigest messageDigest = null;
		try {
			messageDigest =  MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(bytes);
		}catch (NoSuchAlgorithmException e) {
			logger.error("",e);
		}
		return messageDigest.digest();
	}
}