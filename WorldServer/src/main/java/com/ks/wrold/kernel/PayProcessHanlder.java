/**
 * 
 */
package com.ks.wrold.kernel;

import java.util.List;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.DynamicChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;

import com.ks.action.logic.ActivityAction;
import com.ks.action.logic.GameAction;
import com.ks.action.logic.UserAction;
import com.ks.app.Application;
import com.ks.app.GameWorkExecutor;
import com.ks.logger.LoggerFactory;
import com.ks.model.pay.PayOrder;
import com.ks.rpc.RPCKernel;

/**
 * @author living.li
 * @date  2015年4月22日 下午1:58:37
 *
 *
 */
public class PayProcessHanlder extends SimpleChannelHandler  {
	
	private org.apache.log4j.Logger logger=LoggerFactory.get(PayProcessHanlder.class);
	
	/**
	 * 支付
	 */
	public static final String URI_PAY = "pay";
	
	/**
	 * 重读基础数据
	 */
	public static final String URI_RELOADDATA = "reloadData";
	
	/**
	 * 清空指定活动数据
	 */
	public static final String URI_CLEAN_ACTIVITY_DATA = "cleanActivityData";
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		 ctx.getChannel().close();
	}
	@Override
	public void messageReceived(ChannelHandlerContext ctx, final MessageEvent e)
			throws Exception {
		final HttpRequest request = (HttpRequest) e.getMessage();
		GameWorkExecutor.execute(new Runnable() {
			@Override
			public void run() {
				
				 final  String url=request.getUri();
				 //check
				 boolean ok=true;
				 if(url.indexOf("favicon.ico")!=-1){
					 ok=false;
						HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.NOT_FOUND);
				        ChannelBuffer buffer=new DynamicChannelBuffer(2048);
						response.setContent(buffer);
						response.setHeader("Content-Type", "text/html; charset=UTF-8");
						response.setHeader("Content-Length", response.getContent().writerIndex());
						Channel ch = e.getChannel();
						ch.write(response);
						return;
				 }
				 String method =getParam("method", url);
				 String ret=PayOrder.NOTIFI_CODE_SUCESS;
				 if(method==null){
					 ok=false;
					 ret=PayOrder.NOTIFI_CODE_FORBIDDEN;
				 }
				 if(ok){
					 if(method.contains(URI_PAY)){
						 ret=processPay(url);
					 } else if(method.equals(URI_RELOADDATA)){
						 ret=reloadData(url);
					 } else if(method.equals(URI_CLEAN_ACTIVITY_DATA)) {
						 ret=cleanActivityData(url);
					 }
				 }
			
				HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.OK);
		        ChannelBuffer buffer=new DynamicChannelBuffer(2048);
		        buffer.writeBytes(ret.getBytes());
				response.setContent(buffer);
				response.setHeader("Content-Type", "text/html; charset=UTF-8");
				response.setHeader("Content-Length", response.getContent().writerIndex());
				Channel ch = e.getChannel();
				ch.write(response);
			}
		});
		super.messageReceived(ctx, e);

	}
	
	/**
	 * 重新读取基础数据
	 * @param url
	 * @return
	 */
	private String reloadData(String url){
		
		try {
			GameAction gameAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER,GameAction.class);
			gameAction.reloadGameCache();
			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			return "fail";
		}
	}
	
	/**
	 * 清空指定活动数据
	 * @param url
	 * @return
	 */
	private String cleanActivityData(String url){
		String result;
		try {
			String defineId = getParam("defineId", url);
			ActivityAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ActivityAction.class);
			action.cleanActivityRecordData(Integer.parseInt(defineId));
			result = "success";
		} catch (Exception e) {
			e.printStackTrace();
			result = "fail";
		}
		return result + "," + System.currentTimeMillis();
	}
	
	/**
	 * 响应支付请求
	 * @param url
	 * @return
	 */
	private String processPay(String url){
		
		 boolean ok=true;
		 String method =getParam("method", url);
		 String ret=PayOrder.NOTIFI_CODE_SUCESS;
		 if(method==null){
			 ok=false;
			 ret=PayOrder.NOTIFI_CODE_FORBIDDEN;
		 }
		 String serverId=getParam("server_id", url);
		 String userName=getParam("user_name", url);
		 String partner=getParam("user_partner", url);
		 String gameCoin=getParam("game_coin", url);
		 String amount=getParam("amount", url);
		 String orderNo=getParam("order_no", url);
		 String userId=getParam("user_id", url);
		 String strGoodsId=getParam("goods_id", url);
		 String sign=getParam("sign", url);
		
		 Integer goodsId = getInt(strGoodsId);
		 goodsId = goodsId == null ? 0 : goodsId;
		 
		 logger.info("processPayUrl : " + url);
		 
		 if(serverId==null||partner==null||gameCoin==null||amount==null||orderNo==null||sign==null){
			 ok=false;
			 ret=PayOrder.NOTIFI_CODE_参数错误;
		 }
		 if(ok){
			 String localSign=PayOrder.getNofiSign(serverId, userId, userName, partner, gameCoin, amount, orderNo, goodsId + "");
					 
			 if(!localSign.equals(sign)){
				 ok=false;
				 ret=PayOrder.NOTIFI_CODE_签名验证失败;
			 }
		 }
		 if(ok){
			 try {
				   UserAction  action=RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
					action.pay(orderNo, Integer.valueOf(amount),Integer.valueOf(gameCoin), userName, Integer.valueOf(partner), goodsId);
					ret=PayOrder.NOTIFI_CODE_SUCESS;
				} catch (Exception e) {
					ret=PayOrder.NOTIFI_CODE_应用处理出现错误;
					logger.error("process order failure."+e.getMessage());
				}
		 }
		 return ret;
	}
	private String getParam(String key,String url){
		 QueryStringDecoder queryString = new QueryStringDecoder(url);
		 Map<String, List<String>> params = queryString.getParameters();
		 if(params==null){
			 return null;
		 }
		 if(params.get(key)==null||params.get(key).isEmpty()){
			 return null;
		 }
		 List<String> values=params.get(key);
		 String ret="";
		 for(String value:values){
			 ret=ret+value+",";
		 }
		 if(ret.endsWith(",")){
			 ret=ret.substring(0, ret.length()-1);
		 }
		 return ret;
	}
	
	private Integer getInt(String str){
		Integer v = null;
		try{
			v = Integer.parseInt(str);
		}catch(Exception e) {}
		return v;
	}
}
