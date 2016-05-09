package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.access.mapper.impl.RowMapperImpl;
import com.ks.model.filter.SoulFilter;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SkillStrike;
import com.ks.model.soul.CallSoulRule;
import com.ks.model.soul.Soul;
import com.ks.model.soul.SoulEvolution;

/**
 * 战魂dao
 * 
 * @author ks
 */
public class SoulCfgDAO extends GameCfgDAOTemplate {


	private static final RowMapper<SoulEvolution> ROW_MAPPER = new RowMapper<SoulEvolution>() {
		@Override
		public SoulEvolution rowMapper(ResultSet rs) throws SQLException {
			SoulEvolution obj = new SoulEvolution();
			obj.setBassSoul(rs.getInt("bass_soul"));
			obj.setEvo(rs.getInt("evo"));
			obj.setTargetSoul(rs.getInt("target_soul"));
			obj.setSouls(getSouls(rs.getString("souls")));
			obj.setGold(rs.getInt("gold"));
			obj.setScrollPropId(rs.getInt("scroll_prop_id"));
			return obj;
		}

		private List<Integer> getSouls(String str) {
			str = str.replaceAll("，", ",");
			String[] strs = str.split(",");
			List<Integer> list = new ArrayList<Integer>();
			for (String s : strs) {
				int x = Integer.parseInt(s);
				if (x != 0) {
					list.add(x);
				}
			}
			return list;
		}
	};
	
	private static final RowMapper<Soul> SOUL_ROW_MAPPER = new RowMapper<Soul>() {
		@Override
		public Soul rowMapper(ResultSet rs) throws SQLException {
			Soul obj = new Soul();
			obj.setSoulId(rs.getInt("soul_id"));
			obj.setName(rs.getString("name"));
			obj.setSoulRare(rs.getInt("soul_rare"));
			obj.setSoulEle(rs.getInt("soul_ele"));
			obj.setSkill(rs.getInt("skill"));
			obj.setCapSkill(rs.getInt("cap_skill"));
			obj.setHp(rs.getInt("hp"));
			obj.setAtk(rs.getInt("atk"));
			obj.setDef(rs.getInt("def"));
			obj.setRep(rs.getInt("rep"));
			obj.setAttackHit(rs.getInt("attack_hit"));
			obj.setLvMax(rs.getInt("lv_max"));
			obj.setLvMode(rs.getInt("lv_mode"));
			obj.setSoulCost(rs.getInt("soul_cost"));
			obj.setGiveExp(rs.getInt("give_exp"));
			obj.setSellGold(rs.getInt("sell_gold"));
			obj.setSkillHit(rs.getInt("skill_hit"));
			obj.setSeries(rs.getInt("series"));
			obj.setGrowthFactor(rs.getDouble("growth_factor"));
			obj.setMarquee(rs.getInt("marquee"));
			obj.setAddDevote(rs.getInt("addDevote"));
			return obj;
		}
	};
	
	public List<Soul> findAllSoul() {
		return queryForList("select * from t_soul", SOUL_ROW_MAPPER);
	}

	public List<Soul> getSouls(SoulFilter soul) {
		StringBuffer sql = new StringBuffer("select * from t_soul where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		if (soul.getSoulId() != null) {
			   sql.append(" and soul_id like concat('%',?,'%')");
			val.add(soul.getSoulId()); 
		}
		if (soul.getLvMax() != null) {
			sql.append(" and lv_max = ? ");
			val.add(soul.getLvMax());
		}
		if (soul.getGiveExp() != null) {
			sql.append(" and  give_exp = ? ");
			val.add(soul.getGiveExp());
		}
		sql.append(" limit "+soul.getStart()+","+soul.getPageSize());
		return queryForList(sql.toString(), SOUL_ROW_MAPPER, val.toArray());

	}

	public List<CapSkillEffect> queryCapSkillEffects() {
		return queryForList("select * from t_cap_skill_effect",
				new RowMapperImpl<CapSkillEffect>(CapSkillEffect.class));
	}

	public List<SoulEvolution> querySoulEvolution() {
		return queryForList("select * from t_soul_evolution", ROW_MAPPER);
	}
	
