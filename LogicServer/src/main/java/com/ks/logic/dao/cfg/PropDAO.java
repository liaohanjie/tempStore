package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.access.mapper.impl.RowMapperImpl;
import com.ks.model.goods.BakProp;
import com.ks.model.goods.GoodsSynthesis;
import com.ks.model.goods.GoodsSynthesisRule;
import com.ks.model.goods.Prop;
import com.ks.model.goods.PropEffect;

/**
 * 道具
 * 
 * @author ks
 */
public class PropDAO extends GameCfgDAOTemplate {

	private static final RowMapper<GoodsSynthesis> GOODS_SYNTHESIS_ROW_MAPPER = new RowMapper<GoodsSynthesis>() {
		@Override
		public GoodsSynthesis rowMapper(ResultSet rs) throws SQLException {
			GoodsSynthesis obj = new GoodsSynthesis();
			obj.setId(rs.getInt("id"));
			obj.setGoodsType(rs.getInt("goods_type"));
			obj.setGoodsId(rs.getInt("goods_id"));
			obj.setBudingLevel(rs.getInt("buding_level"));
			return obj;
		}
	};

	private static final RowMapper<BakProp> BAK_PROP_ROW_MAPPER = new RowMapper<BakProp>() {
		@Override
		public BakProp rowMapper(ResultSet rs) throws SQLException {
			BakProp obj = new BakProp();
			obj.setId(rs.getInt("id"));
			obj.setMaxNum(rs.getInt("max_num"));
			return obj;
		}
	};

	private static final RowMapper<GoodsSynthesisRule> GOODS_SYNTHESIS_RULE_ROW_MAPPER = new RowMapper<GoodsSynthesisRule>() {
		@Override
		public GoodsSynthesisRule rowMapper(ResultSet rs) throws SQLException {
			GoodsSynthesisRule obj = new GoodsSynthesisRule();
			obj.setId(rs.getInt("id"));
			obj.setGoodsType(rs.getInt("goods_type"));
			obj.setGoodsId(rs.getInt("goods_id"));
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	private static final RowMapper<PropEffect> PROP_EFFECT_ROW_MAPPER = new RowMapper<PropEffect>() {
		@Override
		public PropEffect rowMapper(ResultSet rs) throws SQLException {
			PropEffect obj = new PropEffect();
			obj.setPropId(rs.getInt("prop_id"));
			obj.setEffectType(rs.getInt("effect_type"));
			obj.setTarget(rs.getInt("target"));
			obj.setTargetEle(rs.getInt("target_ele"));
			obj.setAddPoint(rs.getInt("add_point"));
			obj.setAddPercent(rs.getDouble("add_percent"));
			obj.setRound(rs.getInt("round"));
			return obj;
		}
	};

	public List<Prop> queryAllProp() {
		return queryForList("select * from t_prop", new RowMapperImpl<>(Prop.class));
	}

	public List<PropEffect> queryAllPropEffect() {
		return queryForList("select * from t_prop_effect", PROP_EFFECT_ROW_MAPPER);
	}

	public List<GoodsSynthesis> queryGoodsSynthesis() {
		String sql = "select * from t_goods_synthesis";
		return queryForList(sql, GOODS_SYNTHESIS_ROW_MAPPER);
	}

	public List<GoodsSynthesisRule> queryGoodsSynthesisRule() {
		String sql = "select * from t_goods_synthesis_rule";
		return queryForList(sql, GOODS_SYNTHESIS_RULE_ROW_MAPPER);
	}

	public List<BakProp> queryBakProp() {
		String sql = "select * from t_bak_prop";
		return queryForList(sql, BAK_PROP_ROW_MAPPER);
	}
}
