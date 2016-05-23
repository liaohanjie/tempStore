package com.ks.account.test.service;

import org.junit.Test;

import com.ks.account.service.AdminService;
import com.ks.account.service.ServiceFactory;
import com.ks.account.test.BaseTestCase;
import com.ks.model.pay.PayOrder;
import com.ks.util.HttpUtil;

public class PayTestCase {
	
	@Test
	public void generateNotifyUrl(){
		
	}
	
	public static void main(String[] args) {
		String serverId = "24";
		String userId = "483679678";
		String userName = "196405502";
		String partner = "116";
		String gameCoin = "60";
		String amount = "6";
		String orderNo = "D0201510271653170008";
		String goodId = "0";
		
		String sign = PayOrder.getNofiSign(serverId, userId, userName,  partner + "",gameCoin + "", amount + "", orderNo, goodId + "");
		StringBuffer buff = new StringBuffer();
		buff.append("method=pay");
		buff.append("&server_id=" + serverId);
		buff.append("&user_id=" + userId);
		buff.append("&user_name=" + userName);
		buff.append("&user_partner=" + partner);
		buff.append("&game_coin=" + gameCoin);
		buff.append("&amount=" + amount);
		buff.append("&order_no=" + orderNo);
		buff.append("&goods_id=" + goodId);
		buff.append("&sign=" + sign);
		
		String url = "http://182.254.147.65:3001/?method=pay";
		url = "http://127.0.0.1:3001/?method=pay";
		String bak = HttpUtil.getRet(url, buff.toString(), null, null);
		
		System.out.println(bak);
		System.out.println(bak);
    }
}
