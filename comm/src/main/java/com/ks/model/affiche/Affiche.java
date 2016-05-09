package com.ks.model.affiche;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ks.model.goods.Goods;
import com.ks.model.soul.Soul;

/**
 * 公告
 * 
 * @author ks
 */
public class Affiche implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int STATE_未读 = 1;
	public static final int STATE_已读 = 2;

	public static final int AFFICHE_TYPE_开服有礼 = 2;
	public static final int AFFICHE_TYPE_指定时间段登录 = 3;
	public static final int AFFICHE_TYPE_连续登录 = 4;
	public static final int AFFICHE_TYPE_累计登录 = 5;
	public static final int AFFICHE_TYPE_成就奖励 = 6;
	public static final int AFFICHE_TYPE_礼品券奖励 = 7;
	public static final int AFFICHE_TYPE_竞技场称号升级奖励 = 8;
	public static final int AFFICHE_TYPE_充值送奖励 = 9;
	public static final int AFFICHE_TYP_限时礼包物品 = 10;
	public static final int AFFICHE_TYP_系统发放 = 11;
	public static final int AFFICHE_TYP_冲级等级奖励 = 12;
	public static final int AFFICHE_TYP_等级排行礼包 = 13;
	public static final int AFFICHE_TYP_竞技场排行礼包 = 14;
	public static final int AFFICHE_TYP_关卡进度礼包 = 15;
	public static final int AFFICHE_TYP_世界boss排名奖励 = 16;
	public static final int AFFICHE_TYP_交换排行榜排名奖励 = 17;
	/** 编号 */
	private int id;
	/** 用户编号 */
	private int userId;
	/** 类型(邮件来源) */
	private int type;
	/** 标题 */
	private String title;
	/** 内容 */
	private String context;
	/** 物品 */
	private List<Goods> goodsList;
	/** 状态 */
	private int state;
	/** 创建时间 */
	private Date createTime;
	/** 修改时间 */
	private Date updateTime;
	/** 邮件图标 */
	private String logo;

	public static Affiche create(int userId, int type, String title, String context, List<Goods> list, int state, String logo) {
		Affiche affiche = new Affiche();
		affiche.setUserId(userId);
		affiche.setType(type);
		affiche.setTitle(title);
		affiche.setContext(context);
		affiche.setState(state);
		affiche.setLogo(logo);

		affiche.setGoodsList(converToMailReward(list));
		return affiche;
	}

	/**
	 * 随机经验宝{@link Goods#TYPE_RANDOM_EXPSOUL} 在发送邮件时就随机出具体的经验宝
	 * 
	 * @return
	 */
	public static List<Goods> converToMailReward(List<Goods> list) {
		if (list != null) {
			// 随机经验宝在发送邮件的时候就随机出具体的经验宝
			List<Goods> rewardGoods = new ArrayList<>();
			for (Goods good : list) {
				if (good.getType() == Goods.TYPE_THREE_LEVEL_RANDOM_EXPSOUL) {
					// 随机一个3星经验战魂
					for (int i = 0; i < good.getNum(); i++) {
						int random = (int) (Math.random() * Soul.THREE_LEVEL_EXPSOULS.length);
						Goods soul = Goods.create(Soul.THREE_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
						rewardGoods.add(soul);
					}
				} else if (good.getType() == Goods.TYPE_FOUR_LEVEL__RANDOM_EXPSOUL) {
					// 随机一个4星经验战魂
					for (int i = 0; i < good.getNum(); i++) {
						int random = (int) (Math.random() * Soul.FOUR_LEVEL_EXPSOULS.length);
						Goods soul = Goods.create(Soul.FOUR_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
						rewardGoods.add(soul);
					}
				} else if (good.getType() == Goods.TYPE_FIVE_LEVEL_RANDOM_EXPSOUL) {
					// 随机一个5星经验战魂
					for (int i = 0; i < good.getNum(); i++) {
						int random = (int) (Math.random() * Soul.FIVE_LEVEL_EXPSOULS.length);
						Goods soul = Goods.create(Soul.FIVE_LEVEL_EXPSOULS[random], Goods.TYPE_SOUL, 1, 1);
						rewardGoods.add(soul);
					}
				} else {
					rewardGoods.add(good);
				}
			}
			return rewardGoods;
		}
		return list;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Goods> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<Goods> goodsList) {
		this.goodsList = goodsList;
	}
}
