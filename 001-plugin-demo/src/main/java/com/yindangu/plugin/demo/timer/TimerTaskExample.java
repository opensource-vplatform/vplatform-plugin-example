package com.yindangu.plugin.demo.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.v3.business.timer.ITimeVo;
import com.yindangu.v3.business.timer.ITimerTask;
import com.yindangu.v3.business.timer.TaskScene;
  
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