/**
 * 
 */
package com.living.web.hander;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ks.action.account.AccountAction;
import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.account.Partner;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.protocol.vo.login.LoginVO;
import com.ks.util.HttpUtil;
import com.ks.util.JSONUtil;
import com.ks.util.MD5Util;
import com.living.web.JsonResult;
import com.living.web.core.WebContext;
import com.living.web.model.OauthRet;
import com.living.web.model.OauthRet.Common;
import com.living.web.remote.HandlerAdapter;
import com.living.web.view.JsonPage;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

/**
 * @author living.li
 * @date  2015年5月25日 下午3:18:29
 *
 *
 */
public class UserHandler  extends HandlerAdapter {
	private Logger logger=LoggerFactory.get(UserHandler.class);

	//---------------------any sdk-------------------------------------------------------
	/**登录api*/
	private static final String NAY_SAK_USER_API="http://oauth.anysdk.com/api/User/LoginOauth/";
	/***/
	private static final String USER_AGENT = "px v1.0";
	/**private key*/
	private static final String ANY_PRIVATE_KEY="5C0C017B66AFB21A443DD81041C90F76";
	private static final Map<String,String> HEADER=new HashMap<String,String>();
	private static final List<String> PAY_PARAMS=new ArrayList<String>();
	//-------------------------------------------------------------------------------------------
	static{
		HEADER.put("User-Agent",USER_AGENT);
		
		PAY_PARAMS.add("amount");
		PAY_PARAMS.add("channel_number");
		PAY_PARAMS.add("enhanced_sign");
		PAY_PARAMS.add("game_user_id");
		PAY_PARAMS.add("order_id");
		PAY_PARAMS.add("order_type");
		PAY_PARAMS.add("pay_status");
		PAY_PARAMS.add("pay_time");
		PAY_PARAMS.add("private_data");
		PAY_PARAMS.add("product_count");
		PAY_PARAMS.add("product_id");
		PAY_PARAMS.add("product_name");
		PAY_PARAMS.add("server_id");
		PAY_PARAMS.add("user_id");
	}
	/**
	 * any sdk 登录回调
	 * @param ctx
	 * @return
	 */
	public ViewPage loginOauth(WebContext ctx) {
		String param=ctx.getQuery();
		String retStr= HttpUtil.postRet(NAY_SAK_USER_API, param, "utf-8","utf-8",HEADER);
		OauthRet ret=null;
		String result="";
		try {
			ret=JSONUtil.toObject(retStr,OauthRet.class);
		} catch (Exception e) {
			
			logger.error("nay sdk message error."+result);
			Map<String, String> map=new HashMap<String, String>();
			
			ctx.put("status","fail");
			map.put("error", "nay sdk message error");
			map.put("error_no", "20002"); 
			ctx.put("data",map);
			return  ctx.go(new JsonPage());
		}
		Common comm=null;
		if("ok".equals(ret.getStatus())){
			comm=ret.getCommon();
		}
		//没数据comm数据不要添加
		if(comm!=null){
			String sign=LoginVO.getOauthSign(comm.getUid(), comm.getChannel());
			ctx.put("common", comm);
			ctx.put("ext", sign);
		}
		ctx.put("status",ret.getStatus());
		ctx.put("data", ret.getData());
		return ctx.go(new JsonPage());
	}
	public ViewPage anyNotifiPay(WebContext ctx){
		List<String> keys=new ArrayList<String>();
		
		for(String str:ctx.getReqParamer().keySet()){
			keys.add(str);
		}
		Collections.sort(keys);
		keys.remove("sign");
		StringBuffer buff=new StringBuffer();
		for(String key:keys){
			buff.append(ctx.getAsString(key));
		}
		String local=MD5Util.decode(MD5Util.decode(buff.toString()).toLowerCase()+ANY_PRIVATE_KEY).toLowerCase();
		
		
		PayOrder order=new PayOrder();
		order.setAmount(ctx.getAsDouble("amount").intValue());
		order.setGameCoin(ctx.getAsDouble("amount").intValue()*10);
		order.setServerId(ctx.getAsString("server_id"));
		order.setOrderNo(ctx.getAsString("order_id"));
		order.setUserName(ctx.getAsString("user_id"));
		order.setUserParnter(ctx.getAsInt("channel_number"));
		order.setUserId(ctx.getAsInt("game_user_id"));
		order.setGiftCoin(ctx.getAsInt("private_data"));
		order.setCreateTime(Calendar.getInstance().getTime());
		
		String payStatus=ctx.getAsString("pay_status");
		String sign=ctx.getAsString("sign");
		//1成功
		String ret="fail"; //ok 表示成功收到通知
		if(local.equals(sign)){
			
			if("1".equals(payStatus)){
				try {
					order.setStatus(PayOrder.STATUS_成功通知渠道);
					payAction().gainOrderPayOk(order);
					ret="ok";
				} catch (Exception e) {
					logger.error("pay order failure."+e.getMessage());
				}
			}else{
				try {
					order.setStatus(PayOrder.STATUS_失败);
					order.setLastRetCode("P:"+payStatus);
					payAction().gainPayId(order);
					ret="ok";
				} catch (Exception e) {
					logger.error("pay order failure."+e.getMessage());
				}
			}			
		}else{
			logger.error("signr failure."+local);
		}
		return new StringPage(ret);
	}
	public ViewPage payResult(WebContext ctx){
		
		String orderNo=ctx.getAsString("order_no");
		//String serverId=ctx.getAsString("server_id");
		//String parntner=ctx.getAsString("partner");
		//String userId=ctx.getAsString("user_id");
		PayOrderFilter filter=new PayOrderFilter();
		filter.setOrderNo(orderNo);
		List<PayOrder> orders=payAction().getPayOrders(filter);
		PayOrder order=orders.isEmpty()?null:orders.get(0);
		int status=PayOrder.STATUS_订单不存在;
		int gameCoin = 0;
		int gameGiftCoin = 0;
		if(order!=null){
			status=order.getStatus();
			gameCoin=order.getGameCoin();
			gameGiftCoin = order.getGiftCoin();
		}
		//status
		ctx.put("status", status);
		ctx.put("game_coin", gameCoin);
		ctx.put("game_gift_coin", gameGiftCoin);
		return ctx.go(new JsonPage());
	}
	
