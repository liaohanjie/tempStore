package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.game.Stat;

/**
 * 游戏状态 DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月11日
 */
public class StatDAO extends GameDAOTemplate {

	private static final String TABLE = "t_stat";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_SELECT_BY_ID = SQL_SELECT + " AND id=?";
	
//	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(id, `value`) VALUES(?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET `value`=? WHERE id=?";
	
//	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<Stat> ROW_MAPPER = new RowMapper<Stat>() {
		@Override
		public Stat rowMapper(ResultSet rs) throws SQLException {
			Stat entity = new Stat();
			entity.setId(rs.getInt("id"));
			entity.setValue(rs.getLong("value"));
			return entity;
		}
	};
	
	public void update(Stat entity) {
		saveOrUpdate(SQL_UPDATE, entity.getValue(), entity.getId());
	}
	
	public Stat findById(int id) {
		return queryForEntity(SQL_SELECT_BY_ID, ROW_MAPPER, id);
	}
}