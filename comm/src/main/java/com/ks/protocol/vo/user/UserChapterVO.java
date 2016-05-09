package com.ks.protocol.vo.user;

import com.ks.model.dungeon.Chapter;
import com.ks.model.user.UserChapter;
import com.ks.protocol.Message;

public class UserChapterVO extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**章节编号*/
	private int chapterId;	
	/**地点*/
	private int siteId;
	/**通关次数*/
	private int passCount;
	/**剩余挑战次数*/
	private int leftCount;
	/**剩余购买次数*/
	private int leftBuyCount;
	
	public void init(UserChapter o,int siteId){
		this.chapterId = o.getChapterId();
		this.passCount = o.getPassCount();
		this.siteId=siteId;
		this.leftCount = Chapter.FB_FIGHT_COUNT + o.getSameDayBuyCount() - o.getSameDayCount();
		this.leftBuyCount = Chapter.FB_FIGHT_BUY_COUNT - o.getSameDayBuyCount();
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
	public int getPassCount() {
		return passCount;
	}
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	public int getLeftCount() {
		return leftCount;
	}
	public void setLeftCount(int leftCount) {
		this.leftCount = leftCount;
	}
	public int getLeftBuyCount() {
		return leftBuyCount;
	}
	public void setLeftBuyCount(int leftBuyCount) {
		this.leftBuyCount = leftBuyCount;
	}
	@Override
    public String toString() {
	    return "UserChapterVO [chapterId=" + chapterId + ", siteId=" + siteId + ", passCount=" + passCount + ", leftCount=" + leftCount + ", leftBuyCount=" + leftBuyCount + "]";
    }
}
