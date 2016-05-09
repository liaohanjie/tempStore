package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.ChapterAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserChapterService;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.dungeon.FightResultVO;
import com.ks.protocol.vo.dungeon.MonsterAwardVO;
import com.ks.protocol.vo.mission.UserAwardVO;
import com.ks.protocol.vo.user.UserBuffVO;
import com.ks.protocol.vo.user.UserChapterVO;

public class ChapterActionImpl implements ChapterAction {
	
	private static UserChapterService userChapterService = ServiceFactory.getService(UserChapterService.class);
	
	@Override
	public UserChapterVO queryUserChapter(int userId) {
		return userChapterService.queryUserChapter(userId);
	}

	@Override
	public FightResultVO startFight(int userId, int chapterId,int friendId,byte teamId) {
		return userChapterService.startFight(userId, chapterId,friendId,teamId);
	}

	@Override
	public FightEndResultVO endFight(int userId, boolean pass,boolean hasJoin) {
		return userChapterService.endFight(userId, pass,hasJoin);
	}

	@Override
	public MonsterAwardVO openBox(int userId, int boxId) {
		return userChapterService.openBox(userId, boxId);
	}

	@Override
	public int resurrection(int userId) {
		return userChapterService.resurrection(userId);
	}

	@Override
	public List<UserBuffVO> userBakProp(int userId, int propId) {
		return userChapterService.userBakChapterProp(userId, propId);
	}

	@Override
	public FightEndResultVO sweep(int userId, int chapterId, int count,byte teamId) {
		return userChapterService.sweep(userId,chapterId,count,teamId);
	}

	@Override
	public List<UserChapterVO> queryUserActivityChapter(int userId) {
		return userChapterService.queryUserActivityChapter(userId);
	}
	
	@Override
	public List<Integer> getChapterChestRecrds(int userId){
		return userChapterService.getChapterChestRecrds(userId);
	}
	
	@Override
    public UserAwardVO getChapterChestAward(int userId, int chapterId) {
	    return userChapterService.getChapterChestAward(userId, chapterId);
    }

	@Override
    public List<UserChapterVO> queryUserChapterList(int userId) {
	    return userChapterService.queryUserChapterList(userId);
    }

	@Override
    public UserChapterVO buyChapterFightCount(int userId, int chapterId) {
	    return userChapterService.buyChapterFightCount(userId, chapterId);
    }

}
