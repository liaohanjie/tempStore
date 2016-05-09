package com.ks.timer;
/**
 * 定时器异常
 * @author ks
 *
 */
public class TimerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TimerException() {
		super();
	}

	public TimerException(String message, Throwable cause) {
		super(message, cause);
	}

	public TimerException(String message) {
		super(message);
	}

	public TimerException(Throwable cause) {
		super(cause);
	}
	
}
