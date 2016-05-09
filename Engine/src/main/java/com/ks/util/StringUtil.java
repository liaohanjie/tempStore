package com.ks.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字符串工具
 * @author ks.wu
 *
 */
public class StringUtil {
	/**
	* 将一个字符串的首字母改为大写或者小写
	*
	* @param srcString 源字符串
	* @param flag            大小写标识，ture小写，false大写
	* @return 改写后的新字符串
	*/
    public static String toLowerCaseInitial(String srcString, boolean flag) {
            StringBuilder sb = new StringBuilder();
            if (flag) {
                    sb.append(Character.toLowerCase(srcString.charAt(0)));
            } else {
                    sb.append(Character.toUpperCase(srcString.charAt(0)));
            }
            sb.append(srcString.substring(1));
            return sb.toString();
    }
    /**
     * 将逗号分隔开的字符串转换成集合
     * @param str 字符串
     * @return 集合
     */
	public static List<Integer> stringToList(String str) {
		str=str.replaceAll("，", ",");
		String[] strs = str.split(",");
		List<Integer> list = new ArrayList<Integer>();
		if(!str.equals("")){
			for(String s : strs){
				int x = Integer.parseInt(s);
				if(x!=0){
					list.add(x);
				}
			}
		}
		return list;
	}
    /**
    * 将逗号分隔开的字符串转换成集合(包含0);
    * @param str 字符串
    * @return 集合
    */
	public static List<Integer> stringToList1(String str) {
		str=str.replaceAll("，", ",");
		String[] strs = str.split(",");
		List<Integer> list = new ArrayList<Integer>();
		if(!str.equals("")){
			for(String s : strs){
				int x = Integer.parseInt(s);
				list.add(x);
			}
		}
		return list;
	}
	/**
	 * 将集合转换成逗号隔开的字符串
	 * @param list 集合
	 * @return 逗号隔开的字符串
	 */
	public static String listToString(List<? extends Number> list){
		if(list == null || list.size() == 0){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for(Number num : list){
			sb.append(num).append(',');
		}
		sb.setLength(sb.length()-1);
		return sb.toString();
	}

	public static int[][] getHinder(String hinder) {
		if(hinder==null||"".equals(hinder)){
			return new int[][]{};
		}
		String[] hin = hinder.split("_");
		int[][] is = new int[hin.length][3];
		int i=0;
		for(String str : hin){
			String[] ss = str.split(",");
			is[i][0]=Integer.parseInt(ss[0]);
			is[i][1]=Integer.parseInt(ss[1]);
			is[i][2]=Integer.parseInt(ss[2]);
			i++;
		}
		return is;
	}
}
