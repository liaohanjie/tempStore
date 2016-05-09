package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.affiche.Mail;

/**
 * 全局状态
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月11日
 */
public interface MailService {

	/**
	 * 所有
	 * 
	 * @return
	 */
	List<Mail> queryAll();
	
	/**
	 * 查询当前有效
	 * 
	 * @return
	 */
	List<Mail> queryInTime();
	
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
	@Transaction
	void add(Mail entity);
	
	/**
	 * 修改
	 * 
	 * @param entity
	 */
	@Transaction
	void update(Mail entity);
	
	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transaction
	void delete(int id);
	
	/**
	 * 发送后台邮件
	 * 
	 * @param userId
	 * @param notify
	 */
	@Transaction
	void send(int userId, boolean notify);
}