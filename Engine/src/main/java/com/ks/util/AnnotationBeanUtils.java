package com.ks.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.ks.access.DBBeanSetDeleteSqlSet;
import com.ks.access.DBBeanSetInsertSqlSet;
import com.ks.access.DBBeanSetSelectSqlSet;
import com.ks.access.DBBeanSetSqlSetList;
import com.ks.access.DBBeanSetUpdateSqlSet;
import com.ks.access.DBFieldSet;
import com.ks.cache.JedisFieldSet;

public class AnnotationBeanUtils {
	
	@SuppressWarnings("rawtypes")
	public static List<Field> getDBFields(Object obj){
		List<Field> list = new ArrayList<Field>();
		Class clazz = obj.getClass();
		while(clazz != Object.class){
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields){
				if(field.isAnnotationPresent(DBFieldSet.class)){
					list.add(field);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return list;
	}
	
	public static Field getDBField(String dbfname, List<Field> fields){
		for(Field field : fields){
			DBFieldSet fset = field.getAnnotation(DBFieldSet.class);
			if(fset.dbfname().equals(dbfname)){
				return field;
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Field> getJedisFields(Object obj){
		List<Field> list = new ArrayList<Field>();
		Class clazz = obj.getClass();
		while(clazz != Object.class){
			Field[] fields = clazz.getDeclaredFields();
			for(Field field : fields){
				if(field.isAnnotationPresent(JedisFieldSet.class)){
					list.add(field);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return list;
	}
	
	public static Field getJedisField(String dbfname, List<Field> fields){
		for(Field field : fields){
			JedisFieldSet fset = field.getAnnotation(JedisFieldSet.class);
			if(fset.jedisname().equals(dbfname) 
					|| (fset.jedisname().length() == 0 && field.getName().equals(dbfname))){
				return field;
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Method> getDBSqlMethod(Object obj){
		List<Method> list = new ArrayList<Method>();
		Class clazz = obj.getClass();
		while(clazz != Object.class){
			Method[] methods = clazz.getMethods();
			for(Method method : methods){
				if(method.isAnnotationPresent(DBBeanSetSqlSetList.class)){
					list.add(method);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Method> getDBSqlMethod(Class clazz){
		List<Method> list = new ArrayList<Method>();
		while(clazz != Object.class){
			Method[] methods = clazz.getMethods();
			for(Method method : methods){
				if(method.isAnnotationPresent(DBBeanSetSqlSetList.class)){
					list.add(method);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<Method> getDBSqlMethod(Class clazz, Class dbbean){
		List<Method> list = new ArrayList<Method>();
		while(clazz != Object.class){
			Method[] methods = clazz.getMethods();
			for(Method method : methods){
				if(method.isAnnotationPresent(DBBeanSetSqlSetList.class)){
					DBBeanSetSqlSetList setlist = method.getAnnotation(DBBeanSetSqlSetList.class);
					boolean sign = false;
					if(!sign){
						for(DBBeanSetDeleteSqlSet set : setlist.deletes()){
							if(set.dbbean() == dbbean){
								sign = true;
								break;
							}
						}
					}
					if(!sign){
						for(DBBeanSetSelectSqlSet set : setlist.selects()){
							if(set.dbbean() == dbbean){
								sign = true;
								break;
							}
						}
					}
					if(!sign){
						for(DBBeanSetUpdateSqlSet set : setlist.updates()){
							if(set.dbbean() == dbbean){
								sign = true;
								break;
							}
						}
					}
					if(!sign){
						for(DBBeanSetInsertSqlSet set : setlist.inserts()){
							if(set.dbbean() == dbbean){
								sign = true;
								break;
							}
						}
					}
					if(sign){
						list.add(method);
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		return list;
	}
}
