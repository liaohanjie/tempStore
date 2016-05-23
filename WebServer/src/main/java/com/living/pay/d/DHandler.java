package com.living.pay.d;

import java.util.List;
import java.util.Map;

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
 * 当乐处理
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月12日
 */
public class DHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(DHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.d.auth.url");

	static final String MERCHANT_ID = PropertyUtils.SYS_CONFIG.get("sdk.d.merchant.id");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.d.app.id");

	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.d.app.key");

	static final String PAYMENT_KEY = PropertyUtils.SYS_CONFIG.get("sdk.d.payment.key");

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String umid, String token) {
		return HttpUtil.postRet(AUTH_URL,
		        "appid=" + APP_ID + "&token=" + token + "&umid=" + umid + "&sig=" + MD5Util.decode(APP_ID + "|" + APP_KEY + "|" + token + "|" + umid), UTF_8,
		        UTF_8);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		// 果盘分配给该游戏对应的唯一账号
		String username = ctx.getAsString("username");
		String umid = ctx.getAsString("mid");
		String token = ctx.getAsString("token");

		if (token == null || token.trim().equals("") || umid == null || umid.trim().equals("")) {
			jsonResult.setMsg("DangLe token or uid is null");
			logger.warn("DangLe token or uid is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			String result = _requestAuth(umid, token);
			logger.info("DangLe auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
				});

				if (resMap != null && !resMap.isEmpty()) {
					String msgCode = String.valueOf(resMap.get("msg_code"));
					// 错误信息描述
					// String msgDesc = resMap.get("msg_desc");
					// Integer valid = resMap.get("valid");
					// Integer interval = resMap.get("interval");
					// Integer times = resMap.get("times");
					// Boolean roll = resMap.get("roll");

					if ("2000".equals(msgCode)) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(umid);
						account.setUserName(umid);
						processLogin(ctx, account);
						return new StringPage(JSONUtil.toJson(jsonResult));
					}
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
		// 唯一订单号
		String billno = ctx.getAsString("order");
		// 厂商订单号
		String orderId = ctx.getAsString("ext");
		// 当乐进入游戏用户名
		String mid = ctx.getAsString("mid");
		// 消费金额
		Double amount = ctx.getAsDouble("money");
		// 时间戳
		String time = ctx.getAsString("time");
		// 加密串
		// sign=md5(order=xxxx&money=xxxx&mid=xxxx&time=xxxx&result=x&ext=xxx&key=xxxx)
		String sign = ctx.getAsString("signature");
		// 0=失败；1=成功
		Integer status = ctx.getAsInt("result");

		boolean flag = true;
		flag = flag && orderId != null && !orderId.trim().equals("") && orderId.length() >= 5;
		flag = flag && billno != null && !billno.trim().equals("");
		flag = flag && amount != null && amount > 0.0;
		flag = flag && time != null;
		flag = flag && status != null && status == 1;
		flag = flag && sign != null && !sign.trim().equals("");

		try {

			if (flag && _validateNotify(billno, amount, mid, time, status, orderId, sign)) {

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderId);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("DangLe notify can't find order id [" + orderId + "]");
					return new StringPage("fail");
				}

				order = list.get(0);
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(billno);
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("DangLe order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("success");
			} else {
				logger.info("DangLe notify param validate fail");
				return new StringPage("DangLe notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("failure");
	}

	private boolean _validateNotify(String billno, Double amount, String mid, String time, Integer status, String orderId, String sign) {
		String checkSign = MD5Util.decode("order=" + billno + "&money=" + String.format("%.2f", amount) + "&mid=" + mid + "&time=" + time + "&result=" + status
		        + "&ext=" + orderId + "&key=" + PAYMENT_KEY);
		return sign.toLowerCase().equals(checkSign.toLowerCase());
	}

}
