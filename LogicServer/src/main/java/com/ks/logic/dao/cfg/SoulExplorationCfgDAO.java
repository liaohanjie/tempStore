package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.explora.ExplorationAward;
import com.ks.model.explora.ExplorationAwardExp;

/**
 * 战魂探索
 * 
 * @author fengpeng E-mail:fengpeng_15@163.com
 *
 * @version 创建时间：2014年8月8日 下午4:40:47
 */
public class SoulExplorationCfgDAO extends GameCfgDAOTemplate {
	
	private static RowMapper<ExplorationAward> EXPLORATION_AWARD_MAP = new RowMapper<ExplorationAward>() {
		@Override
		public ExplorationAward rowMapper(ResultSet rs)throws SQLException {
			ExplorationAward pojo = new ExplorationAward();
			pojo.setId(rs.getInt("id"));
			pojo.setType(rs.getInt("type"));
			pojo.setAssId(rs.getInt("ass_id"));
			pojo.setNum(rs.getInt("num"));
			pojo.setHourTime(rs.getInt("hour_time"));
			pojo.setSoulRare(rs.getInt("soul_rare"));
			pojo.setLevel(rs.getInt("level"));
			//pojo.setRate(rs.getDouble("rate"));
			pojo.setWeight(rs.getInt("weight"));
			return pojo;
		}
	};

	private static RowMapper<ExplorationAwardExp> EXPLORATION_AWARD_EXP_MAP = new RowMapper<ExplorationAwardExp>() {
		@Override
		public ExplorationAwardExp rowMapper(ResultSet rs)throws SQLException {
			ExplorationAwardExp pojo = new ExplorationAwardExp();
			pojo.setHourTime(rs.getInt("hour_time"));
			pojo.setSoulRare(rs.getInt("soul_rare"));
			pojo.setExp(rs.getInt("exp"));
			pojo.setGold(rs.getInt("gold"));
			pojo.setAwardNum1(rs.getInt("award_num1"));
			pojo.setRate1(rs.getDouble("rate1"));
			pojo.setAwardNum2(rs.getInt("award_num2"));
			pojo.setRate2(rs.getDouble("rate2"));
			return pojo;
		}
	};
	public List<ExplorationAward> queryExplorationAward() {
		String sql = "select * from t_exploration_award";
		return queryForList(sql, EXPLORATION_AWARD_MAP);
	}
	
	public List<ExplorationAwardExp> queryExplorationAwardExp() {
		String sql = "select * from t_exploration_exp";
		return queryForList(sql, EXPLORATION_AWARD_EXP_MAP);
	}

	
	
	
}
