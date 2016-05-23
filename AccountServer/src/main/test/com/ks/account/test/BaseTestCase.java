package com.ks.account.test;



import org.junit.Before;

import com.ks.access.DataSourceUtils;
import com.ks.account.kernel.AccountServerKernel;
import com.ks.account.service.ServiceFactory;
import com.ks.app.Application;

/**
 * 
 * @author ks
 *
 */
public class BaseTestCase {
	@Before
	public void init() throws Exception{
		Application application = new Application();
		application.init("AccountApplication.xml",application);
		AccountServerKernel.initDataSource();
		DataSourceUtils.setTest(true);
//		ServiceFactory.initService();
	}
	protected <T>T getService(Class<T> clazz) {
		return ServiceFactory.getService(clazz);
	}
}
