package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.totem.TotemRule;

/**
 * 神木图腾战魂重塑规则
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月9日
 */
public class TotemRuleDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "TOTEM_RULE_CACHE_KEY";
	
	private static final String TABLE = "t_totem_rule";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(soul_rare,cost_coin, rate_next_rere, rate_self_rare, rate_equal_rare) VALUES(?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET soul_rare=?,cost_coin=?, rate_next_rere=?, rate_self_rare=?, rate_equal_rare=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<TotemRule> ROW_MAPPER = new RowMapper<TotemRule>() {
		@Override
		public TotemRule rowMapper(ResultSet rs) throws SQLException {
			TotemRule obj = new TotemRule();
			obj.setId(rs.getInt("id"));
			obj.setSoulRare(rs.getInt("soul_rare"));
			obj.setCostCoin(rs.getInt("cost_coin"));
			obj.setRateNextRare(rs.getDouble("rate_next_rare"));
			obj.setRateSelfRare(rs.getDouble("rate_self_rare"));
			obj.setRateEqualRare(rs.getDouble("rate_equal_rare"));
			return obj;
		}
	};
	
	
//	public void add(TotemRule entity) {
//		saveOrUpdate(SQL_ADD, entity.getSoulRare(), entity.getCostCoin(), entity.getRateNextRare(), entity.getRateSelfRare(), entity.getRateEqualRare());
//	}
//
//	public void update(TotemRule entity) {
//		saveOrUpdate(SQL_UPDATE, entity.getSoulRare(), entity.getCostCoin(), entity.getRateNextRare(), entity.getRateSelfRare(), entity.getRateEqualRare(), entity.getId());
//	}
//	
//	public void delete(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public List<TotemRule> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}