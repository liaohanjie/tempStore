package com.ks.cache;
/**
 * 
 * @author ks
 *
 */
public interface JedisRowMapper<T> {
	
	public T rowMapper(JedisResultSet jrs);
}
                                                  