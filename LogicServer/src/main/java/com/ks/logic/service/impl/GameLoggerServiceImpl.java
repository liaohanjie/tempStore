package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.GameLoggerService;
import com.ks.model.filter.GameLoggerFilter;
import com.ks.model.logger.AthleticsInfoLog;
import com.ks.model.logger.BakPropLogger;
import com.ks.model.logger.CurrencyLogger;
import com.ks.model.logger.ExpLogger;
import com.ks.model.logger.FriendlyPointLogger;
import com.ks.model.logger.GameLogger;
import com.ks.model.logger.GoldLogger;
import com.ks.model.logger.GoodsLogger;
import com.ks.model.logger.SoulLogger;
import com.ks.model.logger.StaminaLogger;
import com.ks.model.logger.SweepCountLogger;

public class GameLoggerServiceImpl extends BaseService implements GameLoggerService {

	@Override
	public void addGameLogger(GameLogger logger) {
		gameLoggerDAO.addGameLogger(logger);
	}

	@Override
	public List<SoulLogger> getSoulLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getSoulLoggers(filter);
	}

	@Override
	public Integer getSoulLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getSoulLoggersCount(filter);
	}

	@Override
	public List<StaminaLogger> getStaminLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getStaminLoggers(filter);
	}

	@Override
	public Integer getStaminaLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getStaminLoggersCount(filter);
	}

	@Override
	public List<GoldLogger> getGoldLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getGoldLoggers(filter);
	}

	@Override
	public Integer getGoldLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getGoldLoggersCount(filter);
	}

	@Override
	public List<CurrencyLogger> getCurrencyLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getCurrencyLoggers(filter);
	}

	@Override
	public Integer getCurrencyLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getCurrencyLoggersCount(filter);
	}

	@Override
	public List<ExpLogger> getExpLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getExpLoggers(filter);
	}

	@Override
	public Integer getExpLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getExpLoggersCount(filter);
	}

	@Override
	public List<AthleticsInfoLog> getAthleticsPointLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getAthleticsPointLoggers(filter);
	}

	@Override
	public Integer getAthleticsPointLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getAthleticsPointLoggersCount(filter);
	}

	@Override
	public List<AthleticsInfoLog> getAthleticsScoreLoggers(
			GameLoggerFilter filter) {
		return gameLoggerDAO.getAthleticsScoreLoggers(filter);
	}

	@Override
	public Integer getAthleticsScoreLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getAthleticsScoreLoggersCount(filter);
	}

	@Override
	public List<SweepCountLogger> getSweppCountLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getSweppCountLoggers(filter);
	}

	@Override
	public Integer getSweppCountLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getSweppCountLoggersCount(filter);
	}

	@Override
	public List<FriendlyPointLogger> getFriendlyPointLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getFriendlyPointLoggers(filter);
	}

	@Override
	public Integer getFriendlyPointLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getFriendlyPointLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getStuffLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getStuffLoggers(filter);
	}

	@Override
	public Integer getStuffLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getStuffLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getEquipmentLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getEquipmentLoggers(filter);
	}

	@Override
	public Integer getEquipmentLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getEquipmentLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getPropLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getPropLoggers(filter);
	}

	@Override
	public Integer getPropLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getPropLoggersCount(filter);
	}

	@Override
	public List<BakPropLogger> getBakPropLoggers(GameLoggerFilter filter) {
		return gameLoggerDAO.getBakPropLoggers(filter);
	}

	@Override
	public Integer getBakPropLoggersCount(GameLoggerFilter filter) {
		return gameLoggerDAO.getBakPropLoggersCount(filter);
	}

}
