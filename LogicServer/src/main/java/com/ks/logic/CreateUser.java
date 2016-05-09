package com.ks.logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import com.ks.access.DataSourceUtils;
import com.ks.app.Application;
import com.ks.cache.JedisUtils;
import com.ks.logic.cache.GameCache;
import com.ks.logic.dao.UserBudingDAO;
import com.ks.logic.dao.cfg.BudingDAO;
import com.ks.logic.kernel.LogicServerKernel;
import com.ks.logic.service.BaseService;
import com.ks.logic.service.GoodsService;
import com.ks.logic.service.ServiceFactory;
import com.ks.logic.service.SoulService;
import com.ks.logic.service.UserGoodsService;
import com.ks.logic.service.UserService;
import com.ks.logic.service.impl.SoulServiceImpl;
import com.ks.model.buding.Buding;
import com.ks.model.buding.UserBuding;
import com.ks.model.dungeon.Chapter;
import com.ks.model.equipment.Equipment;
import com.ks.model.goods.Goods;
import com.ks.model.goods.Prop;
import com.ks.model.logger.LoggerType;
import com.ks.model.soul.Soul;
import com.ks.model.stuff.Stuff;
import com.ks.model.user.User;
import com.ks.model.user.UserChapter;
import com.ks.model.user.UserRule;
import com.ks.timer.TimerController;

/**
 * @author fengpeng E-mail:fengpeng_15@163.com
 * @version 创建时间：2014年12月26日 下午2:32:18
 * 类说明
 */
public class CreateUser extends BaseService{
	public static void init() throws Exception{
		DataSourceUtils.setTest(true);
		Application application = new Application();
		application.init("DatabaseApplication.xml",application);
		LogicServerKernel.initDataSource();
		//ServiceFactory.initService();
		LogicServerKernel.initGameCache();
	}
	public static void main(String[] args) throws Exception {
		init();
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("conf"+File.separatorChar+"user.txt"))))){
			String name = br.readLine();
			while(name != null){
				int userId = 0;
				DataSourceUtils.setAutoCommit(false);
				try{
					
					addUser(name, name);
					User user = userDAO.findUserByUsername(name, 10);
					userId = user.getUserId();
					TimerController.execEvents();
					DataSourceUtils.commit();
					JedisUtils.exec();
				}catch (Exception e) {
					DataSourceUtils.rollback();
					JedisUtils.discard();
					TimerController.clearEvents();
					throw e;
				}finally{
					DataSourceUtils.releaseConnection();
					JedisUtils.returnJedis();
			
				}
				
				UserService service = ServiceFactory.getService(UserService.class);
				service.gainUserInfo(userId);
				service.nextSetp(User.GUIDE_STEP4_角色选择, userId);
				service.newbieSoul(User.GUIDE_SOULS[0], userId);
				service.nextSetp(21100, userId);			
				
				UserBudingDAO dao=new UserBudingDAO();
				BudingDAO bd=new BudingDAO();
	
				List<Buding> list = bd.queryAllBuding();
				for (Buding buding :list) {
					UserBuding ub=new UserBuding();
					ub.setBudingId(buding.getBudingId());
					ub.setLevel(buding.getMaxLevel());
					ub.setGold(0);
				    ub.setCollectCount(0);
				    ub.setUserId(userId);
					dao.addUserBuding(ub);
				}
				List<Chapter> clist = chapterService.queryAllChapters();
				userChapterDAO.delUserChapter(userId);
				for (Chapter c:clist) {
						if(c.getChapterId()<=7010107){
							UserChapter	ud = new UserChapter();
							ud.setUserId(userId);
							ud.setChapterId(c.getChapterId());
							ud.setPassCount(1);
							userChapterDAO.addUserChapter(ud);
						}
	
				}
				
				GoodsService goodsService = ServiceFactory.getService(GoodsService.class);
				UserGoodsService userGoodsService=ServiceFactory.getService(UserGoodsService.class);
				
				List<Prop> props = goodsService.getAllProp();
				List<Goods> itemGoods = new ArrayList<Goods>();
				List<Equipment> eqlist= goodsService.getEquipments();
				List<Stuff> stuffList=goodsService.getStuffs();
				for (Prop prop : props) {				
					itemGoods.add(Goods.create(prop.getPropId(), 2,99, 1));
			    }
				for (Equipment e : eqlist) {
					itemGoods.add(Goods.create(e.getEquipmentId(), 4,1, 1));
			    }
				for (Stuff s : stuffList) {				
					itemGoods.add(Goods.create(s.getStuffId(), 3,99, 1));
			    }
				User user=userService.getExistUser(userId);
				userGoodsService.addGoods(user,userGoodsService.getPackage(user.getUserId()), itemGoods, LoggerType.TYPE_高级账号, "");
					
				SoulService soulservice= new SoulServiceImpl();
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
				for (Soul soul:listnew){
					userSoulService.addUserSoul(user, soul.getSoulId(), soul.getLvMax(),LoggerType.TYPE_高级账号);
				}
				
				name = br.readLine();
			}
		}
	}
	
	private static final void addUser(String username,String playerName){
		User user = new User();
		user.setUsername(username);
		user.setPlayerName(playerName);
		user.setPartner(10);
		user.setGold(1000000);
		user.setCurrency(1000000);
		user.setTotalCurrency(10000);
		user.setItemCapacity(430);
		user.setSoulCapacity(450);
		user.setFriendCapacity(450);
		user.setLevel(99);
		user.setGuideStep(User.GUIDE_STEP1_播放GC);
		UserRule rule = GameCache.getUserRule(user.getLevel());
		user.setStamina(rule.getStamina());
		user = userDAO.addUser(user);
		userDAO.updateLevelRank(user.getUserId(), user.getLevel());
	}
}

