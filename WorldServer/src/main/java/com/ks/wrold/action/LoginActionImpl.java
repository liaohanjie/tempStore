package com.ks.wrold.action;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.locks.Lock;

import com.ks.action.game.LoginGameAction;
import com.ks.action.logic.PlayerAction;
import com.ks.action.world.LoginAction;
import com.ks.app.Application;
import com.ks.game.model.Player;
import com.ks.game.model.PlayerModel;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.login.LoginResultVO;
import com.ks.protocol.vo.login.LoginVO;
import com.ks.protocol.vo.login.RegisterVO;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.ServerInfo;
import com.ks.wrold.kernel.PlayerStaticInfo;
import com.ks.wrold.kernel.WorldServerCache;

public final class LoginActionImpl implements
		LoginAction {

	@Override
	public LoginResultVO userLogin(LoginVO loginVO) {
		PlayerModel model = PlayerModel.create(loginVO.getPartner(), loginVO.getUsername());
		Lock playerLock = WorldServerCache.getPlayerLock(model);
		playerLock.lock();
		try{
			PlayerStaticInfo info = WorldServerCache.getPlayerStaticInfoByUsername(model);
			LoginResultVO vo;
			Player player;
			if(info!=null){//重复登录
				vo = MessageFactory.getMessage(LoginResultVO.class);
				vo.setUserId(info.getUserId());
				
				player = new Player();
				player.setUserId(vo.getUserId());
				player.setUsername(info.getUsername());
				player.setPartner(loginVO.getPartner());
				
				LoginGameAction loginGameAction = RPCKernel.getRemoteByServerId(info.getGameServerId(), LoginGameAction.class);
				loginGameAction.repeatLogin(info.getSessionId());
				
				WorldServerCache.removePlayerStaticInfo(player);
				WorldServerCache.decrementServerInfo(info.getGameServerId());
			}else{
				
				WorldServerCache.checkOnlinePlayer();
				
				PlayerAction playerAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, PlayerAction.class);
				vo = playerAction.userLogin(loginVO);
			}
			if(vo.getUserId()!=0){
				vo.setSessionId(UUID.randomUUID().getLeastSignificantBits());
				ServerInfo gameServerInfo = WorldServerCache.gainGameServerInfo();
				vo.setGameServerHost(gameServerInfo.getHost());
				vo.setGameServerPort(gameServerInfo.getPort());
				
				player = new Player();
				player.setUserId(vo.getUserId());
				player.setPartner(loginVO.getPartner());
				player.setUsername(loginVO.getUsername());
				player.setLastHeartTime(new Date());
				player.setSessionId(vo.getSessionId());
				player.setPartner(model.getPartner());
				
				LoginGameAction loginGameAction = RPCKernel.getRemoteByServerId(gameServerInfo.getServerId(), LoginGameAction.class);
				loginGameAction.addOnlinePlayer(vo.getSessionId(), player);
				
				WorldServerCache.incrementGameServerInfo(gameServerInfo.getServerId());
				WorldServerCache.addPlayerStaticInfo(player, gameServerInfo.getServerId(),vo.getSessionId());
			}
			return vo;
		}finally{
			playerLock.unlock();
		}
	}

	@Override
	public void logout(Player player) {
		PlayerStaticInfo info = WorldServerCache.getPlayerStaticInfoByUserId(player.getUserId());
		if(info!=null){
			WorldServerCache.removePlayerStaticInfo(player);
			WorldServerCache.decrementServerInfo(info.getGameServerId());
		}
	}

	@Override
	public void userRegister(RegisterVO register) {
//		if(true){
//			throw new GameException(GameException.CODE_参数错误, "user not found");
//		}
		PlayerAction playerAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, PlayerAction.class);
		playerAction.userRegister(register);
	}
}