package com.ks.account.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import com.ks.account.cache.AccountCache;
import com.ks.account.service.BaseService;
import com.ks.account.service.GiftCodeService;
import com.ks.exceptions.GameException;
import com.ks.model.account.Gift;
import com.ks.model.account.GiftCode;
import com.ks.model.account.GiftCodeAward;
import com.ks.model.account.GiftCodeLogger;
import com.ks.model.account.ServerInfo;
import com.ks.model.goods.Goods;

public class GiftCodeServiceImpl extends BaseService implements GiftCodeService {

	@Override
	public void createCode(Gift gift, GiftCode codeDemo, int size, List<GiftCodeAward> award) {
		Calendar c = Calendar.getInstance();
		List<GiftCode> codeList = new ArrayList<>();
		String awardConfig = c.getTimeInMillis() + "";
		for (int i = 0; i < size; i++) {
			GiftCode code = codeDemo.copy();
			String rep = UUID.randomUUID().toString().replace("_", "").substring(0, 8);
			code.setCode(rep);
			code.setState(GiftCode.STATE_NOT_UES);
			code.setAwardId(awardConfig);
			code.setGiftId(gift.getId());
			codeList.add(code);
		}
		for (GiftCodeAward f : award) {
			f.setAwardId(awardConfig);
			f.setGiftId(gift.getId());
		}
		codeDAO.addBatchCode(codeList);
		codeDAO.addBatchAward(award);
	}

	@Override
	public void userCode(int userId, String code, String serverId) {
		GiftCode giftCode = codeDAO.queryCode(code);

		if (giftCode == null) {
			throw new GameException(GameException.CODE_礼品券无效, "gift code no found." + code);
		}

		// 判断礼品券是否过期
		if (giftCode.getValidType() == GiftCode.VALID_TYPE_FIX_TIME && System.currentTimeMillis() > giftCode.getEndTime().getTime()) {
			throw new GameException(GameException.CODE_礼品券已失效, "gift code has been invalidated." + code);
		}

		if (giftCode.getUseType() == GiftCode.USE_TYPE_NO_LIMIT) {
			if (giftCode.getState() == GiftCode.STATE_UESD) {
				throw new GameException(GameException.CODE_礼品券已被使用, "gift code has used." + code);
			}
		} else if (giftCode.getUseType() == GiftCode.USE_TYPE_SINGLE) {
			// 同一个用户在同一个区服下只能使用同一个类型的礼品券
			List<GiftCodeLogger> logList = codeDAO.queryGiftCodeLogger(userId, giftCode.getAwardId());
			if (logList != null && !logList.isEmpty()) {
				throw new GameException(GameException.CODE_同类型只能使用一次, "the same kind of gift vouchers can be used only once ." + giftCode.getCode());
			}

			if (giftCode.getState() == GiftCode.STATE_UESD) {
				throw new GameException(GameException.CODE_礼品券已被使用, "gift code has used." + code);
			}
		} else if (giftCode.getUseType() == GiftCode.USE_TYPE_SHARE) {
			// 所有用户在同一个区服下只能使用同一个类型的礼品券
			List<GiftCodeLogger> logList = codeDAO.queryGiftCodeLogger(userId, giftCode.getAwardId(), serverId);
			if (logList != null && !logList.isEmpty()) {
				throw new GameException(GameException.CODE_同类型只能使用一次, "the same kind of gift vouchers can be used only once ." + giftCode.getCode());
			}
		}

		// 应该发送到哪个区服务
		// ServerInfo serverInfo = serverInfo =
		// AccountCache.getServerByServerId(serverId);
		ServerInfo serverInfo = AccountCache.getServerById(Integer.parseInt(serverId));
		if (serverInfo == null) {
			throw new GameException(GameException.CODE_服务器不存在, "server no found." + serverId);
		}
		List<GiftCodeAward> awards = codeDAO.quertCodeAward(giftCode.getAwardId());
		List<Goods> goods = new ArrayList<Goods>();
		for (GiftCodeAward a : awards) {
			goods.add(Goods.create(a.getAssId(), a.getGoodsType(), a.getNum(), 1));
		}
		worldGiftCodeAction(serverInfo.getServerId()).giveCodeGift(userId, code, goods);

		codeDAO.useCode(code);
		GiftCodeLogger giftCodeLog = new GiftCodeLogger();
		giftCodeLog.setAwardId(giftCode.getAwardId());
		giftCodeLog.setCode(code);
		giftCodeLog.setUserId(userId);
		giftCodeLog.setServerId(serverId);
		codeDAO.addGiftCodeLogger(giftCodeLog);
	}

	@Override
	public List<GiftCode> queryGiftCodes(int giftId) {
		return codeDAO.queryGiftCodes(giftId);
	}

	@Override
	public GiftCode queryCode(String code) {
		return codeDAO.queryCode(code);
	}

	public static Integer parseInt(String value) {
		Integer v = null;
		try {
			v = Integer.parseInt(value);
		} catch (NumberFormatException e) {
		}
		return v;
	}

	@Override
	public void addGift(Gift gift) {
		codeDAO.addGift(gift);
	}

	@Override
    public Gift queryGift(String giftName) {
	    return codeDAO.queryGift(giftName);
    }

	@Override
    public List<Gift> queryGifts() {
	    return codeDAO.queryGifts();
    }
}
