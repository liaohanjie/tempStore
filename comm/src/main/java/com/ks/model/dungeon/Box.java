package com.ks.model.dungeon;

import java.io.Serializable;
/**
 * 宝箱
 * @author ks
 */
public class Box implements Serializable {

	
	public static final int TYPE_普通宝箱=1;
	public static final int TYPE_怪物宝箱=2;
	
	private static final long serialVersionUID = 1L;
	/**箱子Id*/
	private int boxId;
	/**类型*/
	private int type;
	/**掉落Id*/
	private int dropId;
	
	
	public int getBoxId() {
		return boxId;
	}
	public void setBoxId(int boxId) {
		this.boxId = boxId;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDropId() {
		return dropId;
	}
	public void setDropId(int dropId) {
		this.dropId = dropId;
	}	
	
	
}
