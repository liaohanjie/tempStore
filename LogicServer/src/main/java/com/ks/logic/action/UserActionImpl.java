package com.ks.logic.action;

import java.util.List;
import java.util.Map;

import com.ks.action.logic.UserAction;
import com.ks.logic.event.DataOnlineEvent;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserService;
import com.ks.model.filter.UserFilter;
import com.ks.model.user.User;
import com.ks.timer.TimerController;

public class UserActionImpl implements UserAction {

	private static final UserService userService = ServiceFactory.getService(UserService.class);

	@Override
	public User bossFindUserByUsername(String username, int partner) {
		return userService.bossFindUserByUsername(username, partner);
	}

	@Override
	public void pay(String orderNo, int amount, int gameCoin, String userName, int partner, int goodsId) {
		userService.pay(orderNo, amount, gameCoin, userName, partner, goodsId);
	}

	@Override
	public void logout(int userId) {
		userService.logout(userId);
	}

	@Override
	public User findUserByUserId(int userId) {
		return userService.getExistUser(userId);
	}

	@Override
	public List<User> getUsers(UserFilter filter) {
		return userService.getUsers(filter);
	}

	@Override
	public void sendOnlinePlayerNum(String serverId, int num) {
		DataOnlineEvent event = new DataOnlineEvent(null, num);
		TimerController.execEvent(event);
	}

	@Override
	public List<Map<String, Object>> statisticsUserLevel() {
		return userService.statisticsUserLevel();
	}

	@Override
    public void updateUser(User user) {
		userService.updateUser(user);	    
    }

	@Override
    public List<Map<String, Object>> statisticsUserGuide() {
	    return userService.statisticsUserGuide();
    }

	@Override
    public void orderReturn(int partner, String userName, String orderNo, int currency, int extraCurrency) {
		userService.orderReturn(partner, userName, orderNo, currency, extraCurrency);
    }

}
