package com.ks.model.affiche;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统日志
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月15日
 */
public class MailLog implements Serializable {

    
    private static final long serialVersionUID = 5471687098439494431L;
    
	private int id;
	/**用户编号*/
	private int userId;
	/**邮件编号*/
	private int mailId;
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
	public int getMailId() {
		return mailId;
	}
	public void setMailId(int mailId) {
		this.mailId = mailId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
