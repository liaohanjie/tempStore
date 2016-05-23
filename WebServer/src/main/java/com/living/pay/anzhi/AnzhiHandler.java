package com.living.pay.anzhi;

import java.util.Date;
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
import com.living.util.DateUtil;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * 安智 SDK
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月28日
 */
public class AnzhiHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(AnzhiHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.anzhi.auth.url");

	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.anzhi.app.key");

	static final String APP_SECRET = PropertyUtils.SYS_CONFIG.get("sdk.anzhi.app.secret");

	static final Map<String, String> HEADERS = new HashMap<String, String>();
	static {
		HEADERS.put("Content-type", "text/xml; charset=UTF-8");
		HEADERS.put("appKey", APP_KEY);
		HEADERS.put("host", AUTH_URL);
	}

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String msg) {
		return HttpUtil.postRet(AUTH_URL, msg, UTF_8, UTF_8, HEADERS);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String id = ctx.getAsString("id");
		String loginName = ctx.getAsString("login_name");
		String deviceid = ctx.getAsString("deviceid");
		String time = DateUtil.dateTostring(new Date(), "yyyyMMddHHmmssSSS");
		String gameUser = "{\"id\":" + id + "," + "\"loginname\":\"" + loginName + "\"," + "\"deviceid\":\"" + deviceid + "\"}";
		String msgTemp = "{'head':{'appkey':'" + APP_KEY + "','version':'1.0','time':'" + time + "'},'body':{'msg':{'gameUser':'" + gameUser + "','time':'"
		        + time + "'},'ext':{}}}";

		try {
			String request = Des3Util.encrypt(msgTemp, APP_SECRET);

			String result = _requestAuth(request);
			logger.info("auth result: " + result);

			if (result == null || result.trim().equals("")) {
				jsonResult.setMsg("auth result is null or empty.");
				return new StringPage(JSONUtil.toJson(jsonResult));
			}

			Map<String, Object> resMap = JSONUtil.toObject(result.replaceAll("'", "\""), new TypeReference<Map<String, Object>>() {
			});

			String msg = Des3Util.decrypt((String) resMap.get("msg"), APP_SECRET);
			String sign = Des3Util.getSigned((String) resMap.get("sign"));

			Map<String, String> msgMap = JSONUtil.toObject(msg.replaceAll("'", "\""), new TypeReference<Map<String, String>>() {
			});

			// String sid = msgMap.get("sid");
			if (msgMap != null && !msgMap.isEmpty()) {
				String uid = (String) msgMap.get("uid");

				if (uid != null && !uid.trim().equals("")) {
					jsonResult = JsonResult.success();
					jsonResult.setObj(loginName);
					account.setUserName(loginName);
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

		String result = Des3Util.decrypt(ctx.getAsString("data"), APP_SECRET);
		logger.info("notify result: " + result);

		if (result == null || result.trim().equals("")) {
			return new StringPage("fail");
		}

		Map<String, String> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, String>>() {
		});

		String uid = resMap.get("uid"); // 安智帐号
		String billno = resMap.get("orderId"); // 订单号
		String orderAmount = resMap.get("orderAmount");// 用户实际支付金额
		String orderTime = resMap.get("orderTime"); // 支付时间
		String orderAccount = resMap.get("orderAccount");// 支付帐号
		int code = Integer.parseInt(resMap.get("code"));// 订单状态（1为成功）
		String notifyTime = resMap.get("notifyTime");// 通知时间
		/*
		 * 回调信息，此参数可作为厂商判别 用户充值的身份标记（如保存用户账号 ID 和所在区分等身份 信息）或其他用处。
		 * 若用户以此参数为自己内部自定义订单号，只作为内部参考，将不作为用户充值订单唯一性依据。
		 */
		String orderNo = resMap.get("cpInfo");
		String msg = resMap.get("msg");
		String payAmount = resMap.get("payAmount"); // 暂无效
		String memo = resMap.get("memo"); // 备注
		String redBagMoney = resMap.get("redBagMoney"); // 礼券金额（单位为分）

		boolean flag = true;
		flag = flag && uid != null && !uid.trim().equals("");
		flag = flag && orderNo != null && !orderNo.trim().equals("") && orderNo.length() >= 5;
		flag = flag && billno != null && !billno.trim().equals("");
		flag = flag && orderAmount != null;
		flag = flag && orderTime != null;
		flag = flag && orderAccount != null;
		flag = flag && code == 1;
		flag = flag && notifyTime != null;

		try {
			if (flag) {
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

}
