package com.ks.logic.event;

import com.ks.event.GameEvent;
import com.ks.logic.service.GameLoggerService;
import com.ks.logic.service.ServiceFactory;
import com.ks.model.logger.GameLogger;
/**
 * 游戏日志事件
 * @author ks
 */
public class GameLoggerEvent extends GameEvent {
	
	private GameLogger logger;
	
	public GameLoggerEvent(GameLogger logger) {
		this.logger = logger;
	}
	@Override
	public void runEvent() {
		GameLoggerService service = ServiceFactory.getService(GameLoggerService.class);
		service.addGameLogger(logger);
	}

}
