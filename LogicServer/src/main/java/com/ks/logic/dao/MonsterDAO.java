package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.dungeon.Monster;
import com.ks.model.filter.MonsterFilter;

public class MonsterDAO extends GameDAOTemplate {
	private static final RowMapper<Monster> MONSTER_ROW_MAPPER = new RowMapper<Monster>() {

		@Override
		public Monster rowMapper(ResultSet rs) throws SQLException {
			Monster obj = new Monster();
			obj.setMonsterId(rs.getInt("monster_id"));
			obj.setName(rs.getString("name"));
			obj.setEle(rs.getInt("ele"));
			obj.setHp(rs.getInt("hp"));
			obj.setAtk(rs.getInt("atk"));
			obj.setDef(rs.getInt("def"));
			obj.setHit(rs.getInt("hit"));
			obj.setExp(rs.getInt("exp"));
			obj.setDropId(rs.getInt("drop_id"));
			obj.setSoulId(rs.getInt("soul_id"));
			obj.setMarquee(rs.getInt("marquee"));
			return obj;
		}
	};

	public List<Monster> getMonster(MonsterFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select * from t_monster where 1 =1 ");
		List<Object> val = new ArrayList<Object>();
		if (filter.getMonsterId() != null) {
			sql.append(" and monster_id like concat('%',?,'%') ");
			val.add(filter.getMonsterId());
		}
		if (filter.getName() != null) {
			sql.append(" and name like concat('%',?,'%') ");
			val.add(filter.getName());
		}
		sql.append(" limit " + filter.getStart() + "," + filter.getPageSize());
		return queryForList(sql.toString(), MONSTER_ROW_MAPPER, val.toArray());
	}

	public Integer getMonsterCount(MonsterFilter filter) {
		StringBuffer sql = new StringBuffer(
				"select count(1) from t_monster where 1 =1 ");
		List<Object> val = new ArrayList<Object>();
		if (filter.getMonsterId() != null) {
			sql.append(" and monster_id  like concat('%',?,'%') ");
			val.add(filter.getMonsterId());
		}
		if (filter.getName() != null) {
			sql.append(" and name like concat('%',?,'%') ");
			val.add(filter.getName());
		}
		return queryForEntity(sql.toString(), INT_ROW_MAPPER, val.toArray());
	}

}
