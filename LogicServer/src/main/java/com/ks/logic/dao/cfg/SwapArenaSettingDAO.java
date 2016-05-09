package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.goods.Goods;
import com.ks.model.robot.Robot;
import com.ks.model.robot.TeamTemplate;
import com.ks.model.swaparena.SwapArenaBuySetting;
import com.ks.model.swaparena.SwapArenaRewardSetting;
import com.ks.util.JSONUtil;
/**
 * 机器dao
 * 
 * @author hanjie.l
 * 
 */
public class SwapArenaSettingDAO extends GameCfgDAOTemplate {

	private static final String ROBOT_TABLE = "t_robot";

	private static final String TEAM_TEMPLATE_TABLE = "t_team_template";
	
	private static final String RANK_REWARD_TABLE = "t_swaparena_reward_setting";
	
	private static final String BUY_TABLE = "t_swaparena_buy_setting";

	private static final RowMapper<Robot> ROBOT_ROW_MAPPER = new RowMapper<Robot>() {
		@Override
		public Robot rowMapper(ResultSet rs) throws SQLException {
			Robot robot = new Robot();
			robot.setId(rs.getInt("id"));
			robot.setPlayerName(rs.getString("playerName"));
			robot.setLevel(rs.getInt("level"));
			robot.setTemplateId(rs.getInt("templateId"));
			return robot;
		}
	};

	private static final RowMapper<TeamTemplate> TEAM_TEMPLATE_ROW_MAPPER = new RowMapper<TeamTemplate>() {
		@Override
		public TeamTemplate rowMapper(ResultSet rs) throws SQLException {
			TeamTemplate teamTemplate = new TeamTemplate();
			teamTemplate.setTemplateId(rs.getInt("templateId"));
			teamTemplate.setSoulId1(rs.getInt("soulId1"));
			teamTemplate.setLevel1(rs.getInt("level1"));
			teamTemplate.setSoulId2(rs.getInt("soulId2"));
			teamTemplate.setLevel2(rs.getInt("level2"));
			teamTemplate.setSoulId3(rs.getInt("soulId3"));
			teamTemplate.setLevel3(rs.getInt("level3"));
			teamTemplate.setSoulId4(rs.getInt("soulId4"));
			teamTemplate.setLevel4(rs.getInt("level4"));
			teamTemplate.setSoulId5(rs.getInt("soulId5"));
			teamTemplate.setLevel5(rs.getInt("level5"));
			return teamTemplate;
		}
	};
	
	private static final RowMapper<SwapArenaRewardSetting> REWARD_ROW_MAPPER = new RowMapper<SwapArenaRewardSetting>() {
		@Override
		public SwapArenaRewardSetting rowMapper(ResultSet rs) throws SQLException {
			SwapArenaRewardSetting reward = new SwapArenaRewardSetting();
			reward.setRank(rs.getInt("rank"));
			reward.setRewards(JSONUtil.toObject(rs.getString("rewards"), new TypeReference<List<Goods>>() {}));
			return reward;
		}
	};
	
	
	private static final RowMapper<SwapArenaBuySetting> BUY_ROW_MAPPER = new RowMapper<SwapArenaBuySetting>() {
		@Override
		public SwapArenaBuySetting rowMapper(ResultSet rs) throws SQLException {
			SwapArenaBuySetting buy = new SwapArenaBuySetting();
			buy.setTimes(rs.getInt("times"));
			buy.setCost(rs.getInt("cost"));
			return buy;
		}
	};

	public List<Robot> queryAllRobot() {
		return queryForList("select * from " + ROBOT_TABLE + " order by id", ROBOT_ROW_MAPPER);
	}
	
	public List<TeamTemplate> queryAllTeamTemplate() {
		return queryForList("select * from " + TEAM_TEMPLATE_TABLE, TEAM_TEMPLATE_ROW_MAPPER);
	}
	
	public List<SwapArenaRewardSetting> queryAllRankReward() {
		return queryForList("select * from " + RANK_REWARD_TABLE, REWARD_ROW_MAPPER);
	}
	
	public List<SwapArenaBuySetting> queryAllBuyCountSetting() {
		return queryForList("select * from " + BUY_TABLE, BUY_ROW_MAPPER);
	}

}
