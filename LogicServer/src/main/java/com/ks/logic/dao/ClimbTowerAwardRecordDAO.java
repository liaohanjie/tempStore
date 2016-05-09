package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.climb.ClimbTowerAwardRecord;

/**
 * 爬塔试炼集星奖励领取记录 DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerAwardRecordDAO extends GameDAOTemplate {

	private static final String TABLE = "t_climb_tower_award_record";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(user_id, star_region, star_num, create_time) VALUES(?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET user_id=?, star_region=?, star_num=?, create_time=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ClimbTowerAwardRecord> ROW_MAPPER = new RowMapper<ClimbTowerAwardRecord>() {
		@Override
		public ClimbTowerAwardRecord rowMapper(ResultSet rs) throws SQLException {
			ClimbTowerAwardRecord entity = new ClimbTowerAwardRecord();
			entity.setId(rs.getInt("id"));
			entity.setUserId(rs.getInt("user_id"));
			entity.setStarRegion(rs.getShort("star_region"));
			entity.setStarNum(rs.getInt("star_num"));
			//entity.setStatus(rs.getInt("status"));
			//entity.setCreateTime(rs.getTimestamp("create_time"));
			return entity;
		}
	};
	
	public void add(ClimbTowerAwardRecord entity) {
		saveOrUpdate(SQL_ADD, entity.getUserId(), entity.getStarRegion(), entity.getStarNum(), entity.getCreateTime());
	}

	public void update(ClimbTowerAwardRecord entity) {
		saveOrUpdate(SQL_UPDATE, entity.getUserId(), entity.getStarRegion(), entity.getStarNum(), entity.getCreateTime(), entity.getId());
	}
	
	public void delete(int id) {
		saveOrUpdate(SQL_DELETE, id);
	}

	/**
	 * 查询指定用户的领取记录
	 * @param size
	 * @return
	 */
	public List<ClimbTowerAwardRecord> queryClimbTowerAwardRecord(int userId) {
		String sql = SQL_SELECT + " and user_id=?";
		return queryForList(sql, ROW_MAPPER, userId);
	}
	
	/**
	 * 查询指定用户星级的领取记录
	 * @param userId
	 * @param starRegion
	 * @param starNum
	 * @return
	 */
	public ClimbTowerAwardRecord queryClimbTowerAwardRecord(int userId, int starRegion, int starNum) {
		String sql = SQL_SELECT + " and user_id=? and star_region=? and star_num=?";
		return queryForEntity(sql, ROW_MAPPER, userId, starRegion, starNum);
	}
	
	/**
	 * 查询用户每个区域的最大星星领取记录
	 * @param userId
	 * @return
	 */
	public List<ClimbTowerAwardRecord> queryClimbTowerRegionStarRecord(int userId) {
		//String sql = "select id,star_region,max(star_num) star_num,user_id from t_climb_tower_award_record where user_id=? group by user_id,star_region";
		String sql = SQL_SELECT + " and user_id=?";
		return queryForList(sql, ROW_MAPPER, userId);
	}
	
	/**
	 * 查询用户当前区域最大星星领取记录
	 * @param userId
	 * @param starRegion
	 * @return
	 */
//	public ClimbTowerAwardRecord queryClimbTowerRegionMaxStarRecord(int userId, int starRegion) {
//		String sql = "select id,star_region,max(star_num) star_num,user_id from t_climb_tower_award_record where user_id=? and star_region=? group by user_id,star_region";
//		return queryForEntity(sql, ROW_MAPPER, userId, starRegion);
//	}
}