/**
 * 
 */
package com.living.web.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author living.li
 * @date  2015年5月25日 下午5:55:54
 *
 *
 */
public class OauthRet implements Serializable{
	
	/** */
	private static final long serialVersionUID = 1L;

	private String status;
	
	private Map<String,Object> data;

	private Common common;
	
	private String ext;
	
	

	
	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public class Common{
		/**渠道编号*/
		private String channel;
		/**渠道SDK标识*/
		private String user_sdk;
		
		private String uid;
		
		private String server_id;

		public String getChannel() {
			return channel;
		}

		public void setChannel(String channel) {
			this.channel = channel;
		}

		public String getUser_sdk() {
			return user_sdk;
		}

		public void setUser_sdk(String user_sdk) {
			this.user_sdk = user_sdk;
		}

		public String getUid() {
			return uid;
		}

		public void setUid(String uid) {
			this.uid = uid;
		}

		public String getServer_id() {
			return server_id;
		}

		public void setServer_id(String server_id) {
			this.server_id = server_id;
		}

		@Override
		public String toString() {
			return "Common [channel=" + channel + ", user_sdk=" + user_sdk
					+ ", uid=" + uid + ", server_id=" + server_id + "]";
		}
		
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Common getCommon() {
		return common;
	}
	public void setCommon(Common common) {
		this.common = common;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}

	@Override
	public String toString() {
		return "OauthRet [status=" + status + ", data=" + data + ", common="
				+ common + ", ext=" + ext + "]";
	}
	
	
}
