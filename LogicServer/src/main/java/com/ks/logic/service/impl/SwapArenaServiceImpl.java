package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.SwapArenaService;
import com.ks.logic.utils.DateUtils;
import com.ks.model.affiche.Affiche;
import com.ks.model.logger.LoggerType;
import com.ks.model.swaparena.SwapArena;
import com.ks.model.swaparena.SwapArenaBuySetting;
import com.ks.model.swaparena.SwapArenaFightLog;
import com.ks.model.swaparena.SwapArenaRewardSetting;
import com.ks.model.user.User;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.fight.FightVO;
import com.ks.protocol.vo.rank.RankNoticeVO;
import com.ks.protocol.vo.rank.RankerVO;
import com.ks.protocol.vo.swaparena.BuyTimesResultVO;
import com.ks.protocol.vo.swaparena.SwapArenaBaseVO;
import com.ks.protocol.vo.swaparena.SwapArenaFightLogVO;
import com.ks.protocol.vo.swaparena.ChallengeResultVO;
import com.ks.protocol.vo.swaparena.SwapArenaVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.protocol.vo.user.UserSoulTeamVO;

/**
 * 交换竞技场
 * 
 * @author hanjie.l
 * 
 */
public class SwapArenaServiceImpl extends BaseService implements SwapArenaService {

	/**
	 * 胜利荣誉值
	 */
	public static final int WIN_HONOR = 200;

	/**
	 * 失败荣誉值
	 */
	public static final int FAIL_HONOR = 100;

	@Override
	public SwapArenaVO getSwapArenaInfo(int userId) {

		User user = userService.getExistUserCache(userId);

		SwapArena swapArena = swapArenaDAO.getSwapArena(user.getUserId());
		if (swapArena == null) {
			int rank = swapArenaDAO.getSwapArenaCount();
			swapArena = init(userId, rank, rank);
			swapArenaDAO.addSwapArena(swapArena);
		}

		// 每天凌晨0点0分刷新
		if (DateUtils.calcDiffTimezone(new Date(), new Date(swapArena.getLastUpdateTime()), 0, 0, 0) > 0) {
			swapArena.reset();
			swapArenaDAO.updateSwapArena(swapArena);
		}

		// 要返回挑战列表名次
		List<Integer> ranks = culateRanks(swapArena.getRank());
		List<SwapArena> swapArenaByRanks = swapArenaDAO.getSwapArenaByRanks(ranks);

		// 挑战列表VO
		List<SwapArenaBaseVO> arenaBaseVOs = new ArrayList<>();
		for (SwapArena arena : swapArenaByRanks) {
			SwapArenaBaseVO baseVO = MessageFactory.getMessage(SwapArenaBaseVO.class);
			// 用户信息
			User ranker = userService.getExistUser(arena.getUserId());
			// 当前队伍VO
			UserSoulTeamVO currentTeam = userTeamService.findUserCurrentTeam(arena.getUserId());

			baseVO.init(arena.getUserId(), arena.getRank(), ranker.getLevel(), ranker.getPlayerName(), currentTeam);

			arenaBaseVOs.add(baseVO);
		}

		SwapArenaVO arenaVO = MessageFactory.getMessage(SwapArenaVO.class);
		arenaVO.init(swapArena, arenaBaseVOs);
		return arenaVO;
	}

