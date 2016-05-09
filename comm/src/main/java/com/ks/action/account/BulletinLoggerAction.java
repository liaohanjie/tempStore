package com.ks.action.account;

import java.util.List;

import com.ks.model.notice.BulletinLogger;

/**
 * 跑马灯公告日志
 * 
 * @author lipp 2016年3月9日
 */
public interface BulletinLoggerAction {
	/**
	 * 保存跑马灯公告日志
	 */
	void saveLogger(BulletinLogger logger);

	/**
	 * 查询日志
	 * 
	 * @return
	 */
	List<BulletinLogger> getBulletinLogger();

	/**
	 * 得到日志数量
	 * 
	 * @return
	 */
	Integer getBulletinLogCount();
}
