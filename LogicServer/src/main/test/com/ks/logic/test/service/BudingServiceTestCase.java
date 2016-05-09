package com.ks.logic.test.service;

import org.junit.Test;

import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserBudingService;
import com.ks.logic.test.BaseTestCase;

public class BudingServiceTestCase extends BaseTestCase {
	
	private static final UserBudingService service = ServiceFactory.getService(UserBudingService.class);
	
	@Test
	public void testLevelUp(){
		service.levelUpBuding(483641024, 3, 2000);
	}
	
	@Test
	public void testcollectBuding(){
		service.collectBuding(483641024, 3, 2);
	}
}
