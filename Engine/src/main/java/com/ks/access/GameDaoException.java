package com.ks.access;

public class GameDaoException extends RuntimeException {

	private static final long serialVersionUID = -5979608812674088701L;

	public GameDaoException(String message){
		super(message);
	}
	
	public GameDaoException(){
		super();
	}
	
	public GameDaoException(String msg,Throwable cause){
		super(msg, cause);
	}
}
