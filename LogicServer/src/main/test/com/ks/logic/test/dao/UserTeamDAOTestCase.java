package com.ks.logic.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ks.cache.JedisUtils;
import com.ks.logic.dao.UserTeamDAO;
import com.ks.logic.test.BaseTestCase;
import com.ks.model.user.UserCap;

public class UserTeamDAOTestCase extends BaseTestCase {

	@Test
	public void testFindAllSoul(){
		UserTeamDAO soulDAO = new UserTeamDAO();
		UserCap cap=soulDAO.getUserCapCache(483646418);
		cap.setPlayerName("tmp_x1");
		soulDAO.updateUserCapCache(cap);
		JedisUtils.exec();
		System.out.println(soulDAO.getUserCapCache(483646418));
	}
	
	@Test
	public void testFind(){
//		UserSoulMapDAO soulDAO = new UserSoulMapDAO();
		List<Integer> list=new ArrayList<Integer>();
		list.add(11);
		list.add(11);
		list.add(12);
//		List<UserSoulMap> souls = soulDAO.getUserMapSouls(100001,list);
	}
}
