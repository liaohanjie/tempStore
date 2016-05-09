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

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.user.UserSoul;

/**
 * 用户魂将
 * 
 * @author ks
 */

public class UserSoulDAO extends GameDAOTemplate {
	

	private static final RowMapper<UserSoul> ROW_MAPPER = new RowMapper<UserSoul>() {
		@Override
		public UserSoul rowMapper(ResultSet rs) throws SQLException {
			UserSoul us = new UserSoul();
			us.setId(rs.getLong("id"));
			us.setUserId(rs.getInt("user_id"));
			us.setSoulId(rs.getInt("soul_id"));
			us.setSoulSafe(rs.getInt("soul_safe"));
			us.setLevel(rs.getInt("level"));
			us.setExp(rs.getInt("exp"));
			us.setSkillLv(rs.getInt("skill_lv"));
			us.setSoulType(rs.getInt("soul_type"));
			us.setCreateTime(rs.getTime("create_time"));
			us.setUpdateTime(rs.getTimestamp("update_time"));
			return us;
		}
	};
	
	private static final ObjectFieldMap<UserSoul> FIELD_MAP = new ObjectFieldMap<UserSoul>(){
		@Override
		public Map<String, String> objectToMap(UserSoul o){
			Map<String, String> map = new HashMap<String, String>();
			map.put("id",String.valueOf(o.getId()));
			map.put("userId",String.valueOf(o.getUserId()));
			map.put("soulId",String.valueOf(o.getSoulId()));
			map.put("soulSafe",String.valueOf(o.getSoulSafe()));
			map.put("level",String.valueOf(o.getLevel()));
			map.put("exp",String.valueOf(o.getExp()));
			map.put("skillLv",String.valueOf(o.getSkillLv()));
			map.put("soulType",String.valueOf(o.getSoulType()));
			map.put("createTime",String.valueOf(o.getCreateTime().getTime()));
			map.put("updateTime",String.valueOf(o.getUpdateTime().getTime()));
			map.put("update", String.valueOf(o.isUpdate()));
			return map;
		}
	};
	
