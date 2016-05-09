package com.ks.model.alliance;
/**
 * 工会商店道具
 * @author admin
 *
 */
public class AllianceShopItem {
	
	/**主键id*/
	private int id;
    /**物品编号*/
    private int goodsId;
    /**物品类型*/
    private int goodsType;
    /**物品等级*/
    private int level;
    /**物品数量*/
    private int num;
    /**需要工会等级*/
    private int allianceLevel;
    /**需要消耗的工会点*/
    private int costDevote;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public int getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(int goodsType) {
		this.goodsType = goodsType;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getAllianceLevel() {
		return allianceLevel;
	}
	public void setAllianceLevel(int allianceLevel) {
		this.allianceLevel = allianceLevel;
	}
	public int getCostDevote() {
		return costDevote;
	}
	public void setCostDevote(int costDevote) {
		this.costDevote = costDevote;
	}
}
