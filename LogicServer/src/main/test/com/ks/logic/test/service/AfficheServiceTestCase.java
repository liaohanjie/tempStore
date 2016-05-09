package com.ks.logic.test.service;

import org.junit.Test;

import com.ks.logic.service.AfficheService;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.test.BaseTestCase;
import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Goods;

/**
 * @author living.li
 * @date 2014年4月9日
 */
public class AfficheServiceTestCase   extends BaseTestCase{
	private static AfficheService service = ServiceFactory.getService(AfficheService.class);
	@Test
	public void getAffiche() {
		
	}
	
}
