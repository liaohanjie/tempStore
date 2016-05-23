package com.living.pay;

import java.util.Map;

import com.ks.model.account.Account;
import com.ks.model.pay.PayOrder;
import com.living.web.core.WebContext;
import com.living.web.remote.HandlerAdapter;
import com.living.web.view.ViewPage;

public abstract class BaseHandler extends HandlerAdapter {
	
	/**
	 * 授权登陆
	 * @param ctx
	 * @return
	 */
	public abstract ViewPage oauth(WebContext ctx, Account account);
	
	/**
	 * 支付通知回调处理
	 * @param ctx
	 * @return
	 */
	public abstract ViewPage notify(WebContext ctx);
	
	
	/**
	 * 提交订单回调处理， 没有回调不需要重写
	 * @param ctx
	 * @param order
	 * @return
	 */
	abstract Map<String, Object> submitOrderCallback(WebContext ctx, PayOrder order);
	
	/**
	 * 登陆回调
	 * 
	 * @param ctx
	 * @param order
	 * @return
	 */
	public abstract ViewPage loginCallback(WebContext ctx, PayOrder order);
}
