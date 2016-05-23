package com.living.pay.anysdk;

import java.util.Map;

public class AnySdkAuthResult {
	
	/**状态: ok fail*/
	private String status;
	/**id, name, avatar, sex, area, sex, area, nick ,access_token, refresh_token*/
	private Map<String, Object> data;
	/**channel, user_sdk, uid, server_id*/
	private Map<String, Object> comm;
	/***/
	private String ext;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public Map<String, Object> getComm() {
		return comm;
	}
	public void setComm(Map<String, Object> comm) {
		this.comm = comm;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	
}
