package com.living.pay.wandoujia;

import java.util.HashMap;
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
import com.living.pay.ChannelHandler;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * 豌豆荚SDK
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月18日
 */
public class WanDouJiaHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(WanDouJiaHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.wdj.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.wdj.app.id");

	static final String SECRET_KEY = PropertyUtils.SYS_CONFIG.get("sdk.wdj.key");

	static final Map<String, String> HEADERS = new HashMap<String, String>();

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String uid, String token) {
		//return HttpUtil.postRet(AUTH_URL, "uid=" + uid + "&token=" + token + "&appkey_id=" + APP_ID, UTF_8, UTF_8, HEADERS);
		return HttpUtil.getRet(AUTH_URL, "uid=" + uid + "&token=" + token + "&appkey_id=" + APP_ID, UTF_8, UTF_8);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String userId = ctx.getAsString("userId");
		String token = ctx.getAsString("token");

		if (token == null || token.trim().equals("")) {
			jsonResult.setMsg("token is null");
			logger.warn("token is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			String result = _requestAuth(userId, token);
			logger.info("auth result: " + result);

			if (result != null && result.trim().equals("true")) {
				jsonResult = JsonResult.success();
				// jsonResult.setObj(userId);
				account.setUserName(userId);
				processLogin(ctx, account);
				return new StringPage(JSONUtil.toJson(jsonResult));
			} else {
				jsonResult.setMsg("auth is fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		// JSON
		String content = ctx.getAsString("content");
		// 值固定 RSA
		String signType = ctx.getAsString("signType");
		// 签名
		String sign = ctx.getAsString("sign");
		
		boolean flag = true;
		flag = flag && sign != null && !sign.trim().equals("");
		flag = flag && signType != null && signType.trim().equals("RSA");
		flag = flag && content != null && !content.trim().equals("");
		
		if (flag) {
			logger.warn("params validate fail");
			return new StringPage("wandoujia params is wrong.");
		}
		
		logger.info("content=" + content);
		Map<String, Object> resMap = JSONUtil.toObject(content, new TypeReference<Map<String, Object>>() {});
		// 订单号
		String orderId = resMap.get("out_trade_no") == null ? "" : String.valueOf(resMap.get("out_trade_no"));
		// 豌豆荚订单id
		String billNo = resMap.get("orderId") == null ? "" : String.valueOf(resMap.get("orderId"));
		// 时间戳
		/*String timeStamp = resMap.get("timeStamp") == null ? "" : String.valueOf(resMap.get("timeStamp"));
		// 支付金额,单位是（分）
		String money = resMap.get("money") == null ? "" : String.valueOf(resMap.get("money"));
		// 支付类型
		String chargeType = resMap.get("chargeType") == null ? "" : String.valueOf(resMap.get("chargeType"));
		// 
		String appKeyId = resMap.get("appKeyId") == null ? "" : String.valueOf(resMap.get("appKeyId"));
		// 购买人的账户id
		String buyerId = resMap.get("buyerId") == null ? "" : String.valueOf(resMap.get("buyerId"));
		// 充值卡id
		String cardNo = resMap.get("cardNo") == null ? "" : String.valueOf(resMap.get("cardNo"));*/
		
		flag = flag && orderId != null && !orderId.trim().equals("");
		
		try {

			if (flag && _validateNotify(content, sign)) {

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderId);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("notify can't find order id [" + orderId + "]");
					return new StringPage("fail");
				}

				order = list.get(0);
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(billNo);
					order.setStatus(PayOrder.STATUS_成功通知渠道);
					
					logger.info("order success. orderId=" + orderId);
					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("success");
			} else {
				logger.info("notify sign validate fail");
				return new StringPage("notify sign validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String content, String sign) {
		return WandouRsa.doCheck(content, sign);
	}

}
