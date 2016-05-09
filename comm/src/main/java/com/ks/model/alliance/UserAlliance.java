package com.ks.model.alliance;

import com.ks.model.alliance.constant.RoleType;
import com.ks.util.DateUtil;
/**
 * 个人玩家工会信息
 * @author hanjie.l
 *
 */
public class UserAlliance {
	
	/**
	 * 玩家id
	 */
	private int userId;
	
	/**
	 * 所属工会id
	 */
	private int allianceId;
	
	/**
	 * 角色
	 */
	private int role = RoleType.MEMBER;
	
	/**
	 * 贡献值
	 */
	private long devote;
	
	/**
	 * 今日普通建设次数
	 */
	private int generalBuild;
	
	/**
	 * 金币建设次数
	 */
	private int goldBuild;
	
	/**
	 * 魂钻建设次数
	 */
	private int currencyBuild;
	
	/**
	 * 下次更新时间
	 */
	private long nextRefreshTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(int allianceId) {
		this.allianceId = allianceId;
	}

	public long getNextRefreshTime() {
		return nextRefreshTime;
	}

	public void setNextRefreshTime(long nextRefreshTime) {
		this.nextRefreshTime = nextRefreshTime;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public long getDevote() {
		return devote;
	}

	public void setDevote(long devote) {
		this.devote = devote;
	}

	public int getGeneralBuild() {
		return generalBuild;
	}

	public void setGeneralBuild(int generalBuild) {
		this.generalBuild = generalBuild;
	}

	public int getGoldBuild() {
		return goldBuild;
	}

	public void setGoldBuild(int goldBuild) {
		this.goldBuild = goldBuild;
	}

	public int getCurrencyBuild() {
		return currencyBuild;
	}

	public void setCurrencyBuild(int currencyBuild) {
		this.currencyBuild = currencyBuild;
	}

	/**
	 * 初始化
	 * @param userId
	 */
	public void init(int userId){
		this.userId = userId;
		reset();
	}
	
	/**
	 * 重置
	 */
	public void reset(){
		this.nextRefreshTime = DateUtil.getNextDateTime(0, 0, 0).getTime();
		this.generalBuild = 0;
		this.goldBuild = 0;
		this.currencyBuild = 0;
	}
	
	/**
	 * 离开工会
	 */
	public void quitAlliance(){
		this.allianceId = 0;
		this.devote = 0;
		this.role = RoleType.MEMBER;
	}
}
