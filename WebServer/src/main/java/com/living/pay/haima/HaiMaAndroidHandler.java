package com.living.pay.haima;

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
 * 海马SDK - android
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年4月14日
 */
public class HaiMaAndroidHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(HaiMaAndroidHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.haima.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.haima.app.id");

	static final String KEY = PropertyUtils.SYS_CONFIG.get("sdk.haima.key");

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String uid, String t) {
		return HttpUtil.postRet(AUTH_URL, "uid=" + uid + "&t=" + t + "&appid=" + APP_ID, "UTF-8", "UTF-8");
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String uid = ctx.getAsString("userId");
		String t = ctx.getAsString("loginToken");

		try {
			String result = _requestAuth(uid, t);
			logger.info("auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				if (result.startsWith("success")) {
					jsonResult = JsonResult.success();
					jsonResult.setObj(uid);
					account.setUserName(uid);
					processLogin(ctx, account);
					return new StringPage(JSONUtil.toJson(jsonResult));
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
		// 通知时间
		String notifyTime = ctx.getAsString("notify_time");
		// 应用 id
		String appId = ctx.getAsString("appid");
		// 开发者的订单号
		String orderNo = ctx.getAsString("out_trade_no");
		// 订单的总金额 1.00
		String price = ctx.getAsString("total_fee");
		// 商品名称
		String subject = ctx.getAsString("subject");
		// 游戏名或商品详情
		String body = ctx.getAsString("body");
		// 0,代表订单未支付；
		// 1,代表订单支付成功；
		// 2 ,代表请求订单失败；
		// 3,代表订单签名失败；
		// 4,订单支付失败；
		// 5,其他失败
		String tradeStatus = ctx.getAsString("trade_status");
		// 签名
		String sign = ctx.getAsString("sign");

		boolean flag = true;
		flag = flag && orderNo != null && !orderNo.trim().equals("") && orderNo.length() >= 5;
		flag = flag && price != null && !price.trim().equals("");
		flag = flag && sign != null && !sign.trim().equals("");
		flag = flag && tradeStatus != null && tradeStatus.trim().equals("1");

		try {

			if (flag && _validateNotify(notifyTime, orderNo, price, subject, body, tradeStatus, sign)) {

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderNo);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("notify can't find order id [" + orderNo + "]");
					return new StringPage("fail");
				}
				
				order = list.get(0);
				if (Double.parseDouble(price) < order.getAmount()) {
					logger.warn("notify amount is error. order id [" + orderNo + "], price=" + price);
					return new StringPage("fail");
				}
				
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo("");
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("success");
			} else {
				logger.info("notify param validate fail");
				return new StringPage("notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String notifyTime, String orderNo, String price, String subject, String body, String tradeStatus, String sign) {
		String checkSign = MD5Util.decode("notify_time=" + notifyTime + "&appid=" + APP_ID + "&out_trade_no=" + orderNo + "&total_fee=" + price + "&subject=" + subject + "&body=" + body + "&trade_status=" + tradeStatus + KEY);
		return sign.equals(checkSign);
	}
}
