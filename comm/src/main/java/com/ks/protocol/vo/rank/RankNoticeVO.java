package com.ks.protocol.vo.rank;

import java.util.ArrayList;
import java.util.List;

import com.ks.protocol.Message;
import com.ks.protocol.vo.items.GoodsVO;

/**
 * 排行公告信息
 * @author hanjie.l
 *
 */
public class RankNoticeVO  extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8044968417488575644L;

	/**
	 * 前N名信息
	 */
	private List<RankerVO> rankers = new ArrayList<>();
	
	/**
	 * 当前玩家排名信息(需要进行空判断，如果没有参加过当前比赛可能没有记录)
	 */
	private RankerVO ownRanker;
	
	/**
	 * boss参与奖励(boss榜专用字段)
	 */
	private List<GoodsVO> joinRewards;
	
	/**
	 * boss排名奖励(boss榜专用字段)
	 */
	private List<GoodsVO> rankRewards;
	
	/**
	 * 是否已经领取奖励
	 */
	private boolean receieveRewards;

	public List<RankerVO> getRankers() {
		return rankers;
	}

	public void setRankers(List<RankerVO> rankers) {
		this.rankers = rankers;
	}

	public RankerVO getOwnRanker() {
		return ownRanker;
	}

	public void setOwnRanker(RankerVO ownRanker) {
		this.ownRanker = ownRanker;
	}

	public List<GoodsVO> getJoinRewards() {
		return joinRewards;
	}

	public void setJoinRewards(List<GoodsVO> joinRewards) {
		this.joinRewards = joinRewards;
	}

	public List<GoodsVO> getRankRewards() {
		return rankRewards;
	}

	public void setRankRewards(List<GoodsVO> rankRewards) {
		this.rankRewards = rankRewards;
	}

	public boolean isReceieveRewards() {
		return receieveRewards;
	}

	public void setReceieveRewards(boolean receieveRewards) {
		this.receieveRewards = receieveRewards;
	}
	
	public boolean noJoinRewards(){
		return joinRewards==null || joinRewards.size() <= 0;
	}
	
	public boolean noRankReards(){
		return rankRewards==null || rankRewards.size() <= 0;
	}
	
	public boolean noRewards(){
		return noJoinRewards() && noRankReards();
	}
	
}
