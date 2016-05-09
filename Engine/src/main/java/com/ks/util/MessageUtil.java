package com.ks.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.ks.app.ServerEngine;
import com.ks.protocol.FieldDesc;
import com.ks.protocol.Message;

public class MessageUtil {
	
	@SuppressWarnings("unchecked")
	public static void initProxyMessages(String path, String packagename, String asPath) throws Exception{
		List<String> voPacks = PackageUtil.getClassName("com.ks.protocol.vo", true);
		List<Class<? extends Message>> list = new ArrayList<Class<? extends Message>>();
		System.out.println("init communication VO proxy");
		for (String pack : voPacks) {
			if(pack.endsWith("VO")){//通讯类必须以VO结尾
				list.add((Class<? extends Message>)Class.forName(pack));
			}else if(pack.equals("com.ks.protocol.vo.Head")){
				list.add((Class<? extends Message>)Class.forName(pack));
			}
		}
		createProxyMessages(list, path, packagename, asPath);
	}
	public static void createProxyMessages(List<Class<? extends Message>> list, String path, String packagename, String asPath) throws Exception{
		for(Class<? extends Message> clazz : list){
			createProxyMessage(clazz, path, packagename, asPath);
		}
	}
	public static void createProxyMessage(Class<? extends Message> clazz, String path, String packagename, String asPath) throws Exception{
		System.out.println("Initializing:"+clazz.getName());
		
		String classStr = "package com.ks.protocol.proxy;\n" + 
				"public final class "+clazz.getSimpleName() + "Proxy extends "+ clazz.getName()+"{\n" +
				"		private static final long serialVersionUID = 1L;\n";
		String encodeStr = "	public void encode(org.jboss.netty.buffer.ChannelBuffer channelBuff){\n";
		String decodeStr = "	public void decode(org.jboss.netty.buffer.ChannelBuffer channelBuff){\n";
		String asClass = "package com.vo{\n" +
				"	import flash.utils.ByteArray;\n" +
				"	import starling.socket.VOModel;\n" +
				"	public class "+clazz.getSimpleName()+" extends VOModel{\n";
		String asencode="		override public function decode(byteArray:ByteArray):void{\n";
		String asdncode="		override public function encode(byteArray:ByteArray):void{\n";
		Field[] fields = clazz.getDeclaredFields();
		long fl= 0;
		for(Field f : fields){
			if(Modifier.isStatic(f.getModifiers())){
				continue;
			}
			String setM = "set"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1);
			String getM = "get"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1)+"()";
			String isM = "is"+f.getName().substring(0,1).toUpperCase()+f.getName().substring(1)+"()";
			FieldDesc desc = f.getAnnotation(com.ks.protocol.FieldDesc.class);
			if(desc != null){
				asClass += "		/**" + desc.desc() + "*/\n";
			}
			String fname = f.getName();
			if(desc != null && desc.rename().length() > 0){
				fname=desc.rename();
			}
			asClass+="		public var "+ fname;
			if(f.getType().getName().equals("java.util.List")){
				ParameterizedType pt = (ParameterizedType)f.getGenericType();
				Type type=pt.getActualTypeArguments()[0];
				decodeStr+="		"+setM+"(new java.util.ArrayList<"+((Class<?>)type).getName()+">());\n";
				encodeStr+="		if("+getM+"==null){\n";
				encodeStr+="			"+setM+"(new java.util.ArrayList<"+((Class<?>)type).getName()+">());\n" +
						"		}";
				StringBuilder eS = new StringBuilder();
				StringBuilder dS = new StringBuilder();
				fl += 1000;
				processArray(setM, getM, eS, dS, pt,0, fl);
				encodeStr+=eS.toString();
				decodeStr+=dS.toString();
				asClass += ":Array;\n";
				
				if(type instanceof Class){
					Class<?> c = (Class<?>)type;
					String asforString = "			for(var "+fname+"i:int=0;"+fname+"i<"+fname+"Length;"+fname+"i++){\n";
					String asdeforString = "			for(var "+fname+"i:int=0;"+fname+"i<"+fname+".length;"+fname+"i++){\n";
					if(c.getName().equals("java.lang.Integer")){
						
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"["+fname+"i]=byteArray.readInt();\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				byteArray.writeInt("+fname+"["+fname+"i]);\n" +
								"			}\n";
					}else if(c.getName().equals("java.lang.Long")){
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"["+fname+"i]=readLong(byteArray);\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				writeLong(byteArray,"+fname+"["+fname+"i]);\n" +
								"			}\n";
					}else if(c.getName().equals("java.lang.Short")){
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"["+fname+"i]=byteArray.readShort();\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				byteArray.writeShort("+fname+"["+fname+"i]);\n" +
								"			}\n";
					}else if(c.getName().equals("java.lang.Byte")){
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"["+fname+"i]=byteArray.readByte();\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				byteArray.writeByte("+fname+"["+fname+"i]);\n" +
								"			}\n";
					}else if(c.getName().equals("java.lang.Boolean")){
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"["+fname+"i]=byteArray.readBoolean();\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				byteArray.writeBoolean("+fname+"["+fname+"i]);\n" +
								"			}\n";
					}else if(c.getName().equals("java.lang.Float")){
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"[i]=byteArray.readFloat();\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				byteArray.writeFloat("+fname+"["+fname+"i]);\n" +
								"			}\n";
					}else if(c.getName().equals("java.lang.Double")){
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"["+fname+"i]=byteArray.readDouble();\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				byteArray.writeDouble("+fname+"["+fname+"i]);\n" +
								"			}\n";
					}else if(c.getName().equals("java.lang.String")){
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"["+fname+"i]=readString(byteArray);\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				writeObject(byteArray,"+fname+"["+fname+"i]);\n" +
								"			}\n";
					}else{
						asencode+="		" +fname+"=new Array();\n"+
								"			var "+fname+"Length:int = byteArray.readShort();\n" +
								asforString +
								"				" +fname+"["+fname+"i]=readObject(byteArray,new "+((Class<?>)type).getSimpleName()+"());\n"+
								"			}\n";
						asdncode+="			byteArray.writeShort("+fname+".length);\n" +
								asdeforString +
								"				writeObject(byteArray,"+fname+"["+fname+"i]);\n" +
								"			}\n";
					}
					
				}
			}else if(f.getType().getName().equals("int")){
				decodeStr += "		"+setM+"(channelBuff.readInt());\n";
				encodeStr += "		channelBuff.writeInt("+getM+");\n";
				asClass+=":int;\n";
				asencode+="			"+fname+" = byteArray.readInt();\n";
				asdncode+="			byteArray.writeInt("+fname+");\n";
			}else if(f.getType().getName().equals("short")){
				decodeStr += "		"+setM+"(channelBuff.readShort());\n";
				encodeStr += "		channelBuff.writeShort("+getM+");\n";
				asClass+=":int;\n";
				asencode+="			"+fname+" = byteArray.readShort();\n";
				asdncode+="			byteArray.writeShort("+fname+");\n";
			}else if(f.getType().getName().equals("float")){
				decodeStr += "		"+setM+"(channelBuff.readFloat());\n";
				encodeStr += "		channelBuff.writeFloat("+getM+");\n";
				asClass+=":Number;\n";
				asencode+="			"+fname+" = byteArray.readFloat();\n";
				asdncode+="			byteArray.writeFloat("+fname+");\n";
			}else if(f.getType().getName().equals("double")){
				decodeStr += "		"+setM+"(channelBuff.readDouble());\n";
				encodeStr += "		channelBuff.writeDouble("+getM+");\n";
				asClass+=":Number;\n";
				asencode+="			"+fname+" = byteArray.readDouble();\n";
				asdncode+="			byteArray.writeDouble("+fname+");\n";
			}else if(f.getType().getName().equals("long")){
				decodeStr += "		"+setM+"(channelBuff.readLong());\n";
				encodeStr += "		channelBuff.writeLong("+getM+");\n";
				asClass+=":Number;\n";
				asencode+="			"+fname+" = readLong(byteArray);\n";
				asdncode+="			writeLong(byteArray,"+fname+");\n";
			}else if(f.getType().getName().equals("byte")){
				decodeStr += "		"+setM+"(channelBuff.readByte());\n";
				encodeStr += "		channelBuff.writeByte("+getM+");\n";
				asClass+=":int;\n";
				asencode+="			"+fname+" = byteArray.readByte();\n";
				asdncode+="			byteArray.writeByte("+fname+");\n";
			}else if(f.getType().getName().equals("java.lang.String")){
				decodeStr += "		"+setM+"(readString(channelBuff));\n";
				encodeStr += "		writeString(channelBuff,"+getM+");\n";
				asClass+=":String;\n";
				asencode+="			"+fname+"=readString(byteArray);\n";
				asdncode+="			writeString(byteArray,"+fname+");\n";
			}else if(f.getType().getName().equals("boolean")){
				decodeStr += "		"+setM+"(channelBuff.readByte()==1);\n";
				encodeStr += "		channelBuff.writeByte("+isM+"?(byte)1:(byte)0);\n";
				asClass+=":Boolean;\n";
				asencode+="			"+fname+" = byteArray.readBoolean();\n";
				asdncode+="			byteArray.writeBoolean("+fname+");\n";
			}else{
				decodeStr += "		"+setM+"(readObject(channelBuff,"+f.getType().getName()+".class));\n";
				encodeStr += "		writeObject(channelBuff,"+getM+");\n";
				asClass+=":"+f.getType().getSimpleName()+";\n";
				asencode+="			"+fname+"=readObject(byteArray,new "+f.getType().getSimpleName()+"());\n";
				asdncode+="			writeObject(byteArray,"+fname+");\n";
			}
		}
		encodeStr+="	}\n";
		decodeStr+="	}\n";
		asencode +="		}\n";
		asdncode +="		}\n";
		asClass+=asencode;
		asClass+=asdncode;
		asClass+="	}\n}\n";
		
		String copyString = "	public Object copy(){return new "+clazz.getSimpleName() +"Proxy();}\n";
		
		classStr+=encodeStr+decodeStr+copyString+"}";
		if(!ServerEngine.SERVVER_STATUS){
			try {
				File asF = new File(asPath + File.separator + clazz.getSimpleName()+".as");
				FileWriter fw  = new FileWriter(asF);
				fw.write(asClass);
				fw.close();
			} catch (IOException e) {
			}
		}
