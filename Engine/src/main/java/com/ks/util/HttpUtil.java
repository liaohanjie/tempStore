package com.ks.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;

import com.ks.logger.LoggerFactory;
/**
 * 
 * @author living.li
 * @date 2014年7月7日
 */
public class HttpUtil {
	private final static Logger logger = LoggerFactory.get(HttpUtil.class);

	private static final int HTTP_CODE_OK = 200;
	private static final String DEFAULT_ENCODE="UTF-8";

	/**
	 * 
	 * @param host	
	 * @param val  (v1=XX&v2=XX)
	 * @param encode (urlEncode dealut=utf-8)
	 * @throws Exception
	 * 
	 */
	public static String postRet(String host, String param, String encode,String rpsEncode) {
		BufferedWriter pw = null;
		BufferedReader in = null;
		HttpURLConnection conn=null;
		String params = param;
		URL getUrl;
		try {
			getUrl = new URL(host);
			conn = (HttpURLConnection) getUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.connect();
			logger.debug("post >>>>>>" + host + " params." + param);
			//write
			pw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),encode));
			pw.write(params);
			pw.flush();
			int stat = conn.getResponseCode();
			if (stat != HTTP_CODE_OK) {
				logger.warn("post <<<<<<"+stat +" "+ host +  " params:" + params);
				return null;
			}
			in= new BufferedReader(new InputStreamReader(conn.getInputStream(),rpsEncode==null?DEFAULT_ENCODE:rpsEncode));
			StringBuffer temp = new StringBuffer();
			String line = null;
			while ((line=in.readLine())!= null) {
				temp.append(line);
			}
			return temp.toString();
		} catch (Exception e) {
			logger.error("post>>>>"+host,e);
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (in != null) {
					in.close();
				}
				if(conn!=null){
					conn.disconnect();
				}
			} catch (Exception e) {
				logger.error("post>>>>"+host,e);
			}

		}
		return null;
	}
	/**
	 * 
	 * @param host	
	 * @param val  (v1=XX&v2=XX)
	 * @param encode (urlEncode dealut=utf-8)
	 * @throws Exception
	 * 
	 */
	public static String postRet(String host, String param, String encode,String rpsEncode,Map<String,String> headers) {
		BufferedWriter pw = null;
		BufferedReader in = null;
		HttpURLConnection conn=null;
		String params = param;
		URL getUrl;
		try {
			getUrl = new URL(host);
			conn = (HttpURLConnection) getUrl.openConnection();
			Set<Entry<String, String>> headSet=headers.entrySet();
			for(Entry<String, String> entry:headSet){
				conn.addRequestProperty(entry.getKey(), entry.getValue());
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("post >>>>>>" + host + " params." + param);
			}
			//write
			pw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),encode));
			pw.write(params);
			pw.flush();
			int stat = conn.getResponseCode();
			if (stat != HTTP_CODE_OK) {
				logger.warn("post <<<<<<"+stat +" "+ host +  " params:" + params);
				return null;
			}
			in= new BufferedReader(new InputStreamReader(conn.getInputStream(),rpsEncode==null?DEFAULT_ENCODE:rpsEncode));
			StringBuffer temp = new StringBuffer();
			String line = null;
			while ((line=in.readLine())!= null) {
				temp.append(line);
			}
			return temp.toString();
		} catch (Exception e) {
			logger.error("post>>>>"+host,e);
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (in != null) {
					in.close();
				}
				if(conn!=null){
					conn.disconnect();
				}
			} catch (Exception e) {
				logger.error("post>>>>"+host,e);
			}

		}
		return null;
	}
	/**
	 * 
	 * @param host
	 * @param val  (v1=XX&v2=XX)
	 * @param encode (urlEncode dealut=utf-8)
	 * @throws Exception
	 * 
	 */
	public static String getRet(String host, String param, String encode,String rpsEncode) {
		PrintWriter pw = null;
		BufferedReader in = null;
		HttpURLConnection conn=null;
		String params = param;
		URL getUrl;
		try {
			if (encode != null) {
				params = URLEncoder.encode(param, encode);
			}		
			if(param!=null&&param.length()>0){
				host+="?"+param;
			}
			getUrl = new URL(host);
			conn = (HttpURLConnection) getUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("get>>>>>>" + host + " params." + param);
			}
			int stat = conn.getResponseCode();
			if (stat != HTTP_CODE_OK) {
				logger.warn("get <<<<<<"+stat +" "+ host +  " params:" + params);
				return null;
			}
			// read
			in= new BufferedReader(new InputStreamReader(conn.getInputStream(),rpsEncode==null?DEFAULT_ENCODE:rpsEncode));
			StringBuffer temp = new StringBuffer();
			String line = null;
			while ((line=in.readLine())!= null) {
				temp.append(line);
			}
			return temp.toString();
		} catch (Exception e) {
			logger.error("get>>>>"+host,e);
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (in != null) {
					in.close();
				}
				if(conn!=null){
					conn.disconnect();
				}
			} catch (Exception e) {
				logger.error("get>>>>"+host,e);
			}

		}
		return null;
	}
	
	public static void post(String host, String param, String encode) {
		BufferedWriter pw = null;
		BufferedReader in = null;
		HttpURLConnection conn=null;
		String params = param;
		URL getUrl;
		try {
			if (encode == null) {
				encode="utf-8";
			}
			getUrl = new URL(host);
			conn = (HttpURLConnection) getUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("post >>>>>>" + host + " params." + param);
			}
			//write
			pw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(),encode));
			pw.write(params);
			pw.flush();
			int stat = conn.getResponseCode();
			if (stat != HTTP_CODE_OK) {
				logger.warn("post <<<<<<"+stat +" "+ host +  " params:" + params);
			}
		} catch (Exception e) {
			logger.error("post>>>>"+host,e);
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (in != null) {
					in.close();
				}
				if(conn!=null){
					conn.disconnect();
				}
			} catch (Exception e) {
				logger.error("post>>>>"+host,e);
			}

		}
	}
	/**
	 * 
	 * @param host
	 * @param val  (v1=XX&v2=XX)
	 * @param encode (urlEncode dealut=utf-8)
	 * @throws Exception
	 * 
	 */
	public void  get(String host, String param, String encode) {
		PrintWriter pw = null;
		BufferedReader in = null;
		HttpURLConnection conn=null;
		String params = param;
		URL getUrl;
		try {
			if (encode != null) {
				params = URLEncoder.encode(param, encode);
			}			
			getUrl = new URL(host+"?"+params);
			conn = (HttpURLConnection) getUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("get>>>>>>" + host + " params." + param);
			}
			int stat = conn.getResponseCode();
			if (stat != HTTP_CODE_OK) {
				logger.warn("get <<<<<<"+stat +" "+ host +  " params:" + params);
			}
		} catch (Exception e) {
			logger.error("get>>>>"+host,e);
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (in != null) {
					in.close();
				}
				if(conn!=null){
					conn.disconnect();
				}
			} catch (Exception e) {
				logger.error("get>>>>"+host,e);
			}

		}
	}
	
	public static void main(String[] args) {
		System.out.println(getRet("http://192.168.100.13:8000/GameDataHanlder/login.do",null, null,null));
	}
}
