package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserTeam;
import com.ks.util.JSONUtil;
/**
 * 用户队伍
 * @author ks
 */
public class UserTeamDAO extends GameDAOTemplate {
	
	private static final String getTableName(int userId){
		return "t_user_team_"+userId%10;
	}
	private static final String getUserTeamKey(int userId,int teamId){
		return "USER_TEAM_"+userId+SPEET+teamId;
	}
	public static void main(String[] args) {
		System.out.println(getUserTeamKey(483641024, 5));
		System.out.println(getUserTeamKey(483641024, 5).hashCode()%16);
	}
	public static final String getUserCapKey(int userId){
		return "USER_CAP_"+userId;
	}
	private static final RowMapper<UserTeam> ROW_MAPPER = new RowMapper<UserTeam>() {
		@Override
		public UserTeam rowMapper(ResultSet rs) throws SQLException {
			UserTeam ut = new UserTeam();
			ut.setUserId(rs.getInt("user_id"));
			ut.setTeamId(rs.getByte("team_id"));
			ut.setCap(rs.getByte("cap"));
			ut.setPos(getPos(rs));
			ut.setCreateTime(rs.getTimestamp("create_time"));
			ut.setUpdateTime(rs.getTimestamp("update_time"));
			return ut;
		}

		private List<Long> getPos(ResultSet rs) throws SQLException {
			List<Long> pos = new ArrayList<Long>();
			for(int i=0;i<5;i++){
				pos.add(rs.getLong("place"+(i+1)));
			}
			return pos;
		}
	};
	private static final JedisRowMapper<UserTeam> JEDIS_MAPPER = new JedisRowMapper<UserTeam>(){
		@Override
		public UserTeam rowMapper(JedisResultSet jrs) {
			UserTeam obj = new UserTeam();
			obj.setUserId(jrs.getInt("userId"));
			obj.setTeamId(jrs.getByte("teamId"));
			obj.setCap(jrs.getByte("cap"));
			obj.setPos(getPos(jrs));
			obj.setCreateTime(jrs.getDate("createTime"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			return obj;
		}
		private List<Long> getPos(JedisResultSet jrs) {
			List<Long> pos = new ArrayList<Long>();
			for(int i=0;i<5;i++){
				pos.add(jrs.getLong("place"+(i+1)));
			}
			return pos;
		}
	};
	
	private static final ObjectFieldMap<UserTeam> FIELD_MAP = new ObjectFieldMap<UserTeam>(){
		@Override
		public Map<String, String> objectToMap(UserTeam o){
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId",String.valueOf(o.getUserId()));
			map.put("teamId",String.valueOf(o.getTeamId()));
			map.put("cap",String.valueOf(o.getCap()));
			map.put("place1",String.valueOf(o.getPos().get(0)));
			map.put("place2",String.valueOf(o.getPos().get(1)));
			map.put("place3",String.valueOf(o.getPos().get(2)));
			map.put("place4",String.valueOf(o.getPos().get(3)));
			map.put("place5",String.valueOf(o.getPos().get(4)));
			map.put("createTime",String.valueOf(o.getCreateTime().getTime()));
			map.put("updateTime",String.valueOf(o.getUpdateTime().getTime()));
			return map;
		}
	};
	private static final JedisRowMapper<UserCap> USER_CAP_JEDIS_MAPPER = new JedisRowMapper<UserCap>(){
		@Override
		public UserCap rowMapper(JedisResultSet jrs) {
			UserCap obj = new UserCap();
			obj.setUserId(jrs.getInt("userId"));
			obj.setUserSoulId(jrs.getLong("userSoulId"));
			obj.setSoulId(jrs.getInt("soulId"));
			obj.setLevel(jrs.getInt("level"));
			obj.setUg(jrs.getInt("ug"));
			obj.setExSkillCount(jrs.getInt("exSkillCount"));
			obj.setUserLevel(jrs.getInt("userLevel"));
			obj.setCreateTime(jrs.getDate("createTime"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			obj.setPlayerName(jrs.getString("playerName"));
			obj.setEquipments(JSONUtil.toObject(jrs.getString("equipments", "[]"), new TypeReference<List<Integer>>() {}));
			obj.setWant(JSONUtil.toObject(jrs.getString("want", "[]"), new TypeReference<List<Integer>>() {}));
			obj.setArenaIntegral(jrs.getInt("arenaIntegral"));
			obj.setCurrChapterId(jrs.getInt("currChapterId"));
			return obj;
		}
	};
	private static final ObjectFieldMap<UserCap> USER_CAP_FIELD_MAP = new ObjectFieldMap<UserCap>(){
		@Override
		public Map<String, String> objectToMap(UserCap o){
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId",String.valueOf(o.getUserId()));
			map.put("userSoulId",String.valueOf(o.getUserSoulId()));
			map.put("soulId",String.valueOf(o.getSoulId()));
			map.put("level",String.valueOf(o.getLevel()));
			map.put("ug",String.valueOf(o.getUg()));
			map.put("userLevel", String.valueOf(o.getUserLevel()));
			map.put("exSkillCount",String.valueOf(o.getExSkillCount()));
			map.put("createTime",String.valueOf(o.getCreateTime().getTime()));
			map.put("updateTime",String.valueOf(o.getUpdateTime().getTime()));
			map.put("playerName", o.getPlayerName());
			map.put("equipments", JSONUtil.toJson(o.getEquipments()));
			map.put("want", JSONUtil.toJson(o.getWant()));
			map.put("arenaIntegral", String.valueOf(o.getArenaIntegral()));
			map.put("currChapterId", String.valueOf(o.getCurrChapterId()));
			return map;
		}
	};
	public List<UserTeam> queryUserTeam(int userId){
		String sql = "select * from "+getTableName(userId)+" where user_id=?";
		return queryForList(sql, ROW_MAPPER, userId);
	}
	
	public UserTeam queryUserTeam(int userId,byte teamId){
		String sql = "select * from "+getTableName(userId)+" where user_id=? and team_id=?";
		return queryForEntity(sql, ROW_MAPPER, userId,teamId);
	}
	
	public void updateUserTeam(List<UserTeam> uts){
		if(uts==null || uts.size()==0){
			return;
		}
		String sql = "update "+getTableName(uts.get(0).getUserId())+" set cap=?,place1=?,place2=?,place3=?,place4=?,place5=?,update_time=now() where user_id=? and team_id=?";
		List<Object[]> args = new ArrayList<Object[]>();
		for(UserTeam ut : uts){
			Object[] arg = new Object[]{
					ut.getCap(),ut.getPos().get(0),ut.getPos().get(1),ut.getPos().get(2),ut.getPos().get(3),ut.getPos().get(4),ut.getUserId(),ut.getTeamId()
			};
			args.add(arg);
		}
		executeBatch(sql, args);
	}
	public void addUserTeams(List<UserTeam> uts){
		if(uts==null || uts.size()==0){
			return;
		}
		String sql = "insert into "+getTableName(uts.get(0).getUserId())+"(user_id,team_id,cap,place1,place2,place3,place4,place5,create_time,update_time) values(?,?,?,?,?,?,?,?,now(),now())";
		List<Object[]> args = new ArrayList<Object[]>();
		for(UserTeam ut : uts){
			Object[] arg = new Object[]{
					ut.getUserId(),ut.getTeamId(),ut.getCap(),ut.getPos().get(0),ut.getPos().get(1),ut.getPos().get(2),ut.getPos().get(3),ut.getPos().get(4)
			};
			args.add(arg);
		}
		executeBatch(sql, args);
	}
	
	public void addUserTeamCache(List<UserTeam> uts){
		for(UserTeam ut : uts){
			hmset(getUserTeamKey(ut.getUserId(), ut.getTeamId()), FIELD_MAP.objectToMap(ut));
		}
	}
	public void updateUserTeamCache(UserTeam ut){
		ut.setUpdateTime(new Date());
		hmset(getUserTeamKey(ut.getUserId(), ut.getTeamId()), FIELD_MAP.objectToMap(ut));
	}
	public UserTeam getUserTeamCache(int userId,byte teamId){
		return hgetAll(getUserTeamKey(userId, teamId), JEDIS_MAPPER);
	}
	public List<UserTeam> getUserTeamCache(int userId){
		List<String> keys = new ArrayList<>();
		for(int i=1;i<=UserTeam.TEAM_SIZE;i++){
			keys.add(getUserTeamKey(userId, i));
		}
		return hgetAll(JEDIS_MAPPER, keys);
	}
	public void deleteUserTeamCache(UserTeam ut){
		del(getUserTeamKey(ut.getUserId(), ut.getTeamId()));
	}
	public void updateUserCapCache(UserCap cap){
		hmset(getUserCapKey(cap.getUserId()), USER_CAP_FIELD_MAP.objectToMap(cap));
	}
	public UserCap getUserCapCache(int userId){
		return hgetAll(getUserCapKey(userId), USER_CAP_JEDIS_MAPPER);
	}
	public void deleteUserCapCache(int userId){
		del(getUserCapKey(userId));
	}
	
	public List<UserCap> getUserCapsCache(List<Integer> userIds){
		String[] keys = new String[userIds.size()];
		int i=0;
		for(int userId:userIds){
			keys[i]=getUserCapKey(userId);
			i++;
		}
		return hgetAll(USER_CAP_JEDIS_MAPPER, keys);
	}
}
