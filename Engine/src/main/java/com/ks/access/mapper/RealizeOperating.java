package com.ks.access.mapper;

import java.sql.Connection;

public interface RealizeOperating<T> {

	/**
	 * 需要你自己实现的接口
	 * GenerateKeyUtil中提供了基本类型的实现，
	 * 如果需要其他的实现的话你就需要自己实现这个接口
	 * */
	T doInYourself(Connection conn);
}
