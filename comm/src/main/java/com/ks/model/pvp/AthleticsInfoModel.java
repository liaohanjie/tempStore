package com.ks.model.pvp;

import java.io.Serializable;
import java.util.List;

import com.ks.model.user.UserCap;

public class AthleticsInfoModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private List<AthleticsInfo> infoList;
	private List<UserCap> capList;
	public List<AthleticsInfo> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<AthleticsInfo> infoList) {
		this.infoList = infoList;
	}
	public List<UserCap> getCapList() {
		return capList;
	}
	public void setCapList(List<UserCap> capList) {
		this.capList = capList;
	}
	public int getMyRank() {
		return myRank;
	}
	public void setMyRank(int myRank) {
		this.myRank = myRank;
	}
	private int myRank;
}
