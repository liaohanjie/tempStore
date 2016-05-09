package com.ks.model.goods;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
/**
 * 用户副本物品
 * @author living.li
 * @date   2014年6月23日
 */
public class UserBakProp implements Serializable{

	private static final long serialVersionUID = 1L;
	/**用户ID*/	
	private int userId;
	/**副本道具ID*/
	private int bakPropId;
	/**数量*/
	private int num;
	/**修改时间*/
	private Date updateTime;
	/**创建时间*/
	private Date createTime;
	

	public static UserBakProp create(int userId,int bakPropId,int num){
		UserBakProp prop=new UserBakProp();
		prop.setUserId(userId);
		prop.setBakPropId(bakPropId);
		prop.setNum(num);
		prop.setCreateTime(Calendar.getInstance().getTime());
		prop.setUpdateTime(Calendar.getInstance().getTime());
		return prop;
	}
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBakPropId() {
		return bakPropId;
	}

	public void setBakPropId(int bakPropId) {
		this.bakPropId = bakPropId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	
	
	
}
