package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.explora.SoulExploretion;

/**
 * 战魂探索
 * 
 * @author fengpeng E-mail:fengpeng_15@163.com
 *
 * @version 创建时间：2014年8月8日 下午4:40:47
 */
public class SoulExplorationDAO extends GameDAOTemplate {
	private static String getTableName(int userId) {
		return "t_soul_exploretion_" + (userId % 10);
	}

	private static RowMapper<SoulExploretion> SOUL_EXPLORETION_MAP = new RowMapper<SoulExploretion>() {
		@Override
		public SoulExploretion rowMapper(ResultSet rs)throws SQLException {
			SoulExploretion pojo = new SoulExploretion();
			pojo.setId(rs.getInt("id"));
			pojo.setUserId(rs.getInt("user_id"));
			pojo.setSoulId(rs.getLong("soul_id"));
			pojo.setSoulRare(rs.getInt("soul_rare"));
			pojo.setHourTime(rs.getInt("hour_time"));
			pojo.setState(rs.getInt("state"));
			pojo.setTeamId(rs.getInt("team_id"));
			pojo.setStartTime(rs.getTimestamp("start_time"));
			pojo.setEndTime(rs.getTimestamp("end_time"));
			return pojo;
		}
	};
	public void addSoulExploretion(SoulExploretion se) {
		String sql = "INSERT INTO "
				+ getTableName(se.getUserId())
				+ " (user_id, soul_id,"
				+ " soul_rare,hour_time,state,start_time,end_time,team_id)"
				+ " VALUES (?,?,?,?,?,?,now(),?);";
		saveOrUpdate(sql, se.getUserId(),se.getSoulId(),se.getSoulRare(),
				se.getHourTime(),se.getState(),se.getStartTime(),se.getTeamId());
	}
	
	

	public void updateSoulExploretion(int userId,long soulId){
		String sql="update  "+getTableName(userId)+" set state=1,end_time=now()  where user_id=? and soul_id=?";
		saveOrUpdate(sql,userId,soulId);
	}

	public List<SoulExploretion> querSoulExploretionList(int userId) {
		String sql = "select * from "
				+ getTableName(userId)
				+ " where state=0 and user_id=?";
		return queryForList(sql, SOUL_EXPLORETION_MAP, userId);
	}
	
	public SoulExploretion querSoulExploretion(int userId,long soulId) {
		String sql = "select * from "
				+ getTableName(userId)
				+ " where state=0 and user_id=? and soul_id=?";
		return queryForEntity(sql, SOUL_EXPLORETION_MAP,userId,soulId);
	}
	
}
