package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.totem.TotemSoul;

/**
 * 神木图腾战魂重塑规则对应战魂概率
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class TotemSoulDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "TOTEM_SOUL_CACHE_KEY";
	
	private static final String TABLE = "t_totem_soul";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(soul_id,soul_rare, weight) VALUES(?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET soul_id=?,soul_rare=?, weight=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<TotemSoul> ROW_MAPPER = new RowMapper<TotemSoul>() {
		@Override
		public TotemSoul rowMapper(ResultSet rs) throws SQLException {
			TotemSoul obj = new TotemSoul();
			obj.setId(rs.getInt("id"));
			obj.setSoulId(rs.getInt("soul_id"));
			obj.setSoulRare(rs.getInt("soul_rare"));
			obj.setWeight(rs.getInt("weight"));
			return obj;
		}
	};
	
	
//	public void add(TotemSoul entity) {
//		saveOrUpdate(SQL_ADD, entity.getSoulId(), entity.getSoulRare(), entity.getWeight());
//	}
//
//	public void update(TotemSoul entity) {
//		saveOrUpdate(SQL_UPDATE, entity.getSoulId(), entity.getSoulRare(), entity.getWeight(), entity.getId());
//	}
//	
//	public void delete(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public List<TotemSoul> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}