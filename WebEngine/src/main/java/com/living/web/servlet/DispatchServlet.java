/**
 * 
 */
package com.living.web.servlet;

import java.io.IOException;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ks.exception.EngineException;
import com.ks.logger.LoggerFactory;
import com.living.web.core.WebContext;
import com.living.web.hanlder.WebHandlerManager;
import com.living.web.view.ViewPage;

/**
 * @author living.li
 * @date 2013-12-24 下午11:24:11
 * 
 */
public class DispatchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=LoggerFactory.get(DispatchServlet.class);

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		WebContext context=new WebContext(request,response);
		context.init();
		traceHanler(context);
		Object view=null;
		try {			
			view=WebHandlerManager.get().invokeHanlder(context.getReqServiceId(),context);
		} catch (Exception e) {
			Throwable ex = e;
			while(ex.getCause()!=null){
				ex=ex.getCause();
			}
			log.error("invoke servce error.",e);
			if(e instanceof  EngineException){
				response.sendError(HttpServletResponse.SC_NOT_FOUND);				
			}else{
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
			return;
		}
		if(view==null){
			log.error("the response view can't be null");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		if(!(view instanceof ViewPage)){
			log.error(" the page must extends "+ViewPage.class.getSimpleName());
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		ViewPage page=(ViewPage)view;
		try {
			page.Render(context,response);			
		} catch (Exception e) {
			log.error("render page error:"+e);
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
	private void traceHanler(WebContext c){
		StringBuffer buff=new StringBuffer();
		buff.append("trace request ");
		buff.append("\r\n  method:"+c.getMethod());
		buff.append("\r\n  reqUrl:"+c.getReqURL());
		buff.append("\r\n  remote:"+c.getRemoteAddr());
		buff.append("\r\n  param:\r\n");
		for(Entry<String, Object[]> entry: c.getReqParamer().entrySet()){
			buff.append("  "+entry.getKey()+":");
			for(Object o:entry.getValue()){
				buff.append(o+",");
			}
		}
		log.debug(buff.toString());
	}
}
