package com.ks.model.pvp;

import java.io.Serializable;
/**
 * 升级称号奖励
 * @author aaa
 *
 */
public class AthleticsInfoAward  implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**称号ID*/
	private int id;
	/**奖励类型*/
	private int type;
	/**关联编号*/
	private int assId;
	/**数量*/
	private int num;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getAssId() {
		return assId;
	}
	public void setAssId(int assId) {
		this.assId = assId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
