package com.living.pay;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单号生产类
 * 
 * @author zhoujf
 * @date 2015年6月16日
 */
public class OrderUtil {
	
	public static final String DEFAULT_PARTNER = "SF";
	
	public static AtomicInteger ai = new AtomicInteger(1);
	
	static final String pattern = "yyyyMMddHHmmss";
	
	static final String PATTERN_ORDER = "%s%04d";
	
	static SimpleDateFormat format = new SimpleDateFormat(pattern);
	
	static Random random = new Random();
	
	/**
	 * 生成 20位的订单号， 格式: parnter(2位大写) 年(4位)月(2位)日(2位)时(2位)分(2位)秒(2位)序列(4位)
	 * @return
	 */
	public synchronized static String generateOrderId(String partner) {
		if(partner == null || partner.trim().equals("") || partner.trim().length() != 2) {
			partner = OrderUtil.DEFAULT_PARTNER;
		}
		return partner.toUpperCase() + generateOrderId();
	}
	
	/**
	 * 生成 18位的订单号， 格式: 年(4位)月(2位)日(2位)时(2位)分(2位)秒(2位)序列(4位)
	 * @return
	 */
	public synchronized static String generateOrderId() {
		String id = format.format(new Date());
		int num = ai.getAndIncrement();
		
		if(num >= 9999) {
			ai.set(1);
		}
		
		return String.format(PATTERN_ORDER, id, num);
	}
	
	/*public static void main(String[] args) {
	    for(int i = 0; i < 10000; i ++) {
	    	String id = generateOrderId("pp");
	    	System.out.println(id.length() + ":" + id);
	    }
    }*/
}
