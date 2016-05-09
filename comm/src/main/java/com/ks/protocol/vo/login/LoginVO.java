package com.ks.protocol.vo.login;

import com.ks.protocol.Message;
import com.ks.util.MD5Util;
/**
 * 登录消息
 * @author ks
 *
 */
public class LoginVO extends Message {
	
	private static final String LOGIN_KEY="sf_key_2015482sfa";
	public static final String NAY_SAK_USER_API="http://oauth.anysdk.com/api/User/LoginOauth/";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**用户名*/
	private String username;
	/**渠道编号*/
	private int partner;
	/***/
	//private String sign;
	
	private String mac;
	
	/**像素*/
	private String pixel;
	/**手机型号*/
	private String model;
	/**连网方式*/
	private String net;
	/**运营商*/
	private String operator;
	/**系统*/
	private String system;
	/**ip*/
	private String ip;
	
	
	
	

	public String getPixel() {
		return pixel;
	}
	public void setPixel(String pixel) {
		this.pixel = pixel;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getNet() {
		return net;
	}
	public void setNet(String net) {
		this.net = net;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getPartner() {
		return partner;
	}
	public void setPartner(int partner) {
		this.partner = partner;
	}
	
	public  static String getOauthSign(String userId,String partnerId){
		return MD5Util.decode(userId+"|"+partnerId+"|"+LOGIN_KEY).toLowerCase();
	}
	
}
