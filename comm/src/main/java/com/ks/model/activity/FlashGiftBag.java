/**
 * 
 */
package com.ks.model.activity;

import java.io.Serializable;
import java.util.List;

import com.ks.model.goods.Goods;

/**
 * @author living.li
 * @date  2014年10月17日 下午4:34:16
 *限时礼包
 */
public class FlashGiftBag implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***/
	private int id;
	/**最大数量*/
	private int maxNum;
	/**已经购买数量*/
	private int buyNum;
	/**魂币价格*/
	private int price;
	/**物品*/
	private List<Goods> goods;
	/**邮件标题*/
	private String  mailTital;
	/**邮件内容*/
	private String mailContext;
	
	public String getMailTital() {
		return mailTital;
	}
	public void setMailTital(String mailTital) {
		this.mailTital = mailTital;
	}
	public String getMailContext() {
		return mailContext;
	}
	public void setMailContext(String mailContext) {
		this.mailContext = mailContext;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public int getBuyNum() {
		return buyNum;
	}
	public void setBuyNum(int buyNum) {
		this.buyNum = buyNum;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public List<Goods> getGoods() {
		return goods;
	}
	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}
}
