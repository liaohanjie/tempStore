package com.ks.action.logic;

import com.ks.protocol.vo.game.StatVO;

/**
 * 全局状态相关
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年1月11日
 */
public interface StatAction {
	
	/**
	 * 获取全局状态
	 * @param id
	 * @return
	 */
	StatVO findById(int id);
}
