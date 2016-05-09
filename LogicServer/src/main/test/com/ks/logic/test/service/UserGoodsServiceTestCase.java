package com.ks.logic.test.service;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import com.ks.logic.dao.UserBudingDAO;
import com.ks.logic.dao.UserChapterDAO;
import com.ks.logic.dao.UserDAO;
import com.ks.logic.dao.UserSoulDAO;
import com.ks.logic.dao.cfg.BudingDAO;
import com.ks.logic.service.BudingService;
import com.ks.logic.service.ChapterService;
import com.ks.logic.service.GoodsService;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.SoulService;
import com.ks.logic.service.UserGoodsService;
import com.ks.logic.service.UserService;
import com.ks.logic.service.UserSoulService;
import com.ks.logic.service.impl.ChapterServiceImpl;
import com.ks.logic.service.impl.SoulServiceImpl;
import com.ks.logic.service.impl.UserServiceImpl;
import com.ks.logic.service.impl.UserSoulServiceImpl;
import com.ks.logic.test.BaseTestCase;
import com.ks.model.buding.Buding;
import com.ks.model.buding.UserBuding;
import com.ks.model.dungeon.Chapter;
import com.ks.model.equipment.Equipment;
import com.ks.model.goods.Goods;
import com.ks.model.goods.Prop;
import com.ks.model.goods.UserGoods;
import com.ks.model.logger.LoggerType;
import com.ks.model.soul.Soul;
import com.ks.model.stuff.Stuff;
import com.ks.model.user.User;
import com.ks.model.user.UserChapter;
import com.ks.model.user.UserSoul;
import com.ks.protocol.vo.goods.SellGoodsVO;
import com.ks.util.JSONUtil;

public class UserGoodsServiceTestCase  extends BaseTestCase{
	
	private UserGoodsService serive=getService(UserGoodsService.class);
	
	private GoodsService service = ServiceFactory.getService(GoodsService.class);
	protected static final UserSoulService userSoulService = new UserSoulServiceImpl();
	protected static final UserService userService = new UserServiceImpl();
	protected static final BudingService buingdservice = ServiceFactory.getService(BudingService.class);
	private SoulService soulservice= new SoulServiceImpl();
	
	private ChapterService cservice=new ChapterServiceImpl();
	@Test
	public void getPropTest(){
		List<UserGoods> user=new ArrayList<UserGoods>();
		List<UserGoods> ugs = serive.gainUserGoods(483641007);
		for(UserGoods u:ugs){
			if(u.getGoodsType()==4&&u.getUserSoulId()>0){
				user.add(u);
			}
		}
		System.out.println(ugs);
	}
	
