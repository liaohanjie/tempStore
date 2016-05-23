package com.living.web.servlet.sdk;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ks.action.account.PayAction;
import com.ks.app.Application;
import com.ks.logger.LoggerFactory;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.rpc.RPCKernel;


/**
 * SDK 处理器
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月17日
 */
public abstract class SdkHandler {
	
	private Logger logger = LoggerFactory.get(getClass());
	
	/**
	 * sdk登陆
	 * 
	 * @param req
	 * @param resp
	 */
	public abstract void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException;
	
	/**
	 * sdk登陆（通用）
	 * @param req
	 * @param resp
	 */
	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		doLogin(req, resp);
	}
	

	/**
	 * sdk渠道通知
	 * 
	 * @param req
	 * @param resp
	 */
	public abstract void doNotify(HttpServletRequest req, HttpServletResponse resp) throws IOException;
	
	/**
	 * sdk渠道通知（通用）
	 * 
	 * @param req
	 * @param resp
	 */
	public void notify(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		doNotify(req, resp);
	}
	
	/**
	 * 订单充值
	 * 
	 * @param orderId
	 * @return
	 */
	public boolean recharge(String orderNo, String billNo, double amount){
		PayOrder order = null;
		PayOrderFilter filter = new PayOrderFilter();
		filter.setOrderNo(String.valueOf(orderNo));

		List<PayOrder> list = payAction().getPayOrders(filter);
		if (list == null || list.isEmpty()) {
			logger.warn("notify can't find order id [" + orderNo + "]");
			return false;
		}
		order = list.get(0);
		
		if (amount == 0 || amount < order.getAmount()) {
			logger.warn("notify amount is error. order id [" + orderNo + "], amount=" + amount);
			return false;
		}
		
		if (order.getStatus() == PayOrder.STATUS_INIT) {
			order.setBillNo(billNo);
			order.setStatus(PayOrder.STATUS_成功通知渠道);
			// 修改订单状态，并发送游戏币
			payAction().gainOrderPayOk(order);
		} else {
			logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
		}
		
		logger.info("orderNo=[" + orderNo + "] recharge success");
		return true;
	}
	
	public static <T>T getInfoAction(Class<T> clazz){
			return RPCKernel.getRemoteByServerType(Application.ACCOUNT_SERVER, clazz);	
	}
	
	public static PayAction payAction(){
		return getInfoAction(PayAction.class);
	}
}
