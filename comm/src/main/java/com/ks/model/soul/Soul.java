package com.ks.model.soul;

import java.io.Serializable;

/**
 * 战魂
 * 
 * @author ks
 */
public class Soul implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final int SOUL_MAX_LEVEL = 50;
	// 1火 2土 3气 4水 5光6暗
	public static final int SOUL_ELE_火 = 1;
	public static final int SOUL_ELE_土 = 2;
	public static final int SOUL_ELE_气 = 3;
	public static final int SOUL_ELE_水 = 4;
	public static final int SOUL_ELE_光 = 5;
	public static final int SOUL_ELE_暗 = 6;
	public static final int SOUL_RESHAPE_GOOD = 10000 * 5;
	public static final int[] STRANG_GOLD = new int[] { 100, 100, 100, 200, 200, 200 };

	public static final int[] SOUL_性格 = new int[] { 1, 2, 3, 4, 5, 6 };
	/**
	 * 性格加成 SOUL_TYPE_ADDITION[x]=性格-1 
	 * SOUL_TYPE_ADDITION[x][0]=生命加成
	 * SOUL_TYPE_ADDITION[x][1]=攻击加成
	 * SOUL_TYPE_ADDITION[x][2]=防御加成
	 * SOUL_TYPE_ADDITION[x][3]=回复加成
	 * */
	private static final double[][] SOUL_TYPE_ADDITION = { { 33.3, 16.7, 5.5, 6.6 }, { 30.3, 18.4, 5.5, 6.6 }, { 33.3, 15.2, 6.1, 6.6 },
	        { 30.3, 16.7, 5.5, 7.3 } };

	/** 满级总经验 */
	private static final int[] TOTLE_EXP = new int[] { 1000000, 1500000, 2000000, 2500000, 3000000, 4000000, 5000000 };

	/** 升级经验 LEVEL_UP_CURVE[x]=曲线类型 LEVEL_UP_CURVE[x][x]=下一级所需经验 */
	private static final int[][] LEVEL_UP_EXP = {
	        { 104, 106, 109, 111, 113, 116, 119, 121, 124 },
	        { 104, 106, 109, 111, 113, 116, 119, 121, 124, 126, 181, 184, 188, 192 },
	        { 121, 124, 126, 181, 184, 188, 192, 196, 200, 204, 208, 212, 217, 584, 595, 607, 618, 630, 642, 654, 666, 678, 691, 1140 },
	        { 200, 204, 208, 212, 217, 584, 595, 607, 618, 630, 642, 654, 666, 678, 691, 1140, 1161, 1181, 1202, 1223, 1245, 1266, 1288, 1310, 1332, 1873,
	                1905, 1936, 1968, 2000, 2032, 2065, 2098, 2131, 2165, 2411, 2448, 2486, 2524, 2562, 2601, 2640, 2679, 2719, 2758, 4306, 4368, 4431, 4494 },
	        { 666, 678, 691, 1140, 1161, 1181, 1202, 1223, 1245, 1266, 1288, 1310, 1332, 1873, 1905, 1936, 1968, 2000, 2032, 2065, 2098, 2131, 2165, 2411,
	                2448, 2486, 2524, 2562, 2601, 2640, 2679, 2719, 2758, 4306, 4368, 4431, 4494, 4558, 4623, 4687, 4753, 4819, 4885, 6013, 6094, 6177, 6260,
	                6343, 6427, 6512, 6597, 6683, 6769, 8066, 8169, 8273, 8377, 8482, 8588, 8695, 8802, 8910, 9019, 9128, 9238, 9349, 9460, 9572, 9685, 9798,
	                9913, 10027, 10143, 10259 },
	        { 1936, 1968, 2000, 2032, 2065, 2098, 2131, 2165, 2411, 2448, 2486, 2524, 2562, 2601, 2640, 2679, 2719, 2758, 4306, 4368, 4431, 4494, 4558, 4623,
	                4687, 4753, 4819, 4885, 6013, 6094, 6177, 6260, 6343, 6427, 6512, 6597, 6683, 6769, 8066, 8169, 8273, 8377, 8482, 8588, 8695, 8802, 8910,
	                9019, 9128, 9238, 9349, 9460, 9572, 9685, 9798, 9913, 10027, 10143, 10259, 10376, 10494, 10612, 10731, 10851, 10971, 11093, 11214, 11337,
	                11460, 11584, 11709, 11834, 11960, 12087, 12214, 12342, 12471, 12601, 12731, 12862, 12993, 13125, 13258, 13392, 13526, 13661, 13797, 13934,
	                14071, 14209, 14347, 14486, 14626, 14767, 14908, 15050, 15193, 14907 } };

	/** 3星经验宝战魂id */
	public static final int THREE_LEVEL_EXPSOULS[] = { 1010209, 1010212, 1010215, 1010218, 1010221, 1010224 };

	/** 4星经验宝战魂id */
	public static final int FOUR_LEVEL_EXPSOULS[] = { 1010210, 1010213, 1010216, 1010219, 1010222, 1010225 };

	/** 5星经验宝战魂id */
	public static final int FIVE_LEVEL_EXPSOULS[] = { 1010211, 1010214, 1010217, 1010220, 1010223, 1010226 };


	/** 游戏编号 */
	private int soulId;
	/** 战魂名称 */
	private String name;
	/** 稀有度 */
	private int soulRare;
	/** 属性1 */
	private int soulEle;
	/** 主动技ID */
	private int skill;
	/** 队长技ID */
	private int capSkill;
	/** 生命 */
	private int hp;
	/** 攻击 */
	private int atk;
	/** 防御 */
	private int def;
	/** 回复 */
	private int rep;
	/** 攻击次数 */
	private int attackHit;
	/** 最高LV */
	private int lvMax;
	/** LV类型 */
	private int lvMode;
	/** 战魂cost */
	private int soulCost;
	/** 产出经验 */
	private int giveExp;
	/** 出售价格 */
	private int sellGold;
	/** 技能hit次数 */
	private int skillHit;
	/** 系列 */
	private int series;
	/** 成长因子 */
	private double growthFactor;
	/**跑马灯(0:不显示， 1:显示)*/
	private int marquee;
	/**捐赠后获得荣誉值*/
	private int addDevote;

	/**
	 * 获得性格加成
	 * 
	 * @param soulType
	 *            性格
	 * @return 性格加成
	 */
	public static final double[] getSoulTypeAddition(int soulType) {
		return SOUL_TYPE_ADDITION[soulType - 1];
	}

	/**
	 * 获得升级经验
	 * 
	 * @param lvModel
	 *            等级类型
	 * @param level
	 *            等级
	 * @return 升级经验
	 */
	public static final int getLevelUpExp(int lvModel, int level) {
		return LEVEL_UP_EXP[lvModel - 1][level - 1];
	}

	/**
	 * 获得满级总经验
	 * 
	 * @param lvModel
	 *            等级类型
	 * @return 满级总经验
	 */
	public static final int getTotleExp(int lvModel) {
		return TOTLE_EXP[lvModel - 1];
	}

	/** 用户战魂出售价格公式=填表金钱+(稀有度*稀有度)*99+等级*63 */
	public int getSellPrice(int grade) {
		return this.sellGold + (this.soulRare * this.soulRare) * 99 + grade * 63;
	}

	/** 强化时素材产出经验公式：填表经验*（经验类型+稀有度）*等级 */
	public int getStrongEx(int grade) {
		return (int) (this.giveExp * (this.lvMode + this.soulRare) * Math.pow(grade, 0.5));
	}

	//
	// 当强化对象的稀有度是1星、2星或者3星时，每增加一个强化素材所需要的费用是：强化对象的等级*100；
	// 当强化对象的稀有度是4星、5星或者6星时，每增加一个强化素材所需要的费用是：强化对象的等级*200
	public int getStrongGold(int grade, int materialSize) {
		return STRANG_GOLD[this.soulRare - 1] * grade * materialSize;
	}

	public int getSoulId() {
		return soulId;
	}

	public void setSoulId(int soulId) {
		this.soulId = soulId;
	}

	public int getSoulRare() {
		return soulRare;
	}

	public void setSoulRare(int soulRare) {
		this.soulRare = soulRare;
	}

	public int getSkill() {
		return skill;
	}

	public void setSkill(int skill) {
		this.skill = skill;
	}

	public int getCapSkill() {
		return capSkill;
	}

	public void setCapSkill(int capSkill) {
		this.capSkill = capSkill;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getRep() {
		return rep;
	}

	public void setRep(int rep) {
		this.rep = rep;
	}

	public int getLvMax() {
		return lvMax;
	}

	public void setLvMax(int lvMax) {
		this.lvMax = lvMax;
	}

	public int getLvMode() {
		return lvMode;
	}

	public void setLvMode(int lvMode) {
		this.lvMode = lvMode;
	}

	public int getSoulCost() {
		return soulCost;
	}

	public void setSoulCost(int soulCost) {
		this.soulCost = soulCost;
	}

	public int getGiveExp() {
		return giveExp;
	}

	public void setGiveExp(int giveExp) {
		this.giveExp = giveExp;
	}

	public int getSellGold() {
		return sellGold;
	}

	public void setSellGold(int sellGold) {
		this.sellGold = sellGold;
	}

	public int getSoulEle() {
		return soulEle;
	}

	public void setSoulEle(int soulEle) {
		this.soulEle = soulEle;
	}

	public int getAttackHit() {
		return attackHit;
	}

	public void setAttackHit(int attackHit) {
		this.attackHit = attackHit;
	}

	public int getSeries() {
		return series;
	}

	public void setSeries(int series) {
		this.series = series;
	}

	public int getSkillHit() {
		return skillHit;
	}

	public void setSkillHit(int skillHit) {
		this.skillHit = skillHit;
	}

	public double getGrowthFactor() {
		return growthFactor;
	}

	public void setGrowthFactor(double growthFactor) {
		this.growthFactor = growthFactor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMarquee() {
		return marquee;
	}

	public void setMarquee(int marquee) {
		this.marquee = marquee;
	}

	public int getAddDevote() {
		return addDevote;
	}

	public void setAddDevote(int addDevote) {
		this.addDevote = addDevote;
	}
}
