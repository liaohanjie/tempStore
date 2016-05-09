package com.ks.protocol.vo.activity;

import java.util.List;

import com.ks.model.activity.ActivityDefine;
import com.ks.protocol.Message;

/**
 * 活动
 * 
 * @author zhoujf
 * @date 2015年7月7日
 */
public  class  ActivityVO extends Message {

	private static final long serialVersionUID = 1L;

	/**进行中副本/活动*/
	private int defineId;
	/**类型*/
	private int type;
	/**开始时间*/
	private long startTime;
	/**结束时间*/
	private long endTime;
	/**副本章节**/
	private String chapterIds;
	/**标题**/
	private String title;
	/**内容**/
	private String context;
	/**活动礼包*/
	private List<ActivityGiftVO> activityGiftList;
	
	public ActivityVO(){
		
	}
	
	public void initActivityVO(ActivityDefine o, List<ActivityGiftVO> activityGiftList) {
	    this.defineId = o.getDefineId();
	    this.type = o.getType();
	    this.startTime = o.getStartTime().getTime();
	    this.endTime = o.getEndTime().getTime();
	    this.chapterIds = o.getChapterIds();
	    this.title = o.getTitle();
	    this.context = o.getContext();
	    this.activityGiftList = activityGiftList;
    }
	
	public int getDefineId() {
		return defineId;
	}
	public void setDefineId(int defineId) {
		this.defineId = defineId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getChapterIds() {
		return chapterIds;
	}
	public void setChapterIds(String chapterIds) {
		this.chapterIds = chapterIds;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public List<ActivityGiftVO> getActivityGiftList() {
		return activityGiftList;
	}
	public void setActivityGiftList(List<ActivityGiftVO> activityGiftList) {
		this.activityGiftList = activityGiftList;
	}
}