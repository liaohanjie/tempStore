package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.activity.ActivityGift;

/**
 * 活动礼包 DAO
 * 
 * @author zhoujf
 * @date 2015年7月6日
 */
public class ActivityGiftCfgDAO extends GameCfgDAOTemplate {

	public static final String ACTIVITY_GIFT_KEY = "ACTIVITY_GIFT_CACHE_KEY";
	
	private static final String TABLE = "t_activity_gift";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
//	private static final String SQL_ADD = "INSERT into " + TABLE + "(activityDefineId, name, key, gift,activity_gift_desc) VALUES(?,?,?,?,?)";
	
//	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET activityDefineId=?, name=?, key=?, gift=?,activity_gift_desc=? WHERE id=?";
	
//	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ActivityGift> ROW_MAPPER = new RowMapper<ActivityGift>() {
		@Override
		public ActivityGift rowMapper(ResultSet rs) throws SQLException {
			ActivityGift obj = new ActivityGift();
			obj.setId(rs.getInt("id"));
			obj.setActivityDefineId(rs.getInt("activity_define_id"));
			obj.setName(rs.getString("name"));
			obj.setKey1(rs.getString("key"));
			obj.setKey2(rs.getString("key2"));
			obj.setGift(rs.getString("gift"));
			obj.setActivityGiftDesc(rs.getString("activity_gift_desc"));
			return obj;
		}
	};
	
//	private static final ObjectFieldMap<ActivityGift> FIELD_MAP = new ObjectFieldMap<ActivityGift>() {
//		@Override
//		public Map<String, String> objectToMap(ActivityGift o) {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("id", String.valueOf(o.getId()));
//			map.put("activityDefineId", String.valueOf(o.getActivityDefineId()));
//			map.put("name", o.getName());
//			map.put("key1", o.getKey1());
//			map.put("key2", o.getKey2());
//			map.put("gift", o.getGift());
//			map.put("activityGiftDesc", o.getActivityGiftDesc());
//			return map;
//		}
//	};
//	
//	private static final JedisRowMapper<ActivityGift> JEDIS_ROW_MAPPER = new JedisRowMapper<ActivityGift>() {
//		@Override
//		public ActivityGift rowMapper(JedisResultSet jrs) {
//			ActivityGift obj = new ActivityGift();
//			obj.setId(jrs.getInt("id"));
//			obj.setActivityDefineId(jrs.getInt("activityDefineId"));
//			obj.setName(jrs.getString("name"));
//			obj.setKey1(jrs.getString("key1"));
//			obj.setKey2(jrs.getString("key2"));
//			obj.setGift(jrs.getString("gift"));
//			obj.setActivityGiftDesc(jrs.getString("activityGiftDesc"));
//			return obj;
//		}
//	};
	
//	public void addActivityGift(ActivityGift entity) {
//		saveOrUpdate(SQL_ADD, entity.getActivityDefineId(), entity.getName(), entity.getKey(), entity.getGift(), entity.getActivityGiftDesc());
//	}
//
//	public void updateActivityGift(ActivityGift entity) {
//		saveOrUpdate(SQL_UPDATE, entity.getActivityDefineId(), entity.getName(), entity.getKey(), entity.getGift(), entity.getActivityGiftDesc(), entity.getId());
//	}
//	
//	public void deleteActivityGift(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public List<ActivityGift> queryAllActivityGift() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
	
	public List<ActivityGift> queryActivityGiftByDefineId(int activityDefineId) {
		String sql = SQL_SELECT + " and activity_define_id=?";
		return queryForList(sql, ROW_MAPPER, activityDefineId);
	}
	
	public ActivityGift queryActivityGiftById(int id) {
		String sql = SQL_SELECT + " and id=?";
		return queryForEntity(sql, ROW_MAPPER, id);
	}
}