	private static final JedisRowMapper<UserSoul> JEDIS_MAPPER = new JedisRowMapper<UserSoul>(){
		@Override
		public UserSoul rowMapper(JedisResultSet jrs) {
			UserSoul obj = new UserSoul();
			obj.setId(jrs.getLong("id"));
			obj.setUserId(jrs.getInt("userId"));
			obj.setSoulId(jrs.getInt("soulId"));
			obj.setSoulSafe(jrs.getInt("soulSafe"));
			obj.setLevel(jrs.getInt("level"));
			obj.setExp(jrs.getInt("exp"));
			obj.setSkillLv(jrs.getInt("skillLv"));
			obj.setSoulType(jrs.getInt("soulType"));
			obj.setCreateTime(jrs.getDate("createTime"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			obj.setUpdate(jrs.getBoolean("update", true));
			return obj;
		}
	};
	
private static final String USER_SOUL_KEY = "USER_SOUL_";
	
	/**获取表名*/
	private static String getTableName(int userId) {
		return "t_user_soul_" + (userId % 10);
	}

	/**获取战魂缓存key*/
	private static final String getUserSoulKey(int userId, long id) {
		return USER_SOUL_KEY + userId + SPEET + id;
	}

	/**获取战魂缓存key的set集合*/
	private static final String getUserSoulSetKey(int userId) {
		return USER_SOUL_KEY + userId;
	}

	/**获取玩家的所有战魂 */
	public List<UserSoul> queryUserSoul(int userId) {
		String sql = "SELECT * FROM " + getTableName(userId) + " WHERE user_id=?";
		return queryForList(sql, ROW_MAPPER, userId);
	}
	
	/**获取玩家的单个战魂 */
	public UserSoul queryUserSoul(int userId, long id) {
		String sql = "SELECT * FROM " + getTableName(userId) + " WHERE user_id=? and id=?";
		return queryForEntity(sql, ROW_MAPPER, userId, id);
	}

	/**添加新战魂*/
	public void addUserSoul(UserSoul us) {
		String sql = "INSERT INTO " + getTableName(us.getUserId()) + " (user_id, soul_id," + " soul_safe, level, exp, skill_lv, " + "soul_type,  create_time, update_time)"
				+ " VALUES (?,?,?,?,?,?,?,now(),now());";
		long id = insertAndReturnId(sql, LONG_KEY, us.getUserId(), us.getSoulId(), us.getSoulSafe(), us.getLevel(), us.getExp(), us.getSkillLv(), us.getSoulType());
		us.setId(id);
	}
	
	/**批量更新战魂*/
	public void updateUserSouls(List<UserSoul> userSouls) {
		if (userSouls == null || userSouls.size() == 0) {
			return;
		}
		String sql = "UPDATE " + getTableName(userSouls.get(0).getUserId()) + " SET soul_safe=?,level=?,exp=?,skill_lv=?," + "soul_type=?,soul_id=?," + "update_time=? WHERE user_id=? AND id=?";
		List<Object[]> args = new ArrayList<>();
		for (UserSoul us : userSouls) {
			Object[] arg = new Object[] { us.getSoulSafe(), us.getLevel(), us.getExp(), us.getSkillLv(), us.getSoulType(), us.getSoulId(), us.getUpdateTime(), us.getUserId(), us.getId() };
			args.add(arg);
		}
		executeBatch(sql, args);
	}

	/**删除战魂*/
	public void deleteUserSoul(int userId, long id) {
		String sql = "DELETE FROM " + getTableName(userId) + " WHERE user_id=? AND id=?";
		saveOrUpdate(sql, userId, id);
	}

	/**
	 * 添加战魂到缓存
	 * @param us
	 */
	public void addUserSoulCache(UserSoul us) {
		String key = getUserSoulKey(us.getUserId(), us.getId());
		String setKey = getUserSoulSetKey(us.getUserId());
		// 先添加key的set集合缓存
		sadd(setKey, key);
		// 对象添加到缓存
		hmset(key, FIELD_MAP.objectToMap(us));
	}

	/**
	 * 批量添加战魂到缓存
	 * @param uses
	 */
	public void addUserSoulCache(List<UserSoul> uses) {
		for (UserSoul us : uses) {
			this.addUserSoulCache(us);
		}
	}

	/**
	 * 更新战魂到缓存（所有字段都更新） 
	 * @param us
	 */
	public void updateUserSoulCache(UserSoul us) {
		//设置更新标志位
		us.setUpdate(true);
		us.setUpdateTime(new Date());
		
		String key = getUserSoulKey(us.getUserId(), us.getId());
		hmset(key, FIELD_MAP.objectToMap(us));
	}

	/**
	 * 更新战魂到缓存（只更新部分字段）
	 * @param userId
	 * @param id
	 * @param hash  更新的字段(map<field,value>结构)
	 */
	public void updateUserSoulCache(int userId, long id, Map<String, String> hash) {
		//设置更新标志位
		hash.put("update", "true");
		hash.put("updateTime", String.valueOf(new Date().getTime()));
		
		String key = getUserSoulKey(userId, id);
		hmset(key, hash);
	}

	/**
	 * 从缓存获取战魂数据
	 * @param userId
	 * @param id
	 * @return
	 */
	public UserSoul getUserSoulFromCache(int userId, long id) {
		return hgetAll(getUserSoulKey(userId, id), JEDIS_MAPPER);
	}

	/**
	 * 从缓存批量获取战魂数据
	 * @param userId
	 * @param ids
	 * @return
	 */
	public List<UserSoul> getUserSoulsFromCache(int userId, Collection<Long> ids) {
		List<String> keys = new ArrayList<>();
		for (Long id : ids) {
			keys.add(getUserSoulKey(userId, id));
		}
		return hgetAll(JEDIS_MAPPER, keys);
	}

	/**
	 * 从缓存获取玩家的所有战魂
	 * @param userId
	 * @return
	 */
	public List<UserSoul> getUserSoulListFromCache(int userId) {
		//先获取战魂key集合
		Set<String> keys = smembers(getUserSoulSetKey(userId));
		return hgetAll(JEDIS_MAPPER, keys);
	}

	/**
	 * 获得玩家拥有战魂个数
	 * @param userId
	 * @return 战魂个数
	 */
	public int getUserSoulCount(int userId) {
		return scard(getUserSoulSetKey(userId));
	}

	/**
	 * 清除单个战魂缓存数据
	 * @param userId
	 * @param id
	 */
	public void delUserSoulCache(int userId, long id) {
		//删除战魂缓存
		del(getUserSoulKey(userId, id));
		//删除key集合缓存中的一个元素
		srem(getUserSoulSetKey(userId), getUserSoulKey(userId, id));
	}

	/**
	 * 清除所有战魂缓存数据(并将最新数据更新到数据库)
	 * @param userId
	 */
	public void clearUserSoulCache(int userId) {
		String setKey = getUserSoulSetKey(userId);
		Set<String> keys = smembers(setKey);
		List<UserSoul> souls = hgetAll(JEDIS_MAPPER, keys);
		//需要更新到数据库的战魂
		List<UserSoul> needUpdate = new ArrayList<UserSoul>();
		for (UserSoul s : souls) {
			if (s.isUpdate()) {
				needUpdate.add(s);
			}
		}
		updateUserSouls(needUpdate);
		//清除所有缓存数据
		keys.add(setKey);
		del(keys.toArray(new String[keys.size()]));
	}
}
