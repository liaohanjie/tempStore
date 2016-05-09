package com.ks.protocol.vo.rank;

import com.ks.protocol.Message;
/**
 * 排行信息VO
 * @author hanjie.l
 *
 */
public class RankInfoVO  extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 614583117388432783L;
	
	/**
	 * 排行榜类型id
	 */
	private int rankTypeId;

	/**
	 * 排行榜信息
	 */
	private RankNoticeVO notice;

	public RankNoticeVO getNotice() {
		return notice;
	}

	public void setNotice(RankNoticeVO notice) {
		this.notice = notice;
	}

	public int getRankTypeId() {
		return rankTypeId;
	}

	public void setRankTypeId(int rankTypeId) {
		this.rankTypeId = rankTypeId;
	}
}
