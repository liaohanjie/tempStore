package com.ks.model.user;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户战魂
 * 
 * @author ks
 */
public class UserSoul implements Serializable {

	private static final long serialVersionUID = 1L;

	/**召换消耗的魂币*/
	public static final int CALL_SOUL_CURRENCY=5;	
	/**召换消耗的友情点*/
	public static final int CALL_SOUL_FRIEND_POINT=200;
	
	
	/** 冷静 */
	public static final int SOUL_TYPE_COOL = 1;
	/** 持重 */
	public static final int SOUL_TYPE_DISCREET = 2;
	/** 热血 */
	public static final int SOULE_TYPE_BLOOD = 3;
	/** 固执 */
	public static final int SOUL_TYPE_STUBBORN = 4;
	
	/**强化成功*/
	public static final int OPT_SUCESS = 0;
	/**强化大成功*/
	public static final int OPT_LARGE_SUCESS = 1;
	/**强化超级成功*/
	public static final int OPT_SUPER_SUCESS = 2;
	/**变异*/
	public static final int OPT_VARIATION = 3;
	/**进化*/
	public static final int OPT_EVOLUTION = 4;
	/**重塑*/
	public static final int OPT_重塑 = 5;
	
	/**保护状态*/
	public static final int SOUL_SAFE_PROTECTION = 0b1;
	/**在队伍中 */
	public static final int[] SOUL_SAFE_IN_TEAM = new int[]{0b10,0b100,0b1000,0b10000,0b100000,0b1000000,0b10000000};
	
	/**探索状态中*/
	public static final int SOUL_SAFE_探索状态中 =0b100000000;
	
	/** 编号 */
	private long id;
	/** 用户编号 */
	private int userId;
	/** 战魂编号 */
	private int soulId;
	/** 战魂状态 */
	private int soulSafe;
	/** 等级 */
	private int level;
	/** 经验 */
	private int exp;
	/**主动技能 */
	private int skillLv;
	/** 性格 */
	private int soulType;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/**在队伍中的位置*/
	private byte pos;
	/**是否修改过*/
	private boolean update;
	public static final int getInTeamSafe(byte teamId){
		return SOUL_SAFE_IN_TEAM[teamId-1];
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSoulId() {
		return soulId;
	}

	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}

	public int getSoulSafe() {
		return soulSafe;
	}

	public void setSoulSafe(int soulSafe) {
		this.soulSafe = soulSafe;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getSkillLv() {
		return skillLv;
	}

	public void setSkillLv(int skillLv) {
		skillLv=skillLv==0?1:skillLv;
		this.skillLv = skillLv;
	}

	public int getSoulType() {
		return soulType;
	}

	public void setSoulType(int soulType) {
		this.soulType = soulType;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public byte getPos() {
		return pos;
	}

	public void setPos(byte pos) {
		this.pos = pos;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	@Override
	public String toString() {
		return "UserSoul [id=" + id + ", ex=" + soulId + ", level=" + level
				+ ", skillLv=" + skillLv + ", exp=" + exp + "]";
	}
	

}
