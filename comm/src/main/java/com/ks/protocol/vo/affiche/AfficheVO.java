package com.ks.protocol.vo.affiche;

import java.util.List;

import com.ks.model.affiche.Affiche;
import com.ks.protocol.Message;
import com.ks.protocol.vo.items.GoodsVO;

public class AfficheVO extends Message {

	private static final long serialVersionUID = 1L;
	
	
	/**编号*/
	private int id;
	/**类型*/
	private int type;
	/**标题*/
	private String title;
	/**内容*/
	private String context;
	/**物品*/
	private List<GoodsVO> goodsList;
	/**状态*/
	private int state;
	/**创建时间*/
	private long createTime;	
	/**图标*/
	private String logo;
	
	
	public void init(Affiche o){
		this.id = o.getId();
		this.type = o.getType();
		this.title = o.getTitle();
		this.context = o.getContext();	
		this.state = o.getState();
		this.createTime = o.getCreateTime().getTime();
		this.logo=o.getLogo();
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public List<GoodsVO> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<GoodsVO> goodsList) {
		this.goodsList = goodsList;
	}
	
}
