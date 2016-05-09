package com.ks.logic.action;

import com.ks.action.logic.StatAction;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.StatService;
import com.ks.model.game.Stat;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.game.StatVO;
/**
 * 重读游戏缓存
 * @author hanjie.l
 *
 */
public class StatActionImpl implements StatAction {

	private static StatService statService = ServiceFactory.getService(StatService.class);
	
	@Override
    public StatVO findById(int id) {
		Stat o = statService.findById(id);
		if (o != null) {
			StatVO vo = MessageFactory.getMessage(StatVO.class);
			vo.init(o);
		    return vo;
		} else {
			return null;
		}
    }

}
