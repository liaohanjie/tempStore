package com.ks.logic.test.service;

import org.junit.Test;

import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserAchieveService;
import com.ks.logic.test.BaseTestCase;
/**
 * 
 * @author living.li
 * @date  2014年4月26日
 */
public class UserAcieveServiceTestCase extends BaseTestCase {
	
	private static UserAchieveService service =  ServiceFactory.getService(UserAchieveService.class);;
	
	@Test
	public void testgetAward() throws Exception{
		
		System.out.println(
		service.getAchieveAward(483641000, 9020001));
	}

}
