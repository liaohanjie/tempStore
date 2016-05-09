package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.check.CheckConfig;
/**
 * 配置DAO
 * @author hanjie.l
 *
 */
public class CheckConfigDAO extends GameCfgDAOTemplate{
	
	private static final String TABLE = "t_check_config";
	
	private static final RowMapper<CheckConfig> CONFIG_ROW_MAPPER = new RowMapper<CheckConfig>() {
		@Override
		public CheckConfig rowMapper(ResultSet rs) throws SQLException {
			CheckConfig config = new CheckConfig();
			config.setId(rs.getInt("id"));
			config.setOpen(rs.getBoolean("open"));
			config.setAttRange(rs.getFloat("attRange"));
			config.setDefRange(rs.getFloat("defRange"));
			config.setHpRange(rs.getFloat("hpRange"));
			config.setReplyRange(rs.getFloat("replyRange"));
			config.setSkillDamageRange(rs.getFloat("skillDamageRange"));
			config.setNoSkillDamageRange(rs.getFloat("noSkillDamageRange"));
			return config;
		}
	};
	
	
	public CheckConfig queryCheckConfig() {
		return queryForEntity("select * from " + TABLE, CONFIG_ROW_MAPPER);
	}

}
