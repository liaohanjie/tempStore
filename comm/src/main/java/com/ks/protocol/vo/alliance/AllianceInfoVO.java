package com.ks.protocol.vo.alliance;

import com.ks.model.alliance.Alliance;
import com.ks.protocol.Message;
/**
 * 工会信息
 * @author admin
 *
 */
public class AllianceInfoVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9220396171331912056L;
	
	
	public void init(Alliance alliance){
		this.id = alliance.getId();
		this.name = alliance.getAllianceName();
		this.level = alliance.getAllianceLevel();
		this.notice = alliance.getNotice();
		this.desc = alliance.getDescs();
		this.devote = alliance.getDevote();
		this.todayDevote = alliance.getTodayDevote();
	}
	
	
	/** 
	 * 工会id(主键id)
	 */
	private int id;
	
	/**
	 * 公会名称
	 */
	private String name = "";

	/**
	 * 公会等级
	 */
	private int level = 1;

	/**
	 * 公会公告(对内)
	 */
	private String notice = "";
	
	/**
	 * 描述
	 */
	private String desc = "";
	
	/**
	 * 贡献值
	 */
	private long devote;
	
	/**
	 * 今日贡献值
	 */
	private long todayDevote;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
}
