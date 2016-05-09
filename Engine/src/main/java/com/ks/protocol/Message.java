package com.ks.protocol;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.apache.log4j.Level;
import org.jboss.netty.buffer.ChannelBuffer;

import com.ks.logger.LoggerFactory;

/**
 * 消息类，每一个需要发送的消息都需要继承此类。
 * 每一个消息类必须以VO结尾，voId等于VO类名首字母小写去掉最后的VO如：
 * UserVO(voId=user),AttackVO(voId=attack)。
 * 每个通讯类必须放到com.ks.protocol.vo中。
 * @author ks
 */
public abstract class Message implements Cloneable,Serializable{
	
	public static final long serialVersionUID = 1L;
	/**字符编码*/
	public static final String CHARSET = "UTF-8";
	/**
	 * 编码
	 * @param channelBuff
	 */
	public void encode(ChannelBuffer channelBuff){
		throw new UnsupportedOperationException("方法未定义，请不要使用new关键字创建Message的实例。");
	}
	/**
	 * 解码
	 * @param channelBuff
	 */
	public void decode(ChannelBuffer channelBuff){
		throw new UnsupportedOperationException("方法未定义，请不要使用new关键字创建Message的实例。");
	}
	/**
	 * 从缓冲区中读取一个字符串
	 * @param channelBuff 读取的缓冲区
	 * @return 读出的String
	 */
	public static String readString(ChannelBuffer channelBuff){
		short i = channelBuff.readShort();
		byte[] arr = new byte[i];
		channelBuff.readBytes(arr);
		try {
			return new String(arr,CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持字符集："+CHARSET);
		}
	}
	/**
	 * 向缓冲区中写入一个字符串
	 * @param channelBuff 要写入的缓冲区
	 * @param str 写入的字符串
	 */
	public static void writeString(ChannelBuffer channelBuff,String str){
		try {
			if(str==null){
				str="";
			}
			byte[] dst = str.getBytes(CHARSET);
			channelBuff.writeShort(dst.length);
			channelBuff.writeBytes(dst);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("不支持字符集："+CHARSET);
		}
	}
	/**
	 * 读取对象
	 * @param channelBuffer 读取对象缓冲区
	 * @param clazz 读取对象的class
	 * @return 读出的对象
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Message>T readObject(ChannelBuffer channelBuffer , Class<T> clazz){
		if(channelBuffer.readByte()==1){
			Message message = MessageFactory.getMessage(clazz);
			message.decode(channelBuffer);
			return (T) message;
		}
		return null;
	}
	/**
	 * 向缓冲区内写入对象
	 * @param channelBuffer 写入的缓冲区
	 * @param message 要写入的对象
	 */
	public static void writeObject(ChannelBuffer channelBuffer , Message message){
		if(message==null){
			channelBuffer.writeByte(0);
		}else{
			channelBuffer.writeByte(1);
			message.encode(channelBuffer);
		}
	}
	public Object copy(){
		try {
			return clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("copy object exception.");
		}
	}
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	@Override
	public String toString() {
		if(LoggerFactory.getLevel()==Level.DEBUG){
			Class<?> clazz = this.getClass();
			if(clazz.getSimpleName().endsWith("Proxy")){
				clazz = clazz.getSuperclass();
			}
			Field[] fields = clazz.getDeclaredFields();
			StringBuilder sb = new StringBuilder();
			sb.append(clazz.getSimpleName()+"[\n		");
			for(Field f : fields){
				if(Modifier.isStatic(f.getModifiers())){
					continue;
				}
				try {
					f.setAccessible(true); 
					sb.append(f.getName()+":"+f.get(this)+"\n		");
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			sb.setLength(sb.length()-1);
			sb.append(']');
			return sb.toString();
		}
		return super.toString();
	}
}