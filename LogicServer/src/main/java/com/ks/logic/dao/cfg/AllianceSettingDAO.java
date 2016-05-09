package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.alliance.AllianceSetting;
/**
 * 工会配置DAO
 * @author admin
 *
 */
public class AllianceSettingDAO extends GameCfgDAOTemplate{
	
	private static final String getTableName(){
		return " t_alliance_setting ";
	}

	private static final RowMapper<AllianceSetting> ALLIANCE_SETTING_ROW_MAPPER = new RowMapper<AllianceSetting>(){
		@Override
		public AllianceSetting rowMapper(ResultSet rs) throws SQLException{
			AllianceSetting obj = new AllianceSetting();
			obj.setLevel(rs.getInt("level"));
			obj.setCapacity(rs.getInt("capacity"));
			obj.setCostDevote(rs.getInt("costDevote"));
			obj.setCostGold(rs.getInt("costGold"));
			obj.setMaxLevel(rs.getBoolean("maxLevel"));
			return obj;
		}
	};
	
	/**
	 * 获取工会配置
	 * @param id
	 * @return
	 */
	public List<AllianceSetting> getAllAllianceSetting(){
		String sql = "select * from " + getTableName();
		return queryForList(sql, ALLIANCE_SETTING_ROW_MAPPER);
	}
}
