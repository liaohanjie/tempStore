package  com.living.pay.baidu;

import java.net.URLDecoder;
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
import com.ks.util.MD5Util;
import com.living.pay.ChannelHandler;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * 百度SDK
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年10月19日
 */
public class BaiDuHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(BaiDuHandler.class);

	// 正式环境 SDK版本 >=3.6.0
	static final String AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.baidu.auth.url");

	static final String APP_ID = PropertyUtils.SYS_CONFIG.get("sdk.baidu.app.id");

	static final String KEY = PropertyUtils.SYS_CONFIG.get("sdk.baidu.key");

	static final String APP_SECRET = PropertyUtils.SYS_CONFIG.get("sdk.baidu.app.secret");

	static final String UTF_8 = "UTF-8";

	static final Map<String, Object> TYPE_REFERENCE = new HashMap<String, Object>();

	private String _requestAuth(String AccessToken) {
		// -- 请求参数
		// 1.AppID 应用ID
		// 2.AccessToken 客户端SDK返回的登录令牌
		// 3.Sign 签名 MD5(AppID+AccessToken+SecretKey)
		String sign = MD5Util.decode(APP_ID + AccessToken + APP_SECRET).toLowerCase();
		return HttpUtil.postRet(AUTH_URL, "AppID=" + APP_ID + "&AccessToken=" + AccessToken + "&Sign=" + sign, "UTF-8", "UTF-8");
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String accessToken = ctx.getAsString("AccessToken");
		if (accessToken == null || accessToken.trim().equals("")){
			logger.warn("baidu token is null");
			jsonResult.setMsg("baidu token is null");
			new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		try {
			String result = _requestAuth(accessToken);
			logger.info("--------------auth result: " + result);
		
			if (result != null && !result.trim().equals("")) {

				Map<String, Object> resMap = JSONUtil.toObject(result, new TypeReference<Map<String, Object>>() {});
				
				//--返回值
				Integer appId = (Integer) resMap.get("AppID"); //应用ID  int 
				Integer resultCode = (Integer) resMap.get("ResultCode"); // 结果编号 int 等于1成功， 不等于1为失败
				String resultMsg = (String) resMap.get("ResultMsg"); // 结果描述 string
				String resultSign = (String) resMap.get("Sign"); //返回值签名 string MD5(AppID+ResultCode+content+SeretKey)
				String content = (String) resMap.get("content"); // Json ， 编码格式 UTF-8 ， CP取到该参数后，应先UrlDecode 再Base64
				
				String contentDecode = URLDecoder.decode(content, UTF_8); 
				String contentBase64 = Base64.decodeBase64(contentDecode).toString();
				logger.info("--------------result contentBase64: " + contentBase64);
				String md5Sign = MD5Util.decode(APP_ID + resultCode + contentDecode + APP_SECRET);
				logger.info("--------------sign check value: " + md5Sign.toLowerCase().equals(resultSign.toLowerCase()));

				if (resultCode == 1 && md5Sign.toLowerCase().equals(resultSign.toLowerCase())) {
					Map<String, Object> contentJson = JSONUtil.toObject(contentBase64, new TypeReference<Map<String, Object>>() {});
					Long userId = (Long) contentJson.get("UID"); // 用户ID Long
					if (userId != null) {
						jsonResult = JsonResult.success();
						jsonResult.setObj(userId);
						account.setUserName(userId + "");
						processLogin(ctx, account);
						return new StringPage(JSONUtil.toJson(jsonResult));
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
		// 请求参数
		String appId = ctx.getAsString("AppID"); //应用ID
		String billNo = ctx.getAsString("OrderSerial"); //SDK系统内部订单号
		String orderNo = ctx.getAsString("CooperatorOrderSerial"); //CP订单号
		String sign = ctx.getAsString("Sign");
		String content = ctx.getAsString("content"); // Content通过Request读取的数据已经自动解码

		 boolean flag = true;
		 flag = flag && appId != null;
		 flag = flag && billNo != null && !billNo.trim().equals("");
		 flag = flag && orderNo != null && !orderNo.trim().equals("");
		 flag = flag && sign != null && !sign.trim().equals("");
		 flag = flag && content != null && !content.trim().equals("");

		if (flag) {
			logger.warn("params validate wrong.");
			return new StringPage("params validate wrong.");
		}

		String md5Sign = MD5Util.decode(appId + billNo + orderNo + content + APP_SECRET);
		if (!md5Sign.toLowerCase().equals(sign.toLowerCase())) {
			logger.warn("baidu sign validate wrong.");
			return new StringPage("baidu sign validate wrong.");
		}

		try {
			String contentBase64 = new String(Base64.decodeBase64(content));
        	Map<String, Object> resMap = JSONUtil.toObject(contentBase64, new TypeReference<Map<String, Object>>() {});
        	String orderStatus = resMap.get("OrderStatus") == null ? "" :  String.valueOf(resMap.get("OrderStatus"));//订单状态 （0：失败，1：成功）
        	String extInfo = resMap.get("ExtInfo") == null ? "" : resMap.get("ExtInfo").toString() ; // CP扩展信息
        	//Long  userId = (Long) contentJson.get("UID"); //用户id
        	//String merchandiseName = (String) contentJson.get("MerchandiseName"); //商品名称
        	//Double orderMondy = (Double) contentJson.get("OrderMondy"); // 订单金额
        	//Date startDateTime  = (Date) contentJson.get("StartDateTime"); // 创建时间
        	//Date bankDateTime = (Date) contentJson.get("BankDateTime");// 银行到账时间
        	//String statusMsg = (String) contentJson.get("StatusMsg"); //  订单状态描述
        	//Integer voucherMoney = (Integer) contentJson.get("VoucherMoney");//代金券金额

        	if ("1".equals(orderStatus)) {
        		
        		PayOrder order = null;
        		PayOrderFilter filter = new PayOrderFilter();
        		filter.setOrderNo(orderNo);
        		List<PayOrder> list = payAction().getPayOrders(filter);
        		if (list == null || list.isEmpty()) {
        			logger.info("notify can't find order id [" + orderNo + "]");
        			return new StringPage("fail");
        		}
        		order = list.get(0);
        		if (order.getStatus() == PayOrder.STATUS_INIT) {
        			order.setBillNo(billNo);
        			order.setStatus(PayOrder.STATUS_成功通知渠道);
        			logger.info("order success. [" + orderNo + "]");
        			
        			// 修改订单状态，并发送游戏币
        			payAction().gainOrderPayOk(order);
        		}
        		return notifySuccess();
            }
        } catch (Exception e) {
	        e.printStackTrace();
        }
		return new StringPage("fail");
	}
	
	public StringPage notifySuccess(){
		String resultCode = "1";
		return new StringPage("{\"AppID\":"+ APP_ID +", \"ResultCode\":" + resultCode + ",\"ResultMsg\":\"成功\",\"Sign\":\""+ MD5Util.decode(APP_ID + resultCode + APP_SECRET) +"\",\"Content\":\"\"}");
	}

}
