package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.cache.JedisResultSet;
import com.ks.cache.JedisRowMapper;
import com.ks.cache.ObjectFieldMap;
import com.ks.model.shop.Product;

/**
 * 商品信息
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月14日
 */
public class ProductDAO extends GameCfgDAOTemplate {

	public static final String ACTIVITY_GIFT_KEY = "PRODUCT_CACHE_KEY";
	
	private static final String TABLE = "t_product";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(class_id,type_id, buy_count, product_name, product_desc, price, gold, num, `status`, create_time) VALUES(?,?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET class_id=?, type_id=?, buy_count=?, product_name=?, product_desc=?, price=?, gold=?, num=?, `status`=?, create_time=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<Product> ROW_MAPPER = new RowMapper<Product>() {
		@Override
		public Product rowMapper(ResultSet rs) throws SQLException {
			Product obj = new Product();
			obj.setId(rs.getInt("id"));
			obj.setClassId(rs.getInt("class_id"));
			obj.setTypeId(rs.getInt("type_id"));
			obj.setBuyCount(rs.getInt("buy_count"));
			obj.setProductName(rs.getString("product_name"));
			obj.setProductDesc(rs.getString("product_desc"));
			obj.setPrice(rs.getInt("price"));
			obj.setGold(rs.getInt("gold"));
			obj.setNum(rs.getInt("num"));
			obj.setStatus(rs.getByte("status"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	private static final ObjectFieldMap<Product> FIELD_MAP = new ObjectFieldMap<Product>() {
		@Override
		public Map<String, String> objectToMap(Product o) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", String.valueOf(o.getId()));
			map.put("classId", String.valueOf(o.getClassId()));
			map.put("typeId", String.valueOf(o.getId()));
			map.put("buyCount", String.valueOf(o.getBuyCount()));
			map.put("productName", o.getProductName());
			map.put("productDesc", o.getProductDesc());
			map.put("price", String.valueOf(o.getPrice()));
			map.put("gold", String.valueOf(o.getGold()));
			map.put("num", String.valueOf(o.getNum()));
			map.put("status", String.valueOf(o.getStatus()));
			map.put("createTime", String.valueOf(o.getCreateTime().getTime()));
			return map;
		}
	};
	
	private static final JedisRowMapper<Product> JEDIS_ROW_MAPPER = new JedisRowMapper<Product>() {
		@Override
		public Product rowMapper(JedisResultSet jrs) {
			Product obj = new Product();
			obj.setId(jrs.getInt("id"));
			obj.setClassId(jrs.getInt("classId"));
			obj.setTypeId(jrs.getInt("typeId"));
			obj.setBuyCount(jrs.getInt("buyCount"));
			obj.setProductName(jrs.getString("productName"));
			obj.setProductDesc(jrs.getString("productDesc"));
			obj.setPrice(jrs.getInt("price"));
			obj.setGold(jrs.getInt("gold"));
			obj.setNum(jrs.getInt("num"));
			obj.setStatus(jrs.getByte("status"));
			obj.setCreateTime(jrs.getDate("createTime"));
			return obj;
		}
	};
	
//	public void addProduct(Product entity) {
//		saveOrUpdate(SQL_ADD, entity.getClassId(), entity.getTypeId(), entity.getBuyCount(), entity.getProductName(), entity.getProductDesc(), entity.getPrice(), entity.getGold(), entity.getNum(), entity.getStatus(), entity.getCreateTime());
//	}
//
//	public void updateProduct(Product entity) {
//		saveOrUpdate(SQL_UPDATE, entity.getClassId(), entity.getTypeId(), entity.getBuyCount(), entity.getProductName(), entity.getProductDesc(), entity.getPrice(), entity.getGold(), entity.getNum(), entity.getStatus(), entity.getCreateTime());
//	}
//	
//	public void deleteProduct(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public List<Product> queryAllProduct() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
	
	public List<Product> queryProductByClassId(int classId) {
		String sql = SQL_SELECT + " and class_id=?";
		return queryForList(sql, ROW_MAPPER, classId);
	}
	
	public Product queryProductById(int id) {
		String sql = SQL_SELECT + " and id=?";
		return queryForEntity(sql, ROW_MAPPER, id);
	}
	
	public Product findProductByTypeId(int typeId) {
		String sql = SQL_SELECT + " and type_id=?";
		return queryForEntity(sql, ROW_MAPPER, typeId);
	}
	
	public List<Product> findByPage(Map<String, Object> params, Integer offset, Integer rows) {
		List<Object> list = new ArrayList<Object>();
		String sql = SQL_SELECT;
		
		if(params.get("classId") != null) {
			sql = sql + " and class_id=?";
			list.add(params.get("classId"));
		}
		
		if(params.get("typeId") != null) {
			sql = sql + " and type_id=?";
			list.add(params.get("typeId"));
		}
		
		if(params.get("productName") != null) {
			sql = sql + " and product_name like ?";
			list.add(params.get("productName"));
		}
		
		if(offset != null && rows != null && rows > 0) {
			sql = sql + " limit ?,?";
			list.add(offset);
			list.add(rows);
		}
		return queryForList(sql, ROW_MAPPER, list.toArray());
	}
	
	public Integer count(Map<String, Object> params){
		List<Object> list = new ArrayList<Object>();
		String sql = SQL_COUNT;
		
		if(params.get("classId") != null) {
			sql = sql + " and class_id=?";
			list.add(params.get("classId"));
		}
		
		if(params.get("typeId") != null) {
			sql = sql + " and type_id=?";
			list.add(params.get("typeId"));
		}
		
		if(params.get("productName") != null) {
			sql = sql + " and product_name LIKE CONCAT('%',?,'%' )";
			list.add(params.get("productName"));
		}
		
		return queryForEntity(sql, INT_ROW_MAPPER, list.toArray());
	}
}