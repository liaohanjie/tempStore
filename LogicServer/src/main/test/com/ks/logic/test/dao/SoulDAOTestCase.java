package com.ks.logic.test.dao;

import java.util.List;

import org.junit.Test;

import com.ks.logic.dao.cfg.SoulCfgDAO;
import com.ks.logic.test.BaseTestCase;
import com.ks.model.soul.Soul;

public class SoulDAOTestCase extends BaseTestCase {

	@Test
	public void testFindAllSoul(){
		SoulCfgDAO soulCfgDAO = new SoulCfgDAO();
		List<Soul> souls = soulCfgDAO.findAllSoul();
		for(Soul s : souls){
			System.out.println(s);
		}
	}
	
	@Test
	public void testFind(){
//		UserSoulMapDAO soulDAO = new UserSoulMapDAO();
//		List<Integer> list=new ArrayList<Integer>();
//		list.add(11);
//		list.add(11);
//		list.add(12);
//		List<UserSoulMap> souls = soulDAO.getUserMapSouls(100001,list);
	}
}
