package com.ks.game.handler;

import com.ks.action.logic.ActivityAction;
import com.ks.action.logic.AfficheAction;
import com.ks.action.logic.AllianceAction;
import com.ks.action.logic.AthleticsInfoAction;
import com.ks.action.logic.BossAction;
import com.ks.action.logic.ChapterAction;
import com.ks.action.logic.ClimbTowerAction;
import com.ks.action.logic.FightCheckAction;
import com.ks.action.logic.FriendAction;
import com.ks.action.logic.PlayerAction;
import com.ks.action.logic.ProductAction;
import com.ks.action.logic.RankAction;
import com.ks.action.logic.SoulExploretionAction;
import com.ks.action.logic.SwapArenaAction;
import com.ks.action.logic.UserAchieveAction;
import com.ks.action.logic.UserBudingAction;
import com.ks.action.logic.UserGoodsAction;
import com.ks.action.logic.UserMissionAction;
import com.ks.action.logic.UserSoulAction;
import com.ks.action.logic.UserTeamAction;
import com.ks.action.world.LockAction;
import com.ks.app.Application;
import com.ks.rpc.RPCKernel;

public class ActionAdapter {
	
	protected final static PlayerAction playerAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, PlayerAction.class);
	}
	protected final static UserTeamAction userTeamAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserTeamAction.class);
	}
	protected final static AfficheAction afficheAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, AfficheAction.class);
	}
	protected final static UserBudingAction userBudingAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserBudingAction.class);
	}
	protected final static ChapterAction chapterAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ChapterAction.class);
	}
	protected final static FriendAction friendAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, FriendAction.class);
	}
	protected final static UserGoodsAction userGoodsAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserGoodsAction.class);
	}
	protected final static UserSoulAction userSoulAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserSoulAction.class);
	}
	protected final static ActivityAction activityAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ActivityAction.class);
	}
	
	protected final static UserAchieveAction userAchieveAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAchieveAction.class);
	}
	
	protected final static AthleticsInfoAction athleticsInfoAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, AthleticsInfoAction.class);
	}
	
	protected final static SoulExploretionAction soulExploretionAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, SoulExploretionAction.class);
	}
	
	protected final static UserMissionAction userMissionAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserMissionAction.class);
	}
	
	protected final static ProductAction productAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ProductAction.class);
	}
	
	protected final static ClimbTowerAction climbTowerAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ClimbTowerAction.class);
	}

	protected final static BossAction bossAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, BossAction.class);
	}
	
	protected final static RankAction rankAction(){
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, RankAction.class);
	}
	
	public final static LockAction lockAction() {
		return RPCKernel.getRemoteByServerType(Application.WORLD_SERVER, LockAction.class);
	}
	
	public final static SwapArenaAction swapArenaAction() {
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, SwapArenaAction.class);
	}
	
	public final static FightCheckAction fightCheckAction() {
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, FightCheckAction.class);
	}
	
	public final static AllianceAction allianceAction() {
		return RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, AllianceAction.class);
	}
}
