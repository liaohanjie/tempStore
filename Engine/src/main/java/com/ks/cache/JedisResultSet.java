package com.ks.cache;

import java.util.Date;
import java.util.Map;

/**
 * jedis 结果集
 * @author ks
 *
 */
public class JedisResultSet {
	
	private Map<String,String> hash;

	public JedisResultSet(Map<String, String> hash) {
		this.hash = hash;
	}
	
	protected Map<String,String> getHash(){
		return hash;
	}
	
	/**
	 * 获得int类型值
	 * @param key 键
	 * @return 如果key不存在则返回0，否则返回key对应的值。
	 */
	public int getInt(String key){
		if(hash.get(key)==null){
			return 0;
		}
		return Integer.parseInt(hash.get(key));
	}
	
	/**
	 * 获得byte类型值
	 * @param key 键
	 * @return 如果key不存在则返回0，否则返回key对应的值。
	 */
	public byte getByte(String key){
		if(hash.get(key)==null){
			return 0;
		}
		return Byte.parseByte(hash.get(key));
	}
	
	/**
	 * 获取short类型值
	 * @param key 键
	 * @return 如果key不存在则返回0，否则返回key对应的值。
	 */
	public short getShort(String key){
		if(hash.get(key)==null){
			return 0;
		}
		return Short.parseShort(hash.get(key));
	}
	/**
	 * 获取long类型值
	 * @param key 键
	 * @return 如果key不存在则返回0，否则返回key对应的值。
	 */
	public long getLong(String key){
		if(hash.get(key)==null){
			return 0;
		}
		return Long.parseLong(hash.get(key));
	}
	/**
	 * 获取boolean类型值
	 * @param key 键
	 * @return 如果key不存在则返回false，否则返回key对应的值。
	 */
	public boolean getBoolean(String key){
		if(hash.get(key)==null){
			return false;
		}
		return Boolean.parseBoolean(hash.get(key));
	}
	/**
	 * 获取Date类型值
	 * @param key 键
	 * @return 如果key不存在则返回null，否则返回key对应的值。
	 */
	public Date getDate(String key){
		if(hash.get(key)==null || hash.get(key).equals("") || hash.get(key).equals("null")){
			return null;
		}
		return new Date(Long.parseLong(hash.get(key)));
	}
	/**
	 * 获取String类型值
	 * @param key 键
	 * @return 如果key不存在则返回null，否则返回key对应的值。
	 */
	public String getString(String key){
		if(hash.get(key)==null){
			return null;
		}
		return hash.get(key);
	}
	/**
	 * 获取double类型值
	 * @param key 键
	 * @return 如果key不存在则返回0，否则返回key对应的值。
	 */
	public double getDouble(String key){
		if(hash.get(key)==null){
			return 0;
		}
		return Double.parseDouble(hash.get(key));
	}
	/**
	 * 获取float类型值
	 * @param key 键
	 * @return 如果key不存在则返回0，否则返回key对应的值。
	 */
	public float getFloat(String key){
		if(hash.get(key)==null){
			return 0;
		}
		return Float.parseFloat(hash.get(key));
	}
	/**
	 * 获取int类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public int getInt(String key,int defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return Integer.parseInt(hash.get(key));
	}
	/**
	 * 获取byte类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public byte getByte(String key,byte defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return Byte.parseByte(hash.get(key));
	}
	/**
	 * 获取short类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public short getShort(String key,short defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return Short.parseShort(hash.get(key));
	}
	/**
	 * 获取long类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public long getLong(String key,long defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return Long.parseLong(hash.get(key));
	}
	/**
	 * 获取boolean类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public boolean getBoolean(String key,boolean defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return Boolean.parseBoolean(hash.get(key));
	}
	/**
	 * 获取Date类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public Date getDate(String key,Date defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return new Date(getLong(key));
	}
	/**
	 * 获取String类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public String getString(String key,String defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return hash.get(key);
	}
	/**
	 * 获取double类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public double getDouble(String key,double defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return Double.parseDouble(hash.get(key));
	}
	/**
	 * 获取float类型值
	 * @param key 键
	 * @param defVal 默认值
	 * @return 如果key不存在则返回defVal，否则返回key对应的值。
	 */
	public float getFloat(String key,float defVal){
		if(hash.get(key)==null){
			return defVal;
		}
		return Float.parseFloat(hash.get(key));
	}
	
}
