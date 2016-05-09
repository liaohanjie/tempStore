package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ks.logic.service.BaseService;
import com.ks.logic.service.UserBuffService;
import com.ks.model.user.UserBuff;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.user.UserBuffVO;

public class UserBuffServiceImpl extends BaseService implements UserBuffService {

	@Override
	public List<UserBuff> getUserBuff(int userId) {
		return userBuffDAO.getUserBuffs(userId);
	}

	@Override
	public boolean buffOffTime(int userId, int buffId, int value) {
		UserBuff buff=userBuffDAO.getUserBuff(userId, buffId, value);
		if(buff==null){
			return false;
		}
		Calendar c=Calendar.getInstance();
		return buff.getEndTime().before(c.getTime());
	}

	@Override
	public UserBuff addUserBuff(int userId,UserBuff userBuff,long addTime) {
		UserBuff buff=userBuffDAO.getUserBuff(userId, userBuff.getBuffId(), userBuff.getValue());
		Calendar now = Calendar.getInstance();
		if (buff == null) {
			buff = userBuff;
			if (addTime != 0) {
				buff.setEndTime(new Date(now.getTimeInMillis()+ addTime));
			} else {
				buff.setEndTime(buff.getEndTime());
			}
			userBuffDAO.addBuff(userId, buff);
		} else {
			if (buff.getEndTime().before(now.getTime())) {
				buff.setEndTime(now.getTime());
			}
			buff.setEndTime(new Date(buff.getEndTime().getTime()+ addTime));
			userBuffDAO.updateUserBuff(userId, buff);
		}
		return buff;
	}

	@Override
	public List<UserBuffVO> gainUserBuff(int userId) {
		List<UserBuff> buffs=userBuffDAO.getUserBuffs(userId);
		List<UserBuffVO> vos=new ArrayList<UserBuffVO>();
		Calendar c=Calendar.getInstance();
		for(UserBuff buff:buffs){
			//没有过期的发到前端
			if(buff.getEndTime().after(c.getTime())){
				UserBuffVO vo=MessageFactory.getMessage(UserBuffVO.class);
				vo.init(buff);
				vos.add(vo);
			}
		}
		return vos;
	}

}
