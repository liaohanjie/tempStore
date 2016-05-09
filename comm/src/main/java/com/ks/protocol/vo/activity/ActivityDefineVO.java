package com.ks.protocol.vo.activity;

import com.ks.model.activity.ActivityDefine;
import com.ks.protocol.Message;
/**
 * 
 * @author living.li
 * @date   2014年6月23日
 */
public  class ActivityDefineVO extends Message{

	/**
	 * 
	 */
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
	/**活动开始时间*/
	private long startHourTime;
	/**活动结束时间*/
	private long endHourTime;
	/*中国星期习惯  周日=7*/
	private String weekTime;
	/**活动类型类别，客户端显示tab使用*/
	private int typeClass;
	
	public String getWeekTime() {
		return weekTime;
	}
	public void setWeekTime(String weekTime) {
		this.weekTime = weekTime;
	}
	public void init(ActivityDefine o){
		this.defineId = o.getDefineId();
		this.type = o.getType();
		this.startTime = o.getStartTime().getTime();
		this.endTime = o.getEndTime().getTime();
		this.chapterIds=o.getChapterIds();
		this.title=o.getTitle();
		this.context=o.getContext();
		this.startHourTime=o.getStartHour();
		this.endHourTime=o.getEndHour();
		this.weekTime=o.getWeekTime();
		this.typeClass=o.getTypeClass();
	}
	public long getStartHourTime() {
		return startHourTime;
	}
	public void setStartHourTime(long startHourTime) {
		this.startHourTime = startHourTime;
	}
	public long getEndHourTime() {
		return endHourTime;
	}
	public void setEndHourTime(long endHourTime) {
		this.endHourTime = endHourTime;
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

	public String getChapterIds() {
		return chapterIds;
	}
	public void setChapterIds(String chapterIds) {
		this.chapterIds = chapterIds;
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
	public Long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public Long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getTypeClass() {
		return typeClass;
	}
	public void setTypeClass(int typeClass) {
		this.typeClass = typeClass;
	}
}