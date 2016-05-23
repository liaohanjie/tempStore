package com.living.pay;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ks.action.account.PayConfigAction;
import com.ks.action.account.ServerInfoAction;
import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.account.Partner;
import com.ks.model.account.PayConfig;
import com.ks.model.account.ServerInfo;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.util.JSONUtil;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.remote.HandlerAdapter;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * 订单处理
 * 
 * @author zhoujf
 * @date 2015年6月16日
 */
public class OrderHandler extends HandlerAdapter {
	
	private static final Logger logger = LoggerFactory.get(OrderHandler.class);
	
	/**
	 * 渠道用户登陆授权验证
	 * @param ctx
	 * @return
	 */
	public ViewPage login(WebContext ctx) {
		JsonResult jsonResult = JsonResult.fail();
		Integer serverId = ctx.getAsInt("serverId");
		
		if(serverId == null || serverId < 0) {
			jsonResult.setMsg("serverId is invalid");
			//return new StringPage(JSONUtil.toJson(jsonResult));
			serverId = 1;
			logger.warn("serverId is invalid");
		}
		
		Integer payConfigId = ctx.getAsInt("payConfigId");
		if(payConfigId == null) {
			
			payConfigId = getPayConfigId(ctx.getRequest());
			if(payConfigId == null) {
				jsonResult.setMsg("payConfigId is null");
				return new StringPage(JSONUtil.toJson(jsonResult));
			}
		}
		
		ChannelHandler handler = ChannelHandlerManager.getHandler(payConfigId);
		if(handler == null) {
			jsonResult.setMsg("param is error");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		try{
			PayConfigAction payConfigAction = HandlerAdapter.getInfoAction(PayConfigAction.class);
			PayConfig payConfig = payConfigAction.queryById(payConfigId);
			if(payConfig == null) {
				jsonResult.setMsg("can't find pay config in account server");
				return new StringPage(JSONUtil.toJson(jsonResult));
			}
			
			Account account = new Account();
			account.setUserId(0);
			account.setGameId(payConfig.getGameId());
			account.setPartnerId(payConfig.getPartnerId());
			account.setLastLoginServerId(serverId);
			account.setLastLoginTime(new Date());
			account.setIp("");
			
			return handler.oauth(ctx, account);
		}catch(Exception e){
			jsonResult = JsonResult.error();
			e.printStackTrace();
		}
		return new StringPage(JSONUtil.toJson(jsonResult));
	}
	
	/**
	 * 生成订单号
	 * 
	 * @param ctx
	 * @return
	 */
	public ViewPage acquireOrderId(WebContext ctx){
		String result = null;
		
		Integer payConfigId = getPayConfigId(ctx.getRequest());
		
		if (payConfigId == null || payConfigId <= 0) {
			payConfigId = ctx.getAsInt("payConfigId");
		}
		if(payConfigId == null || payConfigId <= 0 || ChannelHandlerManager.getOrderPrefix(payConfigId) == null) {
			return new StringPage("{\"orderId\": \"\"}");
		}
		
		String orderId = OrderUtil.generateOrderId(ChannelHandlerManager.getOrderPrefix(payConfigId));
		if("txt".equals(ctx.getAsString("type"))) {
			result = orderId;
		} else {
			result = "{\"orderId\": \"" + orderId + "\"}";
		}
		
		return new StringPage(result);
	}
	
	@SuppressWarnings("unchecked")
    public static void traceHttpRequest(HttpServletRequest req){
		Map<String, String[]> map = req.getParameterMap();
		
		StringBuffer sb = new StringBuffer();
		sb.append(req.getMethod()).append(": ");
		sb.append(req.getRequestURL());
		sb.append("?");
		
		for(Map.Entry<String, String[]> entry : map.entrySet()) {
			String key = entry.getKey();
			
			for(String v : entry.getValue()) {
				sb.append(key);
				sb.append("=");
				sb.append(v);
				sb.append("&");
			}
		}
		sb.append("\nContextPath : ").append(req.getContextPath());
		sb.append("\nServletPath : ").append(req.getServletPath());
		sb.append("\nHost : ").append(req.getHeader("host"));
		System.out.println(sb.toString());
	}
	
	/**
	 * 订单提交
	 * 
	 * @param ctx
	 * @return
	 */
	public ViewPage submit(WebContext ctx){
		JsonResult jsonResult = JsonResult.fail();
		
		traceHttpRequest(ctx.getRequest());
		
		String orderId = ctx.getAsString("orderId");
		Integer serverId = ctx.getAsInt("serverId");
		Integer payConfigId = ctx.getAsInt("payConfigId");
		Integer partnerId = ctx.getAsInt("partnerId");
		Integer userId = ctx.getAsInt("userId");
		Integer amount = ctx.getAsInt("amount");
		Integer gameCoin = ctx.getAsInt("gameCoin");
		Integer giftCoin = ctx.getAsInt("giftCoin");
		//String parterUserName = nullToEmpty(ctx.getAsString("parterUserName"));
		String userName = nullToEmpty(ctx.getAsString("userName"));
		Integer goodsId = ctx.getAsInt("goodsId");
		String ext1 = nullToEmpty(ctx.getAsString("ext1"));
		String ext2 = nullToEmpty(ctx.getAsString("ext2"));
		String ext3 = nullToEmpty(ctx.getAsString("ext3"));
		
		goodsId = goodsId == null ? 0 : goodsId;
		orderId = (orderId == null || orderId.trim().equals("")) ? OrderUtil.generateOrderId(ChannelHandlerManager.getOrderPrefix(payConfigId)) : orderId;
		
		// 验证数据基本有效性
		boolean flag = true;
		flag = flag && orderId != null && !orderId.equals("") && orderId.length() >= 5 && orderId.length() <= 50;
		flag = flag && amount != null && amount > 0;
		flag = flag && userId != null && userId > 0;
		//flag = flag && serverId != null && !serverId.trim().equals("");
		flag = flag && serverId != null && serverId > 0;
		flag = flag && partnerId != null && partnerId > 0;
		flag = flag && payConfigId != null && payConfigId > 0;
		flag = flag && (gameCoin == null || (gameCoin != null && gameCoin >= 0));
		flag = flag && (giftCoin == null || (giftCoin != null && giftCoin >= 0));
		flag = flag && (goodsId == 0 || goodsId == 1 || goodsId == 2);
		flag = flag && true;
		
		if(!flag) {
			jsonResult.setMsg("param is invalid");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		// 处理月卡，月卡是没有魂钻和金币的
		if(goodsId == 1 || goodsId == 2) {
			gameCoin = 0;
			//giftCoin = 0;
		}
		
		try {
			
			ServerInfoAction siAction = serverInfoAction();
			ServerInfo serverInfo = siAction.getServerInfoById(serverId);
			if(serverInfo == null) {
				jsonResult.setMsg("serverId is invalid");
				return new StringPage(JSONUtil.toJson(jsonResult));
			}
			
			Partner partner = payAction().getPartnerById(partnerId);
			flag = flag && partner != null;
			
			if(!flag) {
				jsonResult.setMsg("param partnerId is invalid");
				return new StringPage(JSONUtil.toJson(jsonResult));
			}
			
			PayOrderFilter filter = new PayOrderFilter();
			filter.setOrderNo(orderId);
			List<PayOrder> list = payAction().getPayOrders(filter);
			if(list != null && !list.isEmpty()) {
				jsonResult.setMsg("order id is duplicate");
				return new StringPage(JSONUtil.toJson(jsonResult));
			}
			
			PayOrder order = new PayOrder();
			order.setOrderNo(orderId);
			order.setGameCoin(gameCoin);
			//order.setGameCoin(amount * 10);
			order.setAmount(amount);
			order.setGiftCoin(giftCoin);
			order.setUserId(userId);
			order.setUserName(userName);
			order.setServerId(serverId + "");
			order.setUserParnter(partnerId);
			order.setRetryTimes(0);
			order.setStatus(PayOrder.STATUS_INIT);
			//order.setGiftCoin(0);
			order.setCreateTime(new Date());
			order.setLastRetCode("");
			order.setPayConfigId(payConfigId);
			order.setGoodsId(goodsId);
			order.setBillNo("");
			order.setExt1(ext1);
			order.setExt2(ext2);
			order.setExt3(ext3);
			
			ChannelHandler handler = ChannelHandlerManager.getHandler(payConfigId);
			if (handler != null) {
				jsonResult.setObj(handler.submitOrderCallback(ctx, order));
			} else {
				logger.warn("payCofnigId can't find");
			}
			
			// 插入订单
			payAction().gainPayId(order);
			jsonResult.setCode(JsonResult.CODE_SUCCESS);
			jsonResult.setMsg(orderId);
		} catch(Exception e){
			e.printStackTrace();
			jsonResult = JsonResult.error();
		}
		
		return new StringPage(JSONUtil.toJson(jsonResult));
	}
	
	/**
	 * 支付渠道通知回调地址
	 * 
	 * @param ctx
	 * @return
	 */
	public ViewPage notify(WebContext ctx){
		Integer payConfigId = getPayConfigId(ctx.getRequest());
		ChannelHandler handler = ChannelHandlerManager.getHandler(payConfigId);
		
		traceHttpRequest(ctx.getRequest());
		
		if(handler == null) {
			return new StringPage("pay config id[" + payConfigId + "] is error, can't find notify handler");
		}
		
		return handler.notify(ctx);
	}
	
	public ViewPage _testNotifyPay(WebContext ctx){
		JsonResult jsonResult = JsonResult.fail();
		
		String orderId = ctx.getAsString("orderId");
		if(orderId == null || orderId.trim().equals("")) {
			jsonResult.setMsg("order id is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		PayOrder order = null;
		PayOrderFilter filter = new PayOrderFilter();
		filter.setOrderNo(orderId);
		List<PayOrder> list = payAction().getPayOrders(filter);
		
		order = list.get(0);
		if(order.getStatus() == PayOrder.STATUS_INIT) {
			order.setBillNo(orderId);
    		order.setExt1("test notify");
    		order.setStatus(PayOrder.STATUS_成功通知渠道);
    		
    		// 修改订单状态，并发送游戏币
			//payAction().gainOrderPayOk(order);
		}
		payAction().gainOrderPayOk(order);
		
		return new StringPage(JSONUtil.toJson(jsonResult));
	}
	
	/**
	 * 根据Servlet地址获取 payConfigId值<br>
	 * 
	 * 例如: /80001/OrderHandler/acquireOrderId.do , 获取到的值是 80001
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getPayConfigId(HttpServletRequest request){
		Integer payConfigId = null;
		String servletPath = request.getServletPath();
		
		if(servletPath == null || servletPath.trim().equals("")) {
        	return null;
        }
		
		servletPath = servletPath.startsWith("/") ? servletPath.substring(1) : servletPath;
		String[] values = servletPath.split("/");
    	if(values != null && values.length > 2) {
    		try{
    			payConfigId = Integer.parseInt(values[0]);
    		} catch(Exception e){}
    	}
		return payConfigId;
	}
	
	public static String nullToEmpty(String string){
		return string == null ? "" : string;
	}
}
