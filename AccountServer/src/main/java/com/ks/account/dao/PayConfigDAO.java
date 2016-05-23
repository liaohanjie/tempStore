package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.account.PayConfig;

/**
 * 支付配置DAO
 * @author zhoujf
 * @date 2015年6月23日
 */
public class PayConfigDAO extends GameDAOTemplate {
	
	private final static String TABLE = "t_pay_config";
	private final static String SQL_SELECT = "select * from " + TABLE + " where 1=1";
	private final static String SQL_ADD = "insert into " + TABLE + "(id, game_id, partner_id, pay_channel_id, pay_channel_name, pay_channel_type_name, order_prefix, status ) values(?,?,?,?,?,?,?,?)";
	private final static String SQL_DELETE = "delete from " + TABLE + " where id=?";
	private final static String SQL_UPDATE = "update " + TABLE + " set game_id=?, partner_id=?, pay_channel_id=?, pay_channel_name=?,pay_channel_type_name=?, order_prefix=?, status=? where id=?";
	
	
	private static final RowMapper<PayConfig> ROW_MAPPER = new RowMapper<PayConfig>() {
		
		@Override
		public PayConfig rowMapper(ResultSet rs) throws SQLException {
			PayConfig entity = new PayConfig();
			entity.setId(rs.getInt("id"));
			entity.setGameId(rs.getInt("game_id"));
			entity.setPartnerId(rs.getInt("partner_id"));
			entity.setPayChannelId(rs.getString("pay_channel_id"));
			entity.setPayChannelName(rs.getString("pay_channel_name"));
			entity.setPayChannelTypeName(rs.getString("pay_channel_type_name"));
			entity.setOrderPrefix(rs.getString("order_prefix"));
			entity.setStatus(rs.getInt("status"));
			return entity;
		}
	};
	
	public List<PayConfig> queryAll(){
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
	
	public PayConfig queryById(Integer id){
		String sql = SQL_SELECT + " and id=?";
		return queryForEntity(sql, ROW_MAPPER, id);
	}
	
	public void add(PayConfig entity){
		saveOrUpdate(SQL_ADD, entity.getId(), entity.getGameId(), entity.getPartnerId(), entity.getPayChannelId(), entity.getPayChannelName(), entity.getPayChannelTypeName(), entity.getOrderPrefix(), entity.getStatus());
	}
	
	public void update(PayConfig entity){
		saveOrUpdate(SQL_UPDATE, entity.getGameId(), entity.getPartnerId(), entity.getPayChannelId(), entity.getPayChannelName(), entity.getPayChannelTypeName(), entity.getOrderPrefix(), entity.getStatus(), entity.getId());
	}
	
	public void delete(Integer id) {
		saveOrUpdate(SQL_DELETE, id);
	}
}
