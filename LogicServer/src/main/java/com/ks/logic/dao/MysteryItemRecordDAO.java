package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.shop.MysteryItemRecord;

/**
 * 神秘商店物品DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月18日
 */
public class MysteryItemRecordDAO extends GameDAOTemplate {

	private static final String TABLE = "t_mystery_item_record";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(user_id, type_id, item_id, create_time) VALUES(?,?,?,?)";
	
//	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET user_id=?, item_id=?, create_time=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<MysteryItemRecord> ROW_MAPPER = new RowMapper<MysteryItemRecord>() {
		@Override
		public MysteryItemRecord rowMapper(ResultSet rs) throws SQLException {
			MysteryItemRecord entity = new MysteryItemRecord();
			entity.setId(rs.getInt("id"));
			entity.setUserId(rs.getInt("user_id"));
			entity.setItemId(rs.getInt("item_id"));
			entity.setTypeId(rs.getInt("type_id"));
			entity.setCreateTime(rs.getTimestamp("create_time"));
			return entity;
		}
	};
	
	/**
	 * 查询指定时间内某个神秘物品的购买记录
	 * @param userId
	 * @param itemId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MysteryItemRecord> query(int userId, int itemId, Date startTime, Date endTime) {
		return queryForList(SQL_SELECT + " and user_id=? and item_id=? and create_time>=? and create_time<=?", ROW_MAPPER, userId, itemId, startTime, endTime);
	}
	
	/**
	 * 查询指定时间段内的所有购买记录
	 * @param userId
	 * @param itemId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<MysteryItemRecord> queryAll(int userId, Date startTime, Date endTime) {
		return queryForList(SQL_SELECT + " and user_id=? and create_time>=? and create_time<=?", ROW_MAPPER, userId, startTime, endTime);
	}
	
	public void add(MysteryItemRecord entity) {
		saveOrUpdate(SQL_ADD, entity.getUserId(), entity.getTypeId(), entity.getItemId(), entity.getCreateTime());
	}
	
//	public void delete(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public void delete(int userId, int typeId, Date startTime, Date endTime) {
		String sql = "DELETE FROM " + TABLE + " WHERE user_id=? and create_time>=? and create_time<=? and (type_id=0 or type_id=?)";
		saveOrUpdate(sql, userId, startTime, endTime, typeId);
	}
	
	public void deleteByUserId(int userId) {
		String sql = "DELETE FROM " + TABLE + " WHERE user_id=?";
		saveOrUpdate(sql, userId);
	}
}