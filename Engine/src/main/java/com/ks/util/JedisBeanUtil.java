package com.ks.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.ks.cache.JedisBeanSet;
import com.ks.cache.JedisFieldSet;

public class JedisBeanUtil {

	/**
	 * 生成Jedis
	 * @throws Exception
	 */
	public static void initJedisBean(String file, String path, String packagename) throws Exception{
		FileInputStream fis = new FileInputStream(new File(file));
		initJedisBean(fis, path, packagename);
	}
	
	public static void initJedisBean(InputStream stream, String path, String packagename) throws Exception{
		try{
			Properties properties = new Properties();
			properties.load(stream);
			for(Object key : properties.keySet()){
				String keystr = key.toString();
				String classname = properties.getProperty(keystr);
				createJedisRowMapper(keystr, classname, path, packagename);
				createObjMapper(keystr, classname, path, packagename);
			}
		}finally{
			stream.close();
		}
	}
	
	public static void createObjMapper(String keystr, String classname, String path, String packagename) throws Exception{
		Object obj = Class.forName(classname).newInstance();
		List<Field> fields = AnnotationBeanUtils.getJedisFields(obj);
		StringBuffer buffer = new StringBuffer();
		String newclassname = "ObjectFieldMap_" + keystr;
		System.out.println("create " + newclassname);
		buffer.append("package "+packagename+";\n");
		JedisBeanSet oset = obj.getClass().getAnnotation(JedisBeanSet.class);
		if(oset != null){
			for(String im : oset.imports()){
				buffer.append("import " + im + ";\n");
			}
		}
		buffer.append("public final class " + newclassname + " implements com.ks.cache.ObjectFieldMap" + "<"+ classname + ">{\n");
		buffer.append("@Override\n");
		buffer.append("public java.util.Map<String, String> objectToMap(" + classname + " bean) {\n");
		buffer.append("java.util.Map<String, String> map = new java.util.HashMap<String, String>();\n");
		String rsstr = "";
		for(Field f : fields){
			String fname = f.getName();
			JedisFieldSet jset = f.getAnnotation(JedisFieldSet.class);
			if(jset != null){
				String jfname = jset.jedisname();
				if(jfname.length() == 0){
					jfname = fname;
				}
				if(jset.parseJedisField().length() > 0){
					rsstr = jset.parseJedisField();
				}else{
					if(f.getGenericType() == boolean.class){
						rsstr = "bean.is" + fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) + "()";
					}else if(f.getGenericType() == Date.class){
						rsstr = "bean.get" + fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) + "().getTime()";
					}else{
						rsstr = "bean.get" + fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) + "()";
					}
					rsstr = "String.valueOf(" + rsstr + ")";
				}
				buffer.append("map.put(\"" + jfname + "\"," + rsstr + ");\n");
			}
		}
		buffer.append("return map;\n");
		buffer.append("}\n");
		buffer.append("}\n");
//			File f =new File(System.getProperty("user.dir")+ File.separator+"bin"+ File.separator+ newclassname + ".java");

			String npath = packagename.replaceAll("\\.", "/");
			File f =new File(path+ File.separator + npath + File.separator + newclassname + ".java");
	
			FileWriter fw = new FileWriter(f);
			fw.write(buffer.toString());
			fw.close();
//			ObjectFieldMap mapper  = (ObjectFieldMap)JavaUtil.javac(f, newclassname).newInstance();
//			objMapperCache.put(obj.getClass(), mapper);
	}
	
	public static void createJedisRowMapper(String keystr, String classname, String path, String packagename) throws Exception{
		Object obj = Class.forName(classname).newInstance();
		List<Field> fields = AnnotationBeanUtils.getJedisFields(obj);
		StringBuffer buffer = new StringBuffer();
		String newclassname = "JedisRowMapper_" + keystr;
		System.out.println("create " + newclassname);
		buffer.append("package com.ks.jedis.model;\n");
		JedisBeanSet oset = obj.getClass().getAnnotation(JedisBeanSet.class);
		if(oset != null){
			for(String im : oset.imports()){
				buffer.append("import " + im + ";\n");
			}
		}
		buffer.append("public final class " + newclassname + " implements com.ks.cache.JedisRowMapper" + "<"+ classname + ">{\n");
		buffer.append("@Override\n");
		buffer.append("public " + classname + " rowMapper(com.ks.cache.JedisResultSet jrs) {\n");
		buffer.append(classname + " bean =" + " new " + classname + "();\n");
		String rsstr = "";
		for(Field f : fields){
			String fname = f.getName();
			JedisFieldSet jset = f.getAnnotation(JedisFieldSet.class);
			if(jset != null){
				String jfname = jset.jedisname();
				if(jfname.length() == 0){
					jfname = fname;
				}
				if(jset.parseJavaField().length() > 0){
					rsstr = jset.parseJavaField();
				}else{
					if(f.getGenericType() == byte.class){
						rsstr = "jrs.getByte(\"" + jfname + "\")";
					}else if(f.getGenericType() == short.class){
						rsstr = "jrs.getShort(\"" + jfname + "\")";
					}else if(f.getGenericType() == int.class){
						rsstr = "jrs.getInt(\"" + jfname + "\")";
					}else if(f.getGenericType() == long.class){
						rsstr = "jrs.getLong(\"" + jfname + "\")";
					}else if(f.getGenericType() == double.class){
						rsstr = "jrs.getDouble(\"" + jfname + "\")";
					}else if(f.getGenericType() == float.class){
						rsstr = "jrs.getFloat(\"" + jfname + "\")";
					}else if(f.getGenericType() == boolean.class){
						rsstr = "jrs.getBoolean(\"" + jfname + "\")";
					}else if(f.getGenericType() == String.class){
						rsstr = "jrs.getString(\"" + jfname + "\")";
					}else if(f.getGenericType() == Date.class){
						rsstr = "jrs.getDate(\"" + jfname + "\")";
					}
				}
				buffer.append("bean.set" + fname.substring(0, 1).toUpperCase() + fname.substring(1, fname.length()) + "(" + rsstr + ");\n");
			}
		}
		buffer.append("return bean;");
		buffer.append("}\n");
		buffer.append("}\n");
//		File f =new File(System.getProperty("user.dir")+ File.separator+"bin"+ File.separator+ newclassname + ".java");
		String npath = packagename.replaceAll("\\.", "/");
		File f =new File(path+ File.separator + npath + File.separator + newclassname + ".java");

		FileWriter fw = new FileWriter(f);
		fw.write(buffer.toString());
		fw.close();

	}
}
