package com.living.pay.lenovo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
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
 * Lenovo SDK (未完成)
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月20日
 */
public class LenovoHandler extends ChannelHandler {
	
	private static final Logger logger = LoggerFactory.get(LenovoHandler.class);
	
	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.lenovo.auth.url");
	
	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.lenovo.app.id");
	
	static final String APP_KEY = "5HGcWdm6uJWo444Go8wC0Wc48";
	static final String APP_SECRET = "2c8A37deD903810B38c57Bd57600E425";
	static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmreYIkPwVovKR8rLHWlFVw7YDfm9uQOJKL89Smt6ypXGVdrAKKl0wNYc3/jecAoPi2ylChfa2iRu5gunJyNmpWZzlCNRIau55fxGW0XEu553IiprOZcaw5OuYGlf60ga8QT6qToP0/dpiL/ZbmNUO9kUhosIjEu22uFgR+5cYyQIDAQAB";
	private static final String RESULT_STR = "result=%s&resultMsg=%s";
	
	static final String UTF_8 = "UTF-8";
	
	private String _requestAuth(String token, String accessTokenSecret) throws UnsupportedEncodingException{
		String method = "POST";
		String oauthNonce = (int)(Math.random() * 10) + "";
		String oauthTimestamp = System.currentTimeMillis() + "";
		String oauthSignatureMethod = "HMAC-SHA1";
		String oauthVersion = "1.0";
		String params = String.format("oauth_consumer_key=%s&oauth_nonce=%s&oauth_signature_method=%s&oauth_timestamp=%s&oauth_token=%s&oauth_version=%s", 
				APP_KEY, oauthNonce, oauthSignatureMethod, oauthTimestamp, token, oauthVersion);
		
		String oauthSign = null;//MacUtil.hamcSha1(method + "&" + URLEncoder.encode(AUTH_URL, UTF_8) + "&" + params, APP_SECRET + "&" + accessTokenSecret);
		
		Map<String, String> headerMap = new HashMap<>();
		headerMap.put("Authorization", "OAuth " + params + "&oauth_signature=" + oauthSign);
		
		return HttpUtil.postRet(AUTH_URL, params + "&oauth_signature=" + oauthSign, UTF_8, UTF_8, headerMap);
	}
	
	
	
	@Override
    public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		
		String token = ctx.getAsString("token");
		String secret = ctx.getAsString("access_token_secret");
		
		if(token == null || token.trim().equals("")) {
			jsonResult.setMsg("token is null");
			logger.warn("token is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		try {
			String result = _requestAuth(token, secret);
			logger.info("auth result: " + result);
			
			if(result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {});
				
				if(resMap != null && !resMap.isEmpty()) {
					jsonResult = JsonResult.success();
					//jsonResult.setObj(uid);
					//account.setUserName(uid);
					//processLogin(ctx, account);
					return new StringPage(JSONUtil.toJson(jsonResult));
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
	    String notifyId = ctx.getAsString("notifyId");
	    String orderId = ctx.getAsString("partnerOrder");
	    String productName = ctx.getAsString("productName");
	    String productDesc = ctx.getAsString("productDesc");
	    Integer price = ctx.getAsInt("price");
	    Integer count = ctx.getAsInt("count");
	    String attach = ctx.getAsString("attach");
	    String sign = ctx.getAsString("sign");
	    
	    StringBuilder sb = new StringBuilder();
		sb.append("notifyId=").append(notifyId);
		sb.append("&partnerOrder=").append(orderId);
		sb.append("&productName=").append(productName);
		sb.append("&productDesc=").append(productDesc);
		sb.append("&price=").append(price);
		sb.append("&count=").append(count);
		sb.append("&attach=").append(attach);
	    
	    boolean flag = true;
	    flag = flag && sign != null && !sign.trim().equals("");
	    flag = flag && orderId != null && !orderId.trim().equals("");
	    flag = flag && price != null && price > 0;
	    flag = flag && count != null && count > 0;
	    
	    try {
	    	
	    	if(flag && _validateNotify(sb.toString(), sign, PUBLIC_KEY)) {
	    		
	    		PayOrder order = null;
	    		
	    		PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderId);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if(list == null || list.isEmpty()) {
					logger.info("notify can't find order id [" + orderId + "]");
					return new StringPage(String.format(RESULT_STR, "FAIL", URLEncoder.encode("验签失败", "UTF-8")));
				}
	    		
				order = list.get(0);
				if(order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(notifyId);
		    		order.setStatus(PayOrder.STATUS_成功通知渠道);
		    		
		    		// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				}
				return new StringPage(String.format(RESULT_STR, "OK", URLEncoder.encode("回调成功", "UTF-8")));
	    	}
	    	return new StringPage(String.format(RESULT_STR, "FAIL", URLEncoder.encode("验签失败", "UTF-8")));
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    return new StringPage("error");
    }
	
	private boolean _validateNotify(String content, String sign, String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] encodedKey = Base64.decodeBase64(publicKey);
		PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		java.security.Signature signature = java.security.Signature.getInstance("SHA1WithRSA");

		signature.initVerify(pubKey);
		signature.update(content.getBytes("UTF-8"));
		boolean bverify = signature.verify(Base64.decodeBase64(sign));
		return bverify;
	}
	
	public static void main(String[] args) {
	    System.out.println(PropertyUtils.SYS_CONFIG.get("sdk.lenovo.pay.key"));
    }
}
