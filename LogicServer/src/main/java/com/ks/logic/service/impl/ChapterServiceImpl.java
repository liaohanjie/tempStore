package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ChapterService;
import com.ks.model.dungeon.Box;
import com.ks.model.dungeon.Chapter;
import com.ks.model.dungeon.ChapterJoin;
import com.ks.model.dungeon.ChapterRound;
import com.ks.model.dungeon.Drop;
import com.ks.model.dungeon.Monster;

public class ChapterServiceImpl extends BaseService implements ChapterService {

	@Override
	public List<Monster> queryAllMonster() {
		return chapterCfgDAO.queryAllMonster();
	}

	@Override
	public List<Drop> queryAllDrop() {
		return chapterCfgDAO.queryAllDrop();
	}

	@Override
	public List<Chapter> queryAllChapters() {
		return chapterCfgDAO.queryAllChapters();
	}

	@Override
	public List<Box> queryAllBoxs() {
		return chapterCfgDAO.queryAllBoxs();
	}

	@Override
	public List<ChapterRound> queryAllRounds() {
		return chapterCfgDAO.queryAllRounds();
	}

	@Override
	public List<ChapterJoin> queryChapterJoin() {
		return chapterCfgDAO.queryAllChpaterJoin();
	}

	
}
