/**
 * 
 */
package com.ks.action.logic;

import java.util.List;

import com.ks.protocol.vo.achieve.AchieveAwardVO;
import com.ks.protocol.vo.achieve.UserAchieveVO;

/**
 * @author living.li
 * @date  2014年4月26日
 */
public interface UserAchieveAction {

	/**
	 * 查看我的成就
	 * @param userId
	 * @return
	 */
	List<UserAchieveVO> getUserAchieve(int userId);
	/**
	 * 领取成就奖励
	 * @param userId
	 * @param achieveId
	 * @return
	 */
	AchieveAwardVO getAchieveAward(int userId,int achieveId);
}