	@Test
	public void sell(){
		List<SellGoodsVO> list=new ArrayList<SellGoodsVO>();
		SellGoodsVO vo=new SellGoodsVO();
		vo.setGridId(320);
		vo.setNum(1);
		list.add(vo);
		
		SellGoodsVO vo1=new SellGoodsVO();
		vo1.setGridId(321);
		vo1.setNum(1);
		list.add(vo1);
		//serive.sellGoods(483641790, list);
		String str=JSONUtil.toJson(list);
		System.out.println(JSONUtil.toObject(str, new TypeReference<List<SellGoodsVO>>() {
		}));
	}
	@Test
	public void userEquipment(){
	
		serive.useEquipment(483641007, 463, 51);
		
	}
	@Test
	public void bakProps(){
	}
	@Test
	public void updateUserSoul(){
//		System.out.println(
//		serive.gainUserBakProps(483641007));;483606713,483606719,483606710
		UserSoulDAO	userChapterDAO=new UserSoulDAO();
		List<UserSoul> list=userChapterDAO.queryUserSoul(483606710);
		List<Soul> souls = soulservice.queryAllSoul();
		for (Soul s:souls) {
			for (UserSoul u:list) {
				if(u.getSoulId()==s.getSoulId()){
					u.setLevel(s.getLvMax());
				}
			}
		}
		userChapterDAO.updateUserSouls(list);
		//userChapterDAO.queryUserChapter(483646712,7010001);
	}
	
	
	@Test
	public void addAllProp(){
		int[] userIds= new int[]{483672850};
		
		//int [] propIds={3061001,3061002,3061003,3061004,3061005,3061006,3061007,3061008,3061009,3061010,3061011,3061012,3061013,3061014,3061015,3061016,3061017,3061018,3061019,3061020,3061021,3061022,3061023,3061024,3061025,3061026,3061027,3061028,3061029,3061030,3061031,3061032,3061033,3061034,3061035,3061036,3061037,3061038,3061039,3061040,3061041,3061042,3061043,3061044,3061045,3061046,3061047,3061048,3061049,3061050,3061051,3061052,3061053,3061054,3061055,3061056,3061057,3061058,3061059,3061060};
		int [] propIds={33040005};

		List<Prop> props = service.getAllProp();
		List<Goods> itemGoods = new ArrayList<Goods>();
		List<Equipment> eqlist= service.getEquipments();
		List<Stuff> stuffList=service.getStuffs();
		for (int i = 0; i < props.size(); i++) {
		//	itemGoods.add(Goods.create(props.get(i).getPropId(), 2,99, 1));
		}
		for (Prop prop : props) {				
		//	itemGoods.add(Goods.create(prop.getPropId(), 2,99, 1));
	    }
		for (Equipment e : eqlist) {
			//if(e.getEquipmentId()>3020019){
			//	itemGoods.add(Goods.create(e.getEquipmentId(), 4,1, 1));
			//}
	    }
		for (Stuff s : stuffList) {				
			itemGoods.add(Goods.create(s.getStuffId(), 3,99, 1));
	    }
		for (int i = 0; i < userIds.length; i++) {
			userService.logout(userIds[i]);
			userService.gainUserInfo(userIds[i]);
			User user=userService.getExistUser(userIds[i]);
			serive.addGoods(user,serive.getPackage(user.getUserId()), itemGoods, LoggerType.TYPE_战斗获得, "");
			
		}
		
		
	}
	
	

	
	
	@Test
	public void addUserChpter(){
		UserChapterDAO	userChapterDAO=new UserChapterDAO();
		//int[] userIds= new int[]{483608240};
////				,483606712,483606713,483606719,483606710};
		int[] userIds= new int[]{483679437};
		List<Chapter> clist = cservice.queryAllChapters();
		for (int i = 0; i < userIds.length; i++) {
			userService.logout(userIds[i]);
			userService.gainUserInfo(userIds[i]);
			User user=userService.getExistUser(userIds[i]);
			userChapterDAO.delUserChapter(userIds[i]);
			for (Chapter c:clist) {
				//7010127
					if(c.getChapterId()<=7010368){
						UserChapter	ud = new UserChapter();
						ud.setUserId(user.getUserId());
						ud.setChapterId(c.getChapterId());
						ud.setPassCount(1);
						userChapterDAO.addUserChapter(ud);
					}

			}

		}
		
		
	}
	
	
	
