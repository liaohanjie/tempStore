package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.friend.FriendGift;
import com.ks.util.JSONUtil;

public class FriendGiftDAO extends GameDAOTemplate {

	private static final String getTableName(int userId) {
		return "t_friend_gift_" + (userId % 10);
	}

	private static final RowMapper<FriendGift> FRIEND_GIFI_ROW_MAPPER = new RowMapper<FriendGift>() {
		@Override
		public FriendGift rowMapper(ResultSet rs) throws SQLException {
			FriendGift obj = new FriendGift();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setFriendId(rs.getInt("friend_id"));
			obj.setFriendName(rs.getString("friend_name"));
			obj.setWant(JSONUtil.toObject(rs.getString("want"), new TypeReference<List<Integer>>() {}));
			obj.setSoulId(rs.getInt("soul_id"));
			obj.setSoulLevel(rs.getInt("soul_level"));
			obj.setZone(rs.getInt("zone"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setReceieve(rs.getBoolean("receieve"));
			return obj;
		}
	};

	/**
	 * 查询所有可领物品
	 * @param userId
	 * @return
	 */
	public List<FriendGift> queryFriendGifts(int userId) {
		String sql = "select * from " + getTableName(userId) + " where user_id=?";
		return queryForList(sql, FRIEND_GIFI_ROW_MAPPER, userId);
	}

	/**
	 * 查询指定礼品
	 * @param id
	 * @param userId
	 * @return
	 */
	public FriendGift queryFriendGift(int id, int userId) {
		String sql = "select * from " + getTableName(userId) + " where id=? and user_id=?";
		return queryForEntity(sql, FRIEND_GIFI_ROW_MAPPER, id, userId);
	}

	/**
	 * 插入
	 * @param gift
	 */
	public void addFirendGift(FriendGift gift) {
		gift.setCreateTime(new Date());
		String sql = "INSERT INTO " + getTableName(gift.getUserId()) + "(user_id, friend_id, friend_name, want, soul_id, soul_level, receieve, zone, create_time) VALUES(?,?,?,?,?,?,?,?,?)";
		saveOrUpdate(sql, gift.getUserId(), gift.getFriendId(), gift.getFriendName(), JSONUtil.toJson(gift.getWant()), gift.getSoulId(), gift.getSoulLevel(), gift.isReceieve(), gift.getZone(), gift.getCreateTime());
	}
	
	/**
	 * 更新(设置为已领取)
	 * @param gift
	 */
	public void updateFirendGift(FriendGift gift) {
		String sql = "UPDATE " + getTableName(gift.getUserId()) + " SET receieve=? WHERE user_id=? AND id=?";
		saveOrUpdate(sql, gift.isReceieve(), gift.getUserId(), gift.getId());
	}

	public void deleteFriendGift(int userId, Date date) {
		String sql = "delete from " + getTableName(userId) + " where user_id=? and create_time < ?";
		saveOrUpdate(sql, userId, date);
	}

}
