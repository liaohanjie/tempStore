package com.ks.logic.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.notice.Notice;
import com.ks.protocol.vo.notice.NoticeVO;

public interface NoticeService {

	/**
	 * 拉取公告
	 * 
	 * @return
	 */
	@Transaction
	List<NoticeVO> poll();

	/**
	 * 添加公告
	 * 
	 * @param entity
	 */
	@Transaction
	void add(Notice entity);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@Transaction
	void delete(int id);

	/**
	 * 修改
	 * 
	 * @param entity
	 */
	@Transaction
	void update(Notice entity);

	/**
	 * 查询
	 * 
	 * @return
	 */
	List<Notice> query();

}
