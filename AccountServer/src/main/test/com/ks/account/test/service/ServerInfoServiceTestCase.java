package com.ks.account.test.service;

import org.junit.Test;

import com.ks.account.service.ServerInfoService;
import com.ks.account.service.ServiceFactory;
import com.ks.account.test.BaseTestCase;

public class ServerInfoServiceTestCase extends BaseTestCase {
	private static ServerInfoService service =  ServiceFactory.getService(ServerInfoService.class);
	
	@Test
	public void getListTest(){
		System.out.println(service.getNotices().size());
	}
	
}
