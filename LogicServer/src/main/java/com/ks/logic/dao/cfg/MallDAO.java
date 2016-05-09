package com.ks.logic.dao.cfg;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.ks.access.GameCfgDAOTemplate;
import com.ks.access.mapper.RowMapper;
import com.ks.model.pay.Mall;

/**
 * 充值送魂钻
 * @author zhoujf
 */
public class MallDAO extends GameCfgDAOTemplate {
	
	private static final String TABLE = "t_mall";
	
	private static final String SQL_SELECT = "SELECT * FROM " + TABLE;
	
	private static final String SQL_ADD = "INSERT into " + TABLE + "(money, currency, extra, status) VALUES(?,?,?,?)";
	
	private static final String SQL_UPDATE = "UPDATE " + TABLE + " SET money=?, currency=?, extra=?, status=? WHERE money=?";
	
	private static final String SQL_DELETE = "DELETE FROM " + TABLE + " WHERE money=?";
	
	
	private static final RowMapper<Mall> ROW_MAPPER = new RowMapper<Mall>(){
		@Override
		public Mall rowMapper(ResultSet rs) throws SQLException{
			Mall obj = new Mall();
			obj.setMoney(rs.getInt("money"));
			obj.setCurrency(rs.getInt("currency"));
			obj.setExtra(rs.getInt("extra"));
			obj.setStatus(rs.getByte("status"));
			return obj;
		}
	};
	
	/**
	 * 查询所有充值送魂钻活动
	 * @return
	 */
	public List<Mall> queryAllMall(){
		return queryForList(SQL_SELECT, ROW_MAPPER);
	}
	
//	/**
//	 * 添加充值送魂钻活动
//	 * @param mall
//	 */
//	public void addMall(Mall mall){
//		saveOrUpdate(SQL_ADD, mall.getMoney(), mall.getCurrency(), mall.getExtra(), mall.getStatus());
//	}
//	
//	/**
//	 * 更新充值送魂钻活动
//	 * @param list
//	 */
//	public void updateMall(List<Mall> list){
//		if (list == null || list.isEmpty()){
//			return;
//		}
//		
//		List<Object[]> args = new ArrayList<>();
//		for(Mall entity : list){
//			Object[] arg = new Object[]{
//					entity.getMoney(), entity.getCurrency(), entity.getExtra(), entity.getStatus(), entity.getMoney()
//			};
//			args.add(arg);
//		}
//		executeBatch(SQL_UPDATE, args);
//	}
//	
//	/**
//	 * 更新充值送魂钻活动
//	 * @param list
//	 */
//	public void updateMall(Mall mall){
//		saveOrUpdate(SQL_UPDATE, mall.getMoney(), mall.getCurrency(), mall.getExtra(), mall.getStatus(), mall.getMoney());
//	}
//	
//	/**
//	 * 删除充值送魂钻活动
//	 * @param money
//	 */
//	public void deleteMall(int money){
//		saveOrUpdate(SQL_DELETE, money);
//	}
	
}