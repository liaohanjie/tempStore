package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.buding.UserBuding;

/**
 * 
 * @author ks
 */
public class UserBudingDAO extends GameDAOTemplate {

	private static final String getTableName(int userId) {
		return "t_user_buding_" + (userId % 10);
	}

	private static final RowMapper<UserBuding> USER_BUDING_ROW_MAPPER = new RowMapper<UserBuding>() {
		@Override
		public UserBuding rowMapper(ResultSet rs) throws SQLException {
			UserBuding obj = new UserBuding();
			obj.setUserId(rs.getInt("user_id"));
			obj.setBudingId(rs.getInt("buding_id"));
			obj.setLevel(rs.getInt("level"));
			obj.setGold(rs.getInt("gold"));
			obj.setCollectCount(rs.getInt("collect_count"));
			obj.setLastCollectTime(rs.getTimestamp("last_collect_time"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			return obj;
		}
	};

	public List<UserBuding> queryUserBudings(int userId) {
		String sql = "select * from " + getTableName(userId)
				+ " where user_id=?";
		return queryForList(sql, USER_BUDING_ROW_MAPPER, userId);
	}

	public UserBuding queryUserBuding(int userId, int budingId) {
		String sql = "select * from " + getTableName(userId)
				+ " where user_id=? and buding_id=?";
		return queryForEntity(sql, USER_BUDING_ROW_MAPPER, userId, budingId);
	}

	public void addUserBuding(UserBuding userBuding) {
		String sql = "insert into "
				+ getTableName(userBuding.getUserId())
				+ "(user_id,buding_id,level,gold,collect_count,last_collect_time,"
				+ "create_time,update_time) values(?,?,?,?,?,now(),now(),now())";
		saveOrUpdate(sql, userBuding.getUserId(), userBuding.getBudingId(),
				userBuding.getLevel(), userBuding.getGold(),
				userBuding.getCollectCount());
	}

	public void updateUserBuding(UserBuding userBuding) {
		String sql = "update "
				+ getTableName(userBuding.getUserId())
				+ " set update_time=now(),level=?,gold=?,collect_count=?,last_collect_time=? where user_id=? and buding_id=?";
		saveOrUpdate(sql, userBuding.getLevel(), userBuding.getGold(),
				userBuding.getCollectCount(), userBuding.getLastCollectTime(),
				userBuding.getUserId(), userBuding.getBudingId());
	}
}
