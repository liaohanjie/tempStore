package com.ks.logic.test.service;

import org.junit.Test;

import com.ks.logic.service.ArenaService;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.test.BaseTestCase;

public class ArenaServiceTestCase extends BaseTestCase {
	
	@Test
	public void testStartFight(){
		ArenaService service = ServiceFactory.getService(ArenaService.class);
		long start = System.currentTimeMillis();
		service.fighting(483604928,483604923);
		System.out.println(System.currentTimeMillis()-start);
	}
}
