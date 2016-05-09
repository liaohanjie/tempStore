package com.ks.logic.action;

import java.util.List;
import java.util.Set;
import com.ks.action.logic.AllianceAction;
import com.ks.logic.service.AllianceService;
import com.ks.logic.service.ServiceFactory;
import com.ks.protocol.vo.alliance.BuildingVO;
import com.ks.protocol.vo.alliance.BuyItemVO;
import com.ks.protocol.vo.alliance.ContributeVO;
import com.ks.protocol.vo.alliance.SimpleAllianceInfoVO;
import com.ks.protocol.vo.alliance.UserAllianceInfoVO;

public class AllianceActionImpl  implements AllianceAction {
	
	private static AllianceService service = ServiceFactory.getService(AllianceService.class);

	@Override
	public void apply2Alliance(int userId, int allianceId) {
		service.apply2Alliance(userId, allianceId);
	}

	@Override
	public void quitAlliance(int userId, int allianceId) {
		service.quitAlliance(userId, allianceId);
	}

	@Override
	public void agreeApply(int userId, int allianceId, int applyId) {
		service.agreeApply(userId, allianceId, applyId);
	}

	@Override
	public UserAllianceInfoVO createAlliance(int userId, String allianceName, String descs) {
		return service.createAlliance(userId, allianceName, descs);
	}

	@Override
	public void destroyAlliance(int userId, int allianceId) {
		service.destroyAlliance(userId, allianceId);
	}

	@Override
	public UserAllianceInfoVO getUserAllianceInfo(int userId) {
		return service.getUserAllianceInfo(userId);
	}

	@Override
	public List<SimpleAllianceInfoVO> listAllianceInfos(int userId) {
		return service.listAllianceInfos(userId);
	}

	@Override
	public void updateNotice(int userId, int allianceId, String notice) {
		service.updateNotice(userId, allianceId, notice);
	}

	@Override
	public void updateDesc(int userId, int allianceId, String desc) {
		service.updateDesc(userId, allianceId, desc);
	}

	@Override
	public void kickMember(int userId, int allianceId, int kickId) {
		service.kickMember(userId, allianceId, kickId);
	}
	
	@Override
	public BuildingVO building(int userId, int allianceId, byte type) {
		return service.building(userId, allianceId, type);
	}

	@Override
	public ContributeVO contributeSoul(int userId, int allianceId, Set<Long> userSoulIds) {
		return service.contributeSoul(userId, allianceId, userSoulIds);
	}

	@Override
	public ContributeVO contributeStuff(int userId, int allianceId, int goodsId, int num) {
		return service.contributeStuff(userId, allianceId, goodsId, num);
	}

	@Override
	public void upgradeAlliance(int userId, int allianceId) {
		service.upgradeAlliance(userId, allianceId);
	}

	@Override
	public BuyItemVO buyItem(int userId, int itemId) {
		return service.buyItem(userId, itemId);
	}
}
