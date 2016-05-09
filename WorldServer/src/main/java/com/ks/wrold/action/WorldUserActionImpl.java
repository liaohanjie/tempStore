package com.ks.wrold.action;

import java.util.List;
import java.util.Map;

import com.ks.action.game.GameNotifiAction;
import com.ks.action.logic.AfficheAction;
import com.ks.action.logic.UserAction;
import com.ks.action.world.WorldUserAction;
import com.ks.app.Application;
import com.ks.model.affiche.Affiche;
import com.ks.model.filter.UserFilter;
import com.ks.model.user.User;
import com.ks.rpc.RPCKernel;
import com.ks.wrold.kernel.PlayerStaticInfo;
import com.ks.wrold.kernel.WorldServerCache;

public class WorldUserActionImpl implements WorldUserAction {

	@Override
	public User bossFindUserByUsername(String username, int partner) {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		return userAction.bossFindUserByUsername(username, partner);
	}

	@Override
	public void addAffiche(Affiche a) {
		AfficheAction afficheAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, AfficheAction.class);
		afficheAction.addAffiche(a);
	}

	@Override
	public void notifiUser(int userId, int nofitiType) {
		if (userId == 0) {
			try {
				for (Map.Entry<Integer, PlayerStaticInfo> entry : WorldServerCache.getPlayerStaticInfo().entrySet()) {
					GameNotifiAction gameNotifiAction = RPCKernel.getRemoteByServerId(entry.getValue().getGameServerId(), GameNotifiAction.class);
					gameNotifiAction.notifiPlayer(entry.getValue().getSessionId(), nofitiType);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			PlayerStaticInfo staticInfo = WorldServerCache.getPlayerStaticInfoByUserId(userId);
			if (staticInfo != null) {
				GameNotifiAction gameNotifiAction = RPCKernel.getRemoteByServerId(staticInfo.getGameServerId(), GameNotifiAction.class);
				gameNotifiAction.notifiPlayer(staticInfo.getSessionId(), nofitiType);
			}
		}
	}

	@Override
	public void logout(int userId) {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		userAction.logout(userId);
	}

//	@Override
//	public void logout(User user) {
//
//		PlayerModel model = PlayerModel.create(user.getPartner(), user.getUsername());
//		PlayerStaticInfo info = WorldServerCache.getPlayerStaticInfoByUsername(model);
//		if(info != null){
//			Player player;
//			player = new Player();
//			player.setUserId(user.getUserId());
//			player.setUsername(info.getUsername());
//			player.setPartner(user.getPartner());
//
//			LoginGameAction loginGameAction = RPCKernel.getRemoteByServerId(info.getGameServerId(), LoginGameAction.class);
//			loginGameAction.repeatLogin(info.getSessionId());
//
//			WorldServerCache.removePlayerStaticInfo(player);
//			WorldServerCache.decrementServerInfo(info.getGameServerId());
//		}
//		
//		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
//		userAction.logout(user.getUserId());
//	}

	@Override
	public User findUserById(int userId) {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		return userAction.findUserByUserId(userId);
	}

	@Override
	public List<User> getUsers(UserFilter filter) {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		return userAction.getUsers(filter);
	}

	@Override
	public List<Map<String, Object>> statisticsUserLevel() {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		return userAction.statisticsUserLevel();
	}

	@Override
	public List<Map<String, Object>> statisticsUserGuide() {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		return userAction.statisticsUserGuide();
	}

	@Override
	public void updateUser(User user) {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		userAction.updateUser(user);
	}

	@Override
	public void addOrder(String orderNo, int amount, int gameCoin, String username, int partner, int goodsId) {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		userAction.pay(orderNo, amount, gameCoin, username, partner, goodsId);
	}

	@Override
    public void orderReturn(int partner, String userName, String orderNo, int currency, int extraCurrency) {
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		userAction.orderReturn(partner, userName, orderNo, currency, extraCurrency);
    }
}
