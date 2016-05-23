package com.living.pay.xiaomi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.HmacUtils;
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
 * XiaoMi SDK (未完成)
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月30日
 */
public class XiaoMiHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(XiaoMiHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.xiaomi.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.xiaomi.app.id");
	
	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.xiaomi.app.key");
	
	static final String APP_SECRET = PropertyUtils.SYS_CONFIG.get("sdk.xiaomi.app.secret");

	static final Map<String, String> HEADERS = new HashMap<String, String>();
	static {
	}

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String session, String uid, String signature) {
		return HttpUtil.postRet(AUTH_URL, "appId=" + APP_ID + "&session=" + session + "&uid=" + uid + "&signature=" + signature, UTF_8, UTF_8, HEADERS);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String userId = ctx.getAsString("userId");
		String session = ctx.getAsString("session");
		String uid = ctx.getAsString("uid");
		String signature = ctx.getAsString("signature");

		if (userId == null || userId.trim().equals("")) {
			jsonResult.setMsg("userId is null");
			logger.warn("userId or userId is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {

			String result = _requestAuth(session, uid, signature);
			logger.info("auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
				});

				String errcode = String.valueOf(resMap.get("errcode"));
				if ("200".equals(errcode)) {
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
		// 游戏ID
		String appId = ctx.getAsString("appId");
		// 开发商订单ID
		String orderNo = ctx.getAsString("cpOrderId");
		// 开发商透传信息
		String cpUserInfo = ctx.getAsString("cpUserInfo");
		// 用户ID
		String uid = ctx.getAsString("uid");
		// 游戏平台订单ID
		String billNo = ctx.getAsString("orderId");
		// 订单状态，TRADE_SUCCESS 代表成功
		String orderStatus = ctx.getAsString("orderStatus");
		// 支付金额,单位为分,即0.01 米币。
		Integer payFee = ctx.getAsInt("payFee");
		// 商品代码
		String productCode = ctx.getAsString("productCode");
		// 商品名称
		String productName = ctx.getAsString("productName");
		// 商品数量
		String productCount = ctx.getAsString("productCount");
		// 支付时间,格式 yyyy-MM-dd HH:mm:ss
		String payTime = ctx.getAsString("payTime");
		// 订单类型：10：普通订单11：直充直消订单
		String orderConsumeType = ctx.getAsString("orderConsumeType");
		// 使用游戏券金额 （如果订单使用游戏券则有,long型），如果有则参与签名
		String partnerGiftConsume = ctx.getAsString("partnerGiftConsume");
		// 签名
		String signature = ctx.getAsString("signature");

		List<String> paramNameList = new ArrayList<>();
		paramNameList.add("appId");
		paramNameList.add("cpOrderId");
		paramNameList.add("orderConsumeType");
		paramNameList.add("orderId");
		paramNameList.add("orderStatus");
		paramNameList.add("payFee");
		paramNameList.add("payTime");
		paramNameList.add("productCode");
		paramNameList.add("productCount");
		paramNameList.add("productName");
		paramNameList.add("uid");
		Collections.sort(paramNameList);

		boolean flag = true;
		flag = flag && orderNo != null && !orderNo.trim().equals("");
		flag = flag && orderStatus != null && orderStatus.equals("TRADE_SUCCESS");
		flag = flag && signature != null && !signature.trim().equals("");

		StringBuffer sb = new StringBuffer();
		for (String paramName : paramNameList) {
			String value = ctx.getAsString(paramName);
			if (value != null && !value.trim().equals("")) {
				sb.append(paramName).append("=").append(value).append("&");
			}
		}

		String signString = sb.substring(0, sb.length() - 1);

		try {

			if (flag && _validateNotify(signString, signature)) {

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
					order.setBillNo(billNo);
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("{\"errcode\": 200,\"errMsg\":\"success\"}");
			} else {
				logger.info("notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("{\"errcode\": 3515,\"errMsg\":\"错误, 订单信息不一致\"}");
	}

	private boolean _validateNotify(String signString, String sign) {
		String checkSign = HmacUtils.hmacSha1Hex(APP_SECRET, signString);
		return sign.equals(checkSign);
	}
}
