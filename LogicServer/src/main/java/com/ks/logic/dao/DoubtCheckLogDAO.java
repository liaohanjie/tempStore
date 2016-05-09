package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.check.UserDoubtCheckLog;
/**
 * 可疑日志记录
 * @author hanjie.l
 *
 */
public class DoubtCheckLogDAO extends GameDAOTemplate {
	
	private static final String TABLE = "t_user_doubtcheck_log";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where userId=?";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(userId, commiter, clientData, serverData, createTime, reason) VALUES(?,?,?,?,?,?)";
	
	private static final RowMapper<UserDoubtCheckLog> DOUBTCHECK_LOG_ROW_MAPPER = new RowMapper<UserDoubtCheckLog>() {
		@Override
		public UserDoubtCheckLog rowMapper(ResultSet rs) throws SQLException {
			UserDoubtCheckLog log = new UserDoubtCheckLog();
			log.setId(rs.getInt("id"));
			log.setUserId(rs.getInt("userId"));
			log.setClientData(rs.getString("clientData"));
			log.setServerData(rs.getString("serverData"));
			log.setCreateTime(rs.getDate("createTime"));
			log.setReason(rs.getString("reason"));
			return log;
		}
	};
	
	/**
	 * 插入
	 * @param check
	 */
	public void addDoubtLog(UserDoubtCheckLog log){
		saveOrUpdate(SQL_ADD, log.getUserId(), log.getCommiter(), log.getClientData(), log.getServerData(), log.getCreateTime(), log.getReason());
	}
	
	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	public List<UserDoubtCheckLog> getDoubtLog(int userId){
		return queryForList(SQL_SELECT, DOUBTCHECK_LOG_ROW_MAPPER, userId);
	}
	
	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	public UserDoubtCheckLog getDoubtLogById(int id){
		String sql = "SELECT * FROM " + TABLE + " where id=?";
		return queryForEntity(sql, DOUBTCHECK_LOG_ROW_MAPPER, id);
	}

}
