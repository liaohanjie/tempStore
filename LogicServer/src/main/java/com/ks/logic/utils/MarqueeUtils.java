package com.ks.logic.utils;

import java.util.Date;

import com.ks.model.chat.MarqueeMsg;
import com.ks.model.soul.Soul;


/**
 * 跑马灯工具
 * 
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2016年2月29日
 */
public class MarqueeUtils {
	
	static String FORMAT_BASE = "<font color=%s>%s</font>";
	static String COLOR_PLAYER = "lblue";
	static String COLOR_EQUIPMENT = "lyellow";
	static String COLOR_BOSS = "lred";
	static String COLOR_SOUL = "lpurple";
	static String COLOR_MSG = "lgreen";
	
	public static MarqueeMsg createSoulMarquee(String playerName, String soulName, int rare) {
		MarqueeMsg entity = new MarqueeMsg();
		playerName = _getPlayerName(playerName);
		soulName = String.format(FORMAT_BASE, COLOR_SOUL, soulName);
		entity.setContent(String.format(FORMAT_BASE, COLOR_MSG, String.format("%s刚刚获得了%s星战魂%s,在冒险的道路上又增加了一名强有力的伙伴", playerName, rare, soulName)));
		entity.setCreateTime(new Date());
		return entity;
	}
	
	public static MarqueeMsg createEquipmentMarquee(String playerName, String equipmentName) {
		MarqueeMsg entity = new MarqueeMsg();
		playerName = _getPlayerName(playerName);
		equipmentName = String.format(FORMAT_BASE, COLOR_EQUIPMENT, equipmentName);
		entity.setContent(String.format(FORMAT_BASE, COLOR_MSG, String.format("通过一次次锲而不舍的战斗,%s玩家终于获得了%s", playerName, equipmentName)));
		entity.setCreateTime(new Date());
		return entity;
	}
	
	public static MarqueeMsg createBossMarquee(String playerName, String monsterName, int level) {
		MarqueeMsg entity = new MarqueeMsg();
		playerName = _getPlayerName(playerName);
		monsterName = String.format(FORMAT_BASE, COLOR_BOSS, monsterName);
		entity.setContent(String.format(FORMAT_BASE, COLOR_MSG, String.format("%s玩家经过不懈的努力终于斩获%s等级%s世界BOSS", playerName, level, monsterName)));
		entity.setCreateTime(new Date());
		return entity;
	}
	
	private static String _getPlayerName(String name){
		return String.format(FORMAT_BASE, COLOR_PLAYER, name);
	}
	
	public static void main(String[] args) {
	    System.out.println(createSoulMarquee("大黄", "大黄蜂", 5).getContent());
	    System.out.println(createEquipmentMarquee("大黄", "开山斧").getContent());
	    System.out.println(createBossMarquee("大黄", "地狱魔王", 10).getContent());
    }
}
