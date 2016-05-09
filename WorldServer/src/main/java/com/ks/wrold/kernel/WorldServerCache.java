package com.ks.wrold.kernel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.ks.app.Application;
import com.ks.exceptions.GameException;
import com.ks.game.model.Player;
import com.ks.game.model.PlayerModel;
import com.ks.logger.LoggerFactory;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.ServerInfo;
/**
 * 世界服务器缓存
 * @author ks
 *
 */
public final class WorldServerCache implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.get(WorldServerCache.class);
	
	private static List<ServerInfo> gameServerInfos = new ArrayList<ServerInfo>();
	private static final Lock serverInfoLock = new ReentrantLock();
	private static final Lock serverLock = new ReentrantLock();
	private static List<ServerInfo> logicServerInfos = new ArrayList<ServerInfo>();
	
	/**用户静态数据ID映射*/
	private static Map<Integer,PlayerStaticInfo> playerStaticInfoIdMap = new ConcurrentHashMap<>();
	/**用户静态数据用户名映射*/
	private static Map<PlayerModel,PlayerStaticInfo> playerStaticInfoNameMap = new ConcurrentHashMap<>();
	
	/**用户名锁映射*/
	private static final Map<PlayerModel , Lock> NAME_LOCK_MAP = new ConcurrentHashMap<>();
	/**名称锁*/
	private static final Lock NAME_LOCK = new ReentrantLock();
	/**用户编号锁*/
	private static final Map<Integer,Lock> USER_ID_LOCKS = new ConcurrentHashMap<>();
	
	public static void serverLock(){
		serverLock.lock();
	}
	public static void serverUnlock(){
		serverLock.unlock();
	}
	
	public static final Lock getPlayerLock(PlayerModel model){
		Lock lock = NAME_LOCK_MAP.get(model);
		if(lock==null){
			lock = newNameLock(model);
		}
		return lock;
	}
	
	private static final Lock newNameLock(PlayerModel model){
		NAME_LOCK.lock();
		try{
			Lock lock = NAME_LOCK_MAP.get(model);
			if(lock==null){
				lock = new ReentrantLock();
				NAME_LOCK_MAP.put(model, lock);
			}
			return lock;
		}finally{
			NAME_LOCK.unlock();
		}
	}
	/**
	 * 增加游戏服务器服务器
	 * @return
	 */
	public static void addGameServerInfo(ServerInfo serverInfo){
		serverInfoLock.lock();
		try{
			gameServerInfos.add(serverInfo);
			Collections.sort(gameServerInfos,serverInfo);
		}finally{
			serverInfoLock.unlock();
		}
	}
	
	
	public static void incrementGameServerInfo(String serverId){
		serverInfoLock.lock();
		try{
			int num=0;
			ServerInfo info = null;
			for(ServerInfo serverInfo : gameServerInfos){
				if(serverInfo.getServerId().equals(serverId)){
					info = serverInfo;
					info.setNum(info.getNum()+1);
					logger.info("游戏服务器："+info.getServerId()+"增加人数" + info.getNum());
				}
				num += info.getNum();
			}
//			RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class).sendOnlinePlayerNum(serverId, num);
			if(info!=null){
				Collections.sort(gameServerInfos,info);
			}
		}finally{
			serverInfoLock.unlock();
		}
	}
	
	/**
	 * 获得服务器中人数较少的服务器
	 * @return
	 */
	public static ServerInfo gainGameServerInfo(){
		serverInfoLock.lock();
		try{
			for(ServerInfo info : gameServerInfos){
				try{
					if(RPCKernel.getRPCClientById(info.getServerId()).canUse()){
						return info;
					}
				}catch(Exception e){
					continue;
				}
			}
			throw new RuntimeException("can't find can use game server");
		}finally{
			serverInfoLock.unlock();
		}
	}
	/**
	 * 减少游戏服务器人数
	 */
	public static void  decrementServerInfo(String serverId){
		serverInfoLock.lock();
		try{
			int num=0;
			ServerInfo info = null;
			for(ServerInfo serverInfo : gameServerInfos){
				if(serverInfo.getServerId().equals(serverId)){
					info = serverInfo;
					serverInfo.setNum(serverInfo.getNum()-1);
					logger.info("游戏服务器："+serverId+"减少人数" + serverInfo.getNum());
				}
				num+=serverInfo.getNum();
			}
//			RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class).sendOnlinePlayerNum(serverId, num);
			if(info!=null){
				Collections.sort(gameServerInfos,info);
			}
		}finally{
			serverInfoLock.unlock();
		}
	}
	
	public static List<ServerInfo> getGameServerInfos() {
		return gameServerInfos;
	}
	
	public static List<ServerInfo> getLogicServerInfos() {
		return logicServerInfos;
	}
	
	public static final void processGameServer(GameServerProcess gameProcess) throws Exception{
		serverInfoLock.lock();
		try{
			gameProcess.process(gameServerInfos);
		}finally{
			serverInfoLock.unlock();
		}
	}
	/**
	 * 增加用户静态数据
	 * @param player 要增加的玩家
	 * @param gameServerId 玩家所在游戏服务器编号
	 */
	public static final void addPlayerStaticInfo(Player player,String gameServerId,long sessionId){
		PlayerStaticInfo playerInfo = new PlayerStaticInfo();
		playerInfo.setGameServerId(gameServerId);
		playerInfo.setUserId(player.getUserId());
		playerInfo.setUsername(player.getUsername());
		playerInfo.setPartner(player.getPartner());
		playerInfo.setSessionId(sessionId);
		playerStaticInfoIdMap.put(playerInfo.getUserId(), playerInfo);
		PlayerModel model = PlayerModel.create(player.getPartner(), player.getUsername());
		playerStaticInfoNameMap.put(model, playerInfo);
	}
	/**
	 * 删除玩家静态数据
	 * @param player 要删除的玩家
	 */
	public static final PlayerStaticInfo removePlayerStaticInfo(Player player){
		playerStaticInfoIdMap.remove(player.getUserId());
		PlayerModel model = PlayerModel.create(player.getPartner(), player.getUsername());
		return playerStaticInfoNameMap.remove(model);
	}
	
	/**
	 * 根据用户ID查找用户静态数据
	 * @param userId 用户编号
	 * @return 用户编号下的用户
	 */
	public static final PlayerStaticInfo getPlayerStaticInfoByUserId(int userId){
		return playerStaticInfoIdMap.get(userId);
	}
	
	/**
	 * 获得在线玩家信息
	 * @return
	 */
	public static Map<Integer,PlayerStaticInfo> getPlayerStaticInfo(){
		return playerStaticInfoIdMap;
	}
	
	/**
	 * 根据用户名查找用户静态数据
	 * @param username 用户名
	 * @return 查找到的用户静态数据
	 */
	public static final PlayerStaticInfo getPlayerStaticInfoByUsername(PlayerModel model){
		return playerStaticInfoNameMap.get(model);
	}
	
	public static final ServerInfo removeLogicServerInfo(String serverId){
		serverInfoLock.lock();
		try{
			ServerInfo info = null;
			for(ServerInfo si : logicServerInfos){
				if(si.getServerId().equals(serverId)){
					info = si;
					break;
				}
			}
			if(info!=null){
				logicServerInfos.remove(info);
			}
			return info;
		}finally{
			serverInfoLock.unlock();
		}
	}
	
	/**
	 * 删除游戏服务器
	 * @param serverId 游戏服务器编号
	 * @return 游戏服务器信息
	 */
	public static final ServerInfo removeGameServerInfo(String serverId){
		serverInfoLock.lock();
		try{
			ServerInfo info = null;
			for(ServerInfo si : gameServerInfos){
				if(si.getServerId().equals(serverId)){
					info = si;
					break;
				}
			}
			if(info!=null){
				gameServerInfos.remove(info);
			}
			return info;
		}finally{
			serverInfoLock.unlock();
		}
	}
	
	/**
	 * 获得游戏服务器
	 * @param serverId 游戏服务器编号
	 * @return 游戏服务器信息
	 */
	public static final ServerInfo getGameServerInfo(String serverId){
		serverInfoLock.lock();
		try{
			ServerInfo info = null;
			for(ServerInfo si : gameServerInfos){
				if(si.getServerId().equals(serverId)){
					info = si;
					break;
				}
			}
			return info;
		}finally{
			serverInfoLock.unlock();
		}
	}
	
	/**
	 * 获得服务器中所有玩家
	 * @param serverId 服务器编号
	 * @return 这个服务器的所有玩家
	 */
	public static final List<PlayerStaticInfo> getServerPlayers(String serverId){
		List<PlayerStaticInfo> infos = new ArrayList<>();
		for(PlayerStaticInfo info : playerStaticInfoIdMap.values()){
			if(info.getGameServerId().equals(serverId)){
				infos.add(info);
			}
		}
		return infos;
	}
	/**
	 * 
	 * @param userId
	 * @return
	 */
	public static final Lock getUserIdLock(int userId){
		Lock lock = USER_ID_LOCKS.get(userId);
		if(lock==null){
			synchronized (WorldServerCache.class) {
				if(lock==null){
					lock = new ReentrantLock();
					USER_ID_LOCKS.put(userId, lock);
				}
				lock = USER_ID_LOCKS.get(userId);
			}
		}
		return lock;
	}
	public synchronized static void checkOnlinePlayer() {
		ServerInfo server = null;
		for(ServerInfo info : gameServerInfos){
			if(info.getNum()<Application.MAX_ONLINE_PLAYER){
				return;
			}
			server = info;
		}
		
		if (server == null) {
			throw new GameException(GameException.CODE_服务器人数已达上限, "game server is not started.");
		}
		throw new GameException(GameException.CODE_服务器人数已达上限, String.format("beyond server top limit player's count. serverId=%s num=%s",  server.getServerId(), server.getNum()));
	}
	public static List<ServerInfo> gainAllGameServer(){
		return gameServerInfos;
	}
}
