package com.ks.wrold.action;

import java.util.List;

import com.ks.action.logic.NoticeAction;
import com.ks.action.world.WorldNoticeAction;
import com.ks.app.Application;
import com.ks.model.notice.Notice;
import com.ks.rpc.RPCKernel;

public class WorldNoticeActionImpl implements WorldNoticeAction {
	public static NoticeAction getNoticeAction() {
		NoticeAction action = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, NoticeAction.class);
		return action;
	}

	@Override
	public List<Notice> query() {
		NoticeAction action = getNoticeAction();
		return action.query();
	}

	@Override
	public void update(Notice bulletin) {
		NoticeAction action = getNoticeAction();
		action.update(bulletin);
	}

	@Override
	public void add(Notice bulletin) {
		NoticeAction action = getNoticeAction();
		action.add(bulletin);

	}

	@Override
	public void delete(int id) {
		NoticeAction action = getNoticeAction();
		action.delete(id);
	}

}
