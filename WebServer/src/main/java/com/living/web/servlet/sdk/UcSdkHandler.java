package com.living.web.servlet.sdk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import com.ks.logger.LoggerFactory;
import com.ks.util.JSONUtil;
import com.ks.util.MD5Util;
import com.living.util.IoUtil;
import com.living.util.PropertyUtils;


/**
 * UC SDK 处理器
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月17日
 */
public class UcSdkHandler extends SdkHandler {

	private Logger logger = LoggerFactory.get(UcSdkHandler.class);
	
	// 测试环境
	static final String TEST_URL = "http://sdk.test4.g.uc.cn/cp/account.verifySession";

	// 正式环境
	static final String AUTH_URL = "http://sdk.g.uc.cn/cp/account.verifySession";

	static final String UTF_8 = "UTF-8";

	static final String GAME_ID = PropertyUtils.SYS_CONFIG.get("sdk.uc.game.id");

	static final String API_KEY = PropertyUtils.SYS_CONFIG.get("sdk.uc.api.key");

	static final String CP_ID = PropertyUtils.SYS_CONFIG.get("sdk.uc.cp.id");

	static final Map<String, Object> TYPE_REFERENCE = new HashMap<String, Object>();

	@Override
    public void doLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//		JsonResult jsonResult = JsonResult.fail();
//		String sid = ctx.getAsString("sid");
//		if (sid == null || sid.trim().equals("")) {
//			jsonResult.setMsg("sid is null");
//			logger.warn("sid is null");
//			return new StringPage(JSONUtil.toJson(jsonResult));
//		}
//
//		String id = System.currentTimeMillis() + "";
//		String game = "{\"gameId\":" + GAME_ID + "}";
//		String data = "{\"sid\": \"" + sid + "\"}";
//		String sign = MD5Util.decode("sid=" + sid + API_KEY);
//		String param = "{" + "\"id\":" + id + "," + "\"data\":" + data + "," + "\"game\":" + game + "," + "\"sign\":" + "\"" + sign + "\"" + "}";
//
//		try {
//			String result = HttpUtil.postRet(AUTH_URL, param, UTF_8, UTF_8);
//			logger.info("uc auth result: " + result);
//
//			if (result != null && !result.trim().equals("")) {
//				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {
//				});
//
//				if (resMap != null && !resMap.isEmpty()) {
//					Map<String, Object> state = (Map<String, Object>) resMap.get("state");
//					Integer code = (Integer) state.get("code");
//					Map<String, Object> datas = (Map<String, Object>) resMap.get("data");
//					String accountId = (String) datas.get("accountId");
//					// state.code 1.成功 10请求参数错误 11用户未登录 99 服务器内部错误
//					if (1 == code) {
//						jsonResult = JsonResult.success();
//						jsonResult.setObj(accountId);
//						account.setUserName(accountId);
//						processLogin(ctx, account);
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			jsonResult = JsonResult.error();
//		}
//		return new StringPage(JSONUtil.toJson(jsonResult));
    }

	@Override
    public void doNotify(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		try {
			if (req.getContentLength() <= 0) {
				_write(resp, "uc input is null");
				return;
			}
			
			String jsonString = IoUtil.read(req.getInputStream(), req.getContentLength());
			if (jsonString == null || jsonString.trim().equals("")) {
				_write(resp, "uc input is null");
				return;
			}
			
			logger.info("json: " + jsonString);
			
			Map<String, Object> resMap = JSONUtil.toObject(jsonString, new TypeReference<Map<String, Object>>() {
			});
			
			if (resMap != null) {
				String sign = (String) resMap.get("sign");
				Map<String, Object> data = (Map<String, Object>) resMap.get("data");
	             
				// -- 支付结果数据 对应data，采用json格式 --
				// 充值订单号, 游戏合作商自定义参数 长度不超过250
				String orderNo = String.valueOf(data.get("callbackInfo"));
				// 渠道订单号
				String billNo = String.valueOf(data.get("orderId"));
				// 游戏编号
				String gameId = String.valueOf(data.get("gameId"));
				// 帐号标识
				String accountId = String.valueOf(data.get("accountId"));
				// 帐号的创建者
				String creator = String.valueOf(data.get("creator"));
				// 支付通道代码 统一费率“999”
				String payWay = String.valueOf(data.get("payWay"));
				// 支付金额
				//String amount = String.valueOf(String.format("%.2f", data.get("amount")));
				double amount = Double.parseDouble(String.valueOf(data.get("amount")));
				// 游戏合作商自定义参数 长度不超过250
				String callbackInfo = String.valueOf(data.get("callbackInfo"));
				// 订单状态
				String orderStatus = String.valueOf(data.get("orderStatus"));
				// 订单失败原因
				String failedDesc = String.valueOf(data.get("failedDesc"));
				// Cp订单号
				String cpOrderId = String.valueOf(data.get("cpOrderId"));
				
				StringBuilder checkDataString = new StringBuilder();
				ArrayList<String> localArrayList = new ArrayList<String>(data.keySet());
			    Collections.sort(localArrayList);
			    for (int i = 0; i < localArrayList.size(); i++) {
			      String key = localArrayList.get(i);
			      String value = data.get(key) == null ? "" : data.get(key).toString();
			      checkDataString.append(key).append("=").append(value);
			    }
			    checkDataString.append(API_KEY);
			    //checkDataString.append("202cb962234w4ers2aaa");

				boolean flag = true;
				flag = flag && sign != null && !sign.trim().equals("");
				flag = flag && gameId != null && !gameId.trim().equals("");
				flag = flag && orderNo != null && !orderNo.trim().equals("");
				flag = flag && billNo != null && !billNo.trim().equals("");
				flag = flag && accountId != null && !accountId.trim().equals("");
//				flag = flag && payWay != null && !payWay.trim().equals("");
//				flag = flag && creator != null && !creator.trim().equals("");
//				flag = flag && payWay != null && !payWay.trim().equals("");
//				flag = flag && amount != null && !amount.trim().equals("");
//				flag = flag && callbackInfo != null && !callbackInfo.trim().equals("");
				flag = flag && orderStatus != null && orderStatus.trim().equals("S");
//				flag = flag && failedDesc != null && !failedDesc.trim().equals("");

				if (flag && _validateNotify(sign, checkDataString.toString())) {
					if (recharge(orderNo, billNo, amount)) {
						_notifySuccess(resp);
						return;
					} else {
						logger.warn("recharge fail");
						_write(resp, "uc recharge fail, orderId=" + orderNo);
						return;
					}
				} else {
					logger.warn("notify param validate fail, orderId=" + orderNo);
					_write(resp, "uc notify param validate fail, orderId=" + orderNo);
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		_notifyFail(resp);
    }
	
	private boolean _validateNotify(String sign, String signString) {
		String checkSign = MD5Util.decode(signString);
		return sign.equals(checkSign);
	}
	
	private void _notifySuccess(HttpServletResponse resp) throws IOException{
		_write(resp ,"SUCCESS");
	}
	
	private void _notifyFail(HttpServletResponse resp) throws IOException{
		_write(resp ,"FAILURE");
	}
	
	private void _write(HttpServletResponse resp, String message) throws IOException {
		resp.getWriter().write(message);
	}
}
