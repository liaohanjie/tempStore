package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.boss.UserBossRecord;
/**
 * 玩家boss记录
 * @author admin
 *
 */
public class UserBossDAO extends GameDAOTemplate{
	
	private static final String getTableName(){
		return " t_user_boss_record ";
	}
	
	/**
	 * 字段
	 */
	public static final String FIELDS = "user_id,cur_boss_id,cur_boss_level,version,total_hurt,next_fight_time,inspired_value,receieve_join,receieve_rank";
	/**
	 * set字段
	 */
	public static final String SET_FIELDS = " cur_boss_level=?,version=?,total_hurt=?,next_fight_time=?,inspired_value=?,receieve_join=?,receieve_rank=? ";
	
	private static final RowMapper<UserBossRecord> USERBOSSRECORD_ROW_MAPPER = new RowMapper<UserBossRecord>(){
		@Override
		public UserBossRecord rowMapper(ResultSet rs) throws SQLException{
			UserBossRecord obj = new UserBossRecord();
			obj.setUserId(rs.getInt("user_id"));
			obj.setCurBossId(rs.getInt("cur_boss_id"));
			obj.setCurBossLevel(rs.getInt("cur_boss_level"));
			obj.setVersion(rs.getString("version"));
			obj.setTotalHurt(rs.getLong("total_hurt"));
			obj.setNextFightTime(rs.getLong("next_fight_time"));
			obj.setInspiredValue(rs.getInt("inspired_value"));
			obj.setReceieveJoin(rs.getBoolean("receieve_join"));
			obj.setReceieveRank(rs.getBoolean("receieve_rank"));
			return obj;
		}
	};
	
	private static final RowMapper<Integer> RANK_ROW_MAPPER = new RowMapper<Integer>(){
		@Override
		public Integer rowMapper(ResultSet rs) throws SQLException{
			return rs.getInt("rank");
		}
	};

	/**
	 * 获取
	 * @param userId
	 * @return
	 */
	public UserBossRecord getUserBossRecord(int userId, int bossId){
		String sql = "select "+FIELDS+" from " + getTableName() +" where user_id=? and cur_boss_id=?";
		return queryForEntity(sql, USERBOSSRECORD_ROW_MAPPER, userId, bossId);
	}
	
	/**
	 * 添加
	 * @param bossRecord
	 */
	public void addUserBossRecord(UserBossRecord userBossRecord){
		String sql = "insert into" + getTableName() +"("+FIELDS+")" + " values(?,?,?,?,?,?,?,?,?);";
		saveOrUpdate(sql, userBossRecord.getUserId(), userBossRecord.getCurBossId(), userBossRecord.getCurBossLevel(), userBossRecord.getVersion(), userBossRecord.getTotalHurt(), userBossRecord.getNextFightTime(), userBossRecord.getInspiredValue(),userBossRecord.isReceieveJoin(), userBossRecord.isReceieveRank());
	}
	
	/**
	 * 更新
	 * @param bossRecord
	 */
	public void updateUserBossRecord(UserBossRecord userBossRecord){
		String sql = "update" + getTableName() + "set "+ SET_FIELDS +"where user_id=? and cur_boss_id=?";
		saveOrUpdate(sql, userBossRecord.getCurBossLevel(), userBossRecord.getVersion(), userBossRecord.getTotalHurt(), userBossRecord.getNextFightTime(), userBossRecord.getInspiredValue(),userBossRecord.isReceieveJoin(),userBossRecord.isReceieveRank(), userBossRecord.getUserId(), userBossRecord.getCurBossId());
	}
	
	/**
	 * 获取前N名
	 * @param bossRecord
	 */
	public List<UserBossRecord> getTopUserBossRecord(int bossId, String version, int num){
		String sql = "SELECT * FROM t_user_boss_record WHERE cur_boss_id=? AND version=? ORDER BY total_hurt DESC LIMIT ?;";
		return queryForList(sql, USERBOSSRECORD_ROW_MAPPER, bossId, version, num);
	}
	
	/**
	 * 获取排名
	 * @param userId
	 * @param version
	 * @return
	 */
	public int getRankByUserId(int userId, int bossId, String version){
		String sql ="SELECT COUNT(*)+1 rank FROM t_user_boss_record WHERE cur_boss_id=? AND version=? AND total_hurt >(SELECT total_hurt FROM t_user_boss_record WHERE user_id=? AND cur_boss_id=?)";
		return queryForEntity(sql, RANK_ROW_MAPPER, bossId, version, userId, bossId);
	}
	
}
