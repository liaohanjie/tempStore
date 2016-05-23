/**
 * 
 */
package com.ks.account.service;

import java.util.List;
import java.util.Map;

import com.ks.access.Transaction;
import com.ks.model.account.Partner;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.model.pay.RestitutionOrderLogger;

/**
 * @author living.li
 * @date 2015年5月19日 下午3:46:17
 * 
 * 
 */
public interface PayService {

	@Transaction
	public PayOrder gainPayId(PayOrder order);

	@Transaction
	public PayOrder orderPayOk(int payId);

	@Transaction
	public PayOrder orderPayOk(String orderNo);

	@Transaction
	public void notifiGameServer(PayOrder order);

	@Transaction
	public void retryNofiGameServer();

	@Transaction
	public void gainOrderPayOk(PayOrder order);

	/**
	 * 查询订单
	 * 
	 * @param filter
	 * @return
	 */
	public List<PayOrder> getPayOrders(PayOrderFilter filter);

	/**
	 * 得到订单总数量
	 * 
	 * @param filter
	 * @return
	 */
	public Integer getPayOrdersCount(PayOrderFilter filter);

	public Partner getPartner(Integer id);

	/**
	 * 统计总订单数，订单金额，平均金额
	 * 
	 * @return
	 */
	public List<Map<String, Object>> totalOrder();

	/**
	 * 统计每天订单数，订单金额，平均金额
	 * 
	 * @return
	 */
	public List<Map<String, Object>> totalOrderByDay();

	/**
	 * 按日期统计订单
	 * 
	 * @param filter
	 * @return
	 */
	public List<Map<String, Object>> statisticsOrderByDay(PayOrderFilter filter);

	/**
	 * 按渠道统计订单
	 * 
	 * @return
	 */
	public List<Map<String, Object>> statisticsOrderByPartner(PayOrderFilter filter);

	/**
	 * 重新发送IAP通知给游戏，并发放游戏币
	 * 
	 * @return
	 */
	@Transaction
	public void reloadIAPNotify();

	/**
	 * 保存苹果票据
	 * 
	 * @return
	 */
	@Transaction
	public void updateIapNotifyOrder(PayOrder order);

	/**
	 * 保存返还订单日志
	 * 
	 * @param order
	 */
	@Transaction
	public void savaSendOrderLogger(RestitutionOrderLogger logger);

	/**
	 * 查询返还订单的日志
	 * 
	 * @return
	 */
	public List<RestitutionOrderLogger> getRestitutionOrderLog();

	/**
	 * 得到返还订单日志的数量
	 * 
	 * @return
	 */
	public Integer getRestitutionOrderLogCount();

	/**
	 * 根据订单号查询订单
	 * 
	 * @param orderNo
	 * @return
	 */
	public PayOrder getOrderByOrderNo(String orderNo);

	/**
	 * 渠道是否打开支付开关
	 * 
	 * @param partner
	 */
	@Transaction
	public void partnerIsPay(Partner partner);
	
	/**
	 * 订单返还
	 * 
	 * @param order
	 */
	@Transaction
	public void orderReturn(String serverId, int partner, String userName);
}
