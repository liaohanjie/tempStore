/**
 * 
 */
package com.ks.account.service.impl;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.account.cache.AccountCache;
import com.ks.account.dao.opt.PayOrderOpt;
import com.ks.account.dao.opt.SQLOpt;
import com.ks.account.service.BaseService;
import com.ks.account.service.PayService;
import com.ks.action.world.WorldUserAction;
import com.ks.exceptions.AccountException;
import com.ks.logger.LoggerFactory;
import com.ks.model.account.Partner;
import com.ks.model.account.ServerInfo;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.Mall;
import com.ks.model.pay.OrderReturn;
import com.ks.model.pay.PayOrder;
import com.ks.model.pay.RestitutionOrderLogger;
import com.ks.util.HttpUtil;

/**
 * @author living.li
 * @date 2015年5月19日 下午4:09:53
 * 
 * 
 */
public class PayServiceImpl extends BaseService implements PayService {
	private Logger logger = LoggerFactory.get(PayServiceImpl.class);

	@Override
	public PayOrder gainPayId(PayOrder order) {
		if (order == null) {
			throw new AccountException(AccountException.code_order_is_null, "order can't be null.");
		}
		
		Mall mall = mallDAO.findById(order.getAmount());
		if (mall == null) {
			throw new AccountException(AccountException.code_mall_no_found, "can't find mall. money=" + order.getAmount());
		}
		order.setGameCoin(mall.getCurrency());
		order.setGiftCoin(mall.getExtra());
		
		int payId = payDao.gainPayId(order);
		order.setPayId(payId);
		return order;
	}

	@Override
	public PayOrder orderPayOk(int payId) {
		PayOrder order = payDao.queryOrder(payId);
		if (order == null) {
			throw new AccountException(AccountException.code_order_not_exist, "order not exist");
		}
		if (order.getStatus() != PayOrder.STATUS_INIT) {
			throw new AccountException(AccountException.code_order_not_match_status, "order has process status." + order.getStatus());
		}
		PayOrderOpt opt = new PayOrderOpt();
		opt.status = SQLOpt.EQUAL;
		opt.paySucessTime = SQLOpt.EQUAL;
		order.setStatus(PayOrder.STATUS_成功通知渠道);
		order.setPaySucessTime(Calendar.getInstance().getTime());
		payDao.updateOrder(order, opt);
		notifiGameServer(order);
		return order;
	}

	@Override
	public PayOrder orderPayOk(String orderNo) {
		PayOrder order = payDao.queryOrder(orderNo);
		if (order == null) {
			throw new AccountException(AccountException.code_order_not_exist, "order not exist");
		}
		if (order.getStatus() != PayOrder.STATUS_INIT) {
			throw new AccountException(AccountException.code_order_not_match_status, "order has process status." + order.getStatus());
		}
		PayOrderOpt opt = new PayOrderOpt();
		opt.status = SQLOpt.EQUAL;
		opt.paySucessTime = SQLOpt.EQUAL;
		order.setStatus(PayOrder.STATUS_成功通知渠道);
		order.setPaySucessTime(Calendar.getInstance().getTime());
		payDao.updateOrder(order, opt);
		notifiGameServer(order);
		return order;
	}

