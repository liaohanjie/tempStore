/**
 * 
 */
package com.living.web.core;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.ks.exception.EngineException;
import com.ks.logger.LoggerFactory;
import com.living.web.view.JspPage;
import com.living.web.view.ViewPage;

/**
 * @author living.li
 * @date 2013-12-22 下午3:56:07
 * 
 */
public class WebContext implements Serializable{

   private static final Logger log=LoggerFactory.get(WebContext.class);
   public static final SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String METHOD_POST="POST";
	public static final String METHOD_GET="GET";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String reqURI;
	private String serverName;
	private StringBuffer reqURL;
	private String remoteAddr;
	private String remoteHost;
	private Map<String,Object[]> reqParamers;
	private Map<String,Object> contexParams;
	protected Object[] viewParam;
	private String reqEncoding;
	private String method;
	private String query;
	private String contextPath;
	
	
	
	public WebContext(HttpServletRequest request,HttpServletResponse response){
		this.request=request;
		this.response=response;
	}
	
	public void init(){
		this.reqURI=request.getRequestURI();
		this.reqURL=request.getRequestURL();
		this.serverName=request.getServerName();
		this.remoteAddr=request.getRemoteAddr();
		this.remoteHost=request.getRemoteHost();
		this.reqEncoding=request.getCharacterEncoding();
		this.method=request.getMethod().toUpperCase();
		this.query=request.getQueryString();
		this.contextPath=request.getContextPath();
		reqParamers=new HashMap<String,Object[]>();
		contexParams=new HashMap<String,Object>();
		Iterator<?> iterator=request.getParameterMap().entrySet().iterator();
		while(iterator.hasNext()){
			Entry<?,?> entry=(Entry<?, ?>) iterator.next();
			String[] value=(String[]) entry.getValue();
			reqParamers.put((String)entry.getKey(), value);
		}
	}
	public String getMethod() {
		return method;
	}

	public String getReqEncoding() {
		return reqEncoding;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getReqURI() {
		return reqURI;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public String getRemoteHost() {
		return remoteHost;
	}
	public Map<String, Object[]> getReqParamer() {
		return reqParamers;
	}

	public Map<String, Object> getResponseParamer() {
		return contexParams;
	}
	
	public void put(String key,Object value){
		contexParams.put(key, value);
	}
	public String getHeader(String headerName){
		return request.getHeader(headerName);
	}	
	public void addHeader(String name,String header){
		response.addHeader(name, header);
	}
	
	public String forwardFor() {
		return request.getHeader("X-Forwarded-For");
	}
	

	public String getAsString(String key) {
		Object ss[]=reqParamers.get(key);
		return ss==null?null:String.valueOf(ss[0]);
	}
	
	public String getAsString(String key, String defaultValue) {
		String v=getAsString(key);
		return v==null?defaultValue:v;
	}
	
	public String getAsString(boolean required, String key) {
		String v=getAsString(key);
		if(v==null){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"key:"+key+" required.");
		}
		return v;
	}
	public String getAsStrWithEncode(String key,String beforeSet,String afterSet) {
		String v=getAsString(key);
		if(v!=null){
			try {
				return new String(v.getBytes(beforeSet),afterSet);
			} catch (UnsupportedEncodingException e) {
				log.error(e.getMessage());				
				throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,e.getMessage());
			}
		}
		return v;
	}
	
