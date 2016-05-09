package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.swaparena.SwapArena;
/**
 * 交换竞技场
 * @author hanjie.l
 *
 */
public class SwapArenaDAO extends GameDAOTemplate{
	
	private static final String TABLE = "t_swap_arena";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where userId=?";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(id, userId, rank, times, buyTimes, nextFightTime, lastUpdateTime, robot) VALUES(?,?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET rank=?, times=?, buyTimes=?, nextFightTime=?, lastUpdateTime=?, robot=? WHERE userId=?";


	private static final RowMapper<SwapArena> SWAP_ARENA_ROW_MAPPER = new RowMapper<SwapArena>() {
		@Override
		public SwapArena rowMapper(ResultSet rs) throws SQLException {
			SwapArena arena = new SwapArena();
			arena.setId(rs.getInt("id"));
			arena.setUserId(rs.getInt("userId"));
			arena.setRank(rs.getInt("rank"));
			arena.setTimes(rs.getInt("times"));
			arena.setBuyTimes(rs.getInt("buyTimes"));
			arena.setNextFightTime(rs.getLong("nextFightTime"));
			arena.setLastUpdateTime(rs.getLong("lastUpdateTime"));
			arena.setRobot(rs.getBoolean("robot"));
			return arena;
		}
	};
	
	
	/**
	 * 插入
	 * @param arena
	 */
	public void addSwapArena(SwapArena arena){
		saveOrUpdate(SQL_ADD, arena.getId(), arena.getUserId(), arena.getRank(), arena.getTimes(), arena.getBuyTimes(), arena.getNextFightTime(), arena.getLastUpdateTime(), arena.isRobot());
	}
	
	/**
	 * 更新
	 * @param arena
	 */
	public void updateSwapArena(SwapArena arena){
		saveOrUpdate(SQL_UPDATE, arena.getRank(), arena.getTimes(), arena.getBuyTimes(), arena.getNextFightTime(), arena.getLastUpdateTime(), arena.isRobot(), arena.getUserId());
	}
	
	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	public SwapArena getSwapArena(int userId){
		return queryForEntity(SQL_SELECT, SWAP_ARENA_ROW_MAPPER, userId);
	}
	
	/**
	 * 查询指定排名的玩家
	 * @param ranks
	 * @return
	 */
	public List<SwapArena> getSwapArenaByRanks(Collection<Integer> ranks){
		StringBuilder builder = new StringBuilder();
		for(int rank : ranks){
			builder.append(rank);
			builder.append(",");
		}
		String sql = "SELECT * FROM " + TABLE + " where rank in("+  builder.subSequence(0, builder.length()-1) + ")" + " order by rank";
		return queryForList(sql, SWAP_ARENA_ROW_MAPPER);
	}
	
	/**
	 * 排行榜总数量
	 * @return
	 */
	public int getSwapArenaCount(){
		String sql = "SELECT count(*) FROM " + TABLE;
		return queryForEntity(sql, INT_ROW_MAPPER)+1;
	}
	
	/**
	 * 获取所有参与玩家
	 * @param num
	 * @return
	 */
	public List<SwapArena> getAllSwapArenas(){
		String sql = "SELECT * FROM " + TABLE + " where robot = FALSE order by rank";
		return queryForList(sql, SWAP_ARENA_ROW_MAPPER);
	}

	
	/**
	 * 获取前num名的玩家
	 * @param num
	 * @return
	 */
	public List<SwapArena> getTopSwapArenas(int num){
		String sql = "SELECT * FROM " + TABLE + " order by rank limit ?";
		return queryForList(sql, SWAP_ARENA_ROW_MAPPER, num);
	}
}
