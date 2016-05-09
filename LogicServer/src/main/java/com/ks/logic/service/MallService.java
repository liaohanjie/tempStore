package com.ks.logic.service;

import java.util.List;

import com.ks.model.pay.Mall;

/**
 * 充值送魂钻服务
 * @author zhoujf
 * @date 2015年6月2日
 */
public interface MallService {
	/**
	 * 查询所有充值送魂钻
	 * @return 所有充值送魂钻
	 */
	List<Mall> queryAllMall();
	
}
