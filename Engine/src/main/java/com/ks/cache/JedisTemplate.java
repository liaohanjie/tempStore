package com.ks.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import com.ks.logger.LoggerFactory;


/**
 * Jedis 操作工具
 * @author ks
 *
 */
public abstract class JedisTemplate{
	
	private static final Logger logger = LoggerFactory.get(JedisTemplate.class);
	
	protected static final String SPEET = "_";
	protected static final String LOAD = "LOAD";
	
	private static void selectDB(Jedis jedis,String key){
		jedis.select(Math.abs(key.hashCode()%16));
	}
	private static void selectDB(Transaction tx,String key){
		tx.select(Math.abs(key.hashCode()%16));
	}
	private static void selectDB(Pipeline pipe,String key){
		pipe.select(Math.abs(key.hashCode()%16));
	}
	/**
	 * 是否已经加载了缓存
	 * @param userId
	 * @return
	 */
	public boolean isLoad(Map<String, String> map){
		return map.get(LOAD) != null;
	}
	/**
	 * 是否已经加载了缓存
	 * @param userId
	 * @return
	 */
	public boolean isLoad(String key){
		return hget(key, LOAD) != null;
	}
	/**
	 * 设置过期时间
	 * @param key
	 * @param seconds
	 */
	protected final void expire(String key, int seconds){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("expire key :"+key);
			logger.debug("seconds : " + seconds);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().expire(key, seconds);
		
	}
	/**
	 * 修改数据
	 * @param key 要修改的key
	 * @param hash 要修改的字段
	 */
	protected final void hset(String key, String field, String value){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("hset key :"+key);
			logger.debug("field : " + field);
			logger.debug("value : " + value);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().hset(key, field, value);
	}
	
	/**
	 * 修改数据
	 * @param key 要修改的key
	 * @param hash 要修改的字段
	 */
	protected final void hdel(String key, String field){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("hdel key :"+key);
			logger.debug("field : " + field);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().hdel(key, field);
	}
	
	/**
	 * 修改数据
	 * @param key 要修改的key
	 * @param hash 要修改的字段
	 */
	protected final void hmset(String key,Map<String,String> hash){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("hmset key :"+key);
			logger.debug("value : " + hash);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().hmset(key, hash);
	}
	
