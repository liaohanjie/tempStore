package com.ks.logic.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ks.exceptions.GameException;
import com.ks.logic.cache.GameCache;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.UserTeamService;
import com.ks.model.goods.Backage;
import com.ks.model.goods.UserGoods;
import com.ks.model.soul.Soul;
import com.ks.model.user.User;
import com.ks.model.user.UserCap;
import com.ks.model.user.UserRule;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.soul.UserSoulInfoVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.protocol.vo.user.UserSoulTeamVO;
import com.ks.protocol.vo.user.UserSoulVO;
import com.ks.protocol.vo.user.UserTeamVO;

public class UserTeamServiceImpl extends BaseService implements UserTeamService {

	@Override
	public List<UserTeam> initUserTeam(User user, List<UserSoul> souls) {
		List<UserTeam> uts = userTeamDAO.getUserTeamCache(user.getUserId());
		if(uts.size()==0){
			uts = userTeamDAO.queryUserTeam(user.getUserId());
			userTeamDAO.addUserTeamCache(uts);
		}
		return uts;
	}

	@Override
	public void clearUserTeamCache(int userId) {
		List<UserTeam> uts = userTeamDAO.getUserTeamCache(userId);
		userTeamDAO.updateUserTeam(uts);
		for(UserTeam ut:uts){
			userTeamDAO.deleteUserTeamCache(ut);
		}
	}

	@Override
	public void initUserCap(User user, UserSoul userSoul) {
		UserCap cap = userTeamDAO.getUserCapCache(user.getUserId());
		if(cap==null){
			cap = new UserCap();
			cap.setUserId(user.getUserId());
			cap.setUserLevel(user.getLevel());
			cap.setPlayerName(user.getPlayerName());
			cap.setWant(new ArrayList<Integer>());
			cap.setCreateTime(new Date());
			updateUserCap(cap, userSoul,new ArrayList<Integer>());
		}else{
			cap.setUpdateTime(new Date());
			cap.setPlayerName(user.getPlayerName());
			cap.setUserLevel(user.getLevel());
			cap.setSoulId(userSoul.getSoulId());
			userTeamDAO.updateUserCapCache(cap);
		}
	}
	
	@Override
	public void updateUserCap(UserCap cap,UserSoul userSoul,List<Integer> equipments){
		cap.setUserSoulId(userSoul.getId());
		cap.setSoulId(userSoul.getSoulId());
		cap.setUg(getSoulUg(userSoul));
		cap.setExSkillCount(0);
		cap.setLevel(userSoul.getLevel());
		cap.setUpdateTime(new Date());
		cap.setEquipments(equipments);
		userTeamDAO.updateUserCapCache(cap);
	}
	
	private int getSoulUg(UserSoul userSoul) {
		return 0;
	}

