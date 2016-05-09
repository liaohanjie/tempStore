package com.ks.util.collect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
/**
 * 
 * @author living.li
 * @date  2014年4月24日
 * @param <K>
 * @param <V>
 * MultiHashMap 一个key对应多个值的map  value为一个set
 * 线程不安全的
 *  
 */
public class MultiHashMap<K, V> {
	private final Map<K, Set<V>> map;

	public MultiHashMap() {
		map = new HashMap<>();
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
			map.put(key, new HashSet<V>());
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
