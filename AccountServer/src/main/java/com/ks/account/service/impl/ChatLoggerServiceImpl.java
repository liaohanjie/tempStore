package com.ks.account.service.impl;

import java.util.List;

import com.ks.account.service.BaseService;
import com.ks.account.service.ChatLoggerService;
import com.ks.model.chat.ChatMessageLogger;
import com.ks.model.filter.ChatMessaggLogFilter;

public class ChatLoggerServiceImpl extends BaseService implements ChatLoggerService {

	@Override
	public List<ChatMessageLogger> getSystemSendLogger(ChatMessaggLogFilter filter) {
		return chatLoggerDAO.getSystemSendLogger(filter);
	}

	@Override
	public Integer getSystemSendLoggerCount(ChatMessaggLogFilter filter) {
		return chatLoggerDAO.getSystemSendLoggerCount(filter);
	}

	@Override
	public void saveSystemSendLogger(ChatMessageLogger logger) {
		chatLoggerDAO.saveSystemSendLogger(logger);
	}

}
