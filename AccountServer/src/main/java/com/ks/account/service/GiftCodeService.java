package com.ks.account.service;

import java.util.List;

import com.ks.access.Transaction;
import com.ks.model.account.Gift;
import com.ks.model.account.GiftCode;
import com.ks.model.account.GiftCodeAward;

public interface GiftCodeService {
	@Transaction
	public void createCode(Gift gift, GiftCode codeDemo, int size, List<GiftCodeAward> award);

	@Transaction
	public void userCode(int userId, String code, String serverId);

	public List<GiftCode> queryGiftCodes(int giftId);

	public GiftCode queryCode(String code);

	@Transaction
	public void addGift(Gift gift);

	public Gift queryGift(String giftName);

	public List<Gift> queryGifts();
}
