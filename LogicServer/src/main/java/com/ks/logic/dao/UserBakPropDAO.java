package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.goods.UserBakProp;

public class UserBakPropDAO  extends GameDAOTemplate{

	private static final String USER_BAK_RROP="USER_BAK_RROP_";
	
	private static final String getUserBakPropKey(int userId,int propId){
		return USER_BAK_RROP+userId+SPEET+propId;
	}
	private static final String getUserBakPropSetKey(int userId){
		return USER_BAK_RROP+userId;
	}
	//-----------------------------------------------------------------------------
	private static final RowMapper<UserBakProp> USER_BAK_PROP_ROW_MAPPER = new RowMapper<UserBakProp>(){
			@Override
			public UserBakProp rowMapper(ResultSet rs) throws SQLException{
				UserBakProp obj = new UserBakProp();
				obj.setUserId(rs.getInt("user_id"));
				obj.setBakPropId(rs.getInt("bak_prop_id"));
				obj.setNum(rs.getInt("num"));
				obj.setUpdateTime(rs.getTimestamp("update_time"));
				obj.setCreateTime(rs.getTimestamp("create_time"));
				return obj;
			}
	};
	private static final JedisRowMapper<UserBakProp> JEDIS_MAPPER = new JedisRowMapper<UserBakProp>(){
		@Override
		public UserBakProp rowMapper(JedisResultSet jrs) {
			UserBakProp obj = new UserBakProp();
			obj.setUserId(jrs.getInt("userId"));
			obj.setBakPropId(jrs.getInt("bakPropId"));
			obj.setNum(jrs.getInt("num"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			obj.setCreateTime(jrs.getDate("createTime"));
			return obj;
		}
	};

	private static final ObjectFieldMap<UserBakProp> FIELD_MAP = new ObjectFieldMap<UserBakProp>(){
		@Override
		public Map<String, String> objectToMap(UserBakProp o){
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId",String.valueOf(o.getUserId()));
			map.put("bakPropId",String.valueOf(o.getBakPropId()));
			map.put("num",String.valueOf(o.getNum()));
			map.put("updateTime",String.valueOf(o.getUpdateTime().getTime()));
			map.put("createTime",String.valueOf(o.getCreateTime().getTime()));
			return map;
		}
	};

	public List<UserBakProp> queryUserBakProp(int userId){
		return queryForList("select * from "+getUserBakPropTable(userId)+" where user_id=?", USER_BAK_PROP_ROW_MAPPER, userId);
	}
	private static final String getUserBakPropTable(int userId){
		return "t_user_bak_prop_"+(userId%10);
	}
	public List<UserBakProp> queryUserBakProp(int userId,int id){
		return queryForList("select * from "+getUserBakPropTable(userId)+" where user_id=? and bak_prop_id=?", USER_BAK_PROP_ROW_MAPPER, userId,id);
	}
	public void addBakProp(UserBakProp prop){
		String sql="insert into "+getUserBakPropTable(prop.getUserId())+" (user_id,bak_prop_id,num,create_time,update_time) values(?,?,?,now(),now())";
		saveOrUpdate(sql, prop.getUserId(),prop.getBakPropId(),prop.getNum());	
	}
	public List<UserBakProp> querUserBakPropCache(int userId){
		Set<String> keys = smembers(getUserBakPropSetKey(userId));
		return hgetAll(JEDIS_MAPPER, keys);
	}
	public UserBakProp getUserBakPropCache(int userId,int propId){
		return hgetAll(getUserBakPropKey(userId, propId),JEDIS_MAPPER);
	}
	public void updateUserBakProp(int userId,UserBakProp prop){
		prop.setUpdateTime(new Date());
		String key = getUserBakPropKey(userId,prop.getBakPropId());
		hmset(key, FIELD_MAP.objectToMap(prop));
	}
	
	public void addUserBakPropCache(UserBakProp prop){
		String mapKey = getUserBakPropKey(prop.getUserId(),prop.getBakPropId());
		String setKey = getUserBakPropSetKey(prop.getUserId());
		sadd(setKey, mapKey);
		hmset(mapKey, FIELD_MAP.objectToMap(prop));
	}
	
	public void updateBakProps(int userId,List<UserBakProp> props){
		String sql = "update "+getUserBakPropTable(userId)+" set num=?,update_time=? where user_id=? and bak_prop_id=?";
		List<Object[]> args = new ArrayList<Object[]>();
		for(UserBakProp prop : props){
			Object[] arg = new Object[]{
					prop.getNum(),prop.getUpdateTime(),prop.getUserId(),prop.getBakPropId()
			};
			args.add(arg);
		}
		executeBatch(sql, args);
	}
	
	public void deleteBakPropCache(int userId){
		Set<String> keys = smembers(getUserBakPropSetKey(userId));
		keys.add(getUserBakPropSetKey(userId));
		del(keys.toArray(new String[keys.size()]));
	}
}
