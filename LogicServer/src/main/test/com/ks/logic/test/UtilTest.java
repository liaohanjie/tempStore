package com.ks.logic.test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.junit.Test;

import com.ks.model.activity.ActivityDefine;
import com.ks.model.fight.FightSkillEffect;
import com.ks.model.user.UserBuff;
import com.ks.util.StringUtil;


public class UtilTest {
	
	@Test
	public void testJedisResultSetTOObject(){
		JedisResultSetTOObject(ActivityDefine.class);
	}
	@Test
	public void testObjectToFieldMap(){
		objectToFieldMap(ActivityDefine.class);
	}
	
	@Test
	public void rowMapper(){
		rowMapper(UserBuff.class);
	}
	
	@Test
	public void testModelToVO(){
//		modelToGwtVO(ServerInfo.class);
	}

	@Test
	public void modelToVO(){
		modelToVO(FightSkillEffect.class);
	}
	
	@Test
	@SuppressWarnings("rawtypes")
	public void testinitDAO() throws Exception{
		Class[] clazz = getClasses("com.ks.logic.dao");
		System.out.println("//dao");
		for(Class cla : clazz){
			if(cla.isInterface()){
				
				String name = cla.getSimpleName().substring(0,1).toLowerCase()+cla.getSimpleName().substring(1);
				
				String str = "protected static final "+cla.getSimpleName() + " "+name +" = new "+cla.getSimpleName()+"Impl();";
				System.out.println(str);
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
	@Test
	@SuppressWarnings("rawtypes")
	public void initService() throws Exception{
		System.out.println("//service");
		Class[] clazz = getClasses("com.ks.logic.service");
		for(Class cla : clazz){
			if(cla.isInterface()){
				
				String name = cla.getSimpleName().substring(0,1).toLowerCase()+cla.getSimpleName().substring(1);
				
				String str = "protected static final "+cla.getSimpleName() + " "+name +" = new "+cla.getSimpleName()+"Impl();";
				System.out.println(str);
			}
		}
	}
	@Test
	@SuppressWarnings("rawtypes")
	public void initServiceFactory() throws Exception{
		System.out.println("//service Proxy");
		Class[] clazz = getClasses("com.ks.logic.service");
		for(Class cla : clazz){
			if(cla.isInterface()){
				
				String name = cla.getSimpleName().substring(0,1).toLowerCase()+cla.getSimpleName().substring(1);
				
				String str = cla.getSimpleName() + " "+name +" = new "+cla.getSimpleName()+"Impl();";
				String ss = "Proxy.newProxyInstance("+cla.getSimpleName()+".class.getClassLoader()"+",new Class<?>[]{"+cla.getSimpleName()+".class},"+"new ServiceHandler((BaseService)"+name+"))";
				System.out.println(str);
				System.out.println("serviceMap.put("+cla.getSimpleName()+".class"+","+ss+");");
				System.out.println();
			}
		}
	}
	
	/**
	 * 获得包下的类
	 * 
	 * @param packageName
	 *            The base package
	 * @return The classes
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Class<?>[] getClasses(String packageName)
			throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			String p = "";
			if (resource.getFile().indexOf("!") >= 0) {// 在其他的jar文件中
				p = resource.getFile().substring(0,
						resource.getFile().indexOf("!")).replaceAll("%20", "");
			} else {// 在classes目录中
				p = resource.getFile();
			}
			if (p.startsWith("file:/"))
				p = p.substring(6);
			if (p.toLowerCase().endsWith(".jar")) {
				try(JarFile jarFile = new JarFile(p)){
					Enumeration<JarEntry> enums = jarFile.entries();
					while (enums.hasMoreElements()) {
						JarEntry entry = (JarEntry) enums.nextElement();
						String n = entry.getName();
	
						if (n.endsWith(".class")) {
							n = n.replaceAll("/", ".").substring(0, n.length() - 6);
							if (n.startsWith(packageName)) {
	
								classes.add(Class.forName(n));
	
							}
						}
					}
				}
			} else {
				dirs.add(new File(p));
			}

		}

		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	/**
	 * 查找一个文件夹下的文件
	 * 
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	public static List<Class<?>> findClasses(File directory, String packageName)
			throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "."
						+ file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName
						+ '.'
						+ file.getName().substring(0,
								file.getName().length() - 6)));
			}
		}
		return classes;
	}
	
	private void rowMapper(Class<?> clazz){
		
		System.out.println("private static final RowMapper<"+clazz.getSimpleName()+"> "+StringChange(clazz.getSimpleName()).toUpperCase().substring(1, StringChange(clazz.getSimpleName()).length())+"_ROW_MAPPER = new RowMapper<"+clazz.getSimpleName()+">(){");
		System.out.println("	@Override");
		System.out.println("	public "+clazz.getSimpleName()+" rowMapper(ResultSet rs) throws SQLException{");
		System.out.println("		"+clazz.getSimpleName() + " obj = new "+ clazz.getSimpleName()+"();");
		String obj = "obj";
		Field[] fs = clazz.getDeclaredFields();
		for(Field f : fs){
			if((f.getModifiers()&Modifier.STATIC)==0){
				String setM = "set"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1);
				if(f.getType().getName().equals("int")){
					System.out.println("		"+obj+"."+setM+"(rs.getInt(\""+StringChange(f.getName())+"\"));");
				}
				if(f.getType().getName().equals("short")){
					System.out.println("		"+obj+"."+setM+"(rs.getShort(\""+StringChange(f.getName())+"\"));");
				}
				if(f.getType().getName().equals("byte")){
					System.out.println("		"+obj+"."+setM+"(rs.getByte(\""+StringChange(f.getName())+"\"));");
				}
				if(f.getType().getName().equals("long")){
					System.out.println("		"+obj+"."+setM+"(rs.getLong(\""+StringChange(f.getName())+"\"));");
				}
				if(f.getType().getName().equals("java.lang.String")){
					System.out.println("		"+obj+"."+setM+"(rs.getString(\""+StringChange(f.getName())+"\"));");
				}
				if(f.getType().getName().equals("double")){
					System.out.println("		"+obj+"."+setM+"(rs.getDouble(\""+StringChange(f.getName())+"\"));");
				}
				if(f.getType().getName().equals("float")){
					System.out.println("		"+obj+"."+setM+"(rs.getFloat(\""+StringChange(StringChange(f.getName())+"\"));"));
				}
				if(f.getType().getName().equals("boolean")){
					System.out.println("		"+obj+"."+setM+"(rs.getBoolean(\""+StringChange(f.getName())+"\"));");
				}
				if(f.getType().getName().equals("java.util.Date")){
					System.out.println("		"+obj+"."+setM+"(rs.getTimestamp(\""+StringChange(f.getName())+"\"));");
				}
			}
		}
		System.out.println("		return obj;");
		System.out.println("	}");
		System.out.println("};");
	}
	
	
	private void JedisResultSetTOObject(Class<?> clazz){
		
		System.out.println("private static final JedisRowMapper<"+clazz.getSimpleName()+"> JEDIS_MAPPER = new JedisRowMapper<"+clazz.getSimpleName()+">(){");
		System.out.println("	@Override");
		System.out.println("	public "+clazz.getSimpleName()+" rowMapper(JedisResultSet jrs) {");
		System.out.println("		"+clazz.getSimpleName() + " obj = new "+ clazz.getSimpleName()+"();");
		String obj = "obj";
		Field[] fs = clazz.getDeclaredFields();
		for(Field f : fs){
			if((f.getModifiers()&Modifier.STATIC)==0){
				String setM = "set"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1);
				if(f.getType().getName().equals("int")){
					System.out.println("		"+obj+"."+setM+"(jrs.getInt(\""+f.getName()+"\"));");
				}else if(f.getType().getName().equals("short")){
					System.out.println("		"+obj+"."+setM+"(jrs.getShort(\""+f.getName()+"\"));");
				}else if(f.getType().getName().equals("byte")){
					System.out.println("		"+obj+"."+setM+"(jrs.getByte(\""+f.getName()+"\"));");
				}else if(f.getType().getName().equals("long")){
					System.out.println("		"+obj+"."+setM+"(jrs.getLong(\""+f.getName()+"\"));");
				}else if(f.getType().getName().equals("java.lang.String")){
					System.out.println("		"+obj+"."+setM+"(jrs.getString(\""+f.getName()+"\"));");
				}else if(f.getType().getName().equals("double")){
					System.out.println("		"+obj+"."+setM+"(jrs.getDouble(\""+f.getName()+"\"));");
				}else if(f.getType().getName().equals("float")){
					System.out.println("		"+obj+"."+setM+"(jrs.getFloat(\""+f.getName()+"\"));");
				}else if(f.getType().getName().equals("boolean")){
					System.out.println("		"+obj+"."+setM+"(jrs.getBoolean(\""+f.getName()+"\"));");
				}else if(f.getType().getName().equals("java.util.Date")){
					System.out.println("		"+obj+"."+setM+"(jrs.getDate(\""+f.getName()+"\"));");
				}
			}
		}
		System.out.println("		return obj;");
		System.out.println("	}");
		System.out.println("};");
	}
	
	private void modelToVO(Class<?> clazz){
		System.out.println("public void init("+clazz.getSimpleName().substring(0, clazz.getSimpleName().length()-2)+" o){");
		Field[] fs = clazz.getDeclaredFields();
		for(Field f : fs){
			if((f.getModifiers()&Modifier.STATIC)==0){
				System.out.println("	this."+f.getName() + " = o.get" +StringUtil.toLowerCaseInitial(f.getName(), false)+"();");
			}
		}
		System.out.println("}");
	}
	
	private void objectToFieldMap(Class<?> clazz){
		
		System.out.println("private static final ObjectFieldMap<"+clazz.getSimpleName()+"> FIELD_MAP = new ObjectFieldMap<"+clazz.getSimpleName()+">(){");
		System.out.println("	@Override");
		System.out.println("	public Map<String, String> objectToMap("+clazz.getSimpleName()+" o){");
		System.out.println("		Map<String, String> map = new HashMap<String, String>();");
		String map = "map";
		Field[] fs = clazz.getDeclaredFields();
		for(Field f : fs){
			if((f.getModifiers()&Modifier.STATIC)==0){
				String getM = "get"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1)+"()";
				String isM = "is"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1)+"()";
				if(f.getType().getName().equals("int")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",String.valueOf(o."+getM+"));");
				}else if(f.getType().getName().equals("short")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",String.valueOf(o."+getM+"));");
				}else if(f.getType().getName().equals("byte")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",String.valueOf(o."+getM+"));");
				}else if(f.getType().getName().equals("long")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",String.valueOf(o."+getM+"));");
				}else if(f.getType().getName().equals("java.lang.String")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",o."+getM+");");
				}else if(f.getType().getName().equals("double")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",String.valueOf(o."+getM+"));");
				}else if(f.getType().getName().equals("float")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",String.valueOf(o."+getM+"));");
				}else if(f.getType().getName().equals("boolean")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",String.valueOf(o."+isM+"));");
				}else if(f.getType().getName().equals("java.util.Date")){
					System.out.println("		"+map+".put(\""+f.getName()+"\",String.valueOf(o."+getM+".getTime()));");
				}else{
					System.out.println("		"+map+".put(\""+f.getName()+"\",JSONUtil.toJson(o."+getM+"));");
				}
			}
		}
		System.out.println("		return map;");
		System.out.println("	}");
		System.out.println("};");
	}
	
	
	/**
	 * 功能：把一个字符串中的大写字母转换成小写，小写字母转换成大写
	 * @param s 传入的字符串
	 * @return 返回转换后的字符串
	 */
	public static String StringChange(String s)
	{
		if(s==""||s==null)
			return "";
		StringBuilder sb=new StringBuilder(s);
		String temp = null;
		boolean flag = true;
		while(flag){
			for (int i = 0; i < s.length(); i++) 
			{
				if(Character.isUpperCase(sb.charAt(i)))
				{
					temp=sb.toString().substring(i, i+1).toLowerCase();
					sb.replace(i, i+1, "_"+temp);
					flag = true;
					s=sb.toString();
					break;
				}
				flag = false;
			}
		}
		return sb.toString();
	}
}
