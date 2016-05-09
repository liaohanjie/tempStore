package com.ks.util.collect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class MultiListMap<K, V> {
	private final Map<K, List<V>> map;

	public MultiListMap() {
		map = new HashMap<>();
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public List<V> get(K key) {
		return map.get(key);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(K key, V value) {
		List<V> list = map.get(key);
		if (list == null) {
			list=new  ArrayList<V>();
			map.put(key, list);
		}
		int size = list.size();
		list.add(value);
		return size < list.size();
	}
	/**
	 * 
	 * @param key
	 * @return
	 */
	public List<V> remove(K key) {
		return map.remove(key);
	}
	/**
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean remove(K key, V value) {
		List<V> list = map.get(key);
		if (list != null) {
			return list.remove(value);
		}
		return false;
	}
	public void sort(K key,Comparator<V> comp){
		List<V> list=map.get(key);
		if(list!=null){
			Collections.sort(list, comp);
		}
	}
}
