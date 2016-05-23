package com.living.pay.m4399;

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
 * 4399 SDK (未完成)
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月26日
 */
public class M4399Handler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(M4399Handler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.m4399.auth.url");

	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.m4399.app.key");

	static final String SECRET_KEY = PropertyUtils.SYS_CONFIG.get("sdk.m4399.secret.key");

	static final Map<String, String> HEADERS = new HashMap<String, String>();
	static {
	}

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String state, String uid) {
		return HttpUtil.postRet(AUTH_URL, "state=" + state + "&uid=" + uid, UTF_8, UTF_8, HEADERS);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		// token
		String state = ctx.getAsString("state");
		String uid = ctx.getAsString("uid");

		try {
			String result = _requestAuth(state, uid);
			logger.info("auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
				});
				String code = "";
				if (resMap != null) {
					code = (String) resMap.get("code");
				}

				// 100:验证成功
				// 87:参数不全
				// 85:验证失败
				// 82:验证成功、但state有效期超时，重置state
				if ("100".equals(code)) {
					jsonResult = JsonResult.success();
					jsonResult.setObj(uid);
					account.setUserName(uid + "");
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
		// 渠道订单号
		String billno = ctx.getAsString("orderid");
		// 充值渠道id
		Integer pType = ctx.getAsInt("p_type");
		// 充值渠道id
		Integer uid = ctx.getAsInt("uid");
		// 用户充值的人民币金额，单位：元
		// Double money = ctx.getAsDouble("money");
		String money = ctx.getAsString("money");
		// 兑换的游戏币数量
		Integer gamemoney = ctx.getAsInt("gamemoney");
		// 要充值的服务区号(最多不超过8位)
		Integer serverid = ctx.getAsInt("serverid");
		// 作为预留字段
		String orderNo = ctx.getAsString("mark");
		// 要充值的游戏角色id
		String roleid = ctx.getAsString("roleid");
		// 发起请求时的时间戳
		// Integer time = ctx.getAsInt("time");
		String time = ctx.getAsString("time");
		// 签名 md5($orderid . $uid . $money . $gamemoney . $serverid . $secrect .
		// $mark . $roleid.$time); 当参数$serverid,$mark ,$roleid为空时，不参与签名计算。
		String sign = ctx.getAsString("sign");

		boolean flag = true;
		flag = flag && billno != null && !billno.trim().equals("");
		flag = flag && orderNo != null && !orderNo.trim().equals("") && orderNo.length() >= 5;
		flag = flag && pType != null && pType > 0;
		flag = flag && uid != null;
		flag = flag && money != null && !money.trim().equals("");
		flag = flag && gamemoney != null;
		flag = flag && sign != null && !sign.trim().equals("");

		try {

			if (flag
			        && _validateNotify(billno + String.valueOf(uid) + String.valueOf(money) + String.valueOf(gamemoney) + String.valueOf(serverid) + SECRET_KEY
			                + orderNo + roleid + time, sign)) {

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
				return new StringPage("{\"status\":2,\"code\":null,\"money\":\"" + money + "\",\"gamemoney\":\"" + gamemoney + "\",\"msg\":\"充值成功\"}");
			} else {
				logger.info("notify param validate fail");
				return new StringPage("{\"status\":1,\"code\":\"sign_error\",\"money\":\"" + money + "\",\"gamemoney\":\"" + gamemoney + "\",\"msg\":\"签名错误\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("{\"status\":1,\"code\":\"other_error\",\"money\":\"" + money + "\",\"gamemoney\":\"" + gamemoney + "\",\"msg\":\"签名错误\"}");
	}

	private boolean _validateNotify(String signString, String sign) {
		String checkSign = MD5Util.decode(signString);
		return sign.equals(checkSign);
	}
}
