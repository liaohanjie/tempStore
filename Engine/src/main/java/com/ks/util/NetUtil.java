package com.ks.util;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetUtil {
	
	/***
	 *  true:already in using  false:not using 
	 * @param host
	 * @param port
	 * @throws UnknownHostException 
	 */
	public static boolean isPortUsing(String host,int port){
		boolean flag = false;
		try {
			InetAddress theAddress = InetAddress.getByName(host);
			Socket socket = new Socket(theAddress,port);
			socket.close();
			flag = true;
		} catch (Exception e) {
			
		}
		return flag;
	}
}
