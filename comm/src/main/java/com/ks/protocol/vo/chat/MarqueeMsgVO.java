package com.ks.protocol.vo.chat;

import com.ks.model.chat.MarqueeMsg;
import com.ks.protocol.Message;

/**
 * 跑马灯内容
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年2月29日
 */
public class MarqueeMsgVO extends Message {

    private static final long serialVersionUID = 8676322196723635466L;
    
	/**内容*/
	private String content;
	
	public void init(MarqueeMsg o){
		this.content = o.getContent();
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
