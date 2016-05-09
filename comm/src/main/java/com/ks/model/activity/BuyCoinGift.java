/**
 * 
 */
package com.ks.model.activity;

import java.io.Serializable;
import java.util.List;

import com.ks.model.goods.Goods;

/**
 * @author living.li
 * @date  2014年10月13日 下午5:40:15
 * 限时礼包（购买魂币送礼品）
 */
public class BuyCoinGift implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	/**游戏币数量*/
	private int gameCoin;
	/**物品*/
	private List<Goods> goods;
	/**邮件主题*/
	private String mailTital;
	/**邮件内容*/
	private String mailContext;
	
	
	
	public List<Goods> getGoods() {
		return goods;
	}
	public void setGoods(List<Goods> goods) {
		this.goods = goods;
	}
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
	public int getGameCoin() {
		return gameCoin;
	}
	public void setGameCoin(int gameCoin) {
		this.gameCoin = gameCoin;
	}
	@Override
	public String toString() {
		return "BuyCoinGift [id=" + id + ", gameCoin=" + gameCoin + ", goods="
				+ goods + ", mailTital=" + mailTital + ", mailContext="
				+ mailContext + "]";
	}
}
