package com.ks.access;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.ks.access.mapper.GeneratedKey;
import com.ks.access.mapper.RowMapper;
import com.ks.access.mapper.impl.GenerateKeyUtil;
import com.ks.cache.JedisTemplate;
import com.ks.logger.LoggerFactory;

/**
 * 工具类在dao层包装的一个类
 * 这里面提供了基本的增、删、改、查功能
 * */
public abstract class GameDAOTemplate extends JedisTemplate{
	private static final Logger logger = LoggerFactory.get(GameDAOTemplate.class);
	
	protected static final GeneratedKey<Integer> INT_KEY = new GeneratedKey<Integer>() {
		@Override
		public Integer getGeneratedKey(ResultSet rs) {
			return GenerateKeyUtil.intKey(rs);
		}
	};
	protected static final  GeneratedKey<Long> LONG_KEY = new GeneratedKey<Long>() {
		@Override
		public Long getGeneratedKey(ResultSet rs) {
			return GenerateKeyUtil.longKey(rs);
		}
	};
	
	protected static final GeneratedKey<String> STR_KEY = new GeneratedKey<String>() {
		@Override
		public String getGeneratedKey(ResultSet rs) {
			return GenerateKeyUtil.StringKey(rs);
		}
	};
	protected static final GeneratedKey<Short> SHORT_KEY = new GeneratedKey<Short>() {
		@Override
		public Short getGeneratedKey(ResultSet rs) {
			return GenerateKeyUtil.shortKey(rs);
		}
	};
	
