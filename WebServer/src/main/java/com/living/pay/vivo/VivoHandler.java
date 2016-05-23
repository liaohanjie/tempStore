package com.living.pay.vivo;

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
import com.living.util.DateUtil;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * Vivo SDK (未完成)
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月21日
 */
public class VivoHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(VivoHandler.class);

	static final String CREATE_ORDER_URL = PropertyUtils.SYS_CONFIG.get("sdk.vivo.auth.url");

	static final String CP_ID = PropertyUtils.SYS_CONFIG.get("sdk.vivo.cp.id");

	static final String CP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.vivo.cp.key");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.vivo.app.ip");

	static final String UTF_8 = "UTF-8";

	private static final Map<String, String> HEADERS = new HashMap<String, String>();

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String userId = ctx.getAsString("userId");

		if (userId == null || userId.trim().equals("")) {
			jsonResult.setMsg("userId is null");
			logger.warn("userId or userId is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			jsonResult = JsonResult.success();
			jsonResult.setObj(userId);
			account.setUserName(userId);
			processLogin(ctx, account);
			return new StringPage(JSONUtil.toJson(jsonResult));
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}

	@Override
	public Map<String, Object> submitOrderCallback(WebContext ctx, PayOrder order) {
		String version = "1.0.0";
		String signMethod = "MD5";
		String cpOrderNumber = order.getOrderNo();
		String notifyUrl = "http://182.254.147.65/97002/OrderHandler/notify.do";
		// String notifyUrl =
		// "http://dev.soul-web.sfplay.net/97002/OrderHandler/notify.do";
		String orderTime = DateUtil.dateTostring(order.getCreateTime(), "yyyyMMddHHmmss");
		String orderAmount = String.valueOf(order.getAmount() * 100);
		String orderTitle = "游戏道具";
		String orderDesc = "游戏道具";
		String extInfo = order.getOrderNo();

		try {
			StringBuffer sb = new StringBuffer();
			sb.append("appId=").append(APP_ID);
			sb.append("&cpId=").append(CP_ID);
			sb.append("&cpOrderNumber=").append(cpOrderNumber);
			sb.append("&extInfo=").append(extInfo);
			sb.append("&notifyUrl=").append(notifyUrl);
			sb.append("&orderAmount=").append(orderAmount);
			sb.append("&orderDesc=").append(orderDesc);
			sb.append("&orderTime=").append(orderTime);
			sb.append("&orderTitle=").append(orderTitle);
			sb.append("&version=").append(version);
			sb.append("&").append(MD5Util.decode(CP_KEY));
			// sb.append("&").append(CP_KEY);

			logger.info(sb.toString());

			String signature = DigestUtils.md5Hex(sb.toString().getBytes(UTF_8));
			String params = "version=" + version + "&signMethod=" + signMethod + "&signature=" + signature + "&cpId=" + CP_ID + "&appId=" + APP_ID
			        + "&cpOrderNumber=" + cpOrderNumber + "&orderTime=" + orderTime + "&orderAmount=" + orderAmount + "&orderTitle=" + orderTitle
			        + "&orderDesc=" + orderDesc + "&extInfo=" + extInfo + "&notifyUrl=" + notifyUrl;
			String result = HttpUtil.postRet(CREATE_ORDER_URL, params, UTF_8, UTF_8, HEADERS);
			logger.info(result);

			if (result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
				});
				String respCode = String.valueOf(resMap.get("respCode"));
				String respMsg = String.valueOf(resMap.get("respMsg"));
				if ("200".equals(respCode)) {
					Map<String, Object> obj = new HashMap<>();
					obj.put("accessKey", resMap.get("accessKey"));
					obj.put("orderNumber", resMap.get("orderNumber"));
					return obj;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		String respCode = ctx.getAsString("respCode");
		String respMsg = ctx.getAsString("respMsg");
		// 签名方法
		String signMethod = ctx.getAsString("signMethod");
		// 签名信息
		String signature = ctx.getAsString("signature");
		// 交易种类 目前固定01
		String tradeType = ctx.getAsString("tradeType");
		// 交易状态, 0000，代表支付成功
		String tradeStatus = ctx.getAsString("tradeStatus");
		// cp-id 定长20位数字，由vivo分发的唯一识别码
		String cpId = ctx.getAsString("cpId");
		// app_id
		String appId = ctx.getAsString("appId");
		// userid
		String uid = ctx.getAsString("uid");
		// 订单号
		String orderId = ctx.getAsString("cpOrderNumber");
		// 交易流水号
		String billNo = ctx.getAsString("orderNumber");
		// 交易金额 单位：分，币种：人民币，为长整型，如：101，10000
		String orderAmount = ctx.getAsString("orderAmount");
		String extInfo = ctx.getAsString("extInfo");
		// 交易时间 yyyyMMddHHmmss
		String payTime = ctx.getAsString("payTime");

		StringBuffer sb = new StringBuffer();
		sb.append("appId=").append(APP_ID);
		sb.append("&cpId=").append(CP_ID);
		sb.append("&cpOrderNumber=").append(orderId);
		sb.append("&extInfo=").append(extInfo);
		sb.append("&orderAmount=").append(orderAmount);
		sb.append("&orderNumber=").append(billNo);
		sb.append("&payTime=").append(payTime);
		sb.append("&respCode=").append(respCode);
		sb.append("&respMsg=").append(respMsg);
		sb.append("&tradeStatus=").append(tradeStatus);
		sb.append("&tradeType=").append(tradeType);
		sb.append("&uid=").append(uid);
		sb.append("&").append(MD5Util.decode(CP_KEY));

		boolean flag = true;
		flag = flag && billNo != null && !billNo.trim().equals("");
		flag = flag && orderId != null && !orderId.trim().equals("");

		try {

			if (flag && _validateNotify(signature, sb.toString())) {

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
