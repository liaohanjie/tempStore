package com.ks.action.logic;

import com.ks.protocol.vo.check.CheckVO;

/**
 * 战斗数据校验服务
 * @author hanjie.l
 *
 */
public interface FightCheckAction {
	
	
	/**
	 * 数据校验
	 * @param userId
	 * @param battleCheckVO
	 */
	public void  check(int userId, CheckVO checkVO);
	
	
	/**
	 * 上报可疑数据
	 * @param userId
	 * @param clientData
	 */
	public void reportDoubtLog(int userId, String clientData);

}
