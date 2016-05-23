package com.living.pay.anysdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.ks.model.account.Account;
import com.ks.model.filter.PayOrderFilter;
import com.ks.model.pay.PayOrder;
import com.ks.util.HttpUtil;
import com.ks.util.MD5Util;
import com.living.pay.ChannelHandler;
import com.living.util.PropertyUtils;
import com.living.web.core.WebContext;
import com.living.web.view.StringPage;
import com.living.web.view.ViewPage;

public class AnySdkHandler extends ChannelHandler {

	private Logger logger = LoggerFactory.get(AnySdkHandler.class);

	private static final String KY_AUTH_URL = PropertyUtils.SYS_CONFIG.get("sdk.anysdk.auth.url");

	private static final String PRIVATE_KEY = PropertyUtils.SYS_CONFIG.get("sdk.anysdk.private.key");

	private static final String STRENGH_KEY = PropertyUtils.SYS_CONFIG.get("sdk.anysdk.strengh.key");

	private static final String UTF8 = "UTF-8";

	private static final String userAgent = "px v1.0";

	private static final Map<String, String> HEADERS = new HashMap<String, String>();

	static {
		HEADERS.put("User-Agent", userAgent);
	}

	/**
	 * check needed parameters isset 检查必须的参数 channel
	 * uapi_key：渠道提供给应用的app_id或app_key（标识应用的id）
	 * uapi_secret：渠道提供给应用的app_key或app_secret（支付签名使用的密钥）
	 * 
	 * @param params
	 * @return boolean
	 */
	private boolean parametersIsset(Map<String, String[]> params) {
		return !(params.containsKey("channel") && params.containsKey("uapi_key") && params.containsKey("uapi_secret"));
	}

	@Override
	public ViewPage processOauth(WebContext ctx, Account account) {
		HttpServletRequest request = ctx.getRequest();
		Map<String, String[]> params = request.getParameterMap();
		if (parametersIsset(params)) {
			return new StringPage("param invalid");
		}

		String queryString = "";
		for (String key : params.keySet()) {
			String[] values = params.get(key);
			for (int i = 0; i < values.length; i++) {
				String value = values[i];
				queryString += key + "=" + value + "&";
			}
		}
		queryString = queryString.substring(0, queryString.length() - 1);

		String result = HttpUtil.postRet(KY_AUTH_URL, queryString, UTF8, UTF8, HEADERS);
		logger.info("anysdk auth result: " + result);
		return new StringPage(result);
	}

	@Override
	public ViewPage processNotify(WebContext ctx) {
		// AnySDK产生的订单号
		String billNo = ctx.getAsString("order_id");
		// 支付金额，单位元, 6位小数
		Double amount = ctx.getAsDouble("amount");
		// 支付状态，1为成功
		String payStatus = ctx.getAsString("pay_status");
		// 支付时间，YYYY-mm-dd HH:ii:ss格式
		Date payTime = ctx.getAsDate("pay_time");
		// 用户id
		String userId = ctx.getAsString("user_id");
		// 支付方式，详见支付渠道标识表
		String orderType = ctx.getAsString("order_type");
		// 游戏内用户id,支付时传入的Role_Id参数
		Integer playerId = ctx.getAsInt("game_user_id");
		// 服务器id，支付时传入的server_id 参数
		Integer serverId = ctx.getAsInt("server_id");
		// 商品名称，支付时传入的product_name 参数
		String productName = ctx.getAsString("product_name");
		// 商品id,支付时传入的product_id 参数
		Integer productId = ctx.getAsInt("product_id");
		// 自定义参数（订单号）
		String orderNo = ctx.getAsString("private_data");
		// 渠道编号
		String channelNumber = ctx.getAsString("channel_number");
		// 通用签名串
		String sign = ctx.getAsString("sign");
		// 渠道服务器通知 AnySDK 时请求的参数
		String source = ctx.getAsString("source");
		// 增强签名串，验签参考签名算法（有增强密钥的游戏有效）
		String enhancedSign = ctx.getAsString("enhanced_sign");

		List<String> keys = new ArrayList<String>();

		for (String str : ctx.getReqParamer().keySet()) {
			keys.add(str);
		}
		Collections.sort(keys);
		keys.remove("sign");
		StringBuffer buff = new StringBuffer();
		for (String key : keys) {
			buff.append(ctx.getAsString(key));
		}
		String local = MD5Util.decode(MD5Util.decode(buff.toString()).toLowerCase() + PRIVATE_KEY).toLowerCase();

		try {
			if (local.equals(sign)) {

				// 1成功
				if ("1".equals(payStatus)) {
					PayOrderFilter filter = new PayOrderFilter();
					filter.setOrderNo(orderNo);

					List<PayOrder> list = payAction().getPayOrders(filter);
					if (list == null || list.isEmpty()) {
						logger.info("anysdk notify can't find order id [" + orderNo + "]");
						return new StringPage("fail");
					}

					PayOrder order = list.get(0);
					if (order.getStatus() == PayOrder.STATUS_INIT) {
						order.setBillNo(billNo);
						order.setStatus(PayOrder.STATUS_成功通知渠道);

						// 修改订单状态，并发送游戏币
						payAction().gainOrderPayOk(order);

						logger.info("order [" + order.getOrderNo() + "] is success");
					} else {
						logger.warn("anysdk order id[ " + order.getOrderNo() + " ] status is [" + order.getStatus() + "]");
					}
					return new StringPage("ok");
				}
				logger.error("anysdk pay status [" + payStatus + "] error");
			} else {
				logger.error("sign invalide." + local);
			}
		} catch (Exception e) {
			logger.error("pay order failure." + e.getMessage());
		}
		return new StringPage("fail");
	}
}
