package com.ks.logic.test;

import org.junit.Before;

import com.ks.app.Application;
import com.ks.logic.kernel.LogicServerKernel;
import com.ks.logic.service.ServiceFactory;

public class BaseServiceTestCase {
	@Before
	public void init() throws Exception{
		Application application = new Application();
		application.init("DatabaseApplication.xml",application);
		LogicServerKernel.initDataSource();
		//ServiceFactory.initService();
		LogicServerKernel.initGameCache();
	}
	protected <T>T getService(Class<T> clazz) {
		return ServiceFactory.getService(clazz);
	}
}
