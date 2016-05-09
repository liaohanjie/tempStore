package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.exceptions.GameException;
import com.ks.logic.dao.opt.SQLOpt;
import com.ks.logic.dao.opt.UserStatOpt;
import com.ks.model.user.UserStat;
import com.ks.util.JSONUtil;
/**
 * 用户统计DAO
 * @author ks
 */
public class UserStatDAO extends GameDAOTemplate {
	
	public static final String TABLE = "t_user_stat";
	
	public static final String SQL_UPDATE_PREFIX = "update " + TABLE + " set ";
	
	private static final RowMapper<UserStat> ROW_MAPPER = new RowMapper<UserStat>(){
		@Override
		public UserStat rowMapper(ResultSet rs) throws SQLException{
			UserStat obj = new UserStat();
			obj.setUserId(rs.getInt("user_id"));
			obj.setFriendlyPoint(rs.getInt("friendly_point"));
			obj.setUseFriend(JSONUtil.toObject(rs.getString("use_friend"),new TypeReference<List<Integer>>() {}));
			obj.setHandselFriend(JSONUtil.toObject(rs.getString("handsel_friend"), new TypeReference<List<Integer>>() {}));
			obj.setTotalLogin(rs.getInt("total_login"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			obj.setOnTimeLoginAward(rs.getString("ontime_login_award"));
			obj.setTotalLoginAward(rs.getString("total_login_award"));
			obj.setVipAwardTime(rs.getTimestamp("vip_award_time"));
			obj.setSweepCount(rs.getInt("sweep_count"));
			obj.setBuySweepCount(rs.getInt("buy_sweep_count"));
			obj.setChpaterJoinCount(rs.getInt("chapter_join_count"));
			obj.setGrowthGift(rs.getString("growth_gift"));
			obj.setOnTimeGiftTime(rs.getTimestamp("ontime_gift_time"));
			obj.setAcChageTotal(rs.getInt("ac_charge_total"));
			//obj.setSweepSoulNum(rs.getInt("sweep_soul_num"));
			obj.setRefreshMissionDate(rs.getTimestamp("refresh_mission_date"));
			obj.setWinFriend(JSONUtil.toObject(rs.getString("win_friend"),new TypeReference<List<Integer>>() {}));
			obj.setBuyStaminaCount(rs.getInt("buy_stamina_count"));
			obj.setSendStamainCount(rs.getInt("send_stamain_count"));
			obj.setBuyExploretionCount(rs.getInt("buy_exploretion_count"));
			obj.setContinuousLoginGiftMark(rs.getString("continuous_login_gift_mark"));
			obj.setLevelGiftMark(rs.getString("level_gift_mark"));
			obj.setProductDayMark(rs.getString("product_day_mark"));
			obj.setActivityRechargeCurrency(rs.getInt("activity_recharge_curreny"));
			obj.setCoinHandNum(rs.getInt("coin_hand_num"));
			//obj.setResurrectionNum(rs.getInt("resurrection_num"));
			obj.setCallSoulCurrencyNum(rs.getInt("call_soul_currency_num"));
			obj.setCallSoulFriendPointNum(rs.getInt("call_soul_friend_point_num"));
			obj.setDayTotalCurrency(rs.getInt("day_total_currency"));
			obj.setActivityContinuousRechargeCount(rs.getInt("activity_continuous_recharge_count"));
			obj.setLastRechargeTime(rs.getTimestamp("last_recharge_time"));
			obj.setActivityTotalCostCurrency(rs.getInt("activity_total_cost_currency"));
			obj.setOnlineGiftGetCount(rs.getInt("online_gift_get_count"));
			obj.setActivityContinuousRechargeGetMark(rs.getInt("activity_continuous_recharge_get_mark"));
			obj.setFreeCallSoulTime(rs.getTimestamp("free_call_soul_time"));
			obj.setMysteryShopFreshTime(rs.getTimestamp("mystery_last_fresh_time"));
			return obj;
		}
	};
	
	public UserStat queryUserStat(int userId){
		String sql = "select * from t_user_stat where user_id=?";
		return queryForEntity(sql, ROW_MAPPER, userId);
	}	
	public void updateUserStat(UserStatOpt opt,UserStat stat){
		StringBuilder sql = new StringBuilder("update t_user_stat set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if(opt.friendlyPoint!=0){
			sql.append(",friendly_point=?");
			args.add(stat.getFriendlyPoint());
		}
		if(opt.useFriend!=0){
			sql.append(",use_friend=?");
			args.add(JSONUtil.toJson(stat.getUseFriend()));
		}
		if(opt.handselFriend!=0){
			sql.append(",handsel_friend=?");
			args.add(JSONUtil.toJson(stat.getHandselFriend()));
		}
		if(opt.buyStaminaCount!=0){
			sql.append(",buy_stamina_count=?");
			args.add(stat.getBuyStaminaCount());
		}
		if(opt.onTimeLoginAward==SQLOpt.EQUAL){
			sql.append(",ontime_login_award=?");
			args.add(stat.getOnTimeLoginAward());
		}
		if(opt.totalLoginAward==SQLOpt.EQUAL){
			sql.append(",total_login_award=?");
			args.add(stat.getTotalLoginAward());
		}
		if(opt.totalLogin==SQLOpt.EQUAL){
			sql.append(",total_login=?");
			args.add(stat.getTotalLogin());
		}
		if(opt.vipAwardTime==SQLOpt.EQUAL){
			sql.append(",vip_award_time=?");
			args.add(stat.getVipAwardTime());
		}
		if(opt.sweepCount!=0){
			sql.append(",sweep_count=?");
			args.add(stat.getSweepCount());
		}
		if(opt.buySweepCount!=0){
			sql.append(",buy_sweep_count=?");
			args.add(stat.getBuySweepCount());
		}
		if(opt.chapterJoinCount==SQLOpt.EQUAL){
			sql.append(",chapter_join_count=?");
			args.add(stat.getChpaterJoinCount());
		}
		if(opt.growthGift==SQLOpt.EQUAL){
			sql.append(",growth_gift=?");
			args.add(stat.getGrowthGift());
		}
		if(opt.onTimeGiftTime==SQLOpt.EQUAL){
			sql.append(",ontime_gift_time=? ");
			args.add(stat.getOnTimeGiftTime());
		}
		if(opt.acChargeTotal==SQLOpt.PULS){
			sql.append(",ac_charge_total=ac_charge_total+?");
			args.add(stat.getAcChageTotal());
		}
		if(opt.refreshMissionDate==SQLOpt.EQUAL){
			sql.append(",refresh_mission_date=? ");
			args.add(stat.getRefreshMissionDate());
		}
		if(opt.winFriend==SQLOpt.EQUAL){
			sql.append(",win_friend=? ");
			args.add(JSONUtil.toJson(stat.getWinFriend()));
		}
		if(opt.sendStamainCount!=0){
			sql.append(",send_stamain_count=?");
			args.add(stat.getSendStamainCount());
		}
		if(opt.buyExploretionCount!=0){
			sql.append(",buy_exploretion_count=?");
			args.add(stat.getBuyExploretionCount());
		}
		if(opt.levelGiftMark != 0){
			sql.append(",level_gift_mark=?");
			args.add(stat.getLevelGiftMark());
		}
		if(opt.continuousLoginGiftMark!=0){
			sql.append(",continuous_login_gift_mark=?");
			args.add(stat.getContinuousLoginGiftMark());
		}
		if(opt.productDayMark!=0){
			sql.append(",product_day_mark=?");
			args.add(stat.getProductDayMark());
		}
		if(opt.activityRechargeCurrency != 0) {
			sql.append(",activity_recharge_curreny=?");
			args.add(stat.getActivityRechargeCurrency());
		}
		if(opt.coinHandNum != 0) {
			sql.append(",coin_hand_num=?");
			args.add(stat.getCoinHandNum());
		}
		/*if(opt.resurrectionNum != 0) {
			sql.append(",resurrection_num=?");
			args.add(stat.getResurrectionNum());
		}*/
		if(opt.callSoulCurrencyNum != 0) {
			sql.append(",call_soul_currency_num=?");
			args.add(stat.getCallSoulCurrencyNum());
		}
		if(opt.callSoulFriendPointNum != 0) {
			sql.append(",call_soul_friend_point_num=?");
			args.add(stat.getCallSoulFriendPointNum());
		}
		if(opt.dayTotalCurrency != 0) {
			sql.append(",day_total_currency=?");
			args.add(stat.getDayTotalCurrency());
		}
		if(opt.activityContinuousRechargeCount != 0) {
			sql.append(",activity_continuous_recharge_count=?");
			args.add(stat.getActivityContinuousRechargeCount());
		}
		if(opt.lastRechargeTime != 0) {
			sql.append(",last_recharge_time=?");
			args.add(stat.getLastRechargeTime());
		}
		if(opt.activityTotalCostCurrency != 0) {
			sql.append(",activity_total_cost_currency=?");
			args.add(stat.getActivityTotalCostCurrency());
		}
		if(opt.onlineGiftGetCount != 0) {
			sql.append(",online_gift_get_count=?");
			args.add(stat.getOnlineGiftGetCount());
		}
		if(opt.activityContinuousRechargeGetMark != 0) {
			sql.append(",activity_continuous_recharge_get_mark=?");
			args.add(stat.getActivityContinuousRechargeGetMark());
		}
		if(opt.freeCallSoulTime != 0) {
			sql.append(",free_call_soul_time=?");
			args.add(stat.getFreeCallSoulTime());
		}
		if(opt.mysteryShopFreshTime != 0) {
			sql.append(",mystery_last_fresh_time=?");
			args.add(stat.getMysteryShopFreshTime());
		}
		sql.append(" where user_id=?");
		args.add(stat.getUserId());
		saveOrUpdate(sql.toString(), args.toArray(new Object[args.size()]));
	}
	
	public void addUserStat(UserStat stat){
		String sql = "insert into t_user_stat(user_id,friendly_point,use_friend,handsel_friend,win_friend,create_time,update_time) values(?,?,?,?,?,now(),now())";
		saveOrUpdate(sql, stat.getUserId(),stat.getFriendlyPoint(),JSONUtil.toJson(stat.getUseFriend()),JSONUtil.toJson(stat.getHandselFriend()),JSONUtil.toJson(stat.getHandselFriend()));
	}

	/**每天重置*/
	public void reset(int userId){
		String sql = "update t_user_stat set use_friend='[]'"
				+ ",handsel_friend='[]'"
				+ ",win_friend='[]'"
				+ ",buy_stamina_count=0"
				+ ",sweep_count=0"
				+ ",buy_sweep_count=0"
				+ ",chapter_join_count=0"
				+ ",send_stamain_count=0"
				+ ",buy_exploretion_count=0"
				+ ",product_day_mark=''"
				+ ",coin_hand_num=0"
				+ ",day_total_currency=0"
				+ ",online_gift_get_count=0"
				+ " where user_id=?";
		saveOrUpdate(sql, userId);
	}
	
	public void resetAcChargeCoin(){
		String sql = "update IGNORE t_user_stat set ac_charge_total=0";
		saveOrUpdate(sql);
	}
	
	/**
	 * 连续登陆活动充值
	 */
	public void resetActivityContinuousLogin(){
		String sql = "update t_user_stat set continuous_login_gift_mark='0'";
		saveOrUpdate(sql);
		String sql2 = "update t_user set uninterrupted_login_count=0";
		saveOrUpdate(sql2);
	}
	
	/**
	 * 重置限时活动累计充值魂钻
	 */
	public void resetActivityRechargeCurrency(){
		String sql = "update IGNORE t_user_stat set activity_recharge_curreny=0";
		saveOrUpdate(sql);
	}
	
	/***
	 * 累计消费魂钻送礼活动重置
	 */
	public void resetActivityTotalCostCurrency(){
		String sql = SQL_UPDATE_PREFIX + " activity_total_cost_currency=0";
		saveOrUpdate(sql);
	}
	
	/***
	 * 每日充值送豪礼活动重置
	 */
	public void resetActivityContinuousRechargeCount(){
		String sql = SQL_UPDATE_PREFIX + " activity_continuous_recharge_count=0, activity_continuous_recharge_get_mark=0";
		saveOrUpdate(sql);
	}
	
	/**
	 * 每日充值送豪礼，中断连续充值处理
	 */
	public void zeroResetActivityContinuousRechargeCount() {
		String sql = SQL_UPDATE_PREFIX + " activity_continuous_recharge_count=0, activity_continuous_recharge_get_mark=0 where where DATEDIFF(now(),last_recharge_time) > 1";
		saveOrUpdate(sql);
	}
}
