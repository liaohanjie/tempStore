package com.ks.protocol.vo.notice;

import com.ks.model.notice.Notice;
import com.ks.protocol.Message;

public class NoticeVO extends Message {

	
    private static final long serialVersionUID = 1223593828352240930L;
    
    /**公告内容*/
    private String content;
    /**显示开始时间*/
	private long fromTime;
	/**显示结束时间*/
	private long endTime;
	/**显示频率(秒/次)*/
	private int interval;
	
	public void init(Notice o) {
		this.content = String.format("<font color=%s>%s</font>", o.getColor(), o.getContent());
		this.fromTime = o.getFromTime().getTime();
		this.endTime = o.getEndTime().getTime();
		this.interval = o.getInterval();
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public long getFromTime() {
		return fromTime;
	}
	public void setFromTime(long fromTime) {
		this.fromTime = fromTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	
	@Override
    public String toString() {
	    return "NoticeVO [content=" + content + ", fromTime=" + fromTime + ", endTime=" + endTime + ", interval=" + interval + "]";
    }
}
