package com.ks.access;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 事务管理类
 * */
public class TransactionManager {

	private static ThreadLocal<Connection> conn;
	private static DataSource dataSource;
	
	public static void setDataSource(DataSource dataSource) {
		TransactionManager.dataSource = dataSource;
	}

	/**
	 * 开启事务
	 * */
	public static void beginTransaction()throws GameDaoException{
		try {
			conn.get().setAutoCommit(false);
		} catch (SQLException e) {
			throw new GameDaoException("开启事务失败！",e);
		}
	}
	
	/**
	 * 提交和关闭连接
	 * */
	public static void commitAndClose()throws GameDaoException{
		try {
			conn.get().commit();
		} catch (SQLException e) {
			throw new GameDaoException("提交事务失败！",e);
		}finally{
			try {
				conn.remove();
				conn.get().close();
			} catch (SQLException e) {
				throw new GameDaoException("关闭连接失败！",e);
			}
		}
	}
	
	/**
	 * 回滚后关闭连接
	 * */
	public static void rollbackAndClose() throws GameDaoException{
		try {
			conn.get().rollback();
		} catch (SQLException e) {
			throw new GameDaoException("提交事务失败！",e);
		}finally{
			try {
				conn.remove();
				conn.get().close();
			} catch (SQLException e) {
				throw new GameDaoException("关闭连接失败！",e);
			}
		}
	}
	
	/**
	 * 获得数据库连接
	 * @return 数据库连接
	 * */
	public static Connection getConnection() {
		try {
			if(conn.get()==null){
				conn.set(dataSource.getConnection());
			}
		} catch (SQLException e) {
			throw new GameDaoException("获得数据库连接时出错",e);
		}
		return conn.get();
	}
	
	/**
	 * 关闭数据库连接
	 * @param conn 需要关闭的数据库连接
	 * */
	public static void close(Connection conn) throws SQLException {
		if(conn.getAutoCommit()){
			conn.close();
		}
	}
	
}
