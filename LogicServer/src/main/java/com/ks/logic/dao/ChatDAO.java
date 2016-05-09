package com.ks.logic.dao;

import java.util.ArrayList;
import java.util.List;

import com.ks.access.GameDAOTemplate;
import com.ks.model.chat.ChatMessage;
import com.ks.util.JSONUtil;

/**
 * 聊天DAO
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年12月28日
 */
public class ChatDAO extends GameDAOTemplate {

	private static final String CHAT_CACHE = "CHAT_";

	// private static final ObjectFieldMap<ChatMessage> FIELD_MAP = new
	// ObjectFieldMap<ChatMessage>() {
	// @Override
	// public Map<String, String> objectToMap(ChatMessage o) {
	// Map<String, String> map = new HashMap<String, String>();
	// map.put("type", String.valueOf(o.getType()));
	// map.put("sendUserId", String.valueOf(o.getSendUserId()));
	// map.put("receiverId", String.valueOf(o.getReceiverId()));
	// map.put("content", String.valueOf(o.getContent()));
	// map.put("createTime", String.valueOf(o.getCreateTime()));
	// return map;
	// }
	// };
	//
	// private static final JedisRowMapper<ChatMessage> ROWMAPPER = new
	// JedisRowMapper<ChatMessage>() {
	// @Override
	// public ChatMessage rowMapper(JedisResultSet jrs) {
	// ChatMessage obj = new ChatMessage();
	// obj.setType(jrs.getByte("type"));
	// obj.setSendUserId(jrs.getInt("sendUserId"));
	// obj.setReceiverId(jrs.getInt("receiverId"));
	// obj.setContent(jrs.getString("content"));
	// obj.setCreateTime(jrs.getLong("createTime"));
	// return obj;
	// }
	// };

	private String _getKey(int userId) {
		return CHAT_CACHE + userId;
	}

	/**
	 * 添加到缓存队列
	 * 
	 * @param entity
	 */
	public void addCache(ChatMessage entity) {
		rpush(_getKey(entity.getReceiverId()), JSONUtil.toJson(entity));
		// 90天自动删除
		// expire(_getKey(entity.getReceiverId()), 3600*24*90);
	}

	/**
	 * 查找用户所有聊天信息
	 * 
	 * @param userId
	 * @return
	 */
	public List<ChatMessage> queryByUserIdCache(int userId) {
		List<ChatMessage> list = new ArrayList<>();
		List<String> _list = lrange(_getKey(userId));
		for (String input : _list) {
			ChatMessage o = JSONUtil.toObject(input, ChatMessage.class);
			list.add(o);
		}
		return list;
	}

	/**
	 * 用户聊天信息数量
	 * 
	 * @param userId
	 * @return
	 */
	public long findLengthByUserIdCache(int userId) {
		return llen(_getKey(userId));
	}

	/**
	 * 删除用户所有聊天
	 * 
	 * @param userId
	 */
	public void deleteAllByUserIdCache(int userId) {
		del(_getKey(userId));
	}

	/**
	 * 删除 left 队头元素
	 * 
	 * @param userId
	 * @return
	 */
	public ChatMessage deleteLeftByUserId(int userId) {
		String value = rpop(_getKey(userId));

		try {
			if (value != null && !value.trim().equals("")) {
				return JSONUtil.toObject(value, ChatMessage.class);
			}
		} catch (Exception e) {
		}
		return null;
	}

}