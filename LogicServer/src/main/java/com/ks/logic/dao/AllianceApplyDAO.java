package com.ks.logic.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.ks.access.GameDAOTemplate;

/**
 * 申请加入工会dao
 * @author hanjie.l
 *
 */
public class AllianceApplyDAO extends GameDAOTemplate{

	private static final String KEY_ALLIANCE = "KEY_ALLIANCE_";
	
	private static final String KEY_USERALLIANCE = "KEY_USERALLIANCE_";

	private static final String KEY_ALLIANCE_USERID = "KEY_ALLIANCE_USERID_";
	
	/**
	 * 过期时间
	 */
	private static final int EXPIRE_TIME = 24*60*60;
	
	/**
	 * 玩家请求集合key
	 * @param userId
	 * @return
	 */
	private String getUserAlliancekey(int userId){
		String key = KEY_USERALLIANCE + userId;
		return key;
	}
	
	/**
	 * 工会请求集合key
	 * @param allianceId
	 * @return
	 */
	private String getAlliancekey(int allianceId){
		String key = KEY_ALLIANCE + allianceId;
		return key;
	}
	
	/**
	 * 个人入会请求key
	 * @param userId
	 * @param allianceId
	 * @return
	 */
	private String getApplyKey(int userId, int allianceId){
		String key = KEY_ALLIANCE_USERID + userId + "_" + allianceId;
		return key;
	}
	
	/**
	 * 个人入会请求是否存在
	 * @param userId
	 * @param allianceId
	 * @return
	 */
	public boolean exitApply(int userId, int allianceId){
		String value = get(getApplyKey(userId, allianceId));
		if(value == null || value.equals("")){
			return false;
		}
		return true;
	}
	
	/**
	 * 申请入会时间
	 * @param userId
	 * @param allianceId
	 * @return
	 */
	public long getUserApplyTime(int userId, int allianceId){
		String value = get(getApplyKey(userId, allianceId));
		if(value == null || value.equals("")){
			return Integer.parseInt(value);
		}
		return 0;
	}
	
	/**
	 * 获取玩家所有工会申请
	 * @param userId
	 * @return 返回所有请求的工会
	 */
	public Set<Integer> getUserApplys(int userId){
		Set<Integer> applyAllianceIds = new HashSet<Integer>();
		for(String temp : smembers(getUserAlliancekey(userId))){
			applyAllianceIds.add(Integer.parseInt(temp));
		}
		return applyAllianceIds;
	}
	
	/**
	 * 工会所有收到申请
	 * @param userId
	 * @return 返回所有请求人id
	 */
	public Set<Integer> getAllianceApplys(int userId){
		Set<Integer> applyUserIds = new HashSet<Integer>();
		for(String temp : smembers(getAlliancekey(userId))){
			applyUserIds.add(Integer.parseInt(temp));
		}
		return applyUserIds;
	}
	
	/**
	 * 添加请求
	 * @param userId
	 * @param allianceId
	 */
	public void addApply(int userId, int allianceId){
		//记录请求key，并设置超时时间
		set(getApplyKey(userId, allianceId), new Date().getTime()+"");
		expire(getApplyKey(userId, allianceId), EXPIRE_TIME);
		//加入到个人请求集合
		sadd(getUserAlliancekey(userId), allianceId+"");
		//加入到工会请求集合
		sadd(getAlliancekey(allianceId), userId+"");
	}
	
	/**
	 * 同意并移除请求
	 * @param allianceId
	 * @param userId
	 */
	public void agreeAndRemoveApply(int allianceId, int userId){
		//从工会请求集合移除
		srem(getAlliancekey(allianceId), userId+"");
		//移除玩家请求集合
		String userApplySetKey = getUserAlliancekey(userId);
		Set<String> userApplyKeys = smembers(userApplySetKey);
		userApplyKeys.add(userApplySetKey);
		del(userApplyKeys.toArray(new String[userApplyKeys.size()]));
		//删除请求
		del(getApplyKey(userId, allianceId));
	}
	
	/**
	 * 拒绝并移除请求
	 * @param allianceId
	 * @param userId
	 */
	public void refuseAndRemoveApply(int allianceId, int userId){
		//从工会请求集合移除
		srem(getAlliancekey(allianceId), userId+"");
		//移除玩家请求集合
		srem(getUserAlliancekey(userId), allianceId+"");
	}
	
	/**
	 * 移除个人集合中请求
	 * @param userId
	 * @param allianceId
	 */
	public void removeUserApplyAlliance(int userId, int allianceId){
		//从工会请求集合移除
		srem(getUserAlliancekey(userId), allianceId+"");
	}
	
	/**
	 * 移除工会集合中请求
	 * @param userId
	 * @param allianceId
	 */
	public void removeAllianceApplyUser(int allianceId, int userId){
		//从工会请求集合移除
		srem(getAlliancekey(allianceId), userId+"");
	}
	
	/**
	 * 删除工会时清除缓存数据
	 * @param allianceId
	 */
	public void clearAllianceData(int allianceId){
		del(getAlliancekey(allianceId));
	}
}
