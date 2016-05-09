package com.ks.cache;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Jedis工厂 
 * @author ks
 *
 */
public final class JedisFactory {
	private static String host;
	private static int port;
	private static String password;
	private static JedisPool pool;
	public static void init(){
		JedisPoolConfig config = new JedisPoolConfig();
		Properties properties = new Properties();
		try(FileInputStream fis = new FileInputStream(new File("conf"+File.separatorChar+"JedisConfig.properties"))) {
			properties.load(fis);
		} catch (Exception e) {
			System.exit(-1);
		} 
		
		host = properties.getProperty("host");
		port = Integer.parseInt(properties.getProperty("port"));
		password = properties.getProperty("password");
		
		config.setMaxActive(Runtime.getRuntime().availableProcessors()*6);
		config.setMaxIdle(Runtime.getRuntime().availableProcessors()*6);
		config.setMaxWait(1000);
		config.setTestOnBorrow(true);
		config.setTestOnReturn(true);
		
		pool = new JedisPool(config, host, port, 2000, password);
		
		List<Jedis> jedises = new ArrayList<>();
		for(int i=0;i<config.getMaxActive();i++){
			Jedis jedis = getJedis();
			jedis.ping();
			jedises.add(jedis);
		}
		for(Jedis jedis : jedises){
			returnJedis(jedis);
		}
	}
	
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	public static void returnJedis(Jedis resource){
		pool.returnResource(resource);
	}
}
