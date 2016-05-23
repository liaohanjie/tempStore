package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.pay.OrderReturn;

/**
 * 订单返还
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年4月14日
 */
public class OrderReturnDAO extends GameDAOTemplate {
	
	private final static String TABLE = "t_order_return";
	private final static String SQL_SELECT = "select * from " + TABLE + " where 1=1";
//	private final static String SQL_ADD = "INSERT INTO `" + TABLE + "` (`user_name`, `order_no`, `amount`, `status`, `return_time`) VALUES (?,?,?,?)";
//	private final static String SQL_DELETE = "delete from " + TABLE + " where id=?";
	private final static String SQL_UPDATE = "update " + TABLE;
	
	
	private static final RowMapper<OrderReturn> ROW_MAPPER = new RowMapper<OrderReturn>() {
		@Override
		public OrderReturn rowMapper(ResultSet rs) throws SQLException {
			OrderReturn entity = new OrderReturn();
			entity.setId(rs.getInt("id"));
			entity.setUserName(rs.getString("user_name"));
			entity.setOrderNo(rs.getString("order_no"));
			entity.setAmount(rs.getInt("amount"));
			entity.setStatus(rs.getInt("status"));
			//entity.setScale(rs.getDouble("scale"));
			entity.setReturnTime(rs.getTimestamp("return_time"));
			return entity;
		}
	};
	
	public List<OrderReturn> queryByUserName(String userName){
		String sql = SQL_SELECT + " and `user_name`=? and status=0";
		return queryForList(sql, ROW_MAPPER, userName);
	}
	
	public void updateStatus(String userName){
		String sql = SQL_UPDATE + " set status=1,return_time=now() where user_name=?";
		saveOrUpdate(sql, userName);
	}
}
