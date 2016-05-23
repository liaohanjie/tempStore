package com.ks.account.test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.ks.account.service.GiftCodeService;
import com.ks.account.service.ServiceFactory;
import com.ks.account.test.BaseTestCase;
import com.ks.model.account.GiftCode;
import com.ks.model.account.GiftCodeAward;

public class GiftCodeServiceServiceTestCase extends BaseTestCase {
	private static GiftCodeService service = ServiceFactory.getService(GiftCodeService.class);

	@Test
	public void getListTest() {
		GiftCode c = new GiftCode();
		c.setServerId("gs_soul_word_test_1");

		List<GiftCodeAward> awardList = new ArrayList<>();
		GiftCodeAward a = new GiftCodeAward();
		a.setGoodsType(5);
		a.setNum(100);
		awardList.add(a);
		service.createCode(null, c, 50, awardList);
	}

	@Test
	public void usercode() {
		service.userCode(483641004, "a7735166", "gs_soul_world_test");
	}

	@Test
	public void testGift() {
		GiftCode code = service.queryCode("2ab9ae25");
		if (System.currentTimeMillis() > code.getEndTime().getTime()) {
			System.out.println("礼品卷已失效");
		} else {
			System.out.println("礼品卷未失效");
		}
		System.out.println("GiftCodeServiceImpl.main()" + System.currentTimeMillis());
	}

	@Test
	public void testGiftIsNotNull() {
		GiftCode code = service.queryCode("2ab9ae25");
		Assert.assertNotNull(code.getEndTime());
	}
	
	@Test
	public void testQueryGift(){
		Assert.assertNotNull(service.queryCode("2ab9ae25"));
	}
}
