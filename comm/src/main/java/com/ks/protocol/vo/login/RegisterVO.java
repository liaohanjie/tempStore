package com.ks.protocol.vo.login;

import com.ks.protocol.Message;
/**
 * 注册
 * @author ks
 */
public class RegisterVO extends Message {

	private static final long serialVersionUID = 1L;
	/**用户名*/
	private String username;
	/**合作方编号*/
	private int partner;
	
	/**战魂编号*/
	private int soulId;
	/**玩家名称*/
	private String playerName;
	/**用户IP*/
	private String ip;
	/**用户mac地址*/
	private String mac;
	/**像素*/
	private String pixel;
	/**手机型号*/
	private String model;
	/**连网方式*/
	private String net;
	/**运营商*/
	private String operator;
	/**手机系统*/
	private String system;

	
	
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
	public int getSoulId() {
		return soulId;
	}
	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
}
