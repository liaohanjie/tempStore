package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.achieve.Achieve;
import com.ks.model.achieve.AchieveAward;

public class AchieveCfgDAO extends GameCfgDAOTemplate {

	private static final RowMapper<Achieve> ACHIEVE_ROW_MAPPER = new RowMapper<Achieve>(){
		@Override
		public Achieve rowMapper(ResultSet rs) throws SQLException{
			Achieve obj = new Achieve();
			obj.setAchieveId(rs.getInt("achieve_id"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	
	private static final RowMapper<AchieveAward> ACHIEVE_AWARD_ROW_MAPPER = new RowMapper<AchieveAward>(){
		@Override
		public AchieveAward rowMapper(ResultSet rs) throws SQLException{
			AchieveAward obj = new AchieveAward();
			obj.setAchieveId(rs.getInt("achieve_id"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	
	public List<Achieve> getAchieveRule(){
		String sql="select * from t_achieve order by type,ass_id,num asc";
		return super.queryForList(sql,ACHIEVE_ROW_MAPPER);				
	}	
	public List<AchieveAward> getAllAchieveAward(){
		String sql="select * from t_achieve_award";
		return super.queryForList(sql, ACHIEVE_AWARD_ROW_MAPPER);
	}
}
