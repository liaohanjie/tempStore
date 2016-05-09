package com.ks.action.logic;

import java.util.List;

import com.ks.model.affiche.Mail;

/**
 * 邮件
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月15日
 */
public interface MailAction {

	/**
	 * 所有
	 * 
	 * @return
	 */
	List<Mail> queryAll();
	
	/**
	 * 查找
	 * 
	 * @param id
	 * @return
	 */
	Mail findById(int id);
	
	/**
	 * 添加
	 * 
	 * @param entity
	 */
	void add(Mail entity);
	
	/**
	 * 修改
	 * 
	 * @param entity
	 */
	void update(Mail entity);
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delete(int id);
}
