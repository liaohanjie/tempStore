/**
 * 
 */
package com.ks.model.filter;

import java.util.Date;

/**
 * 
 * @author lipp 2015年5月19日
 */
public class PayOrderFilter extends Filter {

	/**
	 */
	private static final long serialVersionUID = 1L;

	private String orderNo;

	/** 服务器 */
	private String serverId;

	/** 用户名 */
	private String userName;

	/**
	 * 用户id
	 */
	private Integer userId;

	/** 用户渠道 */
	private Integer userParnter;

	/** 状态 */
	private Integer status;

	/** 订单开始时间 */
	private Date startTime;

	/** 订单结束时间 */
	private Date endTime;

	/** 支付开始时间 */
	private Date payStartTime;

	/** 支付结束时间 */
	private Date payEndTime;

	/** 发货开始时间 */
	private Date deliveryStartTime;

	/** 发货结束时间 */
	private Date deliveryEndTime;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserParnter() {
		return userParnter;
	}

	public void setUserParnter(Integer userParnter) {
		this.userParnter = userParnter;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getPayStartTime() {
		return payStartTime;
	}

	public void setPayStartTime(Date payStartTime) {
		this.payStartTime = payStartTime;
	}

	public Date getPayEndTime() {
		return payEndTime;
	}

	public void setPayEndTime(Date payEndTime) {
		this.payEndTime = payEndTime;
	}

	public Date getDeliveryStartTime() {
		return deliveryStartTime;
	}

	public void setDeliveryStartTime(Date deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}

	public Date getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(Date deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
