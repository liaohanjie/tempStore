package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.skill.ActiveSkill;
import com.ks.model.skill.ActiveSkillEffect;
import com.ks.model.skill.CapSkill;
import com.ks.model.skill.CapSkillEffect;
import com.ks.model.skill.SocialSkill;
import com.ks.model.skill.SocialSkillEffect;
import com.ks.model.skill.SocialSkillRule;
import com.ks.util.StringUtil;

public class SkillDAO extends GameCfgDAOTemplate {
	
	private static final RowMapper<ActiveSkill> ACTIVE_SKILL_ROW_MAPPER = new RowMapper<ActiveSkill>(){
		@Override
		public ActiveSkill rowMapper(ResultSet rs) throws SQLException{
			ActiveSkill obj = new ActiveSkill();
			obj.setSkillId(rs.getInt("skill_id"));
			obj.setSkillName(rs.getString("skill_name"));
			obj.setMaxLevel(rs.getInt("max_level"));
			return obj;
		}
	};
	private static final RowMapper<ActiveSkillEffect> ACTIVE_SKILL_EFFECT_ROW_MAPPER = new RowMapper<ActiveSkillEffect>(){
		@Override
		public ActiveSkillEffect rowMapper(ResultSet rs) throws SQLException{
			ActiveSkillEffect obj = new ActiveSkillEffect();
			obj.setSkillId(rs.getInt("skill_id"));
			obj.setLevel(rs.getInt("level"));
			obj.setEffectType(rs.getInt("effect_type"));
			obj.setTarget(rs.getInt("target"));
			obj.setTargetEle(rs.getInt("target_ele"));
			obj.setSuccessRate(rs.getDouble("success_rate"));
			obj.setAddPoint(rs.getInt("add_point"));
			obj.setAddPercent(rs.getDouble("add_percent"));
			obj.setRound(rs.getInt("round"));
			//obj.setHit(rs.getInt("hit"));
			return obj;
		}
	};
	
	private static final RowMapper<SocialSkillRule> SOCIAL_SKILL_RULE_ROW_MAPPER = new RowMapper<SocialSkillRule>(){
		@Override 
		public SocialSkillRule rowMapper(ResultSet rs) throws SQLException{
			SocialSkillRule obj = new SocialSkillRule();
			obj.setSoulId(rs.getInt("soul_id"));
			obj.setJoinSoulId(rs.getInt("join_soul_id"));
			obj.setSkillId(rs.getInt("skill_id"));
			return obj;
		}
	};

	private static final RowMapper<CapSkill> CAPSKILL_SKILL_ROW_MAPPER = new RowMapper<CapSkill>(){
		@Override
		public CapSkill rowMapper(ResultSet rs) throws SQLException{
			CapSkill obj = new CapSkill();
			obj.setSkillId(rs.getInt("skill_id"));
			obj.setNeedEle(StringUtil.stringToList(rs.getString("need_ele")));
			obj.setSkillName(rs.getString("skill_name"));
			return obj;
		}
	};
	private static final RowMapper<CapSkillEffect> CAP_SKILL_EFFECT = new RowMapper<CapSkillEffect>(){
		@Override
		public CapSkillEffect rowMapper(ResultSet rs) throws SQLException{
			CapSkillEffect obj = new CapSkillEffect();
			obj.setSkillId(rs.getInt("skill_id"));
			obj.setEffectType(rs.getInt("effect_type"));
			//obj.setTarget(rs.getInt("target"));
			obj.setTargetEle(rs.getInt("target_ele"));
			obj.setSuccessRate(rs.getDouble("success_rate"));
			obj.setAddPoint(rs.getInt("add_point"));
			obj.setAddPercent(rs.getDouble("add_percent"));
			return obj;
		}
	};
	private static final RowMapper<SocialSkill> SOCIAL_SKILL_ROW_MAPPER = new RowMapper<SocialSkill>(){
		@Override
		public SocialSkill rowMapper(ResultSet rs) throws SQLException{
			SocialSkill obj = new SocialSkill();
			obj.setSkillId(rs.getInt("skill_id"));
			obj.setSkillName(rs.getString("skill_name"));
			return obj;
		}
	};
	private static final RowMapper<SocialSkillEffect> SOICAL_SKILL_EFFECT_ROW_MAPPER = new RowMapper<SocialSkillEffect>(){
		@Override
		public SocialSkillEffect rowMapper(ResultSet rs) throws SQLException{
			SocialSkillEffect obj = new SocialSkillEffect();
			obj.setSkillId(rs.getInt("skill_id"));
			obj.setEffectType(rs.getInt("effect_type"));
			obj.setTarget(rs.getInt("target"));
			obj.setTargetEle(rs.getInt("target_ele"));
			obj.setSuccessRate(rs.getDouble("success_rate"));
			obj.setAddPoint(rs.getInt("add_point"));
			obj.setAddPercent(rs.getDouble("add_percent"));
			obj.setRound(rs.getInt("round"));
			obj.setLevel(rs.getInt("level"));
			obj.setHit(rs.getInt("hit"));
			return obj;
		}
	};
	public List<ActiveSkill> queryActiveSkills(){
		return queryForList("select * from t_active_skill",ACTIVE_SKILL_ROW_MAPPER);
	}
	public List<ActiveSkillEffect> queryActiveSkillEffects(){
		return queryForList("select * from t_active_skill_effect",ACTIVE_SKILL_EFFECT_ROW_MAPPER);
	}
	public List<CapSkill> queryCapSkills(){
		return queryForList("select * from t_cap_skill",CAPSKILL_SKILL_ROW_MAPPER);
	}
	public List<CapSkillEffect> queryCapSkillEffect(){
		return queryForList("select * from t_cap_skill_effect",CAP_SKILL_EFFECT);
	}
	
	public List<SocialSkill> querySocialSkill(){
		return queryForList("select * from t_social_skill",SOCIAL_SKILL_ROW_MAPPER);
	}
	public List<SocialSkillEffect> queryCapSkillEffects(){
		return queryForList("select * from t_social_skill_effect",SOICAL_SKILL_EFFECT_ROW_MAPPER);
	}
	
	public List<SocialSkillRule> querySocialSkillRule(){
		return queryForList("select * from t_social_skill_rule",SOCIAL_SKILL_RULE_ROW_MAPPER);
	}
}