	@Override
	public ChallengeResultVO challenge(int userId, int targetId) {

		// 攻击者
		User attacker = userService.getExistUserCache(userId);

		SwapArena attackSwapArena = swapArenaDAO.getSwapArena(userId);
		if (attackSwapArena == null) {
			throw new GameException(GameException.CODE_参数错误, "userSwapArena is null");
		}
		int attackOldRank = attackSwapArena.getRank();

		// 判断战斗cd
		if (System.currentTimeMillis() < attackSwapArena.getNextFightTime()) {
			throw new GameException(GameException.CODE_交换竞技场战斗CD中, "in cd");
		}

		// 可用战斗次数
		if (attackSwapArena.getTimes() <= 0) {
			throw new GameException(GameException.CODE_交换竞技场战斗CD中, "in cd");
		}

		// 判断攻击玩家是否存在
		User defender = userService.getExistUser(targetId);
		SwapArena defenderSwapArena = swapArenaDAO.getSwapArena(targetId);
		if (defenderSwapArena == null) {
			throw new GameException(GameException.CODE_参数错误, "targetSwapArena is null");
		}
		int defendOldRank = attackSwapArena.getRank();

		// 战斗VO
		FightVO fightVO = arenaService.fighting(userId, targetId);

		// 扣除次数
		attackSwapArena.setTimes(attackSwapArena.getTimes() - 1);

		// 设置cd时间
		attackSwapArena.setNextFightTime(System.currentTimeMillis() + 60 * 1000);

		// 攻击者胜利，并且攻击者名次低于目标玩家
		if (fightVO.isAttWin() && attackSwapArena.getRank() > defenderSwapArena.getRank()) {

			// 交换排名
			int temp = attackSwapArena.getRank();
			attackSwapArena.setRank(defenderSwapArena.getRank());
			defenderSwapArena.setRank(temp);

			swapArenaDAO.updateSwapArena(defenderSwapArena);

		}
		swapArenaDAO.updateSwapArena(attackSwapArena);

		// 奖励荣誉值
		if (fightVO.isAttWin()) {
			userService.increHonor(attacker, WIN_HONOR, 0, "");
		} else {
			userService.increHonor(attacker, FAIL_HONOR, 0, "");
		}

		// 战斗日志
		SwapArenaFightLog arenaFightLog = new SwapArenaFightLog();
		arenaFightLog.init(attacker, defender);
		arenaFightLog.setWin(fightVO.isAttWin());
		arenaFightLog.setUpdateTime(System.currentTimeMillis());
		arenaFightLog.setAttackOldRank(attackOldRank);
		arenaFightLog.setAttackNewRank(attackSwapArena.getRank());
		//队长
		arenaFightLog.setAttackSoulId(userService.getUserCap(userId).getSoulId());
		arenaFightLog.setDefendOldRank(defendOldRank);
		arenaFightLog.setDefendNewRank(defenderSwapArena.getRank());
		//队长
		arenaFightLog.setDefendSoulId(userService.getUserCap(targetId).getSoulId());
		swapArenaLogDAO.addSwapArena(arenaFightLog);

		// 返回结果
		ChallengeResultVO arenaFightVO = MessageFactory.getMessage(ChallengeResultVO.class);
		arenaFightVO.setOldRank(attackOldRank);
		arenaFightVO.setNewRank(attackSwapArena.getRank());
		arenaFightVO.setTimes(attackSwapArena.getTimes());
		arenaFightVO.setNextFightTime(attackSwapArena.getNextFightTime());
		arenaFightVO.setFightVO(fightVO);
		arenaFightVO.setAddHonor(fightVO.isAttWin() ? WIN_HONOR : FAIL_HONOR);
		return arenaFightVO;
	}

	@Override
	public List<SwapArenaFightLogVO> getFightLog(int userId) {

		List<SwapArenaFightLogVO> arenaFightLogVOs = new ArrayList<>();

		for (SwapArenaFightLog arenaFightLog : swapArenaLogDAO.getSwapArenaFightLog(userId)) {
			SwapArenaFightLogVO arenaFightLogVO = MessageFactory.getMessage(SwapArenaFightLogVO.class);
			if (userId == arenaFightLog.getAttackId()) {
				// 判断是切磋，还是挑战
				int rankChange = arenaFightLog.getAttackOldRank() - arenaFightLog.getAttackNewRank();
				arenaFightLogVO.setUpdateTime(arenaFightLog.getUpdateTime());
				arenaFightLogVO.setTargetUserId(arenaFightLog.getDefendId());
				arenaFightLogVO.setTargetUserName(arenaFightLog.getDefendName());
				arenaFightLogVO.setSoulId(arenaFightLog.getDefendSoulId());
				arenaFightLogVO.setRankChange(rankChange);
				// 如果是挑战
				arenaFightLogVO.setWin(arenaFightLog.isWin());
			} else {
				// 判断是切磋，还是挑战
				int rankChange = arenaFightLog.getDefendOldRank() - arenaFightLog.getDefendNewRank();
				arenaFightLogVO.setUpdateTime(arenaFightLog.getUpdateTime());
				arenaFightLogVO.setTargetUserId(arenaFightLog.getAttackId());
				arenaFightLogVO.setTargetUserName(arenaFightLog.getAttackName());
				arenaFightLogVO.setSoulId(arenaFightLog.getAttackSoulId());
				arenaFightLogVO.setRankChange(rankChange);
				// 如果是挑战
				arenaFightLogVO.setWin(!arenaFightLog.isWin());
			}
			arenaFightLogVOs.add(arenaFightLogVO);
		}

		return arenaFightLogVOs;
	}

