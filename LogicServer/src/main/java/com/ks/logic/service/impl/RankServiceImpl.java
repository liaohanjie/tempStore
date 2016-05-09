package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ks.exceptions.GameException;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.RankService;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.rank.RankInfoVO;
import com.ks.protocol.vo.rank.RankNoticeVO;
import com.ks.protocol.vo.rank.RankerVO;
import com.ks.protocol.vo.user.UserCapVO;
/**
 * 排行榜整合
 * @author hanjie.l
 *
 */
public class RankServiceImpl extends BaseService implements RankService {

	@Override
	public RankInfoVO getRankInfo(int userId, int rankTypeId) {
		
		RankInfoVO rankInfoVO = MessageFactory.getMessage(RankInfoVO.class);
		rankInfoVO.setRankTypeId(rankTypeId);
		
		if(rankTypeId == 1){
			RankNoticeVO userLevelRanknotice = getUserLevelRanknotice(userId);
			rankInfoVO.setNotice(userLevelRanknotice);
		}else if(rankTypeId == 2){
			RankNoticeVO userChapterRanknotice = getUserChapterRanknotice(userId);
			rankInfoVO.setNotice(userChapterRanknotice);
		}else if(rankTypeId == 3){
			RankNoticeVO bossRankNotice = bossService.getBossRankNotice(userId);
			rankInfoVO.setNotice(bossRankNotice);
		} else if (rankTypeId == 4) {
			RankNoticeVO climbTowerRankNotice = climbTowerRankService.getClimbTowerRank(userId);
			rankInfoVO.setNotice(climbTowerRankNotice);
		}else if (rankTypeId == 5) {
			RankNoticeVO swapArenaRankNotice = swapArenaService.getSwapArenaRankNotice(userId);
			rankInfoVO.setNotice(swapArenaRankNotice);
		}else{
			throw new GameException(GameException.CODE_参数错误, "rankTypeId:" + rankTypeId);
		}
		return rankInfoVO;
	}
	
	
	/**
	 * 获取玩家等级榜
	 * @param userId
	 * @return
	 */
	public RankNoticeVO getUserLevelRanknotice(int userId){
		
		RankNoticeVO levelRankNoticeVO = MessageFactory.getMessage(RankNoticeVO.class);
		
		//个人数据
		RankerVO ownRanker = MessageFactory.getMessage(RankerVO.class);
		Integer userLevelRank = userDAO.getUserLevelRank(userId);
		ownRanker.setRank(userLevelRank);
		
		UserCapVO userCapVO = MessageFactory.getMessage(UserCapVO.class);
		userCapVO.init(userService.getUserCap(userId));
		ownRanker.setUserCapVO(userCapVO);
		
		//前10玩家
		List<RankerVO> rankers = new ArrayList<>();
		List<Integer> userIds = userDAO.gainUserLevel();
		if (userIds != null && !userIds.isEmpty()) {
			for(int i=1; i<=userIds.size(); i++){
				RankerVO ranker = MessageFactory.getMessage(RankerVO.class);
				ranker.setRank(i);
				
				UserCapVO userCapVOTemp = MessageFactory.getMessage(UserCapVO.class);
				userCapVOTemp.init(userService.getUserCap(userIds.get(i-1)));
				ranker.setUserCapVO(userCapVOTemp);
				
				rankers.add(ranker);
			}
		}
		
		levelRankNoticeVO.setRankers(rankers);
		levelRankNoticeVO.setOwnRanker(ownRanker);
		
		return levelRankNoticeVO;
	}
	
	
	/**
	 * 获取玩家等级榜
	 * @param userId
	 * @return
	 */
	public RankNoticeVO getUserChapterRanknotice(int userId){
		
		RankNoticeVO chapterRankNoticeVO = MessageFactory.getMessage(RankNoticeVO.class);
		
		//个人数据
		RankerVO ownRanker = MessageFactory.getMessage(RankerVO.class);
		ownRanker.setRank(userDAO.getUserChapterRank(userId));
		
		UserCapVO userCapVO = MessageFactory.getMessage(UserCapVO.class);
		userCapVO.init(userService.getUserCap(userId));
		ownRanker.setUserCapVO(userCapVO);
		
		//前10玩家
		List<RankerVO> rankers = new ArrayList<>();
		List<Integer> userIds = userDAO.gainChapterRankingTop10();
		if (userIds != null && !userIds.isEmpty()) {
			for(int i=1; i<=userIds.size(); i++){
				RankerVO ranker = MessageFactory.getMessage(RankerVO.class);
				ranker.setRank(i);
				
				UserCapVO userCapVOTemp = MessageFactory.getMessage(UserCapVO.class);
				userCapVOTemp.init(userService.getUserCap(userIds.get(i-1)));
				ranker.setUserCapVO(userCapVOTemp);
				
				rankers.add(ranker);
			}
		}

		
		chapterRankNoticeVO.setRankers(rankers);
		chapterRankNoticeVO.setOwnRanker(ownRanker);
		
		return chapterRankNoticeVO;
	}
}
