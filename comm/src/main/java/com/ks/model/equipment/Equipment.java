package com.ks.model.equipment;

import java.io.Serializable;
import java.util.List;
/**
 * 装备
 * @author ks
 */
public class Equipment implements Serializable {

	public static final int EQUIPMENT_TYPE_武具=1;
	public static final int EQUIPMENT_TYPE_饰品=2;
	
	private static final long serialVersionUID = 1L;
	/**装备编号*/
	private int equipmentId;
	/**装备类型*/
	private int equipmentType;
	/**使用次数*/
	private int maxDurable;
	/**出售价格*/
	private int sellPrice;
	/**装备名称*/
	private String name;
	/**跑马灯(0:不显示， 1:显示)*/
	private int marquee;
	
	private List<EquipmentEffect> effects;
	
	private List<EquipmentRepair> repairs;
	
	
	
	
	public int getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(int equipmentType) {
		this.equipmentType = equipmentType;
	}
	public int getMaxDurable() {
		return maxDurable;
	}
	public void setMaxDurable(int maxDurable) {
		this.maxDurable = maxDurable;
	}
	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}
	public List<EquipmentEffect> getEffects() {
		return effects;
	}
	public void setEffects(List<EquipmentEffect> effects) {
		this.effects = effects;
	}
	
	public List<EquipmentRepair> getRepairs() {
		return repairs;
	}
	public void setRepairs(List<EquipmentRepair> repairs) {
		this.repairs = repairs;
	}
	public int getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMarquee() {
		return marquee;
	}
	public void setMarquee(int marquee) {
		this.marquee = marquee;
	}
}
