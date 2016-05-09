package com.ks.protocol.vo.alliance;

import com.ks.protocol.Message;
/**
 * 捐赠VO
 * @author admin
 *
 */
public class ContributeVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2485318711142101335L;

	/**
	 * 工会贡献值
	 */
	private long allianceDevote;
	
	/**
	 * 玩家贡献值
	 */
	private long userDevote;
	

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
}
