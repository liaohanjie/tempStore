package com.living.web;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtil {
	
	public static ObjectMapper mapper = new ObjectMapper();
	
	static {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mapper.getSerializationConfig().setDateFormat(df);
		//mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
	}
	
	public static String writeAsString(Object obj){
		if(obj == null || obj.toString().trim().equals("")) {
			return null;
		}
		try {
	        return mapper.writeValueAsString(obj);
        } catch (JsonGenerationException e) {
	        e.printStackTrace();
        } catch (JsonMappingException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }
		return null;
	}
	
	public static void writeAsString(OutputStream out, Object obj){
		try {
	        mapper.writeValue(out, obj);
        } catch (JsonGenerationException e) {
	        e.printStackTrace();
        } catch (JsonMappingException e) {
	        e.printStackTrace();
        } catch (IOException e) {
	        e.printStackTrace();
        }
	}
}
