package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.climb.ClimbTowerStar;

/**
 * 爬塔试炼星级配置 DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerStarDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "CLIMB_TOWER_STAR_CACHE_KEY";
	
	private static final String TABLE = "t_climb_tower_star";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(star_region,start_num,award_id) VALUES(?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET award_id=? WHERE start_num=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ClimbTowerStar> ROW_MAPPER = new RowMapper<ClimbTowerStar>() {
		@Override
		public ClimbTowerStar rowMapper(ResultSet rs) throws SQLException {
			ClimbTowerStar obj = new ClimbTowerStar();
			obj.setStartRegion(rs.getInt("star_region"));
			obj.setStartNum(rs.getInt("star_num"));
			obj.setAwardId(rs.getInt("award_id"));
			return obj;
		}
	};
	
	public List<ClimbTowerStar> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}