package com.ks.model.equipment;

import java.io.Serializable;
/**
 * 装备效果
 * @author ks
 */
public class EquipmentEffect implements Serializable {
	
	private static final long serialVersionUID = 1L;	
	public static final int EFFECT_TYPE_加血 = 1;
	public static final int EFFECT_TYPE_攻击 = 2;
	public static final int EFFECT_TYPE_防御 = 3;
	public static final int EFFECT_TYPE_回复力 = 4;
	/**装备编号*/
	private int equipmentId;
	/**装备内容*/
	private int effectType;	
	/**效果值*/
	private int addPoint;
	/**效果百分比*/
	private double addPercent;
	
	
	public int getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public int getEffectType() {
		return effectType;
	}
	public void setEffectType(int effectType) {
		this.effectType = effectType;
	}
	public int getAddPoint() {
		return addPoint;
	}
	public void setAddPoint(int addPoint) {
		this.addPoint = addPoint;
	}
	public double getAddPercent() {
		return addPercent;
	}
	public void setAddPercent(double addPercent) {
		this.addPercent = addPercent;
	}
	
	
}
