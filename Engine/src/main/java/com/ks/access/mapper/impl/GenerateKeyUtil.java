package com.ks.access.mapper.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.ks.access.GameDaoException;

public class GenerateKeyUtil {

	/**
	 * 返回int类型的主键
	 * */
	public static Integer intKey(ResultSet rs) throws GameDaoException{
		try {
			if(rs.next()){
				return rs.getInt(1);
			}else{
				throw new GameDaoException("没有获得主键");
			}
		} catch (SQLException e) {
			throw new GameDaoException("获得主键时出错",e);
		}
	}
	
	/**
	 * 返回long类型的主键
	 * */
	public static Long longKey(ResultSet rs){
		try {
			if(rs.next()){
				return rs.getLong(1);
			}else{
				throw new GameDaoException("没有获得主键");
			}
		} catch (SQLException e) {
			throw new GameDaoException("获得主键时出错",e);
		}
	}
	
	/**
	 * 返回Short类型的主键
	 * */
	public static Short shortKey(ResultSet rs){
		try {
			if(rs.next()){
				return rs.getShort(1);
			}else{
				throw new GameDaoException("没有获得主键");
			}
		} catch (SQLException e) {
			throw new GameDaoException("获得主键时出错",e);
		}
	}
	
	/**
	 * 返回String类型的主键
	 * */
	public static String StringKey(ResultSet rs){
		try {
			if(rs.next()){
				return rs.getString(1);
			}else{
				throw new GameDaoException("没有获得主键");
			}
		} catch (SQLException e) {
			throw new GameDaoException("获得主键时出错",e);
		}
	}
}
