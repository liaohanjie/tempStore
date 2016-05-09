package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.climb.ClimbTowerUser;

/**
 * 爬塔试炼用户爬塔层数记录 DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerUserDAO extends GameDAOTemplate {

	private static final String TABLE = "t_climb_tower_user";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(user_id, tower_floor, star_count, fight_count, buy_fight_count, update_time, create_time) VALUES(?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET user_id=?, tower_floor=?, star_count=?, fight_count=?, buy_fight_count=?,update_time=?, create_time=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ClimbTowerUser> ROW_MAPPER = new RowMapper<ClimbTowerUser>() {
		@Override
		public ClimbTowerUser rowMapper(ResultSet rs) throws SQLException {
			ClimbTowerUser entity = new ClimbTowerUser();
			entity.setId(rs.getInt("id"));
			entity.setUserId(rs.getInt("user_id"));
			entity.setTowerFloor(rs.getInt("tower_floor"));
			entity.setStartCount(rs.getShort("star_count"));
			entity.setFightCount(rs.getShort("fight_count"));
			entity.setBuyFightCount(rs.getShort("buy_fight_count"));
			entity.setUpdateTime(rs.getTimestamp("update_time"));
			entity.setCreateTime(rs.getTimestamp("create_time"));
			return entity;
		}
	};
	
	
	public void add(ClimbTowerUser entity) {
		saveOrUpdate(SQL_ADD, entity.getUserId(), entity.getTowerFloor(), entity.getStartCount(), entity.getFightCount(), entity.getBuyFightCount(), entity.getUpdateTime(), entity.getCreateTime());
	}

	public void update(ClimbTowerUser entity) {
		saveOrUpdate(SQL_UPDATE, entity.getUserId(), entity.getTowerFloor(), entity.getStartCount(), entity.getFightCount(), entity.getBuyFightCount(), entity.getUpdateTime(), entity.getCreateTime(), entity.getId());
	}
	
	public void delete(int id) {
		saveOrUpdate(SQL_DELETE, id);
	}

	public List<ClimbTowerUser> queryClimbTowerUser(int userId) {
		String sql = SQL_SELECT + " and user_id=? order by id";
		return queryForList(sql, ROW_MAPPER, userId);
	}
	
	public ClimbTowerUser findClimbTowerUser(int userId, int towerFloor) {
		String sql = SQL_SELECT + " and user_id=? and tower_floor=?";
		return queryForEntity(sql, ROW_MAPPER, userId, towerFloor);
	}
	
	/**
	 * 获取用户星星总数
	 * @param userId
	 * @param towerFloor
	 * @return
	 */
	public int getUserStarCount(int userId) {
		String sql = "select sum(star_count) from " + TABLE + " where user_id=?";
		return queryForEntity(sql, INT_ROW_MAPPER, userId);
	}
	
	/**
	 * 获取用户指定区域的星星总数
	 * 
	 * @param userId
	 * @param region
	 * @return
	 */
	public int getUserRegionStarCount(int userId, int region) {
		String sql = "select sum(star_count) from " + TABLE + " where user_id=? and tower_floor > ? and tower_floor <= ?";
		int minStar = (region -1) * 10;
		int maxStar = region * 10;
		return queryForEntity(sql, INT_ROW_MAPPER, userId, minStar, maxStar);
	}
	
	/**
	 * 重置每层战斗通过次数，和购买次数
	 */
	public void refreshClimbTower(int userId, Date nextUpdateTime) {
		String sql =  "UPDATE " + TABLE + " SET fight_count=?, buy_fight_count=?, update_time=? where user_id=? and update_time<now()";
		saveOrUpdate(sql, 0, 0, nextUpdateTime, userId);
	}
}