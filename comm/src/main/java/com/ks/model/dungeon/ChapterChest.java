package com.ks.model.dungeon;

import java.io.Serializable;

/**
 * 章节宝箱奖励
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月23日
 */
public class ChapterChest implements Serializable {	
	
    private static final long serialVersionUID = -6747668668790400451L;
    
	/**章节编号*/
	private int id;
	/**奖励编号*/
	private int awardId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAwardId() {
		return awardId;
	}
	public void setAwardId(int awardId) {
		this.awardId = awardId;
	}
}
