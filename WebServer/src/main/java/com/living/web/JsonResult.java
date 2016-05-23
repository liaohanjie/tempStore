package com.living.web;

public class JsonResult {
	
	public static final int CODE_SUCCESS = 1;
	public static final int CODE_FAIL = 0;
	public static final int CODE_ERROR = -1;
	
	public static final String MSG_SUCCESS = "success";
	public static final String MSG_FAIL = "fail";
	public static final String MSG_ERROR = "error";

	private int code;
	private String msg;
	private Object obj;
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	public JsonResult() {
    }
	
	public JsonResult(int code, String msg) {
	    this.code = code;
	    this.msg = msg;
    }
	
	public static JsonResult success(){
		return new JsonResult(CODE_SUCCESS, MSG_SUCCESS);
	}
	
	public static JsonResult fail(){
		return new JsonResult(CODE_FAIL, MSG_FAIL);
	}
	
	public static JsonResult error(){
		return new JsonResult(CODE_ERROR, MSG_ERROR);
	}
}
