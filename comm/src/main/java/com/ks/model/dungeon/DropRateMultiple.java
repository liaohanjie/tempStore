package com.ks.model.dungeon;

import java.io.Serializable;
/**
 * 
 * @author fengpeng E-mail:fengpeng_15@163.com
 *
 * @version 创建时间：2014年10月14日 上午11:22:13
 */
public class DropRateMultiple  implements Serializable{

private static final long serialVersionUID = 1L;
/***/
private int id;
/**活动id*/
private int defineId;
/**章节的地点*/
private int siteId;
/**掉落概率翻倍*/
private int multiple=1;
/**指定掉落的物品类型*/
private int goodsType;
public int getId(){
	 return id;
}
public void setId( int id){
	 this.id = id;
}
public int getDefineId(){
	 return defineId;
}
public void setDefineId( int defineId){
	 this.defineId = defineId;
}
public int getSiteId(){
	 return siteId;
}
public void setSiteId( int siteId){
	 this.siteId = siteId;
}
public int getMultiple(){
	 return multiple;
}
public void setMultiple( int multiple){
	 this.multiple = multiple;
}
public int getGoodsType(){
	 return goodsType;
}
public void setGoodsType( int goodsType){
	 this.goodsType = goodsType;
}

}