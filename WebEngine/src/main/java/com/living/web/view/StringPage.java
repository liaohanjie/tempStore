/**
 * 
 */
package com.living.web.view;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
import com.living.web.core.WebContext;

/**
 * @author living.li
 * @date  2015年5月29日 上午9:42:52
 *
 *
 */
public class StringPage  extends ViewPage {
	
	private String value;
	private static Logger logger=LoggerFactory.get(JsonPage.class);
	@Override
	public void Render(WebContext web, HttpServletResponse rsp)
			throws Exception {
		if(logger.isDebugEnabled()){
			logger.debug("render String text view.");
		}
		rsp.setContentType("text/plain");
		rsp.getWriter().write(value);
		closeResponseWriter(rsp);
	}
	public StringPage(String value){
		this.value=value;
	}
	
	public static void main(String[] args) {
		
	}
}
