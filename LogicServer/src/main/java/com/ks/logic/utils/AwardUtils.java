package com.ks.logic.utils;

import java.util.ArrayList;
import java.util.List;

import com.ks.model.Award;
import com.ks.model.goods.Goods;


/**
 * 奖励工具
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月23日
 */
public class AwardUtils {
	
	public static List<Goods> getGoodsList(List<Award> list) {
		List<Goods> goodsList = new ArrayList<>();
		if (list != null) {
			for (Award a : list) {
				if (Math.random() < a.getRate()) {
					Goods g = new Goods();
					g.setGoodsId(a.getGoodsId());
					g.setType(a.getGoodsType());
					g.setNum(a.getNum());
					g.setLevel(a.getLevel());
					goodsList.add(g);
				}
			}
		}
		return goodsList;
	}
}
