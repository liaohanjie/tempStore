package com.ks.wrold.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ks.action.logic.ChatAction;
import com.ks.action.logic.UserAction;
import com.ks.action.logic.UserTeamAction;
import com.ks.action.world.WorldChatAction;
import com.ks.app.Application;
import com.ks.exceptions.GameException;
import com.ks.logger.LoggerFactory;
import com.ks.model.chat.ChatMessage;
import com.ks.model.chat.MarqueeMsg;
import com.ks.model.chat.SendChatType;
import com.ks.model.user.User;
import com.ks.protocol.MessageFactory;
import com.ks.protocol.vo.chat.ChatMsgRequestVO;
import com.ks.protocol.vo.chat.ChatMsgResponseVO;
import com.ks.protocol.vo.chat.MarqueeMsgVO;
import com.ks.protocol.vo.user.UserCapVO;
import com.ks.rpc.RPCKernel;
import com.ks.wrold.kernel.ChatCache;
import com.ks.wrold.kernel.MarqueeCache;
import com.ks.wrold.kernel.PlayerStaticInfo;
import com.ks.wrold.kernel.WorldServerCache;

public final class WorldChatActionImpl implements WorldChatAction {

	private Logger logger = LoggerFactory.get(getClass());
	
	/**发送间隔*/
	static long MAX_SEND_SPAN_TIME = 1000*15;
	
	@Override
    public void send(ChatMsgRequestVO request) {
		
		long now = System.currentTimeMillis();
		
		UserAction userAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserAction.class);
		User user = userAction.findUserByUserId(request.getSendUserId());
		if (user == null) {
			logger.warn(String.format("userId=%s not found", request.getSendUserId()));
			return;
		}
		
		if (user.getBanAccountTime() != null && user.getBanAccountTime().getTime() > now) {
			logger.warn(String.format("userId=%s has banned", request.getSendUserId()));
			return;
		}
		
		if (user.getBanChatTime() != null && user.getBanChatTime().getTime() > now) {
			logger.warn(String.format("userId=%s has banned chat", request.getSendUserId()));
			throw new GameException(GameException.CODE_禁言, "forbiden user chat. userId=" + user.getUserId() + ", time=" + user.getBanAccountTime());
		}
		
    	// 发送时间验证
    	PlayerStaticInfo player = WorldServerCache.getPlayerStaticInfoByUserId(request.getSendUserId());
    	if (player.getLastSendChatTime() != 0L && now - player.getLastSendChatTime() < MAX_SEND_SPAN_TIME && request.getType() == SendChatType.WORLD) {
    		return;
    	}
    	player.setLastSendChatTime(now);
    	
    	ChatMsgResponseVO vo = MessageFactory.getMessage(ChatMsgResponseVO.class);
    	vo.init(request.getType(), request.getContent(), user.getTotalCurrency(), System.currentTimeMillis(), null);
    	
    	ChatAction chatAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ChatAction.class);
		UserTeamAction userTeamAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserTeamAction.class);
		UserCapVO ucVO = userTeamAction.findUserCap(request.getSendUserId());
		vo.setUserCapVO(ucVO);
    	
	   if(request.getType() == SendChatType.WORLD) {
	    	ChatCache.add(vo);
	    } else if(request.getType() == SendChatType.PRIVATE) {
	    	chatAction.send(request);
	    } else if(request.getType() == SendChatType.UNION) {
	    	// ?
	    }
    }

	@Override
    public List<ChatMsgResponseVO> poll(int userId) {
		List<ChatMsgResponseVO> list = new ArrayList<>();
		
		PlayerStaticInfo player = WorldServerCache.getPlayerStaticInfoByUserId(userId);
		
		if (player != null) {
			ChatAction chatAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, ChatAction.class);
//			UserTeamAction userTeamAction = RPCKernel.getRemoteByServerType(Application.LOGIC_SERVER, UserTeamAction.class);
			
			List<ChatMsgResponseVO> copyList = new ArrayList<>();
			copyList.addAll(ChatCache.getMsgList());
			
			
			// 第一次轮询
			if(player.getLastPollMessageId() == 0L){
				//更新最后拉取id
				player.setLastPollMessageId(ChatCache.idGenerater.get());
			} else {
				//记录拉取最新的消息id
				long lastMessageId = player.getLastPollMessageId();
				
				// 获取 系统、世界、公会聊天信息
				for (ChatMsgResponseVO cmrVO : copyList) {
					if(cmrVO.getId() > player.getLastPollMessageId()) {
						
						//判断是否为最新消息
						lastMessageId = cmrVO.getId() > lastMessageId? cmrVO.getId() : lastMessageId;
						
						if (cmrVO.getType() == SendChatType.WORLD || cmrVO.getType() == SendChatType.UNION) {
							
							// 过滤自己发给自己的消息
							if (userId != 0 && cmrVO.getUserCapVO() != null && cmrVO.getUserCapVO().getUserId() == userId) {
								continue;
							}
						}
						list.add(cmrVO);
						
					}
				}
				// 获取密语/私信
				list.addAll(chatAction.poll(userId));
				
				player.setLastPollMessageId(lastMessageId);
			}
		}
	    return list;
    }

	@Override
    public void systemSend(ChatMessage request) {
		ChatMsgResponseVO vo = MessageFactory.getMessage(ChatMsgResponseVO.class);
		vo.init(request.getType(), request.getContent(), 0, System.currentTimeMillis(), null);
		ChatCache.add(vo);
	}

	@Override
	public List<String> getWorldChatMsgs(int length) {
		List<String> list = new ArrayList<>();
		List<ChatMsgResponseVO> copyMsg = new ArrayList<>(ChatCache.getMsgList());
		
		if (length <= 1) {
			length = copyMsg.size();
		}
		
		if (length > copyMsg.size()) {
			length = copyMsg.size();
		}
		
		for (int i=length - 1; i>=0; i--) {
			ChatMsgResponseVO vo = copyMsg.get(i);
			UserCapVO uc = vo.getUserCapVO();
			
			String content = vo.getContent();
			if(uc != null) {
				content = uc.getPlayerName() + "[ " + uc.getUserId() +  "]:" + content;
			}
			list.add(content);
		}
		return list;
	}

	@Override
    public List<MarqueeMsgVO> pollMarquee(int userId) {
		List<MarqueeMsgVO> list = new ArrayList<>();
		
		PlayerStaticInfo player = WorldServerCache.getPlayerStaticInfoByUserId(userId);
		if(player != null) {
			long lastPollTime = player.getLastPollMaqueeTime();
			
			if(player.getLastPollMaqueeTime() != 0L) {
				for (MarqueeMsg entity : MarqueeCache.poll(lastPollTime)) {
					MarqueeMsgVO vo = MessageFactory.getMessage(MarqueeMsgVO.class);
					vo.init(entity);
					list.add(vo);
				}
			}
			player.setLastPollMaqueeTime(System.currentTimeMillis());
		}
	    return list;
    }

	@Override
    public void addMarquee(MarqueeMsg entity) {
	    MarqueeCache.add(entity);
    }

}