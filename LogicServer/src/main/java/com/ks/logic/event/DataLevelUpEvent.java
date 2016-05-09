/**
 * 
 */
package com.ks.logic.event;

import java.util.Date;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.sf.data.DataSDK;
import com.sf.data.domain.UserLevelUp;

/**
 * @author living.li
 * @date 2014年9月28日 下午2:53:19
 * 
 * 
 */
public class DataLevelUpEvent extends GameEvent {
	private Logger logger=LoggerFactory.get(DataLevelUpEvent.class);
	private UserLevelUp up;

	public DataLevelUpEvent(int partner,int roleId, int level, Date levelUpTime,Date loginTime) {
		this.up = UserLevelUp.create(partner, roleId, level,levelUpTime,loginTime);
	}

	@Override
	public void runEvent() {
		try {
			DataSDK.userLevelUp(up);
		} catch (Exception e) {
			logger.error("", e);
		}
	}
}
