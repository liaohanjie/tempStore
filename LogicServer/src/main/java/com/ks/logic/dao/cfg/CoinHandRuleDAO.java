package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.coin.CoinHandRule;

/**
 * 点金手DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月11日
 */
public class CoinHandRuleDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "COIN_HAND_RULE_CACHE_KEY";
	
	private static final String TABLE = "t_coin_hand_rule";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(num, coin_hand_id, weight) VALUES(?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET num=?,coin_hand_id=?,weight=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<CoinHandRule> ROW_MAPPER = new RowMapper<CoinHandRule>() {
		@Override
		public CoinHandRule rowMapper(ResultSet rs) throws SQLException {
			CoinHandRule entity = new CoinHandRule();
			entity.setId(rs.getInt("id"));
			entity.setCoinHandId(rs.getInt("coin_hand_id"));
			entity.setTimes(rs.getInt("times"));
			entity.setWeight(rs.getInt("weight"));
			return entity;
		}
	};
	
	
//	public void add(CoinHandRule entity) {
//		saveOrUpdate(SQL_ADD, entity.getCoinHandId(), entity.getTimes(), entity.getWeight());
//	}
//
//	public void update(CoinHandRule entity) {
//		saveOrUpdate(SQL_UPDATE, entity.getCoinHandId(), entity.getTimes(), entity.getWeight(), entity.getId());
//	}
//	
//	public void delete(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public List<CoinHandRule> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}