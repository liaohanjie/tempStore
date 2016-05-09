package com.ks.logic.utils;

/**
 * 狼旗手游统计
 * @author hanjie.l
 *
 */
public class CountUtils {

//	/**
//	 * 访问主机
//	 */
//	public static final String HOST = "http://rdata.7you.com/";
//	
//	/**
//	 * 参数头
//	 */
//	public static final String PARAMHEAD = "appid=cydpygdbhyzhcjoj&data=";
//	
//	/**
//	 * 日期格式化
//	 */
//	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	
//	/**
//	 * 2.注册统计
//	 * @param register
//	 * @param user
//	 */
//	public static void countRegister(RegisterVO register, User user){
//		
//		List<Object> list = new ArrayList<>();
//		list.add("register");
//		list.add(DATE_FORMAT.format(new Date()));//创建时间
//		list.add(user.getPartner());//渠道id
//		list.add(register.getOperator());//渠道代码[可选]
//		list.add(register.getModel()); // 手机串号[可选]
//		list.add(register.getMac());// mac地址[可选]
//		list.add(user.getUserId()+"");//玩家id
//		list.add(Application.DATA_SDKSERVERID_NO);//服务器id[可选]
//		list.add("");//服务器名称[可选]
//		list.add(user.getUserId());//角色id[可选]
//		list.add(user.getUsername());//角色名[可选]
//		list.add(register.getIp());//ip[可选]
//		list.add(20);//年龄[可选]
//		list.add(0);//性别[可选]
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	/**
//	 * 3.登录统计
//	 * @param user
//	 */
//	public static void countLogin(User user){
//		
//		final List<Object> list = new ArrayList<>();
//		list.add("login");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId()+"");//玩家表示ID
//		list.add(Application.DATA_SDKSERVERID_NO);//服务器ID[可选]
//		list.add(user.getUserId());//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add(0);//玩家当前关卡[可选]
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	
//	/**
//	 * 4.登出统计
//	 * @param user
//	 */
//	public static void countLogout(User user){
//		List<Object> list = new ArrayList<>();
//		list.add("logout");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId());//玩家表示ID
//		list.add("");//服务器ID[可选]
//		list.add("");//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add("");//玩家当前关卡[可选]
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	/**
//	 * 5.升级统计
//	 * @param user
//	 */
//	public static void countUpgrade(User user){
//		
//		List<Object> list = new ArrayList<>();
//		list.add("upgrade");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId());//玩家表示ID
//		list.add("");//服务器ID[可选]
//		list.add("");//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add("");//玩家当前关卡[可选]
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	/**
//	 * 6.副本统计
//	 * @param user
//	 * @param chapterId 副本id
//	 * @param pass 是否通关 
//	 */
//	public static void countChapter(User user, int chapterId, int pass){
//		
//		List<Object> list = new ArrayList<>();
//		list.add("pass");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId());//玩家表示ID
//		list.add("");//服务器ID[可选]
//		list.add("");//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add("");//玩家当前关卡[可选]
//		list.add(user.getLevel());//玩家通关关卡
//		list.add(pass);//是否过关
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	/**
//	 * 7.支付统计
//	 * @param user
//	 * @param orderNo  订单号
//	 * @param amount   支付金额
//	 * @param gameCoin 游戏币数量
//	 * @param goodsId  购买哪种套餐
//	 */
//	public static void countPay(User user, String orderNo, int amount, int gameCoin, int goodsId){
//		
//		List<Object> list = new ArrayList<>();
//		list.add("pay");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId()+"");//玩家表示ID
//		list.add(Application.DATA_SDKSERVERID_NO);//服务器ID[可选]
//		list.add(user.getUserId());//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add(0);//玩家当前关卡[可选]
//		list.add(orderNo);//订单ID
//		list.add(amount);//支付金额
//		list.add("CNY");//货币代码，例如：人民币 CNY
//		list.add("");//支付方式，例如：”xxx sdk 支付”，”支付宝”[可选]
//		list.add(goodsId);//购买项ID，例如：2[可选]
//		list.add(gameCoin+"");//购买项名称，例如：”300钻石”[可选]
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	/**
//	 * 8.虚拟币产入统计
//	 * @param user
//	 * @param type 	  虚拟币类型，例如：”钻石”,”金币”
//	 * @param reason 获得虚拟币原因，例如：”充值”
//	 * @param gain	  获得虚拟币数量，例如：300
//	 * @param remain 玩家剩余虚拟币数量，例如：3000
//	 */
//	public static void countIncrementGameCoin(User user, String type, String reason, int gain, int remain){
//		
//		List<Object> list = new ArrayList<>();
//		list.add("gain");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId());//玩家表示ID
//		list.add("");//服务器ID[可选]
//		list.add("");//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add("");//玩家当前关卡[可选]
//		list.add(type);//虚拟币类型，例如：”钻石”,”金币”
//		list.add(reason);//获得虚拟币原因，例如：”充值”，”任务奖励”,”关卡奖励”,”连登奖励”
//		list.add(gain);//获得虚拟币数量，例如：300
//		list.add(remain);//玩家剩余虚拟币数量，例如：3000
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	/**
//	 * 9.虚拟币产出统计
//	 * @param user
//	 * @param type 	  虚拟币类型，例如：”钻石”,”金币”
//	 * @param reason 获得虚拟币原因，例如：”充值”
//	 * @param lost	  消耗虚拟币数量，例如：300
//	 * @param remain 玩家剩余虚拟币数量，例如：3000
//	 */
//	public static void countDecrementGameCoin(User user, String type, String reason, int lost, int remain){
//		
//		List<Object> list = new ArrayList<>();
//		list.add("lost");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId());//玩家表示ID
//		list.add("");//服务器ID[可选]
//		list.add("");//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add("");//玩家当前关卡[可选]
//		list.add(type);//虚拟币类型，例如：”钻石”,”金币”
//		list.add(reason);//获得虚拟币原因，例如：”充值”，”任务奖励”,”关卡奖励”,”连登奖励”
//		list.add(lost);//获得虚拟币数量，例如：300
//		list.add(remain);//玩家剩余虚拟币数量，例如：3000
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	
//	/**
//	 * 10、道具获得统计
//	 * @param user
//	 * @param type 	  道具类型，例如：”洗练石”,”金币”
//	 * @param reason 获得道具原因
//	 * @param amount	  获得道具数量，例如：3
//	 * @param remain 玩家剩余道具数量，例如：30
//	 */
//	public static void countGetItems(User user, String type, String reason, int amount, int remain){
//		
//		List<Object> list = new ArrayList<>();
//		list.add("getitem");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId());//玩家表示ID
//		list.add("");//服务器ID[可选]
//		list.add("");//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add("");//玩家当前关卡[可选]
//		list.add(type);//道具类型，例如：”洗练石”,”金币”
//		list.add(reason);//获得原因，例如：”充值”，”任务奖励”,”关卡奖励”,”连登奖励”
//		list.add(amount);//获得道具数量，例如：3
//		list.add(remain);//玩家剩余道具数量，例如：30
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
//	/**
//	 * 11、道具使用统计
//	 * @param user
//	 * @param type 	  道具类型，例如：”洗练石”,”金币”
//	 * @param reason 获得道具原因
//	 * @param amount	  获得道具数量，例如：3
//	 * @param remain 玩家剩余道具数量，例如：30
//	 */
//	public static void countLostItems(User user, String type, String reason, int amount, int remain){
//		
//		List<Object> list = new ArrayList<>();
//		list.add("getitem");
//		list.add(DATE_FORMAT.format(new Date()));//登陆时间
//		list.add(user.getPartner());//渠道ID
//		list.add(user.getUserId());//玩家表示ID
//		list.add("");//服务器ID[可选]
//		list.add("");//角色ID[可选]
//		list.add("");//玩家IP[可选]
//		list.add(user.getLevel());//玩家当前等级[可选]
//		list.add("");//玩家当前关卡[可选]
//		list.add(type);//道具类型，例如：”洗练石”,”金币”
//		list.add(reason);//获得原因，例如：”充值”，”任务奖励”,”关卡奖励”,”连登奖励”
//		list.add(amount);//获得道具数量，例如：3
//		list.add(remain);//玩家剩余道具数量，例如：30
//		
//		final String param = JSONUtil.toJson(new Object[]{list});
//		GameEvent gameEvent = new GameEvent() {
//			@Override
//			public void runEvent() throws Exception {
//				String url = HOST + "?" + PARAMHEAD + URLEncoder.encode(param, "UTF-8");
//				HttpUtil.getRet(url, "", "UTF-8", "UTF-8");
//			}
//		};
//		TimerController.execEvent(gameEvent);
//	}
//	
	
}