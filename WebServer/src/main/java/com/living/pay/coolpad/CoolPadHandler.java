package com.living.pay.coolpad;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.TypeReference;

import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.util.JSONUtil;
import com.living.pay.ChannelHandler;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * CoolPad SDK (未完成)
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月19日
 */
public class CoolPadHandler extends ChannelHandler {
	
	private static final Logger logger = LoggerFactory.get(CoolPadHandler.class);
	
	static final String AUTH_URL = "http://thapi.nearme.com.cn/account/GetUserInfoByGame";
	
	static final String APP_ID = "5000002197";
	static final String APP_KEY = "N0M5QjNDNEQ3NjczRTU3MTExQTVGQ0JFOEY2RDI2NDRBREU2QjVFNE1URXlOREV3T1RreU9ESXdNakV6TXpFeE1qY3JNVFl3TURrNU1ESXlOelV5TVRVME5EZ3lORFE0TXpnNE5UazRNemt3TWpJM01EZzJOak01";
	
	static final String UTF_8 = "UTF-8";
	
	@Override
    public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		
		String token = ctx.getAsString("token");
		String secret = ctx.getAsString("access_token_secret");
		
		try {
			
			jsonResult = JsonResult.success();
			//jsonResult.setObj(uid);
			//account.setUserName(uid);
			//processLogin(ctx, account);
			return new StringPage(JSONUtil.toJson(jsonResult));
		} catch(Exception e) {
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
    }
	

	@Override
    public ViewPage processNotify(WebContext ctx) {
	    try {
	    	
	    	InputStream in = ctx.getRequest().getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in, UTF_8));
			String line = null;
			StringBuilder tranData = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				tranData.append(line);
			}
			line = tranData.toString();
			logger.info("info: 支付结果通知内容["+line+"]");
		    
		    boolean flag = true;
		    flag = flag && line != null && !line.trim().equals("");
	    	
	    	int index = line.indexOf('&');
	    	if (0 > index) {
	    		logger.warn("notify data is wrong");
	    	} else {
	    		String transdata = line.substring(10, index);
				String sign = line.substring(index + 6);
				
				logger.info("info:支付结果通知内容transdata["+transdata+"]");
				logger.info("info:支付结果通知签名sign["+sign+"]");
				
				flag = flag && transdata != null && !transdata.trim().equals("");
				flag = flag && sign != null && !sign.trim().equals("");
				
				if (flag && CpTransSyncSignValid.validSign(transdata, sign, APP_KEY)) {
					//"{\"exorderno\":\"iVk4eRZknftx4vAJm5VE\",\"transid\":\"02115061814204200016\",\"waresid\":1,\"appid\":\"3000962200\",\"feetype\":0,\"money\":1,\"count\":1,\"result\":0,\"transtype\":0,\"transtime\":\"2015-06-18 14:20:59\",\"cpprivate\":\"cp private info!!\",\"paytype\":401}
					
					Map<String, Object> resMap = JSONUtil.toObject(transdata, new TypeReference<Map<String, Object>>() {});
					String orderId = null;
					String billNo = null;
					if (resMap != null && !resMap.isEmpty()) {
						orderId = String.valueOf(resMap.get("exorderno"));
						billNo = String.valueOf(resMap.get("transid"));
					}
					
					if (orderId == null || orderId.trim().equals("") || billNo == null || billNo.trim().equals("") ) {
						logger.warn("params is null");
						return new StringPage("FAILURE");
					}
					
					PayOrder order = null;
		    		
		    		PayOrderFilter filter = new PayOrderFilter();
					filter.setOrderNo(orderId);
					List<PayOrder> list = payAction().getPayOrders(filter);
					if(list == null || list.isEmpty()) {
						logger.warn("notify can't find order id [" + orderId + "]");
						return new StringPage("FAILURE");
					}
		    		
					order = list.get(0);
					if(order.getStatus() == PayOrder.STATUS_INIT) {
						order.setBillNo(billNo);
			    		order.setStatus(PayOrder.STATUS_成功通知渠道);
			    		
			    		// 修改订单状态，并发送游戏币
						payAction().gainOrderPayOk(order);
					}
					return new StringPage("SUCCESS");
				} else {
					logger.warn("validate notify is error");
				}
	    	}
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    return new StringPage("FAILURE");
    }
}
