package com.ks.access.mapper.impl;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.ks.access.GameDaoException;
import com.ks.access.mapper.RowMapper;

public class RowMapperImpl<T> implements RowMapper<T> {
	
	private Class<T> clazz;
	
	public RowMapperImpl(Class<T> clazz){
		this.clazz = clazz;
	}
	
	@Override
	public T rowMapper(ResultSet rs) throws GameDaoException {
		return this.rowMapper(clazz, rs);
	}

	public T rowMapper(Class<T> clazz,ResultSet rs) throws GameDaoException {
		  if (rs == null) {
	            return null;
	        }
	        T object = null;
	        try {
	            // 通过默认构造方法创建一个新的对象
	            object = clazz.newInstance();
	            Method[] ms = clazz.getMethods();
	            String[] colNames = getColNames(rs);
	            // 获得对象的所有属性
	            for (int i = 0; i < colNames.length; i++) {
					String colName = colNames[i];
					String[] names = colName.split("_");
					String newName="";
					for(String s : names){
						newName += s.substring(0, 1).toUpperCase()+s.substring(1);
					}
					String methodName = "set" + newName;
					for (Method m : ms) {
						if (methodName.equals(m.getName())) {
							m.invoke(object, rs.getObject(colName));
							break;
						}
					}
				}
	        } catch (Exception e) {
	            object = null;
	            throw new GameDaoException("映射成为对象时出错",e);
	        }

	        return object;
	}
	
	private String[] getColNames(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		String[] colNames = new String[count];
		for (int i = 1; i <= count; i++) {
			colNames[i - 1] = rsmd.getColumnLabel(i);
		}
		return colNames;
	}
}
