package com.ks.protocol.vo.pvp;

import java.util.List;

import com.ks.protocol.Message;

public class AthleticsInfoModelVO extends Message {
	
	private static final long serialVersionUID = 1L;
	/**排行榜信息*/
	private List<AthleticsInfoVO> infoList;
	/**我的排名*/
	private int myRank;
	public List<AthleticsInfoVO> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<AthleticsInfoVO> infoList) {
		this.infoList = infoList;
	}
//private List<UserCapVO> capList;
//	public List<UserCapVO> getCapList() {
//		return capList;
//	}
//	public void setCapList(List<UserCapVO> capList) {
//		this.capList = capList;
//	}
	public int getMyRank() {
		return myRank;
	}
	public void setMyRank(int myRank) {
		this.myRank = myRank;
	}
	
}
