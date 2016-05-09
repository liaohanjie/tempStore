package com.ks.model.goods;

import java.io.Serializable;
import java.util.Date;
/**
 * 战斗道具
 * @author ks
 */
public class FightProp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**格子编号*/
	private int gridId;
	/**用户编号*/
	private int userId;
	/**道具编号*/
	private int propId;
	/**数量*/
	private int num;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	
	public int getGridId() {
		return gridId;
	}
	public void setGridId(int gridId) {
		this.gridId = gridId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
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
}
