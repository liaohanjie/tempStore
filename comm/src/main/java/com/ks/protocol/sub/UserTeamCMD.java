package com.ks.protocol.sub;
/**
 * 用户队伍命令
 * @author ks
 */
public interface UserTeamCMD {
	/**修改用户队伍*/
	short UPDATE_USER_TEAM = 1;
	
	/**查找用户当前队伍信息*/
	short FIND_USER_CURRENT_TEAM = 2;
	
	/**查找用户队长信息*/
	short FIND_USER_CAP = 3;
}