	public Integer getSoulsCount(SoulFilter soul) {
		StringBuffer sql = new StringBuffer("select count(1) from t_soul where 1=1 ");
		List<Object> val = new ArrayList<Object>();
		if (soul.getSoulId() != null) {
			sql.append(" and  soul_id  like concat('%',?,'%') ");
			val.add(soul.getSoulId());
		}
		if (soul.getLvMax() != null) {
			sql.append(" and lv_max = ? ");
			val.add(soul.getLvMax());
		}
		if (soul.getGiveExp() != null) {
			sql.append(" and  give_exp = ? ");
			val.add(soul.getGiveExp());
		}
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());

	}

	public List<SkillStrike> querySkillStrike() {
		return queryForList("select * from t_skill_strike",
				new RowMapperImpl<SkillStrike>(SkillStrike.class));
	}



	// private static final RowMapper<CapSkill> CAP_SKILL_ROW_MAPPER = new
	// RowMapper<CapSkill>(){
	// @Override
	// public CapSkill rowMapper(ResultSet rs) throws SQLException{
	// CapSkill obj = new CapSkill();
	// obj.setCapSkillId(rs.getInt("cap_skill_id"));
	// obj.setCapSkillName(rs.getString("cap_skill_name"));
	// obj.setCapType(rs.getInt("cap_type"));
	// obj.setTimes(rs.getInt("times"));
	// obj.setTypeValue(getTypeValue(rs.getString("type_value")));
	// obj.setEle(rs.getInt("ele"));
	// obj.setRace(rs.getInt("race"));
	// return obj;
	// }
	//
	// private List<Integer> getTypeValue(String str) {
	// str=str.replaceAll("，", ",");
	// String[] strs = str.split(",");
	// List<Integer> list = new ArrayList<Integer>();
	// if(!str.equals("")){
	// for(String s : strs){
	// int x = Integer.parseInt(s);
	// if(x!=0){
	// list.add(x);
	// }
	// }
	// }
	// return list;
	// }
	// };

	private static final RowMapper<CallSoulRule> CALL_SOUL_RULE_ROW_MAPPER = new RowMapper<CallSoulRule>() {
		@Override
		public CallSoulRule rowMapper(ResultSet rs) throws SQLException {
			CallSoulRule obj = new CallSoulRule();
			obj.setType(rs.getInt("type"));
			obj.setSoulId(rs.getInt("soul_id"));
			obj.setLevel(rs.getInt("level"));
			obj.setRate(rs.getInt("rate"));
			return obj;
		}
	};

	private static final RowMapper<CallSoulRule> ACTIVITY_SOUL_RULE_MAPPER = new RowMapper<CallSoulRule>() {
		@Override
		public CallSoulRule rowMapper(ResultSet rs) throws SQLException {
			CallSoulRule obj = new CallSoulRule();
			obj.setId(rs.getInt("id"));
			obj.setType(rs.getInt("type"));
			obj.setSoulId(rs.getInt("soul_id"));
			obj.setLevel(rs.getInt("level"));
			obj.setRate(rs.getInt("rate"));
			return obj;
		}
	};

	public List<CallSoulRule> queryActivitySoulRule() {
		String sql = "select * from t_activity_soul_rule ";
		return queryForList(sql, ACTIVITY_SOUL_RULE_MAPPER);
	}

//	public void updateActivitySoulRule(CallSoulRule asr) {
//		String sql = "update t_activity_soul_rule set soul_id=?,level=?,rate=? where id = ?";
//		saveOrUpdate(sql, asr.getSoulId(), asr.getLevel(), asr.getRate(),
//				asr.getId());
//	}
//
//	public void addActivitySoulRule(CallSoulRule asr) {
//		String sql = "insert into t_activity_soul_rule(type,soul_id,level,rate ) VALUES(?,?,?,?)";
//		saveOrUpdate(sql, asr.getType(), asr.getSoulId(), asr.getLevel(),
//				asr.getRate());
//
//	}
//
//	public List<CallSoulRule> querySoulCallRule(int type) {
//		String sql = "select * from t_call_soul_rule where type=?";
//		return queryForList(sql, CALL_SOUL_RULE_ROW_MAPPER, type);
//	}
//
//	public List<CallSoulRule> queryActivitySoulCallRule(int type) {
//		String sql = "select * from t_activity_call_soul_rule where type=? and  start_time<=now() and end_time>=now() ";
//		return queryForList(sql, CALL_SOUL_RULE_ROW_MAPPER, type);
//	}
}
