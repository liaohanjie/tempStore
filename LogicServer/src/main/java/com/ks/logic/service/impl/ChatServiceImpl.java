package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ChatService;
import com.ks.model.chat.ChatMessage;
import com.ks.model.chat.SendChatType;

/**
 * 爬塔试炼集星奖励配置
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public class ChatServiceImpl extends BaseService implements ChatService {

	/** 最大消息数量 */
	final static int MAX_COUNT = 20;

	@Override
	public void add(ChatMessage entity) {
		long length = chatDAO.findLengthByUserIdCache(entity.getReceiverId());

		if (entity.getType() == SendChatType.PRIVATE) {
			// 删除多余的聊天记录, 最多保存 MAX_COUNT 条
			if (length >= MAX_COUNT) {
				for (int i = 0; i < length - MAX_COUNT; i++) {
					chatDAO.deleteLeftByUserId(entity.getReceiverId());
				}
			}
		}
		chatDAO.addCache(entity);
	}

	@Override
	public List<ChatMessage> queryByUserId(int userId) {
		List<ChatMessage> list = chatDAO.queryByUserIdCache(userId);
		chatDAO.deleteAllByUserIdCache(userId);
		return list;
	}

//	@Override
//	public List<ChatMsgResponseVO> poll(int userId) {
//		List<ChatMsgResponseVO> list = new ArrayList<>();
//		for (ChatMessage entity : queryByUserId(userId)) {
//			User user = userService.getExistUser(entity.getSendUserId());
//			if (user == null) {
//				continue;
//			}
//
//			ChatMsgResponseVO vo = new ChatMsgResponseVO();
//			UserCapVO userCapVO = MessageFactory.getMessage(UserCapVO.class);
//			userCapVO.init(userTeamDAO.getUserCapCache(userId));
//			vo.init(entity.getType(), entity.getContent(), entity.getCreateTime(), userCapVO);
//			list.add(vo);
//		}
//		return list;
//	}
}
