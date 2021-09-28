package com.yindangu.plugin.demo.timer;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yindangu.v3.business.timer.ITimeVo;
import com.yindangu.v3.business.timer.ITimerTask;
import com.yindangu.v3.business.timer.TaskScene;
 

class RepeatTask implements ITimerTask{ 
	private static final Logger log = LoggerFactory.getLogger(RepeatTask.class);
	protected static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void close(Closeable os) {
		if(os == null) {
			return ;
		}
		try {
			os.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
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
		log.info(s); 
		sleep(6 * 60 * 1000);
		Timestamp d = new Timestamp(System.currentTimeMillis());
		
		s = getTaskName() + "--end--" +  d.toString(); 
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

	@Override
	public TaskScene getTaskScene() { 
		return TaskScene.distributed;
	}

	@Override
	public void run(ITimeVo vo) { 
		String s =getTaskName() + "--start--" +  vo.getDate();
		//log.info(s);
		RepeatTask.sleep(6 * 60 * 1000);
		Timestamp d = new Timestamp(System.currentTimeMillis());
		
		s = getTaskName() + "*****-end-*****-" +  d.toString();
		//log.info(s,new RuntimeException("============"));
		log.info(s);
		
	}	
}
/**启动时间*/
class RunTimeVo implements ITimeVo{
	public enum Format{
		YYYYMMDD,YYYY_MM_DD,
		HHMMSS,HH_MM_SS,
		YYYYMMDDHHMMSS,YYYY_MM_DD_HH_MM_SS,
	}
	
	private final int year,month,day;
	private final int hour,minute,second;
	/*public RunTimeVo(int year,int month,int day) {
		this(year, month, day, 0, 0, 0);
	}*/
	public RunTimeVo(int year,int month,int day,int hour,int minute,int second) {
		this.year = year;
		this.month = month;
		this.day = day;
		
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}
	public RunTimeVo(Calendar c) {
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH) + 1;
		day = c.get(Calendar.DATE);
		hour = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		second = c.get(Calendar.SECOND);
	}
	@Override
	public int getYear() {
		return year;
	}

	@Override
	public int getMonth() { 
		return month;
	}

	@Override
	public int getDay() { 
		return day;
	}

	@Override
	public int getHour() { 
		return hour;
	}

	@Override
	public int getMinute() {
		return minute;
	}

	@Override
	public int getSecond() {
		return second;
	}
	@Override
	public String toString() {
		return formatDate(Format.YYYY_MM_DD_HH_MM_SS);
	}
	@Override
	public String getDate() {
		return formatDate(Format.YYYY_MM_DD_HH_MM_SS);
	}
	/**
	 * 
	 * @param format 10位(yyyy-mm-dd)或者19位(yyyy-mm-dd hh:mm:ss)
	 * @return
	 */
	public String formatDate(Format fmt) {  
		/*StringBuilder sb = new StringBuilder();
		sb.append(year).append('-');
		appendTime(sb, month).append('-');
		appendTime(sb, day);
		if(format !=10) {
			sb.append(' ');
			appendTime(sb, hour).append(':');
			appendTime(sb, minute).append(':');
			appendTime(sb, second);
		}
		return sb.toString();
		*/
		
		boolean sp = (fmt ==  Format.YYYY_MM_DD || fmt == Format.YYYY_MM_DD_HH_MM_SS || fmt== Format.HH_MM_SS);
		StringBuilder sb = new StringBuilder();
		if(fmt == Format.YYYY_MM_DD_HH_MM_SS || fmt== Format.YYYY_MM_DD || fmt == Format.YYYYMMDD || fmt == Format.YYYYMMDDHHMMSS) {
			if(year>999) {
				sb.append(year);
			}
			else {
				String ys = "000" + year;
				sb.append(ys.substring(ys.length()-4));
			}
			if(sp) {
				sb.append('-');
			}
			appendTime(sb, month);
			if(sp) {
				sb.append('-');
			}
			appendTime(sb, day);
			if(sp && fmt == Format.YYYY_MM_DD_HH_MM_SS ) {	
				sb.append(' ');
			}
		}
		if(fmt == Format.YYYY_MM_DD_HH_MM_SS || fmt== Format.HH_MM_SS || fmt == Format.HHMMSS || fmt == Format.YYYYMMDDHHMMSS) {
			appendTime(sb, hour);
			if(sp) {
				sb.append(':');
			}
			appendTime(sb, minute);
			if(sp) {
				sb.append(':');
			}
			appendTime(sb, second);
		}
		
		return sb.toString();
	}
	private StringBuilder appendTime(StringBuilder sb,int n) {
		if(n>9) {
			sb.append(n);
		}
		else {
			sb.append('0').append(n);
		}
		return sb;
	}
	/**解析日期,对应 {@linkplain #formatDate(int)}*/
	public static RunTimeVo parseDates(String date,Format fmt) {
 		int year = 0,month = 0,day = 0;
		int hour = 0,mi = 0,sec = 0;
		if(fmt == Format.YYYY_MM_DD_HH_MM_SS || fmt == Format.YYYY_MM_DD) {
			//String s = date;//"yyyy-mm-dd hh:MM:ss";
			year = Integer.parseInt( date.substring(0,4));
			month = Integer.parseInt( date.substring(5,7));
			day = Integer.parseInt( date.substring(8,10));
		}
		else if(fmt == Format.YYYYMMDD || fmt == Format.YYYYMMDDHHMMSS) {
			year = Integer.parseInt( date.substring(0,4));
			month = Integer.parseInt( date.substring(4,6));
			day = Integer.parseInt( date.substring(6,8));
		}
		else if(fmt == Format.HH_MM_SS || fmt == Format.HHMMSS) {
			//不处理
		}
		else {
			throw new RuntimeException("不能识别的日期格式:" + fmt);
		}
		
		if(fmt == Format.YYYY_MM_DD_HH_MM_SS || fmt == Format.HH_MM_SS) {
			int idx = date.indexOf(':');
			hour = Integer.parseInt(date.substring(idx-2,idx));
			mi = Integer.parseInt(date.substring(idx+1,idx+3));
			sec = Integer.parseInt(date.substring(idx+4,idx+6));
		}
		else if(fmt == Format.HHMMSS || fmt == Format.YYYYMMDDHHMMSS) {
			int idx = date.length() - 4;
			hour = Integer.parseInt(date.substring(idx-2,idx));
			mi = Integer.parseInt(date.substring(idx,idx+2));
			sec = Integer.parseInt(date.substring(idx+2,idx+4));
		}
		else if(fmt == Format.YYYY_MM_DD || fmt == Format.YYYYMMDD) {
			//不处理
		}
		else {
			throw new RuntimeException("不能识别的日期格式:" + fmt);
		}
		 
		return new RunTimeVo(year, month, day, hour, mi, sec);
	}
	
}