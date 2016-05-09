package com.ks.model.stuff;

import java.io.Serializable;
/**
 * 材料
 * @author ks
 */
public class Stuff implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**材料编号*/
	private int stuffId;
	/**材料名称*/
	private String stuffName;
	/**材料品质*/
	private int quality;
	/**出售价格*/
	private int sellGold;
	/**捐赠后获得荣誉值*/
	private int addDevote;

	public int getStuffId() {
		return stuffId;
	}
	public void setStuffId(int stuffId) {
		this.stuffId = stuffId;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getSellGold() {
		return sellGold;
	}
	public void setSellGold(int sellGold) {
		this.sellGold = sellGold;
	}
	public String getStuffName() {
		return stuffName;
	}
	public void setStuffName(String stuffName) {
		this.stuffName = stuffName;
	}
	public int getAddDevote() {
		return addDevote;
	}
	public void setAddDevote(int addDevote) {
		this.addDevote = addDevote;
	}
}
