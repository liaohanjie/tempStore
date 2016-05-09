package com.ks.model.explora;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.ks.model.Weight;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * 
 * @version 创建时间：2014年8月8日 下午4:30:57
 * 类说明
 */
public class ExplorationAward implements Weight, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**小时*/
	public static final long ONE_TIME = 60*60*1000L;
	/**探索时间*/
	public static final int[] HOUE_TIME= new int[]{1,3,5,10,20};
	
	/**主键*/
	private int id;
	/**奖励类型*/
	private int type;
	/**关联ID*/
	private int assId;
	/**物品数量*/
	private int num;
	/**探索时间*/
	private int hourTime;
	/**稀有度*/
	private int soulRare;
	/**等级*/
	private int level;
	/**概率*/
	//private double rate;
	/**权重*/
    private int weight;
    /**权重和*/
    private int totalWeight;
	
	public int getId(){
		 return id;
	}
	public void setId( int id){
		 this.id = id;
	}
	public int getType(){
		 return type;
	}
	public void setType( int type){
		 this.type = type;
	}
	public int getAssId(){
		 return assId;
	}
	public void setAssId( int assId){
		 this.assId = assId;
	}
	public int getNum(){
		 return num;
	}
	public void setNum( int num){
		 this.num = num;
	}
	public int getHourTime(){
		 return hourTime;
	}
	public void setHourTime( int hourTime){
		 this.hourTime = hourTime;
	}
	public int getSoulRare(){
		 return soulRare;
	}
	public void setSoulRare( int soulRare){
		 this.soulRare = soulRare;
	}

	/**探索时间消耗的金币*/
	public static int getHourGold(int hour){
		Map<Integer, Integer> hourMap=new HashMap<>();
		hourMap.put(1, 100);
		hourMap.put(3, 300);
		hourMap.put(5, 500);
		hourMap.put(10, 1000);
		hourMap.put(20, 2000);
		return hourMap.get(hour);	
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	/*public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}*/
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getTotalWeight() {
		return totalWeight;
	}
	public void setTotalWeight(int totalWeight) {
		this.totalWeight = totalWeight;
	}

}
