package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.notice.Notice;

/**
 * 游戏公告
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月3日
 */
public class NoticeDAO extends GameDAOTemplate {

	private static final String TABLE = "t_notice";

	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";

	// private static final String SQL_SELECT_BY_ID = SQL_SELECT + " AND id=?";

	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(`content`, `color`, `interval`, from_time, end_time, create_time) VALUES(?,?,?,?,?,?)";

	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET `content`=?, `color`=?, `interval`=?, from_time=?, end_time=?, create_time=? WHERE id=?";

	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<Notice> ROW_MAPPER = new RowMapper<Notice>() {
		@Override
		public Notice rowMapper(ResultSet rs) throws SQLException {
			Notice entity = new Notice();
			entity.setId(rs.getInt("id"));
			entity.setContent(rs.getString("content"));
			entity.setColor(rs.getString("color"));
			entity.setFromTime(rs.getTimestamp("from_time"));
			entity.setEndTime(rs.getTimestamp("end_time"));
			entity.setCreateTime(rs.getTimestamp("create_time"));
			entity.setInterval(rs.getInt("interval"));
			return entity;
		}
	};

	public void queryAll() {
		queryForList(SQL_SELECT, ROW_MAPPER);
	}

	/**
	 * 查询当前时间有效的公告
	 */
	public List<Notice> queryAllInTime() {
		String sql = SQL_SELECT + " and from_time < now() and end_time > now()" + " order by from_time desc";
		return queryForList(sql, ROW_MAPPER);
	}

	public void add(Notice entity) {
		saveOrUpdate(SQL_ADD, entity.getContent(), entity.getColor(), entity.getInterval(), entity.getFromTime(), entity.getEndTime(), entity.getCreateTime());
	}

	public void update(Notice entity) {
		saveOrUpdate(SQL_UPDATE, entity.getContent(), entity.getColor(), entity.getInterval(), entity.getFromTime(), entity.getEndTime(),
		        entity.getCreateTime(), entity.getId());
	}

	public void delete(int id) {
		saveOrUpdate(SQL_DELETE, id);
	}

	public List<Notice> query() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}

}