package com.ks.protocol.vo.alliance;

import com.ks.protocol.Message;
import com.ks.protocol.vo.mission.UserAwardVO;

/**
 * 购买物品id
 * @author admin
 *
 */
public class BuyItemVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -323732864443589536L;

	/**
	 * 购买后剩余贡献点
	 */
	private long devote;
	
	/**
	 * 道具变化VO[可以参考商城返回VO]
	 */
	private UserAwardVO userAwardVO;

	public long getDevote() {
		return devote;
	}

	public void setDevote(long devote) {
		this.devote = devote;
	}

	public UserAwardVO getUserAwardVO() {
		return userAwardVO;
	}

	public void setUserAwardVO(UserAwardVO userAwardVO) {
		this.userAwardVO = userAwardVO;
	}
}
