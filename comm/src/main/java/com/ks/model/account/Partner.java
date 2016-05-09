/**
 * 
 */
package com.ks.model.account;

import java.io.Serializable;

/**
 * @author living.li
 * @date  2015年6月3日 上午10:59:51
 * 用户渠道
 */
public class Partner  implements Serializable{
	
	
	/** */
	private static final long serialVersionUID = 1L;

	private int parnterId;
	
	private String partnerName;
	
	private boolean isPay;
	
	private String sysPlatform;

	public int getParnterId() {
		return parnterId;
	}

	public void setParnterId(int parnterId) {
		this.parnterId = parnterId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public boolean isPay() {
		return isPay;
	}

	public void setPay(boolean isPay) {
		this.isPay = isPay;
	}

	public String getSysPlatform() {
	    return sysPlatform;
    }

	public void setSysPlatform(String sysPlatform) {
	    this.sysPlatform = sysPlatform;
    }
	
	
}
