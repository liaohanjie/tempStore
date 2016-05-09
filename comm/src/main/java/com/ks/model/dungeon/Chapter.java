package com.ks.model.dungeon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
/**
 * 章节
 * @author living.li
 * @date  2014年4月16日
 */
public class Chapter implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	/**初始章节编号*/
	public static final int CHAPTER_ID_关卡_START = 7010001;
	public static final int CHAPTER_ID_副本_START=7020001;
	public static final int CHAPTER_ID_爬塔试炼_START=7060001;
	public static final int CHAPTER_ID_武器装备_START=7070001;
	
	
	/**副本战斗次数*/
	public static final int FB_FIGHT_COUNT = 2;
	/**副本可以购买战斗的次数*/
	public static final int FB_FIGHT_BUY_COUNT = 3;
	/**副本战斗次数购买价格(魂钻)*/
	public static final int FB_FIGHT_BUY_COUNT_PRICE = 20;
	
	/**章节ID*/
	private int chapterId;
	/**地点Id*/
	private int siteId;
	/**章节名称*/
	private String name;	
	/**描述*/
	private String des;
	/**消耗体力*/
	private int stamina;
	/**下一章*/
	private int nextId;
	/**前一章*/
	private int pevId;	
	/**战斗背景id*/
	private int fightmapId;
	/**经验*/
	private int exp;
	/**用户所要达到的等级*/
	private int minLevel;
	/**战斗次数限制(0无限制)*/
	private int fightCount;
	
	private static final  Map<Integer,Integer> CHPATER_KEY_MAP=new HashMap<Integer, Integer>();;
	static {
		CHPATER_KEY_MAP.put(7040001, 3050002);
		CHPATER_KEY_MAP.put(7040002, 3050002);
		CHPATER_KEY_MAP.put(7040003, 3050002);
		
//		CHPATER_KEY_MAP.put(7040004, 3050003);
//		CHPATER_KEY_MAP.put(7040004, 3050003);
//		CHPATER_KEY_MAP.put(7040005, 3050003);
//		CHPATER_KEY_MAP.put(7040006, 3050003);
		
		CHPATER_KEY_MAP.put(7020001, 3050003);
		CHPATER_KEY_MAP.put(7020002, 3050003);
		CHPATER_KEY_MAP.put(7020003, 3050003);
		CHPATER_KEY_MAP.put(7020004, 3050003);
		CHPATER_KEY_MAP.put(7020005, 3050003);
		CHPATER_KEY_MAP.put(7020006, 3050003);
	}
	public static Integer getChapterKeyProp(Integer ch){
		return CHPATER_KEY_MAP.get(ch);
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public int getStamina() {
		return stamina;
	}
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
	public int getNextId() {
		return nextId;
	}
	public void setNextId(int nextId) {
		this.nextId = nextId;
	}
	public int getPevId() {
		return pevId;
	}
	public void setPevId(int pevId) {
		this.pevId = pevId;
	}
	public int getFightmapId() {
		return fightmapId;
	}
	public void setFightmapId(int fightmapId) {
		this.fightmapId = fightmapId;
	}

	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getMinLevel() {
		return minLevel;
	}
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	public int getFightCount() {
		return fightCount;
	}
	public void setFightCount(int fightCount) {
		this.fightCount = fightCount;
	}
}
