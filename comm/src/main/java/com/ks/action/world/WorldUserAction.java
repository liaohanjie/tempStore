package com.ks.action.world;

import java.util.List;
import java.util.Map;

import com.ks.model.affiche.Affiche;
import com.ks.model.filter.UserFilter;
import com.ks.model.user.User;
import com.ks.rpc.Async;

/**
 * @author living
 * @date 2014年6月28日
 * 
 */
public interface WorldUserAction {
	public User bossFindUserByUsername(String username, int partner);

	public void addAffiche(Affiche a);

	/**
	 * 根据ID查找玩家信息
	 * 
	 * @param userId
	 * @return
	 */
	public User findUserById(int userId);

	/**
	 * 用户通知
	 * <br/>
	 * userId=0 通知所有在线用户， userId !=0 通知指定用户
	 * 
	 * @param userId
	 * @param property
	 */
	@Async
	public void notifiUser(int userId, int property);

	/**
	 * 登出清缓存
	 * @param userId
	 */
	public void logout(int userId);
	
//	/**
//	 * 登出清缓存
//	 * @param user
//	 */
//	public void logout(User user);

	/**
	 * 查询玩家信息
	 * 
	 * @param filter
	 * @return
	 */
	public List<User> getUsers(UserFilter filter);

	/**
	 * 玩家等级统计
	 * 
	 * @return
	 */
	public List<Map<String, Object>> statisticsUserLevel();

	/**
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
	
	
	/**
	 * 添加订单, 发放游戏币
	 * 
	 * @param orderNo
	 * @param amount
	 * @param gameCoin
	 * @param username
	 * @param partner
	 * @param goodsId
	 */
	public void addOrder(String orderNo, int amount, int gameCoin, String username, int partner, int goodsId);
	
	/**
	 * 订单返还
	 * 
	 * @param partner
	 * @param userName
	 * @param orderNo
	 * @param currency
	 * @param extraCurrency
	 */
	public void orderReturn(int partner, String userName, String orderNo, int currency, int extraCurrency);
}
