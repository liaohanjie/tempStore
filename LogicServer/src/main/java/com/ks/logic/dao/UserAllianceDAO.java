package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.alliance.UserAlliance;
/**
 * 个人玩家工会信息DAO
 * @author admin
 *
 */
public class UserAllianceDAO extends GameDAOTemplate{
	private static final String getTableName(){
		return " t_user_alliance ";
	}

	/**
	 * 字段
	 */
	public static final String FIELDS = "userId, allianceId, role, devote, generalBuild, goldBuild, currencyBuild, nextRefreshTime";
	/**
	 * set字段
	 */
	public static final String SET_FIELDS = " allianceId=?, role=?, devote=?, generalBuild=?, goldBuild=?, currencyBuild=?, nextRefreshTime=? ";
	
	private static final RowMapper<UserAlliance> USER_ALLIANCE_ROW_MAPPER = new RowMapper<UserAlliance>(){
		@Override
		public UserAlliance rowMapper(ResultSet rs) throws SQLException{
			UserAlliance obj = new UserAlliance();
			obj.setUserId(rs.getInt("userId"));
			obj.setAllianceId(rs.getInt("allianceId"));
			obj.setNextRefreshTime(rs.getLong("nextRefreshTime"));
			obj.setRole(rs.getInt("role"));
			obj.setDevote(rs.getInt("devote"));
			obj.setGeneralBuild(rs.getInt("generalBuild"));
			obj.setGoldBuild(rs.getInt("goldBuild"));
			obj.setCurrencyBuild(rs.getInt("currencyBuild"));
			return obj;
		}
	};
	
	/**
	 * 获取玩家工会对象
	 * @param userId
	 * @return
	 */
	public UserAlliance getUserAlliance(int userId){
		String sql = "select * from " + getTableName() +" where userId=?";
		return queryForEntity(sql, USER_ALLIANCE_ROW_MAPPER, userId);
	}
	
	/**
	 * 获取并创建
	 * @param userId
	 * @return
	 */
	public UserAlliance getOrCreateUserAlliance(int userId){
		String sql = "select * from " + getTableName() +" where userId=?";
		UserAlliance userAlliance = queryForEntity(sql, USER_ALLIANCE_ROW_MAPPER, userId);
		if(userAlliance == null){
			userAlliance = new UserAlliance();
			userAlliance.init(userId);
			addUserAlliance(userAlliance);
		}
		return userAlliance;
	}
	
	/**
	 * 添加
	 * @param UserAlliance
	 */
	public void addUserAlliance(UserAlliance userAlliance){
		String sql = "insert into" + getTableName() +"("+FIELDS+")" + " values(?,?,?,?,?,?,?,?);";
		saveOrUpdate(sql, userAlliance.getUserId(), userAlliance.getAllianceId(), userAlliance.getRole(), userAlliance.getDevote(), userAlliance.getGeneralBuild(), userAlliance.getGoldBuild(), userAlliance.getCurrencyBuild(), userAlliance.getNextRefreshTime());
	}
	
	/**
	 * 更新
	 * @param UserAlliance
	 */
	public void updateUserAlliance(UserAlliance userAlliance){
		String sql = "update" + getTableName() + "set "+ SET_FIELDS +"where userId=?";
		saveOrUpdate(sql, userAlliance.getAllianceId(), userAlliance.getRole(), userAlliance.getDevote(), userAlliance.getGeneralBuild(),userAlliance.getGoldBuild(), userAlliance.getCurrencyBuild(), userAlliance.getNextRefreshTime(), userAlliance.getUserId());
	}

}
