package com.ks.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LockKeyUtil {
	public static final String USER_PREFIX = "USER_";
	/**
	 * 得到玩家锁key
	 * @param userId
	 * @return
	 */
	public static String getUserLockKey(int userId){
		return "USER_" + userId;
	}
	
	/**
	 * 得到玩家锁key
	 * @param userIds
	 * @return
	 */
	public static List<String> getUserLockKeys(Collection<Integer> userIds){
		List<String> list = new ArrayList<String>();
		for(int userId : userIds){
			list.add(getUserLockKey(userId));
		}
		return list;
	}
	
	/**
	 * 得到玩家交换竞技场锁key
	 * @param userId
	 * @return
	 */
	public static List<String> getSwapArenaLockKey(int ... userIds){
		List<String> list = new ArrayList<String>();
		for(int userId : userIds){
			list.add("SWAPARENA_" + userId);
		}
		return list;
	}
	
	/**
	 * 得到工会对象锁key
	 * @param userId
	 * @return
	 */
	public static List<String> getAllianceLockKey(int allianceId){
		List<String> list = new ArrayList<String>();
		list.add("ALLIANCE_" + allianceId);
		return list;
	}
	
	/**
	 * 得到工会成员对象锁key
	 * @param userId
	 * @return
	 */
	public static List<String> getAllianceMemberLockKey(int allianceId){
		List<String> list = new ArrayList<String>();
		list.add("ALLIANCE_MEMBER_" + allianceId);
		return list;
	}
	
	/**
	 * 玩家工会对象
	 * @param allianceId
	 * @return
	 */
	public static List<String> getUserAllianceLockKey(int userId){
		List<String> list = new ArrayList<String>();
		list.add("USER_ALLIANCE_" + userId);
		return list;
	}

}
