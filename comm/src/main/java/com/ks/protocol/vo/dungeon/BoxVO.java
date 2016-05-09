package com.ks.protocol.vo.dungeon;

import com.ks.model.dungeon.Box;
import com.ks.protocol.Message;

public class BoxVO  extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	
	/**箱子Id*/
	private int boxId;
	/**类型*/
	private int type;
	
	public void init(Box o){
		this.boxId = o.getBoxId();
		this.type = o.getType();
	}

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
	
}
