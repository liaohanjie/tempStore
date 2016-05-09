package com.ks.wrold.kernel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import com.ks.protocol.vo.chat.ChatMsgResponseVO;


public class ChatCache {
	
	/**
	 * id生成器
	 */
	public static AtomicLong idGenerater = new AtomicLong(0);

	private static List<ChatMsgResponseVO> msgList = new CopyOnWriteArrayList<>();
	
	static Lock lock = new ReentrantLock();
	
	public static List<ChatMsgResponseVO> getMsgList(){
		return msgList;
	} 
	
	public static void add(ChatMsgResponseVO e) {
		
		try {
			lock.lock();
			
			if (msgList.size() > 1000) {
				msgList.remove(0);
			}
			
			long id = idGenerater.incrementAndGet();
			e.setId(id);
			msgList.add(e);
		} finally {
			lock.unlock();
		}
	}
}
