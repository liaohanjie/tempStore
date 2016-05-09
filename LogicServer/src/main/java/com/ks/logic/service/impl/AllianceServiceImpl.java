package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.AllianceService;
import com.ks.logic.service.BaseService;
import com.ks.model.alliance.Alliance;
import com.ks.model.alliance.AllianceMembers;
import com.ks.model.alliance.AllianceSetting;
import com.ks.model.alliance.AllianceShopItem;
import com.ks.model.alliance.UserAlliance;
import com.ks.model.alliance.constant.AllianceConfig;
import com.ks.model.alliance.constant.RoleType;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.logger.LoggerType;
import com.ks.model.soul.Soul;
import com.ks.model.stuff.Stuff;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserSoul;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.alliance.AllianceInfoVO;
import com.ks.protocol.vo.alliance.BuildingVO;
import com.ks.protocol.vo.alliance.BuyItemVO;
import com.ks.protocol.vo.alliance.ContributeVO;
import com.ks.protocol.vo.alliance.SimpleAllianceInfoVO;
import com.ks.protocol.vo.alliance.UserAllianceInfoVO;
import com.ks.protocol.vo.alliance.AllianceMemberVO;
import com.ks.protocol.vo.alliance.ApplyUserInfoVO;
import com.ks.protocol.vo.mission.UserAwardVO;

/**
 * 工会服务
 * 
 * @author hanjie.l
 * 
 */
public class AllianceServiceImpl extends BaseService implements AllianceService {

	@Override
	public List<AllianceSetting> getAllAllianceSetting() {
		return allianceSettingDAO.getAllAllianceSetting();
	}

	@Override
	public List<AllianceShopItem> getAllianceShopItems() {
		return allianceShopItemDAO.getAllianceShopItems();
	}

	@Override
	public void apply2Alliance(int userId, int allianceId) {

		userService.getExistUserCache(userId);

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}

		// 获取玩家个人工会信息
		UserAlliance userAlliance = userAllianceDAO.getOrCreateUserAlliance(userId);

		// 是否需要重置
		boolean update = refreshUserAlliance(userAlliance);

		// 更新
		if (update) {
			userAllianceDAO.updateUserAlliance(userAlliance);
		}

		// 已经加入工会
		if (userAlliance.getAllianceId() > 0) {
			throw new GameException(GameException.CODE_已经加入工会, "");
		}

		// 是否请求过了
		if (allianceApplyDAO.exitApply(userId, allianceId)) {
			throw new GameException(GameException.CODE_已经申请过了, "");
		}

