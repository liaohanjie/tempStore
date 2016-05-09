package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.pvp.AthleticsInfo;
import com.ks.util.StringUtil;

public class AthleticsInfoDAO extends GameDAOTemplate {

	
	private static final RowMapper<AthleticsInfo> ATHLETICS_INFO_MAPPER = new RowMapper<AthleticsInfo>(){
		@Override
		public AthleticsInfo rowMapper(ResultSet rs) throws SQLException{
			AthleticsInfo pojo = new AthleticsInfo();
			pojo.setUserId(rs.getInt("user_id"));
			pojo.setAthleticsPoint(rs.getInt("athletics_point"));
			pojo.setTotalIntegral(rs.getInt("total_integral"));
			pojo.setWins(rs.getInt("wins"));
			pojo.setLose(rs.getInt("lose"));
			pojo.setStreakWin(rs.getInt("streak_win"));
			pojo.setHighestWinStreak(rs.getInt("highest_win_streak"));
			pojo.setLastBackTime(rs.getTimestamp("last_back_time"));
			pojo.setAwardTitle(StringUtil.stringToList(rs.getString("award_titile")));
			pojo.setCreateTime(rs.getTimestamp("create_time"));
			pojo.setUpdateTime(rs.getTimestamp("update_time"));
		    return pojo;
		}
	};
	
	/**
	 * 添加
	 * @param info
	 */
	public void addAthleticsInfo(AthleticsInfo info){
		String sql="insert into t_athletics_info(user_id,athletics_point,total_integral,wins,lose,streak_win,highest_win_streak,last_back_time,award_titile,create_time,update_time)"+ 
        " values (?,?,?,?,?,?,?,?,?,now(),now())";
		saveOrUpdate(sql,info.getUserId(),info.getAthleticsPoint(),
				info.getTotalIntegral(),info.getWins(),info.getLose(),info.getStreakWin(),info.getHighestWinStreak(),
				info.getLastBackTime(),StringUtil.listToString(info.getAwardTitle()));
	}
	
	public void updateAthleticsInfo(AthleticsInfo info){
		    String sql = "update t_athletics_info set "+"athletics_point=?,total_integral=?,"
				+ "wins=?,lose=?,streak_win=?,highest_win_streak=?,last_back_time=?,award_titile=?,update_time=now() where user_id = ?";
			saveOrUpdate(sql,info.getAthleticsPoint(),
			info.getTotalIntegral(),info.getWins(),info.getLose(),info.getStreakWin(),info.getHighestWinStreak(),
			info.getLastBackTime(),StringUtil.listToString(info.getAwardTitle()),info.getUserId());
		
	}
	public AthleticsInfo getAthleticsInfo(int userId){
		 String sql ="select * from t_athletics_info where user_id=?";
		 return queryForEntity(sql, ATHLETICS_INFO_MAPPER, userId);
	}
	
	public int queryMyRank(int integral){
		String sql ="select count(1) from t_athletics_info  where total_integral>?";
		return queryForEntity(sql, INT_ROW_MAPPER, integral)+1;
	}
	
	/**
	 * 查询积分前三十名
	 * @return
	 */
	public List<AthleticsInfo> queryAthleticsInfoBytotalIntegral(){
		String sql="select * from t_athletics_info where total_integral!=0 order by total_integral desc,wins desc,highest_win_streak desc,user_id asc limit 0,30";
		return super.queryForList(sql,ATHLETICS_INFO_MAPPER);	
	}
	
	/**
	 * 匹配对手
	 * @return
	 */
	public List<AthleticsInfo> queryMatchUserIds(int order,int point,int userId){
		String sql="select * from t_athletics_info";
		if(order==1){
		     sql+=" where user_id != ? and total_integral>=? order by total_integral asc limit 0,10";
		}else if(order==2){
			 sql+=" where user_id != ? and total_integral<=? order by total_integral asc limit 0,10";
		}
		return super.queryForList(sql,ATHLETICS_INFO_MAPPER,userId,point);	
	}
	
	
}
