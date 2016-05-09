package com.ks.model.dungeon;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ks.exceptions.GameException;
import com.ks.model.goods.Goods;
/**
 * 当前战斗
 * @author ks
 */
public class CurrFight implements Serializable {

	private static final long serialVersionUID = 1L;
	/**用户编号*/
	private int userId;
	/**副本编号*/
	private int chapterId;
	/**友情点*/
	private int friendlyPoint;
	/**奖励*/
	private List<Goods> awards;
	/**箱子*/
	private List<Box> boxes;
	
	private List<Integer> monsters;
	/**经验*/
	private int exp;
	/**乱入奖励 */
	private List<Goods> joinGoods;
	/**乱入怪物*/
	private String joinMonsters;
	/**战斗好友ID**/
	private int  friendId;
	/**复活次数*/
	private int resurrectionNum;
	
	public int getFriendId() {
		return friendId;
	}

	public void setFriendId(int friendId) {
		this.friendId = friendId;
	}

	public String getJoinMonsters() {
		return joinMonsters;
	}

	public void setJoinMonsters(String joinMonsters) {
		this.joinMonsters = joinMonsters;
	}

	public List<Goods> getJoinGoods() {
		return joinGoods;
	}

	public void setJoinGoods(List<Goods> joinGoods) {
		this.joinGoods = joinGoods;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public List<Integer> getMonsters() {
		return monsters;
	}

	public void setMonsters(List<Integer> monsters) {
		this.monsters = monsters;
	}

	public CurrFight(){	
		setMonsters(new ArrayList<Integer>());
		setAwards(new ArrayList<Goods>());
		setBoxes(new ArrayList<Box>());
		setJoinGoods(new ArrayList<Goods>());
	}
	
	public Box getBox(int boxId){
		for(Box b : boxes){
			if(b.getBoxId()==boxId){
				return b;
			}
		}
		throw new GameException(GameException.CODE_参数错误, "boxId : "+ boxId);
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}	
	public int getChapterId() {
		return chapterId;
	}

	public void setChapterId(int chapterId) {
		this.chapterId = chapterId;
	}

	public List<Goods> getAwards() {
		return awards;
	}
	public void setAwards(List<Goods> awards) {
		this.awards = awards;
	}
	public List<Box> getBoxes() {
		return boxes;
	}
	public void setBoxes(List<Box> boxes) {
		this.boxes = boxes;
	}


	public int getFriendlyPoint() {
		return friendlyPoint;
	}
	public void setFriendlyPoint(int friendlyPoint) {
		this.friendlyPoint = friendlyPoint;
	}

	public int getResurrectionNum() {
		return resurrectionNum;
	}

	public void setResurrectionNum(int resurrectionNum) {
		this.resurrectionNum = resurrectionNum;
	}
	
}
