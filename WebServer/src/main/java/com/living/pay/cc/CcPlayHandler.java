package com.living.pay.cc;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * 虫虫 SDK (未完成)
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月21日
 */
public class CcPlayHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(CcPlayHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.cc.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.cc.cp.id");

	static final String DEV_KEY = PropertyUtils.SYS_CONFIG.get("sdk.cc.dev.key");

	static final String SIGN_KEY = PropertyUtils.SYS_CONFIG.get("sdk.cc.sign.key");

	static final String UTF_8 = "UTF-8";

	private static final Map<String, String> HEADERS = new HashMap<String, String>();

	private String _requestAuth(String token) throws UnsupportedEncodingException {
		return HttpUtil.postRet(AUTH_URL, "token=" + token, UTF_8, UTF_8, HEADERS);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String token = ctx.getAsString("token");
		String userId = ctx.getAsString("userId");

		if (token == null || token.trim().equals("") || userId == null || userId.trim().equals("")) {
			jsonResult.setMsg("token is null");
			logger.warn("token or userId is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			String result = _requestAuth(token);
			logger.info("auth result: " + result);

			if (result != null && !result.trim().equals("success")) {
				jsonResult = JsonResult.success();
				// jsonResult.setObj(uid);
				account.setUserName(userId);
				processLogin(ctx, account);
				return new StringPage(JSONUtil.toJson(jsonResult));
			}
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		// statusCode
		// 0000 支付成功
		// 0001 支付正在处理中
		// 0002 支付失败
		// 0003 用户中途取消

		String billNo = ctx.getAsString("transactionNo");
		String orderId = ctx.getAsString("partnerTransactionNo");
		// 订单状态
		String statusCode = ctx.getAsString("statusCode");
		// 支付商品的Id
		String productId = ctx.getAsString("productId");
		// 订单金额
		Integer orderPrice = ctx.getAsInt("orderPrice");
		// 游戏ID
		String packageId = ctx.getAsString("packageId");
		String sign = ctx.getAsString("sign");

		List<String> paramNameList = new ArrayList<>();
		paramNameList.add("transactionNo");
		paramNameList.add("partnerTransactionNo");
		paramNameList.add("statusCode");
		paramNameList.add("statusCode");
		paramNameList.add("productId");
		paramNameList.add("orderPrice");
		paramNameList.add("packageId");
		Collections.sort(paramNameList);

		StringBuffer sb = new StringBuffer();
		for (String input : paramNameList) {
			String value = ctx.getAsString(input);
			if (value != null && !value.trim().equals("")) {
				sb.append(input).append("=").append(value).append("&");
			}
		}
		sb.append(SIGN_KEY);

		boolean flag = true;
		flag = flag && billNo != null && !billNo.trim().equals("");
		flag = flag && orderId != null && !orderId.trim().equals("");
		flag = flag && statusCode != null && !statusCode.trim().equals("");
		flag = flag && orderPrice != null && orderPrice > 0;
		flag = flag && sign != null && !sign.trim().equals("");
		flag = flag && statusCode != null && statusCode.trim().equals("0000");

		try {

			if (flag && _validateNotify(sign, sb.toString())) {

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

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				}
				return new StringPage("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String sign, String signString) throws Exception {
		String checkSign = MD5Util.decode(signString);
		return checkSign.equals(sign);
	}

}
