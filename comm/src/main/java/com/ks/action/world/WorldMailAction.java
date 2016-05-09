package com.ks.action.world;

import java.util.List;

import com.ks.model.affiche.Mail;
import com.ks.rpc.Async;

/**
 * 系统邮件
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月15日
 */
public interface WorldMailAction {

	/**
	 * 添加
	 * 
	 * @param entity
	 */
	@Async
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
	 * @param entity
	 */
	void delete(int id);
	
	/**
	 * 所有
	 * 
	 * @return
	 */
	List<Mail> queryAll();
}
