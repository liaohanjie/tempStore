package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ChapterChestService;
import com.ks.model.dungeon.ChapterChest;

public class ChapterChestServiceImpl extends BaseService implements ChapterChestService {

	@Override
    public List<ChapterChest> queryAll() {
	    return chapterChestDAO.queryAll();
    }

}
