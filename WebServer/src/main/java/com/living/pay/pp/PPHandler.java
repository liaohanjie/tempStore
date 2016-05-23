package com.living.pay.pp;

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
 * PP支付处理
 * 
 * @author zhoujf
 * @date 2015年6月15日
 */
public class PPHandler extends ChannelHandler {
	
	private static final Logger logger = LoggerFactory.get(PPHandler.class);
	
	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.pp.auth.url");
	
	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.pp.app.id");
	
	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.pp.app.key");
	
	private String _requestAuth(String sid){
		String sign = "sid=" + sid + APP_KEY;
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("sid", sid);
		Map<String, Object> game = new HashMap<String, Object>();
		game.put("gameId", APP_ID);
		
		Map<String, Object> dataRootMap = new HashMap<String, Object>();
		dataRootMap.put("id", System.currentTimeMillis());
		dataRootMap.put("service", "account.verifySession");
		dataRootMap.put("game", game);
		dataRootMap.put("data", data);
		dataRootMap.put("encrypt", "md5");
		dataRootMap.put("sign", MD5Util.decode(sign));
		
		String json = JSONUtil.toJson(dataRootMap);
		logger.info(json);
		return HttpUtil.postRet(AUTH_URL, json, "UTF-8", "UTF-8");
	}
	
	@Override
    public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		
		String sid = ctx.getAsString("sid");
		if(sid == null || sid.trim().equals("")) {
			jsonResult.setMsg("pp sid is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		try {
			String result = _requestAuth(sid);
			logger.info("PP auth result: " + result);
			
			if(result != null && !result.trim().equals("")) {
				TypeReference<Map<String, Object>> trf = new TypeReference<Map<String, Object>>() {};
				Map<String, Object> resMap = JSONUtil.toObject(result, trf);
				
				if(resMap != null && !resMap.isEmpty()) {
					long id = Long.parseLong(String.valueOf(resMap.get("id")));
					Map<String, Object> state = (Map<String, Object>) resMap.get("state");
					int code = Integer.parseInt(state.get("code").toString());
					String msg = (String) state.get("msg");
					
					if(State.SUCCESS.getCode() == code) {
						Map<String, Object> data = (Map<String, Object>) resMap.get("data");					
						String accountId = (String) data.get("accountId");
						String creator = (String) data.get("creator");
						String nickName = (String) data.get("nickName");
						
						jsonResult = JsonResult.success();
						jsonResult.setObj(data);
						
						account.setUserName(accountId);
						processLogin(ctx, account);
						
						return new StringPage(JSONUtil.toJson(jsonResult));
					} else if(State.PARAM_ERROR.getCode() == code){
						jsonResult.setMsg(State.PARAM_ERROR.getMsg());
					} else if(State.NO_LOGIN.getCode() == code){
						jsonResult.setMsg(State.NO_LOGIN.getMsg());
					} else if(State.NETWORK_BUSY.getCode() == code){
						jsonResult.setMsg(State.NETWORK_BUSY.getMsg());
					} else {
						jsonResult.setMsg("fail");
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
    }
	

	@Override
    public ViewPage processNotify(WebContext ctx) {
		// 兑换订单号
	    String orderId = ctx.getAsString("order_id");
	    // 厂商订单号
	    String billno = ctx.getAsString("billno");
	    // 通行账号
	    String account = ctx.getAsString("account");
	    // PP 币数量
	    Double amount = ctx.getAsDouble("amount");
	    // 状态 : 0 正常 ， 1 已兑换过并成功返回
	    Integer status = ctx.getAsInt("status");
	    // 厂商 app_id
	    String appId = ctx.getAsString("app_id");
	    // 设备号
	    String uuid = ctx.getAsString("uuid");
	    // roleid 角色id
	    Integer roleId = ctx.getAsInt("roleid");
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
	    //flag = flag && uuid != null && !uuid.trim().equals("");
	    flag = flag && roleId != null && roleId > 0;
	    flag = flag && zone != null;
	    flag = flag && sign != null && !sign.trim().equals("");
	    flag = flag && status != null;
	    
	    try {
	    	if(flag && _validateNotify(orderId, billno, amount, roleId, sign)) {
	    		
	    		PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(billno);
				
				List<PayOrder> list = payAction().getPayOrders(filter);
				if(list == null || list.isEmpty()) {
					logger.info("pp notify can't find order id [" + billno + "]");
					return new StringPage("fail");
				}
	    		
				PayOrder order = list.get(0);
				if(order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(orderId);
		    		order.setExt1(uuid);
		    		order.setStatus(PayOrder.STATUS_成功通知渠道);
		    		
		    		// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
					
					logger.info("order [" + order.getOrderNo() +  "] is success");
				} else {
					logger.warn("pp order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
	    		return new StringPage("success");
	    	} else {
	    		logger.info("pp notify param validate fail");
		    	return new StringPage("pp notify param validate fail");
	    	}
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    return new StringPage("fail");
    }
	
	private boolean _validateNotify(String orderId, String billNo, double amount, int roleId, String sign){
		String notifyResult = RSAEncrypt.decode(sign);
		
		logger.info("pp decode sign: " + notifyResult);
		
	    if(notifyResult != null && !notifyResult.trim().equals("")) {
	    	Map<String, Object> map = JSONUtil.toObject(notifyResult, Map.class);
	    	
	    	boolean flag = true;
	    	flag = flag && map.get("order_id") != null && map.get("order_id").equals(orderId);
	    	flag = flag && map.get("billno") != null && map.get("billno").equals(billNo);
	    	flag = flag && map.get("roleid") != null && map.get("roleid").equals(String.valueOf(roleId));
	    	flag = flag && map.get("amount") != null && map.get("amount").equals(String.format("%.02f", amount));
	    	return flag;
	    }
	    return false;
	}
	
	
	public enum State {
		
		SUCCESS(1, "success"), PARAM_ERROR(10, "param error"), NO_LOGIN(11, "no login"), NETWORK_BUSY(99, "network busy");

		private int code;
		private String msg;
		
		private State(int code,  String msg){
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
