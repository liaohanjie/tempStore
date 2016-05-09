package com.ks.app;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.util.internal.ConcurrentHashMap;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.ks.protocol.AbstractHead;
import com.ks.protocol.Message;
import com.ks.protocol.MessageFactory;
import com.ks.rpc.RPCKernel;
import com.ks.rpc.client.ClientRPCHandler;
import com.ks.schedue.ScheduledTask;
import com.ks.schedue.SimpleScheduler;
import com.ks.schedue.annotion.Scheduled;
import com.ks.timer.TimerController;
import com.ks.timer.job.BaseJob;
import com.ks.timer.job.Job;
import com.ks.timer.task.BaseTask;
import com.ks.timer.task.Task;
import com.ks.util.PackageUtil;
import com.ks.util.StringUtil;
import com.ks.util.ZLibUtils;
/**
 * 
 * @author ks
 *
 */
public abstract class ServerEngine {
	
	private static final Logger logger = LoggerFactory.get(ServerEngine.class);
	
	/**服务器编号*/
	public static String serverId;
	
	/**版本标识*/
	public static byte MESSAGE_VER;
	/**填充字段0*/
	public static byte UNUSE0;
	/**填充字段1*/
	public static byte UNUSE1;
	
	public static int SERVER_TYPE;
	
	/**服务器状态*/
	public static boolean SERVVER_STATUS;
	
	/**消息头长度*/
	public static int HEAD_LENGTH=45;
	
	/**压缩字节最小长度*/
	public static final int COMPRESSED_BUFF_LEANTH = 512;
	
	public static boolean RUN_TASK=true;
	/**远程调用客户端映射 key=ServerType,value[key=name,value=class]*/
	public static final Map<Integer,Map<String,Class<?>>> RPC_CLIENT_MAPPER = new ConcurrentHashMap<Integer, Map<String,Class<?>>>();
	/**服务映射*/
	private static final Map<Class<?>,Object> SERVICE_MAPPER = new HashMap<Class<?>, Object>();
	
	private static ServerEngine engine;
	
	private static  ClassLoader CLASS_LOADER = ClassLoader.getSystemClassLoader();
	/**发送消息之前*/
	private static BeforeSendMessage beforeSendMessage;
	/**
	 * 获得类加载器
	 * @return 类加载器
	 */
	public static ClassLoader getClassLoader() {
		return CLASS_LOADER;
	}
	
	public static void setClassLoader(ClassLoader loader){
		CLASS_LOADER=loader;
	}
	
	public static void setBeforeSendMessage(BeforeSendMessage beforeSendMessage) {
		ServerEngine.beforeSendMessage = beforeSendMessage;
	}

	/**
	 * 初始化系统
	 * @throws Exception
	 */
	public abstract void initApplication() throws Exception;
	/**
	 * 是否加载VO
	 * @return 是否加载VO
	 */
	public abstract boolean isInitVO();
	
	public boolean isCreateProxyVO(){
		return true;
	}
	
