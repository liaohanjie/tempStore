package com.ks.account.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.activity.GiftTemplate;
import com.ks.model.goods.Goods;
import com.ks.model.goods.GoodsTemplate;
import com.ks.util.JSONUtil;

/***
 * 礼包模版
 * 
 * @author lipp 2015年7月7日
 */
public class GiftTemplateDAO extends GameDAOTemplate {

	private static final RowMapper<GiftTemplate> GIFT_TEMPLATE_ROW = new RowMapper<GiftTemplate>() {
		@Override
		public GiftTemplate rowMapper(ResultSet rs) throws SQLException {
			GiftTemplate giftTemplate = new GiftTemplate();
			giftTemplate.setId(rs.getInt("id"));
			giftTemplate.setGiftTemplateName(rs.getString("gift_template_name"));
			String goods = rs.getString("gift_template_goods");
			giftTemplate.setGiftTemplateGoods(getGoodsList(goods));
			giftTemplate.setGiftTemplateString(goods);
			return giftTemplate;
		}

		private List<Goods> getGoodsList(String json) {
			if (json == null || "".equals(json)) {
				return null;
			} else {
				return JSONUtil.toObject(json, new TypeReference<List<Goods>>() {
				});
			}
		}
	};

	private static final RowMapper<GoodsTemplate> GOODS_TEMPLATE_ROW = new RowMapper<GoodsTemplate>() {
		@Override
		public GoodsTemplate rowMapper(ResultSet rs) throws SQLException {
			GoodsTemplate goodsTempate = new GoodsTemplate();
			goodsTempate.setGoodsId(rs.getInt("goodsId"));
			goodsTempate.setName(rs.getString("name"));
			goodsTempate.setType(rs.getInt("type"));
			goodsTempate.setLevel(rs.getInt("level"));
			return goodsTempate;
		}

	};

	/**
	 * 修改礼包模版
	 * 
	 * @param gift
	 */
	public void updateGiftTemplate(GiftTemplate gift) {
		String sql = " update  t_gift_template set gift_template_name=?,gift_template_goods=? where id=? ";
		saveOrUpdate(sql, gift.getGiftTemplateName(), getGoodsListJson(gift.getGiftTemplateGoods()), gift.getId());
	}

	/**
	 * 删除礼包模版
	 * 
	 * @param id
	 */
	public void deleteGiftTemplate(int id) {
		String sql = " delete from t_gift_template where id=?";
		saveOrUpdate(sql, id);
	}

	/**
	 * 增加礼包模版
	 * 
	 * @param gift
	 */
	public void addGiftTemplate(GiftTemplate gift) {
		String sql = " insert  into t_gift_template (gift_template_name,gift_template_goods) values(?,?)";
		saveOrUpdate(sql, gift.getGiftTemplateName(), getGoodsListJson(gift.getGiftTemplateGoods()));
	}

	private String getGoodsListJson(List<Goods> goodsList) {
		if (goodsList == null) {
			return null;
		} else {
			return JSONUtil.toJson(goodsList);
		}
	}

	/**
	 * 查询礼包模版
	 * 
	 * @return
	 */
	public List<GiftTemplate> queryGiftTemplate() {
		String sql = "select * from t_gift_template";
		return queryForList(sql, GIFT_TEMPLATE_ROW);
	}

	/**
	 * 查询所有物品
	 * 
	 * @return
	 */
	public List<GoodsTemplate> queryGoodsTemplate() {
		String sql = "select * from t_goods_template";
		return queryForList(sql, GOODS_TEMPLATE_ROW);
	}
}
