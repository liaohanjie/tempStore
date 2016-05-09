package com.ks.login.handler;

import com.ks.action.world.LoginAction;
import com.ks.app.Application;
import com.ks.exceptions.GameException;
import com.ks.handler.GameHandler;
import com.ks.protocol.MainCmd;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.SubCmd;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.LoginCMD;
import com.ks.protocol.vo.Head;
import com.ks.protocol.vo.login.LoginResultVO;
import com.ks.protocol.vo.login.LoginVO;
import com.ks.protocol.vo.login.RegisterVO;
import com.ks.rpc.RPCKernel;

/**
 * 登录
 * @author ks
 *
 */
@MainCmd(mainCmd=MainCMD.LOGIN)
public final class LoginHandler {
	/**
	 * 登录
	 * @param gameHandler
	 * @param loginVO
	 */
	@SubCmd(subCmd=LoginCMD.USER_LOGIN,args={"login"})
	public void login(GameHandler gameHandler,LoginVO loginVO) throws Exception{
		
		//String localSign=LoginVO.getOauthSign(loginVO.getUsername(), loginVO.getPartner()+"");
		/*if(localSign.equals(loginVO.getSign())){
			throw new GameException(GameException.CODE_签名非法,"");
		}*/
		LoginAction login = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, LoginAction.class);
		LoginResultVO vo = login.userLogin(loginVO);
		//set ip
		loginVO.setIp(gameHandler.getChannel().getRemoteAddress().toString());
		
		Head head = MessageFactory.getMessage(Head.class);
		head.init(MainCMD.LOGIN, LoginCMD.USER_LOGIN);
		head.setSessionId(vo.getSessionId());
		Application.sendMessage(gameHandler.getChannel(), head, vo);
	}
	
	/**
	 * 用户注册
	 * @param handler
	 * @param register 注册信息
	 */
	@SubCmd(subCmd=LoginCMD.USER_REGISTER,args={"register"})
	public void userRegister(GameHandler handler,RegisterVO register){
		if(Application.CANT_REGISTER){
			throw new GameException(GameException.CODE_参数错误, "can not register");
		}
		//String localSign=LoginVO.getOauthSign(register.getUsername(), register.getPartner()+"");
		/*if(localSign.equals(register.getSign())){
			throw new GameException(GameException.CODE_签名非法,"");
		}*/
		LoginAction login = RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, LoginAction.class);
		register.setIp(handler.getChannel().getRemoteAddress().toString());
		login.userRegister(register);
		Head head = MessageFactory.getMessage(Head.class);
		head.init(MainCMD.LOGIN, LoginCMD.USER_REGISTER);
		Application.sendMessage(handler.getChannel(), head);
	}
}
