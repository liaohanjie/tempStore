package com.ks.logic.dao.dynamic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.access.GameDynamicDaoTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.dungeon.DropRateMultiple;
/**
 * 地下城
 * @author ks
 */
public class ChapterDAO extends GameDynamicDaoTemplate {
	
	public static final String DROP_RATE_MULTIPLE_KEY = "DROP_RATE_MULTIPLE_KEY";
	private static RowMapper<DropRateMultiple> DROP_RATE_MULTIPLE_ROW_MAPPER = new RowMapper<DropRateMultiple>() {
		@Override
		public DropRateMultiple rowMapper(ResultSet rs)throws SQLException {
			DropRateMultiple pojo = new DropRateMultiple();
			pojo.setId(rs.getInt("id"));
			pojo.setDefineId(rs.getInt("define_id"));
			pojo.setSiteId(rs.getInt("site_id"));
			pojo.setMultiple(rs.getInt("multiple"));
			pojo.setGoodsType(rs.getInt("goods_type"));
			return pojo;
		}
	};
	public DropRateMultiple queryDropRateMultiple(int defineId){
		String sql = "select * from t_drop_rate_multiple where define_id=?";
		return queryForEntity(sql,DROP_RATE_MULTIPLE_ROW_MAPPER,defineId);
	}
	public DropRateMultiple queryDropRateMultipleDefineId(int defineId,int siteId){
		String sql = "select * from t_drop_rate_multiple where define_id=? and  site_id=?";
		return queryForEntity(sql,DROP_RATE_MULTIPLE_ROW_MAPPER,defineId,siteId);
	}
	public void addDropRateMultiple(DropRateMultiple drm){
		String sql = "insert into t_drop_rate_multiple(define_id,site_id,multiple,goods_type) VALUES(?,?,?,?)";
		saveOrUpdate(sql, drm.getDefineId(),drm.getSiteId(),drm.getMultiple(),drm.getGoodsType());
	}
	public void updateDropRateMultiple(DropRateMultiple drm){
		String sql="update t_drop_rate_multiple set define_id=?,site_id=?,multiple=?,goods_type=? where id = ?";
		saveOrUpdate(sql, drm.getDefineId(),drm.getSiteId(),drm.getMultiple(),drm.getGoodsType(),drm.getId());
	}
	public List<DropRateMultiple> queryDropRateMultipleList(){
		String sql="select * from t_drop_rate_multiple";
		return queryForList(sql, DROP_RATE_MULTIPLE_ROW_MAPPER);
	}
	public List<DropRateMultiple> queryDropRateMultipleListBySite(){
		String sql="select * from t_drop_rate_multiple where site_id>0 and site_id<1010001";
		return queryForList(sql, DROP_RATE_MULTIPLE_ROW_MAPPER);
	}
	public void deleteDropRateMultiple(int id){
		String sql="delete from t_drop_rate_multiple where id=? limit 1";
		saveOrUpdate(sql,id);
	}
	
   public void addDropRateMultipleCache(DropRateMultiple drm) {
//		String mapKey = getDropRateMultiple(drm.getDefineId(),drm.getSiteId());
//		String setKey = getDropRateMulmipleSetKey();
//		sadd(setKey, mapKey);
//		hmset(mapKey, FIELD_MAP.objectToMap(drm));
	   addDropRateMultiple(drm);
	}
	
	public List<DropRateMultiple> queryAllDropRateMultipleCache() {
//		Set<String> keys = smembers(getDropRateMulmipleSetKey());
//		List<DropRateMultiple> list = hgetAll(JEDIS_ROW_MAPPER, keys);
//		if (list.isEmpty()) {
//			list = initDropRateMultipleCache();
//		}
		return queryDropRateMultipleList();
	}

	public List<DropRateMultiple> initDropRateMultipleCache() {
		List<DropRateMultiple> list;
		list = queryDropRateMultipleList();
//		for (DropRateMultiple drm : list) {
//			addDropRateMultipleCache(drm);
//		}
		return list;
	}
	
	public void updateDropRateMultipleCache(DropRateMultiple drm) {
		updateDropRateMultiple(drm);
//		String key = getDropRateMultiple(drm.getDefineId(),drm.getSiteId());
//		hmset(key, FIELD_MAP.objectToMap(drm));
	}

	public DropRateMultiple getDropRateMultipleCache(int defineId,int siteId) {
		return queryDropRateMultipleDefineId(defineId,siteId);
	}
	public void delDropRateMultipleCache() {
//		Set<String> keys = smembers(getDropRateMulmipleSetKey());
//		keys.add(getDropRateMulmipleSetKey());
//		del(keys.toArray(new String[keys.size()]));
	}
}
