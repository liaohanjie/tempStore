package com.ks.model.boss;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.ks.model.goods.Goods;

/**
 * boss开启设置
 * @author hanjie.l
 *
 */
public class BossOpenSetting {

	/**
	 * Id
	 */
	private int bossId;

	/**
	 * 开启时间
	 */
	private List<BeginEndTime> beginEndTimes = new ArrayList<>();

	/**
	 * 鼓舞消耗金币
	 */
	private int costGold;

	/**
	 * 金币鼓舞增加值
	 */
	private int goldAdd;

	/**
	 * 钻石鼓舞消耗钻石
	 */
	private int costDiamond;

	/**
	 * 钻石鼓舞增加值
	 */
	private int diamondAdd;

	/**
	 * 鼓舞上限
	 */
	private int uplimit;
	
	/**
	 * 参与等级
	 */
	private int joinLevel;
	
	/**
	 * 击杀奖励
	 */
	private List<Goods> joinRewards;

	public int getBossId() {
		return bossId;
	}

	public void setBossId(int bossId) {
		this.bossId = bossId;
	}

	public List<BeginEndTime> getBeginEndTimes() {
		return beginEndTimes;
	}

	public void setBeginEndTimes(List<BeginEndTime> beginEndTimes) {
		this.beginEndTimes = beginEndTimes;
	}

	public void setBeginEndTimes(String beginEndString) {
		String[] split = beginEndString.split(";");
		for(String temp : split){
			try {
				this.beginEndTimes.add(new BeginEndTime(temp));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public int getCostGold() {
		return costGold;
	}

	public void setCostGold(int costGold) {
		this.costGold = costGold;
	}

	public int getGoldAdd() {
		return goldAdd;
	}

	public void setGoldAdd(int goldAdd) {
		this.goldAdd = goldAdd;
	}

	public int getCostDiamond() {
		return costDiamond;
	}

	public void setCostDiamond(int costDiamond) {
		this.costDiamond = costDiamond;
	}

	public int getDiamondAdd() {
		return diamondAdd;
	}

	public void setDiamondAdd(int diamondAdd) {
		this.diamondAdd = diamondAdd;
	}

	public int getUplimit() {
		return uplimit;
	}

	public void setUplimit(int uplimit) {
		this.uplimit = uplimit;
	}

	public int getJoinLevel() {
		return joinLevel;
	}

	public void setJoinLevel(int joinLevel) {
		this.joinLevel = joinLevel;
	}

	public List<Goods> getJoinRewards() {
		return joinRewards;
	}

	public void setJoinRewards(List<Goods> joinRewards) {
		this.joinRewards = joinRewards;
	}
}
