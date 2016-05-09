package com.ks.model.shop;

import java.io.Serializable;
import java.util.Date;

/**
 * 神秘商店物品购买记录
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月18日
 */
public class MysteryItemRecord implements Serializable {

    private static final long serialVersionUID = 7056713249442165695L;
    
	private int id;
	/**用户编号*/
    private int userId;
    /**神秘物品编号*/
    private int itemId;
    /**类型: 1 : 金币 2: 竞技场 3: 世界boss 4: 荣誉(排名赛)*/
    private int typeId;
    private Date createTime;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
}
