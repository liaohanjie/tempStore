package com.ks.model.affiche;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.ks.model.goods.Goods;

/**
 * 系统邮件
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月15日
 */
public class Mail implements Serializable {

    private static final long serialVersionUID = 4654567169992451722L;
    
	private int id;
	/**发送类型*/
	private int type;
	/**标题*/
	private String title;
	/**内容*/
	private String context;
	/**奖励物品*/
	private List<Goods> goodsList;
	/**用户编号，用 逗号隔开*/
	private String userIds;
	/***/
	private String logo;
	/**开始日期*/
	private Date fromDate;
	/**结束日期*/
	private Date endDate;
	/**创建日期*/
	private Date createDate;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public List<Goods> getGoodsList() {
		return goodsList;
	}
	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
