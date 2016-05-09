package com.ks.logic.test.service;

import org.junit.Test;

import com.ks.logic.service.ActivityService;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.test.BaseTestCase;
import com.ks.model.activity.ActivityDefine;

public class ActivityServiceTestCase extends BaseTestCase {
	
	private static  ActivityService service =  ServiceFactory.getService( ActivityService.class);;
	
	@Test
	public void call(){
		System.out.println(
		service.getStartingAcVo());
		
	}
	@Test
	public void update(){
		ActivityDefine de=service.getStartingAc().get(0);
		System.out.println(de.getWeekTime());
		de.setWeekTime("");
		//service.updateAcCache(de);
//		ActivityDefine des=	service.getActivity(5010);
//		System.out.println(des.getTitle());
		
	}
	
	
}
