package com.ks.model.account;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author living.li 礼品券
 */
public class GiftCode implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**激活码未使用*/
	public static final int STATE_NOT_UES = 1;
	
	/**激活码已使用*/
	public static final int STATE_UESD = 2;
	
	
	/**同类型礼包的激活码单个用户无限使用*/
	public static final int USE_TYPE_NO_LIMIT = 0;
	
	/**同类型礼包的激活码单个用户单次使用*/
	public static final int USE_TYPE_SINGLE = 1;
	
	/**同类型礼包的激活码所有用户单次使用*/
	public static final int USE_TYPE_SHARE= 2;
	
	/**永久有效*/
	public static final int VALID_TYPE_FOREVER = 0;
	
	/**固定时间有效*/
	public static final int VALID_TYPE_FIX_TIME= 1;
	
	/**礼包ID*/
	private int giftId;

	/** 激活码 */
	private String code;

	/** 状态 */
	private int state;

	/** 奖励Id */
	private String awardId;

	/** 礼品卷奖励 */
	private List<GiftCodeAward> goods;

	/** 创建时间 */
	private Date createTime;

	/** 使用时间 */
	private Date useTime;

	/** 服务器Id */
	private String serverId;

	/** 失效时间 */
	private Date endTime;

	/** 使用类型 */
	private int useType;

	/** 有效类型 */
	private int validType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAwardId() {
		return awardId;
	}

	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}

	public List<GiftCodeAward> getGoods() {
		return goods;
	}

	public void setGoods(List<GiftCodeAward> goods) {
		this.goods = goods;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public final GiftCode copy() {
		try {
			return (GiftCode) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("copy object exception." + e.getMessage());
		}
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getUseType() {
		return useType;
	}

	public void setUseType(int useType) {
		this.useType = useType;
	}

	public int getValidType() {
		return validType;
	}

	public void setValidType(int validType) {
		this.validType = validType;
	}

	public int getGiftId() {
	    return giftId;
    }

	public void setGiftId(int giftId) {
	    this.giftId = giftId;
    }

}