	@Override
	public BuyTimesResultVO buyChallengeTimes(int userId, int count) {

		if (count <= 0 || count >= 999) {
			throw new GameException(GameException.CODE_参数错误, "count is error");
		}

		User user = userService.getExistUserCache(userId);

		SwapArena swapArena = swapArenaDAO.getSwapArena(userId);
		if (swapArena == null) {
			throw new GameException(GameException.CODE_参数错误, "swapArena is null");
		}

		// 每天凌晨0点0分刷新
		if (DateUtils.calcDiffTimezone(new Date(), new Date(swapArena.getLastUpdateTime()), 0, 0, 0) > 0) {
			swapArena.reset();
			swapArenaDAO.updateSwapArena(swapArena);
		}

		int totalCost = 0;
		for (int i = 1; i <= count; i++) {
			SwapArenaBuySetting swapBuySetting = GameCache.getSwapBuySetting(swapArena.getBuyTimes() + i);
			totalCost += swapBuySetting.getCost();
		}

		// 扣魂钻
		userService.decrementCurrency(user, totalCost, LoggerType.TYPE_交换竞技场购买挑战次数, "");

		// 加次数
		swapArena.setTimes(swapArena.getTimes() + count);
		swapArena.setBuyTimes(swapArena.getBuyTimes() + count);
		swapArenaDAO.updateSwapArena(swapArena);

		BuyTimesResultVO buyTimesResultVO = MessageFactory.getMessage(BuyTimesResultVO.class);
		buyTimesResultVO.setTimes(swapArena.getTimes());
		buyTimesResultVO.setTodayBuyCount(swapArena.getBuyTimes());
		buyTimesResultVO.setCurrency(user.getCurrency());
		return buyTimesResultVO;
	}

	@Override
	public void rewardTopPlayer() {

		List<SwapArena> topSwapArenas = swapArenaDAO.getAllSwapArenas();
		for (SwapArena arena : topSwapArenas) {
			if (!arena.isRobot()) {
				SwapArenaRewardSetting swapReward = GameCache.getSwapReward(arena.getRank());
				// 发邮件
				Affiche affiche = Affiche.create(arena.getUserId(), Affiche.AFFICHE_TYP_交换排行榜排名奖励, "排名赛结算奖励", "您的排名是" + arena.getRank(), swapReward.getRewards(), Affiche.STATE_未读, "0");
				afficheService.addAffiche(affiche);
			}
		}
	}

	/**
	 * 初始化对象
	 * 
	 * @param userId
	 * @param id
	 * @param rank
	 * @return
	 */
	private SwapArena init(int userId, int id, int rank) {
		SwapArena swapArena = new SwapArena();
		swapArena.setId(id);
		swapArena.setRank(rank);
		swapArena.setUserId(userId);
		swapArena.setTimes(SwapArena.FREE_TIMES);
		swapArena.setLastUpdateTime(System.currentTimeMillis());
		return swapArena;
	}

	/**
	 * 获取10个排名，计算要返回哪些排名信息
	 * 
	 * @param rank
	 * @return
	 */
	private List<Integer> culateRanks(int rank) {
		List<Integer> integers = new ArrayList<>();
		// 500以外分隔5名显示
		if (rank > 500) {
			for (int i = 0; i < 10; i++) {
				integers.add(rank - i * 5);
			}
			// 100~500分隔3名显示
		} else if (rank > 100 && rank <= 500) {
			for (int i = 0; i < 10; i++) {
				integers.add(rank - i * 3);
			}
			// 10~100分隔1名显示
		} else if (rank > 10 && rank <= 100) {
			for (int i = 0; i < 10; i++) {
				integers.add(rank - i);
			}
			// 10名内你懂得
		} else if (rank <= 10) {
			for (int i = 1; i <= 10; i++) {
				integers.add(i);
			}
		}
		return integers;
	}

	@Override
	public RankNoticeVO getSwapArenaRankNotice(int userId) {
		RankNoticeVO rankNoticeVO = MessageFactory.getMessage(RankNoticeVO.class);

		// 个人信息
		RankerVO ownRanker = MessageFactory.getMessage(RankerVO.class);
		SwapArena swapArena = swapArenaDAO.getSwapArena(userId);
		if (swapArena != null) {
			ownRanker.setRank(swapArena.getRank());
		}
		UserCapVO userCapVO = MessageFactory.getMessage(UserCapVO.class);
		userCapVO.init(userService.getUserCap(userId));
		ownRanker.setUserCapVO(userCapVO);

		// 前10玩家
		List<RankerVO> rankers = new ArrayList<>();
		List<SwapArena> topSwapArenas = swapArenaDAO.getTopSwapArenas(10);
		for (SwapArena temp : topSwapArenas) {
			RankerVO ranker = MessageFactory.getMessage(RankerVO.class);
			ranker.setRank(temp.getRank());

			UserCapVO userCapVOTemp = MessageFactory.getMessage(UserCapVO.class);
			userCapVOTemp.init(userService.getUserCap(temp.getUserId()));
			ranker.setUserCapVO(userCapVOTemp);

			rankers.add(ranker);
		}

		rankNoticeVO.setRankers(rankers);
		rankNoticeVO.setOwnRanker(ownRanker);
		return rankNoticeVO;
	}
}
