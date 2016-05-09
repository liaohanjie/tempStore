package com.ks.logic.test.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.UserSoulService;
import com.ks.logic.test.BaseTestCase;

public class UserSoulServiceTestCase extends BaseTestCase {
	
	private static UserSoulService service =  ServiceFactory.getService(UserSoulService.class);;
	
	@Test
	public void testStrengUserSoul(){
		
		service.strengUserSoul(483641074, 1095, Arrays.asList(1140l,1139l,1138l,972l,1137l));
	}
	@Test
	public void testSoulEvolution(){
		service.soulEvolution(483641466, 302, Arrays.asList(303l,304l,305l,306l,307l));
	}
	@Test
	public void TestcallSoul(){
		System.out.println(
		service.callSoul(483641070, 2,10).getSouls().size());
	}
	@Test
	public void querySoul(){
		System.out.println(service.queryUserSoulMap(483641024));
		service.queryUserSoulMap(483641024);
	}
	@Test
	public void reShape(){
		List<Long> usIds=new ArrayList<>();
		usIds.add(929l);
		usIds.add(934l);
		usIds.add(935l);
		usIds.add(936l);
		usIds.add(939l);
		System.out.println(service.reShapeSoul(483641004,usIds));
		service.queryUserSoulMap(483641024);
	}
	@Test
	public void call(){
		service.callSoul(483641078, 1, 2);
	}
}