	public Integer getAsInt(String key) {
		String v=getAsString(key);
		return v==null?null:Integer.valueOf(v);
	}
	public Integer getAsInt(String key, Integer defaultValue) {
		String v=getAsString(key);
		return v==null?defaultValue:Integer.valueOf(v);
	}
	public Integer getAsInt(boolean required, String key) {
		String v=getAsString(key);
		if(v==null){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"key:"+key+" required.");
		}
		return Integer.valueOf(v);
	}
	public Double getAsDouble(String key) {
		String v=getAsString(key);
		return v==null?null:Double.valueOf(v);
	}
	public Date getAsDate(String key) {
		String v=getAsString(key);
		try {
			return v==null?null:df.parse(v);
		} catch (ParseException e) {
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"key:"+key+" can't parse today."+v);
			
		}
	}
	public Date getAsDate(boolean required,String key) {
		String v=getAsString(required,key);
		try {
			return v==null?null:df.parse(v);
		} catch (ParseException e) {
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"key:"+key+" can't parse today."+v);
			
		}
	}

	public Double getAsDouble(String key, Double defaultValue) {
		String v=getAsString(key);
		return v==null?defaultValue:Double.valueOf(v);
	}
	public Double getAsDouble(boolean required, String key) {
		String v=getAsString(key);
		if(v==null){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"key:"+key+" required.");
		}
		return Double.valueOf(v);
	}
	public Float getAsFloat(String key) {
		String v=getAsString(key);
		return v==null?null:Float.valueOf(v);
	}
	public Float getAsFloat(String key, Float defaultValue) {
		String v=getAsString(key);
		return v==null?defaultValue:Float.valueOf(v);
	}
	public Float getAsFloat(boolean required, String key) {
		String v=getAsString(key);
		if(v==null){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"key:"+key+" required.");
		}
		return Float.valueOf(v);
	}
	public Long getAsLong(String key) {
		String v=getAsString(key);
		return v==null?null:Long.valueOf(v);
	}
	public Long getAsLong(String key, Long defaultValue) {
		String v=getAsString(key);
		return v==null?defaultValue:Long.valueOf(v);
	}
	public Long getAsLong(boolean required, String key) {
		String v=getAsString(key);
		if(v==null){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"key:"+key+" required.");
		}
		return Long.valueOf(v);
	}
	public Boolean getAsBoolean(String key) {
		String v=getAsString(key);
		return v==null?null:Boolean.valueOf(v);
	}
	public Boolean getAsBoolean(String key, Boolean defaultValue) {
		String v=getAsString(key);
		return v==null?defaultValue:Boolean.valueOf(v);
	}
	public Boolean getAsBoolean(boolean required, String key) {
		String v=getAsString(key);
		if(v==null){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"key:"+key+" required.");
		}
		return Boolean.valueOf(v);
	}
	public String[] getAsStrings(String key) {
		return (String[]) this.reqParamers.get(key);
	}
	public String getReqServiceId(){		
		String last=reqURL.toString();
		if(query!=null){
			last=reqURI.replace(query+"","");
		}
		last=reqURI.replace(contextPath+"","");
		String[] node=last.split("/");		
		if(node==null||node.length<2){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION," can't get serviceId from url."+reqURI);
		}
		String mehtod=node[node.length-1].replace(".do","");
		if(!last.endsWith("/"+mehtod+".do")){
			throw new EngineException(EngineException.CODE_SYSTEM_EXCETION,"request url not right."+reqURL.toString());
		}
		String service=node[node.length-2];
		return service+"."+mehtod;		
	}
	public Cookie[] getCookies(){
		return request.getCookies();
	}
	public Object[] getRemove(String name){
		return reqParamers.remove(name);
	}	
	public ViewPage go(ViewPage view,Object ...viewParams) {
		this.viewParam=viewParams;
		return view;
	}
	public ViewPage go(String url) {
		this.viewParam=new String[]{url};
		return new JspPage();
	}
	public String getViewPath(){
		if(viewParam==null){
			return null;
		}
		return viewParam.length==0?null:(String)viewParam[0];
	}
	
	public Map<String, Object> getContexParams() {
		return contexParams;
	}

	public void setContexParams(Map<String, Object> contexParams) {
		this.contexParams = contexParams;
	}

	public void setResponseEncoding(String encoding){
		response.setCharacterEncoding(encoding);
	}	
	public HttpSession getSession(boolean create) {
	 	return this.getRequest().getSession(create);
	}

	public String getServerName() {
		return serverName;
	}

	public StringBuffer getReqURL() {
		return reqURL;
	}

	public String getQuery() {
		if(method.equals(METHOD_POST)){
			query="";
			@SuppressWarnings("unchecked")
			Map<String, String[]> params = request.getParameterMap();
			for (String key : params.keySet()) {
				String[] values = params.get(key);
				for (int i = 0; i < values.length; i++) {
					String value = values[i];
					query += key + "=" + value + "&";
				}
			}
			if(query.length()<=0){
				return query;
			}
			query = query.substring(0, query.length() - 1);
		}
		return query;
	}

	public String getContextPath() {
		return contextPath;
	}
	
	public Object[] getViewParam() {
		return viewParam;
	}

	public void setViewParam(Object[] viewParam) {
		this.viewParam = viewParam;
	}

	public void setReqURL(StringBuffer reqURL) {
		this.reqURL = reqURL;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	@Override
	public String toString() {
		return "WebContext [reqParamers=" + reqParamers + "]";
	}
	
	
}