	/**
	 * 修改数据
	 * @param key 要修改的key
	 * @param hash 要修改的字段
	 */
	protected final void hmset(String key,Map<String,String> hash,boolean checkExists){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("hmset key :"+key);
			logger.debug("value : " + hash);
		}
		selectDB(JedisUtils.getTransaction(), key);
		boolean sign = true;
		if(checkExists){
			sign = exists(key);
		}
		if(sign){
			JedisUtils.getTransaction().hmset(key, hash);
		}
	}
	
	protected final Long hlen(String key){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("hlen key :" + key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().hlen(key);
	}
	
	protected final boolean exists(String key){
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().exists(key);
	}
	
	/**
	 * 删除
	 * @param keys 要删除的key
	 */
	protected final void del(String ...keys){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			StringBuilder sb = new StringBuilder();
			sb.append("del key : \n");
			sb.append("-------------------------------\n");
			for(String key : keys){
				sb.append(key);
				sb.append('\n');
			}
			sb.append("-------------------------------");
			logger.debug(sb);
		}
		for(String key : keys){
			selectDB(JedisUtils.getTransaction(), key);
			JedisUtils.getTransaction().del(key);
		}
	}
	
	/**
	 * 删除
	 * @param keys 要删除的key
	 */
	protected final void del(byte[] key){
		JedisUtils.getTransaction().select(0);
		
		if(LoggerFactory.getLevel()==Level.DEBUG){
			StringBuilder sb = new StringBuilder();
			sb.append("del key : \n");
			sb.append("-------------------------------\n");
			sb.append(new String(key));
			sb.append('\n');
			sb.append("-------------------------------");
			logger.debug(sb);
		}
		
		JedisUtils.getTransaction().del(key);
	}
	
	/**
	 * hmget
	 * @param key 键
	 * @param jrm 映射
	 * @param fields 参数
	 * @return 表格中的对象
	 */
	protected final <T>T hmget(String key,JedisRowMapper<T> jrm,String...fields){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query for Object key : "+key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		List<String> list = JedisUtils.getReadJedis().hmget(key, fields);
		Map<String,String> hash = new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			hash.put(fields[i], list.get(i));
		}
		if(hash.size()!=0){
			return jrm.rowMapper(new JedisResultSet(hash));
		}else{
			return null;
		}
	}
	/**
	 * hmget
	 * @param key 键
	 * @param jrm 映射
	 * @param fields 参数
	 * @return 表格中的对象
	 */
	protected final Map<String, String> hmget(String key, String...fields){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query for Object key : "+key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		List<String> list = JedisUtils.getReadJedis().hmget(key, fields);
		Map<String,String> hash = new HashMap<String, String>();
		for(int i=0;i<list.size();i++){
			hash.put(fields[i], list.get(i));
		}
		return hash;
	}
	/**
	 * hgetall
	 * @param key 建
	 * @return 对象
	 */
	protected final Map<String, String> hgetAll(String key){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query for Object key : "+key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		Map<String,String> hash = JedisUtils.getReadJedis().hgetAll(key);
		return hash;
	}
	/**
	 * hget
	 * @param key 建
	 * @param field 字段键
	 * @return 对象
	 */
	protected final String hget(String key, String field){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query for Object key : "+key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().hget(key, field);
	}
	/**
	 * hgetall
	 * @param key 建
	 * @param jrm 映射
	 * @return 对象
	 */
	protected final <T>T hgetAll(String key,JedisRowMapper<T> jrm){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query for Object key : "+key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		Map<String,String> hash = JedisUtils.getReadJedis().hgetAll(key);
		if(hash==null){
			return null;
		}
		if(hash.size()!=0){
			return jrm.rowMapper(new JedisResultSet(hash));
		}else{
			return null;
		}
	}
	/**
	 * 批量操作
	 * @param <T>
	 * @param jrm
	 * @param keys
	 * @return
	 */
	protected final <T>List<T> hgetAll(JedisRowMapper<T> jrm,String...keys){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			StringBuilder sb = new StringBuilder();
			sb.append("pipe query for Object key : \n");
			sb.append("-------------------------------\n");
			for(String key : keys){
				sb.append(key);
				sb.append('\n');
			}
			sb.append("-------------------------------");
			logger.debug(sb);
		}
		List<T> list = new ArrayList<T>();
		Jedis jedis = JedisUtils.getReadJedis();
		Pipeline pipe = jedis.pipelined();
		List<Response<Map<String, String>>> resps = new ArrayList<Response<Map<String,String>>>();
		for(String key : keys){
			selectDB(pipe, key);
			resps.add(pipe.hgetAll(key));
		}
		pipe.sync();
		for(Response<Map<String, String>> resp : resps){
			Map<String,String> hash = resp.get();
			if(hash!=null&&hash.size()!=0){
				list.add(jrm.rowMapper(new JedisResultSet(resp.get())));
			}
		}
		return list;
	}
	
	/**
	 * 批量操作
	 * @param <T>
	 * @param jrm
	 * @param keys
	 * @return 
	 */
	protected final <T>List<T> hgetAll(JedisRowMapper<T> jrm,Collection<String> keys){
		return hgetAll(jrm, keys.toArray(new String[keys.size()]));
	}
	
	/**
	 * 增加list集合元素
	 * @param key
	 * @param val
	 */
	protected final void lpush(String key,String val){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("add list object key : " + key +" value : " +val);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().lpush(key, val);
	}
	/**
	 * 增加list集合元素
	 * @param key
	 * @param val
	 */
	protected final void rpush(String key,String val){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("add list object key : " + key +" value : " +val);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().rpush(key, val);
	}
	
	/**
	 * R出队
	 * @param key
	 * @param val
	 */
	protected final String rpop(String key){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("pop list object key : " +" value : ");
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().rpop(key);
	}
	
	/**
	 * 获得集合中的所有元素
	 * @param key
	 * @return 集合中的所有元素
	 */
	protected final List<String> lrange(String key){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query list object key : " + key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().lrange(key, 0, -1);
	}
	/**
	 * 获得集合中元素个数
	 * @param key 
	 * @return 集合中元素个数
	 */
	protected final int llen(String key){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("query list len object key : " + key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		Long val = JedisUtils.getReadJedis().llen(key);
		int x = val == null?0:val.intValue();
		return x;
	}
	/**
	 * 删除list中的元素
	 * @param key list key
	 * @param count 删除数量
	 * @param val 删除的元素
	 */
	protected final void lrem(String key,int count,String val){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("remove list object key : " + key);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().lrem(key, count, val);
	}
	
	/**
	 * 删除list中的元素
	 * @param key list key
	 * @param count 删除数量
	 * @param val 删除的元素
	 */
	protected final void ltim(String key,int start, int end){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("remove list object key : " + key);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().ltrim(key, start, end);
	}
	
	/**
	 * 获取普通类型值
	 * @param key 键
	 * @return 值
	 */
	protected final String get(String key){
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().get(key);
	}
	/**
	 * 设置普通值
	 * @param key 键
	 * @param value 值
	 */
	protected final void set(String key,String value){
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().set(key, value);
	}
	
	/**
	 * 设置普通值
	 * @param key 键
	 * @param value 值
	 */
	protected final byte[] get(byte[] key){
		JedisUtils.getReadJedis().select(0);
		return JedisUtils.getReadJedis().get(key);
	}
	
	/**
	 * 设置普通值
	 * @param key 键
	 * @param value 值
	 */
	protected final void set(byte[] key,byte[] value){
		JedisUtils.getTransaction().select(0);
		JedisUtils.getTransaction().set(key, value);
	}
	
	/**
	 * 增加set中的元素
	 * @param key 键
	 * @param member 值
	 */
	protected final void sadd(String key,String... member){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			StringBuilder sb = new StringBuilder();
			sb.append("add set key : " + key);
			sb.append('\n');
			sb.append("------------------------------\n");
			for(String val : member){
				sb.append(val);
				sb.append('\n');
			}
			sb.append("------------------------------");
			logger.debug(sb);
		}
		
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().sadd(key, member);
	}
	/**
	 * 获取集合中的所有元素
	 * @param key 键
	 * @return 所有元素
	 */
	protected final Set<String> smembers(String key){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("smembers key : "+key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().smembers(key);
	}
	/**
	 * 移除集合中的元素
	 * @param key 建
	 * @param member 值
	 */
	protected final void srem(String key,String... member){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			StringBuilder sb = new StringBuilder();
			sb.append("srem : " + key);
			sb.append('\n');
			sb.append("------------------------------\n");
			for(String val : member){
				sb.append(val);
				sb.append('\n');
			}
			sb.append("------------------------------");
			logger.debug(sb);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().srem(key, member);
	}
	/**
	 * 获得集合中的元素个数
	 * @param key 建
	 * @return 元素个数
	 */
	protected final int scard(String key){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("scard key : "+key);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		Long val = JedisUtils.getReadJedis().scard(key);
		int x = val == null?0:val.intValue();
		return x;
	}
	/**
	 * 获得排序集合中的顺序元素
	 * @param key 键
	 * @param min 最小值
	 * @param max 最大值
	 * @param offset 从什么位置开始
	 * @param count 取出数量
	 * @return 排序集合中的元素
	 */
	protected final Set<String> zrangeByScore(String key,double min,double max,int offset,int count){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zrangeByScore key : "+key + " min : "+min + " max : "+max + " offset : "+offset + " count : "+count);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().zrangeByScore(key, min, max, offset, count);
	}
	/**
	 * 获得排序集合中的倒序元素
	 * @param key 键
	 * @param min 最小值
	 * @param max 最大值
	 * @param offset 从什么位置开始
	 * @param count 取出数量
	 * @return 排序集合中的元素
	 */
	protected final Set<String> zrevrangeByScore(String key,double max,double min,int offset,int count){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zrevrangeByScore key : "+key + " min : "+min + " max : "+max + " offset : "+offset + " count : "+count);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().zrevrangeByScore(key, max, min, offset, count);
	}
	/**
	 * 获得顺序排名
	 * @param key
	 * @param member
	 * @return
	 */
	protected final Long zrank(String key,String member) {
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zrank key : "+key + " member : "+member);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().zrank(key, member);
	}
	/**
	 * 获得倒序排名
	 * @param key
	 * @param member
	 * @return
	 */
	protected final Long zrevrank(String key,String member){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zrevrank key : "+key + " member : "+member);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().zrevrank(key, member);
	}
	
	/**
	 * 顺序取出
	 * @param key 
	 * @param start
	 * @param end
	 * @return 
	 */
	protected final Set<String> zrange(String key,int start,int end){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zcount key : "+key + " start : "+start + " end : "+end);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().zrange(key, start, end);
	}
	/**
	 * 倒序取出
	 * @param key 
	 * @param start
	 * @param end
	 * @return 
	 */
	protected final Set<String> zrevrange(String key,int start,int end){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zrevrange key : "+key + " start : "+start + " end : "+end);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().zrevrange(key, start, end);
	} 
	/**
	 * 增加排序集合中的元素
	 * @param key 键
	 * @param score 排序键
	 * @param member 值
	 */
	protected final void zadd(String key,double score,String member){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zadd key : "+key + " score : "+ score + " member : "+member);
		}
		selectDB(JedisUtils.getTransaction(), key);
		JedisUtils.getTransaction().zadd(key, score, member);
	}
	/**
	 * 获得分数
	 * @param key
	 * @param member
	 * @return
	 */
	protected final double zscore(String key,String member) {
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zscore key : "+key + " member : "+member);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		Double d = JedisUtils.getReadJedis().zscore(key, member);
		if(d == null){
			return 0;
		}
		return d;
	}
	
	/**
	 * 获得排序集合长度
	 * @param key 键
	 * @param min 最小值
	 * @param max 最大值
	 * @return 长度
	 */
	protected final long zcount(String key , double min , double max){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("zcount key : "+key + " min : "+min + " max : "+max);
		}
		selectDB(JedisUtils.getReadJedis(), key);
		return JedisUtils.getReadJedis().zcount(key, min, max);
	}
	
	public static void setCacheSign(){
		JedisUtils.setCacheSign();
	}
	
	public static void clearCacheSign(){
		JedisUtils.clearCacheSign();
	}
}
