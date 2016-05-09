package com.ks.protocol.vo.swaparena;

import java.util.List;

import com.ks.model.swaparena.SwapArena;
import com.ks.protocol.Message;
/**
 * 交换排名竞技赛详细信息VO
 * @author hanjie.l
 *
 */
public class SwapArenaVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4590633970428710917L;
	
	/**
	 * 可挑战次数
	 */
	private int times;
	
	/**
	 * 今日已购买次数
	 */
	private int todayBuyCount;
	
	/**
	 * 下次可挑战时间
	 */
	private long nextFightTime;
	
	/**
	 * 挑战列表
	 */
	private List<SwapArenaBaseVO> arenaBaseVOs;
	
	
	public void init(SwapArena swapArena, List<SwapArenaBaseVO> arenaBaseVOs){
		this.times = swapArena.getTimes();
		this.todayBuyCount = swapArena.getBuyTimes();
		this.nextFightTime = swapArena.getNextFightTime();
		this.arenaBaseVOs = arenaBaseVOs;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getTodayBuyCount() {
		return todayBuyCount;
	}

	public void setTodayBuyCount(int todayBuyCount) {
		this.todayBuyCount = todayBuyCount;
	}

	public long getNextFightTime() {
		return nextFightTime;
	}

	public void setNextFightTime(long nextFightTime) {
		this.nextFightTime = nextFightTime;
	}

	public List<SwapArenaBaseVO> getArenaBaseVOs() {
		return arenaBaseVOs;
	}

	public void setArenaBaseVOs(List<SwapArenaBaseVO> arenaBaseVOs) {
		this.arenaBaseVOs = arenaBaseVOs;
	}
}
