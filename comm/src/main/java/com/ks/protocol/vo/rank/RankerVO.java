package com.ks.protocol.vo.rank;

import com.ks.protocol.Message;
import com.ks.protocol.vo.user.UserCapVO;

/**
 * 排行榜对象
 * @author hanjie.l
 *
 */
public class RankerVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9037892515172121767L;
	
	/**
	 * 排名
	 */
	private int rank;
	
	/**
	 * 个人信息
	 */
	private UserCapVO userCapVO;
	
	/**
	 * 数值1
	 */
	private long value1;
	
	/**
	 * 数值2
	 */
	private long value2;
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public UserCapVO getUserCapVO() {
		return userCapVO;
	}

	public void setUserCapVO(UserCapVO userCapVO) {
		this.userCapVO = userCapVO;
	}

	public long getValue1() {
		return value1;
	}

	public void setValue1(long value1) {
		this.value1 = value1;
	}

	public long getValue2() {
		return value2;
	}

	public void setValue2(long value2) {
		this.value2 = value2;
	}
}
