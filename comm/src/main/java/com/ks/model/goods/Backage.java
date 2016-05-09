package com.ks.model.goods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.ks.exceptions.GameException;
import com.ks.model.user.UserSoul;
/**
 * 背包
 * @author ks
 */
public class Backage implements Serializable {

	private static final long serialVersionUID = 1L;
	/**使用格子分类，key=格子类型，value=[key=物品编号，value=格子列表]*/
	private Map<Integer,Map<Integer,List<UserGoods>>> useGoodsMap;
	/**使用了的格子列表 key=格子编号，value=格子*/
	private Map<Integer,UserGoods> useGoodses;
	/**没有使用的格子*/
	private Queue<UserGoods> notUseGoodses;
	
	public void init(List<UserGoods> goodses){
		useGoodsMap = new HashMap<Integer, Map<Integer,List<UserGoods>>>();
		useGoodses = new HashMap<Integer, UserGoods>();
		notUseGoodses = new LinkedList<UserGoods>();
		for(UserGoods goods : goodses){
			if(goods.getGoodsId()==0){
				notUseGoodses.add(goods);
			}else{
				Map<Integer,List<UserGoods>> map = useGoodsMap.get(goods.getGoodsType());
				if(map == null){
					map = new HashMap<Integer, List<UserGoods>>();
					useGoodsMap.put(goods.getGoodsType(), map);
				}
				List<UserGoods> goodsGrids = map.get(goods.getGoodsId());
				if(goodsGrids == null){
					goodsGrids = new ArrayList<UserGoods>();
					map.put(goods.getGoodsId(), goodsGrids);
				}
				goodsGrids.add(goods);
				useGoodses.put(goods.getGridId(), goods);
			}
		}
	}
	
	public void resetUserGoods(UserGoods ug){
		
		useGoodses.remove(ug.getGridId());
		useGoodsMap.get(ug.getGoodsType()).get(ug.getGoodsId()).remove(ug);
		
		ug.setGoodsType(0);
		ug.setGoodsId(0);
		ug.setUserSoulId(0);
		ug.setDurable(0);
		ug.setNum(0);
		
		notUseGoodses.add(ug);
	}
	
	/**
	 * 获得在使用中的格子
	 * @param gridId 格子编号
	 * @return 使用中的格子
	 */
	public UserGoods getUseGoods(int gridId){
		UserGoods ug = useGoodses.get(gridId);
		if(ug==null){
			throw new GameException(GameException.CODE_参数错误, "");
		}
		return ug;
	}
	
	public List<UserGoods> getItemGoods(int type,int goodsId){
		Map<Integer, List<UserGoods>> map = useGoodsMap.get(type);
		if(map==null){
			return null;
		}
		return map.get(goodsId);
	}
	
	/**
	 * 获得战魂装备
	 * @param soul
	 * @return
	 */
	public List<UserGoods> getSoulEquipments(UserSoul soul){
		List<UserGoods> userGoods = new ArrayList<UserGoods>();
		for(UserGoods ug : useGoodses.values()){
			if(ug.getUserSoulId() == soul.getId()){
				userGoods.add(ug);
			}
		}
		return userGoods;
	}
	public void addUseGoods(UserGoods goods){
		useGoodses.put(goods.getGridId(), goods);
		
		Map<Integer,List<UserGoods>> map = useGoodsMap.get(goods.getGoodsType());
		if(map == null){
			map = new HashMap<Integer, List<UserGoods>>();
			useGoodsMap.put(goods.getGoodsType(), map);
		}
		List<UserGoods> goodsGrids = map.get(goods.getGoodsId());
		if(goodsGrids == null){
			goodsGrids = new ArrayList<UserGoods>();
			map.put(goods.getGoodsId(), goodsGrids);
		}
		goodsGrids.add(goods);
	}

	public Map<Integer, Map<Integer, List<UserGoods>>> getUseGoodsMap() {
		return useGoodsMap;
	}
	public void setUseGoodsMap(
			Map<Integer, Map<Integer, List<UserGoods>>> useGoodsMap) {
		this.useGoodsMap = useGoodsMap;
	}
	public Map<Integer, UserGoods> getUseGoodses() {
		return useGoodses;
	}
	public void setUseGoodses(Map<Integer, UserGoods> useGoodses) {
		this.useGoodses = useGoodses;
	}
	public Queue<UserGoods> getNotUseGoodses() {
		return notUseGoodses;
	}
	public void setNotUseGoodses(Queue<UserGoods> notUseGoodses) {
		this.notUseGoodses = notUseGoodses;
	}
	
}
