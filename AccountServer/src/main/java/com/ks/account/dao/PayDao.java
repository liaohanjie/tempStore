/**
 * 
 */
package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.account.dao.opt.PayOrderOpt;
import com.ks.account.dao.opt.SQLOpt;
import com.ks.model.account.Partner;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.model.pay.RestitutionOrderLogger;

/**
 * @author living.li
 * @date 2015年5月19日 下午4:26:05
 * 
 * 
 */
public class PayDao extends GameDAOTemplate {

	private static final RowMapper<PayOrder> PAY_ORDER_ROW = new RowMapper<PayOrder>() {
		@Override
		public PayOrder rowMapper(ResultSet rs) throws SQLException {
			PayOrder order = new PayOrder();
			order.setPayId(rs.getInt("pay_id"));
			order.setOrderNo(rs.getString("order_no"));
			order.setServerId(rs.getString("server_id"));
			order.setUserName(rs.getString("user_name"));
			order.setUserParnter(rs.getInt("user_partner"));
			order.setGameCoin(rs.getInt("game_coin"));
			order.setRetryTimes(rs.getInt("retry_times"));
			order.setStatus(rs.getInt("status"));
			order.setPaySucessTime(rs.getTimestamp("pay_sucess_time"));
			order.setCreateTime(rs.getTimestamp("create_time"));
			order.setDeliverySucessTime(rs.getTimestamp("delivery_sucess_time"));
			order.setLastRetryTtime(rs.getTimestamp("last_retry_time"));
			order.setUpdateTime(rs.getTimestamp("update_time"));
			order.setAmount(rs.getInt("amount"));
			order.setUserId(rs.getInt("user_id"));
			order.setLastRetCode(rs.getString("last_ret_code"));
			order.setGiftCoin(rs.getInt("gift_coin"));
			order.setPayConfigId(rs.getInt("pay_config_id"));
			order.setBillNo(rs.getString("bill_no"));
			order.setGoodsId(rs.getInt("goods_id"));
			order.setExt1(rs.getString("ext1"));
			order.setExt2(rs.getString("ext2"));
			order.setExt3(rs.getString("ext3"));
			return order;
		}
	};

	private static final RowMapper<RestitutionOrderLogger> RESTITUTION_ORDER_LOG_ROW = new RowMapper<RestitutionOrderLogger>() {
		@Override
		public RestitutionOrderLogger rowMapper(ResultSet rs) throws SQLException {
			RestitutionOrderLogger logger = new RestitutionOrderLogger();
			logger.setId(rs.getInt("id"));
			logger.setAuthor(rs.getString("author"));
			logger.setOrderNo(rs.getString("order_no"));
			logger.setAmount(rs.getInt("amount"));
			logger.setGameCoin(rs.getInt("game_coin"));
			logger.setCreateTime(rs.getTimestamp("create_time"));
			return logger;
		}
	};

	public PayOrder queryOrder(int payId) {
		String sql = "select * from t_pay_order where pay_id=?";
		return queryForEntity(sql, PAY_ORDER_ROW, payId);
	}

	public PayOrder queryOrder(String orderNo) {
		String sql = "select * from t_pay_order where order_no=?";
		return queryForEntity(sql, PAY_ORDER_ROW, orderNo);
	}