//		File f =new File(System.getProperty("user.dir")+ File.separator+"bin"+ File.separator+clazz.getSimpleName() +"Proxy.java");
		String npath = packagename.replaceAll("\\.", "/");
//		File f = new File(System.getProperty("user.dir") + File.separator + path + File.separator + npath + File.separator + clazz.getSimpleName() + "Proxy.java");
		File f = new File(path + File.separator + npath + File.separator + clazz.getSimpleName() + "Proxy.java");
		FileWriter fw = new FileWriter(f);
		fw.write(classStr);
		fw.close();
	}
	
	private static void processArray(String setM,String getM,StringBuilder encodeStr,StringBuilder decodeStr,ParameterizedType pt,long f, long fl){
//		long fl = UUID.randomUUID().getLeastSignificantBits();
//		fl=Math.abs(fl);
		Type type=pt.getActualTypeArguments()[0];
		decodeStr.append("		int p"+fl+"=channelBuff.readShort();\n" +
				"		for(int i"+fl+"=0;i"+fl+"<p"+fl+";i"+fl+"++){\n");
		if(f==0){
			encodeStr.append("		channelBuff.writeShort("+getM+".size());\n");
		}else{
			encodeStr.append("		channelBuff.writeShort(p"+f+".size());\n");
		}
		if(type instanceof Class){
			Class<?> clazz=(Class<?>)type;
			if(f==0){
				encodeStr.append("		for("+clazz.getName()+" p"+fl+" : "+getM+"){\n");
			}else{
				encodeStr.append("		for("+clazz.getName()+" p"+fl+" : "+"p"+f+"){\n");
			}
			if(clazz.getName().equals("java.lang.Integer")){
				decodeStr.append("			"+getM+".add(channelBuff.readInt());\n");
				encodeStr.append("			channelBuff.writeInt(p"+fl+");\n");
			}else if(clazz.getName().equals("java.lang.Long")){
				decodeStr.append("			"+getM+".add(channelBuff.readLong());\n");
				encodeStr.append("			channelBuff.writeLong(p"+fl+");\n");
			}else if(clazz.getName().equals("java.lang.Short")){
				decodeStr.append("			"+getM+".add(channelBuff.readShort());\n");
				encodeStr.append("			channelBuff.writeShort(p"+fl+");\n");
			}else if(clazz.getName().equals("java.lang.Byte")){
				decodeStr.append("			"+getM+".add(channelBuff.readByte());\n");
				encodeStr.append("			channelBuff.writeByte(p"+fl+");\n");
			}else if(clazz.getName().equals("java.lang.Boolean")){
				decodeStr.append("			"+getM+".add(channelBuff.readByte()==(byte)1);\n");
				encodeStr.append("			channelBuff.writeByte(p"+fl+"?(byte)1:(byte)0);\n");
			}else if(clazz.getName().equals("java.lang.Float")){
				decodeStr.append("			"+getM+".add(channelBuff.readFloat());\n");
				encodeStr.append("			channelBuff.writeFloat(p"+fl+");\n");
			}else if(clazz.getName().equals("java.lang.Double")){
				decodeStr.append("			"+getM+".add(channelBuff.readDouble());\n");
				encodeStr.append("			channelBuff.writeDouble(p"+fl+");\n");
			}else if(clazz.getName().equals("java.lang.String")){
				decodeStr.append("			"+getM+".add(readString(channelBuff));\n");
				encodeStr.append("			writeString(channelBuff,p"+fl+");\n");
			}else{
				decodeStr.append("			"+getM+".add(readObject(channelBuff,"+clazz.getName()+".class));\n");
				encodeStr.append("			writeObject(channelBuff,p"+fl+");\n");
				
			}
			decodeStr.append("		}\n");
			encodeStr.append("		}\n");
		}else{
			ParameterizedType p = (ParameterizedType)type;
			String st = "";
			if(p.getActualTypeArguments()[0] instanceof Class){
				st = ((Class<?>)(p.getActualTypeArguments()[0])).getName();
			}else{
				st = p.getActualTypeArguments()[0].toString();
			}
			decodeStr.append("		"+getM+".add(new java.util.ArrayList<"+st+">());\n");
			if(f==0){
				encodeStr.append("		for("+p+" p"+fl+" : "+getM+"){\n");
			}else{
				encodeStr.append("		for("+p+" p"+fl+" : "+"p"+f+"){\n");
			}
			getM+=".get(i"+fl+")";
			processArray(setM, getM, encodeStr, decodeStr, p,fl, fl++);
			decodeStr.append("		}\n");
			encodeStr.append("		}\n");
		}
	}
}
