package com.ks.logic.test.service;

import org.junit.Test;

import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserBudingService;
import com.ks.logic.test.BaseTestCase;

public class UserBuildingServiceTest extends BaseTestCase {
	private static UserBudingService service;

	UserBudingService getService() {
		if (service == null) {
			service = ServiceFactory.getService(UserBudingService.class);
		}
		return service;
	}



	@Test
	public void testGainUserInfo() {
		System.out.println(getService().collectBuding(483641078, 2, 1));
	}
	
	@Test
	public void ainUserInfo() {
		getService().levelUpBuding(483641727, 1, 1);
	}
}