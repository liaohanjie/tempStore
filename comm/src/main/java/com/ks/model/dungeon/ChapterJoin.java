package com.ks.model.dungeon;

import java.io.Serializable;
/**
 * 
 * @author living.li
 * @date 2014年7月29日
 */
public class ChapterJoin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**关卡ID*/
	private int chapterId;
	/**概率*/
	private double  rate;
	/**乱入关卡*/
	private String monster;
	/**掉落ID*/
	private int dropId;
	
	public static final int RATE_BASE=10000;

	
	public String getMonster() {
		return monster;
	}
	public void setMonster(String monster) {
		this.monster = monster;
	}
	public int getDropId() {
		return dropId;
	}
	public void setDropId(int dropId) {
		this.dropId = dropId;
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	
	
	
}
