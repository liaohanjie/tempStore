package com.ks.logic.dao.dynamic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDynamicDaoTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.ZoneConfig;
import com.ks.model.activity.ActivityDefine;
import com.ks.model.activity.ActivityPrice;
import com.ks.model.activity.BuyCoinGift;
import com.ks.model.activity.FlashGiftBag;
import com.ks.model.goods.Goods;
import com.ks.util.JSONUtil;

/**
 * 
 * @author living.li
 * @date 2014年4月9日
 */
public class ActivityDAO extends GameDynamicDaoTemplate {


	public static final String ACTIVITY_CACHE_KEY = "ACTIVITY_CACHE_KEY";


	private static final RowMapper<ZoneConfig> ZONE_CONFIG_MAPPER = new RowMapper<ZoneConfig>() {
		@Override
		public ZoneConfig rowMapper(ResultSet rs) throws SQLException {
			ZoneConfig zone = new ZoneConfig();
			zone.setId(rs.getInt("id"));
			zone.setVal(rs.getInt("val"));
			zone.setAcAthleticsPoint(rs.getInt("ac_athletics_point"));
			zone.setAcFriendCapacityPrice(rs.getInt("ac_friend_capacity_price"));
			zone.setAcItemCapacityPrice(rs.getInt("ac_item_capacity_price"));
			zone.setAcSoulCapacityPrice(rs.getInt("ac_soul_capacity_price"));
			zone.setAcStaminaPrice(rs.getInt("ac_stamina_price"));
			zone.setCreateTime(rs.getTimestamp("create_time"));
			zone.setUpdateTime(rs.getTimestamp("update_time"));
			return zone;
		}
	};
	
	private static final String getActivityKey(int id) {
		return ACTIVITY_CACHE_KEY + SPEET + id;
	}


