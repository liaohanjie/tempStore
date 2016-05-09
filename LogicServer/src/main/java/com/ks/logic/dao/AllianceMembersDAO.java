package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import org.codehaus.jackson.type.TypeReference;
import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.alliance.AllianceMembers;
import com.ks.util.JSONUtil;
/**
 * 工会成员管理dao
 * @author hanjie.l
 *
 */
public class AllianceMembersDAO extends GameDAOTemplate{
	
	private static final String getTableName(){
		return " t_alliance_members ";
	}

	/**
	 * 字段
	 */
	public static final String FIELDS = "id, members";
	/**
	 * set字段
	 */
	public static final String SET_FIELDS = " members=? ";
	
	private static final RowMapper<AllianceMembers> ALLIANCE_CONTAINER_ROW_MAPPER = new RowMapper<AllianceMembers>(){
		@Override
		public AllianceMembers rowMapper(ResultSet rs) throws SQLException{
			AllianceMembers obj = new AllianceMembers();
			obj.setId(rs.getInt("id"));
			obj.setMembers(JSONUtil.toObject(rs.getString("members"), new TypeReference<Set<Integer>>(){}));
			return obj;
		}
	};
	
	/**
	 * 获取工会容器
	 * @param id
	 * @return
	 */
	public AllianceMembers getAllianceMembers(int id){
		String sql = "select * from " + getTableName() +" where id=?";
		return queryForEntity(sql, ALLIANCE_CONTAINER_ROW_MAPPER, id);
	}
	
	/**
	 * 添加
	 * @param Alliance
	 */
	public void addAllianceMembers(AllianceMembers members){
		String sql = "insert into" + getTableName() +"("+FIELDS+")" + " values(?,?);";
		saveOrUpdate(sql, members.getId(), JSONUtil.toJson(members.getMembers()));
	}
	
	/**
	 * 删除工会
	 * @param alliance
	 */
	public void deleteAllianceMembers(int allianceId){
		String sql ="delete from " + getTableName() + " where id=?";
		saveOrUpdate(sql, allianceId);
	}
	
	/**
	 * 更新
	 * @param Alliance
	 */
	public void updateAllianceMembers(AllianceMembers members){
		String sql = "update" + getTableName() + "set "+ SET_FIELDS +"where id=?";
		saveOrUpdate(sql, JSONUtil.toJson(members.getMembers()), members.getId());
	}

}
