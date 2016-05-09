package com.ks.protocol.vo.tower;

import com.ks.model.climb.ClimbTowerUser;
import com.ks.protocol.Message;

public class ClimbTowerUserVO extends Message {

    private static final long serialVersionUID = -3398199018821795967L;
    
	/** 试练塔第几层 */
	private int towerFloor;
	/** 通关星级 */
	private short startCount;
	/** 当天剩余战斗次数 */
	private short remainingCount;
	/** 当天购买战斗次数 */
	private short buyFightCount;

	public void init(ClimbTowerUser o, short remainingCount) {
		this.towerFloor = o.getTowerFloor();
		this.startCount = o.getStartCount();
		this.buyFightCount = o.getBuyFightCount();
		this.remainingCount = remainingCount;
	}

	public int getTowerFloor() {
		return towerFloor;
	}

	public void setTowerFloor(int towerFloor) {
		this.towerFloor = towerFloor;
	}

	public short getStartCount() {
		return startCount;
	}

	public void setStartCount(short startCount) {
		this.startCount = startCount;
	}

	public short getRemainingCount() {
		return remainingCount;
	}

	public void setRemainingCount(short remainingCount) {
		this.remainingCount = remainingCount;
	}

	public short getBuyFightCount() {
		return buyFightCount;
	}

	public void setBuyFightCount(short buyFightCount) {
		this.buyFightCount = buyFightCount;
	}

	@Override
    public String toString() {
	    return "ClimbTowerUserVO [towerFloor=" + towerFloor + ", startCount=" + startCount + ", remainingCount=" + remainingCount + ", buyFightCount=" + buyFightCount + "]";
    }

}
