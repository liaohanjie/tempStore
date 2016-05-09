package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.climb.ClimbTower;

/**
 * 爬塔试炼配置 DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class ClimbTowerDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "CLIMB_TOWER_CACHE_KEY";
	
	private static final String TABLE = "t_climb_tower";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " order by tower_floor";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(tower_floor,`level`,cost_rock,first_award_id,fix_award_id) VALUES(?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET `level`=?, cost_rock=?, first_award_id=?, fix_award_id=? WHERE tower_floor=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ClimbTower> ROW_MAPPER = new RowMapper<ClimbTower>() {
		@Override
		public ClimbTower rowMapper(ResultSet rs) throws SQLException {
			ClimbTower obj = new ClimbTower();
			obj.setTowerFloor(rs.getInt("tower_floor"));
			obj.setLevel(rs.getInt("level"));
			obj.setCostRock(rs.getInt("cost_rock"));
			obj.setFirstAwardId(rs.getInt("first_award_id"));
			obj.setFixAwardId(rs.getInt("fix_award_id"));
			return obj;
		}
	};
	
	public List<ClimbTower> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}