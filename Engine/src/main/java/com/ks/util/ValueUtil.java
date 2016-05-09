package com.ks.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ValueUtil {
//	@SuppressWarnings("rawtypes")
//	public static void copy(Object value, Builder builder) throws Exception{
//		Descriptor desc = builder.getDescriptorForType();
//		Class clazz = value.getClass();
//		while(clazz != Object.class){
//			Field[] fields = clazz.getDeclaredFields();
//			for(Field f : fields){
//				f.setAccessible(true);
//				String fname = f.getName();
//				Object fvalue = f.get(value);
//				if(fvalue != null){
//					FieldDescriptor fd = desc.findFieldByName(fname);
//					if(fd != null){
//						if(fd.getJavaType() != JavaType.MESSAGE){
//							if(fd.isRepeated()){
//								if(Collection.class.isAssignableFrom(f.getType())){
//									builder.setField(fd, fvalue);
//								}
//							}else{
//								builder.setField(fd, fvalue);
//							}
//						}
//					}
//				}
//			}
//			clazz = clazz.getSuperclass();
//		}
//	}
//	
//	@SuppressWarnings("rawtypes")
//	public static void copy(Message message, Object object) throws Exception{
//		Descriptor desc = message.getDescriptorForType();
//		Class clazz = object.getClass();
//		while(clazz != Object.class){
//			Field[] fields = clazz.getDeclaredFields();
//			for(Field f : fields){
//				f.setAccessible(true);
//				String fname = f.getName();
//				FieldDescriptor fd = desc.findFieldByName(fname);
//				if(fd != null){
//					if(fd.getJavaType() != JavaType.MESSAGE){
//						if(fd.isRepeated()){
//							if(Collection.class.isAssignableFrom(f.getType())){
//								f.set(object, message.getField(fd));
//							}
//						}else if(message.hasField(fd)){
//							f.set(object, message.getField(fd));
//						}
//					}
//				}
//			}
//			clazz = clazz.getSuperclass();
//		}
//	}
	/**
	 * 将object1的值拷贝到object2
	 * @param object1
	 * @param object2
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void copy(Object object1, Object object2){
		if(object1 != null){
			try{
				Class clazz1 = object1.getClass();
				while(clazz1 != Object.class){
					Field[] fields1 = clazz1.getDeclaredFields();
					Class clazz2 = object2.getClass();
					while(clazz2 != Object.class){
						Field[] fields2 = clazz2.getDeclaredFields();
						for(Field f2 : fields2){
							if(f2.getName().equals("serialVersionUID")){
								continue;
							}
							f2.setAccessible(true);
							for(Field f1 : fields1){
								if(f1.getName().equals(f2.getName())){
									f1.setAccessible(true);
									f2.set(object2, f1.get(object1));
									break;
								}
							}
						}
						clazz2 = clazz2.getSuperclass();
					}
					clazz1 = clazz1.getSuperclass();
				}
			}catch(Exception e){
				throw new RuntimeException(e);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Field getField(Class clazz, String fieldname){
		Class c = clazz;
		while(c != Object.class){
			Field[] fields = c.getDeclaredFields();
			for(Field field : fields){
				if(field.getName().equals(fieldname)){
					return field;
				}
			}
			c = c.getSuperclass();
		}
		return null;
	}
	
	public static Object getValue(Field field, Object object){
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static String toJsonString(Object object) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		Class clazz = object.getClass();
		Object value = null;
		if(clazz != Object.class){
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields){
				f.setAccessible(true);
				value = f.get(object);
				if(value != null){
					sb.append(f.getName() + ":" + f.get(object).toString() + ",");
				}
			}
			clazz = clazz.getSuperclass();
		}
		if(sb.length() > 0){
			sb.setLength(sb.length() - 1);
		}
		sb.append("}");
		return sb.toString();
	}
	
	public static String toJsonString(Map<String, Object> map) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for(Map.Entry<String, Object> entry : map.entrySet()){
			sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
		}
		if(sb.length() > 1){
			sb.setLength(sb.length() - 1);
		}
		sb.append("}");
		return sb.toString();
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> toMap(Object object) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Class clazz = object.getClass();
		while(clazz != Object.class){
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields){
				f.setAccessible(true);
				Object value = f.get(object);
				if(value != null){
					map.put(f.getName(), value);
				}
			}
			clazz = clazz.getSuperclass();
		}
		return map;
	}

}
