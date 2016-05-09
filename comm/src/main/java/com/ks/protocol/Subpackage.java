package com.ks.protocol;

import java.util.Date;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

import com.ks.app.Application;
import com.ks.exceptions.GameException;
import com.ks.game.model.Player;
import com.ks.handler.GameHandler;
import com.ks.logger.LoggerFactory;
import com.ks.manager.PlayerManager;
import com.ks.protocol.codec.message.obj.MessageObject;
import com.ks.protocol.main.MainCMD;
import com.ks.protocol.sub.ErrorCodeCMD;
import com.ks.protocol.vo.Head;
import com.ks.util.MD5Util;

/**
 * 解包
 * 
 * @author ks
 * 
 */
public final class Subpackage {

	private static final Logger logger = LoggerFactory.get(Subpackage.class);
	/***/
	private GameHandler gameHandler;
	/** 数据区最大长度 */
	private static final short MAX_DATA_LENGTH = 1024;

	public Subpackage(GameHandler gameHandler) {
		super();
		this.gameHandler = gameHandler;
	}

	public void process(MessageObject obj){
		Head head = (Head) obj.getHead();
		try {
				if(checkSerializationCode(obj)){
					checkHead(head);
					loggerInfo(head);
					startHandler(head);
					try {
						processBefor(head);
						
						if(gameHandler.getPlayer()!=null && 
								gameHandler.getPlayer().getLastSendHead()!=null){
							if(((Head) gameHandler.getPlayer().getLastSendHead()) .getOrder() > head.getOrder()){
								logger.warn("error:userId=" + gameHandler.getPlayer().getUserId() + ",username=" + gameHandler.getPlayer().getUsername() +" OrderCode less than last OrderCode");
								return;
							}else if(((Head) gameHandler.getPlayer().getLastSendHead()) .getCheckVal() == head.getCheckVal()){
								Application.sendMessage(gameHandler.getChannel(), gameHandler.getPlayer().getLastSendHead(), gameHandler.getPlayer().getLastSendBody());
								return;
							}
						}
						Application.messageProcess.process(gameHandler, head,
								obj.getBuffers());
					} finally {
						endHandler();
					}
				}
		} catch (Exception e) {
			Exception ex = e;
			while (ex.getCause() != null) {
				ex = (Exception) ex.getCause();
			}
			if (ex instanceof GameException) {
				GameException gameException = (GameException) ex;
				head.init(MainCMD.ERROR_CODE, ErrorCodeCMD.GAME_ERROR);
				Application.sendMessage(gameHandler.getChannel(), head,
						gameException.getCode(), gameException.getMessage());
				logger.warn("game exception code : " + gameException.getCode()
						+ ",message : " + gameException.getMessage());
				return;
			} else {
				head.init(MainCMD.ERROR_CODE, ErrorCodeCMD.APP_ERROR);
				Application.sendMessage(gameHandler.getChannel(), head,
						ex.getMessage()+"");
				logger.error("",ex);
			}
		}
	}

	private void endHandler() {
		if(gameHandler.getPlayer()!=null){
			gameHandler.getPlayer().unlock();
		}
	}

	private void startHandler(Head head) {
		Player player=gameHandler.getPlayer();
		if(player!=null){
			player.lock();
			player.setLastMessageTime(new Date());
			head.setNofiti(player.getNofiti());
			player.setNofiti(0);
		}
	}

	private void loggerInfo(Head head) {
		logger.info(new StringBuilder()
				.append('[')
				.append(gameHandler.getPlayer())
				.append('@')
				.append(gameHandler.getChannel().getRemoteAddress())
				.append("] ").append("exec cmd : ")
				.append(head.getMainCmd()).append("-")
				.append(head.getSubCmd()));
	}

	/**
	 * 消息处理之前
	 * 
	 * @param head
	 *            包头
	 */
	private void processBefor(Head head) {
		gameHandler.setHead(head);
	}

	/**
	 * 检查包头
	 * 
	 * @param head
	 *            包头
	 * @return 是否合法
	 */
	private void checkHead(Head head) {
		if (head.getLength() > MAX_DATA_LENGTH) {
			throw new GameException(GameException.CODE_协议异常, "");
		}
		if (Application.SERVER_TYPE == Application.GAME_SERVER) {
			Player player = PlayerManager.getOnlinePlayer(head.getSessionId());
			if (player == null) {
				throw new GameException(GameException.CODE_已经掉线, "");
			}
			player.setGameHandler(gameHandler);
			gameHandler.setPlayer(player);
			gameHandler.getChannel().setAttachment(player);
		}
	}
	
	/**
	 * 校验加密协议码
	 * @param obj
	 */
	private boolean checkSerializationCode(MessageObject obj){
		Head head = (Head) obj.getHead();
		ChannelBuffer frame = obj.getBuffers();
		long v1 = 0;
		long v2 = 0;
		if(head.getLength() > 0){
			//拷贝一份byte[]避免修改到frame的内容
			byte[] bytes = frame.copy().array();
			
			//加密序列号
			long order = head.getOrder();
			byte[] obytes = getBytes(order);
			
			//加密数据部分
			int len = Math.min(obytes.length , bytes.length);
			for(int i = 0; i < len; i++){
				bytes[i] |= obytes[i];
			}
			byte[] bs = MD5Util.encodeBytes(bytes);
			
			//得到加密校验码
			ChannelBuffer cbuff = ChannelBuffers.wrappedBuffer(ChannelBuffers.LITTLE_ENDIAN, bs);
			v1 = cbuff.readLong();
			v2 = cbuff.readLong();
		}
		
		//对比前端的加密校验码
		if(v1 != head.getSerialization1() || v2 != head.getSerialization2()){
			String error = null;
			if(gameHandler.getPlayer() != null){
				error = "error:userId=" + gameHandler.getPlayer().getUserId() + ",username=" + gameHandler.getPlayer().getUsername() +" check Serialization code error";
			}else{
				error = "error:sessionId=" + gameHandler.getHead().getSessionId() + " check Serialization code error";
			}
			logger.warn(error);
//			throw new GameException(GameException.CODE_外挂, "");
			return false;
		}
		return true;
	}
	
	/**
	 * 小端方式序列化long数据
	 * @param data
	 * @return
	 */
	private static byte[] getBytes(long data){
		byte[] bytes = new byte[8];
		for(int i=0 ; i<8; i++){
			bytes[i] = (byte) ((data >> 8*i) & 0xff);
		}
		return bytes;
	}
}
