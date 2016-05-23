package com.living.web.view;

import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.ks.logger.LoggerFactory;
import com.living.web.core.WebContext;
/**
 * 
 * @author living.li
 * @date   2014年5月10日
 */
public class JsonPage extends ViewPage {
	public static ObjectMapper mapper;
	private static Logger logger=LoggerFactory.get(JsonPage.class);
	
	@SuppressWarnings("deprecation")
	public JsonPage(){
			mapper=new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.WRITE_NULL_PROPERTIES, false);
	}
	@Override
	public void Render(WebContext web, HttpServletResponse rsp)
			throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("render json text view.");
		}
		StringWriter sw=new StringWriter();
		mapper.writeValue(sw,web.getContexParams());
		rsp.setContentType("text/json");
		rsp.getWriter().write(sw.toString());
		closeResponseWriter(rsp);
	}

}