	@Override
	public void notifiGameServer(PayOrder order) {
		// ServerInfo
		// server=AccountCache.getServerByServerId(order.getServerId());
		ServerInfo server = AccountCache.getServerById(Integer.parseInt(order.getServerId()));
		String ret = PayOrder.NOTIFI_CODE_SUCESS;
		if (server == null) {
			ret = PayOrder.NOTIFI_CODE_服务器不存在;
			logger.error("pay notifi game server no found" + order.getServerId());
		} else if (server.getPayNotifiUrl() == null) {
			ret = PayOrder.NOTIFI_CODE_通知无响应;
			logger.error("pay notifi url is null server." + server.getServerId());
		}
		//
		if (ret.equals(PayOrder.NOTIFI_CODE_SUCESS)) {
			String sign = PayOrder.getNofiSign(order.getServerId(), order.getUserId() + "", order.getUserName(), order.getUserParnter() + "",
			        order.getGameCoin() + "", order.getAmount() + "", order.getOrderNo(), order.getGoodsId() + "");
			StringBuffer buff = new StringBuffer();
			buff.append("method=pay");
			buff.append("&server_id=" + order.getServerId());
			buff.append("&user_id=" + order.getUserId());
			buff.append("&user_name=" + order.getUserName());
			buff.append("&user_partner=" + order.getUserParnter());
			buff.append("&game_coin=" + order.getGameCoin());
			buff.append("&amount=" + order.getAmount());
			buff.append("&order_no=" + order.getOrderNo());
			buff.append("&goods_id=" + order.getGoodsId());
			buff.append("&sign=" + sign);
			logger.error("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
			String bak = HttpUtil.getRet(server.getPayNotifiUrl(), buff.toString(), null, null);
			logger.error("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
			if (bak == null) {
				//
				ret = PayOrder.NOTIFI_CODE_通知无响应;
				logger.error("pay notifi game server error ." + bak);
			} else if (PayOrder.NOTIFI_CODE_SUCESS.equals(bak)) {
				PayOrderOpt opt = new PayOrderOpt();
				opt.status = SQLOpt.EQUAL;
				opt.deliverySucessTime = SQLOpt.EQUAL;
				order.setStatus(PayOrder.STATUS_发货完成);
				order.setDeliverySucessTime(Calendar.getInstance().getTime());

				logger.error("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");

				payDao.updateOrder(order, opt);
				//
				ret = PayOrder.NOTIFI_CODE_SUCESS;
				return;
			} else {
				ret = bak;
				logger.error("pay notifi game server error ." + bak);
			}
		}
		PayOrderOpt opt = new PayOrderOpt();
		opt.lastRetCode = SQLOpt.EQUAL;
		order.setLastRetCode(ret);
		payDao.updateOrder(order, opt);
		logger.error("ccccccccccccccccccccccccccccccccccccccccccccccccccccccc");
	}

	@Override
	public void retryNofiGameServer() {
		List<PayOrder> orderList = payDao.getNotifiOrderList(100);
		for (PayOrder order : orderList) {
			/*
			 * if(order.getRetryTimes()<PayOrder.RETRY_TIMES_MAX){ long
			 * now=Calendar.getInstance().getTimeInMillis(); //每30分才重试此订单
			 * if(order
			 * .getLastRetryTtime()==null||now-order.getLastRetryTtime().
			 * getTime()>PayOrder.RETRY_TIMES_SPLIT){ //重试 PayOrderOpt opt=new
			 * PayOrderOpt(); opt.retryTimes=SQLOpt.EQUAL;
			 * opt.lastRetryTime=SQLOpt.EQUAL;
			 * order.setRetryTimes(order.getRetryTimes()+1);
			 * order.setLastRetryTtime(Calendar.getInstance().getTime());
			 * payDao.updateOrder(order, opt); // notifiGameServer(order); }
			 * }else{ PayOrderOpt opt=new PayOrderOpt();
			 * opt.status=SQLOpt.EQUAL; opt.deliverySucessTime=SQLOpt.EQUAL;
			 * opt.lastRetCode=SQLOpt.EQUAL;
			 * order.setStatus(PayOrder.STATUS_放弃发货); payDao.updateOrder(order,
			 * opt); }
			 */

			// 去掉放弃发货限制
			long now = System.currentTimeMillis();

			// 三十分钟太长，改成1分钟
			if (order.getRetryTimes() < PayOrder.RETRY_TIMES_MAX && (order.getLastRetryTtime() == null || now - order.getLastRetryTtime().getTime() > 60000)) {
				// 重试
				PayOrderOpt opt = new PayOrderOpt();
				opt.retryTimes = SQLOpt.EQUAL;
				opt.lastRetryTime = SQLOpt.EQUAL;
				order.setRetryTimes(order.getRetryTimes() + 1);
				order.setLastRetryTtime(Calendar.getInstance().getTime());
				payDao.updateOrder(order, opt);
				//
				notifiGameServer(order);
			}
		}
	}

	@Override
	public List<PayOrder> getPayOrders(PayOrderFilter filter) {
		return payDao.getPayOrders(filter);
	}

	@Override
	public Integer getPayOrdersCount(PayOrderFilter filter) {
		return payDao.getPayOrdersCount(filter);
	}

	@Override
	public void gainOrderPayOk(PayOrder order) {
		PayOrder dataOrder = payDao.queryOrder(order.getOrderNo());
		if (dataOrder == null) {
			order.setStatus(PayOrder.STATUS_成功通知渠道);
			gainPayId(order);
			notifiGameServer(order);
		} else if (dataOrder.getStatus() == PayOrder.STATUS_INIT || dataOrder.getStatus() == PayOrder.STATUS_保存苹果票据) {
			// orderPayOk(order.getPayId());
			PayOrderOpt opt = new PayOrderOpt();
			opt.status = SQLOpt.EQUAL;
			opt.paySucessTime = SQLOpt.EQUAL;
			order.setStatus(PayOrder.STATUS_成功通知渠道);
			order.setPaySucessTime(Calendar.getInstance().getTime());
			payDao.updateOrder(order, opt);
			notifiGameServer(order);
		}
	}

	@Override
	public Partner getPartner(Integer id) {
		return AccountCache.getPartnerMap().get(id);
	}

	@Override
	public List<Map<String, Object>> totalOrder() {
		List<Map<String, Object>> list = null;
		try {
			list = payDao.totalOrder();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> totalOrderByDay() {
		List<Map<String, Object>> list = null;
		try {
			list = payDao.totalOrderByDay();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> statisticsOrderByDay(PayOrderFilter filter) {
		List<Map<String, Object>> list = null;
		try {
			list = payDao.statisticsOrderByDay(filter);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> statisticsOrderByPartner(PayOrderFilter filter) {
		List<Map<String, Object>> list = null;
		try {
			list = payDao.statisticsOrderByPartner( filter);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void reloadIAPNotify() {
		try {
			logger.info("开始重发IAP通知给游戏服");

			List<PayOrder> list = payDao.getIAPNotifyOrderList();
			if (list != null && !list.isEmpty()) {
				for (PayOrder order : list) {
					if (order.getRetryTimes() < PayOrder.RETRY_TIMES_MAX) {
						// 重试
						PayOrderOpt opt = new PayOrderOpt();
						opt.retryTimes = SQLOpt.EQUAL;
						opt.lastRetryTime = SQLOpt.EQUAL;
						order.setRetryTimes(order.getRetryTimes() + 1);
						order.setLastRetryTtime(Calendar.getInstance().getTime());
						payDao.updateOrder(order, opt);

						// 发放游戏币
						notifiGameServer(order);
					}
					logger.info("开始重发IAP通知给游戏服, orderNo=" + order.getOrderNo());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateIapNotifyOrder(PayOrder order) {
		if (order != null) {
			PayOrderOpt opt = new PayOrderOpt();
			opt.status = SQLOpt.EQUAL;
			opt.ext1 = SQLOpt.EQUAL;
			payDao.updateOrder(order, opt);
		}
	}

	@Override
	public void savaSendOrderLogger(RestitutionOrderLogger logger) {
		payDao.saveSendOrderLogger(logger);
	}

	@Override
	public List<RestitutionOrderLogger> getRestitutionOrderLog() {
		return payDao.getRestitutionOrderLog();
	}

	@Override
	public Integer getRestitutionOrderLogCount() {
		return payDao.getRestitutionOrderLogCount();
	}

	@Override
	public PayOrder getOrderByOrderNo(String orderNo) {
		return payDao.getOrderByNorderBo(orderNo);
	}

	@Override
    public void partnerIsPay(Partner partner) {
		payDao.partnerIsPay(partner);
    }

	@Override
    public void orderReturn(String serverId, int partner, String userName) {
		List<OrderReturn> list = orderReturnDAO.queryByUserName(userName);
		if (list != null && list.size() > 0) {
			int amount = 0;
			
			for (OrderReturn entity : list) {
				amount = amount + entity.getAmount();
			}
			
			String orderNo = "R-" + list.get(0).getOrderNo();
			int currency = amount * 10;
			int extraCurrency = currency;
			
			WorldUserAction action = worldAction(serverId, WorldUserAction.class);
			action.orderReturn(partner, userName, orderNo, currency, extraCurrency);
			
			// 修改返还状态
			orderReturnDAO.updateStatus(userName);
			
			logger.info("return order is OK: userName=" + userName + ", currency=" + currency + ",extraCurrency=" + extraCurrency + ", orderNo=" + orderNo);
		}
    }
}
