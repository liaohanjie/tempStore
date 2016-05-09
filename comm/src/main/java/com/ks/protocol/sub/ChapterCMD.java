package com.ks.protocol.sub;
/**
 * 地下城协议
 * @author ks
 */
public interface ChapterCMD {
	
	/**查询当前副本*/
	short QUERY_USER_CHAPTER = 1;
	/**开始战斗*/
	short START_FIGHT = 2;
	/**结束战斗*/
	short END_FIGHT = 3;
	/**开箱子*/
	short OPEN_BOX = 4;
	/**复活*/
	short RESURRECTION = 5;
	
	short CHAPTER_使用副本道具 = 6;
	
	short CHAPTER_扫荡 = 7;
	
	/**活动已通关的章节*/
	short CHAPTER_ACTIVITY_PASS = 8;
	
	/**章节宝箱获取记录*/
	short GET_CHAPTER_CHEST_RECORD = 9;
	
	/**章节宝箱获取*/
	short GET_CHAPTER_CHEST = 10;
	
	/**查询用户通关所有副本*/
	short QUERY_USER_CHAPTER_LIST = 11;
	
	/**购买副本挑战次数*/
	short BUY_CHPATER_FIGHT_COUNT = 12;
}
