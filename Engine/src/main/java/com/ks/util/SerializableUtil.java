package com.ks.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;

import com.ks.app.ServerEngine;

/**
 * 序列化工具
 * @author ks
 */
public class SerializableUtil {
	
	/**
	 * 序列化
	 * @param obj
	 * @return
	 */
	public static byte[] encode(Serializable obj){
		try (ByteArrayOutputStream baos = new  ByteArrayOutputStream(); 
				ObjectOutput oo = new ObjectOutputStream(baos);){
			oo.writeObject(obj);
			return baos.toByteArray();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 序列化
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T>T decode(byte[] array,Class<T> clazz){
		if(array == null){
			return null;
		}
		try (ByteArrayInputStream bin = new ByteArrayInputStream(array);
				MyObjectInputStream obin = new MyObjectInputStream(bin,ServerEngine.getClassLoader());){
			return (T) obin.readObject();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
class MyObjectInputStream extends ObjectInputStream{
	
	private ClassLoader loader;
	
	protected MyObjectInputStream(InputStream in, ClassLoader loader) throws IOException, SecurityException {
		super(in);
		this.loader = loader;
	}
	
	@Override
	protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException,
			ClassNotFoundException {
		if(loader==null){
			return super.resolveClass(desc);
		}
		 return loader.loadClass(desc.getName());
	}
}
