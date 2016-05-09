package com.ks.model.logger;

import java.io.Serializable;
import java.util.Date;
/**
 * 游戏日志
 * @author ks
 */
public class GameLogger implements Serializable {

	private static final long serialVersionUID = 1L;
	/**战魂*/
	public static final int LOGGER_TYPE_SOUL = 1;	
	/**体力*/
	public static final int LOGGER_TYPE_STAMINA = 2;
	/**金币日志*/
	public static final int LOGGER_TYPE_GOLD = 3;
	/**经验日志*/
	public static final int LOGGER_TYPE_EXP = 4;
	/**货币日志*/
	public static final int LOGGER_TYPE_CURRENCY = 5;
	/**道具日志*/
	public static final int LOGGER_TYPE_PROP = 6;
	/**装备日志*/
	public static final int LOGGER_TYPE_EQUIPMENT = 7;
	/**材料日志*/
	public static final int LOGGER_TYPE_STUFF = 8;
	/**友情点日志*/
	public static final int LOGGER_TYPE_FRIENDLY_POINT = 9;
	/**副本道具日志*/
	public static final int LOGGER_TYPE_BAK_PROP= 10;
	/**pvp竞技点日志*/
	public static final int LOGGER_TYPE_PVP_POINT= 11;
	/**pvp分数日志*/
	public static final int LOGGER_TYPE_PVP_INTEGRAL= 12;
	/**扫荡次数*/
	public static final int LOGGER_TYPE_SWEEP_COUNT = 13;
	/**领取活动礼包日志*/
	public static final int LOGGER_TYPE_ACTIVITY_GIFT = 14;
	
	/**编号*/
	private int id;
	/**用户编号*/
	private int userId;
	/**日志类型*/
	private int loggerType;
	/**类型*/
	private int type;
	/**关联编号*/
	private int assId;
	/**数量*/
	private int num;
	/**描述*/
	private String description;
	/**创建时间*/
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getLoggerType() {
		return loggerType;
	}
	public void setLoggerType(int loggerType) {
		this.loggerType = loggerType;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAssId() {
		return assId;
	}
	public void setAssId(int assId) {
		this.assId = assId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
