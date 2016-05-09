package com.ks.protocol.vo.buding;

import com.ks.protocol.Message;
/**
 * 采集材料
 * @author ks
 */
public class CollectStuffVO extends Message {

	private static final long serialVersionUID = 1L;
	/**材料编号*/
	private int stuffId;
	/**数量*/
	private int num;
	public int getStuffId() {
		return stuffId;
	}
	public void setStuffId(int stuffId) {
		this.stuffId = stuffId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
}
