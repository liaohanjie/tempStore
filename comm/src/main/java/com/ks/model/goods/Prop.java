package com.ks.model.goods;

import java.io.Serializable;
import java.util.List;

/**
 * 道具
 * 
 * @author ks
 */
public class Prop implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int ITEM_回复药 = 1;
	public static final int ITEM_回复药II = 2;
	public static final int ITEM_牛王汁 = 3;
	public static final int ITEM_元神汤 = 4;
	public static final int ITEM_罗汉茶 = 5;
	public static final int ITEM_凌晨露 = 6;
	public static final int ITEM_上天之泪 = 7;
	public static final int ITEM_斗酒众 = 8;
	public static final int ITEM_石肤油 = 9;
	public static final int ITEM_斗酒火印 = 10;
	public static final int ITEM_斗酒土印 = 11;
	public static final int ITEM_斗酒气印 = 12;
	public static final int ITEM_斗酒水印 = 13;
	public static final int ITEM_斗酒光印 = 14;
	public static final int ITEM_斗酒暗印 = 15;
	public static final int ITEM_护酒火符 = 16;
	public static final int ITEM_护酒土符 = 17;
	public static final int ITEM_护酒气符 = 18;
	public static final int ITEM_护酒水符 = 19;
	public static final int ITEM_护酒光符 = 20;
	public static final int ITEM_护酒暗符 = 21;
	public static final int ITEM_武神酒 = 22;
	public static final int ITEM_复归之路 = 23;
	public static final int ITEM_仇恨之尘 = 24;

	/** 道具编号 */
	private int propId;
	/**道具名称*/
	private String name;
	/** 堆叠数量 */
	private int stackNum;
	/**战斗可携带数量*/
	private int fightTakeNum;
	/** 出售价格 */
	private int sellPrice;
	/** 道具效果 */
	private List<PropEffect> effects;
	
	public int getPropId() {
		return propId;
	}
	public void setPropId(int propId) {
		this.propId = propId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStackNum() {
		return stackNum;
	}
	public void setStackNum(int stackNum) {
		this.stackNum = stackNum;
	}
	public int getFightTakeNum() {
		return fightTakeNum;
	}
	public void setFightTakeNum(int fightTakeNum) {
		this.fightTakeNum = fightTakeNum;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public List<PropEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<PropEffect> effects) {
		this.effects = effects;
	}

}
