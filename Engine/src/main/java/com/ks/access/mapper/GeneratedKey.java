package com.ks.access.mapper;

import java.io.Serializable;
import java.sql.ResultSet;

public interface GeneratedKey<ID extends Serializable> {
	/**
	 * 获得插入后的主键
	 * GenerateKeyUtil 提供了基本的数据类型主键
	 * 如果你有其他需求可以时间这个接口，用一个匿名的内部类就可以了
	 * @param rs 传给你的结果集
	 * @return ID 需要返回的id 这个id必须是实现了序列化接口的类
	 * */
	ID getGeneratedKey(ResultSet rs);
	
}
