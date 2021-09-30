package com.yindangu.plugin.demo.timer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.plugin.demo.command.ExampleMyHttpCommand;
import com.yindangu.v3.business.VDS;
import com.yindangu.v3.business.plugin.business.api.httpcommand.FormatType;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpCommand;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpContext;
import com.yindangu.v3.business.plugin.business.api.httpcommand.IHttpResultVo;
import com.yindangu.v3.business.timer.ITaskProxy;
import com.yindangu.v3.business.timer.ITimeVo;
import com.yindangu.v3.business.timer.ITimerManager;
import com.yindangu.v3.business.timer.TaskScene;
import com.yindangu.v3.business.timer.TaskState;
import com.yindangu.v3.platform.plugin.util.VdsUtils;
 
/**
 * task例子
 * @author jiqj
 *
 */
public class TimerTaskCommand implements IHttpCommand{
	private static final Logger log = LoggerFactory.getLogger(ExampleMyHttpCommand.class);

	public static final String CODE ="timertask";
	@Override
	public IHttpResultVo execute(IHttpContext context) {
		HttpServletRequest req =context.getRequest();
		String execute = getParameter(req,"execute");
		String message = null;
		Object rs ;
		try {
			if(execute.length()==0 || execute.equalsIgnoreCase("AllTask")) {
				rs = getAllTask();
			}
			else if("findTask".equalsIgnoreCase(execute)){
				String taskName = getParameter(req,"search");
				rs = findTask(taskName);
			}
			else if("action".equalsIgnoreCase(execute)){
				rs = getActionTasks();
			}
			else if("setState".equalsIgnoreCase(execute)){
				String taskName = getParameter(req, "taskName");
				rs = setTaskState(taskName);
			}
			else if("register".equalsIgnoreCase(execute)){ 
				String timer = getParameter(req, "timer");
				rs = doRegister(timer);
			}
			else if("run".equalsIgnoreCase(execute)){ 
				rs = doRun();
			}
			else {
				message ="不能识别的操作(execute):" + execute;
				rs = null;
			}
		}
		catch(Exception e) {
			message = execute + ")出错:" + e.getMessage();
			log.error(message,e);
			rs = null;
		}
		/////////////////////////////////////
		Map<String,Object> success = new HashMap<String,Object>();
		if(message!=null && message.length()>0) {
			success.put("message", message);
		}
		
		if(rs == null) {
			success.put("success", Boolean.FALSE); 
			success.put("message", message);
		}
		else {
			success.put("success", Boolean.TRUE);
			success.put("data", rs);
		}

		IHttpResultVo vo = context.newResultVo();
		vo.setValue(VdsUtils.json.toJson(success));
		vo.setValueType(FormatType.Json);
		return vo;
	}
	private String getParameter(HttpServletRequest req,String key) {
		String opt = req.getParameter(key);
		return (opt == null ? "": opt.trim());
	}
	/**全部任务*/
	private List<MyTaskVo> getAllTask(){
		ITimerManager tm = VDS.getIntance().getTimerManager(); 
		Map<String,ITaskProxy> tks = tm.getTasksAll();//invokeTimerManager(D_AllTask); 
		List<MyTaskVo> tasks = new ArrayList<MyTaskVo>(tks.size());
		for(Map.Entry<String,ITaskProxy> e : tks.entrySet()) {
			tasks.add(new MyTaskVo(e.getKey(), e.getValue()));
		}
		return tasks;
	}
 
	
	/**查找任务*/
	private List<MyTaskVo> findTask(String taskName){
		ITimerManager tm = VDS.getIntance().getTimerManager(); 
		Map<String,ITaskProxy> tks =tm.getTasksAll();// invokeTimerManager(D_AllTask); 
		String name = taskName.toLowerCase();
		List<MyTaskVo> tasks = new ArrayList<MyTaskVo>(tks.size());
		
		for(Map.Entry<String,ITaskProxy> e : tks.entrySet()) {
			String tn = e.getKey();
			if(tn.toLowerCase().indexOf(name)>=0) {
				tasks.add(new MyTaskVo(tn, e.getValue()));
			}
		}
		return tasks;
	}
	/**正在运行的任务*/
	private List<MyTaskVo> getActionTasks(){
		ITimerManager tm = VDS.getIntance().getTimerManager(); 
		Map<String,ITaskProxy> tks = tm.getActionTasks();// invokeTimerManager(D_ActionTasks); 
		List<MyTaskVo> tasks = new ArrayList<MyTaskVo>(tks.size());
		for(Map.Entry<String,ITaskProxy> e : tks.entrySet()) {
			tasks.add(new MyTaskVo(e.getKey(), e.getValue()));
		}
		return tasks;
	}
	private TaskState setTaskState(String taskName) {
		ITimerManager tm = VDS.getIntance().getTimerManager(); 
		ITaskProxy task = tm.getTasksAll().get(taskName);
		TaskState state = TaskState.disabled;
		if(task.getState() == TaskState.disabled) {
			state = TaskState.enabled;
		} 
		tm.setTaskState(taskName, state);
		return state;
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
	
	/**只能调试时使用*/
	private String doRun() {
		ITimerManager tm = VDS.getIntance().getTimerManager(); 
		((Runnable)tm).run();
		return "触发任务运行"; 
	}
}
class MyTaskVo  {
	private final String taskName;
	private final ITaskProxy proxy;
	protected MyTaskVo(String taskName, ITaskProxy p) {
		proxy = p;
		this.taskName = taskName;
	} 
	public String getTaskName() { 
		return taskName;
	}

	public TaskScene getTaskScene() { 
		return proxy.getTaskScene();
	}
	/** 返回带小数的时间，例如 11,11.5 */
	public String getHour() {
		int half = proxy.getHalfHour() ;
		if(half <0) {
			return "-1";
		}
		
		int hour = half /2;
		if(hour  * 2 == half) {
			return String.valueOf(hour);
		}
		else {
			return String.valueOf(hour) + ".5";
		}
	}

	public int getCount() { 
		return proxy.getCount();
	}

	public TaskState getState() { 
		return proxy.getState();
	} 
	public String getStartTime() {
		ITimeVo vo = proxy.getStartTime();
		return (vo == null ? "": vo.getDate());
	}
}