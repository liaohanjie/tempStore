package com.ks.logic.service;

import com.ks.model.game.Stat;

/**
 * 全局状态
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月11日
 */
public interface StatService {

	/**
	 * 查找状态
	 * @param id
	 * @return
	 */
	Stat findById(int id);
}