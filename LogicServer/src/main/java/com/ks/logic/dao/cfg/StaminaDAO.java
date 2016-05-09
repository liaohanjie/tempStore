package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.stamina.Stamina;

/**
 * 体力购买配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月18日
 */
public class StaminaDAO extends GameCfgDAOTemplate {

	private static final String TABLE = "t_stamina";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_COUNT = "SELECT count(*) FROM " + TABLE + " where 1=1";
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(buy_count,price) VALUES(?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET buy_count=?,price=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<Stamina> ROW_MAPPER = new RowMapper<Stamina>() {
		@Override
		public Stamina rowMapper(ResultSet rs) throws SQLException {
			Stamina entity = new Stamina();
			entity.setId(rs.getInt("id"));
			entity.setBuyCount(rs.getInt("buy_count"));
			entity.setPrice(rs.getInt("price"));
			return entity;
		}
	};
	
//	
//	public void add(Stamina entity) {
//		saveOrUpdate(SQL_ADD, entity.getBuyCount(), entity.getPrice());
//	}
//
//	public void update(Stamina entity) {
//		saveOrUpdate(SQL_UPDATE, entity.getBuyCount(), entity.getPrice(), entity.getId());
//	}
//	
//	public void delete(int id) {
//		saveOrUpdate(SQL_DELETE, id);
//	}

	public List<Stamina> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
}