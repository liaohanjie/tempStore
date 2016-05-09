package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.user.UserBuff;

public class UserBuffDAO extends GameDAOTemplate{
	
	/*private static final String USER_BUFF = "USER_BUFF_";

	private static final String USER_SOUL_KEY = "USER_SOUL_";
	
	private static final String getUserSoulKey(int userId,long id){
		return USER_BUFF+USER_BUFF+SPEET+id;
	}
	private static final String getUserBuffSetKey(int userId){
		return USER_BUFF+userId;
	}*/
	private static final RowMapper<UserBuff> USER_BUFF_ROW_MAPPER = new RowMapper<UserBuff>(){
		@Override
		public UserBuff rowMapper(ResultSet rs) throws SQLException{
			UserBuff obj = new UserBuff();
			obj.setUserId(rs.getInt("user_id"));
			obj.setBuffId(rs.getInt("buff_id"));
			obj.setValue(rs.getInt("value"));
			obj.setEndTime(rs.getTimestamp("end_time"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			return obj;
		}
	};
	public List<UserBuff> getUserBuffs(int userId){
		String sql="select * from t_user_buff where user_id=?";
		return queryForList(sql, USER_BUFF_ROW_MAPPER, userId);
	}
	public UserBuff getUserBuff(int userId,int buffId,int value){
		String sql="select * from t_user_buff where user_id=? and buff_id=? and value=?";
		return queryForEntity(sql, USER_BUFF_ROW_MAPPER, userId,buffId,value);
	}
	public UserBuff getUserBuff(int userId,int buffId){
		String sql="select * from t_user_buff where user_id=? and buff_id=?";
		return queryForEntity(sql, USER_BUFF_ROW_MAPPER, userId,buffId);
	}
	public void addBuff(int usreId,UserBuff buff){
		String sql="insert into t_user_buff(user_id,buff_id,value,end_time,create_time,update_time) values(?,?,?,?,now(),now())";
		saveOrUpdate(sql, buff.getUserId(),buff.getBuffId(),buff.getValue(),buff.getEndTime());
	}
	public void updateUserBuff(int userId,UserBuff buff){
		String sql="update t_user_buff set end_time=?,update_time=now() where user_id=? and buff_id=? and value=?";
		saveOrUpdate(sql, buff.getEndTime(),buff.getUserId(),buff.getBuffId(),buff.getValue());
	}
}
