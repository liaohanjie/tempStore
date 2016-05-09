package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.goods.FightProp;

public class FightPropDAO extends GameDAOTemplate {
	
	private static String getTableName(int userId){
		return "t_fight_prop_"+(userId%10);
	}
	
	private static final String FIGHT_PROP_KEY = "FIGHT_PROP_";
	
	private static final String getFightPropKey(int userId,int gridId){
		return FIGHT_PROP_KEY+userId+SPEET+gridId;
	}
	private static final String getFightPropSetKey(int userId){
		return FIGHT_PROP_KEY+userId;
	}
	
	private static final JedisRowMapper<FightProp> JEDIS_MAPPER = new JedisRowMapper<FightProp>(){
		@Override
		public FightProp rowMapper(JedisResultSet jrs) {
			FightProp obj = new FightProp();
			obj.setGridId(jrs.getInt("gridId"));
			obj.setUserId(jrs.getInt("userId"));
			obj.setPropId(jrs.getInt("propId"));
			obj.setNum(jrs.getInt("num"));
			obj.setCreateTime(jrs.getDate("createTime"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			return obj;
		}
	};
	
	private static final ObjectFieldMap<FightProp> FIELD_MAP = new ObjectFieldMap<FightProp>(){
		@Override
		public Map<String, String> objectToMap(FightProp o){
			Map<String, String> map = new HashMap<String, String>();
			map.put("gridId",String.valueOf(o.getGridId()));
			map.put("userId",String.valueOf(o.getUserId()));
			map.put("propId",String.valueOf(o.getPropId()));
			map.put("num",String.valueOf(o.getNum()));
			map.put("createTime",String.valueOf(o.getCreateTime().getTime()));
			map.put("updateTime",String.valueOf(o.getUpdateTime().getTime()));
			return map;
		}
	};
	
	private static final RowMapper<FightProp> ROW_MAPPER = new RowMapper<FightProp>(){
		@Override
		public FightProp rowMapper(ResultSet rs) throws SQLException{
			FightProp obj = new FightProp();
			obj.setGridId(rs.getInt("grid_id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setPropId(rs.getInt("prop_id"));
			obj.setNum(rs.getInt("num"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			return obj;
		}
	};
	
	public List<FightProp> queryFightProp(int userId){
		String sql = "select * from "+getTableName(userId)+" where user_id=?";
		return queryForList(sql, ROW_MAPPER, userId);
	}
	
	public void updateFightProps(List<FightProp> props){
		String sql = "update "+getTableName(props.get(0).getUserId())+" set prop_id=?,num=?,update_time=? where user_id=? and grid_id=?";
		List<Object[]> args = new ArrayList<Object[]>();
		for(FightProp prop : props){
			Object[] arg = new Object[]{
					prop.getPropId(),prop.getNum(),prop.getUpdateTime(),prop.getUserId(),prop.getGridId()
			};
			args.add(arg);
		}
		executeBatch(sql, args);
	}
	
	public void addFightProps(List<FightProp> props){
		String sql = "INSERT INTO "+getTableName(props.get(0).getUserId())+"(user_id,grid_id,prop_id,num,create_time,update_time) values(?,?,?,?,now(),now())";
		List<Object[]> args = new ArrayList<Object[]>();
		for(FightProp prop : props){
			Object[] arg = new Object[]{
					prop.getUserId(),prop.getGridId(),prop.getPropId(),prop.getNum()
			};
			args.add(arg);
		}
		executeBatch(sql, args);
	}
	
	public void addFightPropCache(FightProp prop){
		String mapKey = getFightPropKey(prop.getUserId(), prop.getGridId());
		String setKey = getFightPropSetKey(prop.getUserId());
		sadd(setKey, mapKey);
		hmset(mapKey, FIELD_MAP.objectToMap(prop));
	}
	
	public void addFightPropsCache(List<FightProp> props){
		for(FightProp prop : props){
			addFightPropCache(prop);
		}
	}
	
	public FightProp geFightPropFromCache(int userId,int gridId){
		return hgetAll(getFightPropKey(userId, gridId), JEDIS_MAPPER);
	}
	
	public List<FightProp> getFightPropsFromCache(int userId,Collection<Integer> gridIds){
		List<String> keys = new ArrayList<>();
		for(Integer gridId : gridIds){
			keys.add(getFightPropKey(userId, gridId));
		}
		return hgetAll(JEDIS_MAPPER, keys);
	}
	
	public List<FightProp> getFightPropListFromCache(int userId){
		Set<String> keys = smembers(getFightPropSetKey(userId));
		return hgetAll(JEDIS_MAPPER, keys);
	}
	
	public void updateFightPropCache(FightProp prop){
		hmset(getFightPropKey(prop.getUserId(), prop.getGridId()), FIELD_MAP.objectToMap(prop));
	}
	
	public void clearFightPropCache(int userId){
		Set<String> keys = smembers(getFightPropSetKey(userId));
		del(keys.toArray(new String[keys.size()]));
		del(getFightPropSetKey(userId));
	}
}

