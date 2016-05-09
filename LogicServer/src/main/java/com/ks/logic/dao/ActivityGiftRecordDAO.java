package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.activity.ActivityGiftRecord;

/**
 * 活动礼包领取记录 DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月13日
 */
public class ActivityGiftRecordDAO extends GameDAOTemplate {

	public static final String ACTIVITY_GIFT_RECORD_KEY = "ACTIVITY_GIFT_RECORD_CACHE_KEY";
	
	private static final String TABLE = "t_activity_gift_record";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(user_id, activity_gift_id, create_time) VALUES(?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET user_id=?, activity_gift_id=?, create_time=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ActivityGiftRecord> ROW_MAPPER = new RowMapper<ActivityGiftRecord>() {
		@Override
		public ActivityGiftRecord rowMapper(ResultSet rs) throws SQLException {
			ActivityGiftRecord obj = new ActivityGiftRecord();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setActivityGiftId(rs.getInt("activity_gift_id"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
//	private static final ObjectFieldMap<ActivityGiftRecord> FIELD_MAP = new ObjectFieldMap<ActivityGiftRecord>() {
//		@Override
//		public Map<String, String> objectToMap(ActivityGiftRecord o) {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("id", String.valueOf(o.getId()));
//			map.put("userId", String.valueOf(o.getUserId()));
//			map.put("activityGiftId", String.valueOf(o.getActivityGiftId()));
//			map.put("createTime", String.valueOf(o.getCreateTime().getTime()));
//			return map;
//		}
//	};
//	
//	private static final JedisRowMapper<ActivityGiftRecord> JEDIS_ROW_MAPPER = new JedisRowMapper<ActivityGiftRecord>() {
//		@Override
//		public ActivityGiftRecord rowMapper(JedisResultSet jrs) {
//			ActivityGiftRecord obj = new ActivityGiftRecord();
//			obj.setId(jrs.getInt("id"));
//			obj.setUserId(jrs.getInt("userId"));
//			obj.setActivityGiftId(jrs.getInt("activityGiftId"));
//			obj.setCreateTime(jrs.getDate("createTime"));
//			return obj;
//		}
//	};
	
	public void addActivityGiftRecord(ActivityGiftRecord entity) {
		saveOrUpdate(SQL_ADD, entity.getUserId(), entity.getActivityGiftId(), entity.getCreateTime());
	}

	public void updateActivityGiftRecord(ActivityGiftRecord entity) {
		saveOrUpdate(SQL_UPDATE, entity.getUserId(), entity.getActivityGiftId(), entity.getCreateTime(), entity.getId());
	}
	
	public void deleteActivityGiftRecord(int id) {
		saveOrUpdate(SQL_DELETE, id);
	}
	
	public void deleteActivityGiftByActivityDefineId(int defineId) {
		String sql = "delete t1 from t_activity_gift_record t1, t_activity_gift t2 where t1.activity_gift_id=t2.id and t2.activity_define_id=?";
		saveOrUpdate(sql, defineId);
	}
	
	public List<ActivityGiftRecord> queryActivityGiftRecord(int userId, int activityGiftId) {
		String sql = SQL_SELECT + " and user_id=? and activity_gift_id=?";
		return queryForList(sql, ROW_MAPPER, userId, activityGiftId);
	}
	
	public int queryActivityGiftRecordCount(int userId, int[] activityGiftId) {
		String ids = "";
		for (int i = 0; i < activityGiftId.length; i++) {
			ids = ids + "," + activityGiftId[i];
		}
		String sql = " select count(*) from " + TABLE + " where user_id=? and activity_gift_id in (" + ids.substring(1) + ")";
		return queryForEntity(sql, INT_ROW_MAPPER, userId);
	}
	
	public List<ActivityGiftRecord> queryActivityGiftRecord(int userId, int[] activityGiftId) {
		String ids = "";
		for (int i = 0; i < activityGiftId.length; i++) {
			ids = ids + "," + activityGiftId[i];
		}
		String sql = " select * from " + TABLE + " where user_id=? and activity_gift_id in (" + ids.substring(1) + ")";
		return queryForList(sql, ROW_MAPPER, userId);
	}
	
	public List<ActivityGiftRecord> queryActivityGiftRecord(int userId, int activityGiftId, Date dateFrom, Date dateTo) {
		String sql = SQL_SELECT + " and user_id=? and activity_gift_id=? and create_time>? and create_time<?";
		return queryForList(sql, ROW_MAPPER, userId, activityGiftId, dateFrom, dateTo);
	}
	
	public ActivityGiftRecord queryActivityGiftById(int id) {
		String sql = SQL_SELECT + " and id=?";
		return queryForEntity(sql, ROW_MAPPER, id);
	}
}