package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.logic.dao.opt.ArenaOpt;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.model.arena.Arena;
import com.ks.model.arena.ArenaRule;

/**
 * 竞技场
 * 
 * @author ks
 */
public class ArenaDAO extends GameDAOTemplate {

	private static final RowMapper<Arena> ARENA_ROW_MAPPER = new RowMapper<Arena>() {
		@Override
		public Arena rowMapper(ResultSet rs) throws SQLException {
			Arena obj = new Arena();
			obj.setUserId(rs.getInt("user_id"));
			obj.setPlayerName(rs.getString("player_name"));
			obj.setWin(rs.getInt("win"));
			obj.setLevel(rs.getInt("level"));
			obj.setSoulLevel(rs.getInt("soul_level"));
			obj.setState(rs.getBoolean("state"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			return obj;
		}
	};
	
	private static final RowMapper<ArenaRule> ARENA_RULE_ROW_MAPPER = new RowMapper<ArenaRule>(){
		@Override
		public ArenaRule rowMapper(ResultSet rs) throws SQLException{
			ArenaRule obj = new ArenaRule();
			obj.setLevel(rs.getInt("level"));
			obj.setDemote(rs.getBoolean("demote"));
			obj.setWin(rs.getInt("win"));
			obj.setAwardType(rs.getInt("award_type"));
			obj.setAwardId(rs.getInt("award_id"));
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	
	public List<ArenaRule> queryArenaRules(){
		String sql = "select * from t_arena_rule";
		return queryForList(sql, ARENA_RULE_ROW_MAPPER);
	}
	
	public Arena queryArena(int userId) {
		String sql = "select * from t_arena where user_id=?";
		return queryForEntity(sql, ARENA_ROW_MAPPER, userId);
	}

	public void updateArena(ArenaOpt opt, Arena arena) {
		StringBuilder sql = new StringBuilder(
				"update t_arena set update_time = now()");
		List<Object> args = new ArrayList<Object>();
		if (opt.win != 0) {
			if (opt.win == SQLOpt.PULS) {
				sql.append(",win=win+?");
				args.add(arena.getWin());
			} else if (opt.win == SQLOpt.MINUS) {
				sql.append(",win=win-?");
				args.add(arena.getWin());
			} else {
				sql.append(",win=?");
				args.add(arena.getWin());
			}
		}
		if (opt.state != 0) {
			sql.append(",state=?");
			args.add(arena.isState());
		}
		if (opt.soulLevel != 0) {
			sql.append(",soul_level=?");
			args.add(arena.getSoulLevel());
		}
		if(opt.level != 0){
			sql.append(",level=?");
			args.add(arena.getLevel());
		}
		sql.append(" where user_id=?");
		args.add(arena.getUserId());
		saveOrUpdate(sql.toString(), args.toArray(new Object[args.size()]));
	}

	public void addArena(Arena arena) {
		String sql = "insert into t_arena(user_id,player_name,win,soul_level,"
				+ "state,create_time,update_time) values(?,?,?,?,?,now(),now())";
		saveOrUpdate(sql, arena.getUserId(), arena.getPlayerName(),
				arena.getWin(), arena.getSoulLevel(), arena.isState());
	}
	
	public Arena queryArena(int userId,int soulLevel,int level){
		String sql = " select * from (SELECT * FROM t_arena where soul_level>?-? and soul_level<?+? and user_id!=? and state=true limit 1000) t order by rand() asc limit 1,1;";
		return queryForEntity(sql, ARENA_ROW_MAPPER,soulLevel,level,soulLevel,level,userId);
	}
}
