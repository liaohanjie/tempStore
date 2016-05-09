package com.ks.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.ks.app.ServerEngine;
/**
 * 关键字
 * @author ks
 */
public class KeyWordsUtil {
	
	private static Set<String> SENSITIVE_WORDS = new HashSet<>();
	static{
		try {
			initFromFile("BadWord.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 添加敏感词
	 * @param words 敏感词
	 */
	public static final void addWords(String words){
		SENSITIVE_WORDS.add(words);
	}
	/**
	 * 从文件中初始化
	 * @param filePath 文件地址
	 * @throws IOException 文件不可用
	 * @throws FileNotFoundException 文件不存在 
	 */
	private static final void initFromFile(String filePath) throws IOException{
		Set<String> hashSet = new HashSet<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(ServerEngine.class.getClassLoader().getResourceAsStream("BadWord.txt"),"UTF-8"))) {
			String str =null;
			while ((str =reader.readLine())!=null) {
				if(!"".equals(str)){
					hashSet.add(str);
				}
			}
		}
		SENSITIVE_WORDS = hashSet;
	}
	
	
	
	public static final boolean hasBadWords(String str){
		for(int x=0;x<str.length();x++){
			String ss = str.substring(x);
			for (int i = 1; i <= ss.length(); i++) {
				String wold = ss.substring(0,i);
				if(SENSITIVE_WORDS.contains(wold)){
					return true;
				}
			}
		}
		return false;
	}
}
