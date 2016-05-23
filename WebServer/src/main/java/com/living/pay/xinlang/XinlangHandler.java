package com.living.pay.xinlang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
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
 * 新浪 SDK 未完成
 * 
 * @author lipp 2015年10月28日
 */
public class XinlangHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(XinlangHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.xl.auth.url");

	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.xl.app.key");

	static final String APP_SECRET = PropertyUtils.SYS_CONFIG.get("sdk.xl.app.secret");

	static final String REDIRECT_URL = PropertyUtils.SYS_CONFIG.get("sdk.xl.redirect.url");

	static final String SIGNATURE_KEY = PropertyUtils.SYS_CONFIG.get("sdk.xl.signature.key");

	static final String UTF_8 = "UTF-8";

	static final Map<String, String> TYPE_REFERENCE = new HashMap<String, String>();

	/**
	 * a)参数signature不参与签名 b)值为空的参数，也需要参与签名 c)若参数中包含特殊字符,例如中文,&,%,@等,
	 * 需要在签名之后进行url_encode d)目前仅支持md5方式签名
	 * 
	 * @param deviceid
	 * @param token
	 * @param suid
	 * @return
	 */
	private String _requestAuth(String deviceid, String token, String suid) {
		String signString = "appkey=" + APP_KEY + "&deviceid=" + deviceid + "&token=" + token + "&suid=" + suid;
		String signature = MD5Util.decode(signString + "|" + SIGNATURE_KEY);
		// String encodedSign = UrlEncoded.encodeString(md5Sign);
		return HttpUtil.postRet(AUTH_URL, "appkey=" + APP_KEY + "&deviceid=" + deviceid + "&token=" + token + "&suid=" + suid + "&signature=" + signature,
		        UTF_8, UTF_8);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		String deviceid = ctx.getAsString("deviceid");
		String token = ctx.getAsString("token");
		String suid = ctx.getAsString("suid");

		if (deviceid == null || deviceid.trim().equals("") || token == null || token.trim().equals("") || suid == null || suid.trim().equals("")) {
			logger.error("deviceid is invalid [" + deviceid + "]");
			jsonResult.setMsg("xinlang deviceid or token or suid is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		try {
			String result = _requestAuth(deviceid, token, suid);
			logger.info("xinlang auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				TypeReference<Map<String, String>> trf = new TypeReference<Map<String, String>>() {
				};
				Map<String, String> resMap = JSONUtil.toObject(result, trf);

				if (resMap != null && !resMap.isEmpty()) {
					String resSuid = resMap.get("suid");
					// String resToken = resMap.get("token");
					// 账号类型:1 快速试玩 2手机 3新浪通行证 99微博
					// String resUsertype = resMap.get("usertype");

					if (suid != null || suid.trim().equals("")) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(resSuid);
						// jsonResult.setObj(resToken);
						// jsonResult.setObj(resUsertype);
						account.setUserName(suid);
						processLogin(ctx, account);
					}
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
		// 调用下单接口获取到的订单号
		String billNO = ctx.getAsString("order_id");

		// 支付金额，单位 分
		Integer amount = ctx.getAsInt("amount");

		// 支付用户id
		String orderUid = ctx.getAsString("order_uid");

		// 应用的appkey
		String source = ctx.getAsString("source");

		// 实际支付金额，单位 分
		Integer actualAmount = ctx.getAsInt("actual_amount");

		// 透传参数（该参数的有无决定于下单时有没有上传pt参数）
		String orderNo = ctx.getAsString("pt");

		// 用于参数校验的签名
		String sign = ctx.getAsString("signature");

		// 参数排序
		List<String> paramNameList = new ArrayList<>();
		paramNameList.add("order_id");
		paramNameList.add("amount");
		paramNameList.add("order_uid");
		paramNameList.add("source");
		paramNameList.add("actual_amount");
		paramNameList.add("pt");
		Collections.sort(paramNameList);

		// 参数拼接
		StringBuffer sb = new StringBuffer();
		for (String input : paramNameList) {
			String value = ctx.getAsString(input);
			if (value != null && !value.trim().equals("")) {
				sb.append(input).append("=").append(value).append("|");
			}
		}
		sb.append(APP_SECRET);

		boolean flag = true;
		flag = flag && billNO != null && !billNO.trim().equals("");
		flag = flag && amount != null && amount != 0;
		flag = flag && orderUid != null && !orderUid.trim().equals("");
		flag = flag && source != null && source.trim().equals("");
		flag = flag && actualAmount != null && actualAmount != 0;
		flag = flag && orderNo != null && !orderNo.trim().equals("");
		flag = flag && sign != null && !sign.trim().equals("");

		try {
			if (flag && _validateNotify(sign, sb.toString())) {
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
					order.setBillNo(billNO);
					order.setStatus(PayOrder.STATUS_成功通知渠道);
					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("OK");
			} else {
				logger.info("notify param validate fail");
				return new StringPage("xinlang notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String sign, String signString) {
		String checkSign = DigestUtils.sha1Hex(signString);
		return sign.equals(checkSign);
	}
}
