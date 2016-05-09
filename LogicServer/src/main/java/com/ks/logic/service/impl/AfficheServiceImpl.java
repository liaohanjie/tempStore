package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ks.event.GameEvent;
import com.ks.exceptions.GameException;
import com.ks.logic.event.NotifiEvent;
import com.ks.logic.service.AfficheService;
import com.ks.logic.service.BaseService;
import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Backage;
import com.ks.model.goods.Goods;
import com.ks.model.logger.LoggerType;
import com.ks.model.soul.Soul;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserStat;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.affiche.AfficheVO;
import com.ks.protocol.vo.goods.GainAwardVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
import com.ks.protocol.vo.items.GoodsVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.timer.TimerController;
import com.ks.util.DateUtil;

public class AfficheServiceImpl extends BaseService implements AfficheService {

	@Override
	public Affiche addAffiche(Affiche a) {
		Affiche aff = afficheDAO.addAffiche(a);
		sentMailNotifi(a.getUserId());
		return aff;
	}
	
	@Override
	public List<AfficheVO> gainAffiche(int userId) {
		
		mailService.send(userId, false);
		
		List<Affiche> as = afficheDAO.queryAffiches(userId);
		List<AfficheVO> list = new ArrayList<AfficheVO>();
		for (Affiche a : as) {
			List<GoodsVO> voList = new ArrayList<GoodsVO>();
			AfficheVO vo = MessageFactory.getMessage(AfficheVO.class);
			if (a.getGoodsList() != null && !a.getGoodsList().isEmpty()) {
				for (Goods goods : a.getGoodsList()) {
					GoodsVO goodsVO = MessageFactory.getMessage(GoodsVO.class);
					goodsVO.init(goods);
					voList.add(goodsVO);
				}
				vo.setGoodsList(voList);
			}
			vo.init(a);
			list.add(vo);
		}
		return list;
	}

	@Override
	public void deleteAffiche(int id, int userId) {
		afficheDAO.deleteAffiche(id, userId);
	}

	@Override
	public GainAwardVO getAfficheGoods(int userId, int id) {

		// 判断邮件是否存在
		Affiche a = afficheDAO.queryAffiche(id, userId);
		if (a == null) {
			throw new GameException(GameException.CODE_参数错误, "mail is null");
		}

		User user = userService.getExistUserCache(userId);
		Backage backage = userGoodsService.getPackage(user.getUserId());
		List<Affiche> as = new ArrayList<Affiche>();
		as.add(a);
		return viewAffiche(user, as, backage);
	}

