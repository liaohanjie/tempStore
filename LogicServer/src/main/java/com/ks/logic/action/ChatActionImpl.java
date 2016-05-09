package com.ks.logic.action;

import java.util.ArrayList;
import java.util.List;

import com.ks.action.logic.ChatAction;
import com.ks.logic.service.ChatService;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserService;
import com.ks.logic.service.UserTeamService;
import com.ks.model.chat.ChatMessage;
import com.ks.model.user.User;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.chat.ChatMsgRequestVO;
import com.ks.protocol.vo.chat.ChatMsgResponseVO;

public class ChatActionImpl implements ChatAction {

	private static ChatService chatService = ServiceFactory.getService(ChatService.class);

	private static UserService userService = ServiceFactory.getService(UserService.class);

	private static UserTeamService userTeamService = ServiceFactory.getService(UserTeamService.class);

	@Override
	public void send(ChatMsgRequestVO chatMsgRequestVO) {
		ChatMessage entity = new ChatMessage();
		entity.setType(chatMsgRequestVO.getType());
		entity.setSendUserId(chatMsgRequestVO.getSendUserId());
		entity.setReceiverId(chatMsgRequestVO.getReceiverId());
		entity.setContent(chatMsgRequestVO.getContent());
		entity.setCreateTime(System.currentTimeMillis());
		chatService.add(entity);
	}

	@Override
	public List<ChatMsgResponseVO> poll(int userId) {
		List<ChatMsgResponseVO> list = new ArrayList<>();
		for (ChatMessage entity : chatService.queryByUserId(userId)) {
			ChatMsgResponseVO vo = MessageFactory.getMessage(ChatMsgResponseVO.class);

			User user = userService.getExistUser(entity.getSendUserId());
			if (user == null) {
				continue;
			}
			vo.init(entity.getType(), entity.getContent(), user.getTotalCurrency(), entity.getCreateTime(), userTeamService.findUserCapVOCache(entity.getSendUserId()));
			list.add(vo);
		}
		return list;
	}

}
