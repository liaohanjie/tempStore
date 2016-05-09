package com.ks.logic.service;

import com.ks.model.chat.MarqueeMsg;


/**
 * 跑马灯 Service
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年2月29日
 */
public interface MarqueeService {
	
	/**
	 * 添加跑马灯内容
	 * @param entity
	 */
	void add(MarqueeMsg entity);
}