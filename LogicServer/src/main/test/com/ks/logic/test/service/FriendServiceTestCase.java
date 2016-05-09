package com.ks.logic.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.ks.logic.service.FriendService;
import com.ks.logic.test.BaseTestCase;

public class FriendServiceTestCase  extends BaseTestCase{
	
	private FriendService serive=getService(FriendService.class);
	
	

	@Test
	public void updateWant(){
		List<Integer> list=new ArrayList<>();
		list.add(3);
		list.add(11);
		list.add(9);
		serive.updateWant(483641007, list);
	}
	
	@Test
	public void coolectFriend(){
		serive.unCollectionFriend(483641007, 483641004);
		
	}
}

