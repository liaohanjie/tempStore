package com.ks.action.logic;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.protocol.vo.chat.ChatMsgRequestVO;
import com.ks.protocol.vo.chat.ChatMsgResponseVO;

/**
 * 聊天
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月28日
 */
public interface ChatAction {

	/**
	 * 发送聊天信息
	 * 
	 * @param chatMsgRequestVO
	 */
	@Transaction
	void send(ChatMsgRequestVO chatMsgRequestVO);

	/**
	 * 轮询聊天信息
	 * 
	 * @param chatMsgRequestVO
	 */
	@Transaction
	List<ChatMsgResponseVO> poll(int userId);
}
