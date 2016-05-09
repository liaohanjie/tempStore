package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.alliance.Alliance;
/**
 * 工会dao
 * @author hanjie.l
 *
 */
public class AllianceDAO extends GameDAOTemplate{
	
	private static final String getTableName(){
		return " t_alliance ";
	}

	/**
	 * 字段
	 */
	public static final String FIELDS = "allianceName, ownerUserId, allianceLevel, notice, descs, devote, todayDevote, nextRefreshTime";
	/**
	 * set字段
	 */
	public static final String SET_FIELDS = " allianceName=?,ownerUserId=?,allianceLevel=?,notice=?,descs=?,devote=?,todayDevote=?,nextRefreshTime=? ";
	
	private static final RowMapper<Alliance> ALLIANCE_ROW_MAPPER = new RowMapper<Alliance>(){
		@Override
		public Alliance rowMapper(ResultSet rs) throws SQLException{
			Alliance obj = new Alliance();
			obj.setId(rs.getInt("id"));
			obj.setAllianceName(rs.getString("allianceName"));
			obj.setOwnerUserId(rs.getInt("ownerUserId"));
			obj.setAllianceLevel(rs.getInt("allianceLevel"));
			obj.setNotice(rs.getString("notice"));
			obj.setDescs(rs.getString("descs"));
			obj.setDevote(rs.getLong("devote"));
			obj.setTodayDevote(rs.getLong("todayDevote"));
			obj.setNextRefreshTime(rs.getLong("nextRefreshTime"));
			return obj;
		}
	};
	
	/**
	 * 获取所有工会
	 * @return
	 */
	public List<Alliance> getAllAlliance(){
		String sql = "select * from " + getTableName();
		return queryForList(sql, ALLIANCE_ROW_MAPPER);
	}
	
	/**
	 * 获取工会
	 * @param id
	 * @return
	 */
	public Alliance getAlliance(int id){
		String sql = "select * from " + getTableName() +" where id=?";
		return queryForEntity(sql, ALLIANCE_ROW_MAPPER, id);
	}
	
	/**
	 * 通过名称获取工会
	 * @param id
	 * @return
	 */
	public Alliance getAllianceByName(String name){
		String sql = "select * from " + getTableName() +" where allianceName=?";
		return queryForEntity(sql, ALLIANCE_ROW_MAPPER, name);
	}
	
	/**
	 * 添加
	 * @param Alliance
	 */
	public int addAlliance(Alliance alliance){
		String sql = "insert into" + getTableName() +"("+FIELDS+")" + " values(?,?,?,?,?,?,?,?);";
		return insertAndReturnId(sql, INT_KEY, alliance.getAllianceName(), alliance.getOwnerUserId(), alliance.getAllianceLevel(), alliance.getNotice(), alliance.getDescs(), alliance.getDevote(), alliance.getTodayDevote(), alliance.getNextRefreshTime());
	}
	
	/**
	 * 删除工会
	 * @param alliance
	 */
	public void deleteAlliance(Alliance alliance){
		String sql ="delete from " + getTableName() + " where id=?";
		saveOrUpdate(sql, alliance.getId());
	}
	
	/**
	 * 更新
	 * @param Alliance
	 */
	public void updateAlliance(Alliance alliance){
		String sql = "update" + getTableName() + "set "+ SET_FIELDS +"where id=?";
		saveOrUpdate(sql, alliance.getAllianceName(), alliance.getOwnerUserId(), alliance.getAllianceLevel(), alliance.getNotice(), alliance.getDescs(),alliance.getDevote(), alliance.getTodayDevote(), alliance.getNextRefreshTime(), alliance.getId());
	}
	
}
