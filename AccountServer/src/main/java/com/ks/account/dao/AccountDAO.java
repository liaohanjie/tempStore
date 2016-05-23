package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.account.Account;

/**
 * 用户账号DAO
 * @author zhoujf
 * @date 2015年6月23日
 */
public class AccountDAO extends GameDAOTemplate {
	
	private final static String TABLE = "t_account";
	private final static String SQL_SELECT = "select * from " + TABLE + " where 1=1";
	private final static String SQL_ADD = "insert into " + TABLE + "(partner_id, game_id, user_id, user_name, last_login_server_id, last_login_time, login_count, ip, create_time, status) values(?,?,?,?,?,?,?,?,?,?)";
	private final static String SQL_DELETE = "delete from " + TABLE + " where user_id=?";
	private final static String SQL_UPDATE = "update " + TABLE;
	
	
	private static final RowMapper<Account> ROW_MAPPER = new RowMapper<Account>() {
		
		@Override
		public Account rowMapper(ResultSet rs) throws SQLException {
			Account entity = new Account();
			entity.setId(rs.getInt("id"));
			entity.setPartnerId(rs.getInt("partner_id"));
			entity.setGameId(rs.getInt("game_id"));
			entity.setUserId(rs.getInt("user_id"));
			entity.setUserName(rs.getString("user_name"));
			entity.setLastLoginServerId(rs.getInt("last_login_server_id"));
			entity.setLastLoginTime(rs.getDate("last_login_time"));
			entity.setLoginCount(rs.getInt("login_count"));
			entity.setStatus(rs.getInt("status"));
			entity.setCreateTime(rs.getDate("create_time"));
			entity.setIp(rs.getString("ip"));
			return entity;
		}
	};
	
	public Account queryById(Integer id){
		String sql = SQL_SELECT + " and id=?";
		return queryForEntity(sql, ROW_MAPPER, id);
	}
	
	public Account queryByUserId(Integer userId){
		String sql = SQL_SELECT + " and user_id=?";
		return queryForEntity(sql, ROW_MAPPER, userId);
	}
	
	public Account queryByPartnerIdUserName(Integer partnerId, String userName){
		String sql = SQL_SELECT + " and partner_id=? and user_name=?";
		return queryForEntity(sql, ROW_MAPPER, partnerId, userName);
	}
	
	public void add(Account entity){
		saveOrUpdate(SQL_ADD, entity.getPartnerId(), entity.getGameId(), entity.getUserId(), entity.getUserName(), entity.getLastLoginServerId(), entity.getLastLoginTime(), entity.getLoginCount(), entity.getIp(), entity.getCreateTime(), entity.getStatus());
	}
	
	public void update(Account entity){
		List<Object> values = new ArrayList<Object>();
		
		StringBuffer sb = new StringBuffer();
		if(entity.getPartnerId()!= null) {
			sb.append(",partner_id=?");
			values.add(entity.getPartnerId());
		}
		if(entity.getGameId() != null) {
			sb.append(",game_id=?");
			values.add(entity.getGameId());
		}
		if(entity.getUserId() != null) {
			sb.append(",user_id=?");
			values.add(entity.getUserId());
		}
		if(entity.getUserName() != null) {
			sb.append(",user_name=?");
			values.add(entity.getUserName());
		}
		if(entity.getLastLoginServerId() != null) {
			sb.append(",last_login_server_id=?");
			values.add(entity.getLastLoginServerId());
		}
		if(entity.getLastLoginTime() != null) {
			sb.append(",last_login_time=?");
			values.add(entity.getLastLoginTime());
		}
		if(entity.getLoginCount() != null) {
			sb.append(",login_count=?");
			values.add(entity.getLoginCount());
		}
		if(entity.getIp() != null) {
			sb.append(",ip=?");
			values.add(entity.getIp());
		}
		if(entity.getCreateTime() != null) {
			sb.append(",create_time=?");
			values.add(entity.getCreateTime());
		}
		if(entity.getStatus() != null) {
			sb.append(",status=?");
			values.add(entity.getStatus());
		}
		sb.append(" where id=?");
		values.add(entity.getId());
		String sql = SQL_UPDATE + " set " + sb.substring(1);
		saveOrUpdate(sql, values.toArray());
	}
	
	public void delete(Integer userId) {
		saveOrUpdate(SQL_DELETE, userId);
	}
	
	/**
	 * 玩家渠道分布统计
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> statisticsUserPartner() throws SQLException {
		return queryForListMap("SELECT `partner_id` AS 'partner' ,COUNT(*) AS 'count' from t_account GROUP BY `partner_id` ");
	}
}
