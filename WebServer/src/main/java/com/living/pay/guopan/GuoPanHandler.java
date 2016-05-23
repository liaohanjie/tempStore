package com.living.pay.guopan;

import java.util.List;

import org.apache.log4j.Logger;

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
 * 果盘支付处理
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月12日
 */
public class GuoPanHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(GuoPanHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.guopan.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.guopan.app.id");

	static final String SERVER_KEY = PropertyUtils.SYS_CONFIG.get("sdk.guopan.server.secret.key");
	
	static final String CLIENT_KEY = PropertyUtils.SYS_CONFIG.get("sdk.guopan.client.secrect.key");

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String gameUin, String token) {
		long t = System.currentTimeMillis();
		return HttpUtil.postRet(AUTH_URL,
		        "game_uin=" + gameUin + "&appid=" + APP_ID + "&token=" + token + "&t=" + t + "&sign=" + MD5Util.decode(gameUin + APP_ID + t + SERVER_KEY),
		        UTF_8, UTF_8);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		// 果盘分配给该游戏对应的唯一账号
		String gameUin = ctx.getAsString("loginUIN");
		String token = ctx.getAsString("loginToken");
		String accountName = ctx.getAsString("accountName");

		if (token == null || token.trim().equals("") || gameUin == null || gameUin.trim().equals("")) {
			jsonResult.setMsg("GuoPan token or uid is null");
			logger.warn("GuoPan token or uid is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			String result = _requestAuth(gameUin, token);
			logger.info("GuoPan auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				// true 成功
				// false 失败
				// -1 加密串验证失败
				// -2 APPID不存在

				if ("true".equals(result.trim())) {
					jsonResult = JsonResult.success();
					jsonResult.setObj(gameUin);
					account.setUserName(gameUin + "");
					processLogin(ctx, account);
					return new StringPage(JSONUtil.toJson(jsonResult));
				} else {
					jsonResult.setMsg("fail");
				}
			}
		} catch (Exception e) {
			jsonResult = JsonResult.error();
			e.printStackTrace();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		// 果盘唯一订单号
		String billno = ctx.getAsString("trade_no");
		// 厂商订单号
		String orderId = ctx.getAsString("serialNumber");
		// 消费金额
		Double amount = ctx.getAsDouble("money");
		// 时间戳
		String t = ctx.getAsString("t");
		// 加密串 sign=md5(serialNumber +money+status+t+SERVER_KEY)
		String sign = ctx.getAsString("sign");
		// 0=失败；1=成功；2=失败，原因是余额不足
		Integer status = ctx.getAsInt("status");

		String appid = ctx.getAsString("appid");
		String itemId = ctx.getAsString("item_id");
		String itemPrice = ctx.getAsString("item_price");
		String itemCount = ctx.getAsString("item_count");
		String reserved = ctx.getAsString("reserved");

		boolean flag = true;
		flag = flag && orderId != null && !orderId.trim().equals("") && orderId.length() >= 5;
		flag = flag && billno != null && !billno.trim().equals("");
		flag = flag && amount != null && amount > 0.0;
		flag = flag && t != null && !t.trim().equals("");
		flag = flag && status != null && status == 1;
		flag = flag && sign != null && !sign.trim().equals("");

		try {

			if (flag && _validateNotify(orderId, amount, status, t, sign)) {

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderId);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("GuoPan notify can't find order id [" + orderId + "]");
					return new StringPage("fail");
				}

				order = list.get(0);
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(billno);
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("GuoPan order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("success");
			} else {
				logger.info("GuoPan notify param validate fail");
				return new StringPage("GuoPan notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String orderId, Double amount, Integer status, String t, String sign) {
		String checkSign = MD5Util.decode(orderId + String.format("%.2f", amount) + status + t + SERVER_KEY);
		return sign.toLowerCase().equals(checkSign.toLowerCase());
	}
}
