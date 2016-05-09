package com.ks.logic.utils;

import java.io.File;

import com.ks.util.PropertyBaseUtils;

/**
 * 属性工具
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年11月12日
 */
public class PropertyUtils extends PropertyBaseUtils {
	
	/** 系统配置属性 */
	public static PropertyResource SYS_CONFIG = new PropertyResource("conf" + File.separator + "SysConfig.properties");
}
