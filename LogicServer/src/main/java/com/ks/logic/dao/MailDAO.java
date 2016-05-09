package com.ks.logic.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;

import com.ks.access.GameDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.affiche.Mail;
import com.ks.model.goods.Goods;
import com.ks.util.JSONUtil;

/**
 * 系统邮件
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月15日
 */
public class MailDAO extends GameDAOTemplate {

	private static final String TABLE = "t_mail";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE + " where 1=1";
	
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM " + TABLE + " where id=?";
	
	private static final String SQL_ADD = "INSERT INTO " + TABLE + "(`type`, `title`, `context`, `goods`, `user_ids`, `logo`, `from_time`, `end_time`, `create_time`) VALUES(?,?,?,?,?,?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET `type`=?, `title`=?, `context`=?, `goods`=?, `user_ids`=?, `logo`=?, `from_time`=?, `end_time`=?, `create_time`=? WHERE id=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE id=?";

	private static final RowMapper<Mail> ROW_MAPPER = new RowMapper<Mail>() {
		@Override
		public Mail rowMapper(ResultSet rs) throws SQLException {
			Mail entity = new Mail();
			entity.setId(rs.getInt("id"));
			entity.setType(rs.getInt("type"));
			entity.setTitle(rs.getString("title"));
			entity.setContext(rs.getString("context"));
			entity.setGoodsList(getGoodsList(rs.getString("goods")));
			entity.setUserIds(rs.getString("user_ids"));
			entity.setLogo(rs.getString("logo"));
			entity.setFromDate(rs.getTimestamp("from_time"));
			entity.setEndDate(rs.getTimestamp("end_time"));
			entity.setCreateDate(rs.getTimestamp("create_time"));
			return entity;
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
	
	public List<Mail> queryAll() {
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
	
	/**
	 * 查询当前有效的邮件
	 */
	public List<Mail> queryInTime(){
		return queryForList(SQL_SELECT + " and from_time<now() and end_time>now()", ROW_MAPPER);
	}
	
	public void add(Mail entity) {
		saveOrUpdate(SQL_ADD, entity.getType(), entity.getTitle(), entity.getContext(), getGoodsListJson(entity.getGoodsList()), entity.getUserIds(), entity.getLogo(), entity.getFromDate(), entity.getEndDate(), entity.getCreateDate());
	}
	
	public void update(Mail entity) {
		saveOrUpdate(SQL_UPDATE, entity.getType(), entity.getTitle(), entity.getContext(), getGoodsListJson(entity.getGoodsList()), entity.getUserIds(), entity.getLogo(), entity.getFromDate(), entity.getEndDate(), entity.getCreateDate(), entity.getId());
	}
	
	public Mail findById(int id) {
		return queryForEntity(SQL_SELECT_BY_ID, ROW_MAPPER, id);
	}
	
	public void delete(int id) {
		saveOrUpdate(SQL_DELETE, id);
	}
	
	private String getGoodsListJson(List<Goods> goodsList) {
		if(goodsList==null){
			return "";
		}else{
			return JSONUtil.toJson(goodsList);
		}
	}
}