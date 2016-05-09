package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.dungeon.Box;
import com.ks.model.dungeon.CurrFight;
import com.ks.model.goods.Goods;
import com.ks.model.user.UserChapter;
import com.ks.util.JSONUtil;
/**
 * 用户副本
 * @author ks
 */
public class UserChapterDAO extends GameDAOTemplate {
	
	private static final String getTableName(int userId){
		return "t_user_chapter_"+userId%10;
	}
	
	private static final String getFightKey(int userId){
		return "USER_DUNGEON_"+userId;
	}
	
	private static final RowMapper<UserChapter> ROW_MAPPER = new RowMapper<UserChapter>(){
		@Override
		public UserChapter rowMapper(ResultSet rs) throws SQLException{
			UserChapter obj = new UserChapter();
			obj.setUserId(rs.getInt("user_id"));
			obj.setChapterId(rs.getInt("chapter_id"));
			obj.setPassCount(rs.getInt("pass_count"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			obj.setSameDayBuyCount(rs.getInt("same_day_buy_count"));
			obj.setSameDayCount(rs.getInt("same_day_count"));
			return obj;
		}
	};
	
	private static final ObjectFieldMap<CurrFight> FIELD_MAP = new ObjectFieldMap<CurrFight>(){
		@Override
		public Map<String, String> objectToMap(CurrFight o){
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId",String.valueOf(o.getUserId()));
			map.put("chapterId",String.valueOf(o.getChapterId()));
			map.put("awards",JSONUtil.toJson(o.getAwards()));
			map.put("boxes", JSONUtil.toJson(o.getBoxes()));
			map.put("exp", JSONUtil.toJson(o.getExp()));
			map.put("joinMonsters", String.valueOf(o.getMonsters()));
			map.put("joinGoods", JSONUtil.toJson(o.getJoinGoods()));
			map.put("friendlyPoint", String.valueOf(o.getFriendlyPoint()));
			map.put("friendId", String.valueOf(o.getFriendId()));
			map.put("resurrectionNum", String.valueOf(o.getResurrectionNum()));
			return map;
		}
	};
	/**
	 * 
	 */
	private static final JedisRowMapper<CurrFight> JEDIS_MAPPER = new JedisRowMapper<CurrFight>(){
		@Override
		public CurrFight rowMapper(JedisResultSet jrs) {
			CurrFight obj = new CurrFight();
			obj.setUserId(jrs.getInt("userId"));
			obj.setChapterId(jrs.getInt("chapterId"));
			obj.setAwards(JSONUtil.toObject(jrs.getString("awards"), new TypeReference<List<Goods>>(){}));
			obj.setBoxes(JSONUtil.toObject(jrs.getString("boxes"), new TypeReference<List<Box>>() {}));
			obj.setFriendlyPoint(jrs.getInt("friendlyPoint"));
			obj.setExp(jrs.getInt("exp"));
			obj.setJoinGoods(JSONUtil.toObject(jrs.getString("joinGoods"),  new TypeReference<List<Goods>>() {}));
			obj.setJoinMonsters(jrs.getString("joinMonsters"));
			obj.setFriendId(jrs.getInt("friendId"));
			obj.setResurrectionNum(jrs.getInt("resurrectionNum"));
			return obj;
		}
	};
	public void addUserChapter(UserChapter ud){
		String sql = "insert into "+getTableName(ud.getUserId())+
				"(user_id,chapter_id,pass_count,create_time,update_time, same_day_buy_count, same_day_count) values(?,?,?,now(),now(),?,?)";
		saveOrUpdate(sql, ud.getUserId(),ud.getChapterId(),ud.getPassCount(),ud.getSameDayBuyCount(), ud.getSameDayCount());
	}
	public void delUserChapter(int userId){
		String sql = "delete from "+getTableName(userId)+
				" where user_id=? ";
		   saveOrUpdate(sql,userId);
	}
	
	public void updateUserChapter(UserChapter ud){
		String sql = "update "+getTableName(ud.getUserId())+
				" set pass_count=?,update_time=now(),same_day_buy_count=?, same_day_count=? where user_id=? and chapter_id=?";
		saveOrUpdate(sql,ud.getPassCount(),ud.getSameDayBuyCount(), ud.getSameDayCount(), ud.getUserId(),ud.getChapterId());
	}
	
	public UserChapter queryUserChapter(int userId,int chapterId){
		String sql = "select * from "+getTableName(userId)+" where user_id=? and chapter_id=?";
		return queryForEntity(sql, ROW_MAPPER, userId,chapterId);
	}
	
	public List<UserChapter> queryUserFBChapterList(int userId){
		String sql = "select * from "+getTableName(userId)+" where user_id=? and (chapter_id >= 7020001 and chapter_id <7060001 or chapter_id >= 7070001 and chapter_id <7080001)";
		return queryForList(sql, ROW_MAPPER, userId);
	}
	
	public List<UserChapter> queryUserChapters(int userId,Collection<String> list){
		StringBuffer buff=new StringBuffer("select * from "+getTableName(userId)+" where user_id=? and chapter_id in(");
		List<Object> val=new ArrayList<Object>();
		val.add(userId);
		Iterator<String> iter=list.iterator();
		while(iter.hasNext()){
			buff.append("?,");
			val.add(iter.next());
		}
		if(buff.length()>0){			
			buff.replace(buff.length()-1,buff.length(),")");
		}
		return queryForList(buff.toString(), ROW_MAPPER, val.toArray());
	}
	
	public int queryUserChapterLimit(int userId,int limit){
		String sql = "select max(chapter_id) from "+getTableName(userId)+" where user_id=? and chapter_id<?";
		Integer id = queryForEntity(sql, INT_ROW_MAPPER, userId,limit);
		return id;
	}
	
	/**
	 * 重置副本当日数据
	 */
	public void reset(int userId){
		String sql = "update "+getTableName(userId)+" set same_day_count=0, same_day_buy_count=0 where user_id=?";
		saveOrUpdate(sql, userId);
	}
	
	public void updateCurrFightCache(CurrFight fight){
		hmset(getFightKey(fight.getUserId()), FIELD_MAP.objectToMap(fight));
	}
	
	public void delCurrFightCache(int userId){
		del(getFightKey(userId));
	}
	
	public CurrFight getCurrFight(int userId){
		return hgetAll(getFightKey(userId), JEDIS_MAPPER);
	}
}
