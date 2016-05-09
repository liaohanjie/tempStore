package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.filter.UserFilter;
import com.ks.model.user.User;

/**
 * 用户数据操作
 * 
 * @author ks
 * 
 */
public class UserDAO extends GameDAOTemplate {
	private static final String USER_CACHE = "USER_CACHE_";

	private static final String USER_LEVEL_RANK = "USER_LEVEL_RANK";

	/** 章节排名 */
	private static final String USER_CHAPTER_RANK = "USER_CHAPTER_RANK";

	private static final RowMapper<User> ROW_MAPPER = new RowMapper<User>() {
		@Override
		public User rowMapper(ResultSet rs) throws SQLException {
			User user = new User();
			user.setUserId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			user.setPartner(rs.getInt("partner"));
			user.setPlayerName(rs.getString("player_name"));
			user.setLevel(rs.getInt("level"));
			user.setStamina(rs.getInt("stamina"));
			user.setExp(rs.getInt("exp"));
			user.setCurrency(rs.getInt("currency"));
			user.setGold(rs.getInt("gold"));
			user.setSoulCapacity(rs.getInt("soul_capacity"));
			user.setItemCapacity(rs.getInt("item_capacity"));
			user.setTotalCurrency(rs.getInt("total_currency"));
			user.setCurrTeamId(rs.getByte("curr_team_id"));
			user.setLastLoginTime(rs.getTimestamp("last_login_time"));
			user.setLastLogoutTime(rs.getTimestamp("last_logout_time"));
			user.setCreateTime(rs.getTimestamp("create_time"));
			user.setUpdateTime(rs.getTimestamp("update_time"));
			user.setFriendCapacity(rs.getInt("friend_capacity"));
			user.setLastRegainStaminaTime(rs.getTimestamp("last_regain_stamina_time"));
			user.setProperty(rs.getInt("property"));
			user.setGuideStep(rs.getInt("guide_step"));
			user.setStoryMission(rs.getInt("story_mission"));
			user.setInfoStep(rs.getInt("info_step"));
			user.setUninterruptedLoginCount(rs.getInt("uninterrupted_login_count"));
			user.setFirstCurrency(rs.getInt("first_currency"));
			user.setPoint(rs.getInt("point"));
			user.setHonor(rs.getInt("honor"));
			user.setBanAccountTime(rs.getTimestamp("ban_account_time"));
			user.setBanChatTime(rs.getTimestamp("ban_chat_time"));
			return user;
		}
	};

