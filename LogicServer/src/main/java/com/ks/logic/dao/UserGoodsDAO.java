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
import com.ks.model.goods.UserGoods;
/**
 * 用户物品
 * @author ks
 */
public class UserGoodsDAO extends GameDAOTemplate {
	
	private static final String getTableName(int userId){
		return "t_user_goods_"+(userId%10);
	}
	
	private static final String USER_GOODS_KEY = "USER_GOODS_";
	
	private static final String getUserGoodsKey(int userId,int gridId){
		return USER_GOODS_KEY+userId+SPEET+gridId;
	}
	private static final String getUserGoodsSetKey(int userId){
		return USER_GOODS_KEY+userId;
	}
	private static final JedisRowMapper<UserGoods> JEDIS_MAPPER = new JedisRowMapper<UserGoods>(){
		@Override
		public UserGoods rowMapper(JedisResultSet jrs) {
			UserGoods obj = new UserGoods();
			obj.setGridId(jrs.getInt("gridId"));
			obj.setUserId(jrs.getInt("userId"));
			obj.setGoodsType(jrs.getInt("goodsType"));
			obj.setGoodsId(jrs.getInt("goodsId"));
			obj.setNum(jrs.getInt("num"));
			obj.setDurable(jrs.getInt("durable"));
			obj.setUserSoulId(jrs.getLong("userSoulId"));
			obj.setCreateTime(jrs.getDate("createTime"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			obj.setEqSkillId(jrs.getInt("eqSkillId"));
			obj.setEqSkillLevel(jrs.getInt("eqSkillLevel", 1));
			obj.setUpdate(jrs.getBoolean("update",true));
			return obj;
		}
	};
	
	private static final ObjectFieldMap<UserGoods> FIELD_MAP = new ObjectFieldMap<UserGoods>(){
		@Override
		public Map<String, String> objectToMap(UserGoods o){
			Map<String, String> map = new HashMap<String, String>();
			map.put("gridId",String.valueOf(o.getGridId()));
			map.put("userId",String.valueOf(o.getUserId()));
			map.put("goodsType",String.valueOf(o.getGoodsType()));
			map.put("goodsId",String.valueOf(o.getGoodsId()));
			map.put("num",String.valueOf(o.getNum()));
			map.put("durable",String.valueOf(o.getDurable()));
			map.put("userSoulId",String.valueOf(o.getUserSoulId()));
			map.put("createTime",String.valueOf(o.getCreateTime().getTime()));
			map.put("updateTime",String.valueOf(o.getUpdateTime().getTime()));
			map.put("eqSkillId", String.valueOf(o.getEqSkillId()));
			map.put("eqSkillLevel", String.valueOf(o.getEqSkillLevel()));
			map.put("update", String.valueOf(o.isUpdate()));
			return map;
		}
	};
	
	private static final RowMapper<UserGoods> ROW_MAPPER = new RowMapper<UserGoods>(){
		@Override
		public UserGoods rowMapper(ResultSet rs) throws SQLException{
			UserGoods obj = new UserGoods();
			obj.setGridId(rs.getInt("grid_id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setGoodsType(rs.getInt("goods_type"));
			obj.setGoodsId(rs.getInt("goods_id"));
			obj.setNum(rs.getInt("num"));
			obj.setDurable(rs.getInt("durable"));
			obj.setUserSoulId(rs.getLong("user_soul_id"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			obj.setEqSkillId(rs.getInt("eq_skill_id"));
			obj.setEqSkillLevel(rs.getInt("eq_skill_level"));
			return obj;
		}
	};
	
	public List<UserGoods> queryUserGoods(int userId){
		return queryForList("select * from "+getTableName(userId)+" where user_id=?", ROW_MAPPER, userId);
	}
	
	public void addUserGoods(UserGoods userGoods){
		String sql = "insert into "+getTableName(userGoods.getUserId())+"(user_id,goods_type,goods_id,num,durable,user_soul_id,create_time,update_time) values(?,?,?,?,?,?,now(),now())";
		int gridId = insertAndReturnId(sql, INT_KEY, 
				userGoods.getUserId(),
				userGoods.getGoodsType(),
				userGoods.getGoodsId(),
				userGoods.getNum(),
				userGoods.getDurable(),
				userGoods.getUserSoulId());
		userGoods.setGridId(gridId);
	}
	
	public void updateUserGoodses(List<UserGoods> userGoodses){
		if(userGoodses==null||userGoodses.size()==0){
			return;
		}
		String sql = "update "+getTableName(userGoodses.get(0).getUserId()) + " set goods_type=?,goods_id=?,num=?,durable=?,user_soul_id=?,eq_skill_id=?,eq_skill_level=?,update_time=? where grid_id=?";
		List<Object[]> args = new ArrayList<>();
		for(UserGoods ug : userGoodses){
			Object[] arg = new Object[]{
					ug.getGoodsType(),ug.getGoodsId(),ug.getNum(),ug.getDurable(),ug.getUserSoulId(),ug.getEqSkillId(),ug.getEqSkillLevel(),ug.getUpdateTime(),ug.getGridId()
			};
			args.add(arg);
		}
		executeBatch(sql, args);
	}
	
	public void addUserGoodsCache(UserGoods userGoods){
		String mapKey = getUserGoodsKey(userGoods.getUserId(), userGoods.getGridId());
		String setKey = getUserGoodsSetKey(userGoods.getUserId());
		sadd(setKey, mapKey);
		hmset(mapKey, FIELD_MAP.objectToMap(userGoods));
	}
	
	public void updateUserGoodsCache(UserGoods userGoods){
		userGoods.setUpdateTime(new Date());
		userGoods.setUpdate(true);
		hmset(getUserGoodsKey(userGoods.getUserId(), userGoods.getGridId()), FIELD_MAP.objectToMap(userGoods));
	}
	
	public UserGoods getUserGoodsCache(int userId,int gridId){
		return hgetAll(getUserGoodsKey(userId,gridId),JEDIS_MAPPER);
	}
	
	public List<UserGoods> getUserGoodsesCache(int userId){
		Set<String> keys = smembers(getUserGoodsSetKey(userId));
		return hgetAll(JEDIS_MAPPER, keys);
	}
	public void clearUserGoodsCache(int userId){
		List<UserGoods> ugs = getUserGoodsesCache(userId);
		List<UserGoods> uus = new ArrayList<UserGoods>();
		for(UserGoods ug : ugs){
			if(ug.isUpdate()){
				uus.add(ug);
			}
		}
		updateUserGoodses(uus);
		
		Set<String> keys = smembers(getUserGoodsSetKey(userId));
		keys.add(getUserGoodsSetKey(userId));
		del(keys.toArray(new String[keys.size()]));
	}
	
}