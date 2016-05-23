/**
 * 
 */
package com.living.web.core;

import org.apache.log4j.Level;
import org.eclipse.jetty.util.log.Logger;

import com.ks.logger.LoggerFactory;

/**
 * @author living.li
 * @date 2014年9月2日 下午4:31:55
 * 
 * 
 */
public class JettyLog implements org.eclipse.jetty.util.log.Logger {
	private org.apache.log4j.Logger logger = LoggerFactory.get(JettyLog.class);

	@Override
	public void debug(Throwable arg0) {
		logger.debug(arg0);
	}

	@Override
	public void debug(String arg0, Object... arg) {
		StringBuffer buff = new StringBuffer();
		buff.append(arg0 + ":\n");
		int i = 0;
		for (Object o : arg) {
			buff.append("  [" + i + "]").append(o);
		}
		logger.debug(buff);
	}

	@Override
	public void debug(String arg0, Throwable arg1) {
		logger.debug(arg0, arg1);
		;
	}

	@Override
	public Logger getLogger(String arg0) {
		return this;
	}

	@Override
	public String getName() {
		return "jettyLog";
	}

	@Override
	public void ignore(Throwable arg0) {
		logger.warn(arg0);
	}

	@Override
	public void info(Throwable arg0) {
		logger.info(arg0);
	}

	@Override
	public void info(String arg0, Object... arg1) {
		StringBuffer buff = new StringBuffer();
		buff.append(arg0 + ":\n");
		int i = 0;
		for (Object o : arg1) {
			buff.append("  [" + i + "]").append(o);
		}
		logger.info(buff);
	}

	@Override
	public void info(String arg0, Throwable arg1) {
		logger.info(arg0, arg1);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public void setDebugEnabled(boolean arg0) {
		logger.setLevel(Level.DEBUG);
	}

	@Override
	public void warn(Throwable message) {
		logger.warn(message);
	}
	@Override
	public void warn(String arg0, Object... arg1) {
		StringBuffer buff = new StringBuffer();
		buff.append(arg0 + ":\n");
		int i = 0;
		for (Object o : arg1) {
			buff.append("  [" + i + "]").append(o);
		}
		logger.warn(buff);

	}
	@Override
	public void warn(String arg0, Throwable arg1) {
		logger.warn(arg0,arg1);
	}

}
