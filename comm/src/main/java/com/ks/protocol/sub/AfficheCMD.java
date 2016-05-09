package com.ks.protocol.sub;
/**
 * 邮件子命令
 * @author ks
 */
public interface AfficheCMD {
	/**获取邮件*/
	short GAIN_AFFICHE = 1;
	/**删除邮件*/
	short DELETE_AFFICHE = 2;
	/**查看邮件*/
	short VIEW_收取物品 = 3;
	/**查看所有邮件*/
	short AFFICHE_收取所有物品 =4;
	
	short AFFICHE_查看邮件 =5;
}