	public final void init(InputStream is,ServerEngine engine)throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(is);
		init(doc, engine);
	}
	public final void init(String XMLName,ServerEngine engine)throws Exception{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(ServerEngine.class.getClassLoader().getResourceAsStream(XMLName));
		init(doc, engine);
	}
	private final void init(Document doc,ServerEngine engine)throws Exception{
		Element element = doc.getDocumentElement();
		NamedNodeMap nodeMap = element.getAttributes();
		MESSAGE_VER = Byte.parseByte(nodeMap.getNamedItem("ver").getNodeValue());
		UNUSE0 = Byte.parseByte(nodeMap.getNamedItem("unuse0").getNodeValue());
		UNUSE1 = Byte.parseByte(nodeMap.getNamedItem("unuse1").getNodeValue());
		SERVER_TYPE = Integer.parseInt(nodeMap.getNamedItem("type").getNodeValue());
		
		ServerEngine.engine = engine;
		initApplication();
		
		LoggerFactory.enableColorOutput();
		
		
		List<String> voPacks = PackageUtil.getClassName("com.ks.protocol.vo", true);
		logger.info("init communication VO proxy");
		List<VO> vos = new ArrayList<VO>();
		for (String pack : voPacks) {
			if(pack.endsWith("VO")){//通讯类必须以VO结尾
				VO vo = new VO();
				String voId = pack.substring(pack.lastIndexOf('.')+1);
				voId = StringUtil.toLowerCaseInitial(voId, true);//将首字母小写
				voId = voId.substring(0, voId.length()-2);//去掉最后两个字母
				vo.setId(voId);
				vo.setClazz(pack);
				vos.add(vo);
			}else if(pack.equals("com.ks.protocol.vo.Head")){
				VO vo = new VO();
				String voId = pack.substring(pack.lastIndexOf('.')+1);
				voId = StringUtil.toLowerCaseInitial(voId, true);//将首字母小写
				vo.setId(voId);
				vo.setClazz(pack);
				vos.add(vo);
			}
		}
		if(vos.size()>0&&isInitVO()){
			initMessageFactory(vos);
		}
		//初始化handler
		NodeList handlerList = element.getElementsByTagName("handlers");
		List<VO> handlers = new ArrayList<VO>();
		String path = null;
		for (int i = 0; i < handlerList.getLength(); i++) {
			NamedNodeMap e = handlerList.item(i).getAttributes();
			path= e.getNamedItem("path").getNodeValue();
		}
		Action action = null;
		if(path!=null){
			List<String> classNames = PackageUtil.getClassName(path, false);//搜索path下所有的class
			action = new Action();
			List<MainCmd> mainCmds = new ArrayList<MainCmd>();
			for(String className : classNames){
				Class<?> clazz = getClassLoader().loadClass(className);
				com.ks.protocol.MainCmd amcmd = clazz.getAnnotation(com.ks.protocol.MainCmd.class);
				if(amcmd!=null){
					MainCmd mcmd = new MainCmd();
					mcmd.setValue(amcmd.mainCmd()+"");
					mcmd.setHandler(clazz.getSimpleName());
					
					VO vo = new VO();
					vo.setId(clazz.getSimpleName());
					vo.setClazz(clazz.getName());
					handlers.add(vo);
					
					Method[] ms = clazz.getDeclaredMethods();
					List<SubCmd> subCmds = new ArrayList<SubCmd>();
					for(Method m : ms){
						com.ks.protocol.SubCmd asCmd = m.getAnnotation(com.ks.protocol.SubCmd.class);
						if(asCmd!=null){
							SubCmd scmd = new SubCmd();
							scmd.setValue(asCmd.subCmd()+"");
							scmd.setHandler(null);
							scmd.setMethod(m.getName());
							String[] args = asCmd.args();
							List<Param> ps = new ArrayList<Param>();
							for(String arg : args){
								String[] as = arg.split("_");//判断时候为数组
								Param p = new Param();
								p.setId(as[0]);
								boolean flag = false;
								if(as.length>1){
									flag = Boolean.parseBoolean(as[1]);
								}
								p.setArray(flag);
								ps.add(p);
							}
							scmd.setParam(ps);
							subCmds.add(scmd);
						}
						mcmd.setSubCmds(subCmds);
					}
					mainCmds.add(mcmd);
				}
			}
			action.setMainCmds(mainCmds);
		}
		
		if(action!=null){
			createMessageProcess(action,handlers,vos);
		}
		
		NodeList rcpList = element.getElementsByTagName("actions");
		for(int i=0;i<rcpList.getLength();i++){
			Element e = (Element) rcpList.item(i);
			NodeList l = e.getElementsByTagName("action");
			for(int j=0;j<l.getLength();j++){
				Element el = (Element) l.item(j);
				NamedNodeMap nm = el.getAttributes();
				int type = Integer.parseInt(nm.getNamedItem("type").getNodeValue());
				String rpcPath = nm.getNamedItem("path").getNodeValue();
				List<String> classNames = PackageUtil.getClassName(rpcPath, false);
				for(String className : classNames){
					if(type==SERVER_TYPE){
						try{
							Class<?> clazz = getClassLoader().loadClass(className);
							Object o= clazz.newInstance();
							String name = clazz.getSimpleName();
							name = name.substring(0, name.length()-4);
							RPCKernel.addInterface(name, o);
						}catch (Exception e1) {
							logger.warn("can not find : "+e1.getMessage());
							continue;
						}
					}else{
						Class<?> clazz = getClassLoader().loadClass(className);
						RPCKernel.addServerTypeRemote(type, clazz, Proxy.newProxyInstance(clazz.getClassLoader(),
								new Class<?>[]{clazz}, new ClientRPCHandler(clazz.getSimpleName(),
										RPCKernel.FLAG_TYPE, type,"")));
						Map<String,Class<?>> map = RPC_CLIENT_MAPPER.get(type);
						if(map==null){
							map = new ConcurrentHashMap<String, Class<?>>();
							RPC_CLIENT_MAPPER.put(type, map);
						}
						map.put(clazz.getSimpleName(), clazz);
					}
				}
			}
		}
		if(RUN_TASK){
			initSchedule(element);
		}
	}

	/**
	 * @param element
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void initSchedule(Element element) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		NodeList taskList = element.getElementsByTagName("tasks");
		for(int i=0;i<taskList.getLength();i++){
			Element e = (Element) taskList.item(i);
			NodeList l = e.getElementsByTagName("task");
			for(int j=0;j<l.getLength();j++){
				Element el = (Element) l.item(j);
				NamedNodeMap nm = el.getAttributes();
				String taskPath = nm.getNamedItem("path").getNodeValue();
				List<String> classNames = PackageUtil.getClassName(taskPath, false);
				for(String className : classNames){
					Class<?> clazz = getClassLoader().loadClass(className);
					Task at = clazz.getAnnotation(Task.class);
					if(at!=null){
						BaseTask task = (BaseTask) clazz.newInstance();
						long initialDelay = at.initialDelay();
						long period = at.period();
						task.setType(at.type());
						task.setInitialDelay(initialDelay);
						task.setPeriod(period);
						task.setUnit(at.unit());
						TimerController.register(task);
					}
				}
			}
		}
		
		NodeList jobList = element.getElementsByTagName("jobs");
		for(int i=0;i<jobList.getLength();i++){
			Element e = (Element) jobList.item(i);
			NodeList l = e.getElementsByTagName("job");
			for(int j=0;j<l.getLength();j++){
				Element el = (Element) l.item(j);
				NamedNodeMap nm = el.getAttributes();
				String jobPath = nm.getNamedItem("path").getNodeValue();
				List<String> classNames = PackageUtil.getClassName(jobPath);
				for(String className : classNames){
					Class<?> clazz = getClassLoader().loadClass(className);
					Job aj = clazz.getAnnotation(Job.class);
					if(aj!=null){
						BaseJob job = (BaseJob) clazz.newInstance();
						String context = aj.context();
						job.initialize(context);
					}
				}
			}
		}
		
		NodeList schedulList = element.getElementsByTagName("schedulings");
		for(int i=0;i<schedulList.getLength();i++){
			Element e = (Element) schedulList.item(i);
			NodeList l = e.getElementsByTagName("scheduling");
			for(int j=0;j<l.getLength();j++){
				Element el = (Element) l.item(j);
				NamedNodeMap nm = el.getAttributes();
				String schedulPath = nm.getNamedItem("path").getNodeValue();
				List<String> scheduleName = PackageUtil.getClassName(schedulPath);
				for(String className : scheduleName){
					Class<?> clazz = getClassLoader().loadClass(className);
					Scheduled scheduled = clazz.getAnnotation(Scheduled.class);
					if(scheduled != null){
						ScheduledTask task = (ScheduledTask) clazz.newInstance();
						String taskName = scheduled.name();
						String cron = scheduled.value();
						if(scheduled.debug()){
							SimpleScheduler.debugSchedule(task, taskName, cron);
						}else{
							SimpleScheduler.schedule(task, taskName, cron);
						}
					}
				}
			}
		}
		
		
	}
	private static void createMessageProcess(Action action,List<VO> handlers,List<VO> vos){
		int i=0;
		int j=0;
		String classString = "public final class MessageProcessProxy$ extends com.ks.protocol.MessageProcess{\n";
		String staticString = "	static{\n";
		for(VO vo : handlers){
			classString+="	public static final "+vo.getClazz()+" "+vo.getId() +" = new "+vo.getClazz()+"();\n";
			staticString+="		com.ks.app.Application.putService("+vo.getId()+");\n";
		}
		staticString+="	}\n";
		classString+=staticString;
		String mainSwitchString = "		";
		String processString = "	public void process(com.ks.handler.GameHandler gameHandler,com.ks.protocol.AbstractHead head,org.jboss.netty.buffer.ChannelBuffer channelBuffer) throws Exception{\n" +
				"		switch(head.getMainCmd()){\n";
				
		for(MainCmd mainCmd : action.getMainCmds()){
			mainSwitchString+="		case "+mainCmd.getValue()+":\n" +
							  "		____proc"+mainCmd.getValue()+"(gameHandler,head,channelBuffer);\n" +
							  "			break;\n";
			String ___prString = "		public void ____proc"+mainCmd.getValue()+"(com.ks.handler.GameHandler gameHandler,com.ks.protocol.AbstractHead head,org.jboss.netty.buffer.ChannelBuffer channelBuffer) throws Exception{\n" +
					"		switch(head.getSubCmd()){\n";
			String subSwitchString = "		";
			for(SubCmd subCmd : mainCmd.getSubCmds()){
				subSwitchString+="	case "+subCmd.getValue()+":{\n";
				for(Param p : subCmd.getParam()){
					String sm = "		";
					String clazz = "";
					for(VO vo : vos){
						if(vo.getId().equals(p.getId())){
							clazz = vo.getClazz();
							break;
						}
					}
					
					if(p.isArray()){
						if("".equals(clazz)){
							if(p.getId().equals("int")){
								clazz="Integer";
							}else if(p.getId().equals("float")){
								clazz="Float";
							}else if(p.getId().equals("short")){
								clazz="Short";
							}else if(p.getId().equals("double")){
								clazz="Double";
							}else if(p.getId().equals("long")){
								clazz="Long";
							}else if(p.getId().equals("String")){
								clazz="String";
							}else if(p.getId().equals("boolean")){
								clazz="Boolean";
							}else if(p.getId().equals("byte")){
								clazz="Byte";
							}
						}
						sm+="		java.util.List<"+clazz+"> "+p.getId()+i+"=null;\n";
					}else{
						if("".equals(clazz)){
							clazz=p.getId();
							if("String".equals(clazz)){
								sm+="		"+clazz+" "+p.getId()+i+"=null;\n";
							}else if("boolean".equals(clazz)){
								sm+="		"+clazz+" "+p.getId()+i+"=false;\n";
							}else{
								sm+="		"+clazz+" "+p.getId()+i+"=("+clazz+")0;\n";
							}
						}else{
							sm+="		"+clazz+" "+p.getId()+i+"=null;\n";
						}
					}
					subSwitchString+=sm;
					i++;
				}
				String tryString="	";
				String cc = (subCmd.getHandler()==null?mainCmd.getHandler():subCmd.getHandler())+"."+subCmd.getMethod()+"(gameHandler";
				for(Param p : subCmd.getParam()){
					String clazz = "";
					for(VO vo : vos){
						if(vo.getId().equals(p.getId())){
							clazz = vo.getClazz();
							break;
						}
					}
					if(!p.isArray()){
						if(p.getId().equals("int")){
							tryString+="		"+p.getId()+j+"= channelBuffer.readInt();\n";
						}else if(p.getId().equals("float")){
							tryString+="		"+p.getId()+j+"= channelBuffer.readFloat();\n";
						}else if(p.getId().equals("short")){
							tryString+="		"+p.getId()+j+"= channelBuffer.readShort();\n";
						}else if(p.getId().equals("double")){
							tryString+="		"+p.getId()+j+"= channelBuffer.readDouble();\n";
						}else if(p.getId().equals("long")){
							tryString+="		"+p.getId()+j+"= (channelBuffer.readLong());\n";
						}else if(p.getId().equals("String")){
							tryString+="		"+p.getId()+j+"= com.ks.protocol.Message.readString(channelBuffer);\n";
						}else if(p.getId().equals("boolean")){
							tryString+="		"+p.getId()+j+"= channelBuffer.readByte()==1;\n";
						}else if(p.getId().equals("byte")){
							tryString+="		"+p.getId()+j+"= channelBuffer.readByte();\n";
						}else{
							tryString+="		"+p.getId()+j+"=com.ks.protocol.Message.readObject(channelBuffer,"+clazz+".class);\n";
						}
					}else{
						tryString+="		short x"+j+"=channelBuffer.readShort();\n" +
								"		"+p.getId()+j+"=new java.util.ArrayList<"+clazz+">();\n" +
								"		for(short x=0;x<x"+j+";x++){\n" ;
						if(p.getId().equals("int")){
							tryString+="		int c= channelBuffer.readInt();\n";
						}else if(p.getId().equals("float")){
							tryString+="		float c = channelBuffer.readFloat();\n";
						}else if(p.getId().equals("short")){
							tryString+="		short c = channelBuffer.readShort();\n";
						}else if(p.getId().equals("double")){
							tryString+="		double c = channelBuffer.readDouble();\n";
						}else if(p.getId().equals("long")){
							tryString+="		long c= (channelBuffer.readLong());\n";
						}else if(p.getId().equals("String")){
							tryString+="		String c = com.ks.protocol.Message.readString(channelBuffer);\n";
						}else if(p.getId().equals("boolean")){
							tryString+="		boolean c = channelBuffer.readByte()==1;\n";
						}else if(p.getId().equals("byte")){
							tryString+="		byte c = channelBuffer.readByte();\n";
						}else{
							tryString+="		if(channelBuffer.readByte()!=1){continue;}\n" +
									"		"+clazz+" c=com.ks.protocol.MessageFactory.getMessage("+clazz+".class);\n" +
												"		c.decode(channelBuffer);\n";
						}
						tryString+="		" +p.getId()+j+".add(c);\n"+
												"		}\n";
					}
					cc+=","+p.getId()+j;
					j++;
				}
				cc+=");\n";
				tryString+="		" +cc;
				subSwitchString+=tryString;
				subSwitchString+="		break;\n" +
						"	}\n";
			}
			___prString+=subSwitchString;
			___prString+="	default:\n"+
					"			throw new com.ks.exceptions.GameException(0,\"bad cmd : \"+head.getMainCmd()+\"-\"+head.getSubCmd());\n";
			___prString+="		}\n";
			___prString+="	}\n";
			classString+=___prString;
		}
		
		processString+=mainSwitchString +
				"			default:\n" +
				"			throw new com.ks.exceptions.GameException(0,\"bad cmd : \"+head.getMainCmd()+\"-\"+head.getSubCmd());\n"+
				"		}\n" +
				"	}\n";
		classString+=processString;
		classString+="}";
		try{
			File f =new File(System.getProperty("user.dir")+ File.separator+"bin"+ File.separator+"MessageProcessProxy$.java");
	
			FileWriter fw = new FileWriter(f);
			fw.write(classString);
			fw.close();
			Object o  = MessageFactory.javac(f, "MessageProcessProxy$").newInstance();
			engine.addMessagePropcess(o);
		}catch (Exception e) {
			logger.error("",e);
		}
	}
	public abstract void addMessagePropcess(Object o);
	@SuppressWarnings("unchecked")
	private void initMessageFactory(List<VO> vos){
		if(isCreateProxyVO()){
			List<Class<? extends Message>> list = new ArrayList<Class<? extends Message>>();
			for(VO vo : vos){
				try {
					list.add((Class<? extends Message>) getClassLoader().loadClass(vo.getClazz()));
				} catch (ClassNotFoundException e) {
					logger.error("",e);
				}
			}
			MessageFactory.initFactory(list);
		}else{
			List<Class<? extends Message>> list = new ArrayList<Class<? extends Message>>();
			for(VO vo : vos){
				String clazz = vo.getClazz();
				String cname = "com.ks.protocol.proxy." + clazz.subSequence(clazz.lastIndexOf(".") + 1, clazz.length()) + "Proxy";
				try {
					list.add((Class<? extends Message>) getClassLoader().loadClass(cname));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			MessageFactory.initProxyMessages(list);
		}
	}
	/**
	 * 获取服务对象
	 * @param clazz 服务对象class
	 * @return 服务对象
	 */
	@SuppressWarnings("unchecked")
	public static <T>T getService(Class<T> clazz){
		return (T) SERVICE_MAPPER.get(clazz);
	}
	
	public static void putService(Object o){
		SERVICE_MAPPER.put(o.getClass(), o);
	}
	
	
	public static ChannelBuffer getChannelBuffer(AbstractHead head,Object ... os){
		if(LoggerFactory.getLevel()==Level.DEBUG){
			StringBuilder sb = new StringBuilder();
			sb.append("data trace:");
			sb.append("\n-------------------------------------------\n");
			int i=0;
			sb.append("head:"+head);
			for(Object o : os){
				sb.append("args[").append(i).append("] : ").append(o).append('\n');
				i++;
			}
			sb.append("-------------------------------------------\n");
			logger.debug(sb);
		}
		ChannelBuffer buff = ChannelBuffers.dynamicBuffer(ByteOrder.LITTLE_ENDIAN,0);
		head.setCompressed(false);
		head.encode(buff);
		for(Object o : os){
			if(o instanceof Integer){
				buff.writeInt((Integer)o);
			}else if(o instanceof Long){
				buff.writeLong((Long)o);
			}else if(o instanceof Byte){
				buff.writeByte((Byte)o);
			}else if(o instanceof Float){
				buff.writeFloat((Float)o);
			}else if(o instanceof Double){
				buff.writeDouble((Double)o);
			}else if(o instanceof Boolean){
				buff.writeByte(((Boolean)o)?1:0);
			}else if(o instanceof String){
				Message.writeString(buff, (String)o);
			}else if(o instanceof List){
				buff.writeShort(((List<?>)o).size());
				for(Object x : (List<?>)o){
					if(x instanceof Integer){
						buff.writeInt((Integer)x);
					}else if(x instanceof Long){
						buff.writeLong((Long)x);
					}else if(x instanceof Byte){
						buff.writeByte((Byte)x);
					}else if(x instanceof Float){
						buff.writeFloat((Float)x);
					}else if(x instanceof Double){
						buff.writeDouble((Double)x);
					}else if(x instanceof Boolean){
						buff.writeByte(((Boolean)x)?1:0);
					}else if(x instanceof String){
						Message.writeString(buff, (String)x);
					}else{
						Message.writeObject(buff, (Message) x);
					}
				}
			}else{
				Message.writeObject(buff, (Message) o);
			}
		}
		int wb = buff.writerIndex();
		
		if(wb>COMPRESSED_BUFF_LEANTH){//启用字节压缩
			if(LoggerFactory.getLevel()==Level.DEBUG){
				logger.debug("data length -------->" + wb);
			}
			byte[] array = ZLibUtils.compress(buff.array(),HEAD_LENGTH,wb-HEAD_LENGTH);
			wb = HEAD_LENGTH+array.length;
			buff.writerIndex(0);
			head.setCompressed(true);
			
			head.encode(buff);
			buff.writeBytes(array);
		}
		
		buff.writerIndex(0);
		buff.writeShort(wb-HEAD_LENGTH);
		buff.writerIndex(wb);
		
		if(LoggerFactory.getLevel()==Level.DEBUG){
			logger.debug("send buff length -------->" + wb);
		}
		return buff;
	}
	
	/**
	 * 发送消息
	 * @param channel 客户端channel
	 * @param head 消息头
	 * @param os 消息体
	 */
	public static void sendMessage(Channel channel,AbstractHead head,Object ... os){
		if(channel.isConnected()){
			if(beforeSendMessage != null){
				beforeSendMessage.beforeSendMessage(channel, head, os);
			}
			ChannelBuffer cb = getChannelBuffer(head, os);
			channel.write(cb);
		}
	}
	/**
	 * 发送消息
	 * @param channel
	 * @param channelBuffer
	 */
	public static void sendMessage(Channel channel,ChannelBuffer channelBuffer){
		channel.write(channelBuffer);
	}
	
	/**
	 * 发送消息
	 * @param channel
	 * @param buffers
	 */
	public static void sendMessage(Channel channel,byte[] buffers){
		channel.write(ChannelBuffers.wrappedBuffer(buffers));
	}
	/**
	 * 发送组播消息
	 * @param group 要广播的组
	 * @param head 消息头
	 * @param os 消息体
	 */
	public static void sendGroupMessage(ChannelGroup group,AbstractHead head,Object ... os){
		group.write(getChannelBuffer(head, os));
	}
	/**
	 * 发送组播消息
	 * @param group 要广播的组
	 * @param buffers 消息
	 */
	public static void sendGroupMessage(ChannelGroup group,byte[] buffers){
		group.write(ChannelBuffers.wrappedBuffer(buffers));
	}
	/**
	 * 增加游戏事件
	 * @param gameEvent
	 */
	public static void addGameEvent(GameEvent gameEvent){
		TimerController.submitGameEvent(gameEvent);
	}
	
}

class VO{
	private String id;
	private String clazz;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}

class Action{
	private List<MainCmd> mainCmds;

	public List<MainCmd> getMainCmds() {
		return mainCmds;
	}

	public void setMainCmds(List<MainCmd> mainCmds) {
		this.mainCmds = mainCmds;
	}
	
}
class MainCmd{
	private String value;
	private String handler;
	private List<SubCmd> subCmds;
	
	public List<SubCmd> getSubCmds() {
		return subCmds;
	}
	public void setSubCmds(List<SubCmd> subCmds) {
		this.subCmds = subCmds;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
}
class SubCmd{
	private String value;
	private String handler;
	private String method;
	private List<Param> param;
	
	public List<Param> getParam() {
		return param;
	}
	public void setParam(List<Param> param) {
		this.param = param;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	
}
class Param{
	private String id;
	private boolean array;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isArray() {
		return array;
	}
	public void setArray(boolean array) {
		this.array = array;
	}
	
	
}