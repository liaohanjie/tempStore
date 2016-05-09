package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.Award;

/**
 * 爬塔试炼奖励配置 DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public class AwardDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "CLIMB_TOWER_CACHE_KEY";
	
	private static final String TABLE = "t_award";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE;
	
//	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
//	
//	private static final String SQL_ADD = "INSERT into " + TABLE + "(goods_id, goods_type, num, `level`, rate) VALUES(?,?,?,?,?)";
//	
//	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET goods_id=?, goods_type=?, num=?, `level`=?, rate=? WHERE id=?";
//	
//	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<Award> ROW_MAPPER = new RowMapper<Award>() {
		@Override
		public Award rowMapper(ResultSet rs) throws SQLException {
			Award entity = new Award();
			entity.setId(rs.getInt("id"));
			entity.setGoodsId(rs.getInt("goods_id"));
			entity.setGoodsType(rs.getInt("goods_type"));
			entity.setNum(rs.getInt("num"));
			entity.setLevel(rs.getInt("level"));
			entity.setRate(rs.getDouble("rate"));
			return entity;
		}
	};
	
	public List<Award> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}