package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.user.UserSoulMap;

public class UserSoulMapDAO extends GameDAOTemplate {
	
	private static String getTableName(int userId){
		return "t_user_soul_map_"+userId%10;
	}
	
	private static final RowMapper<UserSoulMap> USER_SOUL_MAP_ROW_MAPPER = new RowMapper<UserSoulMap>() {
		@Override
		public UserSoulMap rowMapper(ResultSet rs) throws SQLException {
			UserSoulMap obj = new UserSoulMap();
			obj.setUserId(rs.getInt("user_id"));
			obj.setSoulId(rs.getInt("soul_id"));
			obj.setState(rs.getInt("state"));
			return obj;
		}
	};

	public List<UserSoulMap> getUserMapSouls(int userId){
		String sql="select * from "+getTableName(userId)+" where user_id=?";
		return queryForList(sql, USER_SOUL_MAP_ROW_MAPPER, userId);
	}
	
	public List<UserSoulMap> getUserMapSoulsState(int userId,int state){
		String sql="select * from "+getTableName(userId)+" where user_id=? and state=?";
		return queryForList(sql, USER_SOUL_MAP_ROW_MAPPER, userId,state);
	}
	public List<UserSoulMap> getUserMapSouls(int userId,Collection<Integer> ids){
		List<Object> param=new ArrayList<Object>();
		param.add(userId);
		
		StringBuffer buff=new StringBuffer("select * from "+getTableName(userId)+" where user_id=? ");
		buff.append(" and soul_id in(");
		int i=0;
		for(Integer value:ids){
			if(i==0){
				buff.append("?");
			}else{
				buff.append(",?");
			}
			param.add(value);
			i++;
		}
		buff.append(")");
		return queryForList(buff.toString(), USER_SOUL_MAP_ROW_MAPPER,param.toArray());
	}
	
	public UserSoulMap getUserMapSoul(int userId,int soulId){
		String sql="select * from "+getTableName(userId)+" where user_id=? and soul_id=?";
		return queryForEntity(sql, USER_SOUL_MAP_ROW_MAPPER, userId,soulId);
	}
	
	public void updateUserMapSoul(int userId,int soulId,int state){
		String sql="update "+getTableName(userId)+" set state=? where user_id=? and soul_id=? ";
		saveOrUpdate(sql, state,userId,soulId);
	}	
	public void addUserMapSoul(UserSoulMap map){		
		String sql="insert into "+getTableName(map.getUserId())+" (user_id,soul_id,state,create_time,update_time) values(?,?,?,now(),now())";
		saveOrUpdate(sql, map.getUserId(),map.getSoulId(),map.getState());
	}
	public void addUserMapSoulBatch(int userId,List<UserSoulMap> mapList){		
		String sql="insert into "+getTableName(userId)+" (user_id,soul_id,state,create_time,update_time) values(?,?,?,now(),now())";
		List<Object[]> values=new  ArrayList<Object[]>();
		for(UserSoulMap map:mapList){			
			values.add(new Object[]{map.getUserId(),map.getSoulId(),map.getState()});
		}
		executeBatch(sql, values);
	}
}
