package com.ks.protocol.vo.game;

import com.ks.model.game.Stat;
import com.ks.protocol.Message;

/**
 * 状态
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月4日
 */
public class StatVO extends Message {

    private static final long serialVersionUID = 6689289197359843425L;
    
	/**编号*/
	private int id;
	/**值*/
	private long value; 

	public void init(Stat o) {
		this.id = o.getId();
		this.value = o.getValue();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getValue() {
		return value;
	}

	public void setValue(long value) {
		this.value = value;
	}

}
