package com.ks.account.action;

import java.util.List;

import com.ks.account.service.GiftCodeService;
import com.ks.account.service.ServiceFactory;
import com.ks.action.account.GiftCodeAction;
import com.ks.model.account.Gift;
import com.ks.model.account.GiftCode;
import com.ks.model.account.GiftCodeAward;

public class GiftCodeActionImpl implements GiftCodeAction {

	private final GiftCodeService giftCodeService = ServiceFactory.getService(GiftCodeService.class);

	@Override
	public void createCode(Gift gift,GiftCode codeDemo, int size, List<GiftCodeAward> award) {
		giftCodeService.createCode(gift,codeDemo, size, award);
	}

	@Override
	public void useCode(String code, String serverId, int userId) {
		giftCodeService.userCode(userId, code, serverId);
	}

	@Override
	public List<GiftCode> queryGiftCodes(int giftId) {
		return giftCodeService.queryGiftCodes(giftId);
	}

	@Override
    public void addGift(Gift gift) {
		 giftCodeService.addGift(gift);
    }

	@Override
    public Gift queryGift(String giftName) {
	    return giftCodeService.queryGift(giftName);
    }

	@Override
    public List<Gift> queryGifts() {
	    return giftCodeService.queryGifts();
    }

}
