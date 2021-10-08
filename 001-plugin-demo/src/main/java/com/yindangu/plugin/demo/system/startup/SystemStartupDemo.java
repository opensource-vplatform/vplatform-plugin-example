package com.yindangu.plugin.demo.system.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.plugin.business.api.system.ISystemStartupListener;
import com.yindangu.v3.business.timer.ITimerManager;

public class SystemStartupDemo implements ISystemStartupListener{
	public static final String PluginCode = "systemStartupDemo";
	private static final Logger log = LoggerFactory.getLogger(SystemStartupDemo.class);

	@Override
	public void beforePublish() {
		try {
			log.info("***************SystemStartupDemo*************");
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
			log.info("*********SystemStartupDemo*******");
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
