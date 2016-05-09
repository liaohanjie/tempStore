package com.ks.action.logic;

import java.util.List;
import java.util.Set;
import com.ks.protocol.vo.alliance.BuildingVO;
import com.ks.protocol.vo.alliance.BuyItemVO;
import com.ks.protocol.vo.alliance.ContributeVO;
import com.ks.protocol.vo.alliance.SimpleAllianceInfoVO;
import com.ks.protocol.vo.alliance.UserAllianceInfoVO;

/**
 * 工会Action
 * @author admin
 *
 */
public interface AllianceAction {
	
	/**
	 * 申请加入工会
	 * @param userId	  申请人id
	 * @param allianceId 工会id
	 */
	public void apply2Alliance(int userId, int allianceId);
	
	/**
	 * 退出工会
	 * @param userId
	 * @param allianceId
	 */
	public void quitAlliance(int userId, int allianceId);
	
	/**
	 * 同意入会申请
	 * @param userId     同意人id
	 * @param allianceId 工会id
	 * @param applyId	   申请人id
	 */
	public void agreeApply(int userId, int allianceId, int applyId);
	
	/**
	 * 创建工会
	 * @param userId
	 * @param allianceName
	 * @param descs
	 */
	public UserAllianceInfoVO createAlliance(int userId, String allianceName, String descs);
	
	/**
	 * 解散工会
	 * @param userId
	 * @param allianceId
	 */
	public void destroyAlliance(int userId, int allianceId);
	
	/**
	 * 获取玩家工会信息
	 */
	public UserAllianceInfoVO getUserAllianceInfo(int userId);
	
	/**
	 * 获取到所有工会信息
	 * @param userId
	 * @return
	 */
	public List<SimpleAllianceInfoVO> listAllianceInfos(int userId);
	
	/**
	 * 更新工会公告
	 * @param userId
	 * @return
	 */
	public void updateNotice(int userId, int allianceId, String notice);
	
	/**
	 * 更新工会描述
	 * @param userId
	 * @return
	 */
	public void updateDesc(int userId, int allianceId, String desc);
	
	/**
	 * 踢人
	 * @param userId
	 * @param allianceId
	 * @param kickId 被踢玩家
	 */
	public void kickMember(int userId, int allianceId, int kickId);
	
	/**
	 * 建设
	 * @param userId
	 * @param allianceId
	 * @param type 建设类型    1-普通建设    2-金币建设  3-魂钻建设
	 * 
	 */
	public BuildingVO building(int userId, int allianceId, byte type);
	
	/**
	 * 捐献战魂
	 * @param userId
	 * @param allianceId
	 * 
	 */
	public ContributeVO contributeSoul(int userId, int allianceId, Set<Long> userSoulIds);
	
	/**
	 * 捐献材料
	 * @param userId
	 * @param allianceId
	 * @param goodsId
	 * @param num
	 */
	public ContributeVO contributeStuff(int userId, int allianceId, int goodsId, int num);
	
	/**
	 * 升级工会
	 * @param userId
	 * @param allianceId
	 * 
	 */
	public void upgradeAlliance(int userId, int allianceId);
	
	/**
	 * 购买道具
	 * @param userId
	 * @param itemId
	 */
	public BuyItemVO buyItem(int userId, int itemId);
}
