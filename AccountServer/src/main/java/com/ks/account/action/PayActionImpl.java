/**
 * 
 */
package com.ks.account.action;

import java.util.List;
import java.util.Map;

import com.ks.account.service.BaseService;
import com.ks.account.service.PayService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.PayAction;
import com.ks.model.account.Partner;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.model.pay.RestitutionOrderLogger;

/**
 * @author living.li
 * @date 2015年5月19日 下午4:04:56
 * 
 * 
 */
public class PayActionImpl extends BaseService implements PayAction {
	private static final PayService payService = ServiceFactory.getService(PayService.class);

	@Override
	public PayOrder gainPayId(PayOrder order) {
		return payService.gainPayId(order);
	}

	@Override
	public void gainOrderPayOk(PayOrder order) {
		payService.gainOrderPayOk(order);
	}

	@Override
	public PayOrder orderPayOk(int payId) {
		return payService.orderPayOk(payId);
	}

	@Override
	public PayOrder orderPayOk(String orderNo) {
		return payService.orderPayOk(orderNo);
	}

	@Override
	public List<PayOrder> getPayOrders(PayOrderFilter filter) {
		return payService.getPayOrders(filter);
	}

	@Override
	public Integer getPayOrdersCount(PayOrderFilter filter) {
		return payService.getPayOrdersCount(filter);
	}

	@Override
	public Partner getPartnerById(Integer partnerId) {
		return payService.getPartner(partnerId);
	}

	@Override
	public List<Map<String, Object>> totalOrder() {
		return payService.totalOrder();
	}

	@Override
	public List<Map<String, Object>> totalOrderByDay() {
		return payService.totalOrderByDay();
	}

	@Override
	public List<Map<String, Object>> statisticsOrderByDay(PayOrderFilter filter) {
		return payService.statisticsOrderByDay(filter);
	}

	@Override
	public List<Map<String, Object>> statisticsOrderByPartner(PayOrderFilter filter) {
		return payService.statisticsOrderByPartner(filter);
	}

	@Override
	public void updateIapNotifyOrder(PayOrder order) {
		payService.updateIapNotifyOrder(order);
	}

	@Override
	public void savaSendOrderLogger(RestitutionOrderLogger logger) {
		payService.savaSendOrderLogger(logger);
	}

	@Override
	public List<RestitutionOrderLogger> getRestitutionOrderLog() {
		return payService.getRestitutionOrderLog();
	}

	@Override
	public Integer getRestitutionOrderLogCount() {
		return payService.getRestitutionOrderLogCount();
	}

	@Override
	public PayOrder getOrderByOrderNo(String orderNo) {
		return payService.getOrderByOrderNo(orderNo);
	}

	@Override
	public void partnerIsPay(Partner partner) {
		payService.partnerIsPay(partner);
	}

}
