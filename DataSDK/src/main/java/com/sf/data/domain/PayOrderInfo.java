package com.sf.data.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值
 * @author living.li
 * @date 2014年7月3日
 */
public class PayOrderInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int gameId;
	/**角较色ID*/
	private String serverId;
	/**渠道*/
	private int partner;
	/**用户名*/
	private String username;
	/**角色ID*/
	private int roleId;
	/**RMB*/
	private int money;
	/**游戏币*/
	private int gameCoin;
	/**创建时间*/
	private Date createTime;
	/**1首充*/
	private int optType;
	/**充值方式**/
	private int payType;
	/**充值订单*/
	private String orderNo;
	/**成交时间*/
	private Date dealTime;
	
	private int status;
	
	private int partnerId;
	/**像素*/
	private String pixel;
	/**手机型号*/
	private String model;
	/**连网方式*/
	private String net;
	/**运营商*/
	private String operator;
	/**/
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
	
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getDealTime() {
		return dealTime;
	}
	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public int getPartner() {
		return partner;
	}
	public void setPartner(int partner) {
		this.partner = partner;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getGameCoin() {
		return gameCoin;
	}
	public void setGameCoin(int gameCoin) {
		this.gameCoin = gameCoin;
	}

	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getOptType() {
		return optType;
	}
	public void setOptType(int optType) {
		this.optType = optType;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}
	
}
