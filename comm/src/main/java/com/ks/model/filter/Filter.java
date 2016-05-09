package com.ks.model.filter;

import java.io.Serializable;

public class Filter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int pageSize=15;

	private int pageIndex=1;

	public  int getStart(){
		return (this.pageIndex-1)*pageSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	@Override
	public String toString() {
		return "Filter [pageSize=" + pageSize + ", pageIndex=" + pageIndex
				+ "]";
	}
	
	
	
}
