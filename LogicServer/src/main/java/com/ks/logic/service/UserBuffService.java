package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.user.UserBuff;
import com.ks.protocol.vo.user.UserBuffVO;

/**
 * 用户buff
 * @author living.li
 * @date   2014年6月24日
 */
public interface UserBuffService {
	public List<UserBuff> getUserBuff(int userId);
	/**
	 * buff是否过期
	 * @param userId
	 * @param buffId
	 * @param value
	 * @return
	 */
	public boolean buffOffTime(int userId,int buffId,int value);
	/**
	 * 添加用户buff
	 * @param userId
	 * @param buff
	 * @param time
	 * @return
	 */
	@Transaction
	public UserBuff addUserBuff(int userId,UserBuff buff,long time);
	
	/**
     * 用户buff
     * @param userId
     * @return
     */
    public List<UserBuffVO> gainUserBuff(int userId);
}
