package com.ks.logic.service;

import java.util.List;
import java.util.Map;

import com.ks.access.Transaction;
import com.ks.model.filter.UserFilter;
import com.ks.model.user.GrowthFundRule;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserRule;
import com.ks.model.user.UserStat;
import com.ks.model.vip.VipPrivilege;
import com.ks.model.vip.VipWeekAward;
import com.ks.protocol.vo.goods.FightPropVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.GuideRetVO;
import com.ks.protocol.vo.login.LoginResultVO;
import com.ks.protocol.vo.login.LoginVO;
import com.ks.protocol.vo.login.RegisterVO;
import com.ks.protocol.vo.user.CoinHandVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.protocol.vo.user.UserInfoVO;
import com.ks.protocol.vo.user.UserStatVO;

/**
 * 用户服务
 * 
 * @author ks
 */
public interface UserService {

	/**
	 * 用户登录
	 * 
	 * @param login
	 *            登陆信息
	 * @return 用户登录结果 :483672798
	 */
	@Transaction
	LoginResultVO userLogin(LoginVO login);

	/**
	 * 用户退出
	 * 
	 * @param userId
	 *            用户编号
	 */
	@Transaction
	void logout(int userId);

	/**
	 * 用户注册
	 * 
	 * @param register
	 *            注册信息
	 */
	@Transaction
	void userRegister(RegisterVO register);

	/**
	 * 获取用户信息
	 * 
	 * @param userId
	 *            用户编号
	 * @return 用户信息
	 */
	@Transaction
	UserInfoVO gainUserInfo(int userId);

	/**
	 * 查询在线用户
	 * 
	 * @param userId
	 *            用户编号
	 * @return 在线用户
	 */
	User getExistUserCache(int userId);

	/**
	 * 查询用户是否存在
	 * 
	 * @param userId
	 *            用户编号
	 * @return 用户
	 */
	User getExistUser(int userId);

	/**
	 * 增加体力
	 * 
	 * @param user
	 *            用户
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	void incrementStamina(User user, int num, int type, String description);

	/**
	 * 减少体力
	 * 
	 * @param user
	 *            用户
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	void decrementStamina(User user, int num, int type, String description);

	/**
	 * 增加金币
	 * 
	 * @param user
	 *            用户
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	@Transaction
	void incrementGold(User user, int num, int type, String description);

	/**
	 * 减少金币
	 * 
	 * @param user
	 *            用户
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	void decrementGold(User user, int num, int type, String description);

	/**
	 * 增加积分
	 * 
	 * @param user
	 * @param num
	 * @param type
	 * @param description
	 */
	@Transaction
	void increPoint(User user, int num, int type, String description);

	/**
	 * 扣减积分
	 * 
	 * @param user
	 * @param num
	 * @param type
	 * @param description
	 */
	void decrementPoint(User user, int num, int type, String description);

	/**
	 * 增加荣誉值
	 * 
	 * @param user
	 * @param num
	 * @param type
	 * @param description
	 */
	@Transaction
	void increHonor(User user, int num, int type, String description);

	/**
	 * 扣减积分
	 * 
	 * @param user
	 * @param num
	 * @param type
	 * @param description
	 */
	void decrementHonor(User user, int num, int type, String description);

	/**
	 * 增加经验
	 * 
	 * @param user
	 *            用户
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	@Transaction
	void incrementExp(User user, int num, int type, String description, UserCap userCap);

	/**
	 * 增加货币
	 * 
	 * @param user
	 *            用户
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	@Transaction
	void incrementCurrency(User user, int num, int type, String description);

	/**
	 * 减少货币
	 * 
	 * @param user
	 *            用户
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	@Transaction
	void decrementCurrency(User user, int num, int type, String description);

	/**
	 * 获取用户规则
	 * 
	 * @return 用户规则
	 */
	List<UserRule> getUserRules();

	/**
	 * 重置用户信息
	 * 
	 * @param userId
	 *            用户编号
	 */
	@Transaction
	void resetUserStat(int userId);

	/**
	 * 增加友情点
	 * 
	 * @param userId
	 *            用户编号
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	@Transaction
	UserStat incrementFriendlyPoint(int userId, int num, int type, String description);

	/**
	 * 减少友情点
	 * 
	 * @param userId
	 *            用户编号
	 * @param num
	 *            数量
	 * @param type
	 *            类型
	 * @param description
	 *            描述
	 */
	@Transaction
	UserStat decrementFriendlyPoint(int userId, int num, int type, String description);

