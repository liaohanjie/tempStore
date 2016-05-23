package com.living.web.view;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ks.exception.EngineException;
import com.ks.logger.LoggerFactory;
import com.living.web.core.WebContext;

/**
 * 
 * @author living.li
 * @date   2014年5月10日
 */
public class StaticPage extends ViewPage {

	private Logger logger=LoggerFactory.get(StaticPage.class);
	@Override
	public void Render(WebContext web,HttpServletResponse response) throws Exception {
		String resource=web.getViewPath();
		if(resource==null){
			 throw new EngineException(EngineException.CODE_SYSTEM_EXCETION," view reutrn path can't be null");
		}
		RequestDispatcher dis= web.getRequest().getRequestDispatcher(resource);
		if(dis==null){
			logger.error("can not found  resource:"+resource);
			response.sendError(HttpServletResponse.SC_FOUND);
		}else{
			dis.forward(web.getRequest(), web.getResponse());
		}
	}
	
}
