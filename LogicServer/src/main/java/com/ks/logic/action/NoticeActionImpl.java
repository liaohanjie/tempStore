package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.NoticeAction;
import com.ks.logic.service.NoticeService;
import com.ks.logic.service.ServiceFactory;
import com.ks.model.notice.Notice;
import com.ks.protocol.vo.notice.NoticeVO;

public class NoticeActionImpl implements NoticeAction {

	@Override
	public List<NoticeVO> poll(int userId) {
		return ServiceFactory.getService(NoticeService.class).poll();
	}

	@Override
	public void add(Notice entity) {
		ServiceFactory.getService(NoticeService.class).add(entity);
	}

	@Override
	public void update(Notice entity) {
		ServiceFactory.getService(NoticeService.class).update(entity);
	}

	@Override
	public void delete(int id) {
		ServiceFactory.getService(NoticeService.class).delete(id);
	}

	@Override
	public List<Notice> query() {
		return ServiceFactory.getService(NoticeService.class).query();
	}

}