	@Test
	public void addUserSoul(){
		//int[] userIds= new int[]{483608240};
////				,483606712,483606713,483606719,483606710};
		int[] userIds= new int[]{483678671};
		List<Soul> souls = soulservice.queryAllSoul();
		List<Soul> listnew=new ArrayList<Soul>();
		int[] soulsss={1010240,1010241,1010242,1010500,1010175};
		for (Soul soul:souls) {
			boolean falg=true;
			for (int i = 0; i < soulsss.length; i++) {
				if(soul.getSoulId()==soulsss[i]){
				  falg=false;	
				}
			}
			if(falg){
				listnew.add(soul);
			}
		}
		for (int i = 0; i < userIds.length; i++) {
			userService.logout(userIds[i]);
			userService.gainUserInfo(userIds[i]);
			User user=userService.getExistUser(userIds[i]);
			int j=1;
			for (Soul soul:listnew){
				j++;
				//if(	soul.getSoulId()<=1010024){
					userSoulService.addUserSoul(user, soul.getSoulId(), soul.getLvMax(),LoggerType.TYPE_扫荡获得);
				//}
			}
		}
		
		
	}
	@Test
	public void addUserBind(){
		int[] userIds={483678671};
		
		UserBudingDAO dao=new UserBudingDAO();
		BudingDAO bd=new BudingDAO();

		List<Buding> list = bd.queryAllBuding();
		for (int i = 0; i < userIds.length; i++) {
			for (Buding buding :list) {
				UserBuding ub=new UserBuding();
				ub.setBudingId(buding.getBudingId());
				ub.setLevel(buding.getMaxLevel());
				ub.setGold(0);
			    ub.setCollectCount(0);
			    ub.setUserId(userIds[i]);
				dao.addUserBuding(ub);
			}
		}
		
	}
	
	@Test
	public void addAllPropBack(){
		//int[] userIds= new int[]{483608240};
////				,483606712,483606713,483606719,483606710};
		int[] userIds= new int[]{
				483604757,
				483604751,
				483604759,
				483604745,
				483604756,
				483604750,
				483604754,
				483604730,
				483604738,
				483604732,
				483604743,
				483604735,
				483604733,
				483604796,
				483604795,
				483604739,
				483604737,
				483604798,
				483604797,
				483604734,
				483604731,
				483604736,
				483604742,
				483604748,
				483604790,
				483604791,
				483604793,
				483604792,
				483604799,
				483604794};
		List<Prop> props = service.getAllProp();
		List<Goods> itemGoods = new ArrayList<Goods>();
		List<Equipment> eqlist= service.getEquipments();
		List<Stuff> stuffList=service.getStuffs();
		for (Prop prop : props) {				
			itemGoods.add(Goods.create(prop.getPropId(), 2,99, 1));
	    }
		for (Equipment e : eqlist) {				
			itemGoods.add(Goods.create(e.getEquipmentId(), 4,1, 1));
	    }
		for (Stuff s : stuffList) {				
			itemGoods.add(Goods.create(s.getStuffId(), 3,99, 1));
	    }
		for (int i = 0; i < userIds.length; i++) {
			userService.logout(userIds[i]);
			//userService.gainUserInfo(userIds[i]);
			//User user=userService.getExistUser(userIds[i]);
			//serive.addBakProps(userIds[i], 3050003, 10, 1, "");
			
		}
		
		
	}
	
	
	@Test
	public void updateUser(){
		//int[] userIds= new int[]{483608240};
////				,483606712,483606713,483606719,483606710};
		int[] userIds= new int[]{
				483604769,483604764,483604763,483604766,483604188};
		
		UserDAO dao=new UserDAO();
		for (int i = 0; i < userIds.length; i++) {
			userService.logout(userIds[i]);
			User user=dao.findUserByUserId(userIds[i]);
			if(user!=null){
				user.setTotalCurrency(100000);
				user.setLevel(99);
				user.setSoulCapacity(450);
				user.setItemCapacity(430);
				user.setFriendCapacity(450);
				user.setCurrency(1000000);
				user.setGold(1000000);
				
//				Map<String, String> hash = new HashMap<>();
//				hash.put("itemCapacity", String.valueOf(user.getItemCapacity()));
//				hash.put("soulCapacity", String.valueOf(user.getSoulCapacity()));
//				hash.put("totalCurrency", String.valueOf(user.getTotalCurrency()));
//				hash.put("level", String.valueOf(user.getLevel()));
//				hash.put("friendCapacity", String.valueOf(user.getFriendCapacity()));
//				hash.put("currency", String.valueOf(user.getCurrency()));
//				hash.put("gold", String.valueOf(user.getGold()));
//				
//				dao.updateUserCache(user.getUserId(), hash);
				dao.updateUser(user);		
			}
		}
		
		
	}
}

