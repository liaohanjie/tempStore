package com.living.pay.qihoo;

import java.util.ArrayList;
import java.util.Collections;
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
import com.ks.util.MD5Util;
import com.living.pay.ChannelHandler;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * Qihoo 360 SDK (未完成)
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月28日
 */
public class QihooHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(QihooHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.qihoo360.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.qihoo360.app.id");

	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.qihoo360.app.key");

	static final String APP_SECRET = PropertyUtils.SYS_CONFIG.get("sdk.qihoo360.app.secret");

	static final Map<String, String> HEADERS = new HashMap<String, String>();
	static {
	}

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String accessToken) {
		return HttpUtil.postRet(AUTH_URL, "access_token=" + accessToken, UTF_8, UTF_8, HEADERS);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String token = ctx.getAsString("access_token");

		try {
			String result = _requestAuth(token);
			logger.info("auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				Map<String, String> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, String>>() {
				});
				String id = resMap.get("id");
				// String name = resMap.get("name");
				// String avatar = resMap.get("avatar");
				// String sex = resMap.get("sex");
				// String area = resMap.get("area");
				// String nick = resMap.get("nick");

				if (id != null && !id.trim().endsWith("")) {
					jsonResult = JsonResult.success();
					jsonResult.setObj(id);
					account.setUserName(id);
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
		// 应用app key
		String appKey = ctx.getAsString("app_key");
		// 应用自定义的商品id
		String productId = ctx.getAsString("product_id");
		// 总价,以分为单位
		Integer amount = ctx.getAsInt("amount");
		// 应用分配给用户的id
		String appUid = ctx.getAsString("app_uid");
		// 扩展信息1
		String appExt1 = ctx.getAsString("app_ext1");
		// 扩展信息2
		String appExt2 = ctx.getAsString("app_ext2");
		// 360账号id
		Long userId = ctx.getAsLong("user_id");
		// 360返回的支付订单号
		Long orderId = ctx.getAsLong("order_id");
		// 如果支付返回成功，返回success 应用需要确认是success才给用户加钱
		String gatewayFlag = ctx.getAsString("gateway_flag");
		// 定值 md5
		String signType = ctx.getAsString("sign_type");
		// 应用订单号 支付请求时传递，原样返回
		String orderNo = ctx.getAsString("app_order_id");
		// 应用回传给订单核实接口的参数
		String sign_return = ctx.getAsString("sign_return");
		// 签名
		String sign = ctx.getAsString("sign");

		List<String> paramNameList = new ArrayList<>();
		paramNameList.add("amount");
		paramNameList.add("app_ext1");
		paramNameList.add("app_key");
		paramNameList.add("app_order_id");
		paramNameList.add("gateway_flag");
		paramNameList.add("order_id");
		paramNameList.add("product_id");
		paramNameList.add("sign_type");
		paramNameList.add("user_id");
		Collections.sort(paramNameList);

		StringBuffer sb = new StringBuffer();
		for (String paramName : paramNameList) {
			sb.append(ctx.getAsString(paramName)).append("#");
		}
		sb.append(APP_SECRET);

		boolean flag = true;
		flag = flag && appKey != null && !appKey.trim().equals("");
		flag = flag && amount != null && amount > 0;
		flag = flag && orderNo != null && !orderNo.trim().equals("");
		flag = flag && gatewayFlag != null && gatewayFlag.equals("success");
		flag = flag && sign != null && !sign.trim().equals("");

		try {

			if (flag && _validateNotify(sb.toString(), sign)) {

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderNo);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("notify can't find order id [" + orderNo + "]");
					return new StringPage("fail");
				}

				order = list.get(0);
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(orderId + "");
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("ok");
			} else {
				logger.info("notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String signString, String sign) {
		String checkSign = MD5Util.decode(signString);
		return sign.equals(checkSign);
	}
}
