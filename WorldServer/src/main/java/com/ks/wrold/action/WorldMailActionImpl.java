package com.ks.wrold.action;

import java.util.List;

import com.ks.action.logic.MailAction;
import com.ks.action.world.WorldMailAction;
import com.ks.app.Application;
import com.ks.model.affiche.Mail;
import com.ks.rpc.RPCKernel;

public class WorldMailActionImpl implements WorldMailAction {

	private MailAction getService(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, MailAction.class);
	}
	
	@Override
    public void add(Mail entity) {
		getService().add(entity);
    }

	@Override
    public void update(Mail entity) {
		getService().update(entity);
    }

	@Override
    public void delete(int id) {
		getService().delete(id);
    }

	@Override
    public List<Mail> queryAll() {
	    return getService().queryAll();
    }
	
}
