package com.living.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.living.web.servlet.sdk.SdkHandler;
import com.living.web.servlet.sdk.SdkManager;

/**
 * SDK
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年3月17日
 */
public class SdkServlet extends HttpServlet {
	
    private static final long serialVersionUID = -7125119830399292524L;
    
	private Logger logger = LoggerFactory.get(SdkServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info(req.getRequestURI());
		
		String uri = req.getRequestURI();
		String[] params = uri.split("/");
		
		if (params.length < 5) {
			_notFound(req, resp);
			return;
		}
		
		String name = params[2];
		String sysType = params[3];
		String interfaceName = params[4];
		
		SdkHandler handler = SdkManager.get(name + "-" + sysType);
		
		if (handler != null) {
			try {
				if ("login".equals(interfaceName)) {
					handler.login(req, resp);
				} else if ("notify".equals(interfaceName)) {
					handler.notify(req, resp);
				} else {
					_notFound(req, resp);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		} else {
			_notFound(req, resp);
		}
	}
	
	private void _notFound(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setStatus(404);
		resp.getWriter().write("not found sdk");
		logger.error("sdk pattern is wrong. uri=" + req.getRequestURI());
	}
}
