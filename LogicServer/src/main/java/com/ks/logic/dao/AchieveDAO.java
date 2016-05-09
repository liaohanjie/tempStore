package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.achieve.UserAchieve;

public class AchieveDAO extends GameDAOTemplate {
	
	
	private static String getTable(int userId){
		return "t_user_achieve_"+userId%10;
	}

	private static final RowMapper<UserAchieve> USER_ACHIEVE_ROW_MAPPER = new RowMapper<UserAchieve>(){
		@Override
		public UserAchieve rowMapper(ResultSet rs) throws SQLException{
			UserAchieve obj = new UserAchieve();
			obj.setUserId(rs.getInt("user_id"));
			obj.setAchieveId(rs.getInt("achieve_id"));
			obj.setCurrentNum(rs.getInt("current_num"));
			obj.setTotalNum(rs.getInt("total_num"));
			obj.setAchieveType(rs.getInt("achieve_type"));
			obj.setState(rs.getInt("state"));
			return obj;
		}
	};
	
	public UserAchieve queryUserMaxAchieve(int userId,int type,int assId){
		String sql="select * from "+getTable(userId)+" where user_id=? and  achieve_type=? and  ass_id=? order by total_num desc limit 1";
		return super.queryForEntity(sql, USER_ACHIEVE_ROW_MAPPER,userId,type,assId);
	}
	public List<UserAchieve> queryUserAchieve(int userId){
		String sql="select * from "+getTable(userId)+" where user_id=?";
		return queryForList(sql, USER_ACHIEVE_ROW_MAPPER,userId);
	}
	public void addUserAchieveBatch(int userId,List<UserAchieve> mapList){		
		String sql="insert into "+getTable(userId)+" (user_id,achieve_id,achieve_type,ass_id,state,current_num,total_num,update_time,create_time) values(?,?,?,?,?,?,?,now(),now())";
		List<Object[]> values=new  ArrayList<Object[]>();
		for(UserAchieve map:mapList){			
			values.add(new Object[]{map.getUserId(),map.getAchieveId(),map.getAchieveType(),map.getAssId(),map.getState(),map.getCurrentNum(),map.getTotalNum()});
		}
		executeBatch(sql, values);
	}
	public UserAchieve queryUserAchieve(int userId,int achieveId){
		String sql="select * from "+getTable(userId)+" where user_id=? and achieve_id=?";
		return queryForEntity(sql,USER_ACHIEVE_ROW_MAPPER,userId,achieveId);
	}
	public void addUserAchieve(UserAchieve achieve){
		String sql="insert into "+getTable(achieve.getAchieveId())+" (user_id,achieve_id,state,current_num,total_num,update_time,create_time) values(?,?,?,?,?,now(),now())";
		saveOrUpdate(sql, achieve.getUserId(),achieve.getAchieveId(),achieve.getAchieveType(),achieve.getAssId(),achieve.getState(),achieve.getCurrentNum(),achieve.getTotalNum());
	}	
/*	public void updateUserAchieveState(int userId,int achieveId,int state){
		String sql="update  "+getTable(userId)+" set state=?  where user_id=? and achieve_id=?";
		saveOrUpdate(sql,state,userId,achieveId);
	}*/
	public void updateUserAchieve(UserAchieve userAc){
		String sql="update  "+getTable(userAc.getUserId())+" set state=?,current_num=?  where user_id=? and achieve_id=?";
		saveOrUpdate(sql,userAc.getState(),userAc.getCurrentNum(),userAc.getUserId(),userAc.getAchieveId());
	}
/*	public void addAchievePrcess(int userId,int achieveId,int addNum){
		String sql="update  "+getTable(userId)+" set current_num=current_num+?  where user_id=? and achieve_id=?";
		saveOrUpdate(sql,addNum,userId,achieveId);
	}*/
}
