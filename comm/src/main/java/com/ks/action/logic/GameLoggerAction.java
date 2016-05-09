package com.ks.action.logic;

import java.util.List;

import com.ks.model.filter.GameLoggerFilter;
import com.ks.model.logger.AthleticsInfoLog;
import com.ks.model.logger.BakPropLogger;
import com.ks.model.logger.CurrencyLogger;
import com.ks.model.logger.ExpLogger;
import com.ks.model.logger.FriendlyPointLogger;
import com.ks.model.logger.GoldLogger;
import com.ks.model.logger.GoodsLogger;
import com.ks.model.logger.SoulLogger;
import com.ks.model.logger.StaminaLogger;
import com.ks.model.logger.SweepCountLogger;

public interface GameLoggerAction {
	/**
	 * 查询战魂日志
	 * 
	 * @param serverId
	 * @param soulLogger
	 * @return
	 */
	public List<SoulLogger> getSoulLoggers(GameLoggerFilter soulLogger);

	/**
	 * 得到战魂日志总数量
	 * 
	 * 
	 * @param serverId
	 * @param soulLogger
	 * @return
	 */
	public Integer getSoulLoggersCount(GameLoggerFilter soulLogger);
	
	/**
	 * 查询体力日志
	 * 
	 * @param filter
	 * @return
	 */
	public List<StaminaLogger> getStaminLoggers(GameLoggerFilter filter);

	/**
	 * 得到体力日志总数量
	 * 
	 * @param filter
	 * @return
	 */
	public Integer getStaminaLoggersCount(GameLoggerFilter filter);
	
	/**
	 * 查询金币日志
	 * 
	 * @param filter
	 * @return
	 */
	public List<GoldLogger> getGoldLoggers(GameLoggerFilter filter);
	
	/**
	 * 得到金币日志总数量
	 * 
	 * @param filter
	 * @return
	 */
	public Integer getGoldLoggersCount(GameLoggerFilter filter);
	/**
	 * 查询货币日志
	 * 
	 * @param filter
	 * @return
	 */
	public List<CurrencyLogger> getCurrencyLoggers(GameLoggerFilter filter);
	
	/**
	 * 得到货币日志总数量
	 * 
	 * @param filter
	 * @return
	 */
	public Integer getCurrencyLoggersCount(GameLoggerFilter filter);
	
	/**
	 * 查询经验日志
	 * 
	 * @param filter
	 * @return
	 */
	public List<ExpLogger> getExpLoggers(GameLoggerFilter filter);
	
	/**
	 * 得到经验日志总数量
	 * 
	 * @param filter
	 * @return
	 */
	public Integer getExpLoggersCount(GameLoggerFilter filter);
	
	/**
	 * 查询竞技点日志
	 * @param filter
	 * @return
	 */
	public List<AthleticsInfoLog> getAthleticsPointLoggers(GameLoggerFilter filter);
	
	/**
	 * 得到竞技点日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getAthleticsPointLoggersCount(GameLoggerFilter filter);
	
	/**
	 * 查询竞技分数日志
	 * @param filter
	 * @return
	 */
	public List<AthleticsInfoLog> getAthleticsScoreLoggers(GameLoggerFilter filter);
	
	/***
	 * 得到竞技分数日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getAthleticsScoreLoggersCount(GameLoggerFilter filter);
	
	/**
	 * 查询扫荡次数日志
	 * @param filter
	 * @return
	 */
	public List<SweepCountLogger> getSweppCountLoggers(GameLoggerFilter filter);
	
	/***
	 * 得到扫荡次数日记总数量
	 * @param filter
	 * @return
	 */
	public Integer getSweppCountLoggersCount(GameLoggerFilter filter);
	
	/**
	 * 查询友情点日志
	 * @param filter
	 * @return
	 */
	public List<FriendlyPointLogger> getFriendlyPointLoggers(GameLoggerFilter filter);

	/**
	 * 得到友情点日志总数量
	 * @param filter
	 * @return
	 */
	public Integer getFriendlyPointLoggersCount(GameLoggerFilter filter);
	
	/**
	 * 查询材料日志
	 * @param filter
	 * @return
	 */
	public List<GoodsLogger> getStuffLoggers(GameLoggerFilter filter);
	
	/**
	 * 得到材料日志总数量
	 * @param filter
	 * @return
	 */
    public Integer getStuffLoggersCount(GameLoggerFilter filter);

    /**
     * 查询装备日志
     * @param filter
     * @return
     */
    public List<GoodsLogger> getEquipmentLoggers(GameLoggerFilter filter);

    /**
     * 得到装备日志总数量
     * @param filter
     * @return
     */
    public Integer getEquipmentLoggersCount(GameLoggerFilter filter);
    
    /**
     * 查询道具日志
     * @param filter
     * @return
     */
    public List<GoodsLogger> getPropLoggers(GameLoggerFilter filter);

    /**
     * 得到道具日志总数量
     * @param filter
     * @return
     */
    public Integer getPropLoggersCount(GameLoggerFilter filter);
    
    /**
     * 查询副本道具日志
     * @param filter
     * @return
     */
    public List<BakPropLogger> getBakPropLoggers(GameLoggerFilter filter);

    /**
     * 得到查询副本道具日志总数量
     * @param filter
     * @return
     */
    public Integer getBakPropLoggersCount(GameLoggerFilter filter);
}