	/**
	 * 获得用户统计
	 * 
	 * @param userId
	 *            用户编号
	 * @return 用户统计
	 */
	UserStatVO gainUserStat(int userId);

	/**
	 * 检测体力恢复
	 * 
	 * @param user
	 *            用户
	 */
	void checkStamina(User user);

	/**
	 * 恢复体力
	 * 
	 * @param userId
	 *            用户编号
	 * @return 剩余金钱
	 */
	@Transaction
	int regainStamina(int userId);

	/**
	 * 零点重置统计，同时增加连续登录天数
	 * 
	 * @param userId
	 *            用户编号
	 */
	@Transaction
	void zeroResetUserStat(int userId);

	/**
	 * 起名
	 */
	@Transaction
	FightPropVO givePlayerName(String playerName, int userId);

	/**
	 * 新手战魂
	 */
	@Transaction
	GuideRetVO newbieSoul(int soulId, int userId);

	@Transaction
	GainAwardVO nextSetp(int nextStep, int userId);
	
	/**
	 * 非强制引导下一步
	 */
	@Transaction
	void nextInfoStep(int step, int userId);
	
	/**
	 * 引导下一步
	 * @param user
	 * @param nextStep
	 */
	@Transaction
	void nextStep(User user, int nextStep);

	/**
	 * boss后台查询用户信息
	 * 
	 * @param username
	 * @param partner
	 * @return
	 */
	User bossFindUserByUsername(String username, int partner);

	/**
	 * vip特权
	 * 
	 * @return
	 */
	public List<VipPrivilege> queryListVipPrivilege();

	/**
	 * vip周奖励
	 * 
	 * @return
	 */
	public List<VipWeekAward> queryListVipWeekAward();

	/**
	 * 购买扫荡次数
	 * 
	 * @param userId
	 *            用户编号
	 * @return 剩余金钱
	 */
	@Transaction
	int buySweepCount(int userId, int count);

	/**
	 * 充值升级vip奖励
	 * 
	 * @param user
	 * @param num
	 */
	@Transaction
	void rechargeVipAward(User user, int num);

	/**
	 * 购买成长基金
	 * 
	 * @param userId
	 * @return
	 */
	@Transaction
	int buyGrowthfund(int userId);

	/**
	 * 领取成长基金
	 * 
	 * @param userId
	 * @param grade
	 * @return
	 */
	@Transaction
	int getGrowthCurrency(int userId, int grade);

	/**
	 * 成长基金规则
	 * 
	 * @return
	 */
	List<GrowthFundRule> queryGrowthFundRule();

	/**
	 * 完成剧情任务
	 * 
	 * @return
	 */
	@Transaction
	void nextStoryMission(int storyMission, int userId);

	/**
	 * 充值
	 * 
	 * @param orderNo
	 *            订单号
	 * @param amount
	 *            充值金额
	 * @param gameCoin
	 *            充值游戏币
	 * @param userName
	 *            用户名
	 * @param partner
	 *            和桌上
	 * @param goodsId
	 *            商品类型编号 (0: 魂钻， 1： 黄金月卡， 2：钻石月卡)
	 */
	@Transaction
	void pay(String orderNo, int amount, int gameCoin, String userName, int partner, int goodsId);
	
	/**
	 * 订单返还
	 * 
	 * @param userName
	 * @param orderNo
	 * @param amount
	 * @param currency
	 */
	@Transaction
	void orderReturn(int partner, String userName, String orderNo, int currency, int extraCurrency);

	/**
	 * 免费送体力
	 * 
	 * @param userId
	 *            用户编号
	 * 
	 */
	@Transaction
	void sendRegainStamina(int userId);

	/**
	 * 查询玩家信息
	 * 
	 * @param filter
	 * @return
	 */
	public List<User> getUsers(UserFilter filter);

	/**
	 * 排行
	 * 
	 * @param rankTypeId
	 *            排行类型编号 (1: 等级排行， 2：推图（章节排行）)
	 * @return
	 */
	List<UserCapVO> userRank(int rankTypeId);

	/**
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
	 * 点金手
	 * 
	 * @param userId
	 * @return
	 */
	@Transaction
	CoinHandVO coinHand(int userId);

	/**
	 * 发放VIP周礼包
	 * 
	 * @param userStat
	 * @param vipGrade
	 *            VIP等级
	 */
	@Transaction
	void issueVipWeekAward(UserStat userStat, int vipGrade);

	/**
	 * 修改玩家信息
	 * 
	 * @param user
	 */
	@Transaction
	public void updateUser(User user);

	/**
	 * 获取玩家队长信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserCap getUserCap(int userId);
}