	public ViewPage isPay(WebContext ctx){
		Integer partner=ctx.getAsInt("parnterId");
		Partner p=payAction().getPartnerById(partner);
		ctx.put("isPay",p==null?false:p.isPay());
		return new JsonPage();
	}
	
	public ViewPage lastLogin(WebContext ctx){
		JsonResult jsonResult = JsonResult.fail();
		
		Integer partnerId = ctx.getAsInt("partnerId");
		String userName = ctx.getAsString("userName");
		if(partnerId == null || partnerId < 0 || userName == null || userName.trim().equals("")) {
			jsonResult.setMsg("partnerId or userName is null");
			return new StringPage(JSONUtil.toJson(jsonResult));
		}
		
		AccountAction accountAction = getInfoAction(AccountAction.class);
		Account account = accountAction.queryByPartnerIdUserName(partnerId, userName);
		if(account != null) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("gameId", account.getGameId());
			data.put("partnerId", account.getPartnerId());
			data.put("userName", account.getUserName());
			data.put("lastLoginServerId", account.getLastLoginServerId());
			data.put("loginCount", account.getLoginCount());
			data.put("lastLoginTime", account.getLastLoginTime());
			jsonResult.setObj(data);
		}
		jsonResult.setCode(JsonResult.CODE_SUCCESS);
		jsonResult.setMsg(JsonResult.MSG_SUCCESS);
		return new StringPage(JSONUtil.toJson(jsonResult));
	}
}
