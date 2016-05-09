package com.ks.logic.test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.ks.util.MD5Util;

public class Code {
	
	public static String[] getShortUrl(String s) {
		String md5=MD5Util.decode(s);
		char strArray[]=new char[]
						{'a','b','c','d','e','f',
						 'g','h','i','j','k','l',
						 'm','n','o','p','q','r',
						 's','t','u','v','w','x',
						 'y','z',						 
						 'A','B','C','D','E','F',
						 'G','H','i','J','K','L',
						 'M','N','O','P','Q','R',
						 'S','t','U','V','W','X',
						 'Y','Z',						 
						 '1','2','3','4','5','6',						 
						 '7','8','9','0'
						};		
		int hexLen = md5.length();   
        int subHexLen = hexLen / 8;   
        String[] retUrl = new String[4];        
        for(int i=0;i<subHexLen;i++){
        	  String outChars = "";   
              int j = i + 1;   
              String subHex = md5.substring(i * 8, j * 8);
              long hexint =Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);              
              for (int m = 0; m < 6; m++) 
              { 
                  //把得到的值与0x0000003D进行位与运算，取得字符数组chars索引 
            	  long index = 0x0000003D & hexint; 
                  //把取得的字符相加 
                  outChars += strArray[(int)index]; 
                  //每次循环按位右移5位 
                  hexint = hexint >> 5; 
              } 
              //把字符串存入对应索引的输出数组 
              retUrl[i] = outChars; 
        } 
        return retUrl;
	}
	
	public static void main(String[] args) {
//		Map<String,String> map=new HashMap<>();
		Map<String,String> uuMap=new HashMap<>();
		for(int i=0;i<1000000;i++){
			String uukey=UUID.randomUUID().toString().replace("_","").substring(0,8);
			//String key=getShortUrl(uukey)[(int)(Math.random()*4)];
			//map.put(key, key);
			uuMap.put(uukey,uukey);
		}
		//System.out.println(map.size());
		System.out.println(uuMap.size());
	}
	
}
