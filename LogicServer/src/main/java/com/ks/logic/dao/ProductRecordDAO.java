package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.shop.ProductRecord;

/**
 * 商品购买记录
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月18日
 */
public class ProductRecordDAO extends GameDAOTemplate {

private static final String TABLE = "t_product_record";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(product_id, product_num, user_id, create_time) VALUES(?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET product_id=?, product_num=?, user_id=?, create_time=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<ProductRecord> ROW_MAPPER = new RowMapper<ProductRecord>() {
		@Override
		public ProductRecord rowMapper(ResultSet rs) throws SQLException {
			ProductRecord obj = new ProductRecord();
			obj.setId(rs.getInt("id"));
			obj.setProductId(rs.getInt("product_id"));
			obj.setProductNum(rs.getInt("product_num"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			return obj;
		}
	};
	
	public void add(ProductRecord entity) {
		saveOrUpdate(SQL_ADD, entity.getProductId(), entity.getProductNum(), entity.getUserId(), entity.getCreateTime());
	}

	public void update(ProductRecord entity) {
		saveOrUpdate(SQL_UPDATE, entity.getProductId(), entity.getProductNum(), entity.getUserId(), entity.getCreateTime(), entity.getId());
	}
	
	public void delete(int id) {
		saveOrUpdate(SQL_DELETE, id);
	}

	public List<ProductRecord> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
	
	public List<ProductRecord> findByPage(Map<String, Object> params, Integer offset, Integer rows) {
		List<Object> list = new ArrayList<Object>();
		String sql = SQL_SELECT;
		
		if(params.get("productId") != null) {
			sql = sql + " and product_id=?";
			list.add(params.get("productId"));
		}
		
		if(params.get("userId") != null) {
			sql = sql + " and user_id=?";
			list.add(params.get("userId"));
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
		
		if(params.get("productId") != null) {
			sql = sql + " and product_id=?";
			list.add(params.get("productId"));
		}
		
		if(params.get("userId") != null) {
			sql = sql + " and user_id=?";
			list.add(params.get("userId"));
		}
		
		return queryForEntity(sql, INT_ROW_MAPPER, list.toArray());
	}
}