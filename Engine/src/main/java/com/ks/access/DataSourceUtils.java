package com.ks.access;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

/**
 * 数据源
 * @author ks
 *
 */
public final class DataSourceUtils {
	/**数据源*/
	private static DataSource dataSource;
	/**数据库连接*/
	private static ThreadLocal<Connection> connLocal = new ThreadLocal<Connection>();
	/**事务是否提交*/
	private static ThreadLocal<Boolean> autoCommit = new ThreadLocal<Boolean>();
	/**数据源*/
	private static DataSource cfgDataSource;
	/**cfg数据库连接*/
	private static ThreadLocal<Connection> cfgConnLocal = new ThreadLocal<Connection>();
	/**dynamic数据源*/
	private static DataSource dynamicDataSource;
	/**dynamic cfg数据库连接*/
	private static ThreadLocal<Connection> dynamicConnLocal = new ThreadLocal<Connection>();
	/**测试*/
	private static boolean test;
	
	public static void setDataSource(DataSource dataSource) {
		DataSourceUtils.dataSource = dataSource;
	}
	public static void setCfgDataSource(DataSource dataSource) {
		DataSourceUtils.cfgDataSource = dataSource;
	}
	public static void setDynamicDataSource(DataSource dataSource) {
		DataSourceUtils.dynamicDataSource = dataSource;
	}
	public static Connection getDynamicConnection() throws SQLException{
		Connection conn = dynamicConnLocal.get();
		if(conn==null){
			conn = dynamicDataSource.getConnection();
			boolean auto = autoCommit.get()==null?true:autoCommit.get();
			if(!test){
				if(autoCommit.get()!=null){
					conn.setReadOnly(auto);//如果不支持事务，把链接设置为只读
				}
			}
			conn.setAutoCommit(auto);//设置事务
			dynamicConnLocal.set(conn);
		}
		return conn;
	}
	/**
	 * 获取cfg连接
	 * @return
	 * @throws SQLException
	 */
	public static Connection getCfgConnection() throws SQLException{
		Connection conn = cfgConnLocal.get();
		if(conn==null){
			conn = cfgDataSource.getConnection();
			cfgConnLocal.set(conn);
		}
		return conn;
	}
	/**
	 * 获得连接
	 * @return 数据库连接
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn = connLocal.get();
		if(conn==null){
			conn = dataSource.getConnection();
			boolean auto = autoCommit.get()==null?true:autoCommit.get();
			if(!test){
				if(autoCommit.get()!=null){
					conn.setReadOnly(auto);//如果不支持事务，把链接设置为只读
				}
			}
			conn.setAutoCommit(auto);//设置事务
			connLocal.set(conn);
		}
		return conn;
	}
	/**
	 * 设置事务
	 * @param autoCommit
	 */
	public static void setAutoCommit(boolean autoCommit){
		DataSourceUtils.autoCommit.set(autoCommit);
	}
	/**
	 * 回滚事务
	 * @throws SQLException
	 */
	public static void rollback() throws SQLException{
		Connection conn = connLocal.get();
		if(conn!=null){
			if(!conn.getAutoCommit()){
				conn.rollback();
			}
		}
		conn = cfgConnLocal.get();
		if(conn!=null){
			if(!conn.getAutoCommit()){
				conn.rollback();
			}
		}
		conn = dynamicConnLocal.get();
		if(conn!=null){
			if(!conn.getAutoCommit()){
				conn.rollback();
			}
		}
	}
	public static void commit() throws SQLException{
		Connection conn = connLocal.get();
		if(conn!=null){
			if(!conn.getAutoCommit()){
				conn.commit();
			}
		}
		conn = cfgConnLocal.get();
		if(conn!=null){
			if(!conn.getAutoCommit()){
				conn.commit();
			}
		}
		conn = dynamicConnLocal.get();
		if(conn!=null){
			if(!conn.getAutoCommit()){
				conn.commit();
			}
		}
	}
	/**
	 * 释放连接资源
	 * @throws SQLException
	 */
	public static void releaseConnection() throws SQLException{
		autoCommit.remove();
		Connection conn = connLocal.get();
		if(conn!=null){
			connLocal.remove();
			conn.close();
		}
		conn = cfgConnLocal.get();
		if(conn!=null){
			cfgConnLocal.remove();
			conn.close();
		}
		conn = dynamicConnLocal.get();
		if(conn != null){
			dynamicConnLocal.remove();
			conn.close();
		}
	}
	public static void setTest(boolean test) {
		DataSourceUtils.test = test;
	}
	
}
