package com.ks.model.goods;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 * 奖励合并构造器
 * @author hanjie.l
 *
 */
public class RewardBuilder {
	
	private List<Goods> rewards = new ArrayList<Goods>();
	
	// 当前是否是已经合并了打结果
	private boolean combined = true;
	
	
	/**
	 * 添加奖励
	 * @param good
	 * @return
	 */
	public RewardBuilder addReward(Goods good){
		this.rewards.add(good);
		combined = false;
		return this;
	}
	
	/**
	 * 添加奖励
	 * @param goods
	 * @return
	 */
	public RewardBuilder addReward(Collection<Goods> goods){
		this.rewards.addAll(goods);
		combined = false;
		return this;
	}
	
	
	/**
	 * 执行合并操作
	 * 
	 * @return
	 */
	public RewardBuilder combine() {
		if (!combined) {
			List<Goods> list = new ArrayList<Goods>();
			for (Goods reward : rewards) {
				Goods cloneReward = reward.clone();
				boolean matched = false;
				for (Goods rewardInSet : list) {
					//这几种类型无法合并数量
					if(cloneReward.getType() == Goods.TYPE_PROP || cloneReward.getType() == Goods.TYPE_STUFF || cloneReward.getType() == Goods.TYPE_EQUIPMENT || cloneReward.getType() == Goods.TYPE_SOUL){
						break;
					}
					//如有合并同类型
					if (rewardInSet.getType() == cloneReward.getType()) {
						rewardInSet.setNum(rewardInSet.getNum() + cloneReward.getNum());
						matched = true;
					}
				}
				if (!matched) {
					list.add(cloneReward);
				}
			}
			this.rewards.clear();
			this.rewards.addAll(list);
			combined = true;
		}
		return this;
	}
	
	public List<Goods> build(){
		return rewards;
	}

}
