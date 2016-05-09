package com.ks.protocol.sub;

/**
 * 商城
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年7月16日
 */
public interface ShopCMD {
	
	/**购买*/
	short BUY = 1;
	
	/**神秘商店列表*/
	short MYSTERY_SHOP_LIST = 2;
	
	/**刷新神秘商店列表*/
	short FRESH_MYSTERY_SHOP_LIST = 3;
	
	/**神秘商店购买*/
	short MYSTER_SHOP_BUY = 4;
	
	/**神秘商店购买记录*/
	short MYSTER_SHOP_BUY_RECORD = 5;
}
                                                  