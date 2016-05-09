package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.alliance.AllianceShopItem;
/**
 * 工会商店配置DAO
 * @author admin
 *
 */
public class AllianceShopItemDAO extends GameCfgDAOTemplate{
	private static final String getTableName(){
		return " t_alliance_shopitem ";
	}

	
	private static final RowMapper<AllianceShopItem> ALLIANCE_SETTING_ROW_MAPPER = new RowMapper<AllianceShopItem>(){
		@Override
		public AllianceShopItem rowMapper(ResultSet rs) throws SQLException{
			AllianceShopItem obj = new AllianceShopItem();
			obj.setId(rs.getInt("id"));
			obj.setGoodsId(rs.getInt("goodsId"));
			obj.setGoodsType(rs.getInt("goodsType"));
			obj.setLevel(rs.getInt("level"));
			obj.setNum(rs.getInt("num"));
			obj.setAllianceLevel(rs.getInt("allianceLevel"));
			obj.setCostDevote(rs.getInt("costDevote"));
			return obj;
		}
	};
	
	/**
	 * 获取工会配置
	 * @param id
	 * @return
	 */
	public List<AllianceShopItem> getAllianceShopItems(){
		String sql = "select * from " + getTableName();
		return queryForList(sql, ALLIANCE_SETTING_ROW_MAPPER);
	}
}
