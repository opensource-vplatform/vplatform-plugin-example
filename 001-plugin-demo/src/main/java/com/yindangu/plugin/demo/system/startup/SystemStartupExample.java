package com.yindangu.plugin.demo.system.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.plugin.business.api.system.ISystemStartupListener;
import com.yindangu.v3.business.timer.ITimeVo;
import com.yindangu.v3.business.timer.ITimerManager;
import com.yindangu.v3.business.timer.ITimerTask;
import com.yindangu.v3.business.timer.TaskScene;

public class SystemStartupExample implements ISystemStartupListener{
	public static final String PluginCode = "systemStartupExample";
	private static final Logger log = LoggerFactory.getLogger(SystemStartupExample.class);

	@Override
	public void beforePublish() {
		try {
			log.info("***************SystemStartupExample*************");
			log.info("执行系统开始发布构件时触发（系统所有服务都初始化完成后）");
			//doRegister("11");
		}
		catch(RuntimeException e) {
			log.info("****应该报错的看下平台的健壮性a********",e);
			throw e;
		}
	}

	@Override
	public void afterStartup() { 
		try {
			log.info("*********SystemStartupExample*******");
			log.info("执行系统全部启动完成时触发（系统完全可以访问）");
			doRegister("10");
		}
		catch(RuntimeException e) {
			log.info("****应该报错的看下平台的健壮性b********",e);
			throw e;
		}
	}
	/**
	 * 注册定时任务
	 * @return
	 */
	private int doRegister(String times) {
		ITimerManager tm = VDS.getIntance().getTimerManager();//取得任务管理器
		String[] tasks = {"repeatTask","singleTaskHalf","singleTask","distributedTask"};
		for(String t : tasks) {
			tm.unregister(t);
		}
		int time =1;//凌晨1点
		if(times !=null && times.length()>0) {
			time = Integer.parseInt(times);
		}
		tm.registerRepeatTask(new LocalTask(tasks[0])); //多次执行的任务
		tm.registerSingleTaskHalf(new LocalTask(tasks[1]),time); //凌晨1:30执行
		tm.registerSingleTask(new LocalTask(tasks[2]),time);//凌晨1:00执行
		tm.registerSingleTask(new DistributedTask(tasks[3]),time);//凌晨1:00执行分布式任务
		
		return time;
	}
}

class LocalTask implements ITimerTask{ 
	private static final Logger log = LoggerFactory.getLogger(LocalTask.class);
	 
	private final String taskName;
	public LocalTask(String task) {
		taskName = task;
	}
	@Override
	public String getTaskName() { 
		return taskName;
	}
	/**本地任务*/
	@Override
	public TaskScene getTaskScene() { 
		return TaskScene.local;
	}

	@Override
	public void run(ITimeVo vo) { 
		if(vo.getDay() % 2 == 0) {
			//每2天执行
		}
		if(vo.getHour() % 4 ==0) {
			//每4小时执行
		}
		// 自己定义执行时间
		String s =getTaskName() + "--start--" +  vo.getDate();
		log.info(s); 
		
	}	
}

class DistributedTask implements ITimerTask{
	private static final Logger log = LoggerFactory.getLogger(DistributedTask.class);
	private final String taskName;
	public DistributedTask(String task) {
		taskName = task;
	}
	@Override
	public String getTaskName() { 
		return taskName;
	}

	/**分布式任务*/
	@Override
	public TaskScene getTaskScene() { 
		return TaskScene.distributed;
	}

	@Override
	public void run(ITimeVo vo) { 
		String s =getTaskName() + "--start--" +  vo.getDate();
		log.info(s);
		
	}	
}
 