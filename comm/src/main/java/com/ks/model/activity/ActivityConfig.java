package com.ks.model.activity;

import java.io.Serializable;
import java.util.List;

public class ActivityConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> chapterIds;

	public List<Integer> getChapterIds() {
		return chapterIds;
	}
	public void setChapterIds(List<Integer> chapterIds) {
		this.chapterIds = chapterIds;
	}
	
	
}
