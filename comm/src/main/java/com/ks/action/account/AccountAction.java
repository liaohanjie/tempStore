package com.ks.action.account;

import java.util.List;
import java.util.Map;

import com.ks.model.account.Account;


/**
 * 玩家账号Action
 * 
 * @author zhoujf
 * @date 2015年6月24日
 */
public interface AccountAction {
	
	/**
	 * 用户登陆，没有就注册
	 * @param entity
	 * @return
	 */
	Account login(Account entity);
	
	/**
	 * 根据账号id查询
	 * @param id
	 * @return
	 */
	Account queryById(Integer id);
	
	/**
	 * 根据用户ID查询
	 * @param userId
	 * @return
	 */
	Account queryByUserId(Integer userId);
	
	/**
	 * 根据合作商id和用户名查找
	 * @param partnerId
	 * @param userName
	 * @return
	 */
	Account queryByPartnerIdUserName(Integer partnerId, String userName);
	
	/**
	 * 添加
	 * @param entity
	 */
	void add(Account entity);
	
	/**
	 * 修改
	 * @param entity
	 */
	void update(Account entity);
	
	/**
	 * 删除
	 * @param entity
	 */
	void delete(Account entity);

	/**
	 * 玩家渠道分布统计
	 * 
	 * @return
	 */
	public List<Map<String, Object>>  statisticsUserPartner();
}
