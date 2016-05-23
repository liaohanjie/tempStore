package com.ks.account.service;

import java.util.List;
import java.util.Map;

import com.ks.access.Transaction;
import com.ks.model.account.Account;

/**
 * 玩家账号 Service
 * 
 * @author zhoujf
 * @date 2015年6月24日
 */
public interface AccountService {

	/**
	 * 根据ID查找用户
	 * @param id
	 * @return
	 */
	Account queryById(Integer id);
	
	/**
	 * 根据用户ID查找
	 * @param userId
	 * @return
	 */
	Account queryByUserId(Integer userId);
	
	/**
	 * 根据用户名和合作商ID查找
	 * @param partnerId
	 * @param userName
	 * @return
	 */
	Account queryByPartnerIdUserName(Integer partnerId, String userName);
	
	/**
	 * 添加账号
	 * @param entity
	 */
	@Transaction
	void add(Account entity);
	
	/**
	 * 修改账号
	 * @param entity
	 */
	@Transaction
	void update(Account entity);
	
	/**
	 * 删除账号
	 * @param userId
	 */
	@Transaction
	void delete(Integer userId);
	
	/**
	 * 玩家渠道分布统计
	 * 
	 * @return
	 */
	public List<Map<String, Object>>  statisticsUserPartner();
}
