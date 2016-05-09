package com.sf.data.util;

import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

	/**
	 * 
	 * @param host
	 * @param val
	 *            (v1=XX&v2=XX)
	 * @param encode
	 *            (urlEncode dealut=utf-8)
	 * @throws Exception
	 * 
	 */
	public static String post(String host, String param, String encode) {
		PrintWriter pw = null;
		InputStream in = null;
		HttpURLConnection conn = null;
		String params = param;
		URL getUrl;
		try {
			getUrl = new URL(host);
			conn = (HttpURLConnection) getUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("post>>>>>>" + host + " params." + param);
			}
			// write
			pw = new PrintWriter(conn.getOutputStream());
			pw.write(params);
			pw.flush();
			int stat = conn.getResponseCode();
			if (stat != HTTP_CODE_OK) {
				logger.warn("post <<<<<<" + stat + " " + host + " params:"
						+ params);
				return null;
			}
			// read
			in = conn.getInputStream();
			byte[] byteB = new byte[conn.getContentLength()];
			in.read(byteB);
			return new String(byteB);
		} catch (Exception e) {
			logger.error("post>>>>" + host, e);
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (in != null) {
					in.close();
				}
				if (conn != null) {
					conn.disconnect();
				}
			} catch (Exception e) {
				logger.error("post>>>>" + host, e);
			}

		}
		return null;
	}

	/**
	 * 
	 * @param host
	 * @param val
	 *            (v1=XX&v2=XX)
	 * @param encode
	 *            (urlEncode dealut=utf-8)
	 * @throws Exception
	 * 
	 */
	public static String get(String host, String param, String encode) {
		PrintWriter pw = null;
		InputStream in = null;
		HttpURLConnection conn = null;
		String params = param;
		URL getUrl;
		try {
			getUrl = new URL(host + "?" + params);
			conn = (HttpURLConnection) getUrl.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);
			conn.connect();
			if (logger.isDebugEnabled()) {
				logger.debug("get>>>>>>" + getUrl + " params." + param);
			}
			int stat = conn.getResponseCode();
			if (stat != HTTP_CODE_OK) {
				logger.warn("get <<<<<<" + stat + " " + host + " params:"
						+ params);
				return null;
			}
			// read
			in = conn.getInputStream();
			byte[] byteB = new byte[conn.getContentLength()];
			in.read(byteB);
			return new String(byteB);
		} catch (Exception e) {
			logger.error("get>>>>" + host, e);
		} finally {
			try {
				/*if (pw != null) {
					pw.close();
				}
				if (in != null) {
					in.close();
				}
				if (conn != null) {
					conn.disconnect();
				}*/
			} catch (Exception e) {
				logger.error("get>>>>" + host, e);
			}

		}
		return null;
	}
	
	class cun  implements Runnable{
		
		private int i;
		public cun(int i){
			this.i=i;
		}
		@Override
		public void run() {
//			for( int j=0;j<=1000;j++){
				String ss=HttpUtil.get("http://127.0.0.1:8001", "?username="+i, "utf-8");
				 ss=ss.substring(ss.indexOf("%3D")+3);
				System.out.println(ss.equals(i+""));
//			}
		}
		
	}
	public static void main(String[] args) {
		for(int i=0;i<=100;i++){
			new Thread( new HttpUtil().new cun(i)).start();;
		}
	}
}
