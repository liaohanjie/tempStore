package com.ks.model.alliance;

import com.ks.util.DateUtil;

/**
 * 工会对象
 * @author hanjie.l
 *
 */
public class Alliance {
	
	/** 
	 * 工会id(主键id)
	 */
	private int id;
	
	/**
	 * 公会名称
	 */
	private String allianceName = "";
	
	/**
	 * 会长用户id
	 */
	private int ownerUserId;

	/**
	 * 公会等级
	 */
	private int allianceLevel = 1;

	/**
	 * 公会公告(对内)
	 */
	private String notice = "";
	
	/**
	 * 描述
	 */
	private String descs = "";
	
	/**
	 * 贡献值
	 */
	private long devote;
	
	/**
	 * 今日贡献值
	 */
	private long todayDevote;
	
	/**
	 * 下次更新时间
	 */
	private long nextRefreshTime;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAllianceName() {
		return allianceName;
	}

	public void setAllianceName(String allianceName) {
		this.allianceName = allianceName;
	}

	public int getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(int ownerUserId) {
		this.ownerUserId = ownerUserId;
	}

	public int getAllianceLevel() {
		return allianceLevel;
	}

	public void setAllianceLevel(int allianceLevel) {
		this.allianceLevel = allianceLevel;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public long getDevote() {
		return devote;
	}

	public void setDevote(long devote) {
		this.devote = devote;
	}

	public long getTodayDevote() {
		return todayDevote;
	}

	public void setTodayDevote(long todayDevote) {
		this.todayDevote = todayDevote;
	}

	public long getNextRefreshTime() {
		return nextRefreshTime;
	}

	public void setNextRefreshTime(long nextRefreshTime) {
		this.nextRefreshTime = nextRefreshTime;
	}

	/**
	 * 重置
	 */
	public void reset(){
		this.nextRefreshTime = DateUtil.getNextDateTime(0, 0, 0).getTime();
		this.todayDevote = 0;
	}
}