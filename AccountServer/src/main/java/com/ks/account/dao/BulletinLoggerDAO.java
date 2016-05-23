package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.notice.BulletinLogger;

public class BulletinLoggerDAO extends GameDAOTemplate {

	private static final RowMapper<BulletinLogger> ROW_LOGGER_MAPPER = new RowMapper<BulletinLogger>() {
		@Override
		public BulletinLogger rowMapper(ResultSet rs) throws SQLException {
			BulletinLogger entity = new BulletinLogger();
			entity.setId(rs.getInt("id"));
			entity.setAuthor(rs.getString("author"));
			entity.setContent(rs.getString("content"));
			entity.setServerId(rs.getString("server_id"));
			entity.setCreateTime(rs.getTimestamp("create_time"));
			return entity;
		}
	};

	/**
	 * 保存跑马灯公告日志
	 * 
	 * @param logger
	 */
	public void saveLogger(BulletinLogger logger) {
		String sql = " INSERT INTO  t_bulletin_logger (author,server_id,content,create_time) VALUES(?,?,?,?)";
		saveOrUpdate(sql, logger.getAuthor(), logger.getServerId(), logger.getContent(), logger.getCreateTime());
	}

	/**
	 * 查询日志
	 * 
	 * @return
	 */
	public List<BulletinLogger> getBulletinLogger() {
		String sql = "SELECT * FROM t_bulletin_logger ";
		return queryForList(sql, ROW_LOGGER_MAPPER);
	}

	/**
	 * 得到日志数量
	 * 
	 * @return
	 */
	public Integer getBulletinLoggerCount() {
		String sql = "SELECT * FROM t_bulletin_logger ";
		return queryForEntity(sql, INT_ROW_MAPPER);
	}
}
