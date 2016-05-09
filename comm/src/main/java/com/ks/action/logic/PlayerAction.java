package com.ks.action.logic;

import java.util.List;

import com.ks.protocol.vo.goods.FightPropVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.GuideRetVO;
import com.ks.protocol.vo.login.LoginResultVO;
import com.ks.protocol.vo.login.LoginVO;
import com.ks.protocol.vo.login.RegisterVO;
import com.ks.protocol.vo.user.CoinHandVO;
import com.ks.protocol.vo.user.UserBuffVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.protocol.vo.user.UserInfoVO;
import com.ks.protocol.vo.user.UserStatVO;

/**
 * 
 * @author ks
 *
 */
public interface PlayerAction{
	
	/**
	 * 用户登录
	 * @param login 登陆信息
	 * @return 用户登录结果
	 */
	LoginResultVO userLogin(LoginVO login);
	/**
	 * 用户下线
	 * @param userId 用户编号
	 */
	void logout(int userId);
	/**
	 * 用户注册
	 * @param register 注册信息
	 */
	void userRegister(RegisterVO register);
	/**
	 * 获取用户信息
	 * @param userId 用户编号
	 * @return 用户信息
	 */
	UserInfoVO gainUserInfo(int userId);
	
	/**
	 * 获得用户统计
	 * @param userId 用户编号
	 * @return 用户统计
	 */
	UserStatVO gainUserStat(int userId);
	/**
	 * 恢复体力
	 * @param userId 用户编号
	 * @return 剩余金钱
	 */
	int regainStamina(int userId);
	/**
	 * 零点重置stat
	 * @param userId 用户编号
	 */
	void zeroResetUserStat(int userId);
	
	/**
	 * 起步
	 * @param playerName
	 * @param userId
	 */
	FightPropVO givePlayerName(String playerName,int userId);
	/**
	 * 新手战魂
	 */
	GuideRetVO newbieSoul(int soulId,int userId);	
	/**
	 * 下一步
	 * @param nextStep
	 * @param userId
	 */
	GainAwardVO nextSetp(int nextStep,int userId);
	/**
	 * 用户buff
	 * @param userId
	 * @return
	 */
	List<UserBuffVO> gainUserBuff(int userId);
	
	/**
	 * 购买扫荡次数
	 * @param userId 用户编号
	 * @return 剩余金钱
	 */
	int  buySweepCount(int userId, int count);
	
	/**
	 * 购买成长基金
	 * @param userid
	 * @return
	 */
	int buyGrowthfund(int userId);
	
	int getGrowthCurrency(int userId,int grade);
	
	/**
	 * 下一个剧情任务
	 * @param nextStory
	 * @param userId
	 */
	void nextStoryMission(int nextStory,int userId);
	/**
	 * 非强制引导下一步
	 */
	void nextInfoStep(int step,int userId);
	
	/**
	 * 充值加金币
	 * @param amount
	 */
	void pay(int amount,String userName,int partner);
	/**
	 * 免费领取体力
	 * @param 
	 */
	public void sendRegainStamina(int userId);
	
	/**
	 * 排行
	 * @param rankTypeId 排行类型 1: 等级
	 */
	List<UserCapVO> userRank(int rankTypeId);
	
	
	/**
	 * 点金手
	 * @param userId
	 * @return
	 */
	CoinHandVO coinHand(int userId);
}