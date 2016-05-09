package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.soul.CallRuleSoul;

/**
 * 战魂召唤战魂分组
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月10日
 */
public class CallRuleSoulDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "CALL_RULE_CACHE_KEY";
	
	private static final String TABLE = "t_call_rule_soul";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(weight) VALUES(?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET weight=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<CallRuleSoul> ROW_MAPPER = new RowMapper<CallRuleSoul>() {
		@Override
		public CallRuleSoul rowMapper(ResultSet rs) throws SQLException {
			CallRuleSoul entity = new CallRuleSoul();
			entity.setId(rs.getInt("id"));
			entity.setCallRuleId(rs.getInt("call_rule_id"));
			entity.setSoulId(rs.getInt("soul_id"));
			entity.setWeight(rs.getInt("weight"));
			return entity;
		}
	};
	
	
//	public void add(CallRuleSoul entity) {
//		saveOrUpdate(SQL_ADD, entity.getWeight());
//	}
//
//	public void update(CallRuleSoul entity) {
//		saveOrUpdate(SQL_UPDATE, entity.getWeight(), entity.getId());
//	}
//	
//	public void delete(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public List<CallRuleSoul> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}