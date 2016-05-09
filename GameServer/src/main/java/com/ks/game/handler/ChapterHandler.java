package com.ks.game.handler;

import com.ks.app.Application;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.ChapterCMD;
import com.ks.protocol.vo.Head;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
/**
 * 副本
 * @author ks
 */
@MainCmd(mainCmd=MainCMD.CHAPTER)
public class ChapterHandler extends ActionAdapter{
	/**
	 * 查询当前副本
	 * @param handler
	 */
	@SubCmd(subCmd=ChapterCMD.QUERY_USER_CHAPTER)
	public void queryUserDungeon(GameHandler handler){
		Head head = handler.getHead();
		Application.sendMessage(handler.getChannel(), head, chapterAction().queryUserChapter(handler.getPlayer().getUserId()));
	}
	
	@SubCmd(subCmd=ChapterCMD.QUERY_USER_CHAPTER_LIST)
	public void queryUserChapterList(GameHandler handler){
		Head head = handler.getHead();
		Application.sendMessage(handler.getChannel(), head, chapterAction().queryUserChapterList(handler.getPlayer().getUserId()));
	}
	
	@SubCmd(subCmd=ChapterCMD.BUY_CHPATER_FIGHT_COUNT, args={"int"})
	public void buyChapterFightCount(GameHandler handler,int chapterId){
		Head head = handler.getHead();
		Application.sendMessage(handler.getChannel(), head, chapterAction().buyChapterFightCount(handler.getPlayer().getUserId(), chapterId));
	}
	
	/**
	 * 开始战斗
	 * @param handler
	 * @param dungeonId 副本编号
	 * @param checkpoint 段
	 */
	@SubCmd(subCmd=ChapterCMD.START_FIGHT,args={"int","int","byte"})
	public void startFight(GameHandler handler,int chapterId,int friendId,byte teamId){
		Head head = handler.getHead();
		Application.sendMessage(handler.getChannel(), 
				head, 
				chapterAction().startFight(handler.getPlayer().getUserId(), chapterId,friendId,teamId));
	}
	/**
	 * 战斗结束
	 * @param handler
	 * @param pass 是否通过
	 */
	@SubCmd(subCmd=ChapterCMD.END_FIGHT,args={"boolean","boolean"})
	public void endFight(GameHandler handler,boolean pass,boolean hasJoin){
		FightEndResultVO vo = chapterAction().endFight(handler.getPlayer().getUserId(), pass,hasJoin);
		Head head = handler.getHead();
		Application.sendMessage(handler.getChannel(),head,vo);
	}
	/**
	 * 开箱子
	 * @param handler
	 * @param boxId 箱子编号
	 */
	@SubCmd(subCmd=ChapterCMD.OPEN_BOX,args={"int"})
	public void openBox(GameHandler handler,int boxId){
		Application.sendMessage(handler.getChannel(),handler.getHead(),
				chapterAction().openBox(handler.getPlayer().getUserId(), boxId));
	}
	/**
	 * 复活
	 * @param handler
	 */
	@SubCmd(subCmd=ChapterCMD.RESURRECTION)
	public void resurrection(GameHandler handler){
		Application.sendMessage(handler.getChannel(),handler.getHead(),
				chapterAction().resurrection(handler.getPlayer().getUserId()));
	}
	/**
	 * 使用副本道具
	 * @param handler
	 * @param propId
	 */
	@SubCmd(subCmd=ChapterCMD.CHAPTER_使用副本道具,args={"int"})
	public void userBakProp(GameHandler handler,int propId){
		Application.sendMessage(handler.getChannel(),handler.getHead(),
				chapterAction().userBakProp(handler.getPlayer().getUserId(),propId));
	}
	
	/**
	 * 开始扫荡
	 * @param handler
	 * @param chapterId 章节id
	 * @param count 扫荡次数
	 */
	@SubCmd(subCmd=ChapterCMD.CHAPTER_扫荡,args={"int","int","byte"})
	public void startSweep(GameHandler handler,int chapterId,int count,byte teamId){
		Head head = handler.getHead();
		Application.sendMessage(handler.getChannel(), 
				head,chapterAction().sweep(handler.getPlayer().getUserId(), chapterId,count,teamId));
	}
	/**
	 * 已通过的活动副本
	 * @param handler
	 * @param propId
	 */
	@SubCmd(subCmd=ChapterCMD.CHAPTER_ACTIVITY_PASS)
	public void userActivityPassChpater(GameHandler handler){
		Application.sendMessage(handler.getChannel(),handler.getHead(),
				chapterAction().queryUserActivityChapter(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 章节宝箱奖励领取记录
	 * @param handler
	 */
	@SubCmd(subCmd=ChapterCMD.GET_CHAPTER_CHEST_RECORD)
	public void getChapterChestRecrds(GameHandler handler){
		Application.sendMessage(handler.getChannel(),handler.getHead(), chapterAction().getChapterChestRecrds(handler.getPlayer().getUserId()));
	}
	
	/**
	 * 领取章节宝箱奖励
	 * @param handler
	 * @param chapterId
	 */
	@SubCmd(subCmd=ChapterCMD.GET_CHAPTER_CHEST, args={"int"})
	public void getChapterChestAward(GameHandler handler, int chapterId){
		Application.sendMessage(handler.getChannel(),handler.getHead(), chapterAction().getChapterChestAward(handler.getPlayer().getUserId(), chapterId));
	}
}
