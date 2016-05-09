package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.shop.ProductItem;

/**
 * 商品物品信息
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月16日
 */
public class ProductItemDAO extends GameCfgDAOTemplate {

	private static final String TABLE = "t_product_item";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(product_id, goods_id, goods_type, goods_num, goods_level, rate) VALUES(?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET product_id=?, goods_id=?, goods_type=?, goods_num=?, goods_level=?, rate=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ProductItem> ROW_MAPPER = new RowMapper<ProductItem>() {
		@Override
		public ProductItem rowMapper(ResultSet rs) throws SQLException {
			ProductItem obj = new ProductItem();
			obj.setId(rs.getInt("id"));
			obj.setProductId(rs.getInt("product_id"));
			obj.setGoodsId(rs.getInt("goods_id"));
			obj.setGoodsType(rs.getInt("goods_type"));
			obj.setGoodsNum(rs.getInt("goods_num"));
			obj.setGoodsLevel(rs.getInt("goods_level"));
			obj.setRate(rs.getDouble("rate"));
			return obj;
		}
	};
	
	private static final ObjectFieldMap<ProductItem> FIELD_MAP = new ObjectFieldMap<ProductItem>() {
		@Override
		public Map<String, String> objectToMap(ProductItem o) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(o.getId()));
			map.put("productId", String.valueOf(o.getId()));
			map.put("goodsId", String.valueOf(o.getGoodsId()));
			map.put("goodsType", String.valueOf(o.getGoodsType()));
			map.put("goodsNum", String.valueOf(o.getGoodsNum()));
			map.put("goodsLevel", String.valueOf(o.getGoodsLevel()));
			map.put("rate", String.valueOf(o.getRate()));
			return map;
		}
	};
	
	private static final JedisRowMapper<ProductItem> JEDIS_ROW_MAPPER = new JedisRowMapper<ProductItem>() {
		@Override
		public ProductItem rowMapper(JedisResultSet jrs) {
			ProductItem obj = new ProductItem();
			obj.setId(jrs.getInt("id"));
			obj.setProductId(jrs.getInt("productId"));
			obj.setGoodsId(jrs.getInt("goodsId"));
			obj.setGoodsType(jrs.getInt("goodsType"));
			obj.setGoodsNum(jrs.getInt("goodsNum"));
			obj.setGoodsLevel(jrs.getInt("goodsLevel"));
			obj.setRate(jrs.getDouble("rate"));
			return obj;
		}
	};
	
	public void addProductItem(ProductItem entity) {
		saveOrUpdate(SQL_ADD, entity.getProductId(), entity.getGoodsId(), entity.getGoodsType(), entity.getGoodsNum(), entity.getGoodsLevel(), entity.getRate());
	}

	public void updateProductItem(ProductItem entity) {
		saveOrUpdate(SQL_UPDATE, entity.getProductId(), entity.getGoodsId(), entity.getGoodsType(), entity.getGoodsNum(), entity.getGoodsLevel(), entity.getRate(), entity.getId());
	}
	
	public void deleteProductItem(int id) {
		saveOrUpdate(SQL_DELETE, id);
	}

	public List<ProductItem> queryProductItemByProductId(int productId) {
		String sql = SQL_SELECT + " and product_id=?";
		return queryForList(sql, ROW_MAPPER, productId);
	}
	
	public ProductItem queryProductById(int id) {
		String sql = SQL_SELECT + " and id=?";
		return queryForEntity(sql, ROW_MAPPER, id);
	}
}