package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.SoulExploretionAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.SoulExploretionService;
import com.ks.protocol.vo.dungeon.FightEndResultVO;
import com.ks.protocol.vo.explora.SoulExploretionVO;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年8月11日 下午2:52:34
 * 类说明
 */
public class SoulExploretionActionImpl implements SoulExploretionAction {
	private static final SoulExploretionService soulExploretionService = ServiceFactory.getService(SoulExploretionService.class);

	@Override
	public SoulExploretionVO addSoulExploretion(int userId, long soulId,int hour,int teamId) {
		return soulExploretionService.addSoulExploretion(userId, soulId, hour,teamId);
	}

	@Override
	public List<SoulExploretionVO> getSoulExploretionList(int userId) {
		return soulExploretionService.getSoulExploretionList(userId);
	}

	@Override
	public FightEndResultVO exploretionAward(int userId, long soulId) {
		return soulExploretionService.exploretionAward(userId, soulId);
	}

	@Override
	public FightEndResultVO quicklyExploretionAward(int userId, long soulId) {
		return soulExploretionService.quicklyExploretionAward(userId, soulId);
	}

}
