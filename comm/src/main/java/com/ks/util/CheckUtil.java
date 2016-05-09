package com.ks.util;

import java.util.List;

/**
 * 校验工具
 * @author ks
 */
public final class CheckUtil {
	/**
	 * list中是否有重复值
	 * @param list 要校验的list
	 * @return 有则返回true，没有为false
	 */
	public static boolean isRepeatlist(List<?> list){
		for(Object id : list){
			int count = 0;
			for(Object x : list){
				if(id.equals(x)){
					count++;
				}
			}
			if(count>1){
				return true;
			}
		}
		return false;
	}
}
