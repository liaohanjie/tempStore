package com.ks.logic.test.service;

import org.junit.Test;
import com.ks.logic.service.SwapArenaSettingService;
import com.ks.logic.test.BaseTestCase;

public class SwapArenaSettingTestCase extends BaseTestCase{
	
	private SwapArenaSettingService serive= getService(SwapArenaSettingService.class);
	
	@Test
	public void renameRobots(){
		serive.renameRobots();
	}
	
	@Test
	public void initRobot(){
		serive.initRobots();
	}
	
	@Test
	public void clearRobot(){
		serive.clearRobot();
	}
	
	
	
}
