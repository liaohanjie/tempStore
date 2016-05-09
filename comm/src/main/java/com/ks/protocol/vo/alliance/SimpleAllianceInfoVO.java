package com.ks.protocol.vo.alliance;

import com.ks.protocol.Message;

/**
 * 工会简单信息对象
 * @author admin
 *
 */
public class SimpleAllianceInfoVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6029774759797745324L;

	/**
	 * 工会id
	 */
	private int allianceId;
	
	/**
	 * 工会等级
	 */
	private int level;
	
	/**
	 * 公会名称
	 */
	private String name = "";
	
	/**
	 * 描述
	 */
	private String descs = "";
	
	/**
	 * 是否已发送入会申请
	 */
	private boolean apply = false;

	public int getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(int allianceId) {
		this.allianceId = allianceId;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public boolean isApply() {
		return apply;
	}

	public void setApply(boolean apply) {
		this.apply = apply;
	}
}
