package com.ks.access;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.ks.util.AnnotationBeanUtils;

/**
 * 生成DB bean 模版
 * @author zck
 *
 */
public class DBBeanSqlTemplateUtil {
	public static void createDBBeanSqlTemplates(String file, String daofile, String path, String packagename) throws Exception{
		FileInputStream fis = new FileInputStream(new File(file));
		FileInputStream daofis = new FileInputStream(new File(daofile));
		createDBBeanSqlTemplates(fis, daofis, path, packagename);
	}
	
	@SuppressWarnings("rawtypes")
	public static void createDBBeanSqlTemplates(InputStream stream, InputStream daostream, String path, String packagename) throws Exception{
//		FileInputStream fis = new FileInputStream(new File(file));
		Properties properties = new Properties();
		try{
			properties.load(stream);
		}finally{
			stream.close();
		}
//		FileInputStream daofis = new FileInputStream(new File(daofile));
		Properties daoproperties = new Properties();
		try{
			daoproperties.load(daostream);
		}finally{
			daostream.close();
		}
		for(Object key : properties.keySet()){
			String keystr = key.toString();
			String classname = properties.getProperty(keystr);
			Class clazz = Class.forName(classname);
			Object obj = clazz.newInstance();
			List<Field> fields = AnnotationBeanUtils.getDBFields(obj);
			StringBuffer buffer = new StringBuffer();//类定义

			String templateclassname = keystr + "DBBeanSqlTemplate";
			System.out.println("create " + templateclassname);
			
			StringBuffer tbuffer = new StringBuffer();
			tbuffer.append("package " + packagename + ";\n");
			
			String newclassname = "DBRowMapper_" + keystr;
			DBBeanSet bset = obj.getClass().getAnnotation(DBBeanSet.class);
			if(bset != null){
				for(String im : bset.imports()){
					tbuffer.append("import " + im + ";\n");
				}
			}
//			buffer.append("public final class " + newclassname + " implements com.ks.access.mapper.RowMapper" + "<"+ classname + ">{\n");
//			buffer.append("@Override\n");
//			buffer.append("public " + classname + " rowMapper(java.sql.ResultSet rs) throws java.sql.SQLException{\n");
//			buffer.append(classname + " bean =" + " new " + classname + "();\n");
//			for(Field f : fields){
//				String rsstr = "";
//				String fname = f.getName();
//				DBFieldSet fset = f.getAnnotation(DBFieldSet.class);
//				if(fset.parseJavaField().length() > 0){
//					rsstr = fset.parseJavaField();
//				}else{
//					if(f.getGenericType() == byte.class){
//						rsstr = "rs.getByte(\"" + fset.dbfname() + "\")";
//					}else if(f.getGenericType() == int.class){
//						rsstr = "rs.getInt(\"" + fset.dbfname() + "\")";
//					}else if(f.getGenericType() == long.class){
//						rsstr = "rs.getLong(\"" + fset.dbfname() + "\")";
//					}else if(f.getGenericType() == double.class){
//						rsstr = "rs.getDouble(\"" + fset.dbfname() + "\")";
//					}else if(f.getGenericType() == float.class){
//						rsstr = "rs.getFloat(\"" + fset.dbfname() + "\")";
//					}else if(f.getGenericType() == boolean.class){
//						rsstr = "rs.getBoolean(\"" + fset.dbfname() + "\")";
//					}else if(f.getGenericType() == String.class){
//						rsstr = "rs.getString(\"" + fset.dbfname() + "\")";
//					}else if(f.getGenericType() == Date.class){
//						rsstr = "rs.getTimestamp(\"" + fset.dbfname() + "\")";
//					}
//				}
//				buffer.append("bean.set" + fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) + "(" + rsstr + ");\n");
//			}
//			buffer.append("return bean;\n");
//			buffer.append("}\n");
//			buffer.append("}\n");
			Map<String, Map<String, String>> daomethodmap = new HashMap<String, Map<String,String>>();
			for(Object daokey : daoproperties.keySet()){
				String daokeystr = daokey.toString();
				String daoclassname = daoproperties.getProperty(daokeystr);
				Class daoclazz = Class.forName(daoclassname);
				List<Method> methods = AnnotationBeanUtils.getDBSqlMethod(daoclazz, clazz);
				for(Method method : methods){
					Map<String, Map<String, String>> map  = createDynamicSql(method, clazz);
					for(String mkey : map.keySet()){
						if(daomethodmap.containsKey(mkey)){
							throw new Exception("DAO 已经包含 dynamic key[" + mkey + "]!");
						}
					}
					daomethodmap.putAll(map);
				}
			}
			try{
				tbuffer.append("public final class " + templateclassname + " extends com.ks.access.DBBeanSqlTemplate" + "<"+ classname + ">{\n");
				tbuffer.append("public " + templateclassname + "(){\n");
				tbuffer.append("super();\n");
				tbuffer.append("clazz=" + classname + ".class;\n");
				tbuffer.append("mapper = new com.ks.table." + keystr + "Table." + newclassname + "();\n");
				for(Map.Entry<String, Map<String, String>> entry : daomethodmap.entrySet()){
					tbuffer.append("sqlMap.put(\"" + entry.getKey() + "\",\"" + entry.getValue().get("sql") + "\");\n");
				}
				for(Map.Entry<String, Map<String, String>> entry : daomethodmap.entrySet()){
					tbuffer.append("paramMethodMap.put(\"" + entry.getKey() + "\", new " + entry.getValue().get("paramMethodClass") + "());\n");
				}
				tbuffer.append("}\n");
				tbuffer.append(buffer.toString());
				for(Map.Entry<String, Map<String, String>> entry : daomethodmap.entrySet()){
					tbuffer.append(entry.getValue().get("paramMethod"));
				}
				tbuffer.append("}");
				String npath = packagename.replaceAll("\\.", "/");
//				File f = new File(System.getProperty("user.dir") + File.separator + path + File.separator + npath + File.separator + templateclassname + ".java");
				File f = new File(path + File.separator + npath + File.separator + templateclassname + ".java");
				FileWriter fw = new FileWriter(f);
				fw.write(tbuffer.toString());
				fw.close();
			}catch (Exception e) {
				throw e;
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Map<String, String>> createDynamicSql(Method method, Class dbbean) throws Exception{
		Map<String, Map<String, String>> map = new HashMap<String, Map<String,String>>();
		DBBeanSetSqlSetList set = method.getAnnotation(DBBeanSetSqlSetList.class);
		for(DBBeanSetSelectSqlSet selectSet : set.selects()){
			if(selectSet.dbbean() == dbbean){
				map.put(selectSet.name(), createDynamicSelectSql(selectSet));
			}
		}
		for(DBBeanSetDeleteSqlSet deleteSet : set.deletes()){
			if(deleteSet.dbbean() == dbbean){
				map.put(deleteSet.name(), createDynamicDeleteSql(deleteSet));
			}
		}
		for(DBBeanSetUpdateSqlSet updateSet : set.updates()){
			if(updateSet.dbbean() == dbbean){
				map.put(updateSet.name(), createDynamicUpdateSql(updateSet));
			}
		}
		for(DBBeanSetInsertSqlSet insertSet : set.inserts()){
			if(insertSet.dbbean() == dbbean){
				map.put(insertSet.name(), createDynamicInsertSql(insertSet));
			}
		}
		return map;
	}

	/**
	 * 创建动态select sql
	 * @param set
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> createDynamicSelectSql(DBBeanSetSelectSqlSet set) throws Exception{
		Class clazz = set.dbbean();
		Object obj = clazz.newInstance();
		DBBeanSet bset = (DBBeanSet) clazz.getAnnotation(DBBeanSet.class);
		List<Field> fields = AnnotationBeanUtils.getDBFields(obj);
		String tablename = DBBeanRowMapperManager.TABLENAME;
		if(bset != null){
			if(bset.tablename().length() > 0){
				tablename = bset.tablename();
			}
		}
		String classname = clazz.getName();
		String paramclassname = "DynamicSelect_" + set.name();
		System.out.println("create " + paramclassname);
		StringBuffer buffer = new StringBuffer();
		StringBuffer sqlBuffer = new StringBuffer();
		buffer.append("public final class " + paramclassname + " implements com.ks.access.DBBeanParamMethod" + "<" + classname + ">{\n");
		buffer.append("@Override\n");
		buffer.append("public java.util.List<Object> getParams(" + classname + " bean){\n");
		buffer.append("java.util.List<Object> list = new java.util.ArrayList<Object>();\n");
		sqlBuffer.append("select ");
		if(set.fields().length > 0){
			for(String field : set.fields()){
				sqlBuffer.append(parseDBFieldName(field)).append(",");
			}
			sqlBuffer.setLength(sqlBuffer.length() - 1);
		}else{
			sqlBuffer.append("*");
		}
		sqlBuffer.append(" from ").append(tablename);
		if(set.wheres().length > 0){
			sqlBuffer.append(" where ");
			for(int i = 0; i < set.wheres().length; i++){
				String where = set.wheres()[i];
				if(i > 0){
					sqlBuffer.append(" and ");
				}
				sqlBuffer.append(parseDBFieldName(where)).append("=?");
				Field f = AnnotationBeanUtils.getDBField(where, fields);
				String fname = f.getName();
				DBFieldSet fset = f.getAnnotation(DBFieldSet.class);
				String irsstr = "bean.get";
				if(fset.parseDBField().length() > 0){
					irsstr = fset.parseDBField();
				}else{
					if(f.getGenericType() == boolean.class){
						irsstr = "bean.is";
					}
					irsstr += fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) +  "()";
				}
				buffer.append("list.add(" + irsstr + ");\n");
			}
		}
		if(set.oderby().length() > 0){
			sqlBuffer.append(" order by ").append(set.oderby());
		}
		if(set.limit().length > 0){
			sqlBuffer.append(" limit ");
			for(int i = 0; i < set.limit().length; i++){
				if(i > 0){
					sqlBuffer.append(",");
				}
				sqlBuffer.append(set.limit()[i]);
			}
		}
		if(set.forupdate()){
			sqlBuffer.append(" for update");
		}
		buffer.append("return list;\n");
		buffer.append("}\n");
		buffer.append("}\n");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", set.name());
		map.put("sql", sqlBuffer.toString());
		map.put("paramMethodClass", paramclassname);
		map.put("paramMethod", buffer.toString());
		return map;
	}
	
	/**
	 * 创建动态delete sql
	 * @param set
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> createDynamicDeleteSql(DBBeanSetDeleteSqlSet set) throws Exception{
		Class clazz = set.dbbean();
		Object obj = clazz.newInstance();
		DBBeanSet bset = (DBBeanSet) clazz.getAnnotation(DBBeanSet.class);
		List<Field> fields = AnnotationBeanUtils.getDBFields(obj);
		String tablename = DBBeanRowMapperManager.TABLENAME;
		if(bset != null){
			if(bset.tablename().length() > 0){
				tablename = bset.tablename();
			}
		}
		String classname = clazz.getName();
		String paramclassname = "DynamicDelete_"  + set.name();
		System.out.println("create " + paramclassname);
		StringBuffer buffer = new StringBuffer();
		StringBuffer sqlBuffer = new StringBuffer();
		buffer.append("public final class " + paramclassname + " implements com.ks.access.DBBeanParamMethod" + "<" + classname + ">{\n");
		buffer.append("@Override\n");
		buffer.append("public java.util.List<Object> getParams(" + classname + " bean){\n");
		buffer.append("java.util.List<Object> list = new java.util.ArrayList<Object>();\n");
		sqlBuffer.append("delete from ").append(tablename);
		if(set.wheres().length > 0){
			sqlBuffer.append(" where ");
			for(int i = 0; i < set.wheres().length; i++){
				String where = set.wheres()[i];
				if(i > 0){
					sqlBuffer.append(" and ");
				}
				sqlBuffer.append(parseDBFieldName(where)).append("=?");
				Field f = AnnotationBeanUtils.getDBField(where, fields);
				String fname = f.getName();
				DBFieldSet fset = f.getAnnotation(DBFieldSet.class);
				String irsstr = "bean.get";
				if(fset.parseDBField().length() > 0){
					irsstr = fset.parseDBField();
				}else{
					if(f.getGenericType() == boolean.class){
						irsstr = "bean.is";
					}
					irsstr += fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) +  "()";
				}
				buffer.append("list.add(" + irsstr + ");\n");
			}
		}
		buffer.append("return list;\n");
		buffer.append("}\n");
		buffer.append("}\n");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", set.name());
		map.put("sql", sqlBuffer.toString());
		map.put("paramMethodClass", paramclassname);
		map.put("paramMethod", buffer.toString());
		return map;
	}
	
	/**
	 * 创建动态update sql
	 * @param set
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> createDynamicUpdateSql(DBBeanSetUpdateSqlSet set) throws Exception{
		Class clazz = set.dbbean();
		Object obj = clazz.newInstance();
		DBBeanSet bset = (DBBeanSet) clazz.getAnnotation(DBBeanSet.class);
		List<Field> fields = AnnotationBeanUtils.getDBFields(obj);
		String tablename = DBBeanRowMapperManager.TABLENAME;
		if(bset != null){
			if(bset.tablename().length() > 0){
				tablename = bset.tablename();
			}
		}
		String classname = clazz.getName();
		String paramclassname = "DynamicUpdata_"  + set.name();
		System.out.println("create " + paramclassname);
		StringBuffer buffer = new StringBuffer();
		StringBuffer sqlBuffer = new StringBuffer();
		buffer.append("public final class " + paramclassname + " implements com.ks.access.DBBeanParamMethod" + "<" + classname + ">{\n");
		buffer.append("@Override\n");
		buffer.append("public java.util.List<Object> getParams(" + classname + " bean){\n");
		buffer.append("java.util.List<Object> list = new java.util.ArrayList<Object>();\n");
		sqlBuffer.append("update ").append(tablename).append(" set ");
		if(set.auto_fields()){
			sqlBuffer.append(DBBeanRowMapperManager.FIELDNAME);
		}else{
			if(set.fields().length > 0){
				for(String field : set.fields()){
					sqlBuffer.append(parseDBFieldName(field)).append("=?,");
					Field f = AnnotationBeanUtils.getDBField(field, fields);
					String fname = f.getName();
					DBFieldSet fset = f.getAnnotation(DBFieldSet.class);
					String irsstr = "bean.get";
					if(fset.parseDBField().length() > 0){
						irsstr = fset.parseDBField();
					}else{
						if(f.getGenericType() == boolean.class){
							irsstr = "bean.is";
						}
						irsstr += fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) +  "()";
					}
					buffer.append("list.add(" + irsstr + ");\n");
				}
			}else{
				for(Field f : fields){
					String fname = f.getName();
					DBFieldSet fset = f.getAnnotation(DBFieldSet.class);
					sqlBuffer.append(parseDBFieldName(fset.dbfname())).append("=?,");
					String irsstr = "bean.get";
					if(fset.parseDBField().length() > 0){
						irsstr = fset.parseDBField();
					}else{
						if(f.getGenericType() == boolean.class){
							irsstr = "bean.is";
						}
						irsstr += fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) +  "()";
					}
					buffer.append("list.add(" + irsstr + ");\n");
				}
			}
			sqlBuffer.setLength(sqlBuffer.length() - 1);
		}
		if(set.wheres().length > 0){
			sqlBuffer.append(" where ");
			for(int i = 0; i < set.wheres().length; i++){
				String where = set.wheres()[i];
				if(i > 0){
					sqlBuffer.append(" and ");
				}
				sqlBuffer.append(parseDBFieldName(where)).append("=?");
				Field f = AnnotationBeanUtils.getDBField(where, fields);
				String fname = f.getName();
				DBFieldSet fset = f.getAnnotation(DBFieldSet.class);
				String irsstr = "bean.get";
				if(fset.parseDBField().length() > 0){
					irsstr = fset.parseDBField();
				}else{
					if(f.getGenericType() == boolean.class){
						irsstr = "bean.is";
					}
					irsstr += fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) +  "()";
				}
				buffer.append("list.add(" + irsstr + ");\n");
			}
		}
		buffer.append("return list;\n");
		buffer.append("}\n");
		buffer.append("}\n");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", set.name());
		map.put("sql", sqlBuffer.toString());
		map.put("paramMethodClass", paramclassname);
		map.put("paramMethod", buffer.toString());
		return map;
	}
	
	/**
	 * 创建动态update sql
	 * @param set
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, String> createDynamicInsertSql(DBBeanSetInsertSqlSet set) throws Exception{
		Class clazz = set.dbbean();
		Object obj = clazz.newInstance();
		DBBeanSet bset = (DBBeanSet) clazz.getAnnotation(DBBeanSet.class);
		List<Field> fields = AnnotationBeanUtils.getDBFields(obj);
		String tablename = DBBeanRowMapperManager.TABLENAME;
		if(bset != null){
			if(bset.tablename().length() > 0){
				tablename = bset.tablename();
			}
		}
		String classname = clazz.getName();
		String paramclassname = "DynamicInsert_"  + set.name();
		System.out.println("create " + paramclassname);
		StringBuffer buffer = new StringBuffer();
		StringBuffer sqlBuffer = new StringBuffer();
		StringBuffer insertValueBuffer = new StringBuffer();
		buffer.append("public final class " + paramclassname + " implements com.ks.access.DBBeanParamMethod" + "<" + classname + ">{\n");
		buffer.append("@Override\n");
		buffer.append("public java.util.List<Object> getParams(" + classname + " bean){\n");
		buffer.append("java.util.List<Object> list = new java.util.ArrayList<Object>();\n");
		sqlBuffer.append("insert ").append(tablename).append(" (");
		for(Field f : fields){
			String fname = f.getName();
			DBFieldSet fset = f.getAnnotation(DBFieldSet.class);
			sqlBuffer.append(parseDBFieldName(fset.dbfname())).append(",");
			String irsstr = "bean.get";
			if(fset.parseDBField().length() > 0){
				irsstr = fset.parseDBField();
			}else{
				if(f.getGenericType() == boolean.class){
					irsstr = "bean.is";
				}
				irsstr += fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) +  "()";
			}
			insertValueBuffer.append("?,");
			buffer.append("list.add(" + irsstr + ");\n");
		}
		sqlBuffer.setLength(sqlBuffer.length() - 1);
		sqlBuffer.append(") values (");
		insertValueBuffer.setLength(insertValueBuffer.length() - 1);
		sqlBuffer.append(insertValueBuffer.toString()).append(")");
		buffer.append("return list;\n");
		buffer.append("}\n");
		buffer.append("}\n");
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", set.name());
		map.put("sql", sqlBuffer.toString());
		map.put("paramMethodClass", paramclassname);
		map.put("paramMethod", buffer.toString());
		return map;
	}
	
	private static String parseDBFieldName(String name){
		return "`" + name + "`";
	}
}
