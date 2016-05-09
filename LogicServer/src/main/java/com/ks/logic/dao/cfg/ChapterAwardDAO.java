package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.dungeon.ChapterAward;

/**
 * 章节奖励DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月23日
 */
public class ChapterAwardDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "CHAPTER_AWARD_CACHE_KEY";
	
	private static final String TABLE = "t_chapter_award";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE;
	
//	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
//	
//	private static final String SQL_ADD = "INSERT into " + TABLE + "(goods_id, goods_type, num, `level`, rate) VALUES(?,?,?,?,?)";
//	
//	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET goods_id=?, goods_type=?, num=?, `level`=?, rate=? WHERE id=?";
//	
//	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ChapterAward> ROW_MAPPER = new RowMapper<ChapterAward>() {
		@Override
		public ChapterAward rowMapper(ResultSet rs) throws SQLException {
			ChapterAward entity = new ChapterAward();
			entity.setAwardId(rs.getInt("award_id"));
			return entity;
		}
	};
	
	public List<ChapterAward> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}