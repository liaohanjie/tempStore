/**
 * 
 */
package com.ks.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author living.li
 * @date  2014年9月2日 上午10:54:25
 *
 *
 */
public class LoggerUtil {
	
	public static String traceObject(StringBuffer buff,Object o){
		return o.toString();
	}
	public static String traceCollection(Collection<?> c){
		StringBuffer buff=new StringBuffer("size"+c.size());
		int i=0;
		for(Object o : c){
			buff.append("\r  Collenction[").append(i).append("] : ").append(o).append('\n');
			i++;
		}
		return buff.toString();
	}
	public static String traceMap(Map<?,?> m){
		StringBuffer buff=new StringBuffer("[size:"+m.size()+"]\r[");
		for(Entry<?, ?> o : m.entrySet()){
			buff.append("\r  Map[").append(o.getKey()).append(": ").append(o).append("]\n");
		}
		return buff.toString();
	}
	
	public static String traceArray(Object o){
		int size=Array.getLength(o);
		StringBuffer buff=new StringBuffer("[size:"+size+"]\r[");
		for(int i=0;i<size;i++){
			buff.append("\r  Array[").append(i).append("]:").append(Array.get(o, i));
		}
		buff.append("\n]");
		return buff.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static String trace( Object o){
		StringBuffer buff=new StringBuffer();
		if(o==null){
			buff.append("[NULL]");
		}
		else if(o instanceof Collection){
			int i=0;
			Collection<?> c=(Collection)o;
			buff.append("\r\n[size:"+c.size()+"]\r\n");
			
			buff.append("[");
			for(Object obj : c){
				buff.append("\r\n  Collenction[").append(i).append("] : ").append(obj.toString()).append("\r\n");
				i++;
			}
			buff.append("]");
		}else if( o instanceof Map){
			Map<?,?> m=(Map)o;
			buff.append("\r\n[size:"+m.size()+"]\r\n");
			buff.append("[");
			for(Entry<?, ?> entry : m.entrySet()){
				buff.append("\r\n  Map[").append(entry.getKey()).append(": ").append(entry.getValue().toString()).append("]\r\n");
			}
			buff.append("]");
		}else if(o.getClass().isArray()){
			int size=Array.getLength(o);
			buff.append("\r\n[size:"+size+"]\r\n");
			for(int i=0;i<size;i++){
				buff.append("\r  Array[").append(i).append(":").append(Array.get(o, i).toString()).append("]\r\n");
			}
			buff.append("]");
		} else {
			buff.append("\r\n[object:"+o.getClass().getName()+"]\r\n");
			buff.append("[");
			buff.append(o.toString()).append("\r\n");
			buff.append("]");
		}
		return buff.toString();
	}
	public static void main(String[] args) {
		Map<String, Object> o=new HashMap<>();
		o.put("data", "111");
		System.out.println(trace(o));
	}
}
