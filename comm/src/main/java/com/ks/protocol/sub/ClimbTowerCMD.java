package com.ks.protocol.sub;

/**
 * 爬塔命令
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月11日
 */
public interface ClimbTowerCMD {
	
	/**查询指定用户层数的爬塔信息*/
	short QUERY_CLIMB_TOWER_FLOOR = 1;
	
	/**查询用户所有爬塔信息*/
	short QUERY_ALL_CLIMB_TOWER_FLOOR = 2;
	
	/**开始战斗*/
	short START_FIGHT = 3;
	
	/**结束战斗*/
	short END_FIGHT = 4;
	
	/**购买一次指定层额外的爬塔次数*/
	short BUG_CLIMB_TOWER_EXTRA_COUNT = 5;
	
	/**扫荡*/
	short SWEEP = 6;
	
	/**领取集星奖励*/
	short GET_STAR_AWARD = 7;
	
	/**查询用户每个区域星星最大记录*/
	short QUERY_EVERY_REGION_STAR_AWARD_RECORD = 8;
}
                                                  