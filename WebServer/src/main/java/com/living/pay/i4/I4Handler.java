package com.living.pay.i4;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;
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
 * 爱思支付处理
 * 
 * @author zhoujf
 * @date 2015年6月15日
 */
public class I4Handler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(I4Handler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.i4.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.i4.app.id");

	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.i4.app.key");

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String token) {
		return HttpUtil.postRet(AUTH_URL, "token=" + token, "UTF-8", "UTF-8");
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String token = ctx.getAsString("token");
		if (token == null || token.trim().equals("")) {
			jsonResult.setMsg("i4 token is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			String result = _requestAuth(token);
			logger.info("i4 auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
				});

				if (resMap != null && !resMap.isEmpty()) {
					Integer status = Integer.parseInt(String.valueOf(resMap.get("status")));

					if (State.SUCCESS.getCode() == status) {
						String userName = String.valueOf(resMap.get("username"));
						Integer userId = Integer.parseInt(String.valueOf(resMap.get("userid")));

						jsonResult = JsonResult.success();
						jsonResult.setObj(userId);

						account.setUserName(userId + "");
						processLogin(ctx, account);
						return new StringPage(JSONUtil.toJson(jsonResult));
					} else if (State.TOKEN_INVALID.getCode() == status) {
						jsonResult.setMsg(State.TOKEN_INVALID.getMsg());
					} else if (State.USER_NOT_EXISTED.getCode() == status) {
						jsonResult.setMsg(State.USER_NOT_EXISTED.getMsg());
					} else if (State.SESSION_TIME_OUT.getCode() == status) {
						jsonResult.setMsg(State.SESSION_TIME_OUT.getMsg());
					} else {
						jsonResult.setMsg("fail");
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
		// 兑换订单号
		String billno = ctx.getAsString("order_id");
		// 厂商订单号
		String orderId = ctx.getAsString("billno");
		// 通行账号
		String account = ctx.getAsString("account");
		// 兑换爱思币数量
		Double amount = ctx.getAsDouble("amount");
		// 状态 : 0 正常 ， 1 已兑换过并成功返回
		Integer status = ctx.getAsInt("status");
		// 厂商 app_id
		String appId = ctx.getAsString("app_id");
		// roleid 角色id
		Integer roleId = ctx.getAsInt("role");
		// 区服编号
		Integer zone = ctx.getAsInt("zone");
		// 签名 RSA私钥加密
		String sign = ctx.getAsString("sign");

		boolean flag = true;
		flag = flag && orderId != null && !orderId.trim().equals("") && orderId.length() >= 5;
		flag = flag && billno != null && !billno.trim().equals("") && billno.length() >= 5;
		flag = flag && account != null && !account.trim().equals("");
		flag = flag && amount != null && amount > 0.0;
		flag = flag && appId != null && !appId.trim().equals("");
		// flag = flag && uuid != null && !uuid.trim().equals("");
		flag = flag && roleId != null && roleId > 0;
		flag = flag && zone != null;
		flag = flag && sign != null && !sign.trim().equals("");
		flag = flag && status != null;

		try {

			if (flag && _validateNotify(orderId, billno, amount, roleId, sign, status)) {

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderId);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("i4 notify can't find order id [" + orderId + "]");
					return new StringPage("fail");
				}

				order = list.get(0);
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(billno);
					// order.setExt1(uuid);
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("pp order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("success");
			} else {
				logger.info("i4 notify param validate fail");
				return new StringPage("i4 notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String orderId, String billNo, double amount, int roleId, String sign, int status) throws Exception {
		Map<String, String> map = parseSignature(sign);
		if (map != null && !map.isEmpty()) {
			boolean flag = true;
			flag = flag && APP_ID.equals(map.get("app_id"));
			flag = flag && billNo.equals(map.get("order_id"));
			flag = flag && orderId.equals(map.get("billno"));
			flag = flag && roleId == Integer.parseInt(map.get("role"));
			// flag = flag && account == map.get("account");
			// flag = flag && zone.equals(map.get("zone"));
			flag = flag && status == Integer.parseInt(map.get("status"));
			flag = flag && amount == Double.parseDouble(map.get("amount"));
			return flag;
		}
		return false;
	}

	/**
	 * 解析应答字符串，生成应答要素
	 * 
	 * @param str
	 *            需要解析的字符串
	 * @return 解析的结果map
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String, String> parseQString(String str) {
		Map<String, String> map = new HashMap<String, String>();
		int len = str.length();
		StringBuilder temp = new StringBuilder();
		char curChar;
		String key = null;
		boolean isKey = true;
		for (int i = 0; i < len; i++) {// 遍历整个带解析的字符串
			curChar = str.charAt(i);// 取当前字符
			if (curChar == '&') {// 如果读取到&分割符
				putKeyValueToMap(temp, isKey, key, map);
				temp.setLength(0);
				isKey = true;
			} else {
				if (isKey) {// 如果当前生成的是key
					if (curChar == '=') {// 如果读取到=分隔符
						key = temp.toString();
						temp.setLength(0);
						isKey = false;
					} else {
						temp.append(curChar);
					}
				} else {// 如果当前生成的是value
					temp.append(curChar);
				}
			}
		}
		putKeyValueToMap(temp, isKey, key, map);
		return map;
	}

	/**
	 * 生成签名
	 * 
	 * @param req
	 *            需要解析签名的要素
	 * @return 解析签名的结果map
	 * @throws Exception
	 */
	public static Map<String, String> parseSignature(String sign) throws Exception {
		byte[] dcDataStr = Base64.decodeBase64(sign);
		byte[] plainData = RSADecrypt.decryptByPublicKey(dcDataStr, RSADecrypt.DEFAULT_PUBLIC_KEY);
		String parseString = new String(plainData);
		logger.info("i4 decode sign: " + parseString);
		return parseQString(parseString);
	}

	private static void putKeyValueToMap(StringBuilder temp, boolean isKey, String key, Map<String, String> map) {
		if (isKey) {
			key = temp.toString();
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, "");
		} else {
			if (key.length() == 0) {
				throw new RuntimeException("QString format illegal");
			}
			map.put(key, temp.toString());
		}
	}

	public enum State {

		SUCCESS(0, "success"), TOKEN_INVALID(1, "invalid token"), USER_NOT_EXISTED(2, "user not exsited"), SESSION_TIME_OUT(3, "session time out");

		private int code;
		private String msg;

		private State(int code, String msg) {
			this.code = code;
			this.msg = msg;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}

	}

}
