package com.ks.account.action;

import java.util.List;

import com.ks.account.service.ChatLoggerService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.ChatLoggerAction;
import com.ks.model.chat.ChatMessageLogger;
import com.ks.model.filter.ChatMessaggLogFilter;

public class ChatLoggerActionImpl implements ChatLoggerAction {
	private static final ChatLoggerService chatLoggerService = ServiceFactory.getService(ChatLoggerService.class);

	@Override
	public List<ChatMessageLogger> getSystemSendLogger(ChatMessaggLogFilter filter) {
		return chatLoggerService.getSystemSendLogger(filter);
	}

	@Override
	public Integer getSystemSendLoggerCount(ChatMessaggLogFilter filter) {
		return chatLoggerService.getSystemSendLoggerCount(filter);
	}

	@Override
	public void saveSystemSendLogger(ChatMessageLogger logger) {
		chatLoggerService.saveSystemSendLogger(logger);
	}

}
