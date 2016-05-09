package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.swaparena.SwapArenaFightLog;
/**
 * 交换竞技场战斗日志
 * @author hanjie.l
 *
 */
public class SwapArenaLogDAO extends GameDAOTemplate{
	
	private static final String TABLE = "t_swaparena_fight_log";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " WHERE attackId=? or defendId=? order by updateTime desc";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(updateTime, win, attackId, attackName, attackSoulId, attackOldRank, attackNewRank, defendId, defendName, defendSoulId, defendOldRank, defendNewRank) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final RowMapper<SwapArenaFightLog> SWAP_ARENA_LOG_ROW_MAPPER = new RowMapper<SwapArenaFightLog>() {
		@Override
		public SwapArenaFightLog rowMapper(ResultSet rs) throws SQLException {
			SwapArenaFightLog log = new SwapArenaFightLog();
			log.setId(rs.getInt("id"));
			log.setUpdateTime(rs.getLong("updateTime"));
			log.setAttackId(rs.getInt("attackId"));
			log.setAttackName(rs.getString("attackName"));
			log.setAttackSoulId(rs.getInt("attackSoulId"));
			log.setAttackOldRank(rs.getInt("attackOldRank"));
			log.setAttackNewRank(rs.getInt("attackNewRank"));
			log.setDefendId(rs.getInt("defendId"));
			log.setDefendName(rs.getString("defendName"));
			log.setDefendOldRank(rs.getInt("defendOldRank"));
			log.setDefendNewRank(rs.getInt("defendNewRank"));
			log.setDefendSoulId(rs.getInt("defendSoulId"));
			log.setWin(rs.getBoolean("win"));
			return log;
		}
	};
	
	/**
	 * 插入
	 * @param arena
	 */
	public void addSwapArena(SwapArenaFightLog arena){
		saveOrUpdate(SQL_ADD, arena.getUpdateTime(),arena.isWin(), arena.getAttackId(), arena.getAttackName(), arena.getAttackSoulId(), arena.getAttackOldRank(), arena.getAttackNewRank(), arena.getDefendId(), arena.getDefendName(), arena.getDefendSoulId(), arena.getDefendOldRank(), arena.getDefendNewRank());
	}

	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	public List<SwapArenaFightLog> getSwapArenaFightLog(int userId){
		return queryForList(SQL_SELECT, SWAP_ARENA_LOG_ROW_MAPPER, userId, userId);
	}
}
