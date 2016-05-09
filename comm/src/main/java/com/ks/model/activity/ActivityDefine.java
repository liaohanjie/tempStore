package com.ks.model.activity;

import java.io.Serializable;
import java.util.Date;

/**
 * 活动
 * @author living.li
 * @date   2014年6月23日
 */
public class ActivityDefine implements Serializable{

	private static final long serialVersionUID = 1L;
	
	public static final String WEEK_SPLIT="_";
	
	public final static int TYPE_日常副本=1;
	
	public final static int TYPE_紧急副本=2;
	
	public final static int TYPE_特殊副本=3;
	
	public final static int TYPE_掉落概率活动=4;	

	public final static int TYPE_充值活动=5;	

	public final static int TYPE_打折=6;	
	
	/**新添加活动*/
	public final static int TYPE_NEW=7;
	
	public final static  int DEFINE_ID_限时购买魂币送礼=4001;
	public final static  int DEFINE_ID_限量购买魂币送礼=4002;
	public final static  int DEFINE_ID_战魂仓库格子打折=4003;
	public final static  int DEFINE_ID_道具仓库格子打折=4004;
	public final static  int DEFINE_ID_好友上限数量打折=4005;
	public final static  int DEFINE_ID_体力打折=4006;
	public final static  int DEFINE_ID_竞技点打折=4007;
	public final static  int DEFINE_ID_免费领体力=4008;
	public final static  int DEFINE_ID_送体力=4009;
	
	public final static int DEFINE_ID_关卡产出概率翻倍=5001;
	public final static int DEFINE_ID_体力消耗减半=5002;
	public final static int DEFINE_ID_乱入概率翻倍=5003;
	public final static int DEFINE_ID_友情点翻倍=5004;
	public final static int DEFINE_ID_强化大成功概率=5005;
	public final static int DEFINE_ID_强化超成功概率=5006;
	public final static int DEFINE_ID_强化技能概率=5007;
	public final static int DEFINE_ID_召唤战魂概率=5008;
	public final static int DEFINE_ID_友情召唤出指定战魂=5009;
	public final static int DEFINE_ID_魂币召唤出指定战魂=5010;
	public final static int DEFINE_ID_冲级活动=5011;
	public final static int DEFINE_ID_连续登陆活动=5012;
	/**
	 * 首次充值(首充), 需要拿到首次充值金额
	 * 奖励：3倍魂钻、金币、友情点、战魂、道具、材料、装备、钥匙（可配）
	 */
	public final static int DEFINE_ID_首充活动=5013;
	/**限时礼包*/
	public final static int DEFINE_ID_限时礼包 = 5014;
	/** 注册用户就送礼包 */
	public final static int DEFINE_ID_开服有礼 = 5015;
	/**冲榜赛(不同关键字对应不同礼包，等级：key=100x, 竞技 key=200x， 推图 key=300x)*/
	public final static int DEFINE_ID_冲榜赛活动 = 5016;
	/**连续每日充值送豪礼*/
	public final static int DEFINE_ID_每日充值送豪礼 = 5017;
	/**收集送礼(收集战魂)*/
	public final static int DEFINE_ID_收集送礼 = 5018;
	/**消费送礼(消费魂钻额度送礼)*/
	public final static int DEFINE_ID_消费送礼 = 5019;
	/**在线时长送礼*/
	public final static int DEFINE_ID_在线礼包 = 5020;
	/**每天登录送礼包*/
	public final static int DEFINE_ID_每天登录送礼包 = 5021;
	/**全名福利，购买成长基金到指定人数全民可以免费领取*/
	public final static int DEFINE_ID_全民福利 = 5022;
	/**七天送礼，每天可以领取一份，没有时间限制*/
	public final static int DEFINE_ID_七天送礼 = 5023;
	/**首次创建账号起，每天完成相应任务*/
	public final static int DEFINE_ID_开服活动 = 5024;
	/**首次创建账号七天额外奖励*/
	public final static int DEFINE_ID_七天额外奖励 = 5025;
	
	/**状态，礼包未发放*/
	public final static int STATUS_GIFT_ISSUE_NOT_COMPLETE = 0;
	/**状态，礼包已发放*/
	public final static int STATUS_GIFT_ISSUE_COMPLETE = 1;
	
	/**未定义*/
	public final static byte TYPE_CLASS_未定义 = 0;
	/**强化素材*/
	public final static byte TYPE_CLASS_强化素材 = 1;
	/**进化素材*/
	public final static byte TYPE_CLASS_进化素材 = 2;
	/**获得金币*/
	public final static byte TYPE_CLASS_获得金币 = 3;
	/**获得战魂*/
	public final static byte TYPE_CLASS_获得战魂 = 4;
	
	private int id;
	
	private int defineId;
	
	private String name;
	
	private int type;
	
	private Date startTime;
	
	private Date endTime;
	
	private int startHour;
	
	private int endHour;
	
	/*中国星期习惯  周日=7*/
	private String weekTime;
	
	private Date createTime;
	
	private Date updateTime;
	
	private String chapterIds;
	
	private String title;
	
	private String context;
	
	/**状态(0: 礼包未发放， 1:礼包发放完成, N: 自定义)*/
	private int status;
	
	/**活动类型类别，客户端显示tab使用*/
	private int typeClass;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getCreateTime() {
		return createTime;
	}
	
	public int getDefineId() {
		return defineId;
	}

	public void setDefineId(int defineId) {
		this.defineId = defineId;
	}

	public boolean isWeekTime(int week){
		return weekTime.indexOf(week+WEEK_SPLIT)!=-1;
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

	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getChapterIds() {
		return chapterIds;
	}
	public void setChapterIds(String chapterIds) {
		this.chapterIds = chapterIds;
	}
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public int getEndHour() {
		return endHour;
	}

	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}

	public String getWeekTime() {
		return weekTime;
	}

	public void setWeekTime(String weekTime) {
		this.weekTime = weekTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(int typeClass) {
		this.typeClass = typeClass;
	}
}
