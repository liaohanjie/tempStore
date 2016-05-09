package com.ks.logic.event;

import com.ks.action.world.WorldUserAction;
import com.ks.app.Application;
import com.ks.event.GameEvent;
import com.ks.rpc.RPCKernel;

public class NotifiEvent extends GameEvent {
	public static final int USER_ALL_ONOLINE=0;
	
	public static final int NOTIF_TYPE_好友申请=0b1;
	public static final int NOTIF_TYPE_邮件=0b10;
	public static final int NOTIF_TYPE_跑马灯公告=0b100;
	
	private int userId;
	private int notifiType;
	public NotifiEvent(int userId, int notifiType) {
		super();
		this.userId = userId;
		this.notifiType = notifiType;
	}
	
	@Override
	public void runEvent() {
		WorldUserAction worldUserAction=RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, WorldUserAction.class);
		worldUserAction.notifiUser(userId, notifiType);	}

}
