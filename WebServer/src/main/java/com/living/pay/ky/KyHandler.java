package com.living.pay.ky;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

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
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * 快用支付处理逻辑(未完成)
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月6日
 */
public class KyHandler extends ChannelHandler {
	
	private static final Logger logger = LoggerFactory.get(KyHandler.class);
	
	static final String AUTH_URL = "http://passport.xyzs.com/checkLogin.php";
	
	static final String APP_ID = "100021504";
	
	static final String APP_KEY = "8RGBDK8raBjeLu2P2ol5jAPs6NhLbGBW";
	static final String PAY_KEY = "N9HRq4KpPlAbUS0DEf1ODU5O3XZDgy8J";
	
	static final Map<String, String> HEADERS = new HashMap<String, String>();
	static{
		HEADERS.put("Content-type", "application/x-www-form-urlencoded");
	}
	
	static final String UTF_8 = "UTF-8";
	
	private String _requestAuth(String uid, String token){
		return HttpUtil.postRet(AUTH_URL, "uid=" + uid + "&appid=" + APP_ID + "&token=" + token, "UTF-8", "UTF-8", HEADERS);
	}
	
	@Override
    public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		
		String token = ctx.getAsString("token");
		String uid = ctx.getAsString("uid");
		
		if(token == null || token.trim().equals("") ||
				uid == null || uid.trim().equals("")) {
			jsonResult.setMsg("xy token or uid is null");
			logger.warn("xy token or uid is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		try {
			String result = _requestAuth(uid, token);
			logger.info("xy auth result: " + result);
			
			if(result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {});
				
				if(resMap != null && !resMap.isEmpty()) {
					// ret
					//0 成功，是登录状态
					//2 uid 不能为空
					//20 缺少 APPID
					//997 Token 过期
					//999 验证码校验失败
					Integer ret = Integer.parseInt(String.valueOf(resMap.get("ret")));
					String error = String.valueOf(resMap.get("error"));
					
					if(ret == 0) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(uid);
						account.setUserName(uid + "");
						processLogin(ctx, account);
						return new StringPage(JSONUtil.toJson(jsonResult));
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
		// xyzs 平台订单号
	    String billno = ctx.getAsString("orderid");
	    // xyzs 平台用户 ID
	    String uid = ctx.getAsString("uid");
	    // 服务器 ID， 不分服， 为 0。 也可以传其它值， 或不传
	    String serverid = ctx.getAsString("serverid");
	    // 厂商订单号,额外参数
	    String orderId = ctx.getAsString("extra");
	    // 兑换爱思币数量
	    Double amount = ctx.getAsDouble("amount");
	    // 时间戳
	    Long ts = ctx.getAsLong("ts");
	    // 签名 
	    String sign = ctx.getAsString("sign");
	    // 签名 2
	    String sig = ctx.getAsString("sig");
	    
	    boolean flag = true;
	    flag = flag && orderId != null && !orderId.trim().equals("") && orderId.length() >= 5;
	    flag = flag && billno != null && !billno.trim().equals("") && billno.length() >= 5;
	    flag = flag && uid != null && !uid.trim().equals("");
	    flag = flag && ts != null && ts > 0;
	    flag = flag && amount != null && amount > 0.0;
	    //flag = flag && uuid != null && !uuid.trim().equals("");
	    flag = flag && sign != null && !sign.trim().equals("");
	    
	    try {
	    	
	    	if(flag && _validateNotify(uid, billno, serverid, amount, orderId, ts, sign, sig)) {
	    		
	    		PayOrder order = null;
	    		
	    		PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderId);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if(list == null || list.isEmpty()) {
					logger.info("xy notify can't find order id [" + orderId + "]");
					return new StringPage("fail");
				}
	    		
				order = list.get(0);
				if(order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(billno);
		    		order.setStatus(PayOrder.STATUS_成功通知渠道);
		    		
		    		// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("pp order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
	    		return new StringPage("success");
	    	} else {
	    		logger.info("xy notify param validate fail");
		    	return new StringPage("xy notify param validate fail");
	    	}
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    return new StringPage("fail");
    }
	
	private boolean _validateNotify(String uid, String billno, String serverid, Double amount, String orderId, Long ts, String sign, String sig) {
		Map<String, Object> paramMap = new TreeMap<String, Object>();
	    paramMap.put("uid", uid);
	    paramMap.put("orderid", billno);
	    paramMap.put("serverid", serverid);
	    paramMap.put("amount", amount);
	    paramMap.put("extra", orderId);
	    paramMap.put("ts", ts);
	    
	    boolean flag = true;
	    flag = flag && sign.equals(getGenSafeSign(paramMap, APP_KEY));
	    flag = flag && sig != null && !sig.trim().equals("") && sig.equals(getGenSafeSign(paramMap, PAY_KEY));
	    return flag;
    }
	
	public static String getGenSafeSign(Map<String, Object> maps, String keys) {
		if (maps.isEmpty()) {
			return "";
		}

		String params = "";

		Iterator it = maps.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if ((pairs.getKey().equals("sign")) || (pairs.getKey().equals("sig"))) {
				it.remove();
			} else {
				if (params != "") {
					params = params + "&";
				}

				params = params + pairs.getKey() + "=" + pairs.getValue();
			}
		}
		//String sign = MD5.GetMD5Code(keys + params);
		return MD5Util.decode(keys + params);
	}

}
