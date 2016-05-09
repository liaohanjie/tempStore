package com.ks.action.world;

import java.util.List;

import com.ks.model.chat.ChatMessage;
import com.ks.model.chat.MarqueeMsg;
import com.ks.protocol.vo.chat.ChatMsgRequestVO;
import com.ks.protocol.vo.chat.ChatMsgResponseVO;
import com.ks.protocol.vo.chat.MarqueeMsgVO;
import com.ks.rpc.Async;

/**
 * 聊天
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月28日
 */
public interface WorldChatAction {

	/**
	 * 发送聊天信息
	 */
	void send(ChatMsgRequestVO request);

	/**
	 * 系统发送信息
	 * 
	 * @param request
	 */
	void systemSend(ChatMessage request);

	/**
	 * 轮询聊天信息
	 * 
	 * @param userId
	 * @return
	 */
	List<ChatMsgResponseVO> poll(int userId);

	/**
	 * 查看length条世界聊天的内容
	 * 
	 * @return
	 */
	List<String> getWorldChatMsgs(int length);

	/**
	 * 玩家获取跑马灯内容
	 * 
	 * @param userId
	 * @return
	 */
	List<MarqueeMsgVO> pollMarquee(int userId);
	
	/**
	 * 系统添加跑马灯内容
	 * 
	 * @param entity
	 */
	@Async
	void addMarquee(MarqueeMsg entity);
}