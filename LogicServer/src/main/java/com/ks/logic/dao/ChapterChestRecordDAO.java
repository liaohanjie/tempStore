package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.dungeon.ChapterChestRecord;

/**
 * 章节宝箱记录
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月24日
 */
public class ChapterChestRecordDAO extends GameDAOTemplate {

	private static final String TABLE = "t_chapter_chest_record";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE;
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(user_id, chapter_id, create_time) VALUES(?,?,?)";
	
/*	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET user_id=?, award_id=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";*/

	private static final RowMapper<ChapterChestRecord> ROW_MAPPER = new RowMapper<ChapterChestRecord>() {
		@Override
		public ChapterChestRecord rowMapper(ResultSet rs) throws SQLException {
			ChapterChestRecord entity = new ChapterChestRecord();
			entity.setId(rs.getInt("id"));
			entity.setUserId(rs.getInt("user_id"));
			entity.setChapterId(rs.getInt("chapter_id"));
			entity.setCreateTime(rs.getTimestamp("create_time"));
			return entity;
		}
	};
	
	public void add(ChapterChestRecord entity) {
		saveOrUpdate(SQL_ADD, entity.getUserId(), entity.getChapterId(), entity.getCreateTime());
	}

	public ChapterChestRecord findByUserIdChapterId(int userId, int chapterId) {
		String sql = SQL_SELECT + " where user_id=? and chapter_id=?";
		return queryForEntity(sql, ROW_MAPPER, userId, chapterId);
	}
	
	public List<ChapterChestRecord> queryChapterChestRecord(int userId) {
		String sql = SQL_SELECT + " where user_id=?";
		return queryForList(sql, ROW_MAPPER, userId);
	}
}