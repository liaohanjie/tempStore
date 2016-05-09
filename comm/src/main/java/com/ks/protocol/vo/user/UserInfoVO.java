package com.ks.protocol.vo.user;

import java.util.ArrayList;
import java.util.List;

import com.ks.model.activity.OnTimeLoginGift;
import com.ks.model.activity.TotalLoginGift;
import com.ks.model.goods.FightProp;
import com.ks.model.goods.UserGoods;
import com.ks.model.user.User;
import com.ks.model.user.UserSoul;
import com.ks.model.user.UserTeam;
import com.ks.protocol.Message;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.activity.LoginGiftVO;
import com.ks.protocol.vo.goods.FightPropVO;
import com.ks.protocol.vo.goods.UserGoodsVO;
/**
 * 用户信息
 * @author ks
 */
public class UserInfoVO extends Message {
	
	private static final long serialVersionUID = 1L;
	/**用户*/
	private UserVO user;
	/**用户武将*/
	private List<UserSoulVO> userSoul;
	/**用户队伍*/
	private List<UserTeamVO> userTeam;
	/**用户物品*/
	private List<UserGoodsVO> goodses;
	/**战斗道具*/
	private List<FightPropVO> fightProps;
	/**累计登录*/
	private List<LoginGiftVO> totalLogin;
	/**指定时间登录*/
	private List<LoginGiftVO> onTimeLogin;
	/**今天是否第一次登录*/
	private boolean todayfisrtLogin;
	/**PVP竞技点*/
	private int athleticsPoint;
	/**PVP回点时间*/
	private long backAthleticsPointTime;
	/**PVP竞技称号*/
	private int athleticName;
	public boolean isTodayfisrtLogin() {
		return todayfisrtLogin;
	}
	public void setTodayfisrtLogin(boolean todayfisrtLogin) {
		this.todayfisrtLogin = todayfisrtLogin;
	}

	public List<LoginGiftVO> getTotalLogin() {
		return totalLogin;
	}

	public void setTotalLogin(List<LoginGiftVO> totalLogin) {
		this.totalLogin = totalLogin;
	}

	public List<LoginGiftVO> getOnTimeLogin() {
		return onTimeLogin;
	}

	public void setOnTimeLogin(List<LoginGiftVO> onTimeLogin) {
		this.onTimeLogin = onTimeLogin;
	}

	public void init(User u,List<UserSoul> uses,
			List<UserTeam> uts,
			List<UserGoods> ugs,
			List<FightProp> fps,
			List<TotalLoginGift> tlgs,
			List<OnTimeLoginGift> otlgs,List<Integer> want,int point,long date,int name){
		user = MessageFactory.getMessage(UserVO.class);
		user.init(u,want);
		userSoul = new ArrayList<UserSoulVO>();
		for(UserSoul us : uses){
			UserSoulVO vo = MessageFactory.getMessage(UserSoulVO.class);
			vo.init(us);
			userSoul.add(vo);
		}
		userTeam = new ArrayList<UserTeamVO>();
		for(UserTeam ut : uts){
			UserTeamVO vo = MessageFactory.getMessage(UserTeamVO.class);
			vo.init(ut);
			userTeam.add(vo);
		}
		goodses = new ArrayList<UserGoodsVO>();
		for(UserGoods ug : ugs){
			UserGoodsVO vo = MessageFactory.getMessage(UserGoodsVO.class);
			vo.init(ug);
			goodses.add(vo);
		}
		
		fightProps = new ArrayList<FightPropVO>();
		for(FightProp fp : fps){
			FightPropVO vo = MessageFactory.getMessage(FightPropVO.class);
			vo.init(fp);
			fightProps.add(vo);
		}		
		totalLogin=new ArrayList<>();
		for(TotalLoginGift gift:tlgs){
			LoginGiftVO vo = MessageFactory.getMessage(LoginGiftVO.class);
			vo.init(gift.getDay(),gift.getGoodsType(),gift.getAssId(),gift.getNum(),gift.getGoodsLevel());
			totalLogin.add(vo);
		}
		
		onTimeLogin=new ArrayList<>();
		for(OnTimeLoginGift gift:otlgs){
			LoginGiftVO vo = MessageFactory.getMessage(LoginGiftVO.class);
			vo.init(gift.getDay(),gift.getGoodsType(),gift.getAssId(),gift.getNum(),gift.getGoodsLevel());
			onTimeLogin.add(vo);
		}
		this.athleticsPoint=point;
		this.backAthleticsPointTime=date;
		this.athleticName=name;
	}
	
	public List<UserSoulVO> getUserSoul() {
		return userSoul;
	}
	public void setUserSoul(List<UserSoulVO> userSoul) {
		this.userSoul = userSoul;
	}
	public UserVO getUser() {
		return user;
	}
	public void setUser(UserVO user) {
		this.user = user;
	}
	public List<UserTeamVO> getUserTeam() {
		return userTeam;
	}
	public void setUserTeam(List<UserTeamVO> userTeam) {
		this.userTeam = userTeam;
	}
	public List<UserGoodsVO> getGoodses() {
		return goodses;
	}
	public void setGoodses(List<UserGoodsVO> goodses) {
		this.goodses = goodses;
	}
	public List<FightPropVO> getFightProps() {
		return fightProps;
	}
	public void setFightProps(List<FightPropVO> fightProps) {
		this.fightProps = fightProps;
	}
	public int getAthleticsPoint() {
		return athleticsPoint;
	}
	public void setAthleticsPoint(int athleticsPoint) {
		this.athleticsPoint = athleticsPoint;
	}
	public long getBackAthleticsPointTime() {
		return backAthleticsPointTime;
	}
	public void setBackAthleticsPointTime(long backAthleticsPointTime) {
		this.backAthleticsPointTime = backAthleticsPointTime;
	}
	public int getAthleticName() {
		return athleticName;
	}
	public void setAthleticName(int athleticName) {
		this.athleticName = athleticName;
	}
	
}
