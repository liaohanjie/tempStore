package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.access.mapper.impl.RowMapperImpl;
import com.ks.model.user.GrowthFundRule;
import com.ks.model.user.UserRule;
import com.ks.model.vip.VipPrivilege;
import com.ks.model.vip.VipWeekAward;

/**
 * 用户数据操作
 * 
 * @author ks
 * 
 */
public class UserCfgDAO extends GameCfgDAOTemplate {


	private static RowMapper<VipWeekAward> VIP_WEEK_AWARD_MAPPER = new RowMapper<VipWeekAward>() {
		@Override
		public VipWeekAward rowMapper(ResultSet rs) throws SQLException {
			VipWeekAward pojo = new VipWeekAward();
			pojo.setVipGrade(rs.getInt("vip_grade"));
			pojo.setType(rs.getInt("type"));
			pojo.setAssId(rs.getInt("ass_id"));
			pojo.setNum(rs.getInt("num"));
			pojo.setLevel(rs.getInt("level"));
			return pojo;
		}
	};
	private static RowMapper<GrowthFundRule> GROWTH_FUND_RULE = new RowMapper<GrowthFundRule>() {
		@Override
		public GrowthFundRule rowMapper(ResultSet rs) throws SQLException {
			GrowthFundRule gwfr = new GrowthFundRule();
			gwfr.setGrade(rs.getInt("grade"));
			gwfr.setCurrency(rs.getInt("currency"));
			return gwfr;
		}
	};

	private static RowMapper<VipPrivilege> VIP_PRIVILEGE_MAPPER = new RowMapper<VipPrivilege>() {
		@Override
		public VipPrivilege rowMapper(ResultSet rs) throws SQLException {
			VipPrivilege pojo = new VipPrivilege();
			pojo.setVipGrade(rs.getInt("vip_grade"));
			pojo.setTotalCurrency(rs.getInt("total_currency"));
			pojo.setEverydaySweepCount(rs.getInt("everyday_sweep_count"));
			pojo.setBuySweepCount(rs.getInt("buy_sweep_count"));
			pojo.setAddStamina(rs.getInt("add_stamina"));
			pojo.setStoreItemGrade(rs.getInt("store_item_grade"));
			pojo.setProperty(rs.getInt("property"));
			pojo.setAddBuyStaminaCount(rs.getInt("add_buy_stamina_count"));
			pojo.setExtraStrengthenExp(rs.getInt("extra_strengthen_exp"));
			pojo.setReduceExploreTime(rs.getDouble("reduce_explore_time"));
			pojo.setExtraSoulCapacity(rs.getInt("extra_soul_capacity"));
			pojo.setCoinHandNum(rs.getInt("coin_hand_num"));
			return pojo;
		}
	};
	
	public List<UserRule> queryUserRules() {
		return queryForList("select * from t_user_rule", new RowMapperImpl<>(UserRule.class));
	}
	

	public List<VipPrivilege> queryListVipPrivilege() {
		String sql = "select * from t_vip_privilege order by total_currency asc";
		return queryForList(sql, VIP_PRIVILEGE_MAPPER);
	}

	public List<GrowthFundRule> queryGrowthFundRule() {
		String sql = "select * from t_growth_fund_rule";
		return queryForList(sql, GROWTH_FUND_RULE);
	}

	public List<VipWeekAward> queryListVipWeekAward() {
		String sql = "select * from t_vip_week_award";
		return queryForList(sql, VIP_WEEK_AWARD_MAPPER);
	}
}
