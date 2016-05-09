package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.shop.MysteryShopItem;
import com.ks.util.JSONUtil;

/**
 * 神秘商店DAO
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月11日
 */
public class MysteryShopItemDAO extends GameCfgDAOTemplate {

	private static final String MYSTERY_SHOP_CACHE_KEY = "MYSTERY_SHOP_";
	
	private static final String TABLE = "t_mystery_shop_item";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
//	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
//	
//	private static final String SQL_ADD = "INSERT into " + TABLE + "(num, base_gold) VALUES(?,?,?)";
//	
//	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET num=?,base_gold=? WHERE id=?";
//	
//	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<MysteryShopItem> ROW_MAPPER = new RowMapper<MysteryShopItem>() {
		@Override
		public MysteryShopItem rowMapper(ResultSet rs) throws SQLException {
			MysteryShopItem entity = new MysteryShopItem();
			entity.setId(rs.getInt("id"));
			entity.setType(rs.getInt("type"));
			entity.setPrice(rs.getInt("price"));
			entity.setGoodsId(rs.getInt("goods_id"));
			entity.setGoodsType(rs.getInt("goods_type"));
			entity.setLevel(rs.getInt("level"));
			entity.setNum(rs.getInt("num"));
			entity.setWeight(rs.getInt("weight"));
			return entity;
		}
	};
	
//	private static final ObjectFieldMap<MysteryShopItem> FIELD_MAP = new ObjectFieldMap<MysteryShopItem>() {
//		@Override
//		public Map<String, String> objectToMap(MysteryShopItem o) {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("id", String.valueOf(o.getId()));
//			map.put("type", String.valueOf(o.getType()));
//			map.put("price", String.valueOf(o.getPrice()));
//			map.put("goodsId", String.valueOf(o.getGoodsId()));
//			map.put("goodsType", String.valueOf(o.getGoodsType()));
//			map.put("level", String.valueOf(o.getLevel()));
//			map.put("num", String.valueOf(o.getNum()));
//			map.put("weight", String.valueOf(o.getWeight()));
//			return map;
//		}
//	};
//	
//	private static final JedisRowMapper<MysteryShopItem> JEDIS_ROW_MAPPER = new JedisRowMapper<MysteryShopItem>() {
//		@Override
//		public MysteryShopItem rowMapper(JedisResultSet jrs) {
//			MysteryShopItem entity = new MysteryShopItem();
//			entity.setId(jrs.getInt("id"));
//			entity.setType(jrs.getInt("type"));
//			entity.setPrice(jrs.getInt("price"));
//			entity.setGoodsId(jrs.getInt("goodsId"));
//			entity.setGoodsType(jrs.getInt("goodsType"));
//			entity.setLevel(jrs.getInt("level"));
//			entity.setNum(jrs.getInt("num"));
//			entity.setWeight(jrs.getInt("weight"));
//			return entity;
//		}
//	};
	
	public List<MysteryShopItem> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
	
	public List<MysteryShopItem> queryByType(int type) {
		return queryForList(SQL_SELECT + " and `type`=?", ROW_MAPPER, type);
	}
	
	public MysteryShopItem findById(int id) {
		return queryForEntity(SQL_SELECT + " and id=?", ROW_MAPPER, id);
	}
	
	/**
	 * 保存神秘商店到缓存, userId=0为系统公用
	 * @param userId
	 * @param list
	 */
	public void saveCache(int userId, List<MysteryShopItem> list) {
		set(MYSTERY_SHOP_CACHE_KEY + userId, JSONUtil.toJson(list));
		if (userId > 0) {
			// 24小时候过期
			expire(MYSTERY_SHOP_CACHE_KEY + userId, 3600*24);
		}
	}
	
	public void saveCache( List<MysteryShopItem> list) {
		set(MYSTERY_SHOP_CACHE_KEY + 0, JSONUtil.toJson(list));
	}
	
	public List<MysteryShopItem> getCache(int userId) {
		String value = get(MYSTERY_SHOP_CACHE_KEY + userId);
		
		if (value == null || value.trim().equals("")) {
			return null;
		}
		return JSONUtil.toObject(value, new TypeReference<List<MysteryShopItem>>() {});
	}
	
	/**
	 * 获取公用神秘商店缓存
	 * @return
	 */
	public List<MysteryShopItem> getCache() {
		return getCache(0);
	}
}