package com.living.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ks.action.account.AccountAction;
import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.pay.PayOrder;
import com.living.web.core.WebContext;
import com.living.web.remote.HandlerAdapter;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * 渠道平台处理器，授权登陆、订单通知回调处理
 * 
 * @author zhoujf
 * @date 2015年6月15日
 */
public abstract class ChannelHandler extends BaseHandler {
	
	private static final Logger logger = LoggerFactory.get(ChannelHandler.class);
	
	
	/**
	 * 支付平台具体的实现逻辑登陆
	 * <li> 请求渠道SDK服务器
	 * <li> 获取渠道SDK游戏登陆用户名
	 * 
	 * @return
	 */
	public abstract ViewPage processOauth(WebContext ctx, Account account);
	
	/**
	 * 支付平台具体的通知处理逻辑
	 * @return
	 */
	public abstract ViewPage processNotify(WebContext ctx);

	@Override
    public ViewPage oauth(WebContext ctx, Account account) {
		// 做一下统一操作， 转发给每个业务逻辑
		traceHttpRequest(ctx.getRequest());
	    return processOauth(ctx, account);
    }

	@Override
    public ViewPage notify(WebContext ctx) {
		traceHttpRequest(ctx.getRequest());
	    return processNotify(ctx);
    }
	
	/**
	 * 处理玩家登陆、注册的账号
	 * @param ctx
	 * @param account
	 */
	public void processLogin(WebContext ctx, Account account) {
		AccountAction accountAction = HandlerAdapter.getInfoAction(AccountAction.class);
		accountAction.login(account);
	}
	
	@Override
	public Map<String, Object> submitOrderCallback(WebContext ctx, PayOrder order) {
	    return null;
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
		logger.info(sb.toString());
	}
	
	@Override
	public ViewPage loginCallback(WebContext ctx, PayOrder order) {
	    return new StringPage("error");
	}
	
}
