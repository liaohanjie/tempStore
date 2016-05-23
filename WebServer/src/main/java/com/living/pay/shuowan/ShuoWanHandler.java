package com.living.pay.shuowan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.util.JSONUtil;
import com.ks.util.MD5Util;
import com.living.pay.ChannelHandler;
import com.living.util.PropertyUtils;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * 说玩
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年4月11日
 */
public class ShuoWanHandler extends ChannelHandler {

	private static final Logger logger = LoggerFactory.get(ShuoWanHandler.class);

	static final String GAME_ID = PropertyUtils.SYS_CONFIG.get("sdk.shuowan.game.id");

	static final String APP_KEY = PropertyUtils.SYS_CONFIG.get("sdk.shuowan.app.key");

	static final Map<String, String> HEADERS = new HashMap<String, String>();

	static final String UTF_8 = "UTF-8";

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		JsonResult jsonResult = JsonResult.fail();

		String username = ctx.getAsString("username");
		String loginTime = ctx.getAsString("logintime");
		String sign = ctx.getAsString("sign");
		
		if (username == null || username.trim().equals("") || sign == null || sign.equals("")) {
			jsonResult.setMsg("username is null or empty");
			logger.warn("username is null or empty");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}

		try {
			
			String signStr = "username=" + username + "&appkey=" + APP_KEY + "&logintime=" + loginTime;
			if (!MD5Util.decode(signStr).equals(sign)) {
				jsonResult.setMsg("sign is wrong");
				logger.warn("sign is wrong");
				return new StringPage(JSONUtil.toJson(jsonResult));
			}
			
			jsonResult = JsonResult.success();
			jsonResult.setObj(username);
			account.setUserName(username + "");
			processLogin(ctx, account);
			return new StringPage(JSONUtil.toJson(jsonResult));
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		// 平台订单号
		String billno = ctx.getAsString("orderid");
		// 登录账号
		String username = ctx.getAsString("username");
		// 游戏编号
		String gameId = ctx.getAsString("gameid");
		// 角色ID
		String roleId = ctx.getAsString("roleid");
		// 区服编号
		String serverId = ctx.getAsString("serverid");
		// 支付类型
		String payType = ctx.getAsString("paytype");
		// 成功充值金额，单位(元)
		String amount = ctx.getAsString("amount");
		// 玩家充值时间
		String payTime = ctx.getAsString("paytime");
		// 商户拓展参数
		String orderNo = ctx.getAsString("attach");
		// 签名
		String sign = ctx.getAsString("sign");

		boolean flag = true;
		flag = flag && orderNo != null && !orderNo.trim().equals("") && orderNo.length() >= 5;
		flag = flag && billno != null && !billno.trim().equals("");
		flag = flag && sign != null && !sign.trim().equals("");
		flag = flag && amount != null;

		try {

			if (flag && _validateNotify(billno, username, gameId, roleId, serverId, payType, amount, payTime, orderNo, sign)) {

				PayOrder order = null;

				PayOrderFilter filter = new PayOrderFilter();
				filter.setOrderNo(orderNo);
				List<PayOrder> list = payAction().getPayOrders(filter);
				if (list == null || list.isEmpty()) {
					logger.info("notify can't find order id [" + orderNo + "]");
					return new StringPage("fail");
				}
				
				order = list.get(0);
				
				if (order.getAmount() != Double.parseDouble(amount)) {
					logger.warn("notify amount is error. order id [" + orderNo + "], price=" + amount);
					return new StringPage("fail");
				}
				
				if (order.getStatus() == PayOrder.STATUS_INIT) {
					order.setBillNo(billno);
					order.setStatus(PayOrder.STATUS_成功通知渠道);

					// 修改订单状态，并发送游戏币
					payAction().gainOrderPayOk(order);
				} else {
					logger.warn("order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
				}
				return new StringPage("success");
			} else {
				logger.info("notify param validate fail");
				return new StringPage("notify param validate fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new StringPage("fail");
	}

	private boolean _validateNotify(String billno, String username, String gameId, String roleId, String serverId, String payType, String amount, String payTime, String orderNo, String sign) {
		String checkSign = MD5Util.decode("orderid=" + billno +  "&username=" + username + "&gameid=" + gameId + "&roleid=" + roleId + "&serverid=" + serverId + "&paytype=" + payType + "&amount=" + amount + "&paytime=" + payTime + "&attach=" + orderNo + "&appkey=" + APP_KEY);
		return sign.equals(checkSign);
	}
	
	
	public static void main(String[] args) {
		String username = "D34675715a41d9357c";
		String appkey = APP_KEY;
		String logintime = "1461120332";
		String sign = "c0bf32beb424bc348fd375baf1b48ecb";
	    String str = "username=" + username + "&appkey=" + appkey + "&logintime=" + logintime;
	    System.out.println(str);
	    System.out.println(MD5Util.decode(str));
	    System.out.println(sign);
    }
}
