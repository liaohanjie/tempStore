package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.mission.Mission;
import com.ks.model.mission.MissionAward;
import com.ks.model.mission.MissionCondition;

/**
 * 
 * @author zck
 *
 */
public class UserMissionCfgDAO extends GameCfgDAOTemplate {

	private static final RowMapper<Mission> MISSION_ROW_MAPPER = new RowMapper<Mission>(){
		@Override
		public Mission rowMapper(ResultSet rs) throws SQLException{
			Mission obj = new Mission();
			obj.setMissionId(rs.getInt("mission_id"));
			obj.setMissionType(rs.getInt("mission_type"));
			obj.setLevel(rs.getInt("level"));;
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	private static final RowMapper<MissionAward> MISSSION_AWARD_ROW_MAPPER = new RowMapper<MissionAward>(){
		@Override
		public MissionAward rowMapper(ResultSet rs) throws SQLException{
			MissionAward obj = new MissionAward();
			obj.setId(rs.getInt("id"));
			obj.setMissionId(rs.getInt("mission_id"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setRate(rs.getDouble("rate"));
			return obj;
		}
	};
	private static final RowMapper<MissionCondition> MISSION_CONDITION_ROW_MAPPER = new RowMapper<MissionCondition>(){
		@Override
		public MissionCondition rowMapper(ResultSet rs) throws SQLException{
			MissionCondition obj = new MissionCondition();
			obj.setId(rs.getInt("id"));
			obj.setMissionId(rs.getInt("mission_id"));
			obj.setType(rs.getInt("type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	public List<MissionAward> getAllMissionAward(){
		String sql="select * from t_mission_award";
		return super.queryForList(sql, MISSSION_AWARD_ROW_MAPPER);
	}
	public List<MissionCondition> getMissionCondition(){
		String sql="select * from t_mission_condition ";
		return super.queryForList(sql,MISSION_CONDITION_ROW_MAPPER);				
	}	
	public List<Mission> getMission(){
		String sql="select * from t_mission ";
		return super.queryForList(sql,MISSION_ROW_MAPPER);				
	}
}
