package com.ks.wrold.kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.ks.model.chat.MarqueeMsg;

/**
 * 跑马灯消息缓存
 * 
 * 默认保存 50 条
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年2月29日
 */
public class MarqueeCache {
	
	static CopyOnWriteArrayList<MarqueeMsg> MARQUEE_LIST = new CopyOnWriteArrayList<>();
	
	static Lock lock = new ReentrantLock();
	
	private static int MAX_SIZE = 50;

	/**
	 * 添加跑马灯内容，超过 MAX_SIZE 删除最开始的一条
	 * @param entity
	 */
	public static void add(MarqueeMsg entity) {
		try {
			lock.lock();
			
			if (MARQUEE_LIST.size() >= MAX_SIZE) {
				MARQUEE_LIST.remove(0);
			}
			MARQUEE_LIST.add(entity);
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 拉取用户需要跑马灯信息
	 * 
	 * @param timestamp
	 * @return
	 */
	public static List<MarqueeMsg> poll(long timestamp){
		List<MarqueeMsg> list = new ArrayList<>();
		
		for (MarqueeMsg entity : MARQUEE_LIST) {
			if (entity.getCreateTime().getTime() > timestamp) {
				list.add(entity);
			}
		}
		return list;
	}

	public static List<MarqueeMsg> getMarqueeList() {
		return MARQUEE_LIST;
	}
}
