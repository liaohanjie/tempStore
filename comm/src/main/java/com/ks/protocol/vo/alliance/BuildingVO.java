package com.ks.protocol.vo.alliance;

import com.ks.protocol.Message;
/**
 * 建设信息
 * @author admin
 *
 */
public class BuildingVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7731323182436523152L;
	
	/**
	 * 工会贡献值
	 */
	private long allianceDevote;
	
	/**
	 * 玩家贡献值
	 */
	private long userDevote;
	
	/**
	 * 建设后的金币
	 */
	private int goold;
	
	/**
	 * 建设后的魂钻
	 */
	private int currency;

	public long getAllianceDevote() {
		return allianceDevote;
	}

	public void setAllianceDevote(long allianceDevote) {
		this.allianceDevote = allianceDevote;
	}

	public long getUserDevote() {
		return userDevote;
	}

	public void setUserDevote(long userDevote) {
		this.userDevote = userDevote;
	}

	public int getGoold() {
		return goold;
	}

	public void setGoold(int goold) {
		this.goold = goold;
	}

	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}
}
