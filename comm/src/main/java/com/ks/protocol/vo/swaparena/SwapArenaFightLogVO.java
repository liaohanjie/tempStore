package com.ks.protocol.vo.swaparena;
import com.ks.protocol.Message;
/**
 * 交换排名竞技赛战斗日志信息VO
 * @author hanjie.l
 *
 */
public class SwapArenaFightLogVO extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3761098376618703288L;
	
	/**
	 * 名次变化，可正可负
	 */
	private int rankChange;
	
	/**
	 * 输赢
	 */
	private boolean win;
	
	/**
	 * 头像战魂id
	 */
	private int soulId;
	
	/**
	 * 对方玩家id
	 */
	private int targetUserId;
	
	/**
	 * 对方玩家名
	 */
	private String targetUserName;
	
	/**
	 * 记录时间
	 */
	private long updateTime;

	public int getRankChange() {
		return rankChange;
	}

	public void setRankChange(int rankChange) {
		this.rankChange = rankChange;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public int getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(int targetUserId) {
		this.targetUserId = targetUserId;
	}

	public String getTargetUserName() {
		return targetUserName;
	}

	public void setTargetUserName(String targetUserName) {
		this.targetUserName = targetUserName;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getSoulId() {
		return soulId;
	}

	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}
}
