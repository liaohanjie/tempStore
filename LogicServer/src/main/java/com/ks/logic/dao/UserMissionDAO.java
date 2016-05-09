package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.mission.Condition;
import com.ks.model.mission.MissionAward;
import com.ks.model.mission.UserMission;
import com.ks.util.JSONUtil;

public class UserMissionDAO extends GameDAOTemplate {
	
	
	private static String getTable(int userId){
		return "t_user_mission_"+userId%10;
	}
	
	private static final String USER_MISSION_KEY = "USER_MISSION_";
	
	private static final String getUserMissionKey(int userId,int missionId){
		return USER_MISSION_KEY+userId+SPEET+missionId;
	}
	private static final String getUserMissionSetKey(int userId){
		return USER_MISSION_KEY+userId;
	}
	
	
	private static final RowMapper<UserMission> USER_MISSION_ROW_MAPPER = new RowMapper<UserMission>(){
		@Override
		public UserMission rowMapper(ResultSet rs) throws SQLException{
			UserMission obj = new UserMission();
			obj.setUserId(rs.getInt("user_id"));
			obj.setMissionId(rs.getInt("mission_id"));
			obj.setCondition(JSONUtil.toObject(rs.getString("condition"), new TypeReference<List<Condition>>(){}));
			obj.setAwardList(JSONUtil.toObject(rs.getString("award"),new TypeReference<List<MissionAward>>() {}));
			//obj.setAwardList(GameCache.getMissionAWardList(obj.getMissionId()));
			obj.setState(rs.getInt("state"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
            obj.setUpdateTime(rs.getTimestamp("update_time"));
			return obj;
		}
	};
	private static final ObjectFieldMap<UserMission> FIELD_MAP = new ObjectFieldMap<UserMission>(){
		@Override
		public Map<String, String> objectToMap(UserMission o){
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId",String.valueOf(o.getUserId()));
			map.put("missionId",String.valueOf(o.getMissionId()));
			map.put("condition",JSONUtil.toJson(o.getCondition()));
			map.put("state",String.valueOf(o.getState()));
			map.put("awardList",JSONUtil.toJson(o.getAwardList()));
			map.put("createTime",String.valueOf(o.getCreateTime().getTime()));
			map.put("updateTime",String.valueOf(o.getUpdateTime().getTime()));
			map.put("update", String.valueOf(o.isUpdate()));
			return map;
		}
	};
	private static final JedisRowMapper<UserMission> JEDIS_MAPPER = new JedisRowMapper<UserMission>(){
		@Override
		public UserMission rowMapper(JedisResultSet jrs) {
			UserMission obj = new UserMission();
			obj.setUserId(jrs.getInt("userId"));
			obj.setMissionId(jrs.getInt("missionId"));
			obj.setCondition(JSONUtil.toObject(jrs.getString("condition"), new TypeReference<List<Condition>>(){}));
			obj.setAwardList(JSONUtil.toObject(jrs.getString("awardList"),new TypeReference<List<MissionAward>>() {}));
			//obj.setAwardList(GameCache.getMissionAWardList(obj.getMissionId()));
			obj.setState(jrs.getInt("state"));
			obj.setCreateTime(jrs.getDate("createTime"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			obj.setUpdate(jrs.getBoolean("update"));
			return obj;
		}
	};
	
	
//	public UserMission queryUserMission(int userId,int missionId){
//		String sql="select * from "+getTable(userId)+" where user_id=? and  achieve_type=? and  ass_id=? order by total_num desc limit 1";
//		return super.queryForEntity(sql, USER_ACHIEVE_ROW_MAPPER,userId,type,assId);
//	}
	public List<UserMission> queryUserMissions(int userId){
		String sql="select * from "+getTable(userId)+" where user_id=?";
		return queryForList(sql, USER_MISSION_ROW_MAPPER,userId);
	}
	public void addUserMissionBatch(int userId,List<UserMission> mapList){		
		String sql="insert into "+getTable(userId)+" (user_id,mission_id,`condition`,state,award,create_time,update_time) values(?,?,?,?,?,now(),now())";
		List<Object[]> values=new  ArrayList<Object[]>();
		for(UserMission map:mapList){			
			values.add(new Object[]{map.getUserId(),map.getMissionId(),JSONUtil.toJson(map.getCondition()),map.getState(),JSONUtil.toJson(map.getAwardList())});
		}
		executeBatch(sql, values);
	}
	public void updateUserMissionBatch(int userId,List<UserMission> mapList){		
		String sql="update "+getTable(userId)+" set `condition`=?,`state`=?,award=?,update_time=now() where user_id=? and mission_id=?";
		List<Object[]> values=new  ArrayList<Object[]>();
		for(UserMission map:mapList){			
			values.add(new Object[]{JSONUtil.toJson(map.getCondition()),map.getState(),JSONUtil.toJson(map.getAwardList()),map.getUserId(),map.getMissionId()});
		}
		executeBatch(sql, values);
	}
	public UserMission queryUserMission(int userId,int mission){
		String sql="select * from "+getTable(userId)+" where user_id=? and mission_id=?";
		return queryForEntity(sql,USER_MISSION_ROW_MAPPER,userId,mission);
	}
	
	public void addUserMissionCache(UserMission us){
		String mapKey = getUserMissionKey(us.getUserId(), us.getMissionId());
		String setKey = getUserMissionSetKey(us.getUserId());
		sadd(setKey, mapKey);
		hmset(mapKey, FIELD_MAP.objectToMap(us));
	}
	public void addUserMissionsCache(List<UserMission> uses){
		for(UserMission us : uses){
			this.addUserMissionCache(us);
		}
	}

	public void updateUserMissionCache(UserMission us){
		us.setUpdateTime(new Date());
		us.setUpdate(true);
		String key = getUserMissionKey(us.getUserId(), us.getMissionId());
		hmset(key, FIELD_MAP.objectToMap(us));
	}
	
	public UserMission getMissionFromCache(int userId,int missionId){
		return hgetAll(getUserMissionKey(userId, missionId), JEDIS_MAPPER);
	}
	
	public List<UserMission> getUserMissionsFromCache(int userId,Collection<Integer> ids){
		List<String> keys = new ArrayList<>();
		for(int id : ids){
			keys.add(getUserMissionKey(userId, id));
		}
		return hgetAll(JEDIS_MAPPER, keys);
	}
	
	public List<UserMission> getUserMissionListFromCache(int userId){
		Set<String> keys = smembers(getUserMissionSetKey(userId));
		return hgetAll(JEDIS_MAPPER, keys);
	}
	public void clearUserMissionCache(int userId){
		String setKey = getUserMissionSetKey(userId);
		Set<String> keys = smembers(setKey);
		List<UserMission> souls = hgetAll(JEDIS_MAPPER, keys);
		List<UserMission> hs = new ArrayList<UserMission>();
		for(UserMission s : souls){
			if(s.isUpdate()){
				hs.add(s);
			}
		}
		updateUserMissionBatch(userId, hs);
		keys.add(setKey);
		del(keys.toArray(new String[keys.size()]));
	}
	
}
