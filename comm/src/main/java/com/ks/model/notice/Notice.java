package com.ks.model.notice;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告
 * 
 * @author ks
 */
public class Notice implements Serializable {

    private static final long serialVersionUID = -6557603755882264928L;

    private int id;
    /**公告内容*/
    private String content;
    /**颜色*/
    private String color;
    /**显示开始时间*/
	private Date fromTime;
	/**显示结束时间*/
	private Date endTime;
	/**显示频率(秒/次)*/
	private int interval;
	private Date createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getFromTime() {
		return fromTime;
	}
	public void setFromTime(Date fromTime) {
		this.fromTime = fromTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
}
