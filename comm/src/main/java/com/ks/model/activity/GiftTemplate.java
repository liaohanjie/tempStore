package com.ks.model.activity;

import java.io.Serializable;
import java.util.List;

import com.ks.model.goods.Goods;

/***
 * 礼包模版
 * 
 * @author lipp 2015年7月6日
 */
public class GiftTemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;

	/** 礼包名称 */
	private String giftTemplateName;

	/** 礼包物品 */
	private List<Goods> giftTemplateGoods;
	
	private String giftTemplateString;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGiftTemplateName() {
		return giftTemplateName;
	}

	public void setGiftTemplateName(String giftTemplateName) {
		this.giftTemplateName = giftTemplateName;
	}

	public List<Goods> getGiftTemplateGoods() {
		return giftTemplateGoods;
	}

	public void setGiftTemplateGoods(List<Goods> giftTemplateGoods) {
		this.giftTemplateGoods = giftTemplateGoods;
	}

	public String getGiftTemplateString() {
		return giftTemplateString;
	}

	public void setGiftTemplateString(String giftTemplateString) {
		this.giftTemplateString = giftTemplateString;
	}

	@Override
	public String toString() {
		return "GiftTemplate [id=" + id + ", giftTemplateName=" + giftTemplateName + ", giftTemplateGoods=" + giftTemplateGoods + "]";
	}

}
