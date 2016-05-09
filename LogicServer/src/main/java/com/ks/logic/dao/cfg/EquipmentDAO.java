package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.access.mapper.impl.RowMapperImpl;
import com.ks.model.equipment.Equipment;
import com.ks.model.equipment.EquipmentEffect;
import com.ks.model.equipment.EquipmentRepair;
import com.ks.model.equipment.EquipmentSkill;
/**
 * 装备
 * @author ks
 */
public class EquipmentDAO extends GameCfgDAOTemplate {
	
	private static final RowMapper<EquipmentRepair> EQUIPMENT_REPAIR_ROW_MAPPER = new RowMapper<EquipmentRepair>(){
		@Override
		public EquipmentRepair rowMapper(ResultSet rs) throws SQLException{
			EquipmentRepair obj = new EquipmentRepair();
			obj.setEquipmentId(rs.getInt("equipment_id"));
			obj.setGoodsType(rs.getInt("goods_type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	private static final RowMapper<EquipmentEffect> EQUIPMENT_EFFECT_ROW_MAPPER = new RowMapper<EquipmentEffect>(){
		@Override
		public EquipmentEffect rowMapper(ResultSet rs) throws SQLException{
			EquipmentEffect obj = new EquipmentEffect();
			obj.setEquipmentId(rs.getInt("equipment_id"));
			obj.setEffectType(rs.getInt("effect_type"));
			obj.setAddPoint(rs.getInt("add_point"));
			obj.setAddPercent(rs.getDouble("add_percent"));
			return obj;
		}
	};
	
//	private static final RowMapper<Equipment> EQUIPMENT_ROW_MAPPER = new RowMapper<Equipment>(){
//		@Override
//		public Equipment rowMapper(ResultSet rs) throws SQLException{
//			Equipment obj = new Equipment();
//			obj.setEquipmentId(rs.getInt("equipment_id"));
//			obj.setName(rs.getString("name"));
//			obj.setEquipmentId(rs.getInt("equipment_type"));
//			obj.setEquipmentId(rs.getInt("sell_price"));
//			obj.setEquipmentId(rs.getInt("max_durable"));
//			return obj;
//		}
//	};
	private static final RowMapper<EquipmentSkill> EQUIPMENT_SKILL_ROW = new RowMapper<EquipmentSkill>(){
	@Override
	public EquipmentSkill rowMapper(ResultSet rs) throws SQLException{
		EquipmentSkill obj = new EquipmentSkill();
		obj.setPropId(rs.getInt("prop_id"));
		obj.setActiveSkillId(rs.getInt("active_skill_id"));
		obj.setSkillLevel(rs.getInt("skill_level"));
		obj.setSkillType(rs.getInt("skill_type"));
		obj.setGold(rs.getInt("gold"));
		return obj;
	}
};
	public List<Equipment> queryAllEquipment(){
		return queryForList("select * from t_equipment", new RowMapperImpl<>(Equipment.class));
	}
	
	public List<EquipmentEffect> queryAllEquipmentEffect(){
		return queryForList("select * from t_equipment_effect",EQUIPMENT_EFFECT_ROW_MAPPER);
	}
	
	public List<EquipmentSkill> queryEquipmentSkill(){
		return queryForList("select * from t_equipment_skill",EQUIPMENT_SKILL_ROW);
	}
	
	public List<EquipmentRepair> queryAllEquipmentRepair(){
		return queryForList("select * from t_equipment_repair", EQUIPMENT_REPAIR_ROW_MAPPER);
	}
	
}
