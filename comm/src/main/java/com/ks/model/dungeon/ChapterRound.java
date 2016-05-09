package com.ks.model.dungeon;

import java.io.Serializable;

/**
 * 章节回合
 * @author living.li
 * @date  2014年4月16日
 */
public class ChapterRound implements Serializable {


	public  static final String MONSTERS_SPLIT="_";
	
	private static final long serialVersionUID = 1L;
	
	/**章节ID*/
	private int chapterId;
	/**回合数*/
	private int round;
	/**怪物*/
	private String monsters;
	
	
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public String getMonsters() {
		return monsters;
	}
	public void setMonsters(String monsters) {
		this.monsters = monsters;
	}
	

	

	
	
}
