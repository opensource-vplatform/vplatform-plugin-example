package com.yindangu.plugin.demo;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MyThread {
	private static final int PeriodTime = 512, PeriodSize = 1024;
	private static ScheduledThreadPoolExecutor threadExecutor;

	public static void main(String[] args) throws Exception{
		threadExecutor = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
			private int threadCount;

			@Override
			public Thread newThread(Runnable r) {
				threadCount++;
				return new Thread(r, "redisPut" + threadCount);
			}
		});

		threadExecutor.scheduleAtFixedRate(new Task1(), PeriodTime, PeriodTime, TimeUnit.MILLISECONDS);
		Thread.sleep(5 * 1000);
		System.out.println("==========");

		threadExecutor.scheduleAtFixedRate(new Task2(), PeriodTime, PeriodTime, TimeUnit.MILLISECONDS);
		Thread.sleep(5 * 1000);
		System.out.println("=====*********=====");
		threadExecutor.submit(new Task3());
	}
}

class Task1 implements Runnable {
	public void run() {
		System.out.println("a");
	}
}

class Task2 implements Runnable {
	private int count =0;
	public void run() {
		System.out.println("b" + count);
		count ++;
		if(count == 10) {
			throw new RuntimeException("异常10");
		}
	}
}


class Task3 implements Runnable {
	public void run() {
		System.out.println("c");
	}
}