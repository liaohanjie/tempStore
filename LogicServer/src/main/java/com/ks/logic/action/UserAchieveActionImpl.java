/**
 * 
 */
package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.UserAchieveAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserAchieveService;
import com.ks.protocol.vo.achieve.AchieveAwardVO;
import com.ks.protocol.vo.achieve.UserAchieveVO;

/**
 * @author living.li
 * @date  2014年4月26日
 */
public class UserAchieveActionImpl implements UserAchieveAction {
	private static UserAchieveService service = ServiceFactory.getService(UserAchieveService.class);
	

	@Override
	public List<UserAchieveVO> getUserAchieve(int userId) {
		return service.queryUserAchieve(userId);
	}

	
	@Override
	public AchieveAwardVO getAchieveAward(int userId, int achieveId) {
		return service.getAchieveAward(userId, achieveId);
	}

}
