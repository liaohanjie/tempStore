package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.buding.Buding;
import com.ks.model.buding.BudingDrop;
import com.ks.model.buding.BudingRule;
/**
 * 
 * @author ks
 */
public class BudingDAO extends GameCfgDAOTemplate {
	
	private static final RowMapper<Buding> BUDING_ROW_MAPPER = new RowMapper<Buding>(){
		@Override
		public Buding rowMapper(ResultSet rs) throws SQLException{
			Buding obj = new Buding();
			obj.setBudingId(rs.getInt("buding_id"));
			obj.setName(rs.getString("name"));
			obj.setMaxLevel(rs.getInt("max_level"));
			obj.setCollect(rs.getBoolean("collect"));
			return obj;
		}
	};
	private static final RowMapper<BudingRule> BUDING_RULE_ROW_MAPPER = new RowMapper<BudingRule>(){
		@Override
		public BudingRule rowMapper(ResultSet rs) throws SQLException{
			BudingRule obj = new BudingRule();
			obj.setBudingId(rs.getInt("buding_id"));
			obj.setLevel(rs.getInt("level"));
			obj.setGold(rs.getInt("gold"));
			obj.setTime(rs.getInt("time"));
			obj.setCount(rs.getInt("count"));
			return obj;
		}
	};
	private static final RowMapper<BudingDrop> BUDING_DROP_ROW_MAPPER = new RowMapper<BudingDrop>(){
		@Override
		public BudingDrop rowMapper(ResultSet rs) throws SQLException{
			BudingDrop obj = new BudingDrop();
			obj.setId(rs.getInt("id"));
			obj.setBudingId(rs.getInt("buding_id"));
			obj.setLevel(rs.getInt("level"));
			obj.setStuffId(rs.getInt("stuff_id"));
			obj.setNum(rs.getInt("num"));
			obj.setRate(rs.getInt("rate"));
			return obj;
		}
	};
	
	
	public List<Buding> queryAllBuding(){
		return queryForList("select * from t_buding", BUDING_ROW_MAPPER);
	}
	
	public List<BudingRule> queryAllBudingRule(){
		return queryForList("select * from t_buding_rule", BUDING_RULE_ROW_MAPPER);
	}
	
	public List<BudingDrop> queryAllBudingDrop(){
		return queryForList("select * from t_buding_drop", BUDING_DROP_ROW_MAPPER);
	}
}
