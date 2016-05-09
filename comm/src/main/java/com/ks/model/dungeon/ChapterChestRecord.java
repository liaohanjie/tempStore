package com.ks.model.dungeon;

import java.io.Serializable;
import java.util.Date;

/**
 * 章节宝箱奖励记录
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月23日
 */
public class ChapterChestRecord implements Serializable {	
	
    private static final long serialVersionUID = -6747668668790400451L;
    
	/**编号*/
	private int id;
	/**用户编号*/
	private int userId;
	/**章节编号*/
	private int chapterId;
	/**创建时间*/
	private Date createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
