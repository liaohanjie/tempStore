package com.ks.protocol.sub;

/**
 * 聊天
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月28日
 */
public interface ChatCMD {
	
	/**发送聊天信息*/
	short SEND = 1;
	
	/**轮询聊天信息*/
	short POLL = 2;
	
	/**跑马灯*/
	short MARQUEE = 3;
}
