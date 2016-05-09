package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.pvp.AthleticsInfoAward;

/**
 * 
 * @author zck
 *
 */
public class AthleticsInfoCfgDAO extends GameCfgDAOTemplate {
	
	private static final RowMapper<AthleticsInfoAward> ATHLETICS_AWARD_ROW_MAPPER = new RowMapper<AthleticsInfoAward>(){
		@Override
		public AthleticsInfoAward rowMapper(ResultSet rs) throws SQLException{
			AthleticsInfoAward obj = new AthleticsInfoAward();
			obj.setId(rs.getInt("id"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	
	public List<AthleticsInfoAward> getAthleticsNameAward(){
		String sql="select * from t_athleticsinfo_award";
		return queryForList(sql, ATHLETICS_AWARD_ROW_MAPPER);
	}
}
