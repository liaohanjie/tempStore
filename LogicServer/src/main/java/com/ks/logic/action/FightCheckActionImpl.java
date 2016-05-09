package com.ks.logic.action;

import com.ks.action.logic.FightCheckAction;
import com.ks.logic.service.CheckFightService;
import com.ks.logic.service.ServiceFactory;
import com.ks.protocol.vo.check.CheckVO;
/**
 * 战斗数据校验服务
 * @author hanjie.l
 *
 */
public class FightCheckActionImpl implements FightCheckAction {
	
	private static CheckFightService checkFightService = ServiceFactory.getService(CheckFightService.class);

	@Override
	public void check(int userId, CheckVO checkVO) {
		checkFightService.check(userId, checkVO);
	}

	@Override
	public void reportDoubtLog(int userId, String clientData) {
		checkFightService.reportDoubtLog(userId, clientData);
	}
}
