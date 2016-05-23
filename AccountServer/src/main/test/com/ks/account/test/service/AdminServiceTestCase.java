package com.ks.account.test.service;

import org.junit.Test;

import com.ks.account.service.AdminService;
import com.ks.account.service.ServiceFactory;
import com.ks.account.test.BaseTestCase;

public class AdminServiceTestCase extends BaseTestCase {
	private static AdminService service =  ServiceFactory.getService(AdminService.class);
	
	@Test
	public void getListTest(){
		System.out.println(service.getAllPermission());
	}
	@Test
	public void getAdmin(){
		service.adminLogin("living", "lving");
	}
	
}
