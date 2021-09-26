package com.yindangu.plugin.demo.timer;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.timer.ITimeVo;
import com.yindangu.v3.business.timer.ITimerManager;
import com.yindangu.v3.business.timer.ITimerTask;
import com.yindangu.v3.business.timer.TaskScene;

public class TimerTask {
	public static final boolean Log4j = true;
	private static final MyLogger log = new MyLogger(TimerTask.class,Log4j);
	
	public static void main(String[] args) {
		log.info("==============");
		ITimerManager tm = VDS.getIntance().getTimerManager();
		
		String pars = (args.length >0 ? args[0]:"10");
		if(pars.equals("run")) {
			System.out.println("触发任务运行");
			((Runnable)tm).run();
			return ;
		}
		else {
			int time = Integer.parseInt(pars);
			String[] tasks = {"repeatTask","singleTaskHalf","singleTask","distributedTask"};
			for(String t : tasks) {
				tm.unregister(t);
			}
			
			//int time = 4; //凌晨4点
	        tm.registerRepeatTask(new RepeatTask(tasks[0])); //每半小时重复执行，不需要指定时间
	        tm.registerSingleTaskHalf(new RepeatTask(tasks[1]),time); //凌晨4:30执行
	        tm.registerSingleTask(new RepeatTask(tasks[2]),time);//凌晨4:00执行
	        tm.registerSingleTask(new DistributedTask(tasks[3]),time);//凌晨4:00执行（分布式）
			
			long delay = getInitialDelay() / 60;
			System.out.println(delay);
		}
		
	}
	protected static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return 返回秒
	 */
	private static long getInitialDelay() {
		Calendar c = Calendar.getInstance();
		int mi = c.get(Calendar.MINUTE);
		if(mi >= 30) {
			c.add(Calendar.HOUR_OF_DAY, 1);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND,0);
		}
		else {
			c.set(Calendar.MINUTE, 30);
			c.set(Calendar.SECOND,0);
		}
		long time = c.getTimeInMillis()  - System.currentTimeMillis() + 999;
		return time / 1000;
	}
}

class RepeatTask implements ITimerTask{ 
	private static final MyLogger logFoo = new MyLogger(TimerTask.class,TimerTask.Log4j);
	private final String taskName;
	public RepeatTask(String task) {
		taskName = task;
	}
	@Override
	public String getTaskName() { 
		return taskName;
	}

	@Override
	public TaskScene getTaskScene() { 
		return TaskScene.local;
	}

	@Override
	public void run(ITimeVo vo) { 
		String s =getTaskName() + "--start--" +  vo.getDate();
		logFoo.info(s); 
		TimerTask.sleep(6 * 60 * 1000);
		Timestamp d = new Timestamp(System.currentTimeMillis());
		
		s = getTaskName() + "--end--" +  d.toString(); 
		logFoo.info(s);
		
	}	
}


class DistributedTask implements ITimerTask{
	private static final MyLogger logFoo = new MyLogger(TimerTask.class,TimerTask.Log4j); 
	private final String taskName;
	public DistributedTask(String task) {
		taskName = task;
	}
	@Override
	public String getTaskName() { 
		return taskName;
	}

	@Override
	public TaskScene getTaskScene() { 
		return TaskScene.distributed;
	}

	@Override
	public void run(ITimeVo vo) { 
		String s =getTaskName() + "--start--" +  vo.getDate();
		//log.info(s);
		TimerTask.sleep(6 * 60 * 1000);
		Timestamp d = new Timestamp(System.currentTimeMillis());
		
		s = getTaskName() + "*****-end-*****-" +  d.toString();
		//log.info(s,new RuntimeException("============"));
		logFoo.info(s);
		
	}	
}