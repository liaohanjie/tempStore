package com.ks.util.collect;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
/**
 * 
 * @author living.li
 * @date  2014年4月24日
 * @param <K>
 * @param <V>
 * MultiHashMap 一个key对应多个元素 value 为一个set
 * 线程安全的
 *  
 */
public class ConcurrentMultiHashMap<K, V> {
	private final ConcurrentHashMap<K, Set<V>> map;

	public ConcurrentMultiHashMap() {
		map = new ConcurrentHashMap<>();
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Set<V> get(K key) {
		return map.get(key);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(K key, V value) {
		Set<V> set = map.get(key);
		if (set == null) {
			map.put(key, new ConcurrentSkipListSet<V>());
		}
		int size = set.size();
		set.add(value);
		return size < set.size();
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public Set<V> remove(K key) {
		return map.remove(key);
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean remove(K key, V value) {
		Set<V> set = map.get(key);
		if (set != null) {
			return set.remove(value);
		}
		return false;
	}
}
