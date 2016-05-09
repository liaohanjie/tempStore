package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.friend.FriendGifiRule;

/**
 * 
 * @author zck
 *
 */
public class FriendGiftCfgDAO extends GameCfgDAOTemplate {

	private static final RowMapper<FriendGifiRule> FRIEND_GIFI_RULE_ROW_MAPPER = new RowMapper<FriendGifiRule>(){
		@Override
		public FriendGifiRule rowMapper(ResultSet rs) throws SQLException{
			FriendGifiRule obj = new FriendGifiRule();
			obj.setZone(rs.getInt("zone"));
			obj.setType(rs.getInt("type"));
			obj.setGifiId(rs.getInt("gifi_id"));
			obj.setNum(rs.getInt("num"));
			return obj;
		}
	};
	
	public List<FriendGifiRule> queryFriendRules(){
		return queryForList("select * from t_friend_gifi_rule", FRIEND_GIFI_RULE_ROW_MAPPER);
	}

}
