package com.ks.logic.service;

import java.util.List;

import com.ks.model.dungeon.ChapterChest;


/**
 * 章节宝箱配置
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月23日
 */
public interface ChapterChestService {
	
	/**
	 * 查找所有
	 * @return
	 */
	List<ChapterChest> queryAll();
	
}