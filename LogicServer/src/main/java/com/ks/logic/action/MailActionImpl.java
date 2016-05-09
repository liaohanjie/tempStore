package com.ks.logic.action;

import java.util.List;

import com.ks.action.logic.MailAction;
import com.ks.logic.service.MailService;
import com.ks.logic.service.ServiceFactory;
import com.ks.model.affiche.Mail;

/**
 * 邮件
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月15日
 */
public class MailActionImpl implements MailAction {

	@Override
    public List<Mail> queryAll() {
	    return ServiceFactory.getService(MailService.class).queryAll();
    }

	@Override
    public Mail findById(int id) {
	    return ServiceFactory.getService(MailService.class).findById(id);
    }

	@Override
    public void add(Mail entity) {
		ServiceFactory.getService(MailService.class).add(entity);
    }

	@Override
    public void update(Mail entity) {
		ServiceFactory.getService(MailService.class).update(entity);
    }

	@Override
    public void delete(int id) {
		ServiceFactory.getService(MailService.class).delete(id);
    }
	
}
