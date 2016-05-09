package com.ks.access;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.ks.util.AnnotationBeanUtils;

public class DBBeanTableUtil {
	/**
	 * 生成表类字段映射
	 */
	public static void createBeanTables(String file, String path, String packagename) throws Exception{
		FileInputStream fis = new FileInputStream(new File(file));
		createBeanTables(fis, path, packagename);
	}
	/**
	 * 生成表类字段映射
	 */
	public static void createBeanTables(InputStream stream, String path, String packagename) throws Exception{
		try{
			Properties properties = new Properties();
			properties.load(stream);
			for(Object key : properties.keySet()){
				String keystr = key.toString();
				String classname = properties.getProperty(keystr);
				createBeanTable(classname, path, packagename);
			}
		}finally{
			stream.close();
		}
	}
	/**
	 * 生成表类字段映射
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void createBeanTable(String classname, String path, String packagename) throws Exception{
		Class clazz = Class.forName(classname);
		Object obj = clazz.newInstance();
		StringBuffer buffer = new StringBuffer();
		String newclassname = clazz.getSimpleName() + "Table";
		List<Field> fields = AnnotationBeanUtils.getDBFields(obj);
		DBBeanSet bset = (DBBeanSet) clazz.getAnnotation(DBBeanSet.class);
		buffer.append("package " + packagename + ";\n");
		if(bset != null){
			if(bset.imports().length > 0){
				for(String im : bset.imports()){
					buffer.append("import ").append(im).append(";\n");
				}
			}
		}
		buffer.append("public final class " + newclassname + " extends com.ks.access.DBBeanTable<" + clazz.getName() + ">{\n");
		buffer.append("public static " + newclassname + " instance;\n");
		if(bset != null){
			if(bset.tablename().length() > 0){
				buffer.append("public static final String TABLE_NAME").append("=\"").append(bset.tablename()).append("\";\n");
			}
			if(bset.tablename_prefix().length() > 0){
				buffer.append("public static final String TABLE_NAME_PREFIX").append("=\"").append(bset.tablename_prefix()).append("\";\n");
			}
		}
		for(Field f : fields){
			DBFieldSet set = f.getAnnotation(DBFieldSet.class);
			buffer.append("public static final String ").append(f.getName().toUpperCase()).append("=\"").append(set.dbfname()).append("\";\n");
		}
		buffer.append("\n");
		for(Field f : fields){
			buffer.append("public static final String ").append("J_" + f.getName().toUpperCase()).append("=\"").append(f.getName()).append("\";\n");
		}
		buffer.append("\n");
		if(bset.tablename_prefix().length() > 0){
			buffer.append(bset.createtablename()).append("\n");
		}
		buffer.append("public " + newclassname + "(){\n");
		buffer.append("mapper=new DBRowMapper_" + clazz.getSimpleName() + "();\n");
		buffer.append("}\n");
		buffer.append("@Override\n");
		buffer.append("public Object getDBFieldValue(" + clazz.getName() + " bean, String fname){\n");
		for(Field f : fields){
			String fieldname = f.getName();
			buffer.append("if(").append(fieldname.toUpperCase()).append(".equals(fname)){\n");
			DBFieldSet set = f.getAnnotation(DBFieldSet.class);
			if(set.parseDBField().length() > 0){
				buffer.append("return ").append(set.parseDBField()).append(";\n");
			}else{
				if(f.getGenericType() == boolean.class){
					buffer.append("return bean.is");
				}else{
					buffer.append("return bean.get");
				}
				buffer.append(fieldname.substring(0, 1).toUpperCase()).append(fieldname.substring(1, fieldname.length())).append("();\n");
			}
			buffer.append("}\n");
		}
		buffer.append("return null;\n");
		buffer.append("}\n");
		buffer.append("@Override\n");
		buffer.append("public Class getClazz(){\n");
		buffer.append("return " + clazz.getName() + ".class;\n");
		buffer.append("}\n");
		buffer.append("public final static class " + "DBRowMapper_" + clazz.getSimpleName() + " implements com.ks.access.mapper.RowMapper" + "<"+ classname + ">{\n");
		buffer.append("@Override\n");
		buffer.append("public " + classname + " rowMapper(java.sql.ResultSet rs) throws java.sql.SQLException{\n");
		buffer.append(classname + " bean =" + " new " + classname + "();\n");
		for(Field f : fields){
			String rsstr = "";
			String fname = f.getName();
			DBFieldSet fset = f.getAnnotation(DBFieldSet.class);
			if(fset.parseJavaField().length() > 0){
				rsstr = fset.parseJavaField();
			}else{
				if(f.getGenericType() == byte.class){
					rsstr = "rs.getByte(\"" + fset.dbfname() + "\")";
				}else if(f.getGenericType() == int.class){
					rsstr = "rs.getInt(\"" + fset.dbfname() + "\")";
				}else if(f.getGenericType() == long.class){
					rsstr = "rs.getLong(\"" + fset.dbfname() + "\")";
				}else if(f.getGenericType() == double.class){
					rsstr = "rs.getDouble(\"" + fset.dbfname() + "\")";
				}else if(f.getGenericType() == float.class){
					rsstr = "rs.getFloat(\"" + fset.dbfname() + "\")";
				}else if(f.getGenericType() == boolean.class){
					rsstr = "rs.getBoolean(\"" + fset.dbfname() + "\")";
				}else if(f.getGenericType() == String.class){
					rsstr = "rs.getString(\"" + fset.dbfname() + "\")";
				}else if(f.getGenericType() == Date.class){
					rsstr = "rs.getTimestamp(\"" + fset.dbfname() + "\")";
				}
			}
			buffer.append("bean.set" + fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) + "(" + rsstr + ");\n");
		}
		buffer.append("return bean;\n");
		buffer.append("}\n");
		buffer.append("}\n");
		
		buffer.append("}\n");
		String npath = packagename.replaceAll("\\.", "/");
//		File f = new File(System.getProperty("user.dir") + File.separator + path + File.separator + npath + File.separator + newclassname + ".java");
		File f = new File(path + File.separator + npath + File.separator + newclassname + ".java");
		FileWriter fw = new FileWriter(f);
		fw.write(buffer.toString());
		fw.close();
	}
}
