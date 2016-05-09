package com.ks.util;

import java.io.File;
import java.io.FileInputStream;
import java.util.PropertyResourceBundle;

/**
 * 属性工具
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月12日
 */
public abstract class PropertyBaseUtils {
	
	public static class PropertyResource {
		private static String DEFAULT_PATH = "config.properties";
		private PropertyResourceBundle resource;
		private String filePath;
		
		public PropertyResource(){
			this.filePath = DEFAULT_PATH;
		}
		
		public PropertyResource(String filePath){
			this.filePath = filePath;
		}
		
		private PropertyResourceBundle _getResource() {
	        try {
	            if (resource == null) {
	                File file = new File(filePath);
	                resource = new PropertyResourceBundle(new FileInputStream(file));
	                
	            }
	            return resource;
	        } catch (Exception e) {
	            throw new RuntimeException("can't find " + filePath, e);
	        }
	    }
	    
	    public String get(String key) {
	        return _getResource().getString(key);
	    }
	    
	    public Short getShort(String key) {
	    	try {
	    		return Short.parseShort(_getResource().getString(key));
	    	} catch (Exception e) {}
	    	return null;
	    }
	    
	    public Integer getInt(String key, Integer defaultValue) {
	    	try {
	    		return Integer.parseInt(_getResource().getString(key));
	    	} catch (Exception e) {}
	    	return defaultValue;
	    }
	    
	    public Integer getInt(String key) {
	    	return getInt(key, null);
	    }
	    
	    public Long getLong(String key, Long defaultValue) {
	    	try {
	    		return Long.parseLong(_getResource().getString(key));
	    	} catch (Exception e) {}
	    	return defaultValue;
	    }
	    
	    public Long getLong(String key) {
	    	return getLong(key, null);
	    }
	    
	    public Boolean getBool(String key) {
	    	return getBool(key, null);
	    }
	    
	    public Boolean getBool(String key, Boolean defaultValue) {
	    	Boolean value = defaultValue;
	    	try {
	    		String reuslt = _getResource().getString(key);
	    		if (reuslt != null && (reuslt.trim().toLowerCase().equals("true") || reuslt.trim().toLowerCase().equals("yes") || reuslt.trim().toLowerCase().equals("1"))) {
	    			value = true;
	    		} else {
	    			value = false;
	    		}
	    	} catch (Exception e) {}
	    	return value;
	    }
	}
}
