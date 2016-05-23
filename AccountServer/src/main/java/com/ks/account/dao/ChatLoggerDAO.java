package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.chat.ChatMessageLogger;
import com.ks.model.filter.ChatMessaggLogFilter;
/**
 * 聊天日志
 * @author lipp
 * 2016年1月6日
 */
public class ChatLoggerDAO extends GameDAOTemplate {

	private static final RowMapper<ChatMessageLogger> CHAT_MES_LOGGER_ROW_MAPPER = new RowMapper<ChatMessageLogger>() {
		@Override
		public ChatMessageLogger rowMapper(ResultSet rs) throws SQLException {
			ChatMessageLogger obj = new ChatMessageLogger();
			obj.setId(rs.getInt("id"));
			obj.setServerId(rs.getString("server_id"));
			obj.setType(rs.getByte("type"));
			obj.setSendUserId(rs.getInt("send_id"));
			obj.setReceiverId(rs.getInt("received_id"));
			obj.setContent(rs.getString("content"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};

	/**
	 * 保存系统发送记录
	 * 
	 * @param logger
	 */
	public void saveSystemSendLogger(ChatMessageLogger logger) {
		String sql = "insert into t_chat_logger(server_id,type,send_id,received_id,content,create_time) values(?,?,?,?,?,now())";
		saveOrUpdate(sql, logger.getServerId(), logger.getType(), logger.getSendUserId(), logger.getReceiverId(), logger.getContent());
	}

	/**
	 * 查看系统发送日志
	 * 
	 * @param filter
	 * @return
	 */
	public List<ChatMessageLogger> getSystemSendLogger(ChatMessaggLogFilter filter) {
		StringBuffer sql = new StringBuffer("select * from t_chat_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), CHAT_MES_LOGGER_ROW_MAPPER, val.toArray());
	}

	/**
	 * 系统聊天查询FilterSQL
	 * 
	 * @param filter
	 * @param sql
	 * @param val
	 */
	private void excuteFilterSQL(ChatMessaggLogFilter filter, StringBuffer sql, List<Object> val) {
		if (filter.getServerId() != null) {
			sql.append(" and server_id = ?");
			val.add(filter.getServerId());
		}
		if (filter.getType() != null) {
			sql.append(" and type = ? ");
			val.add(filter.getType());
		}
		if (filter.getStartTime() != null) {
			sql.append(" and  create_time >=  ?  ");
			val.add(filter.getStartTime());
		}
		if (filter.getEndTime() != null) {
			sql.append(" and  create_time <= ?  ");
			val.add(filter.getEndTime());
		}
	}

	/**
	 * 得到系统聊天总数量
	 * 
	 * @param filter
	 * @return
	 */
	public Integer getSystemSendLoggerCount(ChatMessaggLogFilter filter) {
		StringBuffer sql = new StringBuffer("select count(1) from t_chat_logger where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}
}
