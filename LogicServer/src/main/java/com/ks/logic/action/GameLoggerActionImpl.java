package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.GameLoggerAction;
import com.ks.logic.service.GameLoggerService;
import com.ks.logic.service.ServiceFactory;
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

public class GameLoggerActionImpl implements GameLoggerAction {
	
	private GameLoggerService gameLoggerService = ServiceFactory
			.getService(GameLoggerService.class);

	@Override
	public List<SoulLogger> getSoulLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getSoulLoggers(filter);
	}

	@Override
	public Integer getSoulLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getSoulLoggersCount(filter);
	}

	@Override
	public List<StaminaLogger> getStaminLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getStaminLoggers(filter);
	}

	@Override
	public Integer getStaminaLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getStaminaLoggersCount(filter);
	}

	@Override
	public List<GoldLogger> getGoldLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getGoldLoggers(filter);
	}

	@Override
	public Integer getGoldLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getGoldLoggersCount(filter);
	}

	@Override
	public List<CurrencyLogger> getCurrencyLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getCurrencyLoggers(filter);
	}

	@Override
	public Integer getCurrencyLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getCurrencyLoggersCount(filter);
	}

	@Override
	public List<ExpLogger> getExpLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getExpLoggers(filter);
	}

	@Override
	public Integer getExpLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getExpLoggersCount(filter);
	}

	@Override
	public List<AthleticsInfoLog> getAthleticsPointLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getAthleticsPointLoggers(filter);
	}

	@Override
	public Integer getAthleticsPointLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getAthleticsPointLoggersCount(filter);
	}

	@Override
	public List<AthleticsInfoLog> getAthleticsScoreLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getAthleticsScoreLoggers(filter);
	}

	@Override
	public Integer getAthleticsScoreLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getAthleticsScoreLoggersCount(filter);
	}

	@Override
	public List<SweepCountLogger> getSweppCountLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getSweppCountLoggers(filter);
	}

	@Override
	public Integer getSweppCountLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getSweppCountLoggersCount(filter);
	}

	@Override
	public List<FriendlyPointLogger> getFriendlyPointLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getFriendlyPointLoggers(filter);
	}

	@Override
	public Integer getFriendlyPointLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getFriendlyPointLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getStuffLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getStuffLoggers(filter);
	}

	@Override
	public Integer getStuffLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getStuffLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getEquipmentLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getEquipmentLoggers(filter);
	}

	@Override
	public Integer getEquipmentLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getEquipmentLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getPropLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getPropLoggers(filter);
	}

	@Override
	public Integer getPropLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getPropLoggersCount(filter);
	}

	@Override
	public List<BakPropLogger> getBakPropLoggers(GameLoggerFilter filter) {
		return gameLoggerService.getBakPropLoggers(filter);
	}

	@Override
	public Integer getBakPropLoggersCount(GameLoggerFilter filter) {
		return gameLoggerService.getBakPropLoggersCount(filter);
	}
}
