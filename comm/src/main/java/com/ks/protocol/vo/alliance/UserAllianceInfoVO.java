package com.ks.protocol.vo.alliance;
import java.util.List;
import com.ks.model.alliance.constant.RoleType;
import com.ks.protocol.Message;
/**
 * 玩家所在工会信息
 * @author admin
 *
 */
public class UserAllianceInfoVO extends Message{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5791640002376937676L;

	/**
	 * 工会id
	 */
	private int allianceId;
	
	/**
	 * 在工会中的角色{@link RoleType}
	 */
	private int role;
	
	/**
	 * 贡献值
	 */
	private long devote;
	
	/**
	 * 今日普通建设次数
	 */
	private int generalBuild;
	
	/**
	 * 金币建设次数
	 */
	private int goldBuild;
	
	/**
	 * 魂钻建设次数
	 */
	private int currencyBuild;
	
	
	/**
	 * 工会信息VO
	 */
	private AllianceInfoVO allianceInfoVO;
	
	/**
	 * 工会成员信息
	 */
	private List<AllianceMemberVO> allianceMemberVOs;
	
	/**
	 * 入会申请[有权限的才会看得到]
	 */
	private List<ApplyUserInfoVO>  applyUserInfoVOs;

	public int getAllianceId() {
		return allianceId;
	}

	public void setAllianceId(int allianceId) {
		this.allianceId = allianceId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public List<AllianceMemberVO> getAllianceMemberVOs() {
		return allianceMemberVOs;
	}

	public void setAllianceMemberVOs(List<AllianceMemberVO> allianceMemberVOs) {
		this.allianceMemberVOs = allianceMemberVOs;
	}

	public List<ApplyUserInfoVO> getApplyUserInfoVOs() {
		return applyUserInfoVOs;
	}

	public void setApplyUserInfoVOs(List<ApplyUserInfoVO> applyUserInfoVOs) {
		this.applyUserInfoVOs = applyUserInfoVOs;
	}

	public AllianceInfoVO getAllianceInfoVO() {
		return allianceInfoVO;
	}

	public void setAllianceInfoVO(AllianceInfoVO allianceInfoVO) {
		this.allianceInfoVO = allianceInfoVO;
	}

	public long getDevote() {
		return devote;
	}

	public void setDevote(long devote) {
		this.devote = devote;
	}

	public int getGeneralBuild() {
		return generalBuild;
	}

	public void setGeneralBuild(int generalBuild) {
		this.generalBuild = generalBuild;
	}

	public int getGoldBuild() {
		return goldBuild;
	}

	public void setGoldBuild(int goldBuild) {
		this.goldBuild = goldBuild;
	}

	public int getCurrencyBuild() {
		return currencyBuild;
	}

	public void setCurrencyBuild(int currencyBuild) {
		this.currencyBuild = currencyBuild;
	}
}
