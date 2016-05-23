/**
 * 
 */
package com.living.web.core;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpHeaders;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.RolloverFileOutputStream;
import org.eclipse.jetty.util.component.AbstractLifeCycle;

import com.ks.logger.LoggerFactory;

/**
 * @author living.li
 * @date 2014年9月2日 下午5:20:34
 * 
 * 
 */
public class WebRequestLog extends AbstractLifeCycle implements RequestLog {

	private static final Logger regLogger = LoggerFactory
			.get(WebRequestLog.class);
	private static ThreadLocal<StringBuilder> _buffers = new ThreadLocal<StringBuilder>() {
		@Override
		protected StringBuilder initialValue() {
			return new StringBuilder(256);
		}
	};
	private boolean _extended;
	private boolean _append;
	private boolean _preferProxiedForAddress;
	private String _filenameDateFormat = null;
	private String[] _ignorePaths;
	private boolean _logLatency = false;
	private boolean _logCookies = false;
	private boolean _logServer = false;
	private boolean _logDispatch = false;
	private boolean _NOT_200_WARN = false;
	private transient PathMap _ignorePathMap;

	/* ------------------------------------------------------------ */
	/**
	 * Create request log object with default settings.
	 */
	public WebRequestLog() {
		_extended = true;
		_append = true;
	}

