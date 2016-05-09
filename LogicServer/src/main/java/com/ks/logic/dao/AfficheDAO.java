package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.affiche.Affiche;
import com.ks.model.goods.Goods;
import com.ks.util.JSONUtil;

public class AfficheDAO extends GameDAOTemplate {
	
	private static String getTableName(int userId){
		return "t_affiche_"+(userId%10);
	}
	
	private static final RowMapper<Affiche> AFFICHE_ROW_MAPPER = new RowMapper<Affiche>(){
		@Override
		public Affiche rowMapper(ResultSet rs) throws SQLException{
			Affiche obj = new Affiche();
			obj.setId(rs.getInt("id"));
			obj.setUserId(rs.getInt("user_id"));
			obj.setType(rs.getInt("type"));
			obj.setTitle(rs.getString("title"));
			obj.setContext(rs.getString("context"));
			obj.setGoodsList(getGoodsList(rs.getString("goods")));
			obj.setState(rs.getInt("state"));
			obj.setLogo(rs.getString("logo"));
			obj.setCreateTime(rs.getTimestamp("create_time"));
			obj.setUpdateTime(rs.getTimestamp("update_time"));
			return obj;
		}

		private List<Goods> getGoodsList(String json) {
			if(json==null||"".equals(json)){
				return null;
			}else{
				return JSONUtil.toObject(json, new TypeReference<List<Goods>>() {
				});
			}
		}
	};
	
	public List<Affiche> queryAffiches(int userId){
		String sql = "select * from "+getTableName(userId)+" where user_id=?";
		return queryForList(sql, AFFICHE_ROW_MAPPER, userId);
	}
	
	public Affiche queryAffiche(int id,int userId){
		String sql = "select * from "+getTableName(userId)+" where id=? and user_id=?";
		return queryForEntity(sql, AFFICHE_ROW_MAPPER, id,userId);
		
	}
	public void deleteAffiche(int id,int userId){
		String sql = "delete from "+getTableName(userId)+ " where id=? and user_id=?";
		saveOrUpdate(sql, id,userId);
	}
	private String getGoodsListJson( List<Goods> goodsList) {
		if(goodsList==null){
			return null;
		}else{
			return JSONUtil.toJson(goodsList);
		}
	}
	public Affiche addAffiche(Affiche a){
		String sql = "insert into "+getTableName(a.getUserId())+" (user_id,type,title,context,goods,state,logo,create_time,update_time) values (?, ?, ?, ?,?,?,?,now(), now());";		
		int id = insertAndReturnId(sql,INT_KEY,a.getUserId(),a.getType(),a.getTitle(),a.getContext(),getGoodsListJson(a.getGoodsList()),a.getState(),a.getLogo());
		a.setId(id);
		return a;
	}
	
	public void updateAffiche(Affiche a){
		String sql = "update "+getTableName(a.getUserId())+" set goods=?,state=?,update_time=now() where user_id=? and id=?";
		saveOrUpdate(sql, getGoodsListJson(a.getGoodsList()),a.getState(),a.getUserId(),a.getId());
	}
	public void updateAfficheState(int userId,int state,Collection<Integer> ids){
		StringBuffer buff=new StringBuffer("update "+getTableName(userId)+" set state=?,update_time=now() where user_id=? and id in ");
		List<Object> val=new ArrayList<>();
		val.add(state);
		val.add(userId);
		if (ids!=null&&!ids.isEmpty()) {
				buff.append(" ( ");
				for (Integer value : ids) {
					buff.append("? ,");
					val.add(value);
				}
				buff.replace(buff.length() - 1, buff.length(), "");
				buff.append(" )");
			}
		saveOrUpdate(buff.toString(), val.toArray());
	}
	
	public void cleanAffiche(int state,Date time){
		for(int i=0;i<=9;i++){
			String sql="delete from "+getTableName(i)+" where state=? and create_time<=? ";
			saveOrUpdate(sql, state,time);
		}

	}
	
}
