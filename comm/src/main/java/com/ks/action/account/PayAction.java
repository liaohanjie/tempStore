/**
 * 
 */
package com.ks.action.account;

import java.util.List;
import java.util.Map;

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
public interface PayAction {

	public PayOrder gainPayId(PayOrder order);

	public void gainOrderPayOk(PayOrder order);

	public PayOrder orderPayOk(int payId);

	public PayOrder orderPayOk(String orderNo);

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

	/**
	 * 通过渠道ID查询渠道
	 * 
	 * @param partnerId
	 * @return
	 */
	public Partner getPartnerById(Integer partnerId);

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
	 * 保存苹果票据
	 * 
	 * @return
	 */
	public void updateIapNotifyOrder(PayOrder order);

	/**
	 * 保存返还订单日志
	 * 
	 * @param order
	 */
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
	public void partnerIsPay(Partner partner);
}
