package com.ks.model.account;

import java.io.Serializable;
import java.util.Date;

/***
 * 服务器信息
 * 
 * @author lipp 2015年6月23日
 */
public class ServerInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final int STATUS_关闭 = -1;
	public static final int STATUS_即将开区 = 0;
	public static final int STATUS_维护 = 1;
	public static final int STATUS_正常 = 2;
	public static final int STATUS_推荐 = 3;
	public static final int STATUS_火爆 = 4;

	private int id;

	/** 渠道 */
	private int partner;

	/** 服务器ID */
	private String serverId;

	/** 区服编号 */
	private int serverNo;

	/** 服务器名称 */
	private String name;

	/** 服务器版本 */
	private int version;

	/** 登陆服端口 */
	private int port;

	/** 登陆服Ip */
	private String ip;

	/** 中心服务器ip */
	private String worldIp;

	/** 中心服务器端口 */
	private int worldPort;

	/** 描述 */
	private String desc;

	/** 支付通知地址 */
	private String payNotifiUrl;

	/** 状态 (-1: 关闭， 0: 即将开区, 1: 维护，2: 正常, 3: 推荐, 4:火爆) */
	private int status;

	/** 1: 推荐 ， 0: 不推荐 */
	private int recommend;

	/** 服务器主区ID(0: 表示主区， 其他参数表示所属哪个区服) */
	private int mainServerId;

	/** 开服时间 */
	private Date startTime;

	/** 维护开始时间 */
	private Date maintainStartTime;

	/** 维护结束时间 */
	private Date maintainEndTime;

	/** 维护显示信息 */
	private String maintainMsg;

	/** 连续登陆周期，作为连续登陆活动使用 */
	private int continueLoginCycle;

	/** 创建时间 */
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPartner() {
		return partner;
	}

	public void setPartner(int partner) {
		this.partner = partner;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public int getServerNo() {
		return serverNo;
	}

	public void setServerNo(int serverNo) {
		this.serverNo = serverNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getWorldIp() {
		return worldIp;
	}

	public void setWorldIp(String worldIp) {
		this.worldIp = worldIp;
	}

	public int getWorldPort() {
		return worldPort;
	}

	public void setWorldPort(int worldPort) {
		this.worldPort = worldPort;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPayNotifiUrl() {
		return payNotifiUrl;
	}

	public void setPayNotifiUrl(String payNotifiUrl) {
		this.payNotifiUrl = payNotifiUrl;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getRecommend() {
		return recommend;
	}

	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}

	public int getMainServerId() {
		return mainServerId;
	}

	public void setMainServerId(int mainServerId) {
		this.mainServerId = mainServerId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getMaintainStartTime() {
		return maintainStartTime;
	}

	public void setMaintainStartTime(Date maintainStartTime) {
		this.maintainStartTime = maintainStartTime;
	}

	public Date getMaintainEndTime() {
		return maintainEndTime;
	}

	public void setMaintainEndTime(Date maintainEndTime) {
		this.maintainEndTime = maintainEndTime;
	}

	public String getMaintainMsg() {
		return maintainMsg;
	}

	public void setMaintainMsg(String maintainMsg) {
		this.maintainMsg = maintainMsg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getContinueLoginCycle() {
		return continueLoginCycle;
	}

	public void setContinueLoginCycle(int continueLoginCycle) {
		this.continueLoginCycle = continueLoginCycle;
	}

}
