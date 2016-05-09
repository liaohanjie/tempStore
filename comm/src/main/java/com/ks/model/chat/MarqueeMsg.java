package com.ks.model.chat;

import java.io.Serializable;
import java.util.Date;

/**
 * 跑马灯内容
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年2月29日
 */
public class MarqueeMsg implements Serializable {

    private static final long serialVersionUID = 1055520149697299959L;
    
	/**内容*/
	private String content;
	/**创建时间*/
	private Date createTime;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
