package com.ks.model.account;

import java.io.Serializable;

/**
 * 渠道支付配置表
 * @author zhoujf
 * @date 2015年6月24日
 */
public class PayConfig  implements Serializable{
	
    private static final long serialVersionUID = 452681193003639975L;
    
	private Integer id;
	/**游戏编号*/
	private Integer gameId;
	/**合作商编号*/
	private Integer partnerId;
	/**合作商支付渠道编号(0表示没有)*/
	private String payChannelId;
	/**合作商名称*/
	private String payChannelName;
	/**作商支付渠道类型名*/
	private String payChannelTypeName;
	/**状态  0: 不启用 ， 1: 启用*/
	private Integer status;
	/**订单前缀 2位*/
	private String orderPrefix;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGameId() {
		return gameId;
	}
	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}
	public Integer getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}
	public String getPayChannelId() {
		return payChannelId;
	}
	public void setPayChannelId(String payChannelId) {
		this.payChannelId = payChannelId;
	}
	public String getPayChannelName() {
		return payChannelName;
	}
	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}
	public String getPayChannelTypeName() {
		return payChannelTypeName;
	}
	public void setPayChannelTypeName(String payChannelTypeName) {
		this.payChannelTypeName = payChannelTypeName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderPrefix() {
		return orderPrefix;
	}
	public void setOrderPrefix(String orderPrefix) {
		this.orderPrefix = orderPrefix;
	}
}
