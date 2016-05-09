package com.ks.action.world;

import java.util.List;

import com.ks.model.notice.Notice;

public interface WorldNoticeAction {
	public List<Notice> query();

	public void update(Notice bulletin);

	public void add(Notice bulletin);

	public void delete(int id);

}
