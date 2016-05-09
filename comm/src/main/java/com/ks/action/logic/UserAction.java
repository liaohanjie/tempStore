package com.ks.action.logic;

import java.util.List;
import java.util.Map;

import com.ks.model.filter.UserFilter;
import com.ks.model.user.User;
import com.ks.rpc.Async;

public interface UserAction {
	public User bossFindUserByUsername(String username, int partner);

	/**
	 * 充值
	 * 
	 * @param orderNo
	 *            订单号
	 * @param amount
	 *            充值金额
	 * @param gameCoin
	 *            游戏币
	 * @param username
	 *            用户名
	 * @param partner
	 *            渠道商
	 * @param goodsId
	 *            商品类型编号
	 */
	public void pay(String orderNo, int amount, int gameCoin, String username, int partner, int goodsId);
	
	/**
	 * 订单返还
	 * 
	 * @param userName
	 * @param orderNo
	 * @param currency
	 * @param extraCurrency
	 */
	public void orderReturn(int partner, String userName, String orderNo, int currency, int extraCurrency);

	/**
	 * 登出
	 * 
	 * @param userId
	 */
	public void logout(int userId);

	/**
	 * 根据用户ID查找用户
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserByUserId(int userId);

	/**
	 * 查询玩家信息
	 * 
	 * @param filter
	 * @return
	 */
	public List<User> getUsers(UserFilter filter);

	/**
	 * 在线用户统计
	 * 
	 * @param num
	 */
	@Async
	void sendOnlinePlayerNum(String serverId, int num);

	/***
	 * 玩家等级统计
	 * 
	 * @return
	 */
	public List<Map<String, Object>> statisticsUserLevel();

	/***
	 * 玩家流失统计
	 * 
	 * @return
	 */
	public List<Map<String, Object>> statisticsUserGuide();

	/**
	 * 修改玩家信息
	 * 
	 * @param user
	 */
	public void updateUser(User user);
}
