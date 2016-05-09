package com.ks.model.pay;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单返还
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年4月14日
 */
public class OrderReturn implements Serializable {

    private static final long serialVersionUID = 6571962323979246620L;

	private int id;
	private String userName;
	private String orderNo;
	private int amount;
	private int status;
	private Date returnTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getReturnTime() {
		return returnTime;
	}
	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
