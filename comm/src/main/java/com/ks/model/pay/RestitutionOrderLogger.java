package com.ks.model.pay;

import java.io.Serializable;
import java.util.Date;

import com.ks.model.filter.Filter;

/**
 * 返还订单金额操作日志
 * 
 * @author lipp 2016年3月4日
 */
public class RestitutionOrderLogger extends Filter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	/** 操作者 */
	private String author;

	/** 原始订单号 */
	private String orderNo;

	/** 充值金额 */
	private int amount;

	/** 返还的游戏币 */
	private int gameCoin;

	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getGameCoin() {
		return gameCoin;
	}

	public void setGameCoin(int gameCoin) {
		this.gameCoin = gameCoin;
	}

}
