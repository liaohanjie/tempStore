package com.ks.model.user;

import java.io.Serializable;
import java.util.Date;
/**
 * 用户副本
 * @author ks
 */
public class UserChapter implements Serializable {

	private static final long serialVersionUID = 1L;
	/**用户编号*/
	private int userId;
	/**章节编号*/
	private int chapterId;
	/**通关次数*/
	private int passCount;
	/**当天挑战次数*/
	private int sameDayCount;
	/**当天购买次数*/
	private int sameDayBuyCount;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "UserChapter [userId=" + userId + ", chapterId=" + chapterId
				+ ", passCount=" + passCount + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	public int getPassCount() {
		return passCount;
	}
	public void setPassCount(int passCount) {
		this.passCount = passCount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getChapterId() {
		return chapterId;
	}
	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}
	public int getSameDayCount() {
		return sameDayCount;
	}
	public void setSameDayCount(int sameDayCount) {
		this.sameDayCount = sameDayCount;
	}
	public int getSameDayBuyCount() {
		return sameDayBuyCount;
	}
	public void setSameDayBuyCount(int sameDayBuyCount) {
		this.sameDayBuyCount = sameDayBuyCount;
	}
}
