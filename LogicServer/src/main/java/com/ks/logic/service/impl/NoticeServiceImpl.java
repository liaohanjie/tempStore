package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ks.event.GameEvent;
import com.ks.logic.event.NotifiEvent;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.NoticeService;
import com.ks.model.notice.Notice;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.notice.NoticeVO;
import com.ks.timer.TimerController;

public class NoticeServiceImpl extends BaseService implements NoticeService {

	@Override
	public List<NoticeVO> poll() {
		List<NoticeVO> list = new ArrayList<>();
		for (Notice entity : noticeDAO.queryAllInTime()) {
			NoticeVO vo = MessageFactory.getMessage(NoticeVO.class);
			vo.init(entity);
			list.add(vo);
		}
		return list;
	}

	@Override
	public void add(Notice entity) {
		noticeDAO.add(entity);
		_notifyAllOnlineUser();
	}

	@Override
	public void delete(int id) {
		noticeDAO.delete(id);
		_notifyAllOnlineUser();
	}

	@Override
	public void update(Notice entity) {
		noticeDAO.update(entity);
	}

	private void _notifyAllOnlineUser() {
		GameEvent event = new NotifiEvent(NotifiEvent.USER_ALL_ONOLINE, NotifiEvent.NOTIF_TYPE_跑马灯公告);
		TimerController.submitGameEvent(event);
	}

	@Override
	public List<Notice> query() {
		return noticeDAO.query();
	}

}
