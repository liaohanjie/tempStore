package com.ks.model.account;

import java.io.Serializable;

public class Permiss implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String icon;
	private int parentId;
	private int id;
	private String name;
	private String key;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Permiss [icon=" + icon + ", parentId=" + parentId + ", id="
				+ id + ", name=" + name + "]";
	}

	public String getKey() {
	    return key;
    }

	public void setKey(String key) {
	    this.key = key;
    }

}
