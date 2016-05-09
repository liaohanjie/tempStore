package com.ks.logger;

import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.ks.app.ServerEngine;

/**
 * 日志工厂
 * @author ks.wu
 *
 */
public class LoggerFactory {
	static Map<String, Level> logLevelMap = new HashMap<String, Level>();
	static ConsoleAppender consoleAppender;
	static DailyRollingFileAppender fileAppender;
	static PatternLayout colorlayout;
	static PatternLayout normalLayout;
	static Level level;
	static List<Logger> allLogList;
	static String lastError;
	
	public static void setFile(String file){
	    fileAppender.setFile(file);
	    fileAppender.activateOptions();
	}
	public static void setLevel(String level){
		LoggerFactory.level=logLevelMap.get(level);
		for(Logger logger : allLogList){
			logger.setLevel(LoggerFactory.level);
		}
	}
	
	public static Level getLevel(){
		return level;
	}
	
	public static void enableColorOutput() {
		if(ServerEngine.SERVVER_STATUS){
		    consoleAppender.setLayout(colorlayout);
		    fileAppender.setLayout(colorlayout);
		}else{
			consoleAppender.setLayout(normalLayout);
		    fileAppender.setLayout(normalLayout);
		}
	}
	public static Logger get(Class<?> clazz){
	    Logger logger = Logger.getLogger(clazz);
	    logger.addAppender(consoleAppender);
	    logger.addAppender(fileAppender);
	    logger.setLevel(level);
	    allLogList.add(logger);
	    return logger;
	}
	
	static{
	    logLevelMap.put("ALL", Level.ALL);
	    logLevelMap.put("DEBUG", Level.DEBUG);
	    logLevelMap.put("INFO", Level.INFO);
	    logLevelMap.put("WARN", Level.WARN);
	    logLevelMap.put("ERROR", Level.ERROR);
	    logLevelMap.put("FATAL", Level.FATAL);
	    logLevelMap.put("OFF", Level.OFF);
	
	    level = Level.INFO;
	
	    normalLayout = new PatternLayout();
	    normalLayout.setConversionPattern("[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%-5p] [%t] [%c{1}] %m%n");
	
	    colorlayout = new ANSIColorLayout();
	    colorlayout.setConversionPattern("[%d{yyyy-MM-dd HH:mm:ss:SSS}] [%-5p] [%t] [%c{1}] %m%n");
	
	    consoleAppender = new ConsoleAppender();
	    consoleAppender.setName("logger-console");
	    fileAppender = new DailyRollingFileAppender();
	    fileAppender.setName("logger-file");
	    fileAppender.setAppend(true);
	    consoleAppender.setLayout(normalLayout);
	    fileAppender.setLayout(normalLayout);
	
	    consoleAppender.setWriter(new OutputStreamWriter(System.out));
	    consoleAppender.activateOptions();
	
	    allLogList = new ArrayList<Logger>();
	}
}
