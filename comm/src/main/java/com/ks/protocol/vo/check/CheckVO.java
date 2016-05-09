package com.ks.protocol.vo.check;

import java.util.List;

import com.ks.protocol.Message;

public class CheckVO extends Message{
    /**
	 * 
	 */
	private static final long serialVersionUID = -733086186496776334L;

	/**Pve战斗类型 0：正常pve 1：世界Boss、 2：爬塔试炼、3：排位赛、*/
    private int battleType;
    
    /**
     * 好友id
     */
    private int friendId;

    /**
    *需要校验的数据列表
    */
    private List<BattleCheckVO> checkList;

	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public int getBattleType() {
		return battleType;
	}

	public void setBattleType(int battleType) {
		this.battleType = battleType;
	}

	public List<BattleCheckVO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<BattleCheckVO> checkList) {
		this.checkList = checkList;
	}
}
