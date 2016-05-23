package com.ks.account.service;

import java.util.List;
import com.ks.access.Transaction;
import com.ks.model.chat.ChatMessageLogger;
import com.ks.model.filter.ChatMessaggLogFilter;

/**
 * 聊天日志
 * @author lipp
 * 2016年1月6日
 */
public interface ChatLoggerService {
	/**
	 * 查看系统发送日志
	 * 
	 * @return
	 */
	List<ChatMessageLogger> getSystemSendLogger(ChatMessaggLogFilter filter);

	/**
	 * 得到系统日志数量
	 * 
	 * @param filter
	 * @return
	 */
	Integer getSystemSendLoggerCount(ChatMessaggLogFilter filter);

	/**
	 * 保存系统发送记录
	 */
	@Transaction
	void saveSystemSendLogger(ChatMessageLogger logger);
}
