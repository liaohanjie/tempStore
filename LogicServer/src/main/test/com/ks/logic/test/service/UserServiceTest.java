package com.ks.logic.test.service;

import org.junit.Test;

import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserService;
import com.ks.logic.test.BaseTestCase;
import com.ks.model.logger.LoggerType;
import com.ks.model.user.User;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.login.LoginResultVO;
import com.ks.protocol.vo.login.LoginVO;
import com.ks.protocol.vo.login.RegisterVO;
import com.ks.protocol.vo.user.UserInfoVO;

public class UserServiceTest extends BaseTestCase {
	private static UserService service;

	UserService getService() {
		if (service == null) {
			service = ServiceFactory.getService(UserService.class);
		}
		return service;
	}

	@Test
	public void testRegister() throws InterruptedException {
		RegisterVO register = MessageFactory.getMessage(RegisterVO.class);
		//for (int i = 0; i < 3000; i++) {
			register.setUsername("living3" );
			register.setPartner(1);
			register.setSoulId(1);
			register.setPlayerName("living3hd");
			getService().userRegister(register);
		//}
	}

	@Test
	public void testGainUserInfo() {
		UserInfoVO vo = getService().gainUserInfo(483679433);
		System.out.println(vo.getUser().getGold());
		//UserInfoVO vo = getService().gainUserInfo(483641787);
		System.out.println(vo);
	}

	@Test
	public void testLogout() {
		getService().newbieSoul(1010001,483679431);
	}

	@Test
	public void testAddGold() {
		User user = getService().getExistUser(483641078);
		getService().incrementExp(user, -1000000000, LoggerType.TYPE_战斗获得, "",null);
		System.out.println(user.getLevel());
		// getService().incrementCurrency(getService().getExistUserCache(483641024),
		// 10000000, 1, "");
	}

	@Test
	public void getPlayerName() {
		//UserInfoVO vo = getService().gainUserInfo(483641010);
	//	getService().givePlayerName("zxcttest", 483641000);
		
		//getService().logout(483641000);
		getService().logout(483641745);
		// getService().incrementCurrency(getService().getExistUserCache(483641024),
		// 10000000, 1, "");
	}

	@Test
	public void newGuideS() {
		UserInfoVO vo = getService().gainUserInfo(483641007);
		System.out.println(vo.getUser().getWant());
		//getService().logout(483641000);
		// getService().incrementCurrency(getService().getExistUserCache(483641024),
		// 10000000, 1, "");
	}
	@Test
	public void netxtstep() {
		//UserInfoVO vo = getService().gainUserInfo(483641000);
		getService().newbieSoul(1010006, 483641000);
	//	System.out.println(vo.getUser());
    	//getService().givePlayerName("living", 483641011);
		//getService().logout(483641011);
		// getService().incrementCurrency(getService().getExistUserCache(483641024),
		// 10000000, 1, "");
	}
	@Test
	public void userStat() {
		System.out.println(getService().getUserRules());
	}
	
	@Test
	public void testMallAddExtraCoin() {
		getService().pay("a1", 10, 10*10, "jsolo", 10, 0);
	}
	
	@Test
	public void testRegisterA1A4(){
		RegisterVO register = MessageFactory.getMessage(RegisterVO.class);
		register.setUsername("dafanren001" );
		register.setPartner(10);
		register.setSoulId(1);
		register.setPlayerName("tt001");
		getService().userRegister(register);
		// id = 483675390
	}
	
	@Test
	public void testgainUserInfoA3A5A6(){
		// id = 483675390, 483675391+, 483675348
		int i = 3;
		String userName = "sf00" + i;
		String playerName = "tt00" + i;
		int partner = 10;
		RegisterVO register = MessageFactory.getMessage(RegisterVO.class);
		register.setUsername(userName);
		register.setPartner(partner);
		register.setSoulId(1);
		register.setPlayerName(playerName);
		getService().userRegister(register);
		
		LoginVO vo = new LoginVO();
		vo.setUsername(userName);
		vo.setPartner(partner);
		LoginResultVO result = getService().userLogin(vo);
		System.out.println(result);
		
		UserInfoVO uiVo = getService().gainUserInfo(result.getUserId());
		System.out.println(uiVo);
	}
}