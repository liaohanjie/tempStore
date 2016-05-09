package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.activity.OnTimeLoginGift;
import com.ks.model.activity.TotalLoginGift;

/**
 * 
 * @author living.li
 * @date 2014年4月9日
 */
public class ActivityCfgDAO extends GameCfgDAOTemplate {


	private static final RowMapper<OnTimeLoginGift> AFFICHE_ROW_MAPPER = new RowMapper<OnTimeLoginGift>() {
		@Override
		public OnTimeLoginGift rowMapper(ResultSet rs) throws SQLException {
			OnTimeLoginGift obj = new OnTimeLoginGift();
			obj.setId(rs.getString("id"));
			obj.setDay(rs.getInt("day"));
			obj.setStartTime(rs.getTimestamp("start_time"));
			obj.setEndTime(rs.getTimestamp("end_time"));
			obj.setLogo(rs.getString("logo"));
			obj.setContext(rs.getString("context"));
			obj.setGoodsType(rs.getInt("goods_type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setGoodsLevel(1);
			obj.setTitle(rs.getString("title"));
			return obj;
		}
	};
	private static final RowMapper<TotalLoginGift> TOTAL_ROW_MAPPER = new RowMapper<TotalLoginGift>() {
		@Override
		public TotalLoginGift rowMapper(ResultSet rs) throws SQLException {
			TotalLoginGift obj = new TotalLoginGift();
			obj.setId(rs.getString("id"));
			obj.setDay(rs.getInt("day"));
			obj.setLogo(rs.getString("logo"));
			obj.setContext(rs.getString("context"));
			obj.setGoodsType(rs.getInt("goods_type"));
			obj.setAssId(rs.getInt("ass_id"));
			obj.setNum(rs.getInt("num"));
			obj.setGoodsLevel(1);
			obj.setTitle(rs.getString("title"));
			return obj;
		}
	};
	
	
	public List<OnTimeLoginGift> queryOnTimeLoginGifts() {
		String sql = "select * from t_ontime_login_gift where start_time<=now() and end_time>=now() order by day asc";
		return queryForList(sql, AFFICHE_ROW_MAPPER);
	}

	public List<TotalLoginGift> queryTotalLoginGifts() {
		String sql = "select * from t_total_login_gift order by day asc";
		return queryForList(sql, TOTAL_ROW_MAPPER);
	}
	
	// ---------------------------------------------------------------------------------------
	// logic

	

}
