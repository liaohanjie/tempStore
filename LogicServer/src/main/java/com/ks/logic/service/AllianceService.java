package com.ks.logic.service;

import java.util.List;
import java.util.Set;
import com.ks.access.Transaction;
import com.ks.model.alliance.AllianceSetting;
import com.ks.model.alliance.AllianceShopItem;
import com.ks.protocol.vo.alliance.BuildingVO;
import com.ks.protocol.vo.alliance.BuyItemVO;
import com.ks.protocol.vo.alliance.ContributeVO;
import com.ks.protocol.vo.alliance.SimpleAllianceInfoVO;
import com.ks.protocol.vo.alliance.UserAllianceInfoVO;

/**
 * 工会服务
 * @author hanjie.l
 *
 */
public interface AllianceService {
	
	/**
	 * 获取所有工会配置
	 * @return
	 */
	public List<AllianceSetting> getAllAllianceSetting();
	
	/**
	 * 获取所有工会商店配置
	 * @return
	 */
	public List<AllianceShopItem> getAllianceShopItems();
	
	/**
	 * 申请加入工会
	 * @param userId	  申请人id
	 * @param allianceId 工会id
	 */
	@Transaction
	public void apply2Alliance(int userId, int allianceId);
	
	/**
	 * 退出工会
	 * @param userId
	 * @param allianceId
	 */
	@Transaction
	public void quitAlliance(int userId, int allianceId);
	
	/**
	 * 同意入会申请
	 * @param userId     同意人id
	 * @param allianceId 工会id
	 * @param applyId	   申请人id
	 */
	@Transaction
	public void agreeApply(int userId, int allianceId, int applyId);
	
	/**
	 * 拒绝忽略请求
	 * @param userId
	 * @param allianceId
	 * @param applyId
	 */
	@Transaction
	public void refuseApply(int userId, int allianceId, int applyId);
	
	/**
	 * 创建工会
	 * @param userId
	 * @param allianceName
	 * @param descs
	 */
	@Transaction
	public UserAllianceInfoVO createAlliance(int userId, String allianceName, String descs);
	
	/**
	 * 解散工会
	 * @param userId
	 * @param allianceId
	 */
	@Transaction
	public void destroyAlliance(int userId, int allianceId);
	
	/**
	 * 获取玩家工会信息
	 */
	@Transaction
	public UserAllianceInfoVO getUserAllianceInfo(int userId);
	
	/**
	 * 获取到所有工会信息
	 * @param userId
	 * @return
	 */
	@Transaction
	public List<SimpleAllianceInfoVO> listAllianceInfos(int userId);
	
	
	/**
	 * 更新工会公告
	 * @param userId
	 * @return
	 */
	@Transaction
	public void updateNotice(int userId, int allianceId, String notice);
	
	/**
	 * 更新工会描述
	 * @param userId
	 * @return
	 */
	@Transaction
	public void updateDesc(int userId, int allianceId, String desc);
	
	/**
	 * 踢人
	 * @param userId
	 * @param allianceId
	 * @param kickId 被踢玩家
	 */
	@Transaction
	public void kickMember(int userId, int allianceId, int kickId);
	
	/**
	 * 建设
	 * @param userId
	 * @param allianceId
	 * @param type 建设类型    1-普通建设    2-金币建设  3-魂钻建设
	 * 
	 */
	@Transaction
	public BuildingVO building(int userId, int allianceId, byte type);
	
	/**
	 * 捐献战魂
	 * @param userId
	 * @param allianceId
	 * @param userSoulIds
	 */
	@Transaction
	public ContributeVO contributeSoul(int userId, int allianceId, Set<Long> userSoulIds);
	
	/**
	 * 捐献材料
	 * @param userId
	 * @param allianceId
	 * @param goodsId
	 * @param num
	 */
	@Transaction
	public ContributeVO contributeStuff(int userId, int allianceId, int goodsId, int num);
	
	/**
	 * 升级工会
	 * @param userId
	 * @param allianceId
	 * 
	 */
	@Transaction
	public void upgradeAlliance(int userId, int allianceId);
	
	/**
	 * 购买道具
	 * @param userId
	 * @param itemId
	 */
	@Transaction
	public BuyItemVO buyItem(int userId, int itemId);
}
