package com.ks.exception;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;

import com.ks.protocol.FieldDesc;

/**
 * 错误消息友生成
 * @author zck
 *
 */
public class ExceptionUtil {
	/**
	 * 生成错误代码文件
	 * @param exceptionObj
	 * @param path
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static void createExceptionXMLFile(Object exceptionObj, String path) throws Exception{
		Class clazz = exceptionObj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String fcontent = "<error>\r\n";
		for(Field field : fields){
			field.setAccessible(true);
			FieldDesc desc = field.getAnnotation(FieldDesc.class);
			if(desc != null){
				String fname = String.valueOf(field.getInt(exceptionObj));
				fcontent += "<item key=\"ERROR_CODE_" + fname + "\" value=\"" + desc.desc() + "\" />\r\n";
			}
		}
		fcontent += "</error>\r\n";
		File asF = new File(path+"/error_code.xml");
		FileWriter fw  = new FileWriter(asF);
		fw.write(fcontent);
		fw.close();
	}
	@SuppressWarnings("rawtypes")
	public static void createAs3ExceptionClass(Object exceptionObj, String path) throws Exception{
		Class clazz = exceptionObj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String fcontent = "package com.vo{\n";
		fcontent += "public class GameException{\n";
		for(Field field : fields){
			field.setAccessible(true);
			FieldDesc desc = field.getAnnotation(FieldDesc.class);
			if(desc != null){
				int value = field.getInt(exceptionObj);
				fcontent += "/**"+desc.desc()+"*/\n";
				fcontent += "public const CODE_" + value + ":int=" + value + ";\n";
			}
		}
		fcontent += "}\n";
		fcontent += "}\n";
		File asF = new File(path+"/GameException.as");
		FileWriter fw  = new FileWriter(asF);
		fw.write(fcontent);
		fw.close();
	}
}
