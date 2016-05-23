package com.living.pay.huawei;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
 * 华为SDK (未完成)
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月19日
 */
public class HuaWeiHandler extends ChannelHandler {
	
	private static final Logger logger = LoggerFactory.get(HuaWeiHandler.class);
	
	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.huawei.auth.url");
	
	static final String PAY_KEY = PropertyUtils.SYS_CONFIG.get("sdk.huawei.public.key");
	
	static final Map<String, String> HEADERS = new HashMap<String, String>();
	static{
		HEADERS.put("Content-type", "application/x-www-form-urlencoded");
	}
	
	static final String UTF_8 = "UTF-8";
	
	private String _requestAuth(String token) throws UnsupportedEncodingException{
		return HttpUtil.postRet(AUTH_URL, "nsp_svc=OpenUP.User.getInfo&nsp_ts=" + (System.currentTimeMillis() / 1000) + "&access_token=" + URLEncoder.encode(token, UTF_8).replace("+", "%2B"), UTF_8, UTF_8, HEADERS);
		//return HttpUtil.postRet(AUTH_URL, "nsp_svc=OpenUP.User.getInfo&nsp_ts=" + (System.currentTimeMillis() / 1000) + "&access_token=" + token, UTF_8, UTF_8, HEADERS);
	}
	
	@Override
    public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();
		
		String token = ctx.getAsString("accesstoken");
		
