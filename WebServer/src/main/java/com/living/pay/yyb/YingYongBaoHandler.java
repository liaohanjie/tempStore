package com.living.pay.yyb;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.pay.PayOrder;
import com.ks.util.JSONUtil;
import com.living.pay.ChannelHandler;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * 应用宝SDK
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月19日
 */
public class YingYongBaoHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(YingYongBaoHandler.class);

	static final boolean SANDBOX = PropertyUtils.SYS_CONFIG.getBool("sdk.yyb.sandbox", false);
	
	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.yyb.auth.url");
	
	static final String AUTH_URL_SANDBOX = PropertyUtils.SYS_CONFIG.get("sdk.yyb.auth.url.sandbox");

	static final Map<String, String> HEADERS = new HashMap<String, String>();
	
	static {
		HEADERS.put("Content-type", "application/x-www-form-urlencoded");
	}

	static final String UTF_8 = "UTF-8";

//	private String _requestAuth(String userId, String channel, String token, String productCode) {
//		return HttpUtil.postRet(AUTH_URL, "userId=" + userId + "&channel=" + channel + "&token=" + token + "&productCode=" + productCode, "UTF-8", "UTF-8",
//		        HEADERS);
//	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String userId = ctx.getAsString("userId");

		if (userId == null || userId.trim().equals("")) {
			jsonResult.setMsg("userId is null");
			logger.warn("userId is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			jsonResult = JsonResult.success();
			jsonResult.setObj(userId);
			account.setUserName(userId);
			processLogin(ctx, account);
			return new StringPage(JSONUtil.toJson(jsonResult));
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}
	
	@Override
	public Map<String, Object> submitOrderCallback(WebContext ctx, PayOrder order) {
		
	    return null;
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		// APPID以及QQ号码生成，即不同的appid下，同一个QQ号生成的OpenID是不一样的
		String openid = ctx.getAsString("openid");
		// 应用的唯一ID
		String appid = ctx.getAsString("appid");
		// 时间戳
		String ts = ctx.getAsString("ts");
		// 物品信息
		String payitem = ctx.getAsString("payitem");
		// 
		Integer price = ctx.getAsInt("price");
		// 
		String sign = ctx.getAsString("sign");
		// 
		String channelLabel = ctx.getAsString("channelLabel");
//
//		boolean flag = true;
//		flag = flag && orderId != null && !orderId.trim().equals("") && orderId.length() >= 5;
//		flag = flag && billno != null && !billno.trim().equals("");
//		flag = flag && price != null && price > 0;
//		flag = flag && sign != null && !sign.trim().equals("");
//
//		try {
//
//			if (flag && _validateNotify(billno, price, callbackInfo, sign)) {
//
//				String orderNo = new String(Base64.decodeBase64(callbackInfo));
//
//				PayOrder order = null;
//
//				PayOrderFilter filter = new PayOrderFilter();
//				filter.setOrderNo(orderNo);
//				List<PayOrder> list = payAction().getPayOrders(filter);
//				if (list == null || list.isEmpty()) {
//					logger.info("notify can't find order id [" + orderNo + "]");
//					return new StringPage("fail");
//				}
//
//				order = list.get(0);
//				if (order.getStatus() == PayOrder.STATUS_INIT) {
//					order.setBillNo(billno);
//					order.setStatus(PayOrder.STATUS_成功通知渠道);
//
//					// 修改订单状态，并发送游戏币
//					payAction().gainOrderPayOk(order);
//				} else {
//					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
//				}
//				return new StringPage("success");
//			} else {
//				logger.info("notify param validate fail");
//				return new StringPage("xy notify param validate fail");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return new StringPage("fail");
	}

//	private boolean _validateNotify(String billno, int price, String callbackInfo, String sign) {
//		String checkSign = MD5Util.decode(billno + price + callbackInfo + PRODUCT_KEY);
//		return sign.equals(checkSign);
//	}
}
