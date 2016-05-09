package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.dungeon.ChapterChest;

/**
 * 章节宝箱奖励DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月23日
 */
public class ChapterChestDAO extends GameCfgDAOTemplate {

	public static final String CACHE_KEY = "CHAPTER_CHEST_CACHE_KEY";
	
	private static final String TABLE = "t_chapter_chest";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE;
	
//	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
//	
//	private static final String SQL_ADD = "INSERT into " + TABLE + "(goods_id, goods_type, num, `level`, rate) VALUES(?,?,?,?,?)";
//	
//	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET goods_id=?, goods_type=?, num=?, `level`=?, rate=? WHERE id=?";
//	
//	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ChapterChest> ROW_MAPPER = new RowMapper<ChapterChest>() {
		@Override
		public ChapterChest rowMapper(ResultSet rs) throws SQLException {
			ChapterChest entity = new ChapterChest();
			entity.setId(rs.getInt("id"));
			entity.setAwardId(rs.getInt("award_id"));
			return entity;
		}
	};
	
	public List<ChapterChest> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}