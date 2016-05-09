/**
 * 
 */
package com.ks.model.pay;

import java.io.Serializable;
import java.util.Date;

import com.ks.util.MD5Util;

/**
 * @author living.li
 * @date  2014年10月13日 下午4:48:35
 *
 *
 */
public class PayOrder implements Serializable {

	/**
	 * 
	 */
	public final static int RETRY_TIMES_MAX=6;
	
	public final static int RETRY_TIMES_SPLIT=1000*60*30;
	
	public final static String SIGN_KEY="aljoxljajsdfieoi924839";
	
	public  final static int STATUS_INIT=0;
	public  final static int STATUS_成功通知渠道=1;
	public  final static int STATUS_发货完成=2;
	public  final static int STATUS_放弃发货=3;
	public  final static int STATUS_失败=4;
	public  final static int STATUS_订单不存在=5;
	public  final static int STATUS_保存苹果票据=6;
	
	/**成功*/
	public final static String NOTIFI_CODE_SUCESS="001";
	/**无权访问*/
	public final static String NOTIFI_CODE_FORBIDDEN="002";
	/**参数不对*/
	public final static String NOTIFI_CODE_参数错误="003";
	/**签名验证失败*/
	public final static String NOTIFI_CODE_签名验证失败="004";
	/**应用处理出现错误*/
	public final static String NOTIFI_CODE_应用处理出现错误="005";

	public final static String NOTIFI_CODE_服务器不存在="006";
	
	public final static String NOTIFI_CODE_通知无响应="007";
	
	public final static String NOTIFI_CODE_订单不存在="008";
	
	/**充值商品ID 魂钻*/
	public final static int GOODS_ID_CURRENCY = 0;
	/**充值商品ID 黄金月卡*/
	public final static int GOODS_ID_GOLD_MONTH_CARD = 1;
	/**充值商品ID 钻石月卡*/
	public final static int GOODS_ID_DIAMOND_MONTH_CARD = 2;
	
	private static final long serialVersionUID = 1L;
	
	private int payId;
	
	private String orderNo;
	
	private int gameCoin;
	
	private int amount;
	/**服务器*/
	private String serverId;
	
	/***userId**/
	private int userId;
	
	/**用户名*/
	private String userName;
	/**用户渠道*/
	private int  userParnter;
	/**重试*/
	private int retryTimes;
	/**状态*/
	private int status;
	/**订单创建时间*/
	private Date createTime;
	/**支付成功时间*/
	private Date paySucessTime;
	/**成功发货时间*/
	private Date deliverySucessTime;
	/**最后重试时间*/
	private Date lastRetryTtime;
	/**修改时间*/
	 private Date updateTime;
	 /**最后返回的错误码*/
	 private String lastRetCode;
	 /**送的游戏币*/
	 private int giftCoin;
	 /**支付渠道配置ID*/
	 private int payConfigId;
	 /**商品编号 0: 魂钻， 1: 黄金月卡， 2: 钻石月卡*/
	 private int goodsId;
	 /**渠道商订单号*/
	 private String billNo;
	 /**扩展字段1*/
	 private String ext1;
	 /**扩展字段2*/
	 private String ext2;
	 /**扩展字段3*/
	 private String ext3;
	 
	 public int getGiftCoin() {
		return giftCoin;
	}
	public void setGiftCoin(int giftCoin) {
		this.giftCoin = giftCoin;
	}
	public static String getNofiSign(String serverId,String userId,String userName,String partner,String gameCoin,String amount,String orderNo, String goodsId){
		 return MD5Util.decode(serverId+userId+userName+partner+gameCoin+amount+orderNo+goodsId+SIGN_KEY).toLowerCase();
	 }
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLastRetCode() {
		return lastRetCode;
	}

	public void setLastRetCode(String lastRetCode) {
		this.lastRetCode = lastRetCode;
	}

	public Date getLastRetryTtime() {
		return lastRetryTtime;
	}

	public void setLastRetryTtime(Date lastRetryTtime) {
		this.lastRetryTtime = lastRetryTtime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getPaySucessTime() {
		return paySucessTime;
	}

	public void setPaySucessTime(Date paySucessTime) {
		this.paySucessTime = paySucessTime;
	}

	public Date getDeliverySucessTime() {
		return deliverySucessTime;
	}

	public void setDeliverySucessTime(Date deliverySucessTime) {
		this.deliverySucessTime = deliverySucessTime;
	}

	public int getPayId() {
		return payId;
	}

	public void setPayId(int payId) {
		this.payId = payId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public int getGameCoin() {
		return gameCoin;
	}

	public void setGameCoin(int gameCoin) {
		this.gameCoin = gameCoin;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getRetryTimes() {
		return retryTimes;
	}

	public int getUserParnter() {
		return userParnter;
	}
	public void setUserParnter(int userParnter) {
		this.userParnter = userParnter;
	}
	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getPayConfigId() {
		return payConfigId;
	}
	public void setPayConfigId(int payConfigId) {
		this.payConfigId = payConfigId;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public String getExt3() {
		return ext3;
	}
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	@Override
    public String toString() {
	    return "PayOrder [payId=" + payId + ", orderNo=" + orderNo + ", gameCoin=" + gameCoin + ", amount=" + amount + ", serverId=" + serverId + ", userId=" + userId + ", userName=" + userName
	            + ", userParnter=" + userParnter + ", retryTimes=" + retryTimes + ", status=" + status + ", createTime=" + createTime + ", paySucessTime=" + paySucessTime + ", deliverySucessTime="
	            + deliverySucessTime + ", lastRetryTtime=" + lastRetryTtime + ", updateTime=" + updateTime + ", lastRetCode=" + lastRetCode + ", giftCoin=" + giftCoin + ", payConfigId=" + payConfigId
	            + ", goodsId=" + goodsId + ", billNo=" + billNo + ", ext1=" + ext1 + ", ext2=" + ext2 + ", ext3=" + ext3 + "]";
    }

}
