package com.ks.access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ks.access.mapper.RowMapper;
import com.ks.util.PackageUtil;

public class DBBeanRowMapperManager {

	public static final String TABLENAME = "$tablename";
	public static final String FIELDNAME = "$fieldname";
//	/**Result read*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, RowMapper> mapperCache = new HashMap<Class, RowMapper>();
//	/**更新语句*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, String> updateSqlCache = new HashMap<Class, String>();
//	/**更新语句参数*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, DBBeanParamMethod> updateParamMethodCache = new HashMap<Class, DBBeanParamMethod>();
//	/**插入语句*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, String> insertSqlCache = new HashMap<Class, String>();
//	/**插入语句参数*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, DBBeanParamMethod> insertParamMethodCache = new HashMap<Class, DBBeanParamMethod>();
//	/**删除语句*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, String> deleteSqlCache = new HashMap<Class, String>();
//	/**删除语句参数*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, DBBeanParamMethod> deleteParamMethodCache = new HashMap<Class, DBBeanParamMethod>();
//	/**查询语句*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, String> selectSqlCache = new HashMap<Class, String>();
//	/**查询语句参数*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, DBBeanParamMethod> selectParamMethodCache = new HashMap<Class, DBBeanParamMethod>();
//	
//	/**动态语句*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, Map<String, String>> dynamicSqlCache = new HashMap<Class, Map<String,String>>();
//	/**动态语句参数*/
//	@SuppressWarnings("rawtypes")
//	public static Map<Class, Map<String, DBBeanParamMethod>> dynamicParamMethodCache = new HashMap<Class, Map<String, DBBeanParamMethod>>();
	
	@SuppressWarnings("rawtypes")
	public static Map<Class, DBBeanSqlTemplate> sqlTemplateCache = new HashMap<Class, DBBeanSqlTemplate>();
	
	/**DB Bean映射表*/
	@SuppressWarnings("rawtypes")
	public static Map<Class, DBBeanTable> tableCache = new HashMap<Class, DBBeanTable>();
	
	@SuppressWarnings("rawtypes")
	public static void init(String sql_template_packagename, String table_packagename) throws Exception{
		if(sql_template_packagename != null){
			List<String> templates = PackageUtil.getClassName(sql_template_packagename, true);
			for(String template : templates){
				if(template.endsWith("DBBeanSqlTemplate")){
					DBBeanSqlTemplate obj = (DBBeanSqlTemplate)Class.forName(template).newInstance();
					sqlTemplateCache.put(obj.getClazz(), obj);
				}
			}
		}
		List<String> tables = PackageUtil.getClassName(table_packagename, true);
		for(String table : tables){
			if(table.endsWith("Table")){
				DBBeanTable obj = (DBBeanTable)Class.forName(table).newInstance();
				tableCache.put(obj.getClazz(), obj);
			}
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T>RowMapper<T> getRowMapper(Class<T> clazz){
		DBBeanSqlTemplate template = sqlTemplateCache.get(clazz);
		RowMapper<T> rowMapper = null;
		if(template == null){
			DBBeanTable obj = tableCache.get(clazz);
			if(obj != null){
				rowMapper = obj.getRowMapper();
			}
		}else{
			rowMapper = (RowMapper<T>)template.getMapper();
		}
		return rowMapper;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getDynamicSql(Class clazz, String name, String tablename){
		String sql = getDynamicSql(clazz, name);
		sql = sql.replace(TABLENAME, tablename);
		return sql;
	}
	
	@SuppressWarnings("rawtypes")
	public static String getDynamicSql(Class clazz, String name){
		DBBeanSqlTemplate template = sqlTemplateCache.get(clazz);
		if(template == null){
			System.err.println("找不到[" + clazz.getName() + "]相关DBBeanSqlTemplate, 请重新配置DBBeanMapper.properties并生成");
		}
		String sql = template.getSql(name);
		if(sql == null){
			System.err.println("找不到方法[" + name + "], 请确认DynamicSqlMapper.properties已配置DAO类并成功生成");
		}
		return template.getSql(name);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T>DBBeanParamMethod<T> getDynamicParamMethod(Class<T> clazz, String name){
		DBBeanSqlTemplate template = sqlTemplateCache.get(clazz);
		return template.getParamMethod(name);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Object> getDynamicParams(Class clazz, String name, Object object){
		return getDynamicParamMethod(clazz, name).getParams(object);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static DBBeanSqlSet getDynamicUpdateDBBeanSqlSet(Class clazz, String name, Object object, SQLOpt opt){
		DBBeanTable table = tableCache.get(clazz);
		String sql = getDynamicSql(clazz, name);
		List<Object> params = getDynamicParams(clazz, name, object);
		int len = params.size();
		StringBuffer fsql = new StringBuffer();
		for(Map.Entry<String, Byte> entry : opt.getfieldOpts().entrySet()){
			if(entry.getValue() == SQLOpt.EQUAL){
				if(fsql.length() > 0){
					fsql.append(",");
				}
				fsql.append(entry.getKey()).append("=").append("?");
				params.add(params.size() - len, table.getDBFieldValue(object, entry.getKey()));
			}else if(entry.getValue() == SQLOpt.PULS){
				if(fsql.length() > 0){
					fsql.append(",");
				}
				fsql.append(entry.getKey()).append("=").append(entry.getKey()).append("+?");
				params.add(params.size() - len, table.getDBFieldValue(object, entry.getKey()));
			}else if(entry.getValue() == SQLOpt.MINUS){
				if(fsql.length() > 0){
					fsql.append(",");
				}
				fsql.append(entry.getKey()).append("=").append(entry.getKey()).append("-?");
				params.add(params.size() - len, table.getDBFieldValue(object, entry.getKey()));
			}else if(entry.getValue() == SQLOpt.MULTIPLY){
				if(fsql.length() > 0){
					fsql.append(",");
				}
				fsql.append(entry.getKey()).append("=").append(entry.getKey()).append("*?");
				params.add(params.size() - len, table.getDBFieldValue(object, entry.getKey()));
			}else if(entry.getValue() == SQLOpt.DIVIDED){
				if(fsql.length() > 0){
					fsql.append(",");
				}
				fsql.append(entry.getKey()).append("=").append(entry.getKey()).append("/?");
				params.add(params.size() - len, table.getDBFieldValue(object, entry.getKey()));
			}
		}
		sql = sql.replace(FIELDNAME, fsql.toString());
		return new DBBeanSqlSet(sql, params);
	}
	
}