	private static final ObjectFieldMap<ActivityDefine> FIELD_MAP = new ObjectFieldMap<ActivityDefine>() {
		@Override
		public Map<String, String> objectToMap(ActivityDefine o) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(o.getId()));
			map.put("defineId", String.valueOf(o.getDefineId()));
			map.put("type", String.valueOf(o.getType()));
			map.put("startTime", String.valueOf(o.getStartTime().getTime()));
			map.put("endTime", String.valueOf(o.getEndTime().getTime()));
			map.put("startHour", String.valueOf(o.getStartHour()));
			map.put("endHour", String.valueOf(o.getEndHour()));
			map.put("weekTime", o.getWeekTime());
			map.put("createTime", String.valueOf(o.getCreateTime().getTime()));
			map.put("updateTime", String.valueOf(o.getUpdateTime().getTime()));
			map.put("chapterIds", o.getChapterIds());
			map.put("title", o.getTitle());
			map.put("context", o.getContext());
			map.put("name", o.getName());
			map.put("status", String.valueOf(o.getStatus()));
			map.put("typeClass", String.valueOf(o.getTypeClass()));
			return map;
		}
	};
	private static final RowMapper<ActivityDefine> ACTIVITY_DEFINE_ROW_MAPPER = new RowMapper<ActivityDefine>() {
		@Override
		public ActivityDefine rowMapper(ResultSet rs) throws SQLException {
			ActivityDefine obj = new ActivityDefine();
			obj.setId(rs.getInt("id"));
			obj.setDefineId(rs.getInt("define_id"));
			obj.setName(rs.getString("name"));
			obj.setType(rs.getInt("type"));
			obj.setStartTime(rs.getTimestamp("start_time"));
			obj.setEndTime(rs.getTimestamp("end_time"));
			obj.setStartHour(rs.getInt("start_hour"));
			obj.setEndHour(rs.getInt("end_hour"));
			obj.setWeekTime(rs.getString("week_time"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			obj.setChapterIds(rs.getString("chapter_ids"));
			obj.setTitle(rs.getString("title"));
			obj.setContext(rs.getString("context"));
			obj.setStatus(rs.getInt("status"));
			obj.setTypeClass(rs.getInt("type_class"));
			return obj;
		}
	};
	
	private static final JedisRowMapper<ActivityDefine> JEDIS_ROW_MAPPER = new JedisRowMapper<ActivityDefine>() {
		@Override
		public ActivityDefine rowMapper(JedisResultSet jrs) {
			ActivityDefine obj = new ActivityDefine();
			obj.setId(jrs.getInt("id"));
			obj.setDefineId(jrs.getInt("defineId"));
			obj.setType(jrs.getInt("type"));
			obj.setStartTime(jrs.getDate("startTime"));
			obj.setEndTime(jrs.getDate("endTime"));
			obj.setStartHour(jrs.getInt("startHour"));
			obj.setEndHour(jrs.getInt("endHour"));
			obj.setWeekTime(jrs.getString("weekTime"));
			obj.setCreateTime(jrs.getDate("createTime"));
			obj.setUpdateTime(jrs.getDate("updateTime"));
			obj.setChapterIds(jrs.getString("chapterIds"));
			obj.setTitle(jrs.getString("title"));
			obj.setContext(jrs.getString("context"));
			obj.setName(jrs.getString("name"));
			obj.setStatus(jrs.getInt("status"));
			obj.setTypeClass(jrs.getInt("typeClass"));
			return obj;
		}
	};
	
	private List<ActivityDefine> queryAllActivity() {
		String sql = "select * from t_activity_define";
		return queryForList(sql, ACTIVITY_DEFINE_ROW_MAPPER);
	}
	
	/**
	 * 重新载入活动缓存
	 * @return
	 */
	public List<ActivityDefine> reloadActivityCache() {
		// 删除所有相关活动缓存信息
		Set<String> keys = smembers(ACTIVITY_CACHE_KEY);
		keys.add(ACTIVITY_CACHE_KEY);
		del(keys.toArray(new String[keys.size()]));
		
		List<ActivityDefine> list = queryAllActivity();
		for (ActivityDefine de : list) {
			addActivityCache(de);
		}
		return list;
	}
	/**
	 * 从缓存中查询所有活动信息
	 * @return
	 */
	public List<ActivityDefine> queryAllActivityCache() {
		Set<String> keys = smembers(ACTIVITY_CACHE_KEY);
		List<ActivityDefine> list = hgetAll(JEDIS_ROW_MAPPER, keys);
		if (list == null || list.isEmpty()) {
			list = reloadActivityCache();
		}
		return list;
	}
	
	/**
	 * 查询指定活动信息
	 * @param defineId
	 * @return
	 */
	public List<ActivityDefine> queryActivityCacheByDefineId(int defineId) {
		List<ActivityDefine> list = new ArrayList<>();
		for (ActivityDefine activity : queryAllActivityCache()) {
			if (activity.getDefineId() == defineId){
				list.add(activity);
			}
		}
		return list;
	}
	
	/**
	 * 向Redis里面保存活动key和活动对象
	 * @param ad
	 */
	private void addActivityCache(ActivityDefine ad) {
		String mapKey = getActivityKey(ad.getId());
		
		// 保存活动key到Redis set集合内
		sadd(ACTIVITY_CACHE_KEY, mapKey);
		
		// 保存活动信息到Redis内
		hmset(mapKey, FIELD_MAP.objectToMap(ad));
	}
	
	/**
	 * 查询指定活动信息
	 * @param id
	 * @return
	 */
	public ActivityDefine queryActivityCacheById(int id) {
		String key = getActivityKey(id);
		return hgetAll(key, JEDIS_ROW_MAPPER);
	}

	public void updateActivityCache(ActivityDefine ad) {
		updateActivity(ad);
		ad.setUpdateTime(new Date());
		String key = getActivityKey(ad.getId());
		hmset(key, FIELD_MAP.objectToMap(ad));
	}

	private void updateActivity(ActivityDefine d) {
		String sql = "update t_activity_define set define_id=?,start_time=?,end_time=?,start_hour=?,end_hour=?,week_time=?,chapter_ids=?,title=?,context=?,status=? where id=?";
		saveOrUpdate(sql, d.getDefineId(), d.getStartTime(), d.getEndTime(), d.getStartHour(),
				d.getEndHour(), d.getWeekTime(), d.getChapterIds(),
				d.getTitle(), d.getContext(), d.getStatus(), d.getId());
	}

	private static final RowMapper<BuyCoinGift> BUY_COIN_GIFT_ROW_MAPPER = new RowMapper<BuyCoinGift>() {
		@Override
		public BuyCoinGift rowMapper(ResultSet rs) throws SQLException {
			BuyCoinGift obj = new BuyCoinGift();
			obj.setId(rs.getInt("id"));
			obj.setGameCoin(rs.getInt("game_coin"));
			obj.setGoods(getGoodsList(rs.getString("goods")));
			obj.setMailTital(rs.getString("mail_tital"));
			obj.setMailContext(rs.getString("mail_context"));
			return obj;
		}
	};

	private static List<Goods> getGoodsList(String json) {
		if (json == null || "".equals(json)) {
			return null;
		} else {
			return JSONUtil.toObject(json, new TypeReference<List<Goods>>() {});
		}
	}

	public BuyCoinGift getBuyCoingift() {
		String sql = "select * from t_buy_coin_gift";
		return queryForEntity(sql, BUY_COIN_GIFT_ROW_MAPPER);
	}

	// ---------------------------------------------------------------------------------------
	public void updateBuyCoinGift(BuyCoinGift gift){
		String sql="update t_buy_coin_gift set game_coin=?, goods=?,mail_tital=?,mail_context=?";
		saveOrUpdate(sql, gift.getGameCoin(),JSONUtil.toJson(gift.getGoods()),gift.getMailTital(),gift.getMailContext());
	}
	//---------------------------------------------------------------------------------------
	private static final RowMapper<FlashGiftBag> FlASH_GIFT_BAG_ROW = new RowMapper<FlashGiftBag>(){
		@Override
		public FlashGiftBag rowMapper(ResultSet rs) throws SQLException {
			FlashGiftBag obj = new FlashGiftBag();
			obj.setId(rs.getInt("id"));
			obj.setMaxNum(rs.getInt("max_num"));
			obj.setBuyNum(rs.getInt("buy_num"));
			obj.setPrice(rs.getInt("price"));
			obj.setGoods(getGoodsList(rs.getString("goods")));
			obj.setMailTital(rs.getString("mail_tital"));
			obj.setMailContext(rs.getString("mail_context"));
			return obj;
		}
	};

	// 限时礼包
	public FlashGiftBag getFlashGift() {
		String sql = "select * from t_flash_gift_bag";
		return queryForEntity(sql, FlASH_GIFT_BAG_ROW);
	}
	
	public void updateFlashGiftBag(FlashGiftBag bag){
		String sql="update t_flash_gift_bag set buy_num=?,max_num=?,price=?,goods=?,mail_tital=?,mail_context=? ";
		saveOrUpdate(sql, bag.getBuyNum(),bag.getMaxNum(),bag.getPrice(),JSONUtil.toJson(bag.getGoods()),bag.getMailTital(),bag.getMailContext());
	}
	public FlashGiftBag getFlashGiftForLock(int id) {
		String sql = "select * from t_flash_gift_bag  where id=1 for update";
		return queryForEntity(sql, FlASH_GIFT_BAG_ROW);
	}

	public synchronized void incrementFlashBag(int id) {
		String sql = "update t_flash_gift_bag set buy_num=buy_num+1 where id=?";
		saveOrUpdate(sql, id);
	}

	private static final RowMapper<ActivityPrice> ACTIVITY_PRICE_ROW_MAPPER = new RowMapper<ActivityPrice>() {
		@Override
		public ActivityPrice rowMapper(ResultSet rs) throws SQLException {
			ActivityPrice obj = new ActivityPrice();
			obj.setId(rs.getInt("id"));
			obj.setVal(rs.getInt("val"));
			obj.setAcSoulCapacityPrice(rs.getInt("ac_soul_capacity_price"));
			obj.setAcFriendCapacityPrice(rs.getInt("ac_friend_capacity_price"));
			obj.setAcItemCapacityPrice(rs.getInt("ac_item_capacity_price"));
			obj.setAcStaminaPrice(rs.getInt("ac_stamina_price"));
			obj.setAcAthleticsPoint(rs.getInt("ac_athletics_point"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			return obj;
		}
	};

	public void updateActivityPrice(ActivityPrice price) {
		String sql = "update t_zone_config set val=?,ac_soul_capacity_price=?,ac_friend_capacity_price=?,ac_item_capacity_price=?,ac_stamina_price=?,ac_athletics_point=?,create_time=?,update_time=? where id=?";
		saveOrUpdate(sql, price.getVal(), price.getAcSoulCapacityPrice(),
				price.getAcFriendCapacityPrice(),
				price.getAcItemCapacityPrice(), price.getAcStaminaPrice(),
				price.getAcAthleticsPoint(), price.getCreateTime(),
				price.getUpdateTime(), price.getId());
	}

	public ActivityPrice getActivityPrice() {
		String sql = "select * from t_zone_config";
		return queryForEntity(sql, ACTIVITY_PRICE_ROW_MAPPER);
	}

	public ZoneConfig queryUserIdSeedConfig() {
		String sql = "select * from t_zone_config where id=? limit 1 for update";
		return queryForEntity(sql, ZONE_CONFIG_MAPPER, ZoneConfig.ID_USER_ID_SEED);
	}

	public ZoneConfig queryZoneConfig(int id) {
		String sql = "select * from t_zone_config where id=?";
		return queryForEntity(sql, ZONE_CONFIG_MAPPER, id);
	}

	public void updateZoneConfig(ZoneConfig config) {
		String sql = "update t_zone_config set val=?,update_time=now() where id=?";
		saveOrUpdate(sql, config.getVal(), config.getId());
	}
}
