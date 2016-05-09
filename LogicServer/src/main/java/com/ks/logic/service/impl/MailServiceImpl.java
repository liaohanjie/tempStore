package com.ks.logic.service.impl;

import java.util.Date;
import java.util.List;

import com.ks.event.GameEvent;
import com.ks.logic.event.NotifiEvent;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.MailService;
import com.ks.model.affiche.Affiche;
import com.ks.model.affiche.Mail;
import com.ks.model.affiche.MailLog;
import com.ks.timer.TimerController;

/**
 * 邮件
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月15日
 */
public class MailServiceImpl extends BaseService implements MailService {

	@Override
    public List<Mail> queryAll() {
	    return mailDAO.queryAll();
    }

	@Override
    public List<Mail> queryInTime() {
	    return mailDAO.queryInTime();
    }

	@Override
    public Mail findById(int id) {
	    return mailDAO.findById(id);
    }

	@Override
    public void add(Mail entity) {
		if (entity.getLogo() == null || entity.getLogo().trim().equals("")) {
			entity.setLogo("0");
		}
		if (entity.getUserIds() == null) {
			entity.setUserIds("");
		}
		mailDAO.add(entity);
		
		// 通知所有在线用户收取邮件
		long now = System.currentTimeMillis();
		if (now > entity.getFromDate().getTime() && now < entity.getEndDate().getTime()) {
			GameEvent event = new NotifiEvent(NotifiEvent.USER_ALL_ONOLINE, NotifiEvent.NOTIF_TYPE_邮件);
			TimerController.submitGameEvent(event);	
		}
    }

	@Override
    public void update(Mail entity) {
		mailDAO.update(entity);
    }

	@Override
    public void delete(int id) {
		mailDAO.delete(id);
    }

	@Override
    public void send(int userId, boolean notify) {
		List<Mail> list = mailDAO.queryInTime();
		for (Mail mail : list) {
			
			if (!_validateMail(userId, mail)) {
				continue;
			}
			
			MailLog logEntity = mailLogDAO.findByUserIdAndMailId(userId, mail.getId());
			if (logEntity == null) {
				logEntity = new MailLog();
				logEntity.setMailId(mail.getId());
				logEntity.setUserId(userId);
				logEntity.setCreateTime(new Date());
				mailLogDAO.add(logEntity);
			} else {
				continue;
			}
			
			Affiche affiche = new Affiche();
			affiche.setUserId(userId);
			affiche.setType(mail.getType());
			affiche.setTitle(mail.getTitle());
			affiche.setContext(mail.getContext());
			affiche.setState(Affiche.STATE_未读);
			affiche.setLogo(mail.getLogo());
			affiche.setGoodsList(mail.getGoodsList());
			
			if (notify) {
				afficheService.addAffiche(affiche);
			} else {
				afficheDAO.addAffiche(affiche);	
			}
		}
    }
	
	/**
	 * 判断是否给用户发送邮件
	 * 
	 * @param userId
	 * @param mail
	 * @return
	 */
	private boolean _validateMail(int userId, Mail mail){
		
		if (mail.getUserIds() == null || mail.getUserIds().trim().equals("")) {
			return true;
		}
		
		String temp = String.valueOf(userId);
		for (String strUserId : mail.getUserIds().split(",")) {
			if (strUserId.equals(temp)) {
				return true;
			}
		}
		return false;
	}
}
