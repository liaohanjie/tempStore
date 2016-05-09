package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.coin.CoinHand;

/**
 * 点金手DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月11日
 */
public class CoinHandDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "COIN_HAND_CACHE_KEY";
	
	private static final String TABLE = "t_coin_hand";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
//	
//	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
//	
//	private static final String SQL_ADD = "INSERT into " + TABLE + "(num, base_gold) VALUES(?,?,?)";
//	
//	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET num=?,base_gold=? WHERE id=?";
//	
//	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<CoinHand> ROW_MAPPER = new RowMapper<CoinHand>() {
		@Override
		public CoinHand rowMapper(ResultSet rs) throws SQLException {
			CoinHand entity = new CoinHand();
			entity.setId(rs.getInt("id"));
			entity.setNum(rs.getInt("num"));
			entity.setBaseGold(rs.getInt("base_gold"));
			entity.setPrice(rs.getInt("price"));
			return entity;
		}
	};
	
	public List<CoinHand> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}