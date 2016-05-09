package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.PlayerAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserBuffService;
import com.ks.logic.service.UserService;
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

public final class PlayerActionImpl implements
		PlayerAction {
	
	private static UserService userService = ServiceFactory.getService(UserService.class);
	private static UserBuffService userBuffService = ServiceFactory.getService(UserBuffService.class);
	@Override
	public LoginResultVO userLogin(LoginVO login) {
		return userService.userLogin(login);
	}

	@Override
	public void logout(int userId) {
		userService.logout(userId);
	}

	@Override
	public void userRegister(RegisterVO register) {
		userService.userRegister(register);
	}

	@Override
	public UserInfoVO gainUserInfo(int userId) {
		return userService.gainUserInfo(userId);
	}

	@Override
	public UserStatVO gainUserStat(int userId) {
		return userService.gainUserStat(userId);
	}

	@Override
	public int regainStamina(int userId) {
		return userService.regainStamina(userId);
	}

	@Override
	public void zeroResetUserStat(int userId) {
		userService.zeroResetUserStat(userId);
	}

	@Override
	public FightPropVO givePlayerName(String playerName, int userId) {
		return userService.givePlayerName(playerName, userId);
	}

	@Override
	public GuideRetVO newbieSoul(int soulId, int userId) {
		return userService.newbieSoul(soulId, userId);
	}

	@Override
	public GainAwardVO nextSetp(int nextStep, int userId) {
		return userService.nextSetp(nextStep, userId);
	}

	@Override
	public List<UserBuffVO> gainUserBuff(int userId) {
		return userBuffService.gainUserBuff(userId);
	}

	@Override
	public int buySweepCount(int userId, int count) {
		return userService.buySweepCount(userId, count);
	}

	@Override
	public int buyGrowthfund(int userId) {
		return userService.buyGrowthfund(userId);
	}

	@Override
	public int getGrowthCurrency(int userId, int grade) {
		return userService.getGrowthCurrency(userId, grade);
	}

	@Override
	public void nextStoryMission(int nextStory, int userId) {
		userService.nextStoryMission(nextStory, userId);
	}

	@Override
	public void nextInfoStep(int step, int userId) {
		userService.nextInfoStep(step, userId);
	}

	@Override
	public void pay(int amount,String userName,int partner){
		
	}

	@Override
	public void sendRegainStamina(int userId) {
		userService.sendRegainStamina(userId);
	}

	@Override
    public List<UserCapVO> userRank(int rankTypeId) {
	    return userService.userRank(rankTypeId);
    }

	@Override
    public CoinHandVO coinHand(int userId) {
		return userService.coinHand(userId);
    }
}
