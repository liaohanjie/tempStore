package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.achieve.Achieve;
import com.ks.model.achieve.AchieveAward;
import com.ks.model.achieve.UserAchieve;
import com.ks.protocol.vo.achieve.AchieveAwardVO;
import com.ks.protocol.vo.achieve.UserAchieveVO;

public interface UserAchieveService  {
	
	public List<Achieve> getAchieveRule();

	public List<AchieveAward> getAllAchieveAward();

	/**
	 * 查看用户的成就
	 * @param userId
	 * @return
	 */
	public List<UserAchieveVO> queryUserAchieve(int userId);

	@Transaction
	public void addUserAchieveBatch(int userId, List<UserAchieve> mapList);
	
	/**
	 * 增加进度 会自动达成 会自动找到一个目标
	 * @param userId
	 * @param type
	 * @param assId
	 * @param addNum  增加的进度值
	 */
	@Transaction
	public void addUserAchieveProcess(int userId,int type,int assId,int addNum);
	
	/**
	 * 给用户达成一个成就
	 * @param userId
	 * @param achieveId
	 */
	@Transaction
	public void takeAchieve(int userId,int achieveId);
	
	/**
	 * 领取成就奖励
	 * @param userId
	 * @param achieveId
	 * @return
	 * @throws Exception
	 */
	@Transaction
	public AchieveAwardVO getAchieveAward(int userId,int achieveId);

	
}
