package com.ks.logic.test.service;

import org.junit.Test;

import com.ks.logic.service.CheckFightService;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserService;
import com.ks.logic.test.BaseTestCase;

public class CheckFightTest extends BaseTestCase{
	
	private static final CheckFightService service = ServiceFactory.getService(CheckFightService.class);
	
	private static final UserService userservice = ServiceFactory.getService(UserService.class);
	
	@Test
	public void testLevelUp(){
		userservice.gainUserInfo(483680479);
		service.replay(483680479, 1);
	}
}
