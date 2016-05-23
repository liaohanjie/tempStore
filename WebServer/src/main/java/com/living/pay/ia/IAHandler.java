package com.living.pay.ia;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.util.HttpUtil;
import com.ks.util.JSONUtil;
import com.ks.util.MD5Util;
import com.living.pay.ChannelHandler;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * I苹果支付处理逻辑(未完成)
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月6日
 */
public class IAHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(IAHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.ia.auth.url");

	static final String GAME_KEY = PropertyUtils.SYS_CONFIG.get("sdk.ia.game.key");
	static final String SECRET_KEY = PropertyUtils.SYS_CONFIG.get("sdk.ia.secret.key");

	static final Map<String, String> HEADERS = new HashMap<String, String>();
	static {
		HEADERS.put("Content-type", "application/x-www-form-urlencoded");
	}

	static final String UTF_8 = "UTF-8";
	static final TypeReference<Map<String, String>> TYPE_REFERENCE = new TypeReference<Map<String, String>>() {
	};

	private String _requestAuth(String userId, String session) {
		String sign = MD5Util.decode(MD5Util.decode("game_id=" + GAME_KEY + "&session=" + session + "&user_id=" + userId) + SECRET_KEY);
		return HttpUtil.postRet(AUTH_URL, "user_id=" + userId + "&session=" + session + "&game_id=" + GAME_KEY + "&_sign=" + sign, UTF_8, UTF_8, HEADERS);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String session = ctx.getAsString("session");
		String userId = ctx.getAsString("userId");

		try {
			String result = _requestAuth(userId, session);
			logger.info("iapple auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				Map<String, String> resMap = JSONUtil.toObject(result, TYPE_REFERENCE);

				if (resMap != null && !resMap.isEmpty()) {
					// 1 成功
					// 0 失败
					// 1000001 合作方不存在
					// 1000002 签名错误
					// 1000003 参数错误
					String status = resMap.get("status");
					// String desc = resMap.get("desc");

					if ("1".equals(status)) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(userId);
						account.setUserName(userId);
						processLogin(ctx, account);
						return new StringPage(JSONUtil.toJson(jsonResult));
					} else {
						jsonResult.setMsg("fail");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		// 交易流水号
		String transaction = ctx.getAsString("transaction");
		// 支付方式
		String payType = ctx.getAsString("payType");
		// 充值中心用户ID
		Integer userId = ctx.getAsInt("userId");
		// 服务器编号
		String serverNo = ctx.getAsString("serverNo");
		// 金额
		Double amount = ctx.getAsDouble("amount");
		// 点数
		Integer cardPoint = ctx.getAsInt("cardPoint");
		// 游戏用户唯一标识
		String gameUserId = ctx.getAsString("gameUserId");
		// 订单交易时间
		Long transactionTime = ctx.getAsLong("transactionTime");
		// 扩展 参数
		String orderId = ctx.getAsString("gameExtend");
		// 充值中心表示 GW
		String platform = ctx.getAsString("platform");
		// 状态 :返回 1 表示 成功
		String status = ctx.getAsString("status");
		// 货币类型 （CNY）
		String currency = ctx.getAsString("currency");
		// 签名
		String sign = ctx.getAsString("_sign");

		boolean flag = true;
		flag = flag && orderId != null && !orderId.trim().equals("") && orderId.length() >= 5;
		flag = flag && transaction != null && !transaction.trim().equals("");
		flag = flag && userId != null && userId > 0;
		flag = flag && serverNo != null && !serverNo.trim().equals("");
		flag = flag && amount != null && amount > 0;
		flag = flag && gameUserId != null && !gameUserId.trim().equals("");
		flag = flag && transactionTime != null && transactionTime > 0L;
		flag = flag && platform != null && !platform.trim().equals("");
		flag = flag && currency != null && !currency.trim().equals("");
		flag = flag && sign != null && !sign.trim().equals("");

		Map<String, String> map = new TreeMap<String, String>();
		map.put("transaction", transaction);
		map.put("payType", payType);
		map.put("userId", userId + "");
		map.put("serverNo", serverNo);
		map.put("amount", ctx.getAsString("amount"));
		map.put("cardPoint", cardPoint + "");
		map.put("gameUserId", gameUserId);
		map.put("transactionTime", ctx.getAsString("transactionTime"));
		map.put("gameExtend", orderId);
		map.put("platform", platform);
		map.put("status", status);
		map.put("currency", currency);
		// map.put("_sign", sign);

		try {

			if (flag && _validateNotify(map, sign) && "1".equals(status)) {

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderId);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("iapple notify can't find order id [" + orderId + "]");
					return new StringPage("fail");
				}

				order = list.get(0);
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(transaction);
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("iapple order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("{\"status\":0,\"transIDO\":" + transaction + "}");
			} else {
				logger.info("iapple notify param validate fail");
				return new StringPage("xy notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(Map<String, String> map, String sign) {

		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}
		String checkSign = MD5Util.decode(MD5Util.decode(sb.substring(1)) + SECRET_KEY);
		return sign.toLowerCase().equals(checkSign.toLowerCase());
	}
	
}
