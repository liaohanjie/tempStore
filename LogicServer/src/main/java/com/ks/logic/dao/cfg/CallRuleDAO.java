package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.soul.CallRule;

/**
 * 战魂召唤分组权重
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月10日
 */
public class CallRuleDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "CALL_RULE_CACHE_KEY";
	
	private static final String TABLE = "t_call_rule";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(type_id, weight, first) VALUES(?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET type_id=?,weight=?,first=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<CallRule> ROW_MAPPER = new RowMapper<CallRule>() {
		@Override
		public CallRule rowMapper(ResultSet rs) throws SQLException {
			CallRule entity = new CallRule();
			entity.setId(rs.getInt("id"));
			entity.setTypeId(rs.getInt("type_id"));
			entity.setWeight(rs.getInt("weight"));
			entity.setFirst(rs.getInt("first"));
			return entity;
		}
	};
	
	
//	public void add(CallRule entity) {
//		saveOrUpdate(SQL_ADD, entity.getWeight());
//	}
//
//	public void update(CallRule entity) {
//		saveOrUpdate(SQL_UPDATE, entity.getWeight(), entity.getId());
//	}
//	
//	public void delete(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public List<CallRule> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
	
	public List<CallRule> queryByTypeId(int typeId) {
		return queryForList(SQL_SELECT + " and type_id=?", ROW_MAPPER, typeId);
	}
}