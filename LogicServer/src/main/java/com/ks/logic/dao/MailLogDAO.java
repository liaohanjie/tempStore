package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.affiche.MailLog;

/**
 * 邮件记录
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月15日
 */
public class MailLogDAO extends GameDAOTemplate {

	private static final String TABLE = "t_mail_log";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
//	private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE + " where id=?";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(`user_id`, `mail_id`, `create_time`) VALUES(?,?,?)";
	
//	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET `type`=?, `title`=?, `context`=?, `goods`=?, `user_ids`=?, `logo`=?, `from_time`=?, `end_time`=?, `create_time`=? WHERE id=?";
	
//	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<MailLog> ROW_MAPPER = new RowMapper<MailLog>() {
		@Override
		public MailLog rowMapper(ResultSet rs) throws SQLException {
			MailLog entity = new MailLog();
			entity.setId(rs.getInt("id"));
			entity.setMailId(rs.getInt("mail_id"));
			entity.setUserId(rs.getInt("user_id"));
			entity.setCreateTime(rs.getTimestamp("create_time"));
			return entity;
		}
	};
	
	public void add(MailLog entity) {
		saveOrUpdate(SQL_ADD, entity.getUserId(), entity.getMailId(), entity.getCreateTime());
	}
	
	public MailLog findByUserIdAndMailId(int userId, int mailId) {
		return queryForEntity(SQL_SELECT + " and user_id=? and mail_id=?", ROW_MAPPER, userId, mailId);
	}
}