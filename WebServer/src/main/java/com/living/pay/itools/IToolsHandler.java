package com.living.pay.itools;

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
 * Itools支付处理
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月4日
 */
public class IToolsHandler extends ChannelHandler {
	
	private static final Logger logger = LoggerFactory.get(IToolsHandler.class);
	
	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.itools.auth.url");
	
	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.itools.app.id");
	
	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.itools.app.key");
	
	static TypeReference<Map<String, String>> TYPE_REFERENCE = new TypeReference<Map<String, String>>() {};
	
	private String _requestAuth(String sessionid){
		String sign = MD5Util.decode("appid=" + APP_ID +  "&sessionid=" + sessionid);
		String url = AUTH_URL + "&appid=" + APP_ID + "&sessionid=" + sessionid + "&sign=" + sign;
		return HttpUtil.postRet(url, "", "UTF-8", "UTF-8");
	}
	
	@Override
    public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		
		String sessionId = ctx.getAsString("sessionId");
		
		if(sessionId == null || sessionId.trim().equals("") || sessionId.split("_").length != 2) {
			logger.error("sessionId is invalid [" + sessionId + "]");
			jsonResult.setMsg("itools sessionid is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		String[] sessionArgs = sessionId.split("_");
		String uid = sessionArgs[0];
		String sid = sessionArgs[1];
		
		try {
			String result = _requestAuth(sessionId);
			logger.info("itools auth result: " + result);
			
			if(result != null && !result.trim().equals("")) {
				Map<String, String> resMap = JSONUtil.toObject(result, TYPE_REFERENCE);
				
				if(resMap != null && !resMap.isEmpty()) {
					String status = resMap.get("status");
					
					if("success".equals(status)) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(uid);
						
						account.setUserName(uid);
						processLogin(ctx, account);
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
	    String notifyData = ctx.getAsString("notify_data");
	    String sign = ctx.getAsString("sign");
	    
	    boolean flag = true;
	    flag = flag && notifyData != null && !notifyData.equals("");
	    flag = flag && sign != null && !sign.equals("");
	    
	    try {
	    	String resultData = _validateNotify(notifyData, sign);
	    	if(flag && resultData != null && !resultData.equals("")) {
	    		
	    		Map<String, String> map = JSONUtil.toObject(resultData, TYPE_REFERENCE);
	    		String orderId = map.get("order_id_com");
	    		int userId = Integer.parseInt(map.get("user_id"));
	    		double amount = Double.parseDouble(map.get("amount"));
	    		String account = map.get("account");
	    		String billno = map.get("order_id");
	    		String result = map.get("result");
	    		
	    		if("success".equals(result)) {
	    			PayOrder order = null;
		    		
		    		PayOrderFilter filter = new PayOrderFilter();
					filter.setOrderNo(orderId);
					List<PayOrder> list = payAction().getPayOrders(filter);
					if(list == null || list.isEmpty()) {
						logger.info("itools notify can't find order id [" + billno + "]");
						return new StringPage("fail");
					}
		    		
					order = list.get(0);
					if(order.getStatus() == PayOrder.STATUS_INIT) {
						order.setBillNo(billno);
			    		order.setStatus(PayOrder.STATUS_成功通知渠道);
			    		
			    		// 修改订单状态，并发送游戏币
						payAction().gainOrderPayOk(order);
					} else {
						logger.warn("itools order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
					}
		    		
		    		return new StringPage("success");
	    		}
	    	} else {
	    		logger.info("itools notify param validate fail");
		    	return new StringPage("itools notify param validate fail");
	    	}
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    return new StringPage("fail");
    }
	
	private String _validateNotify(String notifyData, String sign){
		String notifyResult = null;
        try {
	        notifyResult = RSASignature.decrypt(notifyData);
	        // {"order_id_com":"2013050900000712", ”user_id”:”10101” , ” amount”:”0.10” , ” account”:”test001” , ” order_id”:”2013050900000713” , ”result”:” success”}
			logger.info("itools decrypt data: " + notifyResult);
        } catch (Exception e) {
	        e.printStackTrace();
        }
		
	    return notifyResult;
	}

}
