package com.living.pay.tt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
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
 * TTSDK (未完成)
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年4月11日
 */
public class TTHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(TTHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.lj.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.lj.app.id");

	static final String PRODUCT_KEY = PropertyUtils.SYS_CONFIG.get("sdk.lj.product.key");

	static final String TEST_CHANNEL_KEY = PropertyUtils.SYS_CONFIG.get("sdk.lj.test.channel.key");

	static final Map<String, String> HEADERS = new HashMap<String, String>();
	static {
		HEADERS.put("Content-type", "application/x-www-form-urlencoded");
	}

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String userId, String channel, String token, String productCode) {
		return HttpUtil.postRet(AUTH_URL, "userId=" + userId + "&channel=" + channel + "&token=" + token + "&productCode=" + productCode, "UTF-8", "UTF-8",
		        HEADERS);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String userId = ctx.getAsString("userId");
		String channel = ctx.getAsString("channel");
		String token = ctx.getAsString("token");
		String productCode = ctx.getAsString("productCode");

		try {
			String result = _requestAuth(userId, channel, token, productCode);
			logger.info("auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				if ("true".equals(result)) {
					jsonResult = JsonResult.success();
					jsonResult.setObj(userId);
					account.setUserName(userId + "");
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
		// 平台订单号
		String billno = ctx.getAsString("orderId");
		// 渠道编码，同客户端接入文档中的channelID
		String channelCode = ctx.getAsString("channelCode");
		// 透传参数（经过base64编码）
		String callbackInfo = ctx.getAsString("callbackInfo");
		// 厂商订单号
		String orderId = ctx.getAsString("channelOrderId");
		// 本次充值金额，整数，单位分
		Integer price = ctx.getAsInt("price");
		// 签名
		String sign = ctx.getAsString("sign");
		// 渠道标识
		String channelLabel = ctx.getAsString("channelLabel");

		boolean flag = true;
		flag = flag && orderId != null && !orderId.trim().equals("") && orderId.length() >= 5;
		flag = flag && billno != null && !billno.trim().equals("");
		flag = flag && price != null && price > 0;
		flag = flag && sign != null && !sign.trim().equals("");

		try {

			if (flag && _validateNotify(billno, price, callbackInfo, sign)) {

				String orderNo = new String(Base64.decodeBase64(callbackInfo));

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderNo);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("notify can't find order id [" + orderNo + "]");
					return new StringPage("fail");
				}
				
				order = list.get(0);
				
				if (price / 100 == 0 || order.getAmount() != price / 100) {
					logger.warn("notify amount is error. order id [" + orderNo + "], price=" + price);
					return new StringPage("fail");
				}
				
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(billno);
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("success");
			} else {
				logger.info("notify param validate fail");
				return new StringPage("xy notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String billno, int price, String callbackInfo, String sign) {
		String checkSign = MD5Util.decode(billno + price + callbackInfo + PRODUCT_KEY);
		return sign.equals(checkSign);
	}
}
