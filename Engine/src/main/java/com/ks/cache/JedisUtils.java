package com.ks.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

/**
 * 
 * @author ks
 *
 */
public class JedisUtils {
	/**读取链接*/
	private static final ThreadLocal<Jedis> readJedis = new ThreadLocal<Jedis>();
	/**写入链接*/
	private static final ThreadLocal<Jedis> writeJedis = new ThreadLocal<Jedis>();
	/**写入批量操作*/
	private static final ThreadLocal<Transaction> writePipe = new ThreadLocal<Transaction>();

	/**写入缓存链接*/
	private static final ThreadLocal<Jedis> writeCacheJedis = new ThreadLocal<Jedis>();
	/**写入缓存批量操作*/
	private static final ThreadLocal<Transaction> writeCachePipe = new ThreadLocal<Transaction>();
	/**写入缓存标志*/
	private static final ThreadLocal<Boolean> writeCacheSign = new ThreadLocal<Boolean>();
	
	public static Jedis getReadJedis(){
		Jedis jedis = readJedis.get();
		if(jedis == null){
			jedis = JedisFactory.getJedis();
			readJedis.set(jedis);
		}
		return jedis;
	}
	
	public static Transaction getTransaction(){
		Boolean sign = writeCacheSign.get();
		if(sign == null || !sign){
			Jedis jedis = writeJedis.get();
			Transaction tx = writePipe.get();
			if(jedis == null){
				jedis = JedisFactory.getJedis();
				writeJedis.set(jedis);
				tx = jedis.multi();
				writePipe.set(tx);
			}
			return tx;
		}else{
			Jedis jedis = writeCacheJedis.get();
			Transaction tx = writeCachePipe.get();
			if(jedis == null){
				jedis = JedisFactory.getJedis();
				writeCacheJedis.set(jedis);
				tx = jedis.multi();
				writeCachePipe.set(tx);
			}
			return tx;
		}
	}
	
	public static void discard(){
		Transaction tx = writePipe.get();
		if(tx!=null){
			tx.discard();
		}
	}
	
	public static void returnJedis(){
		Jedis reaJedis = readJedis.get();
		Jedis writJedis = writeJedis.get();
		Jedis writCacheJedis = writeCacheJedis.get();
		if(reaJedis!=null){
			readJedis.remove();
			JedisFactory.returnJedis(reaJedis);
		}
		if(writJedis!=null){
			writeJedis.remove();
			writePipe.remove();
			JedisFactory.returnJedis(writJedis);
		}
		if(writCacheJedis != null){
			writeCacheJedis.remove();
			writeCachePipe.remove();
			JedisFactory.returnJedis(writCacheJedis);
		}
		writeCacheSign.remove();
	}
	
	public static void setCacheSign(){
		writeCacheSign.set(true);
	}
	
	public static void clearCacheSign(){
		writeCacheSign.remove();
	}
	
	public static void exec(){
		Transaction tx = writePipe.get();
		if(tx!=null){
			tx.exec();
		}
	}
	
	public static void execCache(){
		Transaction tx = writeCachePipe.get();
		if(tx!=null){
			tx.exec();
		}
		writeCachePipe.remove();
	}
}
