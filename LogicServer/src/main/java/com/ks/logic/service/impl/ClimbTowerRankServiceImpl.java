package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ks.exceptions.GameException;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.ClimbTowerRankService;
import com.ks.model.climb.ClimbTowerRank;
import com.ks.model.user.UserCap;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.rank.RankNoticeVO;
import com.ks.protocol.vo.rank.RankerVO;
import com.ks.protocol.vo.user.UserCapVO;
/**
 * 爬塔试炼配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月17日
 */
public class ClimbTowerRankServiceImpl extends BaseService implements ClimbTowerRankService {

	@Override
    public void add(ClimbTowerRank entity) {
		climbTowerRankDAO.add(entity);
    }

	@Override
    public void update(ClimbTowerRank entity) {
		climbTowerRankDAO.update(entity);
    }

	@Override
    public List<ClimbTowerRank> queryAll(int size) {
	    return climbTowerRankDAO.queryClimbTowerRank(size);
    }
	
	@Override
	public RankNoticeVO getClimbTowerRank(int userId) {
		RankNoticeVO rankNoticeVO = MessageFactory.getMessage(RankNoticeVO.class);
		
		List<ClimbTowerRank> list = climbTowerRankDAO.queryClimbTowerRank(30);
		if(list == null || list.size() <0 ){
			throw new GameException(GameException.CODE_爬塔排行榜暂无数据, "");
		}
		List<RankerVO> rankers = new ArrayList<>();
		rankNoticeVO.setRankers(rankers);
		
		for(int i = 0; i < list.size(); i++) {
			ClimbTowerRank ctr = list.get(i);
			//队长
			UserCap cap = userTeamDAO.getUserCapCache(ctr.getUserId());
			if(cap == null){
				cap = userService.getUserCap(ctr.getUserId());
			}
			
			RankerVO rankerVO = MessageFactory.getMessage(RankerVO.class);
			rankerVO.setRank(i+1);
			rankerVO.setValue1(ctr.getTowerFloor());
			rankerVO.setValue2(ctr.getStarCount());
			if(cap != null){
				UserCapVO capvo = MessageFactory.getMessage(UserCapVO.class);
				capvo.init(cap);
				rankerVO.setUserCapVO(capvo);
			}
			rankers.add(rankerVO);
			
			//自己在前十
			if(ctr.getUserId() == userId){
				rankNoticeVO.setOwnRanker(rankerVO);
			}
		}
		
		if (rankNoticeVO.getOwnRanker() == null) {
			//查询当前用户爬塔排名信息
			UserCapVO capvo = MessageFactory.getMessage(UserCapVO.class);
			RankerVO rankerVO = MessageFactory.getMessage(RankerVO.class);
			
			//队长
			UserCap cap = userTeamDAO.getUserCapCache(userId);
			if(cap == null){
				cap = userService.getUserCap(userId);
			}
			
			ClimbTowerRank userRank = climbTowerRankDAO.getClimbTowerRank(userId);
			if (userRank != null ) {
				//我的排名
				int rank = climbTowerRankDAO.getRankByUserId(userId);
				rankerVO.setRank(rank);
				rankerVO.setValue1(userRank.getTowerFloor());
				rankerVO.setValue2(userRank.getStarCount());
			}
			capvo.init(cap);
			rankerVO.setUserCapVO(capvo);
			rankNoticeVO.setOwnRanker(rankerVO);
		}
		return rankNoticeVO;
	}
}
