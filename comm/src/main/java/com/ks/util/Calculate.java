package com.ks.util;



/**
 * 计算工具
 * 
 * @author ks
 * 
 */
public final class Calculate {
	/**
	 * 获得一个数从1+到这个数的和
	 * 
	 * @param i
	 * @return
	 */
	public static int sigma(int i) {
		return ((1 + i) * i) >> 1;
	}

	/**
	 * 获得两个数之间的一个随机数
	 * 
	 * @param x
	 * @param y
	 * @return [x,y)之间的一个随机数
	 */
	public static int random(int x, int y) {
		if (x > y) {//如果x>y则将两个数交换
			x = x + y;
			y = x - y;
			x = x - y;
		}
		return ((int) (Math.random() * (y - x)) + x);
	}
	/**
	 * 随机[0,x)间的一个随机数
	 * @param x 随机种子
	 * @return [0,x)间的随机数
	 */
	public static int random(int x){
		return (int) (Math.random()*x);
	}
	/**
	 * 获得[x,y]之间的一个随机数
	 * 
	 * @param x
	 * @param y
	 * @return [x,y]之间的一个随机数
	 */
	public static int randomContains(int x,int y){
		if (x > y) {//如果x>y则将两个数交换
			x = x + y;
			y = x - y;
			x = x - y;
		}
		return ((int) (Math.random() * ((y - x)+1)) + x);
	}
	/**
	 * 组合
	 * @param n 
	 * @param k 
	 * @return 
	 */
	public static final int combin(int n,int k){
		if(k>n){
			throw new RuntimeException("k>n");
		}
		return factorial(n)/(factorial(k)*factorial((n-k)));
	}
	public static void main(String[] args) {
		System.out.println(combin(21, 6));
	}
	/**
	 * 阶乘 n!
	 * @param n
	 * @return
	 */
	public static final int factorial(int n){
		int x=1;
		for(int i=1;i<=n;i++){
			x*=i;
		}
		return x;
	}
	/**
	 * 获得主动技能升级概率
	 * @param levelUpRate 概率基数
	 * @param totalCount 总次数
	 * @param currCount 当前次数
	 * @return 概率
	 */
	public static final double getSkillLevelUpRate(double levelUpRate,int totalCount,int currCount){
		if(currCount==totalCount){
			return Math.pow(levelUpRate, totalCount);
		}
		return combin(totalCount, currCount)*Math.pow((1-levelUpRate),totalCount-currCount)*Math.pow(levelUpRate, currCount);
	}
	
	/**
	 * 获得强化金钱
	 * @param level 被强化战魂等级
	 * @param soulRare 被强化战魂稀有度
	 * @param eleCount 被强化战魂属性强化总和
	 * @return 所需金钱
	 */
	public static final int getStrengGold(int level,int soulRare,int eleCount){
		return level*500+soulRare*500+eleCount*500;
	}
}
