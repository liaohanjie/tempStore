package com.living.pay.jinli;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
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
 * 金立 SDK (未完成)
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月28日
 */
public class JinLiHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(JinLiHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.jinli.auth.url");

	static final String API_KEY = PropertyUtils.SYS_CONFIG.get("sdk.jinli.api.key");

	static final String SECRET_KEY = PropertyUtils.SYS_CONFIG.get("sdk.jinli.secret.key");

	static final String PUBLIC_KEY = PropertyUtils.SYS_CONFIG.get("sdk.jinli.public.key");

	static final String UTF_8 = "UTF-8";

	private String _requestAuth(String accessToken, Map<String, String> headers) {
		return HttpUtil.postRet(AUTH_URL, "access_token=" + accessToken, UTF_8, UTF_8, headers);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String playId = ctx.getAsString("playId");
		String isGuest = ctx.getAsString("isGuest");
		String token = ctx.getAsString("token");

		Long ts = System.currentTimeMillis() / 1000;
		String nonce = String.valueOf(System.currentTimeMillis() % 100000000);
		String method = "POST";
		String host = "id.gionee.com";
		String uri = "/account/verify.do";
		String port = "443";

		try {
			String signString = ts + "\n" + nonce + "\n" + method + "\n" + uri + "\n" + host + "\n" + port + "\n\n";
			String mac = Base64.encodeBase64String(HmacUtils.hmacSha1Hex(SECRET_KEY, signString).getBytes(UTF_8));
			String auth = "MAC id=\"" + API_KEY + "\",ts=\"" + ts + "\",nonce=\"" + nonce + "\",mac=\"" + mac + "\"";
			Map<String, String> headers = new HashMap<String, String>();

			String result = _requestAuth(token, headers);
			logger.info("auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
				});
				// 如果里面包含有“r”参数，则认为验证失败，否则成功
				// 1011 认证失败
				// 1020 服务器内部错误
				// 1031 发送超时
				// 1050 MAC签名的timestamp已过期
				// 1051 MAC签名的nonce重复

				Object r = resMap.get("r");
				if (!resMap.isEmpty() && r != null) {
					String webId = String.valueOf(resMap.get("wid"));
					String userId = String.valueOf(resMap.get("uid"));

					jsonResult = JsonResult.success();
					jsonResult.setObj(userId);
					account.setUserName(userId);
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

		// 签名
		String sign = ctx.getAsString("sign");
		String orderNo = ctx.getAsString("orderNo");
		String billNo = ctx.getAsString("billNo");

		Map<String, String> map = new HashMap<String, String>();
		Enumeration<String> attributeNames = ctx.getRequest().getParameterNames();
		while (attributeNames.hasMoreElements()) {
			String name = attributeNames.nextElement();
			map.put(name, ctx.getAsString(name));
		}

		StringBuilder contentBuffer = new StringBuilder();
		Object[] signParamArray = map.keySet().toArray();
		Arrays.sort(signParamArray);
		for (Object key : signParamArray) {
			String value = map.get(key);
			if (!"sign".equals(key) && !"msg".equals(key)) {// sign和msg不参与签名
				contentBuffer.append(key + "=" + value + "&");
			}
		}
		String content = contentBuffer.deleteCharAt(contentBuffer.length() - 1).toString();

		boolean flag = true;
		flag = flag && sign != null && !sign.trim().equals("");

		try {

			if (flag && _validateNotify(content, sign)) {

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
				return new StringPage("success");
			} else {
				logger.info("notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String content, String sign) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException,
	        SignatureException, IOException {
		return RSASignature.doCheck(content, sign, PUBLIC_KEY, "UTF-8");
	}
}