		// 记录请求
		allianceApplyDAO.addApply(userId, allianceId);
	}

	@Override
	public void quitAlliance(int userId, int allianceId) {

		userService.getExistUserCache(userId);

		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() == 0) {
			throw new GameException(GameException.CODE_未加入工会, "");
		}

		if (userAlliance.getAllianceId() != allianceId) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		// 会长不能随意退出工会，只能解散工会
		if (userAlliance.getRole() == RoleType.LEADER) {
			throw new GameException(GameException.CODE_会长不能退出工会, "");
		}

		// 个人信息
		userAlliance.quitAlliance();
		userAllianceDAO.updateUserAlliance(userAlliance);

		// 从工会成员信息移除
		AllianceMembers allianceMembers = allianceMembersDAO.getAllianceMembers(allianceId);
		if (allianceMembers != null) {
			allianceMembers.getMembers().remove(userId);
			allianceMembersDAO.updateAllianceMembers(allianceMembers);
		}
	}

	@Override
	public void agreeApply(int userId, int allianceId, int applyId) {

		userService.getExistUserCache(userId);

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}

		// 看下自己是否有权限
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId || userAlliance.getRole() != RoleType.LEADER) {
			throw new GameException(GameException.CODE_没有权限, "");
		}

		AllianceSetting allianceSetting = GameCache.getAllianceSetting(alliance.getAllianceLevel());
		// 工会成员信息
		AllianceMembers allianceMembers = allianceMembersDAO.getAllianceMembers(allianceId);
		if (allianceMembers.getMembers().size() >= allianceSetting.getCapacity()) {
			throw new GameException(GameException.CODE_工会成员已达到上限, "");
		}

		// 看下请求是否还在
		if (!allianceApplyDAO.exitApply(applyId, allianceId)) {
			throw new GameException(GameException.CODE_申请请求已过期, "");
		}

		// 申请人工会信息
		UserAlliance applyAlliance = userAllianceDAO.getUserAlliance(applyId);
		if (applyAlliance.getAllianceId() != 0) {
			allianceApplyDAO.agreeAndRemoveApply(allianceId, applyId);
			throw new GameException(GameException.CODE_申请请求已过期, "");
		}
		applyAlliance.setAllianceId(allianceId);
		applyAlliance.setRole(RoleType.MEMBER);
		userAllianceDAO.updateUserAlliance(applyAlliance);

		// 移除申请人所有请求记录
		allianceApplyDAO.agreeAndRemoveApply(allianceId, applyId);

		allianceMembers.getMembers().add(applyId);
		allianceMembersDAO.updateAllianceMembers(allianceMembers);
	}

	@Override
	public void refuseApply(int userId, int allianceId, int applyId) {
		userService.getExistUserCache(userId);

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}

		// 看下自己是否有权限
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId || userAlliance.getRole() != RoleType.LEADER) {
			throw new GameException(GameException.CODE_没有权限, "");
		}

		// 看下请求是否还在
		if (allianceApplyDAO.exitApply(applyId, allianceId)) {
			// 移除申请人所有请求记录
			allianceApplyDAO.refuseAndRemoveApply(allianceId, applyId);
		}
	}

	@Override
	public UserAllianceInfoVO createAlliance(int userId, String allianceName, String descs) {

		User user = userService.getExistUserCache(userId);

		// 工会已存在
		Alliance exitAlliance = allianceDAO.getAllianceByName(allianceName);
		if (exitAlliance != null) {
			throw new GameException(GameException.CODE_工会已存在, "");
		}

		// 获取玩家个人工会信息
		UserAlliance userAlliance = userAllianceDAO.getOrCreateUserAlliance(userId);

		// 已经加入工会
		if (userAlliance.getAllianceId() > 0) {
			throw new GameException(GameException.CODE_已经加入工会, "");
		}

		// 扣钱
		userService.decrementCurrency(user, 500, LoggerType.TYPE_创建工会, "创建工会");

		// 创建工会
		Alliance alliance = new Alliance();
		alliance.setAllianceName(allianceName);
		alliance.setDescs(descs);
		alliance.setOwnerUserId(userId);
		int allianceId = allianceDAO.addAlliance(alliance);
		alliance.setId(allianceId);

		// 工会成员对象
		AllianceMembers allianceMembers = new AllianceMembers();
		allianceMembers.setId(allianceId);
		allianceMembers.getMembers().add(userId);
		allianceMembersDAO.addAllianceMembers(allianceMembers);

		userAlliance.reset();
		userAlliance.setAllianceId(allianceId);
		userAlliance.setRole(RoleType.LEADER);
		userAllianceDAO.updateUserAlliance(userAlliance);

		return getUserAllianceInfo(userAlliance);
	}

	@Override
	public void destroyAlliance(int userId, int allianceId) {

		userService.getExistUserCache(userId);

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}

		// 看下自己是否有权限
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId || userAlliance.getRole() != RoleType.LEADER) {
			throw new GameException(GameException.CODE_没有权限, "");
		}

		// 删除工会数据
		allianceDAO.deleteAlliance(alliance);
		allianceMembersDAO.deleteAllianceMembers(allianceId);

		// 清理个人数据
		userAlliance.quitAlliance();
		userAllianceDAO.updateUserAlliance(userAlliance);

		// 缓存请求数据
		allianceApplyDAO.clearAllianceData(allianceId);
	}

	@Override
	public UserAllianceInfoVO getUserAllianceInfo(int userId) {

		UserAlliance userAlliance = userAllianceDAO.getOrCreateUserAlliance(userId);

		return getUserAllianceInfo(userAlliance);
	}

	/**
	 * 获取个人工会信息
	 * 
	 * @param userAlliance
	 * @return
	 */
	private UserAllianceInfoVO getUserAllianceInfo(UserAlliance userAlliance) {
		UserAllianceInfoVO userAllianceInfo = MessageFactory.getMessage(UserAllianceInfoVO.class);

		// 是否更新
		boolean update = refreshUserAlliance(userAlliance);

		// 更新
		if (update) {
			userAllianceDAO.updateUserAlliance(userAlliance);
		}

		// 已加入工会
		if (userAlliance.getAllianceId() > 0) {
			// 找不到工会
			Alliance alliance = allianceDAO.getAlliance(userAlliance.getAllianceId());
			// 刷新不更新，因为更新要加锁，读又比较频繁
			refreshAlliance(alliance);
			AllianceInfoVO allianceInfoVO = MessageFactory.getMessage(AllianceInfoVO.class);
			allianceInfoVO.init(alliance);
			userAllianceInfo.setAllianceInfoVO(allianceInfoVO);

			// 获取所有工会成员信息
			List<AllianceMemberVO> allianceMemberVOs = new ArrayList<>();
			userAllianceInfo.setAllianceMemberVOs(allianceMemberVOs);
			AllianceMembers allianceMembers = allianceMembersDAO.getAllianceMembers(userAlliance.getAllianceId());
			if (!allianceMembers.getMembers().isEmpty()) {
				List<Integer> userIds = new ArrayList<>();
				for (int memberUserId : allianceMembers.getMembers()) {
					userIds.add(memberUserId);
				}
				// 成员个人信息
				List<UserCap> userCaps = userTeamDAO.getUserCapsCache(userIds);
				for (UserCap userCap : userCaps) {
					// 玩家工会对象
					UserAlliance memberUserAlliance = userAllianceDAO.getUserAlliance(userCap.getUserId());

					// 成员VO对象
					AllianceMemberVO allianceMemberVO = MessageFactory.getMessage(AllianceMemberVO.class);
					allianceMemberVO.setUserId(userCap.getUserId());
					allianceMemberVO.setPlayerName(userCap.getPlayerName());
					allianceMemberVO.setRole(memberUserAlliance.getRole());
					allianceMemberVOs.add(allianceMemberVO);
				}

			}

			// 入会请求
			List<ApplyUserInfoVO> applyRequests = getApplyRequest(userAlliance);
			userAllianceInfo.setApplyUserInfoVOs(applyRequests);
		}

		userAllianceInfo.setAllianceId(userAlliance.getAllianceId());
		userAllianceInfo.setRole(userAlliance.getRole());
		userAllianceInfo.setDevote(userAlliance.getDevote());
		userAllianceInfo.setGeneralBuild(userAlliance.getGeneralBuild());
		userAllianceInfo.setGoldBuild(userAlliance.getGoldBuild());
		userAllianceInfo.setCurrencyBuild(userAlliance.getCurrencyBuild());
		return userAllianceInfo;
	}

	/**
	 * 刷新玩家工会
	 * 
	 * @param userAlliance
	 * @return
	 */
	private boolean refreshUserAlliance(UserAlliance userAlliance) {
		// 是否更新
		boolean update = false;

		if (userAlliance != null) {
			if (userAlliance.getAllianceId() > 0) {
				// 工会已解散
				Alliance alliance = allianceDAO.getAlliance(userAlliance.getAllianceId());
				if (alliance == null) {
					userAlliance.quitAlliance();
					update = true;
				}
			}

			// 重置
			if (System.currentTimeMillis() > userAlliance.getNextRefreshTime()) {
				userAlliance.reset();
				update = true;
			}
		}

		return update;
	}

	/**
	 * 刷新工会
	 * 
	 * @param Alliance
	 * @return
	 */
	private boolean refreshAlliance(Alliance alliance) {
		// 是否更新
		boolean update = false;

		if (alliance != null) {
			// 重置
			if (System.currentTimeMillis() > alliance.getNextRefreshTime()) {
				alliance.reset();
				update = true;
			}
		}
		return update;
	}

	/**
	 * 获取入会请求
	 * 
	 * @param userAlliance
	 * @return
	 */
	private List<ApplyUserInfoVO> getApplyRequest(UserAlliance userAlliance) {

		List<ApplyUserInfoVO> applyUserInfos = new ArrayList<>();

		// 权限验证
		if (userAlliance == null || userAlliance.getAllianceId() <= 0 || userAlliance.getRole() != RoleType.LEADER) {
			return applyUserInfos;
		}

		int allianceId = userAlliance.getAllianceId();

		Set<Integer> allianceApplys = allianceApplyDAO.getAllianceApplys(userAlliance.getAllianceId());
		for (int applyUserId : allianceApplys) {
			long userApplyTime = allianceApplyDAO.getUserApplyTime(userAlliance.getUserId(), allianceId);
			UserAlliance applyUserAlliance = userAllianceDAO.getUserAlliance(applyUserId);
			// 请求没过期，并且玩家还没加入工会
			if (userApplyTime > 0 || applyUserAlliance.getAllianceId() <= 0) {
				User applyer = userService.getExistUser(applyUserId);

				ApplyUserInfoVO applyUserInfo = MessageFactory.getMessage(ApplyUserInfoVO.class);
				applyUserInfo.setUserId(applyer.getUserId());
				applyUserInfo.setPlayerLevel(applyer.getLevel());
				applyUserInfo.setApplyTime(userApplyTime);
				applyUserInfo.setPlayerName(applyer.getPlayerName());
			} else {
				// 移除请求
				allianceApplyDAO.removeAllianceApplyUser(allianceId, applyUserId);
			}
		}

		return applyUserInfos;
	}

	@Override
	public List<SimpleAllianceInfoVO> listAllianceInfos(int userId) {

		List<SimpleAllianceInfoVO> allianceInfoVOs = new ArrayList<>();

		// 玩家申请的所有工会
		Set<Integer> userApplys = allianceApplyDAO.getUserApplys(userId);
		// 获取所有工会信息
		List<Alliance> alliances = allianceDAO.getAllAlliance();
		for (Alliance alliance : alliances) {
			SimpleAllianceInfoVO simpleAllianceInfoVO = MessageFactory.getMessage(SimpleAllianceInfoVO.class);
			simpleAllianceInfoVO.setAllianceId(alliance.getId());
			simpleAllianceInfoVO.setName(alliance.getAllianceName());
			simpleAllianceInfoVO.setDescs(alliance.getDescs());
			simpleAllianceInfoVO.setLevel(alliance.getAllianceLevel());

			if (userApplys.contains(alliance.getId())) {
				// 看下请求过期了没有
				if (allianceApplyDAO.exitApply(userId, alliance.getId())) {
					simpleAllianceInfoVO.setApply(true);
				} else {
					allianceApplyDAO.removeUserApplyAlliance(userId, alliance.getId());
				}
			}
			allianceInfoVOs.add(simpleAllianceInfoVO);
		}

		return allianceInfoVOs;
	}

	@Override
	public void updateNotice(int userId, int allianceId, String notice) {
		userService.getExistUserCache(userId);

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}

		// 看下自己是否有权限
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId || userAlliance.getRole() != RoleType.LEADER) {
			throw new GameException(GameException.CODE_没有权限, "");
		}

		alliance.setNotice(notice);
		allianceDAO.updateAlliance(alliance);
	}

	@Override
	public void updateDesc(int userId, int allianceId, String desc) {
		userService.getExistUserCache(userId);

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}

		// 看下自己是否有权限
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId || userAlliance.getRole() != RoleType.LEADER) {
			throw new GameException(GameException.CODE_没有权限, "");
		}

		alliance.setDescs(desc);
		allianceDAO.updateAlliance(alliance);
	}

	@Override
	public void kickMember(int userId, int allianceId, int kickId) {
		if (userId == kickId || kickId <= 0) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		userService.getExistUserCache(userId);

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}

		// 看下自己是否有权限
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId || userAlliance.getRole() != RoleType.LEADER) {
			throw new GameException(GameException.CODE_没有权限, "");
		}

		// 工会成员
		AllianceMembers allianceMembers = allianceMembersDAO.getAllianceMembers(allianceId);
		if (!allianceMembers.getMembers().contains(kickId)) {
			throw new GameException(GameException.CODE_工会成员不存在, "");
		}

		// 移除玩家
		UserAlliance kickUserAlliance = userAllianceDAO.getUserAlliance(kickId);
		kickUserAlliance.setAllianceId(0);
		userAllianceDAO.updateUserAlliance(kickUserAlliance);

		allianceMembers.getMembers().remove(kickId);
		allianceMembersDAO.updateAllianceMembers(allianceMembers);
	}

	@Override
	public BuildingVO building(int userId, int allianceId, byte type) {
		if (type <= 0 || type >= 4) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		User user = userService.getExistUserCache(userId);

		// 校验
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		// 刷新
		refreshUserAlliance(userAlliance);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId) {
			throw new GameException(GameException.CODE_未加入工会, "");
		}

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}
		refreshAlliance(alliance);

		switch (type) {
		// 普通建设
		case 1:
			if (userAlliance.getGeneralBuild() <= 0) {
				alliance.setDevote(alliance.getDevote() + AllianceConfig.GENERAL_BUILD_GET);
				alliance.setTodayDevote(alliance.getTodayDevote() + AllianceConfig.GENERAL_BUILD_GET);
				userAlliance.setDevote(userAlliance.getDevote() + AllianceConfig.GENERAL_BUILD_GET);
				userAlliance.setGeneralBuild(userAlliance.getGeneralBuild() + 1);
				break;
			} else {
				throw new GameException(GameException.CODE_已经建设过了, "");
			}
			// 金币建设
		case 2:
			if (userAlliance.getGoldBuild() <= 0) {
				// 扣金币
				userService.decrementGold(user, AllianceConfig.GOLD_BUILD_COST, LoggerType.TYPE_工会金币建设, "");

				alliance.setDevote(alliance.getDevote() + AllianceConfig.GOLD_BUILD_GET);
				alliance.setTodayDevote(alliance.getTodayDevote() + AllianceConfig.GOLD_BUILD_GET);
				userAlliance.setDevote(userAlliance.getDevote() + AllianceConfig.GOLD_BUILD_GET);
				userAlliance.setGoldBuild(userAlliance.getGoldBuild() + 1);
				break;
			} else {
				throw new GameException(GameException.CODE_已经建设过了, "");
			}
			// 魂钻建设
		case 3:
			if (userAlliance.getCurrencyBuild() <= 0) {
				// 扣魂钻
				userService.decrementCurrency(user, AllianceConfig.CURRENCY_BUILD_COST, LoggerType.TYPE_工会魂钻建设, "");

				alliance.setDevote(alliance.getDevote() + AllianceConfig.CURRENCY_BUILD_GET);
				alliance.setTodayDevote(alliance.getTodayDevote() + AllianceConfig.CURRENCY_BUILD_GET);
				userAlliance.setDevote(userAlliance.getDevote() + AllianceConfig.CURRENCY_BUILD_GET);
				userAlliance.setCurrencyBuild(userAlliance.getCurrencyBuild() + 1);
				break;
			} else {
				throw new GameException(GameException.CODE_已经建设过了, "");
			}

		default:
			throw new GameException(GameException.CODE_参数错误, "");
		}

		userAllianceDAO.updateUserAlliance(userAlliance);
		allianceDAO.updateAlliance(alliance);

		BuildingVO buildingVO = MessageFactory.getMessage(BuildingVO.class);
		buildingVO.setAllianceDevote(alliance.getDevote());
		buildingVO.setUserDevote(alliance.getDevote());
		buildingVO.setGoold(user.getGold());
		buildingVO.setCurrency(user.getCurrency());
		return buildingVO;
	}

	@Override
	public ContributeVO contributeSoul(int userId, int allianceId, Set<Long> userSoulIds) {
		User user = userService.getExistUserCache(userId);

		// 校验
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		// 刷新
		refreshUserAlliance(userAlliance);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId) {
			throw new GameException(GameException.CODE_未加入工会, "");
		}

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}
		// 刷新
		refreshAlliance(alliance);

		// 工会每日捐赠已达到上限
		if (alliance.getTodayDevote() >= AllianceConfig.ALLIANCE_MAX_DEVOTE_LIMIT) {
			throw new GameException(GameException.CODE_工会今日贡献值已达到上限, "");
		}

		// 最后获得荣誉值
		int addDevote = 0;

		for (long userSoulId : userSoulIds) {
			UserSoul userSoul = userSoulService.getExistUserSoul(userId, userSoulId);
			if (userSoul == null) {
				throw new GameException(GameException.CODE_参数错误, "");
			}

			Soul soul = GameCache.getSoul(userSoul.getSoulId());

			userSoulService.deleteUserSoul(user, userSoul, LoggerType.TYPE_工会捐赠战魂, "");

			addDevote += soul.getAddDevote();
		}

		alliance.setDevote(alliance.getDevote() + addDevote);
		alliance.setTodayDevote(alliance.getTodayDevote() + addDevote);
		userAlliance.setDevote(userAlliance.getDevote() + addDevote);

		ContributeVO contributeVO = MessageFactory.getMessage(ContributeVO.class);
		contributeVO.setAllianceDevote(alliance.getDevote());
		contributeVO.setUserDevote(userAlliance.getDevote());
		return contributeVO;
	}

	@Override
	public ContributeVO contributeStuff(int userId, int allianceId, int goodsId, int num) {
		if (num <= 0 || num >= 999) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		// 校验
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		// 刷新
		refreshUserAlliance(userAlliance);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId) {
			throw new GameException(GameException.CODE_未加入工会, "");
		}

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}
		// 刷新
		refreshAlliance(alliance);

		// 工会每日捐赠已达到上限
		if (alliance.getTodayDevote() >= AllianceConfig.ALLIANCE_MAX_DEVOTE_LIMIT) {
			throw new GameException(GameException.CODE_工会今日贡献值已达到上限, "");
		}

		Stuff stuff = GameCache.getStuff(goodsId);
		if (stuff == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		// 最后获得荣誉值
		int addDevote = num * stuff.getAddDevote();

		Backage backage = userGoodsService.getPackage(userId);

		// 扣除道具
		userGoodsService.deleteGoods(backage, Goods.TYPE_STUFF, goodsId, num, LoggerType.TYPE_工会捐赠材料, "");

		alliance.setDevote(alliance.getDevote() + addDevote);
		alliance.setTodayDevote(alliance.getTodayDevote() + addDevote);
		userAlliance.setDevote(userAlliance.getDevote() + addDevote);

		ContributeVO contributeVO = MessageFactory.getMessage(ContributeVO.class);
		contributeVO.setAllianceDevote(alliance.getDevote());
		contributeVO.setUserDevote(userAlliance.getDevote());
		return contributeVO;
	}

	@Override
	public void upgradeAlliance(int userId, int allianceId) {
		User user = userService.getExistUserCache(userId);

		// 找不到工会
		Alliance alliance = allianceDAO.getAlliance(allianceId);
		if (alliance == null) {
			throw new GameException(GameException.CODE_工会不存在, "");
		}

		// 看下自己是否有权限
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() != allianceId || userAlliance.getRole() != RoleType.LEADER) {
			throw new GameException(GameException.CODE_没有权限, "");
		}

		// 工会配置
		AllianceSetting allianceSetting = GameCache.getAllianceSetting(alliance.getAllianceLevel());
		if (allianceSetting.isMaxLevel()) {
			throw new GameException(GameException.CODE_工会已达到最高等级, "");
		}

		if (alliance.getDevote() < allianceSetting.getCostDevote()) {
			throw new GameException(GameException.CODE_工会贡献点不足, "");
		}

		// 扣金币
		userService.decrementGold(user, allianceSetting.getCostGold(), LoggerType.TYPE_工会升级, "");

		alliance.setDevote(alliance.getDevote() - allianceSetting.getCostDevote());
		alliance.setAllianceLevel(alliance.getAllianceLevel() + 1);
		allianceDAO.updateAlliance(alliance);
	}

	@Override
	public BuyItemVO buyItem(int userId, int itemId) {
		User user = userService.getExistUserCache(userId);

		// 看下自己是否有权限
		UserAlliance userAlliance = userAllianceDAO.getUserAlliance(userId);
		if (userAlliance == null || userAlliance.getAllianceId() <= 0) {
			throw new GameException(GameException.CODE_未加入工会, "");
		}

		// 工会已解散
		Alliance alliance = allianceDAO.getAlliance(userAlliance.getAllianceId());
		if (alliance == null) {
			throw new GameException(GameException.CODE_未加入工会, "");
		}

		// 查看道具
		AllianceShopItem allianceShopItem = GameCache.getAllianceShopItem(itemId);
		if (allianceShopItem == null) {
			throw new GameException(GameException.CODE_参数错误, "");
		}

		// 工会等级不足
		if (allianceShopItem.getAllianceLevel() > alliance.getAllianceLevel()) {
			throw new GameException(GameException.CODE_工会等级不足, "");
		}

		// 贡献点不足
		if (userAlliance.getDevote() < allianceShopItem.getCostDevote()) {
			throw new GameException(GameException.CODE_个人贡献点不足, "");
		}

		// 扣除贡献点
		userAlliance.setDevote(userAlliance.getDevote() - allianceShopItem.getCostDevote());
		userAllianceDAO.updateUserAlliance(userAlliance);

		// 发放物品
		List<Goods> goodsList = Arrays.asList(Goods.create(allianceShopItem.getGoodsId(), allianceShopItem.getGoodsType(), allianceShopItem.getNum(), allianceShopItem.getLevel()));
		UserAwardVO userAwardVO = goodsService.getUserAwardVO(goodsList, user, LoggerType.TYPE_工会商城, allianceShopItem.getId() + "");

		BuyItemVO buyItemVO = MessageFactory.getMessage(BuyItemVO.class);
		buyItemVO.setDevote(userAlliance.getDevote());
		buyItemVO.setUserAwardVO(userAwardVO);
		return buyItemVO;
	}
}
