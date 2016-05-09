package com.ks.exceptions;

import com.ks.exception.EngineException;

/**
 * 游戏异常，错误码
 * @author ks.wu
 *
 */
public class GameException extends EngineException {

	private static final long serialVersionUID = 1L;
	
	public static final int CODE_外挂 = -1;
	public static final int CODE_参数错误 = 0;
	public static final int CODE_协议异常 = 1;
	public static final int CODE_已经掉线 = 2;
	public static final int CODE_战魂不存在 = 3;
	public static final int CODE_战魂个数已达上限 = 4;
	public static final int CODE_心跳异常 = 5;
	public static final int CODE_用户不存在 = 6;
	public static final int CODE_体力不足 = 7;
	public static final int CODE_没有队长 = 8;
	public static final int CODE_COST不足 = 9;
	public static final int CODE_金币不足 = 10;
	public static final int CODE_你的好友已满 = 11;
	public static final int CODE_对方好友已满 = 12;
	public static final int CODE_友情点不足 = 13;
	public static final int CODE_道具数量不足 = 14;
	public static final int CODE_建筑不存在 = 15;
	public static final int CODE_建筑等级已满 = 16;
	public static final int CODE_背包已满 = 17;
	public static final int CODE_战魂仓库已到最大容量 = 18;
	public static final int CODE_道具仓库已到最大容量 = 19;
	public static final int CODE_好友容量已到最大容量 = 20;
	public static final int CODE_体力已满 = 21;
	public static final int CODE_体力购买次数已用完 = 22;
	public static final int CODE_成就不存在 = 23;
	public static final int CODE_成就奖励已领取 = 24;
	public static final int CODE_验证不通过=25;
	public static final int CODE_服务器错误=26;
	public static final int CODE_礼品券无效=27;
	public static final int CODE_服务器不存在=28;
	public static final int CODE_礼品券已被使用=29;
	public static final int CODE_错误的步骤=30;
	public static final int CODE_活动不存在=31;
	public static final int CODE_活动未开启=32;
	public static final int CODE_竞技点不够=33;
	public static final int CODE_匹配的竞技对手有误=34;
	public static final int CODE_没有副本道具=35;
	public static final int CODE_等级不够=36;
	public static final int CODE_不是vip=37;
	public static final int CODE_购买扫荡次数不足=38;
	public static final int CODE_扫荡次数不足=39;
	public static final int CODE_此章节没有通关不能进行扫荡=40;
	public static final int CODE_没有购买基金=41;
	public static final int CODE_已购买成长基金=42;
	public static final int CODE_充值总额=43;
	public static final int CODE_不能重复领取=44;
	public static final int CODE_该等级已达探索上线=45;
	public static final int CODE_该战魂在队伍中不可以探索=46;
	public static final int CODE_探索时间没有到不可以领奖=47;
	public static final int CODE_今日扫荡次数已经用完=48;
	public static final int CODE_礼包已经售完=49;
	public static final int CODE_任务已领奖=50;
	public static final int CODE_未完成任务不能领奖=51;
	public static final int CODE_战魂数量不够=52;
	public static final int CODE_签名非法=53;
	public static final int CODE_服务器人数已达上限 = 54;
	public static final int CODE_免费领取体力次数已经用完 = 55;
	public static final int CODE_礼包以领过 = 56;
	public static final int CODE_活动定义类型不存在 = 57;
	public static final int CODE_活动定义礼包日志类型不存在 = 58;
	public static final int CODE_活动领取未实现 = 59;
	public static final int CODE_活动礼包不能为空 = 60;
	public static final int CODE_活动礼包领取条件不满足 = 61;
	public static final int CODE_商品物品项配置为空 = 62;
	public static final int CODE_物品类型不存在 = 63;
	public static final int CODE_超出购买次数 = 64;
	public static final int CODE_魂钻不够 = 65;
	public static final int CODE_充值商品编号不存在 = 66;
	public static final int CODE_未知排行类型 = 67;
	public static final int CODE_礼品券已失效 = 68;
	public static final int CODE_同类型只能使用一次 = 69;
	public static final int CODE_战魂处于保护状态 = 70;
	public static final int CODE_战魂正在队伍中 = 71;
	public static final int CODE_战魂正在探索中 = 72;
	public static final int CODE_超出使用次数 = 73;
	public static final int CODE_材料不足 = 74;
	public static final int CODE_积分不足 = 75;
	public static final int CODE_配置表不存在 = 76;
	public static final int CODE_BOSS活动未开启 = 77;
	public static final int CODE_请先开始战斗 = 78;
	public static final int CODE_不在CD中 = 79;
	public static final int CODE_开放等级不足 = 80;
	public static final int CODE_奖励不存在 = 81; 
	public static final int CODE_奖励已领取 = 82;
	public static final int CODE_鼓舞已达到上限 = 83;
	public static final int CODE_爬塔试炼石不足 = 84;
	public static final int CODE_爬塔挑战次数不足 = 85;
	public static final int CODE_不能购买爬塔次数 = 86;
	public static final int CODE_BOSS排行榜暂无数据 = 87;
	public static final int CODE_爬塔排行榜暂无数据 = 88;
	public static final int CODE_交换竞技场战斗CD中 = 89;
	public static final int CODE_交换竞技场挑战次数不足 = 90;
	public static final int CODE_交换竞技场荣誉值不足 = 91;
	public static final int CODE_重复购买 = 92;
	public static final int CODE_相应货币类型不足 = 93;
	public static final int CODE_已存在该玩家名 = 94;
	public static final int CODE_玩家已封号 = 95;
	public static final int CODE_禁言 = 96;
	public static final int CODE_挑战次数不足 = 97;
	public static final int CODE_副本购买次数不足 = 98;
	public static final int CODE_工会不存在 = 99;
	public static final int CODE_已经申请过了 = 100;
	public static final int CODE_已经加入工会 = 101;
	public static final int CODE_没有权限 = 102;
	public static final int CODE_申请请求已过期 = 103;
	public static final int CODE_未加入工会 = 104;
	public static final int CODE_会长不能退出工会 = 105;
	public static final int CODE_工会已存在 = 106;
	public static final int CODE_工会成员已达到上限 = 107;
	public static final int CODE_工会成员不存在 = 108;
	public static final int CODE_已经建设过了 = 109;
	public static final int CODE_工会今日贡献值已达到上限 = 110;
	public static final int CODE_工会已达到最高等级 = 111;
	public static final int CODE_工会贡献点不足 = 112;
	public static final int CODE_工会等级不足 = 113;
	public static final int CODE_个人贡献点不足 = 114;
	
	
	public GameException(int code,String message){
		super(code,message);
	}
}
