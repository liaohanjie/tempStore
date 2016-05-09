package com.ks.model.robot;

public class Robot {
	
	/**
	 * 主键id
	 */
	private int id;
	
	/**
	 * 机器名
	 */
	private String playerName;
	
	/**
	 * 等级
	 */
	private int level;
	
	/**
	 * 队伍模版
	 */
	private int templateId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}
}
