package com.ks.access;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 配置表
 * @author zck
 *
 */
public class GameCfgDAOTemplate extends GameDAOTemplate {
	/**
	 * 获取连接
	 * @return
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException{
		return DataSourceUtils.getCfgConnection();
	}
}
