package com.ks.timer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import org.apache.log4j.Logger;

import com.ks.event.GameEvent;
import com.ks.logger.LoggerFactory;
import com.ks.timer.task.TaskTypeEnum;

/**
 * 定时器控制器
 * @author ks
 *
 */
public final class TimerController {
	private static final Logger logger = LoggerFactory.get(TimerController.class);
	private static final ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(4);
	private static final ThreadLocal<List<GameEvent>> events = new ThreadLocal<List<GameEvent>>();
	/**
	 * 注册定时任务
	 * @param timer
	 */
	public static ScheduledFuture<?> register(BaseTimer timer){
		logger.info("timer register : "+ timer.getClass().getSimpleName());
		if(timer.type == TaskTypeEnum.DEFAULT){
			if(timer.period==0){
				return scheduledPool.schedule(timer, timer.initialDelay, timer.unit);
			}else{
				return scheduledPool.scheduleWithFixedDelay(timer, timer.initialDelay, timer.period, timer.unit);
			}
		}else{
			if(timer.type == TaskTypeEnum.SCHEDULE){
				return scheduledPool.schedule(timer, timer.initialDelay, timer.unit);
			}else if(timer.type == TaskTypeEnum.SCHEDULE_WITH_FIXED_DELAY){
				return scheduledPool.scheduleWithFixedDelay(timer, timer.initialDelay, timer.period, timer.unit);
			}else if(timer.type == TaskTypeEnum.SCHEDULE_AT_FIXED_RATE){
				return scheduledPool.scheduleAtFixedRate(timer, timer.initialDelay, timer.period, timer.unit);
			}
		}
		return null;
	}
	/**
	 * 提交游戏事件
	 * @param event
	 * @throws Exception 
	 */
	public static void submitGameEvent(GameEvent event){
		if(event.isSync()){
			try{
				event.runEvent();
			}catch (Exception e) {
				throw new RuntimeException(e);
			}
			return;
		}
		List<GameEvent> list = events.get();
		if(list==null){
			list = new ArrayList<GameEvent>();
		}
		events.set(list);
		list.add(event);
	}
	/**
	 * 清除事件
	 * */
	public static void clearEvents(){
		events.remove();
	}
	/**
	 * 执行游戏事件
	 */
	public static void execEvents(){
		List<GameEvent> list = events.get();
		if(list!=null){
			for(GameEvent e : list){
				scheduledPool.submit(e);
			}
			clearEvents();
		}
	}
	/**
	 * 执行游戏事件
	 * @param event 游戏事件
	 */
	public static void execEvent(GameEvent event){
		scheduledPool.submit(event);
	}
	
	public static void shutdown(){
		scheduledPool.shutdown();
	}
	
	public static boolean isTerminated(){
		return scheduledPool.isTerminated();
	}
}
