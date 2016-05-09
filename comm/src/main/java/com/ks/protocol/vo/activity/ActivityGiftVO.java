package com.ks.protocol.vo.activity;

import java.util.List;

import com.ks.model.activity.ActivityGift;
import com.ks.protocol.Message;
import com.ks.protocol.vo.items.GoodsVO;

/**
 * 活动礼包
 * 
 * @author zhoujf
 * @date 2015年7月7日
 */
public  class  ActivityGiftVO extends Message {

	private static final long serialVersionUID = 1L;
	
	/**领取条件不满足*/
	public static byte STATUS_UNSATISFY = 0x0;
	/**未领取*/
	public static byte STATUS_NO_GET = 0x1;
	/**领取*/
	public static byte STATUS_GET = 0x2;

	/**礼包ID*/
	private int id;
	/**活动定义ID*/
	private int activityDefineId;
	/**关键码(判断条件)*/
	private String key1;
	/**关键码(判断条件)*/
	private String key2;
	/**领取状态 0:领取条件不满足， 1:未领取, 2:领取 */
	private byte status;
	/**活动礼包描述*/
	private String activityGiftDesc;
	/**礼包物品*/
	private List<GoodsVO> goods;
	
	public ActivityGiftVO(){
		
	}

	public void initActivityGiftVO(ActivityGift o, List<GoodsVO> goods) {
	    this.id = o.getId();
	    this.activityDefineId = o.getActivityDefineId();
	    this.key1 = o.getKey1();
	    this.key2 = o.getKey2();
	    this.status = ActivityGiftVO.STATUS_UNSATISFY;
	    this.activityGiftDesc = o.getActivityGiftDesc();
	    this.goods = goods;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getActivityDefineId() {
		return activityDefineId;
	}

	public void setActivityDefineId(int activityDefineId) {
		this.activityDefineId = activityDefineId;
	}

	public String getKey1() {
		return key1;
	}

	public void setKey1(String key1) {
		this.key1 = key1;
	}
	
	public String getKey2() {
		return key2;
	}
	
	public void setKey2(String key2) {
		this.key2 = key2;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public String getActivityGiftDesc() {
		return activityGiftDesc;
	}

	public void setActivityGiftDesc(String activityGiftDesc) {
		this.activityGiftDesc = activityGiftDesc;
	}

	public List<GoodsVO> getGoods() {
		return goods;
	}

	public void setGoods(List<GoodsVO> goods) {
		this.goods = goods;
	}
	
}