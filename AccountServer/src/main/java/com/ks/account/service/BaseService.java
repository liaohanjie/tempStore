package com.ks.account.service;

import com.ks.account.dao.AccountDAO;
import com.ks.account.dao.AdminDAO;
import com.ks.account.dao.BulletinLoggerDAO;
import com.ks.account.dao.ChatLoggerDAO;
import com.ks.account.dao.GiftCodeDAO;
import com.ks.account.dao.GiftTemplateDAO;
import com.ks.account.dao.OrderReturnDAO;
import com.ks.account.dao.PayConfigDAO;
import com.ks.account.dao.PayDao;
import com.ks.account.dao.ServerInfoDAO;
import com.ks.account.dao.ViolationLoggerDAO;
import com.ks.account.dao.cfg.MallDAO;
import com.ks.account.service.impl.PayServiceImpl;
import com.ks.account.service.impl.ServerInfoServiceImpl;
import com.ks.action.account.AdminAction;
import com.ks.action.world.WorldGiftCodeAction;
import com.ks.app.Application;
import com.ks.rpc.RPCKernel;

/**
 * 
 * @author ks
 * 
 */
public abstract class BaseService {
	public <T> T worldAction(String serverId, Class<T> clazz) {
		return RPCKernel.getRemoteByServerId(serverId, clazz);
	}

	public static <T> T getInfoAction(Class<T> clazz) {
		return RPCKernel.getRemoteByServerType(Application.ACCOUNT_SERVER, clazz);
	}

	public AdminAction adminAction() {
		return getInfoAction(AdminAction.class);
	}

	protected static final ServerInfoDAO serverInfoDAO = new ServerInfoDAO();
	protected static final ServerInfoService activityService = new ServerInfoServiceImpl();
	protected static final PayService payService = new PayServiceImpl();
	protected static final AdminDAO adminDAO = new AdminDAO();
	protected static final AccountDAO accountDAO = new AccountDAO();
	protected static final PayConfigDAO payConfigDAO = new PayConfigDAO();
	protected static final GiftCodeDAO codeDAO = new GiftCodeDAO();
	protected static final PayDao payDao = new PayDao();
	protected static final GiftTemplateDAO templateDAO = new GiftTemplateDAO();
	protected static final ChatLoggerDAO chatLoggerDAO = new ChatLoggerDAO();
	protected static final ViolationLoggerDAO violationLoggerDAO = new ViolationLoggerDAO();
	protected static final BulletinLoggerDAO bulletinLoggerDAO = new BulletinLoggerDAO();
	protected static final MallDAO mallDAO = new MallDAO();
	protected static final OrderReturnDAO orderReturnDAO = new OrderReturnDAO();

	public WorldGiftCodeAction worldGiftCodeAction(String serverId) {
		return worldAction(serverId, WorldGiftCodeAction.class);
	}

}
