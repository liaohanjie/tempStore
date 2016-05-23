package com.living.pay.apple;

import java.io.UnsupportedEncodingException;
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
 * 苹果内购支付处理
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月8日
 */
public class IapHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(IapHandler.class);

	static final String IAP_VALIDATE_URL = "https://buy.itunes.apple.com/verifyReceipt";

	static final String IAP_VALIDATE_URL_DEBUG = "https://sandbox.itunes.apple.com/verifyReceipt";

	static final String APP_ID = "3951";

	static final String PRODUCT_ID = "1259";

	static final String APP_KEY = "";
	static final String PAY_KEY = "";

	static final String UTF_8 = "UTF-8";

	static final String IAP_SIGN_KEY = "iap.sfplay.net%*&IhsYLAPEIJ!";

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		try {
			logger.info("IAP auth");
			Integer userId = ctx.getAsInt("userId");
			jsonResult = JsonResult.success();
			jsonResult.setObj(userId);
			account.setUserName(userId + "");
			processLogin(ctx, account);
			return new StringPage(JSONUtil.toJson(jsonResult));
		} catch (Exception e) {
			jsonResult = JsonResult.error();
			e.printStackTrace();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		// iap 发送数据
		String receiptData = ctx.getAsString("requestData");
		// 唯一订单号
		String orderId = ctx.getAsString("orderId");
		// 时间戳
		String time = ctx.getAsString("time");
		// 客户端签名
		String sign = ctx.getAsString("sign");
		// 服务器签名
		String checkSign = MD5Util.decode(receiptData + orderId + time + IAP_SIGN_KEY);

		boolean flag = true;
		flag = flag && orderId != null && !orderId.trim().equals("") && orderId.length() >= 5;
		flag = flag && receiptData != null && !receiptData.trim().equals("");
		flag = flag && sign != null && sign.trim().equals(checkSign);

		if (!flag) {
			logger.warn("params is error.");
			return new StringPage("fail");
		}

		try {
			PayOrder order = null;
			PayOrderFilter filter = new PayOrderFilter();
			filter.setOrderNo(orderId);

			List<PayOrder> list = payAction().getPayOrders(filter);
			if (list == null || list.isEmpty()) {
				logger.info("IAP notify can't find order id [" + orderId + "]");
				return new StringPage("fail");
			}

			order = list.get(0);

			// 保存票据
			saveIapTicket(order, receiptData);

			// itunes 验证
			Map<String, Object> iapResultMap = _validateProcessResult(receiptData);

			flag = flag && iapResultMap != null && !iapResultMap.isEmpty();
			// status = 0 是有效的，其他无效
			flag = flag && iapResultMap.get("status") != null && String.valueOf(iapResultMap.get("status")).equals("0");

			if (flag) {
				if (order.getStatus() == PayOrder.STATUS_保存苹果票据) {
					order.setStatus(PayOrder.STATUS_成功通知渠道);
					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("IAP order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("success");
			} else {
				logger.info("IAP notify param validate fail, receipt=" + receiptData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	/**
	 * 保存票据
	 * 
	 * @param order
	 * @param receiptData
	 */
	private void saveIapTicket(PayOrder order, String receiptData) {
		// 判断订单状态
		if (order.getStatus() == PayOrder.STATUS_INIT) {
			order.setStatus(PayOrder.STATUS_保存苹果票据);
			// 保存票据
			order.setExt1(receiptData);
			payAction().updateIapNotifyOrder(order);
		}
	}

	/**
	 * itunes 验证
	 * 
	 * @param receiptData
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public Map<String, Object> _validateProcessResult(String receiptData) throws UnsupportedEncodingException {
		Map<String, Object> map = null;

		if (receiptData == null || receiptData.trim().equals("")) {
			return null;
		}

		String url = IAP_VALIDATE_URL_DEBUG;
		if (!PropertyUtils.SYS_CONFIG.getBool("evn.appple.sandbox")) {
			url = IAP_VALIDATE_URL;
		}

		String result = null;
		if (receiptData.indexOf("{") != -1) {
			result = HttpUtil.postRet(url, receiptData, UTF_8, UTF_8);
		} else {
			result = HttpUtil.postRet(url, "{\"receipt-data\":\"" + receiptData + "\"}", UTF_8, UTF_8);
		}

		if (result != null && !result.trim().equals("")) {
			map = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
			});
		}
		return map;
	}

}