	@Override
	public void updateUserTeam(int userId, List<UserTeamVO> teams,byte currTeamId) {
		//teamId:1
//        cap:5
//        pos:[0, 1187, 1972, 2007, 1186]
		List<UserTeam> uts = new ArrayList<>();
		Set<Long> ids = new HashSet<>();
		User user = userService.getExistUserCache(userId);
		if(teams.size()>7){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		if(currTeamId>7||currTeamId<1){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		for(UserTeamVO vo : teams){
			if(vo.getCap()==0||vo.getPos().get(vo.getCap()-1)==0){
				throw new GameException(GameException.CODE_没有队长, "");
			}
			if(vo.getTeamId()>7||vo.getTeamId()<1){
				throw new GameException(GameException.CODE_参数错误, "");
			}
			UserTeam ut = userTeamDAO.getUserTeamCache(userId, vo.getTeamId());
			uts.add(ut);
			for(long id : vo.getPos()){
				if(id!=0){
					ids.add(id);
				}
			}
			for(long id : ut.getPos()){
				if(id!=0){
					ids.add(id);
				}
			}
		}
		Map<Long, UserSoul> souls = new HashMap<Long, UserSoul>();
		List<UserSoul> list = userSoulDAO.getUserSoulsFromCache(userId, ids);
		for(UserSoul us : list){
			souls.put(us.getId(), us);
		}
		
		Backage backage = userGoodsService.getPackage(userId);
		//更新当前队伍
		if(currTeamId!=user.getCurrTeamId()){
			user.setCurrTeamId(currTeamId);
			Map<String, String> hash = new HashMap<>();
			hash.put("currTeamId", String.valueOf(currTeamId));
			userDAO.updateUserCache(userId, hash);
		}
		for(UserTeamVO team : teams){
			for(UserTeam ut : uts){
				if(ut.getTeamId()==team.getTeamId()){
					updateUserTeam(user,team, ut, souls,backage);
					if(ut.getTeamId()==currTeamId){
						UserCap cap = userTeamDAO.getUserCapCache(userId);
						UserSoul soul = souls.get(ut.getPos().get(ut.getCap()-1));
						List<UserGoods> eqs = backage.getSoulEquipments(soul);
						List<Integer> eq = new ArrayList<Integer>();
						for(UserGoods e : eqs){
							eq.add(e.getGoodsId());
						}
						updateUserCap(cap, soul,eq);
					}
					userTeamDAO.updateUserTeamCache(ut);
					break;
				}
			}
		}
		
		if (user.getGuideStep() == User.GUIDE_STEP7_编队指引) {
			userService.nextStep(user, user.getGuideStep() + 1);
		}
	}

	private void updateUserTeam(User user,UserTeamVO team,UserTeam ut,Map<Long, UserSoul> souls,Backage backage){
		for(long id : ut.getPos()){
			if(id!=0){
				UserSoul us = souls.get(id);
				us.setSoulSafe(us.getSoulSafe()&~UserSoul.getInTeamSafe(ut.getTeamId()));
				
				Map<String, String> hash = new HashMap<>();
				hash.put("soulSafe", String.valueOf(us.getSoulSafe()));
				userSoulDAO.updateUserSoulCache(us.getUserId(), us.getId(), hash);
			}
		}
		
		List<UserSoulInfoVO> ss = new ArrayList<>();
		for(long id : team.getPos()){
			if(id!=0){
				UserSoul us = souls.get(id);
				us.setSoulSafe(us.getSoulSafe()|UserSoul.getInTeamSafe(team.getTeamId()));
				
				Map<String, String> hash = new HashMap<>();
				hash.put("soulSafe", String.valueOf(us.getSoulSafe()));
				userSoulDAO.updateUserSoulCache(us.getUserId(), us.getId(), hash);
				
				ss.add(userSoulService.gainUserSoulInfo(us, backage.getUseGoodses().values()));
			}
		}
		
		checkUserTeam(user, ss);
		
		ut.setCap(team.getCap());
		ut.setPos(team.getPos());
		ut.setUpdateTime(new Date());
	}

	@Override
	public void checkUserTeam(User user, List<UserSoulInfoVO> souls) {
		int cost = 0;
		for(UserSoulInfoVO us : souls){
			Soul s = GameCache.getSoul(us.getUserSoul().getSoulId());
			cost +=s.getSoulCost();
		}
		UserRule rule = GameCache.getUserRule(user.getLevel());
		if(rule.getCost()<cost){
			throw new GameException(GameException.CODE_COST不足, "");
		}
	}

	@Override
	public UserTeam getExistUserTeamCache(int userId, byte teamId) {
		UserTeam team = userTeamDAO.getUserTeamCache(userId, teamId);
		if(team==null){
			throw new GameException(GameException.CODE_参数错误, "teamId : "+ teamId);
		}
		return team;
	}

	@Override
	public UserTeam getExistUserTeam(int userId, byte teamId) {
		UserTeam team = userTeamDAO.getUserTeamCache(userId, teamId);
		if(team==null){
			team = userTeamDAO.queryUserTeam(userId, teamId);
			if(team==null){
				throw new GameException(GameException.CODE_参数错误, "teamId : "+ teamId);
			}
		}
		return team;
	}

	@Override
    public UserSoulTeamVO findUserCurrentTeam(int userId) {
		User user = userService.getExistUser(userId);
		UserTeam team = getExistUserTeam(user.getUserId(), user.getCurrTeamId());
		
		List<UserSoul> soulList = new ArrayList<>();
		
		for(long userSoulId : team.getPos()) {
			if (userSoulId != 0) {
				soulList.add(userSoulService.getExistUserSoul(userId, userSoulId));
			}
		}
		UserSoulTeamVO vo = MessageFactory.getMessage(UserSoulTeamVO.class);
		vo.setUserTeam(createUserTeamVO(team));
		vo.setSoulList(createSoulVOList(soulList));
	    return vo;
    }
	
	public UserTeamVO createUserTeamVO(UserTeam team){
		UserTeamVO vo = MessageFactory.getMessage(UserTeamVO.class);
		vo.setCap(team.getCap());
		vo.setTeamId(team.getTeamId());
		vo.setPos(team.getPos());
		return vo;
	}
	
	public List<UserSoulVO> createSoulVOList(List<UserSoul> list){
		List<UserSoulVO> voList = new ArrayList<UserSoulVO>();
		for(UserSoul soul : list){
			UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
			vo.init(soul);
			voList.add(vo);
		}
		return voList;
	}

	@Override
    public UserCap findUserCapCache(int userId) {
	    return userTeamDAO.getUserCapCache(userId);
    }

	@Override
    public UserCapVO findUserCapVOCache(int userId) {
		UserCap uc = findUserCapCache(userId);
		if (uc != null) {
			UserCapVO vo = MessageFactory.getMessage(UserCapVO.class);
			vo.init(uc);
			return vo;
		}
	    return null;
    }
}
