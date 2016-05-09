package com.ks.wrold.action;

import java.util.List;
import com.ks.action.logic.GameLoggerAction;
import com.ks.action.world.WorldGameLoggerAction;
import com.ks.app.Application;
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
import com.ks.rpc.RPCKernel;

public class WorldGameLoggerActionImpl implements WorldGameLoggerAction {
	public static GameLoggerAction gameLoggerAction() {
		GameLoggerAction gameLoggerActoon = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, GameLoggerAction.class);
		return gameLoggerActoon;
	}

	@Override
	public List<SoulLogger> getSoulLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getSoulLoggers( filter);
	}

	@Override
	public Integer getSoulLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getSoulLoggersCount(filter);
	}

	@Override
	public List<StaminaLogger> getStaminLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getStaminLoggers(filter);
	}

	@Override
	public Integer getStaminaLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getStaminaLoggersCount(filter);
	}

	@Override
	public List<GoldLogger> getGoldLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getGoldLoggers(filter);
	}

	@Override
	public Integer getGoldLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getGoldLoggersCount(filter);
	}

	@Override
	public List<CurrencyLogger> getCurrencyLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getCurrencyLoggers(filter);
	}

	@Override
	public Integer getCurrencyLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getCurrencyLoggersCount(filter);
	}

	@Override
	public List<ExpLogger> getExpLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getExpLoggers(filter);
	}

	@Override
	public Integer getExpLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getExpLoggersCount(filter);
	}

	@Override
	public List<AthleticsInfoLog> getAthleticsPointLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getAthleticsPointLoggers(filter);
	}

	@Override
	public Integer getAthleticsPointLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getAthleticsPointLoggersCount(filter);
	}

	@Override
	public List<AthleticsInfoLog> getAthleticsScoreLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getAthleticsScoreLoggers(filter);
	}

	@Override
	public Integer getAthleticsScoreLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getAthleticsScoreLoggersCount(filter);
	}

	@Override
	public List<SweepCountLogger> getSweppCountLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getSweppCountLoggers(filter);
	}

	@Override
	public Integer getSweppCountLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getSweppCountLoggersCount(filter);
	}

	@Override
	public List<FriendlyPointLogger> getFriendlyPointLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getFriendlyPointLoggers(filter);
	}

	@Override
	public Integer getFriendlyPointLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getFriendlyPointLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getStuffLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getStuffLoggers(filter);
	}

	@Override
	public Integer getStuffLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getStuffLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getEquipmentLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getEquipmentLoggers(filter);
	}

	@Override
	public Integer getEquipmentLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getEquipmentLoggersCount(filter);
	}

	@Override
	public List<GoodsLogger> getPropLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getPropLoggers(filter);
	}

	@Override
	public Integer getPropLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getPropLoggersCount(filter);
	}

	@Override
	public List<BakPropLogger> getBakPropLoggers(GameLoggerFilter filter) {
		return gameLoggerAction().getBakPropLoggers(filter);
	}

	@Override
	public Integer getBakPropLoggersCount(GameLoggerFilter filter) {
		return gameLoggerAction().getBakPropLoggersCount(filter);
	}
}
