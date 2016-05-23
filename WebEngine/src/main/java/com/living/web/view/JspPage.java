package com.living.web.view;

import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ks.exception.EngineException;
import com.ks.logger.LoggerFactory;
import com.living.web.core.WebContext;

public class JspPage extends ViewPage{
	private static Logger logger=LoggerFactory.get(JspPage.class);
	@Override
	public void Render(WebContext web, HttpServletResponse response)
			throws Exception {
		String path=web.getViewPath();
		Map<String,Object> param=web.getContexParams();
		if(path==null||path.length()<=0){
			 throw new EngineException(EngineException.CODE_SYSTEM_EXCETION," view reutrn path can't be null");
		}
		if(logger.isDebugEnabled()){
			logger.debug("rendering:"+path);
		}
		for(Entry<String, Object>model:param.entrySet()){
			web.getRequest().setAttribute(model.getKey(), model.getValue());
		}
		RequestDispatcher rd=web.getRequest().getRequestDispatcher(path);
		if(rd==null){
			logger.error("can not found jsp resource:"+path);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		rd.forward(web.getRequest(), response);
	}
}
