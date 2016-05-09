package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.dungeon.Box;
import com.ks.model.dungeon.Chapter;
import com.ks.model.dungeon.ChapterJoin;
import com.ks.model.dungeon.ChapterRound;
import com.ks.model.dungeon.Drop;
import com.ks.model.dungeon.Monster;
/**
 * 地下城
 * @author ks
 */
public class ChapterCfgDAO extends GameCfgDAOTemplate {
	
	public static final String DROP_RATE_MULTIPLE_KEY = "DROP_RATE_MULTIPLE_KEY";

//	private static final String getDropRateMultiple(int defineId,int siteId) {
//		return DROP_RATE_MULTIPLE_KEY + SPEET + defineId+SPEET+siteId;
//	}

//	private static final String getDropRateMulmipleSetKey() {
//		return DROP_RATE_MULTIPLE_KEY;
//	}
	
		private static final RowMapper<Chapter> CHAPTER_ROW_MAPPER = new RowMapper<Chapter>(){
			@Override
			public Chapter rowMapper(ResultSet rs) throws SQLException{
				Chapter obj = new Chapter();
				obj.setChapterId(rs.getInt("chapter_id"));
				obj.setSiteId(rs.getInt("site_id"));
				obj.setName(rs.getString("name"));
				obj.setStamina(rs.getInt("stamina"));
				obj.setNextId(rs.getInt("next_id"));
				obj.setPevId(rs.getInt("pev_id"));
				obj.setExp(rs.getInt("exp"));
				obj.setMinLevel(rs.getInt("min_level"));
				obj.setFightCount(rs.getInt("fight_count"));
				return obj;
			}
		};
	
		private static final RowMapper<Monster> MONSTER_ROW_MAPPER = new RowMapper<Monster>(){
			@Override
			public Monster rowMapper(ResultSet rs) throws SQLException{
				Monster obj = new Monster();
				obj.setMonsterId(rs.getInt("monster_id"));
				obj.setName(rs.getString("name"));
				obj.setEle(rs.getInt("ele"));
				obj.setHp(rs.getInt("hp"));
				//obj.setExp(rs.getInt("exp"));
				obj.setAtk(rs.getInt("atk"));
				obj.setDef(rs.getInt("def"));
				obj.setHit(rs.getInt("hit"));
				obj.setDropId(rs.getInt("drop_id"));
				obj.setSoulId(rs.getInt("soul_id"));
				return obj;
			}
		};
		

	
	private static final RowMapper<Drop> DROP_ROW_MAPPER = new RowMapper<Drop>(){
		@Override
		public Drop rowMapper(ResultSet rs) throws SQLException{
			Drop obj = new Drop();
			obj.setDropId(rs.getInt("drop_id"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setType(rs.getInt("type"));
			obj.setRate(rs.getDouble("rate"));
			return obj;
		}
	};
	
	private static final RowMapper<Box> BOX_ROW_MAPPER = new RowMapper<Box>(){
		@Override
		public Box rowMapper(ResultSet rs) throws SQLException{
			Box obj = new Box();
			obj.setBoxId(rs.getInt("box_id"));
			obj.setType(rs.getInt("type"));
			obj.setDropId(rs.getInt("drop_id"));
			return obj;
		}
	};
	private static final RowMapper<ChapterRound> CHAPTER_ROUND_ROW_MAPPER = new RowMapper<ChapterRound>(){
		@Override
		public ChapterRound rowMapper(ResultSet rs) throws SQLException{
			ChapterRound obj = new ChapterRound();
			obj.setChapterId(rs.getInt("chapter_id"));
			obj.setRound(rs.getInt("round"));
			obj.setMonsters(rs.getString("monsters"));
			return obj;
		}
	};
	private static final RowMapper<ChapterJoin> CHAPTER_JOIN_ROW_MAPPER = new RowMapper<ChapterJoin>(){
		@Override
		public ChapterJoin rowMapper(ResultSet rs) throws SQLException{
			ChapterJoin obj = new ChapterJoin();
			obj.setChapterId(rs.getInt("chapter_id"));
			obj.setRate(rs.getDouble("rate"));
			obj.setMonster(rs.getString("monsters"));
			obj.setDropId(rs.getInt("drop_id"));
			return obj;
		}
	};

//	private static final ObjectFieldMap<DropRateMultiple> FIELD_MAP = new ObjectFieldMap<DropRateMultiple>() {
//		@Override
//		public Map<String, String> objectToMap(DropRateMultiple o) {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("id", String.valueOf(o.getId()));
//			map.put("defineId", String.valueOf(o.getDefineId()));
//			map.put("siteId", String.valueOf(o.getSiteId()));
//			map.put("multiple", String.valueOf(o.getMultiple()));
//			map.put("goodsType", String.valueOf(o.getGoodsType()));
//			
//			return map;
//		}
//	};
	
//	private static final JedisRowMapper<DropRateMultiple> JEDIS_ROW_MAPPER = new JedisRowMapper<DropRateMultiple>() {
//		@Override
//		public DropRateMultiple rowMapper(JedisResultSet jrs) {
//			DropRateMultiple obj = new DropRateMultiple();
//			obj.setId(jrs.getInt("id"));
//			obj.setDefineId(jrs.getInt("defineId"));
//			obj.setSiteId(jrs.getInt("siteId"));
//			obj.setMultiple(jrs.getInt("multiple"));
//			obj.setGoodsType(jrs.getInt("goodsType"));
//			return obj;
//		}
//	};
	
	
	public List<Chapter> queryAllChapters(){
		return queryForList("select * from t_chapter", CHAPTER_ROW_MAPPER);
	}
	public List<Monster> queryAllMonster(){
		return queryForList("select * from t_monster", MONSTER_ROW_MAPPER);
	}
	
	public List<Box> queryAllBoxs(){
		return queryForList("select * from t_box", BOX_ROW_MAPPER);
	}
	public List<Drop> queryAllDrop(){
		String sql = "select * from t_drop";
		return queryForList(sql, DROP_ROW_MAPPER);
	}
	public List<ChapterRound> queryAllRounds(){
		String sql = "select * from t_chapter_round";
		return queryForList(sql, CHAPTER_ROUND_ROW_MAPPER);
	}
	public List<ChapterJoin> queryAllChpaterJoin(){
		String sql="select * from t_chapter_join";
		 return queryForList(sql, CHAPTER_JOIN_ROW_MAPPER);
	}
}
