package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.boss.WorldBossRecord;
/**
 * boss的dao
 * @author hanjie.l
 *
 */
public class WorldBossDAO extends GameDAOTemplate{
	
	private static final String getTableName(){
		return " t_world_boss_record ";
	}
	
	/**
	 * 字段
	 */
	public static final String FIELDS = "boss_id,version,level,cur_blood,max_blood,open,end_time,next_begin_time";
	/**
	 * set字段
	 */
	public static final String SET_FIELDS = " version=?,level=?,cur_blood=?,max_blood=?,open=?,end_time=?,next_begin_time=? ";
	
	
	private static final RowMapper<WorldBossRecord> WORLDBOSSRECORD_ROW_MAPPER = new RowMapper<WorldBossRecord>(){
		@Override
		public WorldBossRecord rowMapper(ResultSet rs) throws SQLException{
			WorldBossRecord obj = new WorldBossRecord();
			obj.setBossId(rs.getInt("boss_id"));
			obj.setVersion(rs.getString("version"));
			obj.setLevel(rs.getInt("level"));
			obj.setCurBlood(rs.getLong("cur_blood"));
			obj.setMaxBlood(rs.getLong("max_blood"));
			obj.setOpen(rs.getBoolean("open"));
			obj.setEndTime(rs.getLong("end_time"));
			obj.setNextBeginTime(rs.getLong("next_begin_time"));
			return obj;
		}
	};


	/**
	 * 获取
	 * @param bossId
	 * @return
	 */
	public WorldBossRecord getWorldBossRecord(int bossId){
		String sql = "select "+FIELDS+" from " + getTableName() +" where boss_id=?";
		return queryForEntity(sql, WORLDBOSSRECORD_ROW_MAPPER, bossId);
	}
	
	/**
	 * 添加
	 * @param bossRecord
	 */
	public void addWorldBossRecord(WorldBossRecord bossRecord){
		String sql = "insert into" + getTableName() +"("+FIELDS+")" + " values(?,?,?,?,?,?,?,?);";
		saveOrUpdate(sql, bossRecord.getBossId(), bossRecord.getVersion(), bossRecord.getLevel(), bossRecord.getCurBlood(), bossRecord.getMaxBlood(), bossRecord.isOpen(), bossRecord.getEndTime(), bossRecord.getNextBeginTime());
	}
	
	/**
	 * 更新
	 * @param bossRecord
	 */
	public void updateWorldBossRecord(WorldBossRecord bossRecord){
		String sql = "update" + getTableName() + "set "+ SET_FIELDS +"where boss_id=?";
		saveOrUpdate(sql, bossRecord.getVersion(), bossRecord.getLevel(), bossRecord.getCurBlood(), bossRecord.getMaxBlood(), bossRecord.isOpen(), bossRecord.getEndTime(), bossRecord.getNextBeginTime(), bossRecord.getBossId());
	}
}
