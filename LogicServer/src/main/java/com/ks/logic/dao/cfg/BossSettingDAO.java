package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.boss.BossOpenSetting;
import com.ks.model.boss.BossSetting;
import com.ks.model.boss.BossrankRewardSetting;
import com.ks.model.goods.Goods;
import com.ks.util.JSONUtil;
/**
 * boss配置数据dao
 * @author hanjie.l
 *
 */
public class BossSettingDAO extends GameCfgDAOTemplate{
	
	private static final RowMapper<BossSetting> BOSS_SETTING_ROW_MAPPER = new RowMapper<BossSetting>(){
		@Override
		public BossSetting rowMapper(ResultSet rs) throws SQLException{
			BossSetting obj = new BossSetting();
			obj.setBossId(rs.getInt("boss_id"));
			obj.setLevel(rs.getInt("level"));
			obj.setMap(rs.getString("map"));
			obj.setMonsters(rs.getString("monsters"));
			obj.setBlood(rs.getLong("blood"));
			obj.setHurt(rs.getLong("hurt"));
			obj.setKillReward(JSONUtil.toObject(rs.getString("kill_reward"), new TypeReference<List<Goods>>() {}));
			obj.setHurtReward(JSONUtil.toObject(rs.getString("hurt_reward"), new TypeReference<List<Goods>>() {}));
			return obj;
		}
	};
	
	private static final RowMapper<BossOpenSetting> BOSSOPEN_SETTING_ROW_MAPPER = new RowMapper<BossOpenSetting>(){
		@Override
		public BossOpenSetting rowMapper(ResultSet rs) throws SQLException{
			BossOpenSetting obj = new BossOpenSetting();
			obj.setBossId(rs.getInt("boss_id"));
			obj.setBeginEndTimes(rs.getString("begin_end_times"));
			obj.setCostGold(rs.getInt("cost_gold"));
			obj.setGoldAdd(rs.getInt("gold_add"));
			obj.setCostDiamond(rs.getInt("cost_diamond"));
			obj.setDiamondAdd(rs.getInt("diamond_add"));
			obj.setUplimit(rs.getInt("up_limit"));
			obj.setJoinLevel(rs.getInt("join_level"));
			obj.setJoinRewards(JSONUtil.toObject(rs.getString("join_rewards"), new TypeReference<List<Goods>>() {}));
			return obj;
		}
	};

	private static final RowMapper<BossrankRewardSetting> BOSSRANK_REWARD_SETTING_ROW_MAPPER = new RowMapper<BossrankRewardSetting>(){
		@Override
		public BossrankRewardSetting rowMapper(ResultSet rs) throws SQLException{
			BossrankRewardSetting obj = new BossrankRewardSetting();
			obj.setBossId(rs.getInt("boss_id"));
			obj.setRank(rs.getInt("rank"));
			obj.setRewards(JSONUtil.toObject(rs.getString("rewards"), new TypeReference<List<Goods>>() {}));
			return obj;
		}
	};

	
	public List<BossSetting> getAllBossSetting(){
		String sql = "select * from t_boss_setting";
		return queryForList(sql, BOSS_SETTING_ROW_MAPPER);
	}
	
	public List<BossOpenSetting> getAllBossOpenSetting(){
		String sql = "select * from t_bossopen_setting";
		return queryForList(sql, BOSSOPEN_SETTING_ROW_MAPPER);
	}
	
	public List<BossrankRewardSetting> getAllBossRankRewardSetting(){
		String sql = "select * from t_bossrank_reward_setting";
		return queryForList(sql, BOSSRANK_REWARD_SETTING_ROW_MAPPER);
	}

}
