package com.ks.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.log4j.Logger;

import com.ks.app.ServerEngine;
import com.ks.logger.LoggerFactory;

public class JavaUtil {
	private static final Logger logger = LoggerFactory.get(JavaUtil.class);
	public static Class<?> javac(File file,String className){
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		
		Iterable<? extends JavaFileObject> compilationUnits1 =
		fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file));
		StringWriter sw = new StringWriter();
		JavaCompiler.CompilationTask task = compiler.getTask(sw, fileManager, null, null, null, compilationUnits1);
		if(!task.call()){
			logger.error(sw.toString());
			throw new ExceptionInInitializerError("compiler error");
		}
		try {
			fileManager.close();
		} catch (IOException e) {
			logger.error("",e);
		}
		try {
			return ServerEngine.getClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			logger.error("",e);
		}
		return null;
	}
}
