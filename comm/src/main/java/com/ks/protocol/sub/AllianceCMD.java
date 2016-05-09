package com.ks.protocol.sub;

/**
 * 工会子命令
 * @author admin
 *
 */
public interface AllianceCMD {
	
	/**
	 * 获取用户工会信息
	 * @参数 无
	 * 
	 * @return UserAllianceInfoVO
	 */
	short GET_USERALLIANCE_INFO = 1;
	
	/**
	 * 创建工会
	 * @参数  String 工会名
	 * 		 String 工会描述 
	 * 
	 * @return AllianceInfoVO
	 */
	short CREATE_ALLIANCE = 2;
	
	/**
	 * 解散工会
	 * @参数 int 工会id
	 * 
	 * @return 无
	 */
	short DESTROY_ALLIANCE = 3;
	
	/**
	 * 申请加入工会
	 * @参数 int 工会id
	 * 
	 * @return 无
	 */
	short APPLY_ALLIANCE = 4;
	
	/**
	 * 同意入会申请
	 * @参数 int 工会id
	 * 		int 申请人id
	 * 
	 * @return 无
	 */
	short AGREE_APPLY = 5;
	
	/**
	 * 退出工会
	 * @参数 int 工会id
	 * 
	 * @return 无
	 */
	short QUIT_ALLIANCE = 6;
	
	/**
	 * 查看所有工会列表
	 * @参数 无
	 * 
	 * @return List<SimpleAllianceInfoVO>
	 */
	short LIST_ALL_ALLIANCE = 7;
	
	/**
	 * 更新公告
	 * @参数 int 工会id
	 * 		String 公告内容
	 * 
	 * @return 无
	 */
	short UPDATE_NOTICE = 8;
	
	/**
	 * 更新描述
	 * @参数 int 工会id
	 * 		String 描述内容
	 * 
	 * @return 无
	 */
	short UPDATE_DESC = 9;
	
	/**
	 * 踢人
	 * @参数 int 工会id
	 * 		int 要踢的玩家id
	 * 
	 * @return 无
	 */
	short KICK_MEMBER = 10;
	
	/**
	 * 建设
	 * @参数 int 工会id
	 * 		byte 建设类型     1-普通建设    2-金币建设  3-魂钻建设
	 * 
	 * @return 无
	 */
	short BUILDING = 11;
	
	/**
	 * 捐赠战魂
	 * @参数 int 工会id
	 * 		List<Long> 战魂id集合
	 * 
	 * @return ContributeVO
	 */
	short CONTRIBUTE_SOUL = 12;
	
	/**
	 * 捐赠材料
	 * @参数 int 工会id
	 * 		int 材料id
	 * 		int 数量
	 * 
	 * @return ContributeVO
	 */
	short CONTRIBUTE_STUFF = 13;
	
	/**
	 * 升级工会
	 * @参数 int 工会id
	 * 
	 * @return 无
	 */
	short UPGRADE_ALLIANCE = 14;
	
	/**
	 * 购买商城道具
	 * @参数 int 商品id
	 * 
	 * @return BuyItemVO
	 */
	short BUY_SHOP_ITEM = 15;
}
