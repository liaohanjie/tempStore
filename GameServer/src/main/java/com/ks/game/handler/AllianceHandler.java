package com.ks.game.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.ks.app.Application;
import com.ks.game.model.Player;
import com.ks.handler.GameHandler;
import com.ks.manager.ClientLockManager;
import com.ks.protocol.MainCmd;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.AllianceCMD;
import com.ks.protocol.vo.alliance.BuildingVO;
import com.ks.protocol.vo.alliance.BuyItemVO;
import com.ks.protocol.vo.alliance.ContributeVO;
import com.ks.protocol.vo.alliance.SimpleAllianceInfoVO;
import com.ks.protocol.vo.alliance.UserAllianceInfoVO;
import com.ks.util.LockKeyUtil;

/**
 * 工会
 * 
 * @author admin
 * 
 */
@MainCmd(mainCmd = MainCMD.ALLIANCE)
public class AllianceHandler extends ActionAdapter {

	/**
	 * 个人工会信息
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.GET_USERALLIANCE_INFO)
	public void getUserAllianceInfo(GameHandler handler) {

		Player player = handler.getPlayer();
		// 锁个人工会对象
		ClientLockManager.lock(LockKeyUtil.getUserAllianceLockKey(player.getUserId()));
		try {
			UserAllianceInfoVO userAllianceInfo = allianceAction().getUserAllianceInfo(player.getUserId());
			Application.sendMessage(handler.getChannel(), handler.getHead(), userAllianceInfo);
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}

	/**
	 * 创建工会
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.CREATE_ALLIANCE, args = { "String", "String" })
	public void createAlliance(GameHandler handler, String allianceName, String descs) {

		Player player = handler.getPlayer();
		// 锁个人工会对象
		ClientLockManager.lock(LockKeyUtil.getUserAllianceLockKey(player.getUserId()));
		try {
			UserAllianceInfoVO createAlliance = allianceAction().createAlliance(player.getUserId(), allianceName, descs);
			Application.sendMessage(handler.getChannel(), handler.getHead(), createAlliance);
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}

	/**
	 * 解散工会
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.DESTROY_ALLIANCE, args = { "int" })
	public void destroyAlliance(GameHandler handler, int allianceId) {

		Player player = handler.getPlayer();
		// 锁个人工会对象,还有工会成员对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getUserAllianceLockKey(player.getUserId()));
		keys.addAll(LockKeyUtil.getAllianceMemberLockKey(allianceId));
		ClientLockManager.lock(keys);
		try {
			allianceAction().destroyAlliance(player.getUserId(), allianceId);
			Application.sendMessage(handler.getChannel(), handler.getHead());
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}

	/**
	 * 申请加入工会
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.APPLY_ALLIANCE, args = { "int" })
	public void apply2Alliance(GameHandler handler, int allianceId) {
		allianceAction().apply2Alliance(handler.getPlayer().getUserId(), allianceId);
		Application.sendMessage(handler.getChannel(), handler.getHead());
	}

	/**
	 * 同意加入工会申请
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.AGREE_APPLY, args = { "int", "int" })
	public void agreeApply(GameHandler handler, int allianceId, int applyerId) {

		// 锁个人工会对象,还有工会成员对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getUserAllianceLockKey(applyerId));
		keys.addAll(LockKeyUtil.getAllianceMemberLockKey(allianceId));
		ClientLockManager.lock(keys);
		try {
			allianceAction().agreeApply(handler.getPlayer().getUserId(), allianceId, applyerId);
			Application.sendMessage(handler.getChannel(), handler.getHead());
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}

	/**
	 * 退出工会
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.QUIT_ALLIANCE, args = { "int" })
	public void quitAlliance(GameHandler handler, int allianceId) {
		
		Player player = handler.getPlayer();
		// 锁个人工会对象,还有工会成员对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getUserAllianceLockKey(player.getUserId()));
		keys.addAll(LockKeyUtil.getAllianceMemberLockKey(allianceId));
		ClientLockManager.lock(keys);
		try {
			allianceAction().quitAlliance(handler.getPlayer().getUserId(), allianceId);
			Application.sendMessage(handler.getChannel(), handler.getHead());
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}

	/**
	 * 查看所有工会列表
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.LIST_ALL_ALLIANCE)
	public void listAllianceInfos(GameHandler handler) {
		List<SimpleAllianceInfoVO> listAllianceInfos = allianceAction().listAllianceInfos(handler.getPlayer().getUserId());
		Application.sendMessage(handler.getChannel(), handler.getHead(), listAllianceInfos);
	}
	
	/**
	 * 更新公告
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.UPDATE_NOTICE, args = {"int", "String" })
	public void updateNotice(GameHandler handler, int allianceId, String notice) {
		// 锁工会对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getAllianceLockKey(allianceId));
		ClientLockManager.lock(keys);
		try {
			allianceAction().updateNotice(handler.getPlayer().getUserId(), allianceId, notice);
			Application.sendMessage(handler.getChannel(), handler.getHead());
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}
	
	
	/**
	 * 更新描述
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.UPDATE_DESC, args = {"int", "String" })
	public void updateDesc(GameHandler handler, int allianceId, String desc) {
		// 锁工会对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getAllianceLockKey(allianceId));
		ClientLockManager.lock(keys);
		try {
			allianceAction().updateDesc(handler.getPlayer().getUserId(), allianceId, desc);
			Application.sendMessage(handler.getChannel(), handler.getHead());
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}
	
	
	/**
	 * 踢玩家
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.KICK_MEMBER, args = { "int", "int" })
	public void kickMember(GameHandler handler, int allianceId, int kickId) {

		// 锁个人工会对象,还有工会成员对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getUserAllianceLockKey(kickId));
		keys.addAll(LockKeyUtil.getAllianceMemberLockKey(allianceId));
		ClientLockManager.lock(keys);
		try {
			allianceAction().kickMember(handler.getPlayer().getUserId(), allianceId, kickId);
			Application.sendMessage(handler.getChannel(), handler.getHead());
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}
	
	/**
	 * 建设
	 * 
	 * @param handler
	 */
	@SubCmd(subCmd = AllianceCMD.BUILDING, args = { "int", "byte" })
	public void building(GameHandler handler, int allianceId, byte type) {
		
		Player player = handler.getPlayer();

		// 锁个人工会对象,还有工会对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getAllianceLockKey(allianceId));
		keys.addAll(LockKeyUtil.getUserAllianceLockKey(player.getUserId()));
		ClientLockManager.lock(keys);
		try {
			BuildingVO building = allianceAction().building(player.getUserId(), allianceId, type);
			Application.sendMessage(handler.getChannel(), handler.getHead(), building);
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}
	
	/**
	 * 捐献战魂
	 * @param userId
	 * @param allianceId
	 * 
	 */
	@SubCmd(subCmd = AllianceCMD.CONTRIBUTE_SOUL, args = {"int", "long_true" })
	public void contributeSoul(GameHandler handler, int allianceId, List<Long> userSoulIds){
		
		Set<Long> userSoulIdsSet = new HashSet<>();
		for(long id : userSoulIds){
			userSoulIdsSet.add(id);
		}
		Player player = handler.getPlayer();

		// 锁个人工会对象,还有工会对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getAllianceLockKey(allianceId));
		keys.addAll(LockKeyUtil.getUserAllianceLockKey(player.getUserId()));
		ClientLockManager.lock(keys);
		try {
			ContributeVO contributeSoul = allianceAction().contributeSoul(player.getUserId(), allianceId, userSoulIdsSet);
			Application.sendMessage(handler.getChannel(), handler.getHead(), contributeSoul);
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}
	
	/**
	 * 捐献材料
	 * @param userId
	 * @param allianceId
	 * 
	 */
	@SubCmd(subCmd = AllianceCMD.CONTRIBUTE_STUFF, args = { "int", "int", "int" })
	public void contributeStuff(GameHandler handler, int allianceId, int goodsId, int num){
		Player player = handler.getPlayer();

		// 锁个人工会对象,还有工会对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getAllianceLockKey(allianceId));
		keys.addAll(LockKeyUtil.getUserAllianceLockKey(player.getUserId()));
		ClientLockManager.lock(keys);
		try {
			ContributeVO contributeStuff = allianceAction().contributeStuff(player.getUserId(), allianceId, goodsId, num);
			Application.sendMessage(handler.getChannel(), handler.getHead(), contributeStuff);
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}
	
	/**
	 * 升级工会
	 * @param userId
	 * @param allianceId
	 * 
	 */
	@SubCmd(subCmd = AllianceCMD.UPGRADE_ALLIANCE, args = { "int" })
	public void upgradeAlliance(GameHandler handler, int allianceId){
		Player player = handler.getPlayer();

		// 锁个人工会对象,还有工会对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getAllianceLockKey(allianceId));
		ClientLockManager.lock(keys);
		try {
			allianceAction().upgradeAlliance(player.getUserId(), allianceId);
			Application.sendMessage(handler.getChannel(), handler.getHead());
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}
	
	/**
	 * 购买工会道具
	 * @param userId
	 * @param allianceId
	 * 
	 */
	@SubCmd(subCmd = AllianceCMD.BUY_SHOP_ITEM, args = { "int" })
	public void buyItem(GameHandler handler, int itemId){
		Player player = handler.getPlayer();

		// 锁个人工会对象,还有工会对象
		List<String> keys = new ArrayList<String>();
		keys.addAll(LockKeyUtil.getUserAllianceLockKey(player.getUserId()));
		ClientLockManager.lock(keys);
		try {
			BuyItemVO buyItem = allianceAction().buyItem(player.getUserId(), itemId);
			Application.sendMessage(handler.getChannel(), handler.getHead(), buyItem);
		} finally {
			ClientLockManager.unlockThreadLock();
		}
	}
}
