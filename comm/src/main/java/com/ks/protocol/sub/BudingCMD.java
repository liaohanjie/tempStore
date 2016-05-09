package com.ks.protocol.sub;

public interface BudingCMD {
	/**获取用户建筑*/
	short GAIN_USER_BUDINGS = 1;
	/**升级用户建筑*/
	short LEVEL_UP_BUDING = 2;
	/**收集用户建筑*/
	short COLLECT_BUDING = 3;
	/**批量收集用户单个建筑所有物品*/
	short COLLECT_SINGLEL_BUDING_ALL_PROP = 4;
}
