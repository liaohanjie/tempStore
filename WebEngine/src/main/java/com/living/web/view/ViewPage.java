/**
 * 
 */
package com.living.web.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.ks.exception.EngineException;
import com.living.web.core.WebContext;

/**
 * @author living.li
 * @date 2013-12-10 下午9:57:23
 * 
 */
public abstract class ViewPage  {
	public abstract void  Render(WebContext web,HttpServletResponse response)throws Exception;
	
	public static final int V_404=0;
	public static final int V_503=1;
	public static Map<Integer,ViewPage> staticView;
	static{
		staticView=new HashMap<>();
		staticView.put(0,new Page404());
		staticView.put(1,new Page503());
	}	
	public static ViewPage get(int viewType){
		ViewPage v=staticView.get(viewType);
		if(v==null){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION," this type static view not found."+viewType);
		}
		return v;
	}
	protected void closeResponseWriter(HttpServletResponse rsp) 
	throws IOException{
		rsp.getWriter().close();
	}
}
