package com.ks.logic.service.impl;

import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.ChapterAwardService;
import com.ks.model.dungeon.ChapterAward;

public class ChapterAwardServiceImpl extends BaseService implements ChapterAwardService {

	@Override
    public List<ChapterAward> queryAll() {
	    return chapterAwardDAO.queryAll();
    }

}
