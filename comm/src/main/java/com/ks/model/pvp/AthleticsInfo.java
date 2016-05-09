package com.ks.model.pvp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AthleticsInfo implements Serializable {
	public static int ASC_高于自己积分的前10名=1;
	public static int ASC_低于自己积分的前10名=2;
	public static int ATHLETICS_POINT_初始的竞技点数=3;
	public static int ATHLETICS_INTEGRAL_加减分数=200;
	public static int ATHLETICS_MAX_INTEGRAL=10000000;
	public static int ATHLETICS_MIN_INTEGRAL=500;
	public static int ATHLETICS_勇敢的旅者=9080001;
	public static int ATHLETICS_魂徒_一阶=9080002;
	public static int ATHLETICS_魂徒_二阶=9080003;
	public static int ATHLETICS_魂徒_三阶=9080004;
	public static int ATHLETICS_魂士_一阶=9080005;
	public static int ATHLETICS_魂士_二阶=9080006;
	public static int ATHLETICS_魂士_三阶=9080007;
	public static int ATHLETICS_魂修士_一阶=9080008;
	public static int ATHLETICS_魂修士_二阶=9080009;
	public static int ATHLETICS_魂修士_三阶=9080010;
	public static int ATHLETICS_召唤师_一阶=9080011;
	public static int ATHLETICS_召唤师_二阶=9080012;
	public static int ATHLETICS_召唤师_三阶=9080013;
	public static int ATHLETICS_大召唤师_一阶=9080014;
	public static int ATHLETICS_大召唤师_二阶=9080015;
	public static int ATHLETICS_大召唤师_三阶=9080016;
	public static int ATHLETICS_控魂使_一阶=9080017;
	public static int ATHLETICS_控魂使_二阶=9080018;
	public static int ATHLETICS_控魂使_三阶=9080019;
	public static int ATHLETICS_幻魂师_一阶=9080020;
	public static int ATHLETICS_幻魂师_二阶=9080021;
	public static int ATHLETICS_幻魂师_三阶=9080022;
	public static int ATHLETICS_魂引师_一阶=9080023;
	public static int ATHLETICS_魂引师_二阶=9080024;
	public static int ATHLETICS_魂引师_三阶=9080025;
	public static int ATHLETICS_魂帝_一阶=9080026;
	public static int ATHLETICS_魂帝_二阶=9080027;
	public static int ATHLETICS_魂帝_三阶=9080028;
	public static int ATHLETICS_通魂者_一阶=9080029;
	public static int ATHLETICS_通魂者_二阶=9080030;
	public static int ATHLETICS_通魂者_三阶=9080031;
	/**恢复竞技时间*/
	public static final long REGAIN_STAMINA_TIME = 60*60*1000L;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/***/
	private int userId;
	/**竞技点*/
	private int athleticsPoint;
	/**总积分*/
	private int totalIntegral;
	/** 胜场*/
	private int wins;
	/**输的场次*/
	private int lose;
	/**当前连胜场次*/
	private int streakWin;
	/**最长连胜场次*/
	private int highestWinStreak;
	/**已领将称号*/
	private List<Integer> awardTitle;
	/**上次回竞技点时间*/
	private Date lastBackTime;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date updateTime;
	public int getAthleticsPoint() {
		return athleticsPoint;
	}
	public void setAthleticsPoint(int athleticsPoint) {
		this.athleticsPoint = athleticsPoint;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getUserId(){
		 return userId;
	}
	public void setUserId( int userId){
		 this.userId = userId;
	}
	public int getTotalIntegral(){
		 return totalIntegral;
	}
	public void setTotalIntegral( int totalIntegral){
		 this.totalIntegral = totalIntegral;
	}
	public int getWins(){
		 return wins;
	}
	public void setWins( int wins){
		 this.wins = wins;
	}
	public int getLose(){
		 return lose;
	}
	public void setLose( int lose){
		 this.lose = lose;
	}
	public int getStreakWin(){
		 return streakWin;
	}
	public void setStreakWin( int streakWin){
		 this.streakWin = streakWin;
	}
	public Date getLastBackTime(){
		 return lastBackTime;
	}
	public void setLastBackTime( Date lastBackTime){
		 this.lastBackTime = lastBackTime;
	}
	public List<Integer> getAwardTitle() {
		return awardTitle;
	}
	public void setAwardTitle(List<Integer> awardTitle) {
		this.awardTitle = awardTitle;
	}
	public int getHighestWinStreak() {
		return highestWinStreak;
	}
	public void setHighestWinStreak(int highestWinStreak) {
		this.highestWinStreak = highestWinStreak;
	}
	public static int getAthleticsName(int integral){
		int AthleticsNameId=0;
		if(integral<100){
			AthleticsNameId=ATHLETICS_勇敢的旅者;
		}else if(integral<500){
			AthleticsNameId=ATHLETICS_魂徒_一阶;
		}else if(integral<1000){
			AthleticsNameId=ATHLETICS_魂徒_二阶;
		}else if(integral<3000){
			AthleticsNameId=ATHLETICS_魂徒_三阶;
		}else if(integral<5000){
			AthleticsNameId=ATHLETICS_魂士_一阶;
		}else if(integral<8000){
			AthleticsNameId=ATHLETICS_魂士_二阶;
		}else if(integral<12000){
			AthleticsNameId=ATHLETICS_魂士_三阶;
		}else if(integral<16000){
			AthleticsNameId=ATHLETICS_魂修士_一阶;
		}else if(integral<20000){
			AthleticsNameId=ATHLETICS_魂修士_二阶;
		}else if(integral<25000){
			AthleticsNameId=ATHLETICS_魂修士_三阶;
		}else if(integral<30000){
			AthleticsNameId=ATHLETICS_召唤师_一阶;
		}else if(integral<36000){
			AthleticsNameId=ATHLETICS_召唤师_二阶;
		}else if(integral<42000){
			AthleticsNameId=ATHLETICS_召唤师_三阶;
		}else if(integral<49000){
			AthleticsNameId=ATHLETICS_大召唤师_一阶;
		}else if(integral<56000){
			AthleticsNameId=ATHLETICS_大召唤师_二阶;
		}else if(integral<64000){
			AthleticsNameId=ATHLETICS_大召唤师_三阶;
		}else if(integral<=72000){
			AthleticsNameId=ATHLETICS_控魂使_一阶;
		}else if(integral<81000){
			AthleticsNameId=ATHLETICS_控魂使_二阶;
		}else if(integral<90000){
			AthleticsNameId=ATHLETICS_控魂使_三阶;
		}else if(integral<100000){
			AthleticsNameId=ATHLETICS_幻魂师_一阶;
		}else if(integral<110000){
			AthleticsNameId=ATHLETICS_幻魂师_二阶;
		}else if(integral<121000){
			AthleticsNameId=ATHLETICS_幻魂师_三阶;
		}else if(integral<132000){
			AthleticsNameId=ATHLETICS_魂引师_一阶;
		}else if(integral<144000){
			AthleticsNameId=ATHLETICS_魂引师_二阶;
		}else if(integral<156000){
			AthleticsNameId=ATHLETICS_魂引师_三阶;
		}else if(integral<169000){
			AthleticsNameId=ATHLETICS_魂帝_一阶;
		}else if(integral<182000){
			AthleticsNameId=ATHLETICS_魂帝_二阶;
		}else if(integral<196000){
			AthleticsNameId=ATHLETICS_魂帝_三阶;
		}else if(integral<210000){
			AthleticsNameId=ATHLETICS_通魂者_一阶;
		}else if(integral<225000){
			AthleticsNameId=ATHLETICS_通魂者_二阶;
		}else if(integral>=225000){
			AthleticsNameId=ATHLETICS_通魂者_三阶;
		}
		return AthleticsNameId;
	}
	
	public static String getAthleticsNameById(int id){
		Map<String,String> map=new HashMap<>();
		map.put("9080001", "勇敢的旅者");
		map.put("9080002", "英勇魂徒Ⅰ");
		map.put("9080003", "英勇魂徒Ⅱ");
		map.put("9080004", "英勇魂徒Ⅲ");
		map.put("9080005", "不屈魂士Ⅰ");
		map.put("9080006", "不屈魂士Ⅱ");
		map.put("9080007", "不屈魂士Ⅲ");
		map.put("9080008", "野蛮魂修士Ⅰ");
		map.put("9080009", "野蛮魂修士Ⅱ");
		map.put("9080010", "野蛮魂修士Ⅲ");
		map.put("9080011", "无畏召唤师Ⅰ");
		map.put("9080012", "无畏召唤师Ⅱ");
		map.put("9080013", "无畏召唤师Ⅲ");
		map.put("9080014", "荣耀大召唤师Ⅰ");
		map.put("9080015", "荣耀大召唤师Ⅱ");
		map.put("9080016", "荣耀大召唤师Ⅲ");
		map.put("9080017", "骄矜控魂使Ⅰ");
		map.put("9080018", "骄矜控魂使Ⅱ");
		map.put("9080019", "骄矜控魂使Ⅲ");
		map.put("9080020", "恶孽幻魂师Ⅰ");
		map.put("9080021", "恶孽幻魂师Ⅱ");
		map.put("9080022", "恶孽幻魂师Ⅲ");
		map.put("9080023", "狂魔魂引师Ⅰ");
		map.put("9080024", "狂魔魂引师Ⅱ");
		map.put("9080025", "狂魔魂引师Ⅲ");
		map.put("9080026", "璀璨魂帝Ⅰ");
		map.put("9080027", "璀璨魂帝Ⅱ");
		map.put("9080028", "璀璨魂帝Ⅲ");
		map.put("9080029", "最强通魂者Ⅰ");
		map.put("9080030", "最强通魂者Ⅱ");
		map.put("9080031", "最强通魂者Ⅲ");
		return map.get(id+"");
	}

}
