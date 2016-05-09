package com.ks.action.logic;

import java.util.List;

import com.ks.model.notice.Notice;
import com.ks.protocol.vo.notice.NoticeVO;

public interface NoticeAction {

	/**
	 * 获取公告
	 * 
	 * @param userId
	 *            用户编号
	 * @return 所有公告
	 */
	List<NoticeVO> poll(int userId);

	/**
	 * 添加
	 * 
	 * @param entity
	 */
	void add(Notice entity);

	/**
	 * 修改
	 * 
	 * @param entity
	 */
	void update(Notice entity);

	/**
	 * 删除
	 * 
	 * @param id
	 */
	void delete(int id);

	/**
	 * 查询
	 * 
	 * @return
	 */
	List<Notice> query();

}