		if(token == null || token.trim().equals("")) {
			jsonResult.setMsg("token is null");
			logger.warn("token is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		try {
			String result = _requestAuth(token);
			logger.info("auth result: " + result);
			
			if(result != null && !result.trim().equals("")) {
				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {});
				
				if(resMap != null && !resMap.isEmpty()) {
					
					String userId = String.valueOf(resMap.get("userID"));
					String userName = String.valueOf(resMap.get("userName"));
					
					if(userId != null && !userId.trim().equals("")) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(userId);
						account.setUserName(userId + "");
						processLogin(ctx, account);
						return new StringPage(JSONUtil.toJson(jsonResult));
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
		Map<String, Object> map = getValue(ctx.getRequest());
		if(map == null || map.isEmpty()) {
			logger.info("notify result is null");
			return new StringPage("fail");
		}
		
		// 支付结果： “0”：支付成功
	    String result = ctx.getAsString("result");
	    // 用户名
	    String userName = ctx.getAsString("userName");
	    // 平台订单号
	    String billno = ctx.getAsString("orderId");
	    
	    // 商品名称
	    String productName = ctx.getAsString("productName");
	    // 支付类型: 0：华为钱包， 1：充值卡 2：游戏卡 3：信用卡 4：支付宝 16：借记卡
	    String payType = ctx.getAsString("payType");
	    // 商品支付金额 (格式为：元.角分，最小精确到分， 例如：20.01)
	    String amount = ctx.getAsString("amount");
	    // 通知时间，自1970年1月1日0时起的毫秒数
	    String notifyTime = ctx.getAsString("notifyTime");
	    // 开发者支付请求ID
	    String orderNo = ctx.getAsString("requestId");
	    // 银行编码-支付通道信
	    String bankId = ctx.getAsString("bankId");
	    // 下单时间 yyyy-MM-dd hh:mm:ss；
	    String orderTime = ctx.getAsString("orderTime");
	    // 交易/退款时间 yyyy-MM-dd hh:mm:ss；
	    String tradeTime = ctx.getAsString("tradeTime");
	    // 接入方式： 0: 移动 1: PC-Web 2: Mobile-Web
	    String accessMode = ctx.getAsString("accessMode");
	    // 渠道开销，保留两位小数，单位元
	    String spending = ctx.getAsString("spending");
	    // 商户侧保留信息
	    String extReserved = ctx.getAsString("extReserved");
	    // 签名 
	    String sign = ctx.getAsString("sign");
	    
	    boolean flag = true;
	    flag = flag && orderNo != null && !orderNo.trim().equals("");
	    flag = flag && result != null && result.trim().equals("0");
	    flag = flag && sign != null && !sign.trim().equals("");
	    
	    try {
	    	
	    	if(flag && _validateNotify(map, sign)) {
	    		
	    		PayOrder order = null;
	    		
	    		PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderNo);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if(list == null || list.isEmpty()) {
					logger.info("notify can't find order id [" + orderNo + "]");
					return new StringPage("fail");
				}
	    		
				order = list.get(0);
				if (Double.parseDouble(amount) < order.getAmount()) {
					logger.warn("notify amount is error. order id [" + orderNo + "], amount=" + amount);
					return new StringPage("fail");
				}
				
				if(order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(billno);
		    		order.setStatus(PayOrder.STATUS_成功通知渠道);
		    		
		    		// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
	    		return new StringPage("{\"result\": 0}");
	    	} else {
	    		logger.info("notify param validate fail");
		    	return new StringPage("notify param validate fail");
	    	}
	    } catch(Exception e){
	    	e.printStackTrace();
	    }
	    return new StringPage("fail");
    }
	
	private boolean _validateNotify(Map<String, Object> params, String sign) {
		//获取待签名字符串
        String content = RSA.getSignData(params);
        //验签
        return RSA.doCheck(content, sign, PAY_KEY);
    }
	
	/**
     * @param request
     * @return
     *         本接口Content-Type是：application/x-www-form-urlencoded，对所有参数，会自动进行编码，接收端收到消息也会自动根据Content-Type进行解码。
     *         同时，接口中参数在发送端并没有进行单独的URLEncode (sign和extReserved、sysReserved参数除外)，所以，在接收端根据Content-Type解码后，即为原始的参数信息。
     *         但是HttpServletRequest的getParameter()方法会对指定参数执行隐含的URLDecoder.decode(),所以，相应参数中如果包含比如"%"，就会发生错误。
     *         因此，我们建议通过如下方法获取原始参数信息。
     * 
     *         注：使用如下方法必须在原始ServletRequest未被处理的情况下进行，否则无法获取到信息。比如，在Struts情况，由于struts层已经对参数进行若干处理，
     *         http中InputStream中其实已经没有信息，因此，本方法不适用。要获取原始信息，必须在原始的，未经处理的ServletRequest中进行。
     */
	public Map<String, Object> getValue(HttpServletRequest request) {

		String line = null;
		StringBuffer sb = new StringBuffer();
		try {
			request.setCharacterEncoding("UTF-8");

			InputStream stream = request.getInputStream();
			InputStreamReader isr = new InputStreamReader(stream);
			BufferedReader br = new BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\r\n");
			}
			System.out.println("The original data is : " + sb.toString());
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Throwable e) {
			e.printStackTrace();
		}

		String str = sb.toString();
		Map<String, Object> valueMap = new HashMap<>();
//		if (null == str || "".equals(str)) {
//			return valueMap;
//		}
		
		String[] valueKey = str.split("&");
		for (String temp : valueKey) {
			String[] single = temp.split("=");
			if (single != null && single.length == 2) {
				valueMap.put(single[0], single[1]);
			}
		}
		
		if (valueMap.isEmpty()) {
			/*String sign = request.getParameter("sign");
			String extReserved = request.getParameter("extReserved");
			String sysReserved = request.getParameter("sysReserved");
			if (sign != null) {
				valueMap.put("sign", sign);
			}
			if (extReserved != null) {
				valueMap.put("extReserved", extReserved);
			}
			if (sysReserved != null) {
				valueMap.put("sysReserved", sysReserved);
			}*/
			Enumeration<?> enumeration = request.getParameterNames();
			while (enumeration != null && enumeration.hasMoreElements()) {
				String key = (String) enumeration.nextElement();
				String value = request.getParameter(key);
				valueMap.put(key, value);
			}
			return valueMap;
		}
		
		// 接口中，如下参数sign和extReserved、sysReserved是URLEncode的，所以需要decode，其他参数直接是原始信息发送，不需要decode
		try {
			String sign = (String) valueMap.get("sign");
			String extReserved = (String) valueMap.get("extReserved");
			String sysReserved = (String) valueMap.get("sysReserved");

			if (null != sign) {
				sign = URLDecoder.decode(sign, "utf-8");
				valueMap.put("sign", sign);
			}
			if (null != extReserved) {
				extReserved = URLDecoder.decode(extReserved, "utf-8");
				valueMap.put("extReserved", extReserved);
			}

			if (null != sysReserved) {
				sysReserved = URLDecoder.decode(sysReserved, "utf-8");
				valueMap.put("sysReserved", sysReserved);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueMap;

	}

}
