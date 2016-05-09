package com.ks.protocol.sub;
/**
 * 竞技场子命令
 * @author fengpeng
 * @date   2014年7月1日
 */
public interface AthleticsInfoCMD  {
	/**对手信息*/
	short ATHLETICSINFO_匹配对手信息 = 1;
	
	short ATHLETICSINFO_排行榜信息 = 2;
	/**进入竞技场信息*/
	short ATHLETICSINFO_进入竞技场 = 3;
	
	/**开始PK*/
	short ATHLETICSINFO_开始战斗 = 4;
	
	/**花钱购买竞技点*/
	short ATHLETICSINFO_花钱购买竞技点 = 5;
}
                                                  