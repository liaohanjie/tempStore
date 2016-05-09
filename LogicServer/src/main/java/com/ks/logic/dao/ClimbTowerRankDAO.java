package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.climb.ClimbTowerRank;

/**
 * 爬塔试炼排行榜 DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerRankDAO extends GameDAOTemplate {

	private static final String TABLE = "t_climb_tower_rank";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(user_id, tower_floor, star_count, update_time, create_time) VALUES(?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET user_id=?, tower_floor=?, star_count=?, update_time=?, create_time=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ClimbTowerRank> ROW_MAPPER = new RowMapper<ClimbTowerRank>() {
		@Override
		public ClimbTowerRank rowMapper(ResultSet rs) throws SQLException {
			ClimbTowerRank entity = new ClimbTowerRank();
			entity.setId(rs.getInt("id"));
			entity.setUserId(rs.getInt("user_id"));
			entity.setTowerFloor(rs.getInt("tower_floor"));
			entity.setStarCount(rs.getInt("star_count"));
			entity.setUpdateTime(rs.getTimestamp("update_time"));
			entity.setCreateTime(rs.getTimestamp("create_time"));
			return entity;
		}
	};
	
	public void add(ClimbTowerRank entity) {
		saveOrUpdate(SQL_ADD, entity.getUserId(), entity.getTowerFloor(), entity.getStarCount(), entity.getUpdateTime(), entity.getCreateTime());
	}

	public void update(ClimbTowerRank entity) {
		saveOrUpdate(SQL_UPDATE, entity.getUserId(), entity.getTowerFloor(), entity.getStarCount(), entity.getUpdateTime(), entity.getCreateTime(), entity.getId());
	}
	
	public void delete(int id) {
		saveOrUpdate(SQL_DELETE, id);
	}
	
	/**
	 * 查询指定用户排名信息
	 * @param userId
	 * @return
	 */
	public ClimbTowerRank getClimbTowerRank(int userId) {
		String sql = SQL_SELECT + " and user_id=?" ;
		return queryForEntity(sql, ROW_MAPPER, userId);
	}

	/**
	 * 查询指定数量的排行榜数据
	 * @param size
	 * @return
	 */
	public List<ClimbTowerRank> queryClimbTowerRank(int size) {
		String sql = SQL_SELECT + " order by tower_floor desc, star_count desc, update_time asc limit 0, " + size;
		return queryForList(sql, ROW_MAPPER);
	}
	
	/**
	 * 获取排名
	 * @param userId
	 * @return
	 */
	public int getRankByUserId(int userId){
		String sql ="select num from (select @rownum:=@rownum+1 as num,t1.* from t_climb_tower_rank t1, (SELECT @rownum:=0) t2 order by tower_floor desc, star_count desc, update_time desc) t3 where t3.user_id=?";
		return queryForEntity(sql, INT_ROW_MAPPER, userId);
	}
}