	/**
	 * 收取邮件附件
	 * 
	 * @param user
	 * @param as
	 * @param backage
	 * @return
	 */
	private GainAwardVO viewAffiche(User user, List<Affiche> as, Backage backage) {
		GainAwardVO result = MessageFactory.getMessage(GainAwardVO.class);

		// 附件中背包道具
		List<Goods> itemGoods = new ArrayList<Goods>();
		// 附件中的战魂
		List<Goods> soulGoods = new ArrayList<>();
		if (as == null || as.isEmpty()) {
			throw new GameException(GameException.CODE_参数错误, "mail is null");
		}
		
		int oldPoint = user.getPoint();
		int oldGold = user.getGold();
		int oldCurrency = user.getCurrency();
		int oldHonor = user.getHonor();
		
		for (Affiche a : as) {
			if (a.getGoodsList() != null && !a.getGoodsList().isEmpty()) {
				for (Goods gd : a.getGoodsList()) {
					Goods goods = gd;
					switch (goods.getType()) {
					case Goods.TYPE_PROP:
					case Goods.TYPE_STUFF:
					case Goods.TYPE_EQUIPMENT:
						itemGoods.add(goods);
						break;
					case Goods.TYPE_GOLD:
						userService.incrementGold(user, goods.getNum(), LoggerType.TYPE_公告获得, "" + a.getType());
						break;
					case Goods.TYPE_CURRENCY:
						userService.incrementCurrency(user, goods.getNum(), LoggerType.TYPE_公告获得, "" + a.getType());
						break;
					case Goods.TYPE_FRIENDLY_POINT:
						userService.incrementFriendlyPoint(user.getUserId(), goods.getNum(), LoggerType.TYPE_公告获得, "" + a.getType());
						break;
					case Goods.TYPE_SOUL:
						soulGoods.add(goods);
						break;
					case Goods.TYPE_BAK_PROP:
						userGoodsService.addBakProps(user, goods.getGoodsId(), goods.getNum(), LoggerType.TYPE_附件得到, "" + a.getType());
						break;
					case Goods.TYPE_STAMINA:
						userService.incrementStamina(user, goods.getNum(), LoggerType.TYPE_系统补偿, "boss send");
						break;
					case Goods.TYPE_THREE_LEVEL_RANDOM_EXPSOUL:
						for (int i = 0; i < goods.getNum(); i++) {
							// 随机一个3星经验宝
							int random = (int) (Math.random() * Soul.THREE_LEVEL_EXPSOULS.length);
							Goods soul = Goods.create(Soul.THREE_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
							soulGoods.add(soul);
						}
						break;
					case Goods.TYPE_FOUR_LEVEL__RANDOM_EXPSOUL:
						for (int i = 0; i < goods.getNum(); i++) {
							// 随机一个4星经验宝
							int random = (int) (Math.random() * Soul.FOUR_LEVEL_EXPSOULS.length);
							Goods soul = Goods.create(Soul.FOUR_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
							soulGoods.add(soul);
						}
						break;
					case Goods.TYPE_FIVE_LEVEL_RANDOM_EXPSOUL:
						for (int i = 0; i < goods.getNum(); i++) {
							// 随机一个5星经验宝
							int random = (int) (Math.random() * Soul.FIVE_LEVEL_EXPSOULS.length);
							Goods soul = Goods.create(Soul.FIVE_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
							soulGoods.add(soul);
						}
						break;
					case Goods.TYPE_POINT:
						userService.increPoint(user, goods.getNum(), 0, "");
						break;
					case Goods.TYPE_HONOR:
						userService.increHonor(user, goods.getNum(), 0, "");
						break;
					default:
						break;
					}
				}
			}
			// 删除邮件
			afficheDAO.deleteAffiche(a.getId(), user.getUserId());
		}

		// 仓库道具、装备
		List<UserGoodsVO> goodsVOs = new ArrayList<UserGoodsVO>();
		if (!itemGoods.isEmpty()) {
			userGoodsService.checkBackageFull(backage, user);
			goodsVOs.addAll(userGoodsService.addGoods(user, backage, itemGoods, LoggerType.TYPE_公告获得, ""));
		}

		// 战魂
		List<UserSoulVO> soulVOs = new ArrayList<UserSoulVO>();
		if (!soulGoods.isEmpty()) {
			userSoulService.checkSoulFull(user);
			for (Goods goods : soulGoods) {
				for (int i = 1; i <= goods.getNum(); i++) {
					UserSoul soul = userSoulService.addUserSoul(user, goods.getGoodsId(), goods.getLevel(), LoggerType.TYPE_公告获得);

					// 返回给前端的VO
					UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
					vo.init(soul);
					soulVOs.add(vo);
				}
			}
		}

		// 用户统计
		UserStat stat = userStatDAO.queryUserStat(user.getUserId());

		result.setSouls(soulVOs);
		result.setGoodses(goodsVOs);
		result.setFriendlyPoint(stat.getFriendlyPoint());
		result.setGold(user.getGold());
		result.setCurrency(user.getCurrency());
		result.setPoint(user.getPoint());
		result.setHonor(user.getHonor());
		result.setAddPoint(user.getPoint() - oldPoint);
		result.setAddGold(user.getGold() - oldGold);
		result.setAddCurrency(user.getCurrency() - oldCurrency);
		result.setAddHonnor(user.getHonor() - oldHonor);
		return result;
	}

	@Override
	public void addAffiches(List<Affiche> as) {
		if (!as.isEmpty()) {
			for (Affiche a : as) {
				afficheDAO.addAffiche(a);
				sentMailNotifi(a.getUserId());
			}
		}
	}

	@Override
	public void useGiftCode(int userId, String code, List<Goods> goodsList) {
		User user = userService.getExistUser(userId);
		List<Affiche> affiches = new ArrayList<Affiche>();
		// for(Goods goods:goodsList){
		Affiche a = Affiche.create(user.getUserId(), Affiche.AFFICHE_TYPE_指定时间段登录, "礼品券兑换", "恭喜您成功兑换礼品券！祝您游戏愉快！", goodsList, Affiche.STATE_未读, "0");
		affiches.add(a);
		// }
		this.addAffiches(affiches);
	}

	@Override
	public List<Affiche> queryAffiche(int userId) {
		return afficheDAO.queryAffiches(userId);
	}

	@Override
	public GainAwardVO getAllAfficheGoods(int userId) {
		List<Affiche> afs = afficheDAO.queryAffiches(userId);
		User user = userService.getExistUserCache(userId);
		Backage backage = userGoodsService.getPackage(user.getUserId());
		return viewAffiche(user, afs, backage);
	}

	/**
	 * 通知用户有新邮件
	 * 
	 * @param userId
	 */
	private void sentMailNotifi(int userId) {
		GameEvent event = new NotifiEvent(userId, NotifiEvent.NOTIF_TYPE_邮件);
		TimerController.submitGameEvent(event);
	}

	@Override
	public void viewAllAffiche(int userId, Collection<Integer> ids) {
		afficheDAO.updateAfficheState(userId, Affiche.STATE_已读, ids);
	}

	@Override
	public void cleanAffiche() {
		Calendar calendar = DateUtil.getTodayZero();
		afficheDAO.cleanAffiche(Affiche.STATE_已读, new Date(calendar.getTimeInMillis() - 7 * 24 * 60 * 60 * 1000L));
		afficheDAO.cleanAffiche(Affiche.STATE_未读, new Date(calendar.getTimeInMillis() - 30 * 24 * 60 * 60 * 1000L));
	}

	public GainAwardVO gainGoods(List<? extends Goods> goodslist, User user, Backage backage, int[] types, String des) {
		GainAwardVO result = MessageFactory.getMessage(GainAwardVO.class);

		List<UserSoul> souls = new ArrayList<UserSoul>();
		List<Goods> itemGoods = new ArrayList<Goods>();
		List<UserGoodsVO> goodses = new ArrayList<UserGoodsVO>();
		List<Goods> soulGoods = new ArrayList<>();
		int oldPoint = user.getPoint();
		int oldGold = user.getGold();
		int oldCurrency = user.getCurrency();
		for (Goods gd : goodslist) {
			Goods goods = gd;
			switch (goods.getType()) {
			case Goods.TYPE_PROP:
			case Goods.TYPE_STUFF:
			case Goods.TYPE_EQUIPMENT:
				itemGoods.add(goods);
				break;
			case Goods.TYPE_GOLD:
				userService.incrementGold(user, goods.getNum(), types[0], des);
				break;
			case Goods.TYPE_CURRENCY:
				userService.incrementCurrency(user, goods.getNum(), types[1], des);
				break;
			case Goods.TYPE_FRIENDLY_POINT:
				userService.incrementFriendlyPoint(user.getUserId(), goods.getNum(), types[2], des);
				break;
			case Goods.TYPE_SOUL:
				soulGoods.add(goods);
				break;
			case Goods.TYPE_BAK_PROP:
				userGoodsService.addBakProps(user, goods.getGoodsId(), goods.getNum(), types[3], des);
				break;
			case Goods.TYPE_STAMINA:
				userService.incrementStamina(user, goods.getNum(), types[4], "boss send");
				break;
			case Goods.TYPE_THREE_LEVEL_RANDOM_EXPSOUL:
				for (int i = 0; i < goods.getNum(); i++) {
					// 随机一个3星经验宝
					int random = (int) (Math.random() * Soul.THREE_LEVEL_EXPSOULS.length);
					Goods soul = Goods.create(Soul.THREE_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
					soulGoods.add(soul);
				}
				break;
			case Goods.TYPE_FOUR_LEVEL__RANDOM_EXPSOUL:
				for (int i = 0; i < goods.getNum(); i++) {
					// 随机一个4星经验宝
					int random = (int) (Math.random() * Soul.FOUR_LEVEL_EXPSOULS.length);
					Goods soul = Goods.create(Soul.FOUR_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
					soulGoods.add(soul);
				}
				break;
			case Goods.TYPE_FIVE_LEVEL_RANDOM_EXPSOUL:
				for (int i = 0; i < goods.getNum(); i++) {
					// 随机一个5星经验宝
					int random = (int) (Math.random() * Soul.FIVE_LEVEL_EXPSOULS.length);
					Goods soul = Goods.create(Soul.FIVE_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
					soulGoods.add(soul);
				}
				break;
			case Goods.TYPE_POINT:
				userService.increPoint(user, goods.getNum(), 0, "");
				break;
			case Goods.TYPE_HONOR:
				userService.increHonor(user, goods.getNum(), 0, "");
				break;
			default:
				break;
			}

		}

		if (itemGoods.size() != 0) {
			goodses.addAll(userGoodsService.addGoods(user, backage, itemGoods, types[5], ""));
		}
		if (!soulGoods.isEmpty()) {
			for (Goods goods : soulGoods) {
				for (int i = 1; i <= goods.getNum(); i++) {
					UserSoul soul = userSoulService.addUserSoul(user, goods.getGoodsId(), goods.getLevel(), types[6]);
					souls.add(soul);
				}
			}
		}
		List<UserSoulVO> soulVOs = new ArrayList<UserSoulVO>();
		for (UserSoul soul : souls) {
			UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
			vo.init(soul);
			soulVOs.add(vo);
		}
		UserStat stat = userStatDAO.queryUserStat(user.getUserId());
		result.setFriendlyPoint(stat.getFriendlyPoint());
		result.setSouls(soulVOs);
		result.setGold(user.getGold());
		result.setCurrency(user.getCurrency());
		result.setPoint(user.getPoint());
		result.setGoodses(goodses);
		result.setAddPoint(user.getPoint() - oldPoint);
		result.setAddGold(user.getGold() - oldGold);
		result.setAddCurrency(user.getCurrency() - oldCurrency);
		return result;
	}

}