	private static final ObjectFieldMap<User> USER_FIELD_MAP = new ObjectFieldMap<User>() {
		@Override
		public Map<String, String> objectToMap(User o) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", String.valueOf(o.getUserId()));
			map.put("username", o.getUsername());
			map.put("partner", String.valueOf(o.getPartner()));
			map.put("playerName", o.getPlayerName());
			map.put("level", String.valueOf(o.getLevel()));
			map.put("stamina", String.valueOf(o.getStamina()));
			map.put("exp", String.valueOf(o.getExp()));
			map.put("currency", String.valueOf(o.getCurrency()));
			map.put("gold", String.valueOf(o.getGold()));
			map.put("soulCapacity", String.valueOf(o.getSoulCapacity()));
			map.put("itemCapacity", String.valueOf(o.getItemCapacity()));
			map.put("totalCurrency", String.valueOf(o.getTotalCurrency()));
			map.put("currTeamId", String.valueOf(o.getCurrTeamId()));
			map.put("lastLoginTime", String.valueOf(o.getLastLoginTime().getTime()));
			map.put("lastLogoutTime", String.valueOf(o.getLastLogoutTime().getTime()));
			map.put("createTime", String.valueOf(o.getCreateTime().getTime()));
			map.put("updateTime", String.valueOf(o.getUpdateTime().getTime()));
			map.put("friendCapacity", String.valueOf(o.getFriendCapacity()));
			map.put("lastRegainStaminaTime", String.valueOf(o.getLastRegainStaminaTime().getTime()));
			map.put("property", String.valueOf(o.getProperty()));
			map.put("guideStep", String.valueOf(o.getGuideStep()));
			map.put("storyMission", String.valueOf(o.getStoryMission()));
			map.put("infoStep", String.valueOf(o.getInfoStep()));
			map.put("uninterruptedLoginCount", String.valueOf(o.getUninterruptedLoginCount()));
			map.put("firstCurrency", String.valueOf(o.getFirstCurrency()));
			map.put("point", String.valueOf(o.getPoint()));
			map.put("honor", String.valueOf(o.getHonor()));
			map.put("ban_account_time", String.valueOf(o.getBanAccountTime() == null ? null :o.getBanAccountTime().getTime()));
			map.put("ban_chat_time", String.valueOf(o.getBanChatTime() == null ? null : o.getBanChatTime().getTime()));
			return map;
		}
	};

	private static final JedisRowMapper<User> USER_JEDIS_ROWMAPPER = new JedisRowMapper<User>() {
		@Override
		public User rowMapper(JedisResultSet jrs) {
			User obj = new User();
			obj.setUserId(jrs.getInt("userId"));
			obj.setUsername(jrs.getString("username"));
			obj.setPartner(jrs.getInt("partner"));
			obj.setPlayerName(jrs.getString("playerName"));
			obj.setLevel(jrs.getInt("level"));
			obj.setStamina(jrs.getInt("stamina"));
			obj.setExp(jrs.getInt("exp"));
			obj.setCurrency(jrs.getInt("currency"));
			obj.setGold(jrs.getInt("gold"));
			obj.setSoulCapacity(jrs.getInt("soulCapacity"));
			obj.setItemCapacity(jrs.getInt("itemCapacity"));
			obj.setTotalCurrency(jrs.getInt("totalCurrency"));
			obj.setCurrTeamId(jrs.getByte("currTeamId"));
			obj.setLastLoginTime(jrs.getDate("lastLoginTime"));
			obj.setLastLogoutTime(jrs.getDate("lastLogoutTime"));
			obj.setCreateTime(jrs.getDate("createTime"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			obj.setFriendCapacity(jrs.getInt("friendCapacity"));
			obj.setLastRegainStaminaTime(jrs.getDate("lastRegainStaminaTime", new Date()));
			obj.setProperty(jrs.getInt("property"));
			obj.setGuideStep(jrs.getInt("guideStep", 200));
			obj.setStoryMission(jrs.getInt("storyMission"));
			obj.setInfoStep(jrs.getInt("infoStep", 0));
			obj.setUninterruptedLoginCount(jrs.getInt("uninterruptedLoginCount", 0));
			obj.setFirstCurrency(jrs.getInt("firstCurrency", 0));
			obj.setPoint(jrs.getInt("point", 0));
			obj.setHonor(jrs.getInt("honor", 0));
			obj.setBanAccountTime(jrs.getDate("ban_account_time"));
			obj.setBanChatTime(jrs.getDate("ban_chat_time"));
			return obj;
		}
	};

	public List<User> findAllUser() {
		String sql = "select * from t_user";
		return this.queryForList(sql, ROW_MAPPER);
	}
	
	public List<User> findAllUsers(Date fromLoginDate, Date endLoginDate) {
		String sql = "select * from t_user where last_login_time >= ? and last_login_time <= ?";
		return this.queryForList(sql, ROW_MAPPER, fromLoginDate, endLoginDate);
	}

	public User findUserByUsername(String username, int partner) {
		String sql = "select * from t_user where username = ? and partner=? limit 1";
		return this.queryForEntity(sql, ROW_MAPPER, username, partner);
	}

	public User findUserByPlayername(String username) {
		String sql = "select * from t_user where player_name = ? limit 1";
		return this.queryForEntity(sql, ROW_MAPPER, username);
	}

	public User findUserByUserId(int userId) {
		String sql = "select * from t_user where user_id=? limit 1";
		return queryForEntity(sql, ROW_MAPPER, userId);
	}

	public User addUser(User user) {
		String sql = "insert into `t_user` (username,partner,player_name,"
		        + "level,exp,currency,gold,soul_capacity,item_capacity,total_currency,"
		        + "last_logout_time,create_time,update_time,stamina,curr_team_id,last_regain_stamina_time,property,guide_step,story_mission,uninterrupted_login_count) values "
		        + "(?,?,?,?,?,?,?,?,?,?,now(),now(),now(),?,?,now(),?,?,?,?);";
		int userId = insertAndReturnId(sql, INT_KEY, user.getUsername(), user.getPartner(), user.getPlayerName(), user.getLevel(), user.getExp(),
		        user.getCurrency(), user.getGold(), user.getSoulCapacity(), user.getItemCapacity(), user.getTotalCurrency(), user.getStamina(),
		        user.getCurrTeamId(), user.getProperty(), user.getGuideStep(), user.getStoryMission(), user.getUninterruptedLoginCount());
		user.setUserId(userId);
		return user;
	}

	public void updateUser(User user) {
		String sql = "update t_user set "
		        + "level=?,exp=?,currency=?,gold=?,soul_capacity=?,"
		        + "item_capacity=?,total_currency=?,stamina=?,curr_team_id=?,update_time=?,"
		        + "last_login_time=?,last_logout_time=now(),friend_capacity=?,last_regain_stamina_time=?,"
		        + "property=?,guide_step=?,story_mission=?,info_step=?,uninterrupted_login_count=?,first_currency=?,point=?,honor=?,ban_account_time=?,ban_chat_time=?  where user_id=?";
		this.saveOrUpdate(sql, user.getLevel(), user.getExp(), user.getCurrency(), user.getGold(), user.getSoulCapacity(), user.getItemCapacity(),
		        user.getTotalCurrency(), user.getStamina(), user.getCurrTeamId(), user.getUpdateTime(), user.getLastLoginTime(), user.getFriendCapacity(),
		        user.getLastRegainStaminaTime(), user.getProperty(), user.getGuideStep(), user.getStoryMission(), user.getInfoStep(),
		        user.getUninterruptedLoginCount(), user.getFirstCurrency(), user.getPoint(), user.getHonor(), user.getBanAccountTime(), user.getBanChatTime(),
		        user.getUserId());
	}

	public void updatePlayerName(int userId, String playerName) {
		String sql = "update t_user set player_name=? where user_id=?";
		saveOrUpdate(sql, playerName, userId);
	}

	public void addUserCache(User user) {
		hmset(USER_CACHE + user.getUserId(), USER_FIELD_MAP.objectToMap(user));
	}

	public User getUserFromCache(int userId) {
		return hgetAll(USER_CACHE + userId, USER_JEDIS_ROWMAPPER);
	}

	public void delUserCache(int userId) {
		this.del(USER_CACHE + userId);
	}

	public void updateUserCache(int userId, Map<String, String> hash) {
		String key = USER_CACHE + userId;
		hash.put("updateTime", String.valueOf(new Date().getTime()));
		this.hmset(key, hash);
	}

	/**
	 * 随机冒险者
	 * 
	 * @return 冒险者
	 */
	public List<User> randomAdventurers(int level, List<Integer> notInUserIds) {

		StringBuilder sb = new StringBuilder();
		for (int userId : notInUserIds) {
			sb.append(userId).append(',');
		}
		sb.setLength(sb.length() - 1);
		String sql = "select * from t_user where user_id>=" + "(SELECT floor( RAND() *((SELECT MAX(user_id) FROM t_user)-"
		        + "(SELECT MIN(user_id) FROM t_user)) +" + "(SELECT MIN(user_id) FROM t_user))) " + "and `level` between ? and ? and user_id not in("
		        + sb.toString() + ") order by last_login_time desc limit 3;";
		return queryForList(sql, ROW_MAPPER, level - 5, level + 5);
	}

	public void updateLevelRank(int userId, int level) {
		zadd(USER_LEVEL_RANK, level, userId + "");
	}

	public void updateChapterRank(int userId, int chapterId) {
		zadd(USER_CHAPTER_RANK, chapterId, userId + "");
	}
	
	public void removeUserLevel(int userId){
		srem(USER_LEVEL_RANK, userId + "");
	}

	/**
	 * 等级排行前10
	 * 
	 * @return
	 */
	public List<Integer> gainUserLevel() {
		Set<String> set = zrevrangeByScore(USER_LEVEL_RANK, Double.MAX_VALUE, 0, 0, 10);
		List<Integer> list = new ArrayList<>();
		for (String userId : set) {
			list.add(Integer.valueOf(userId));
		}
		return list;
	}
	
	/**
	 * 获取玩家等级排名
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getUserLevelRank(int userId) {
		Long r = zrevrank(USER_LEVEL_RANK, userId + "");
		return r == null ? 0 : r.intValue() + 1;
	}

	/**
	 * 推图排行前10
	 * 
	 * @return
	 */
	public List<Integer> gainChapterRankingTop10() {
		Set<String> set = zrevrangeByScore(USER_CHAPTER_RANK, Double.MAX_VALUE, 0, 0, 10);
		List<Integer> list = new ArrayList<>();
		for (String userId : set) {
			list.add(Integer.valueOf(userId));
		}
		return list;
	}

	/**
	 * 获取玩家推图排名
	 * 
	 * @param userId
	 * @return
	 */
	public Integer getUserChapterRank(int userId) {
		Long r = zrevrank(USER_CHAPTER_RANK, userId + "");
		return r == null ? 0 : r.intValue() + 1;
	}

	/**
	 * 随机冒险者
	 * 
	 * @return 冒险者
	 */
	public List<Integer> randomAdventurers(int level) {
		int count = (int) zcount(USER_LEVEL_RANK, level - 2, level + 2);
		Set<String> set = zrangeByScore(USER_LEVEL_RANK, level - 2, level + 2, (int) (Math.random() * count), 10);
		List<Integer> list = new ArrayList<>();
		for (String userId : set) {
			list.add(Integer.valueOf(userId));
		}
		return list;
	}

	public List<User> getUsers(UserFilter filter) {
		StringBuffer sql = new StringBuffer("select * from t_user where 1= 1 ");
		List<Object> val = new ArrayList<Object>();
		if (filter.getUserId() != 0) {
			sql.append(" and user_id = ? ");
			val.add(filter.getUserId());
		}
		if (filter.getPartner() != 0) {
			sql.append(" and partner = ? ");
			val.add(filter.getPartner());
		}
		if (filter.getUsername() != null) {
			sql.append(" and username like concat('%',?,'%') ");
			val.add(filter.getUsername());
		}
		if (filter.getPlayerName() != null) {
			sql.append(" and player_name like concat('%',?,'%') ");
			val.add(filter.getPlayerName());
		}
		sql.append(" ORDER BY `level` desc limit 50");
		return queryForList(sql.toString(), ROW_MAPPER, val.toArray());
	}

	/**
	 * 玩家等级统计
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> statisticsUserLevel() throws SQLException {
		return queryForListMap("SELECT `LEVEL` AS `level` ,COUNT(*) AS `count` FROM `t_user`  GROUP BY `level` ");
	}

	/**
	 * 玩家流失统计
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> statisticsUserGuide() throws SQLException {
		return queryForListMap("SELECT `guide_step` AS 'guideStep', `LEVEL` AS 'level' , count(*) AS 'count' FROM t_user WHERE guide_step != 100 GROUP BY `level` DESC ,`guide_step` ");
	}
}
