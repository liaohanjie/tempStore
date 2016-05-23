package com.living.pay.uc;

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
import com.living.util.IoUtil;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * UC SDK 
 * 
 * @author lipp 2015年10月26日
 */

public class UCHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(UCHandler.class);

	// 测试环境
	static final String TEST_URL = "http://sdk.test4.g.uc.cn/cp/account.verifySession";

	// 正式环境
	static final String AUTH_URL = "http://sdk.g.uc.cn/cp/account.verifySession";

	static final String UTF_8 = "UTF-8";

	static final boolean UC_DEBUG = PropertyUtils.SYS_CONFIG.getBool("sdk.uc.debug", false);
	
	static final String GAME_ID = PropertyUtils.SYS_CONFIG.get("sdk.uc.game.id");

	static final String API_KEY = PropertyUtils.SYS_CONFIG.get("sdk.uc.api.key");

	static final String CP_ID = PropertyUtils.SYS_CONFIG.get("sdk.uc.cp.id");

	static final Map<String, Object> TYPE_REFERENCE = new HashMap<String, Object>();

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		String sid = ctx.getAsString("sid");
		if (sid == null || sid.trim().equals("")) {
			jsonResult.setMsg("sid is null");
			logger.warn("sid is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		String id = System.currentTimeMillis() + "";
		String game = "{\"gameId\":" + GAME_ID + "}";
		String data = "{\"sid\": \"" + sid + "\"}";
		String sign = MD5Util.decode("sid=" + sid + API_KEY);
		String param = "{" + "\"id\":" + id + "," + "\"data\":" + data + "," + "\"game\":" + game + "," + "\"sign\":" + "\"" + sign + "\"" + "}";

		try {
			String url = AUTH_URL;
			if (UC_DEBUG) {
				url = TEST_URL;
			}
			
			String result = HttpUtil.postRet(url, param, UTF_8, UTF_8);
			logger.info("uc auth result: " + result);

			if (result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
				});

				if (resMap != null && !resMap.isEmpty()) {
					Map<String, Object> state = (Map<String, Object>) resMap.get("state");
					Integer code = (Integer) state.get("code");
					Map<String, Object> datas = (Map<String, Object>) resMap.get("data");
					String accountId = (String) datas.get("accountId");
					// state.code 1.成功 10请求参数错误 11用户未登录 99 服务器内部错误
					if (1 == code) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(accountId);
						account.setUserName(accountId);
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

//		try {
//			String jsonString = IoUtil.read(ctx.getRequest().getInputStream(), ctx.getRequest().getContentLength());
//			Map<String, Object> resMap = JSONUtil.toObject(jsonString, new TypeReference<Map<String, Object>>() {
//			});
//
//			// -- 支付结果数据 对应data，采用json格式 --
//			// 充值订单号
//			String orderId = String.valueOf(resMap.get("orderId"));
//			// 游戏编号
//			String gameId = GAME_ID;
//			// 帐号标识
//			String accountId = String.valueOf(resMap.get("accountId"));
//			// 帐号的创建者
//			String creator = String.valueOf(resMap.get("creator"));
//			// 支付通道代码 统一费率“999”
//			String payWay = String.valueOf(resMap.get("payWay"));
//			// 支付金额
//			String amount = String.valueOf(resMap.get("amount"));
//			// 游戏合作商自定义参数 长度不超过250
//			String callbackInfo = String.valueOf(resMap.get("callbackInfo"));
//			// 订单状态
//			String orderStatus = String.valueOf(resMap.get("orderStatus"));
//			// 订单失败原因
//			String failedDesc = String.valueOf(resMap.get("failedDesc"));
//			// Cp订单号
//			int cpOrderId = (int) resMap.get("cpOrderId");
//
//			// data排序
//			List<String> paramNameList = new ArrayList<>();
//			paramNameList.add("orderId");
//			paramNameList.add("gameId");
//			paramNameList.add("accountId");
//			paramNameList.add("creator");
//			paramNameList.add("payWay");
//			paramNameList.add("amount");
//			paramNameList.add("callbackInfo");
//			paramNameList.add("orderStatus");
//			paramNameList.add("failedDesc");
//			paramNameList.add("cpOrderId");
//			Collections.sort(paramNameList);
//
//			// data拼接
//			StringBuffer data = new StringBuffer();
//			for (String input : paramNameList) {
//				String value = (String) resMap.get(input);
//				if (value != null && !value.trim().equals("")) {
//					data.append(input).append(":").append(value).append(",");
//				}
//			}
//			data.deleteCharAt(data.length() - 1);
//
//			// MD5(签名内容+apikey) 签名内容为data所有子字段按字段名升序拼接（剔除&符号以及回车和换位符）
//			String sign = data + API_KEY;
//
//			boolean flag = true;
//			flag = flag && sign != null && !sign.trim().equals("");
//			flag = flag && gameId != null && !gameId.trim().equals("");
//			flag = flag && orderId != null && !orderId.trim().equals("");
//			flag = flag && accountId != null && !accountId.trim().equals("");
//			flag = flag && payWay != null && !payWay.trim().equals("");
//			flag = flag && creator != null && !creator.trim().equals("");
//			flag = flag && payWay != null && !payWay.trim().equals("");
//			flag = flag && amount != null && !amount.trim().equals("");
//			flag = flag && callbackInfo != null && !callbackInfo.trim().equals("");
//			flag = flag && orderStatus != null && !orderStatus.trim().equals("");
//			flag = flag && failedDesc != null && !failedDesc.trim().equals("");
//
//			if (flag && _validateNotify(sign, data.toString())) {
//				PayOrder order = null;
//				PayOrderFilter filter = new PayOrderFilter();
//				filter.setOrderNo(String.valueOf(cpOrderId));
//
//				List<PayOrder> list = payAction().getPayOrders(filter);
//				if (list == null || list.isEmpty()) {
//					logger.info("notify can't find order id [" + cpOrderId + "]");
//					return new StringPage("fail");
//				}
//				order = list.get(0);
//				if (order.getStatus() == PayOrder.STATUS_INIT) {
//					order.setBillNo(orderId);
//					order.setStatus(PayOrder.STATUS_成功通知渠道);
//					// 修改订单状态，并发送游戏币
//					payAction().gainOrderPayOk(order);
//				} else {
//					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
//				}
//				return new StringPage("SUCCESS");
//			} else {
//				logger.info("notify param validate fail");
//				return new StringPage("uc notify param validate fail");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return new StringPage("FAILURE");
	}

	private boolean _validateNotify(String sign, String signString) {
		String checkSign = MD5Util.decode(signString);
		return sign.equals(checkSign);
	}

}
