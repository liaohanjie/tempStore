package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.friend.Friend;
import com.ks.model.friend.FriendApply;

public class FriendDAO extends GameDAOTemplate {
	
	private static final String getTableName(int userId){
		return "t_friend_"+(userId%10);
	}
	
	private static final RowMapper<Friend> FRIEND_ROW_MAPPER = new RowMapper<Friend>(){
		@Override
		public Friend rowMapper(ResultSet rs) throws SQLException{
			Friend obj = new Friend();
			obj.setUserId(rs.getInt("user_id"));
			obj.setFriendId(rs.getInt("friend_id"));
			obj.setStatus(rs.getInt("status"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			return obj;
		}
	};
	
	private static final RowMapper<FriendApply> APPLY_ROW_MAPPER = new RowMapper<FriendApply>(){
		@Override
		public FriendApply rowMapper(ResultSet rs) throws SQLException{
			FriendApply obj = new FriendApply();
			obj.setUserId(rs.getInt("user_id"));
			obj.setApplyUserId(rs.getInt("apply_user_id"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	public List<FriendApply> queryFriendApplys(int userId){
		String sql = "select * from t_friend_apply where user_id=?";
		return queryForList(sql, APPLY_ROW_MAPPER, userId);
	}
	
	public FriendApply queryFriendApply(int userId,int applyUserId){
		String sql = "select * from t_friend_apply where user_id=? and apply_user_id=?";
		return queryForEntity(sql, APPLY_ROW_MAPPER, userId,applyUserId);
	}
	
	public void addFriendApply(FriendApply fp){
		String sql = "insert into t_friend_apply(user_id,apply_user_id,create_time) values(?,?,now())";
		saveOrUpdate(sql, fp.getUserId(),fp.getApplyUserId());
	}
	
	public void deleteFriendApply(int userId,int applyUserId){
		String sql = "delete from t_friend_apply where user_id=? and apply_user_id=?";
		saveOrUpdate(sql, userId,applyUserId);
	}
	
	public Friend queryFriend(int userId,int friendId){
		String sql = "select * from "+getTableName(userId)+ " where user_id=? and friend_id=?";
		return queryForEntity(sql, FRIEND_ROW_MAPPER, userId,friendId);
	}
	
	public List<Friend> queryFriends(int userId){
		String sql = "select * from "+getTableName(userId)+ " where user_id=?";
		return queryForList(sql, FRIEND_ROW_MAPPER, userId);
	}
	
	public void addFriend(Friend friend){
		String sql = "insert into "+getTableName(friend.getUserId())+ "(user_id,friend_id,status,create_time,update_time) values(?,?,?,now(),now())";
		saveOrUpdate(sql, friend.getUserId(),friend.getFriendId(),friend.getStatus());
	}
	
	public void updateFriend(Friend friend){
		String sql = "update "+getTableName(friend.getUserId())+" set status=? where user_id=? and friend_id=?";
		saveOrUpdate(sql, friend.getStatus(),friend.getUserId(),friend.getFriendId());
	}
	
	public void deleteFriend(int userId,int friendId){
		String sql = "delete from "+getTableName(userId)+ " where user_id=? and friend_id=?";
		saveOrUpdate(sql, userId,friendId);
	}
	
	public int queryFriendCount(int userId){
		String sql = "select count(1) from "+getTableName(userId)+ " where user_id=?";
		return queryForEntity(sql, INT_ROW_MAPPER, userId);
	}
}
