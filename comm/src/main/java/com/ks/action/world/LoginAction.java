package com.ks.action.world;

import com.ks.game.model.Player;
import com.ks.protocol.vo.login.LoginResultVO;
import com.ks.protocol.vo.login.LoginVO;
import com.ks.protocol.vo.login.RegisterVO;


/**
 * 登录
 * @author ks
 *
 */
public interface LoginAction{
	/**
	 * 用户登录
	 * @param loginVO 登录信息
	 * @return 登录结果
	 */
	LoginResultVO userLogin(LoginVO loginVO);
	/**
	 * 用户下线
	 * @param userId
	 */
	void logout(Player player);
	/**
	 * 用户注册
	 * @param register 注册信息
	 */
	void userRegister(RegisterVO register);
}