package com.ks.logic.test.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ks.logic.dao.UserDAO;
import com.ks.logic.test.BaseTestCase;
import com.ks.model.user.User;

public class UserDAOTestCase extends BaseTestCase {
	@Test
	public void testGetUserIdByUsername(){
		UserDAO userDAO = new UserDAO();
		userDAO.getUserFromCache(483675854);
	}
	
	@Test
	public void testRandomAdventurers(){
		UserDAO userDAO = new UserDAO();
		List<Integer> list = new ArrayList<Integer>();
		int userId = 483644468;
		for(int i=0;i<51;i++){
			list.add(userId);
			userId++;
		}
		long start = System.currentTimeMillis();
		System.out.println(userDAO.randomAdventurers(3,list));
		System.out.println(System.currentTimeMillis()-start);
	}
}
