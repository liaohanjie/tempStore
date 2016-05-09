package com.ks.action.account;

import java.util.List;

import com.ks.model.account.Gift;
import com.ks.model.account.GiftCode;
import com.ks.model.account.GiftCodeAward;

public interface GiftCodeAction {
	public void createCode(Gift gift, GiftCode codeDemo, int size, List<GiftCodeAward> award);

	public void useCode(String code, String serverId, int userId);

	public List<GiftCode> queryGiftCodes(int giftId);

	public void addGift(Gift gift);

	public Gift queryGift(String gfitName);

	public List<Gift> queryGifts();
}
