package com.ks.logic.service;

import com.ks.access.Transaction;
import com.ks.model.check.BattleType;
import com.ks.model.check.CheckConfig;
import com.ks.protocol.vo.check.CheckVO;

/**
 * 战斗数据校验服务
 * @author hanjie.l
 *
 */
public interface CheckFightService {
	
	/**
	 * 加载配置
	 * @return
	 */
	public CheckConfig loadConfig();
	
	/**
	 * 数据校验
	 * @param userId
	 * @param battleCheckVO
	 */
	@Transaction
	public void  check(int userId, CheckVO checkVO);
	
	
	/**
	 * 上报可疑数据
	 * @param userId
	 * @param clientData
	 */
	@Transaction
	public void reportDoubtLog(int userId, String clientData);
	
	/**
	 * 检验是否通过战斗校验
	 * @param userId
	 * @param battleType
	 * @return
	 */
	public boolean isPassCheck(int userId, BattleType battleType);
	
	
	/**
	 * 清除校验结果
	 * @param userId
	 * @return
	 */
	@Transaction
	public void clearPassCheck(int userId);
	
	/**
	 * 回放
	 * @param userId
	 * @param logId
	 */
	@Transaction
	public void replay(int userId, int logId);

}
