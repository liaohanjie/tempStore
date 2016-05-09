package com.ks.logic.service;

import java.util.List;

import com.ks.model.climb.ClimbTower;


/**
 * 爬塔试炼配置 Service
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月13日
 */
public interface ClimbTowerService {
	
	/**
	 * 查找所有
	 * @return
	 */
	List<ClimbTower> queryAll();
	
}