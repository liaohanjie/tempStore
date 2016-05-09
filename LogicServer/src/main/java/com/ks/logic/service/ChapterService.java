package com.ks.logic.service;

import java.util.List;

import com.ks.model.dungeon.Box;
import com.ks.model.dungeon.Chapter;
import com.ks.model.dungeon.ChapterJoin;
import com.ks.model.dungeon.ChapterRound;
import com.ks.model.dungeon.Drop;
import com.ks.model.dungeon.Monster;

/**
 * 地下城服务
 * @author ks
 */
public interface ChapterService {
	/**
	 * 查询所有地下城
	 * @return 所有地下城
	 */
	List<Chapter> queryAllChapters();
	/**
	 * 查询所有地下城怪物
	 * @return
	 */
	List<Monster> queryAllMonster();
	/**
	 * 查询所有掉落
	 * @return
	 */
	List<Drop> queryAllDrop();
	/**
	 * 查询所有箱子
	 * @return
	 */
	List<Box> queryAllBoxs();
	
	List<ChapterRound> queryAllRounds();
	
	List<ChapterJoin> queryChapterJoin();
	
}
