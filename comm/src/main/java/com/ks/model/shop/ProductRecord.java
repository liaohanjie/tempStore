package com.ks.model.shop;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品购买记录
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月18日
 */
public class ProductRecord implements Serializable {

    private static final long serialVersionUID = 1;
    
    private int id;
    /**商品编号*/
    private int productId;
    /**物品编号*/
    private int productNum;
    /**物品类型*/
    private int userId;
    /**物品数量*/
    private Date createTime;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getProductNum() {
		return productNum;
	}
	public void setProductNum(int productNum) {
		this.productNum = productNum;
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
