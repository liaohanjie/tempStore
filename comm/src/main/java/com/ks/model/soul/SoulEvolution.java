package com.ks.model.soul;

import java.io.Serializable;
import java.util.List;
/**
 * 战魂进化
 * @author ks
 */
public class SoulEvolution implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**进化*/
	public static final int EVO_EVOLUTION = 1;
	/**隐藏进化*/
	public static final int EVO_HIDE_EVOLUTION = 2;
	
	/**基础战魂编号*/
	private int bassSoul;
	/**类型*/
	private int evo;
	/**进化后的战魂编号*/
	private int targetSoul;
	/**配方*/
	private List<Integer> souls;
	/**券轴道具ID*/
	private int scrollPropId;
	/**所需花费金钱*/
	private int gold;
	
	
	public int getScrollPropId() {
		return scrollPropId;
	}
	public void setScrollPropId(int scrollPropId) {
		this.scrollPropId = scrollPropId;
	}
	public int getBassSoul() {
		return bassSoul;
	}
	public void setBassSoul(int bassSoul) {
		this.bassSoul = bassSoul;
	}
	public int getEvo() {
		return evo;
	}
	public void setEvo(int evo) {
		this.evo = evo;
	}
	public int getTargetSoul() {
		return targetSoul;
	}
	public void setTargetSoul(int targetSoul) {
		this.targetSoul = targetSoul;
	}
	public List<Integer> getSouls() {
		return souls;
	}
	public void setSouls(List<Integer> souls) {
		this.souls = souls;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
}
