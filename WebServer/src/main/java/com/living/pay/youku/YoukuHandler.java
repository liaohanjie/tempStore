package com.living.pay.youku;

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
 * Youku SDK (未完成)
 * 
 * @author lipp 2015年10月26日
 */

public class YoukuHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(YoukuHandler.class);

	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.youku.auth.url");

	static final String APP_ID =  PropertyUtils.SYS_CONFIG.get("sdk.youku.app.id");

	static final String APP_KEY =  PropertyUtils.SYS_CONFIG.get("sdk.youku.app.key");

	static final String APP_SECRET =  PropertyUtils.SYS_CONFIG.get("sdk.youku.app.secret");
	
	static final String PAY_KEY =  PropertyUtils.SYS_CONFIG.get("sdk.youku.pay.key");

	static final String UTF_8 = "UTF-8";

	static final Map<String, Object> TYPE_REFERENCE = new HashMap<String, Object>();

	/**
	 * @param sessionid
	 * @param appkey
	 * @param sign
	 *            ( appkey=xxxxxxxx&sessionid=xxxxxxxxx )
	 * @return
	 */
	private String _requestAuth(String sessionid) {
		String sign = "appkey=" + APP_KEY + "&sessionid=" + sessionid;
		return HttpUtil.postRet(AUTH_URL, "&appkey=" + APP_KEY + "&sessionid=" + sessionid + "&sign" + sign, UTF_8, UTF_8);
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		String sessionId = ctx.getAsString("sessionid");

		if (sessionId == null || sessionId.trim().equals("")) {
			logger.error("sessionId is invalid [" + sessionId + "]");
			jsonResult.setMsg("youku sessionid is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			String result = _requestAuth(sessionId);
			logger.info("youku auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				TypeReference<Map<String, Object>> trf = new TypeReference<Map<String, Object>>() {
				};
				Map<String, Object> resMap = JSONUtil.toObject(result, trf);

				if (resMap != null && !resMap.isEmpty()) {
					String status = (String) resMap.get("status");
					String uid = (String) resMap.get("uid");

					if ("success".equals(status)) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(uid);
						account.setUserName(uid);
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

		String url = ctx.getRemoteHost();

		// 订单号
		String apporderID = ctx.getAsString("apporderID");

		// 用户ID
		String uid = ctx.getAsString("uid");

		// 价格 单位分
		String price = ctx.getAsString("price");

		// 数字签名 以paykey 作为key , 对加密串进行HMAC(md5)加密，透传参数以及短代支付独有参数不参与签名计算
		String sign = ctx.getAsString("sign");

		// 透传参数（最长128位）
		String orderNo = ctx.getAsString("passthrough");

		// 计算结果 0:计费失败 1:计费成功 2:计费部分成功(短代支付独有的参数)
		Integer result = ctx.getAsInt("result");

		// 成功支付金额(短代支付独有的参数)
		Integer successAmount = ctx.getAsInt("success_amount");

		boolean flag = true;
		flag = flag && apporderID != null && !apporderID.trim().equals("");
		flag = flag && uid != null && !uid.trim().equals("");
		flag = flag && price != null && price.trim().equals("");
		flag = flag && sign != null && !sign.trim().equals("");
		flag = flag && orderNo != null && !orderNo.trim().equals("");
		flag = flag && result != null && result != 0;
		flag = flag && successAmount != null && successAmount != 0;

		try {
			if (flag && _validateNotify(url, apporderID, price, uid, sign)) {

				String billNo = new String(Base64.decodeBase64(apporderID));

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
				return new StringPage("youku notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String url, String apporderID, String price, String uid, String sign) {
		String param = url + "?apporderID="+apporderID + "&price" + price + "&uid" + uid;
		// String param ="http://www.callbackurl.com?apporderID=112233&price=100&uid=001";
		String checkSign = HmacUtils.hmacMd5Hex(PAY_KEY, param);
		return sign.equals(checkSign);
	}
}
