package com.living.pay;

import java.util.HashMap;
import java.util.Map;

import com.living.pay.anysdk.AnySdkHandler;
import com.living.pay.anzhi.AnzhiHandler;
import com.living.pay.apple.IapHandler;
import com.living.pay.coolpad.CoolPadHandler;
import com.living.pay.d.DHandler;
import com.living.pay.guopan.GuoPanHandler;
import com.living.pay.haima.HaiMaAndroidHandler;
import com.living.pay.huawei.HuaWeiHandler;
import com.living.pay.i4.I4Handler;
import com.living.pay.itools.IToolsHandler;
import com.living.pay.lj.LJHandler;
import com.living.pay.pp.PPHandler;
import com.living.pay.shuowan.ShuoWanHandler;
import com.living.pay.uc.UCHandler;
import com.living.pay.vivo.VivoHandler;
import com.living.pay.xinlang.XinlangHandler;
import com.living.pay.youku.YoukuHandler;

/**
 * 支付管理类
 * 
 * @author zhoujf
 * @date 2015年6月17日
 */
public class ChannelHandlerManager {

	// private static final Logger logger =
	// LoggerFactory.get(ChannelHandlerManager.class);

	private static Map<Object, ChannelHandler> mapChannel = new HashMap<Object, ChannelHandler>();

	private static Map<Object, String> mapPrefix = new HashMap<Object, String>();

	static {
		// mapPrefix.put(80001, "AS");
		// mapPrefix.put(81001, "PP");
		mapPrefix.put(82001, "IA");
		mapPrefix.put(83001, "I4");
		mapPrefix.put(84001, "XY");
		mapPrefix.put(86001, "KY");
		mapPrefix.put(87001, "D0");
		mapPrefix.put(87002, "D0");
		mapPrefix.put(88001, "HM");
		mapPrefix.put(88002, "HM");
		mapPrefix.put(89001, "PP");
		mapPrefix.put(90001, "IT");
		mapPrefix.put(93001, "GP");
		mapPrefix.put(93002, "GP");
		mapPrefix.put(94001, "A0");

		mapPrefix.put(95002, "HW");
		mapPrefix.put(96002, "OP");
		mapPrefix.put(97002, "VI");
		mapPrefix.put(98002, "JL");
		mapPrefix.put(99002, "LE");
		mapPrefix.put(10002, "KP");
		mapPrefix.put(11002, "AZ");
		mapPrefix.put(12002, "WD");
		mapPrefix.put(13002, "CC");
		mapPrefix.put(14002, "BD");
		mapPrefix.put(15002, "DL");
		mapPrefix.put(16002, "UC");
		mapPrefix.put(17002, "XM");
		mapPrefix.put(18002, "36");
		mapPrefix.put(19002, "MZ");
		mapPrefix.put(20002, "YY");
		mapPrefix.put(21002, "43");
		mapPrefix.put(22002, "JF");
		mapPrefix.put(23002, "YK");
		mapPrefix.put(24002, "XL");
		mapPrefix.put(25002, "YB");
		mapPrefix.put(26002, "TT");
		mapPrefix.put(27002, "SW");

		// mapChannel.put(80001, new AnySdkHandler());
		// mapChannel.put(81001, new PPPayHandler());

		// I苹果
		mapChannel.put(82001, new AnySdkHandler());

		// 爱思
		mapChannel.put(83001, new I4Handler());

		// XY
		mapChannel.put(84001, new AnySdkHandler());

		// 同步推
		// mapChannel.put(85001, new AnySdkHandler());

		// 快用
		mapChannel.put(86001, new AnySdkHandler());

		// 当乐 - IOS
		mapChannel.put(87001, new DHandler());
		// 当乐 - Android
		mapChannel.put(87002, new LJHandler());
		// 当乐 - Android
		mapChannel.put(15002, new LJHandler());

		// 海马-IOS
		mapChannel.put(88001, new AnySdkHandler());
		// 海马-ANDROID
		//mapChannel.put(88002, new HaiMaHandler());
		mapChannel.put(88002, new HaiMaAndroidHandler());

		// PP助手
		mapChannel.put(89001, new PPHandler());

		// itools
		mapChannel.put(90001, new IToolsHandler());

		// 91助手
		// mapChannel.put(91001, new PPPayHandler());

		// 狐狸助手
		// mapChannel.put(92001, new PPPayHandler());

		// 果盘，叉叉助手
		mapChannel.put(93001, new GuoPanHandler());
		// 果盘 - Android
		mapChannel.put(93002, new LJHandler());

		// 苹果内购 IAP
		mapChannel.put(94001, new IapHandler());

		// 第二批开发

		// 华为
		mapChannel.put(95002, new HuaWeiHandler());

		// OPPO
		//mapChannel.put(96002, new OppoHandler());
		mapChannel.put(96002, new LJHandler());

		// Vivo
		mapChannel.put(97002, new VivoHandler());

		// 金立
		// mapChannel.put(98002, new JinLiHandler());
		mapChannel.put(98002, new LJHandler());

		// Lenovo
		// mapChannel.put(99002, new LenovoHandler());
		mapChannel.put(99002, new LJHandler());

		// CoolPad
		mapChannel.put(10002, new CoolPadHandler());
		
		// 安智
		mapChannel.put(11002, new AnzhiHandler());
		//mapChannel.put(11002, new LJHandler());

		// 豌豆荚
		//mapChannel.put(12002, new WanDouJiaHandler());
		mapChannel.put(12002, new LJHandler());
		
		// 百度
		//mapChannel.put(14002, new BaiDuHandler());
		mapChannel.put(14002, new LJHandler());

		// UC
		mapChannel.put(16002, new UCHandler());

		// 小米
		// mapChannel.put(17002, new XiaoMiHandler());
		mapChannel.put(17002, new LJHandler());

		// Qihoo 360 - ANDROID
		// mapChannel.put(18002, new QihooHandler());
		mapChannel.put(18002, new LJHandler());

		// 魅族 - ANDROID
		//mapChannel.put(19002, new MeiZuHandler());
		mapChannel.put(19002, new LJHandler());

		// 4399
		// mapChannel.put(21002, new M4399Handler());
		mapChannel.put(21002, new LJHandler());

		// 优酷
		// mapChannel.put(23002, new LJHandler());
		mapChannel.put(23002, new YoukuHandler());

		// 新浪
		// mapChannel.put(24002, new LJHandler());
		mapChannel.put(24002, new XinlangHandler());
		
		// 应用宝 - ANDROID
		//mapChannel.put(25002, new YingYongBaoHandler());
		mapChannel.put(25002, new LJHandler());
		
		// TT语音 - ANDROID
		//mapChannel.put(26002, new TTHandler());
		mapChannel.put(26002, new LJHandler());
		
		// 说玩 - ANDROID
		//mapChannel.put(27002, new ShuoWanHandler());
		mapChannel.put(27002, new ShuoWanHandler());
	}

	public static ChannelHandler getHandler(Object key) {
		if (key == null || "".equals(key)) {
			return null;
		}
		return mapChannel.get(key);
	}

	public static String getOrderPrefix(Integer payConfigId) {
		return mapPrefix.get(payConfigId);
	}

}