	protected static final RowMapper<Integer> INT_ROW_MAPPER = new RowMapper<Integer>() {
		@Override
		public Integer rowMapper(ResultSet rs) throws SQLException {
			return rs.getInt(1);
		}
	};
	protected static final RowMapper<String>	STRING_ROW_MAPPER = new RowMapper<String>() {
		@Override
		public String rowMapper(ResultSet rs) throws SQLException {
			return rs.getString(1);
		}
	};
	protected static final RowMapper<Date>	DATE_ROW_MAPPER = new RowMapper<Date>() {
		@Override
		public Date rowMapper(ResultSet rs) throws SQLException {
			return rs.getDate(1);
		}
	};
	/**
	 * 获取连接
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException{
		return DataSourceUtils.getConnection();
	}
	/**
	 * 更新数据
	 * @param sql 要执行的SQL语句
	 * @param args 传递的参数（注意参数位置要和问好位置一一对应）
	 * */
	protected void saveOrUpdate(String sql,Object...args)throws GameDaoException{
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("save or update execute sql : "+ sql);
		}
		Connection conn;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setArgs(ps, args);
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new GameDaoException("update data error",e);
		}finally{
			releaseResources(ps, null);
		}
	}
	
	/**
	 * 批量更新
	 * @param sql 要执行的SQL语句
	 * @param args 传递的参数
	 */
	protected void executeBatch(String sql,List<Object[]> args){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("execute batch execute sql : "+ sql);
		}
		Connection conn;
		PreparedStatement ps = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			for(Object[] arg : args){
				setArgs(ps, arg);
				ps.addBatch();
			}
			ps.executeBatch();
		} catch (SQLException e) {
			throw new GameDaoException("update data error",e);
		}finally{
			releaseResources(ps, null);
		}
	}
	
	/**
	 * 查找数据库中的实体
	 * 这里只返回一个对象
	 * @param sql 需要执行的sql语句
	 * @param rowMapper 对应的映射器
	 * @param args 参数集 注意这里的参数需要和sql中的问号一一对应
	 * @return entity 具体的实体对象
	 * */
	protected <T>T queryForEntity(String sql,RowMapper<T> rowMapper,Object...args)throws GameDaoException{
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query for entity execute sql : "+ sql);
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		T entity = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setArgs(ps, args);
			long start = System.currentTimeMillis();
			rs = ps.executeQuery();
			long end = System.currentTimeMillis();
			if(end-start>300){
				logger.warn("Slow SQL:"+sql+" times: "+(end-start));
			}
			while(rs.next()){
				entity = rowMapper.rowMapper(rs);
			}
		} catch (SQLException e) {
			throw new GameDaoException("query entity error",e);
		}finally{
			releaseResources( ps, rs);
		}
		return entity;
	}
	private void setArgs(PreparedStatement ps, Object... args)
			throws SQLException {
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("-------------------------------------");
		}
		for(int i=0;i<args.length;i++){
			if(LoggerFactory.getLevel()==Level.DEBUG){
				logger.debug("args["+i+"]:"+args[i]);
			}
			ps.setObject(i+1, args[i]);
		}
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("-------------------------------------");
		}
	}
	
	
	/**
	 * 查找多个结果
	 * @param sql 需要执行的sql语句
	 * @param rowMapper 对应的映射器
	 * @param args 参数集 注意这里的参数需要和sql中的问号一一对应
	 * @return entity 具体的实体对象
	 * */
	protected <T>List<T> queryForList(String sql,RowMapper<T> rowMapper,Object...args){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query for list execute sql : "+ sql);
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<T> entitys = new ArrayList<T>();
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			setArgs(ps, args);
			long start = System.currentTimeMillis();
			rs = ps.executeQuery();
			long end = System.currentTimeMillis();
			if(end-start>300){
				logger.warn("Slow SQL:"+sql+" times: "+(end-start));
			}
			while(rs.next()){
				entitys.add(rowMapper.rowMapper(rs));
			}
		} catch (SQLException e) {
			throw new GameDaoException("query for list error",e);
		}finally{
			releaseResources( ps, rs);
		}
		return entitys;
	}
	
	/**
	 * 把结果集封装到一个List中,List中是一个Map。
	 * Map的Key是查询的别名 value是里面的值
	 * @param args 参数集 注意这里的参数需要和sql中的问号一一对应
	 * @param sql 要执行的sql
	 * */
	protected List<Map<String, Object>> queryForListMap(String sql,Object...args) throws SQLException {
		Connection conn = getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		try {
			ps = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++){
				ps.setObject(i+1, args[i]);
			}
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int count = rsmd.getColumnCount();
			String[] colNames = new String[count];
			for (int i = 1; i <= count; i++) {
				colNames[i - 1] = rsmd.getColumnLabel(i);
			}
			while (rs.next()) {
				Map<String, Object> data = new HashMap<String, Object>();
				for (int i = 0; i < colNames.length; i++) {
					data.put(colNames[i], rs.getObject(colNames[i]));
				}
				datas.add(data);
			}
		} finally {
			releaseResources( ps, rs);
		}
		return datas;
	}
	
	/**
	 * 插入一条数据并且返回它的主键
	 * @param sql 要执行的sql语句
	 * @param generatedKey 需要以何种方式来获得主键 需要客服端实现这个接口
	 * @param args sql中的参数 参数要和问号一一对应才能够正确插入 可以传入多个参数也可以传入一个Object的数组
	 * @return ID 获得的id
	 * */
	protected <ID extends Serializable>ID insertAndReturnId(String sql,GeneratedKey<ID> generatedKey,Object...args){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("insert and returnId sql : "+ sql);
		}
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ID id = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			setArgs(ps, args);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			id = generatedKey.getGeneratedKey(rs);
		} catch (SQLException e) {
			throw new GameDaoException("update data error",e);
		}finally{
			releaseResources(ps, rs);
		}
		return id;
	}
	
	/**
	 * 释放资源
	 * @param stmt 要释放的Statement
	 * @param rs 要释放的结果集
	 * */
	protected void releaseResources(Statement stmt,ResultSet rs)throws GameDaoException{
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				throw new GameDaoException("", e);
			}
		}
		if(stmt!=null){
			try {
				stmt.close();
			} catch (SQLException e) {
				throw new GameDaoException("", e);
			}
		}
	}
	
	protected String outPout(String table){
		String startSql="show tables";
		List<String> tables=new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug(startSql);
		}

		try {
			conn = getConnection();
			ps = conn.prepareStatement(startSql);
			rs=ps.executeQuery();
			while(rs.next()){
				tables.add(rs.getString(1));
			}
			rs.close();
			for(String str:tables){
				rs=ps.executeQuery(" show create table "+str);
				while(rs.next()){
					System.out.println(rs.getString(2));
				}
			}
			return "";
			
		} catch (SQLException e) {
			throw new GameDaoException("update data error",e);
		}finally{
			releaseResources(ps, rs);
		}
	}
}
