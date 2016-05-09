package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.check.BattleType;
import com.ks.model.check.UserFightCheck;
/**
 * 战斗检查dao
 * @author hanjie.l
 *
 */
public class FightCheckDAO extends GameDAOTemplate {
	
	private static final String TABLE = "t_user_fight_check";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where userId=?";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(userId, battleType, pass) VALUES(?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET battleType=?, pass=?  WHERE userId=?";
	
	private static final RowMapper<UserFightCheck> FIGHT_CHECK_ROW_MAPPER = new RowMapper<UserFightCheck>() {
		@Override
		public UserFightCheck rowMapper(ResultSet rs) throws SQLException {
			UserFightCheck check = new UserFightCheck();
			check.setUserId(rs.getInt("userId"));
			check.setBattleType(BattleType.valueOf(rs.getInt("battleType")));
			check.setPass(rs.getBoolean("pass"));
			return check;
		}
	};
	
	/**
	 * 插入
	 * @param check
	 */
	public void addUserFightCheck(UserFightCheck check){
		saveOrUpdate(SQL_ADD, check.getUserId(), check.getBattleType().ordinal(), check.isPass());
	}
	
	/**
	 * 更新
	 * @param check
	 */
	public void updateUserFightCheck(UserFightCheck check){
		saveOrUpdate(SQL_UPDATE, check.getBattleType().ordinal(), check.isPass(), check.getUserId());
	}
	
	/**
	 * 查询
	 * @param userId
	 * @return
	 */
	public UserFightCheck getUserFightCheck(int userId){
		return queryForEntity(SQL_SELECT, FIGHT_CHECK_ROW_MAPPER, userId);
	}
}