	public void SetNot200Warn(boolean value) {
		this._NOT_200_WARN = value;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Set the extended request log format flag.
	 * 
	 * @param extended
	 *            true - log the extended request information, false - do not
	 *            log the extended request information
	 */
	public void setExtended(boolean extended) {
		_extended = extended;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieve the extended request log format flag.
	 * 
	 * @return value of the flag
	 */
	public boolean isExtended() {
		return _extended;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Set append to log flag.
	 * 
	 * @param append
	 *            true - request log file will be appended after restart, false
	 *            - request log file will be overwritten after restart
	 */
	public void setAppend(boolean append) {
		_append = append;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieve append to log flag.
	 * 
	 * @return value of the flag
	 */
	public boolean isAppend() {
		return _append;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Set request paths that will not be logged.
	 * 
	 * @param ignorePaths
	 *            array of request paths
	 */
	public void setIgnorePaths(String[] ignorePaths) {
		_ignorePaths = ignorePaths;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieve the request paths that will not be logged.
	 * 
	 * @return array of request paths
	 */
	public String[] getIgnorePaths() {
		return _ignorePaths;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Controls logging of the request cookies.
	 * 
	 * @param logCookies
	 *            true - values of request cookies will be logged, false -
	 *            values of request cookies will not be logged
	 */
	public void setLogCookies(boolean logCookies) {
		_logCookies = logCookies;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieve log cookies flag
	 * 
	 * @return value of the flag
	 */
	public boolean getLogCookies() {
		return _logCookies;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Controls logging of the request hostname.
	 * 
	 * @param logServer
	 *            true - request hostname will be logged, false - request
	 *            hostname will not be logged
	 */
	public void setLogServer(boolean logServer) {
		_logServer = logServer;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieve log hostname flag.
	 * 
	 * @return value of the flag
	 */
	public boolean getLogServer() {
		return _logServer;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Controls logging of request processing time.
	 * 
	 * @param logLatency
	 *            true - request processing time will be logged false - request
	 *            processing time will not be logged
	 */
	public void setLogLatency(boolean logLatency) {
		_logLatency = logLatency;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieve log request processing time flag.
	 * 
	 * @return value of the flag
	 */
	public boolean getLogLatency() {
		return _logLatency;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Controls whether the actual IP address of the connection or the IP
	 * address from the X-Forwarded-For header will be logged.
	 * 
	 * @param preferProxiedForAddress
	 *            true - IP address from header will be logged, false - IP
	 *            address from the connection will be logged
	 */
	public void setPreferProxiedForAddress(boolean preferProxiedForAddress) {
		_preferProxiedForAddress = preferProxiedForAddress;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieved log X-Forwarded-For IP address flag.
	 * 
	 * @return value of the flag
	 */
	public boolean getPreferProxiedForAddress() {
		return _preferProxiedForAddress;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Set the log file name date format.
	 * 
	 * @see RolloverFileOutputStream#RolloverFileOutputStream(String, boolean,
	 *      int, TimeZone, String, String)
	 * 
	 * @param logFileDateFormat
	 *            format string that is passed to
	 *            {@link RolloverFileOutputStream}
	 */
	public void setFilenameDateFormat(String logFileDateFormat) {
		_filenameDateFormat = logFileDateFormat;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieve the file name date format string.
	 * 
	 * @return the log File Date Format
	 */
	public String getFilenameDateFormat() {
		return _filenameDateFormat;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Controls logging of the request dispatch time
	 * 
	 * @param value
	 *            true - request dispatch time will be logged false - request
	 *            dispatch time will not be logged
	 */
	public void setLogDispatch(boolean value) {
		_logDispatch = value;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Retrieve request dispatch time logging flag
	 * 
	 * @return value of the flag
	 */
	public boolean isLogDispatch() {
		return _logDispatch;
	}

	/* ------------------------------------------------------------ */
	/**
	 * Writes the request and response information to the output stream.
	 * 
	 * @see org.eclipse.jetty.server.RequestLog#log(org.eclipse.jetty.server.Request,
	 *      org.eclipse.jetty.server.Response)
	 */
	public void log(Request request, Response response) {
		try {
			boolean warn = false;
			if (_ignorePathMap != null
					&& _ignorePathMap.getMatch(request.getRequestURI()) != null)
				return;

			StringBuilder buf = _buffers.get();
			buf.setLength(0);

			if (_logServer) {
				buf.append(request.getServerName());
				buf.append(' ');
			}

			String addr = null;
			if (_preferProxiedForAddress) {
				addr = request.getHeader(HttpHeaders.X_FORWARDED_FOR);
			}

			if (addr == null) {
				addr = request.getRemoteAddr();
			}

			buf.append(addr);
			buf.append("-");
			Authentication authentication = request.getAuthentication();
			if (authentication instanceof Authentication.User) {
				buf.append(((Authentication.User) authentication)
						.getUserIdentity().getUserPrincipal().getName());
			} else {
				buf.append(" - ");
			}
			if (request.getAsyncContinuation().isInitial()) {
				int status = response.getStatus();
				if (status <= 0) {
					status = 404;
				}
				if (status>=400) {
					warn = true;
				}
				buf.append((char) ('0' + ((status / 100) % 10)));
				buf.append((char) ('0' + ((status / 10) % 10)));
				buf.append((char) ('0' + (status % 10)));
			} else {
				buf.append("Async");
			}
			buf.append(' ');
			buf.append(request.getMethod());
			buf.append(' ');
			buf.append(request.getUri().toString());
			buf.append(' ');
			buf.append(request.getProtocol());
			buf.append("\" ");

			long responseLength = response.getContentCount();
			if (responseLength >= 0) {
				buf.append(' ');
				if (responseLength > 99999)
					buf.append(responseLength);
				else {
					if (responseLength > 9999)
						buf.append((char) ('0' + ((responseLength / 10000) % 10)));
					if (responseLength > 999)
						buf.append((char) ('0' + ((responseLength / 1000) % 10)));
					if (responseLength > 99)
						buf.append((char) ('0' + ((responseLength / 100) % 10)));
					if (responseLength > 9)
						buf.append((char) ('0' + ((responseLength / 10) % 10)));
					buf.append((char) ('0' + (responseLength) % 10));
				}
				buf.append(' ');
			} else
				buf.append(" - ");

			if (_extended)
				logExtended(request, response, buf);

			if (_logCookies) {
				Cookie[] cookies = request.getCookies();
				if (cookies == null || cookies.length == 0)
					buf.append(" -");
				else {
					buf.append(" \"");
					for (int i = 0; i < cookies.length; i++) {
						if (i != 0)
							buf.append(';');
						buf.append(cookies[i].getName());
						buf.append('=');
						buf.append(cookies[i].getValue());
					}
					buf.append('\"');
				}
			}

			if (_logDispatch || _logLatency) {
				long now = System.currentTimeMillis();

				if (_logDispatch) {
					long d = request.getDispatchTime();
					buf.append(' ');
					buf.append(now - (d == 0 ? request.getTimeStamp() : d));
				}

				if (_logLatency) {
					buf.append(' ');
					buf.append(now - request.getTimeStamp());
				}
			}
			String log = buf.toString();
			// write(log);
			if (warn&&_NOT_200_WARN) {
				regLogger.warn(log);
			} else {
				regLogger.info(log);
			}
		} catch (IOException e) {
			regLogger.warn(e);
		}
	}

	/* ------------------------------------------------------------ */
	/**
	 * Writes extended request and response information to the output stream.
	 * 
	 * @param request
	 *            request object
	 * @param response
	 *            response object
	 * @param b
	 *            StringBuilder to write to
	 * @throws IOException
	 */
	protected void logExtended(Request request, Response response,
			StringBuilder b) throws IOException {
		String referer = request.getHeader(HttpHeaders.REFERER);
		if (referer == null)
			b.append("\"-\" ");
		else {
			b.append('"');
			b.append(referer);
			b.append("\" ");
		}

		String agent = request.getHeader(HttpHeaders.USER_AGENT);
		if (agent == null)
			b.append("\"-\" ");
		else {
			b.append('"');
			b.append(agent);
			b.append('"');
		}
	}

}
