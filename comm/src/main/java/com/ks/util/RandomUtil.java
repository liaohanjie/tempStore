package com.ks.util;

import java.util.List;
import java.util.Random;

import com.ks.model.Weight;


/**
 * 随机抽取
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年9月10日
 */
public final class RandomUtil {
	
	public static Weight getRandom(List<? extends Weight> list){
		if(list == null || list.isEmpty())
			return null;
		
		int totalWeight = list.get(0).getTotalWeight();
		int randomWeight = new Random().nextInt(totalWeight);
		
		for(Weight weight : list){
			randomWeight = randomWeight - weight.getWeight();
			if(randomWeight <= 0) {
				return weight;
			}
		}
		return null;
	}
}
