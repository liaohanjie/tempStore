package com.ks.wrold.action;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

import org.apache.log4j.Logger;

import com.ks.action.game.GameServerAction;
import com.ks.action.logic.PlayerAction;
import com.ks.action.world.WorldServerAction;
import com.ks.app.Application;
import com.ks.game.model.Player;
import com.ks.game.model.PlayerModel;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.ServerInfo;
import com.ks.rpc.client.ClientRPCHandler;
import com.ks.rpc.client.RPCClient;
import com.ks.wrold.kernel.PlayerStaticInfo;
import com.ks.wrold.kernel.WorldServerCache;
/**
 * 世界服务器处理,处理服务器间的一些初始化,连接问题
 * @author ks
 *
 */
public final class WorldServerActionImpl implements
		WorldServerAction {
	private static final Logger logger = LoggerFactory.get(WorldServerAction.class);
	@Override
	public void gameServerConnected(final String host,final  int port,final  String rpcHost,
			final int rpcPort,final String serverId) throws Exception {
		WorldServerCache.serverLock();
		try{
			logger.info("game server connected……");
			clearGameServerPlayers(serverId);
			ServerInfo serverInfo = new ServerInfo(host, port,serverId);
			WorldServerCache.addGameServerInfo(serverInfo);
			
			RPCClient client = new RPCClient(new InetSocketAddress(rpcHost,
					rpcPort), serverId, Application.GAME_SERVER);
			RPCKernel.addRPCClient(client);
			
			Map<String,Class<?>> map = Application.RPC_CLIENT_MAPPER.get(Application.GAME_SERVER);
			for(Map.Entry<String, Class<?>> e : map.entrySet()){
				RPCKernel.addServerIDRemote(serverId,
						e.getValue(), Proxy.newProxyInstance(e.getValue().
								getClassLoader(), new Class<?>[]{e.getValue()}, 
								new ClientRPCHandler(e.getKey(), RPCKernel.FLAG_ID, 
										0,serverId)));
			}
			RPCKernel.getRemoteByServerId(serverInfo.getServerId(),GameServerAction.class).addLogicServerRPC(WorldServerCache.getLogicServerInfos(),serverId);
		}finally{
			WorldServerCache.serverUnlock();
		}
	}

	public void clearGameServerPlayers(final String serverId) {
		logger.info("clear "+serverId + " players...");
		ServerInfo serverInfo = WorldServerCache.removeGameServerInfo(serverId);
		if(serverInfo!=null){
			List<PlayerStaticInfo> staticInfos = WorldServerCache.getServerPlayers(serverId);
			synchronized (serverInfo) {
				for(PlayerStaticInfo info : staticInfos){
					PlayerModel model = PlayerModel.create(info.getPartner(), info.getUsername());
					Lock lock = WorldServerCache.getPlayerLock(model);
					lock.lock();
					try{
						if(info.getUserId()!=0){
							Player player = new Player();
							player.setUserId(info.getUserId());
							player.setPartner(info.getPartner());
							player.setUsername(info.getUsername());
							player.setSessionId(info.getSessionId());
							WorldServerCache.removePlayerStaticInfo(player);
							PlayerAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, PlayerAction.class);
							action.logout(player.getUserId());
						}
					}catch(Exception e){
						logger.error("", e);
						continue;
					}finally{
						lock.unlock();
					}
				}
				staticInfos.clear();
			}
		}
	}

	@Override
	public void logicServerConndeted(final String host, final int port,final String serverId) throws Exception {
		WorldServerCache.serverLock();
		try{
			logger.info("logic server connected……");
			final ServerInfo logicServerInfo = new ServerInfo(host, port,serverId);
			WorldServerCache.getLogicServerInfos().add(logicServerInfo);
			WorldServerCache.processGameServer(new com.ks.wrold.kernel.GameServerProcess() {
				@Override
				public void process(List<ServerInfo> serveInfos) throws Exception {
					for(ServerInfo serverInfo : serveInfos){
						try{
							RPCKernel.getRemoteByServerId(serverInfo.getServerId(), GameServerAction.class).addLogicServerRPC(logicServerInfo);
						}catch(Exception e){
							continue;
						}
					}
				}
			});
			
			RPCClient client = new RPCClient(new InetSocketAddress(host,
					port), logicServerInfo.getServerId(), Application.LOGIC_SERVER);
			RPCKernel.addRPCClient(client);
			Map<String,Class<?>> map = Application.RPC_CLIENT_MAPPER.get(Application.GAME_SERVER);
			for(Map.Entry<String, Class<?>> e : map.entrySet()){
				RPCKernel.addServerIDRemote( logicServerInfo.getServerId(),
						e.getValue(), Proxy.newProxyInstance(e.getValue().
								getClassLoader(), new Class<?>[]{e.getValue()}, 
								new ClientRPCHandler(e.getKey(), RPCKernel.FLAG_ID, 
										0,logicServerInfo.getServerId())));
			}
		}finally{
			WorldServerCache.serverUnlock();
		}
	}
}