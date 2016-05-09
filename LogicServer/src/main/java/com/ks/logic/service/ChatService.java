package com.ks.logic.service;

import java.util.List;

import com.ks.model.chat.ChatMessage;

/**
 * 聊天
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月28日
 */
public interface ChatService {

	/**
	 * 保存聊天
	 * 
	 * @param entity
	 */
	void add(ChatMessage entity);

	/**
	 * 获取并删除聊天
	 * 
	 * @param userId
	 * @return
	 */
	List<ChatMessage> queryByUserId(int userId);

	/**
	 * 轮询聊天信息
	 * 
	 * @param userId
	 * @return
	 */
	// List<ChatMsgResponseVO> poll(int userId);
}