	public int gainPayId(PayOrder order) {
		String sql = "insert into t_pay_order(" + "order_no," + "game_coin," + "amount," + "server_id," + "user_id," + "user_name," + "user_partner,"
		        + "retry_times," + "status," + "gift_coin," + "create_time," + "last_ret_code," + "pay_config_id," + "goods_id," + "bill_no," + "ext1,"
		        + "ext2," + "ext3) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		return insertAndReturnId(sql, INT_KEY, order.getOrderNo(), order.getGameCoin(), order.getAmount(), order.getServerId(), order.getUserId(),
		        order.getUserName(), order.getUserParnter(), order.getRetryTimes(), order.getStatus(), order.getGiftCoin(), order.getCreateTime(),
		        order.getLastRetCode(), order.getPayConfigId(), order.getGoodsId(), order.getBillNo(), order.getExt1(), order.getExt2(), order.getExt3());
	}

	public void updateOrder(PayOrder order, PayOrderOpt opt) {
		StringBuffer buff = new StringBuffer("update t_pay_order set update_time=now() ");
		List<Object> val = new ArrayList<Object>();
		if (opt.status == SQLOpt.EQUAL) {
			buff.append(" ,status=? ");
			val.add(order.getStatus());
		}
		if (opt.paySucessTime == SQLOpt.EQUAL) {
			buff.append(",pay_sucess_time=?");
			val.add(order.getPaySucessTime());
		}
		if (opt.retryTimes == SQLOpt.EQUAL) {
			buff.append(",retry_times=?");
			val.add(order.getRetryTimes());
		}
		if (opt.deliverySucessTime == SQLOpt.EQUAL) {
			buff.append(",delivery_sucess_time=?");
			val.add(order.getDeliverySucessTime());
		}
		if (opt.lastRetryTime == SQLOpt.EQUAL) {
			buff.append(",last_retry_time=?");
			val.add(order.getLastRetryTtime());
		}
		if (opt.lastRetCode == SQLOpt.EQUAL) {
			buff.append(",last_ret_code=?");
			val.add(order.getLastRetCode());
		}
		if (order.getBillNo() != null && !order.getBillNo().trim().equals("")) {
			buff.append(",bill_no=?");
			val.add(order.getBillNo());
		}
		if (order.getExt1() != null && !order.getExt1().trim().equals("")) {
			buff.append(",ext1=?");
			val.add(order.getExt1());
		}
		if (order.getExt2() != null && !order.getExt2().trim().equals("")) {
			buff.append(",ext2=?");
			val.add(order.getExt2());
		}
		if (order.getExt3() != null && !order.getExt3().trim().equals("")) {
			buff.append(",ext3=?");
			val.add(order.getExt3());
		}
		buff.append(" where pay_id=? ");
		val.add(order.getPayId());
		saveOrUpdate(buff.toString(), val.toArray());
	}

	public List<PayOrder> getNotifiOrderList(int size) {
		StringBuffer buff = new StringBuffer("select * from t_pay_order where 1=1 ");
		buff.append(" and status!=? ");
		buff.append(" and status!=? ");
		buff.append(" and status!=? ");
		buff.append(" limit  ?");
		return queryForList(buff.toString(), PAY_ORDER_ROW, PayOrder.STATUS_INIT, PayOrder.STATUS_放弃发货, PayOrder.STATUS_发货完成, size);
	}

	public List<PayOrder> getIAPNotifyOrderList() {
		String sql = "select * from t_pay_order where status=? and retry_times < 6 limit 0,50";
		return queryForList(sql, PAY_ORDER_ROW, PayOrder.STATUS_保存苹果票据);
	}

	/**
	 * 订单FilterSQL
	 * 
	 * @param filter
	 * @param sql
	 * @param val
	 */
	private void excuteFilterSQL(PayOrderFilter filter, StringBuffer sql, List<Object> val) {

		if (filter.getOrderNo() != null) {
			sql.append(" and order_no = ? ");
			val.add(filter.getOrderNo());
		}

		if (filter.getUserId() != null) {
			sql.append(" and user_id = ? ");
			val.add(filter.getUserId());
		}
		if (filter.getUserName() != null) {
			sql.append(" and user_name = ? ");
			val.add(filter.getUserName());
		}

		if (filter.getUserParnter() != null) {
			sql.append(" and user_partner = ? ");
			val.add(filter.getUserParnter());
		}

		if (filter.getServerId() != null) {
			sql.append(" and server_id = ?");
			val.add(filter.getServerId());
		}

		if (filter.getStatus() != null) {
			sql.append(" and status = ? ");
			val.add(filter.getStatus());
		}

		if (filter.getStartTime() != null) {
			sql.append(" and  create_time >=  ?  ");
			val.add(filter.getStartTime());
		}
		if (filter.getEndTime() != null) {
			sql.append(" and  create_time <= ?  ");
			val.add(filter.getEndTime());
		}

		if (filter.getPayStartTime() != null) {
			sql.append(" and pay_sucess_time >= ? ");
			val.add(filter.getPayStartTime());
		}

		if (filter.getPayEndTime() != null) {
			sql.append(" and pay_sucess_time <= ? ");
			val.add(filter.getPayEndTime());
		}

		if (filter.getDeliveryStartTime() != null) {
			sql.append(" and delivery_sucess_time >= ? ");
			val.add(filter.getDeliveryStartTime());
		}

		if (filter.getDeliveryEndTime() != null) {
			sql.append(" and delivery_sucess_time <= ? ");
			val.add(filter.getDeliveryEndTime());
		}
	}

	/**
	 * 订单查询
	 * 
	 * @param filter
	 * @return
	 */
	public List<PayOrder> getPayOrders(PayOrderFilter filter) {
		StringBuffer sql = new StringBuffer("select * from  t_pay_order where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), PAY_ORDER_ROW, val.toArray());
	}

	/**
	 * 得到订单总数量
	 * 
	 * @param filter
	 * @return
	 */
	public Integer getPayOrdersCount(PayOrderFilter filter) {
		StringBuffer sql = new StringBuffer("select count(1) from t_pay_order where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}

	/**
	 * 统计总订单数，订单金额，平均金额
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> totalOrder() throws SQLException {
		return queryForListMap("SELECT count(pay_id) num, sum(amount) amount, avg(amount) avg FROM t_pay_order WHERE status in (1,2,3)");
	}

	/**
	 * 统计每天订单数，订单金额，平均金额
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> totalOrderByDay() throws SQLException {
		return queryForListMap("SELECT DATE_FORMAT(create_time, '%Y-%m-%d') date, count(pay_id) num, sum(amount) amount,avg(amount) avg FROM t_pay_order WHERE status in (1,2,3) group by date");
	}

	/**
	 * 按日期统计订单
	 * 
	 * @param filter
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> statisticsOrderByDay(PayOrderFilter filter) throws SQLException {
		StringBuffer sql = new StringBuffer(
		        "SELECT DATE_FORMAT(create_time,'%Y-%m-%d') AS `date` ,COUNT(*) AS `sum`,SUM(amount ) AS `money` FROM t_pay_order WHERE 1= 1  ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" and `status` IN (2) GROUP BY `date` ");
		return queryForListMap(sql.toString(), val.toArray());
	}

	/**
	 * 按渠道统计订单
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, Object>> statisticsOrderByPartner(PayOrderFilter filter) throws SQLException {
		StringBuffer sql = new StringBuffer("SELECT user_partner AS `partner`, COUNT(*) AS `sum`, SUM(amount ) AS `money` FROM t_pay_order WHERE 1= 1 ");
		List<Object> val = new ArrayList<Object>();
		excuteFilterSQL(filter, sql, val);
		sql.append(" and `status` IN (2)  GROUP BY `user_partner`  ");
		return queryForListMap(sql.toString(), val.toArray());
	}

	/**
	 * 通过订单号查询订单
	 * 
	 * @param orderNo
	 * @return
	 */
	public PayOrder getOrderByNorderBo(String orderNo) {
		String sql = "SELECT * FROM t_pay_order WHERE order_no = ? ";
		return queryForEntity(sql, PAY_ORDER_ROW, orderNo);
	}

	/**
	 * 保存返还订单日志
	 * 
	 * @param logger
	 */
	public void saveSendOrderLogger(RestitutionOrderLogger logger) {
		String sql = " INSERT INTO t_restitution_order_logger (author,order_no,amount,game_coin,create_time) VALUES(?,?,?,?,now())";
		saveOrUpdate(sql, logger.getAuthor(), logger.getOrderNo(), logger.getAmount(), logger.getGameCoin());
	}

	/**
	 * 查询返还订单的日志
	 * 
	 * @return
	 */
	public List<RestitutionOrderLogger> getRestitutionOrderLog() {
		String sql = "SELECT * FROM t_restitution_order_logger ";
		return queryForList(sql, RESTITUTION_ORDER_LOG_ROW);
	}

	/**
	 * 得到返还订单日志的数量
	 * 
	 * @return
	 */
	public Integer getRestitutionOrderLogCount() {
		String sql = "SELECT COUNT(*) FROM t_restitution_order_logger ";
		return queryForEntity(sql, INT_ROW_MAPPER);
	}

	public void partnerIsPay(Partner partner) {
		String sql = "update  t_partner set is_pay=? where partner_id=? ";
		saveOrUpdate(sql, partner.isPay(), partner.getParnterId());
	}
}
