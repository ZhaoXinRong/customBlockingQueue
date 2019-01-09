package com.snowruin.queue.test;

import java.util.concurrent.CountDownLatch;

import com.snowruin.queue.CustomArrayBlockingQueue;

/**
 *  测试类
 * @author zxm
 * @version 1.0.0
 * @date 2019-01-09 15:29:46
 *
 */
public class TestQueue {
	
	
	public static void main(String[] args) {
		
		CustomArrayBlockingQueue<String> queueDemo = new CustomArrayBlockingQueue<>(3);
		
		for (int i = 0; i < 3; i++) {
			queueDemo.put((i+1)+"");
		}
		
		System.out.println(queueDemo.toString());
		
		for (int i = 0; i <4 ; i++) {
			if(queueDemo.notEmpty()) {
				System.out.println(queueDemo.get());
			}
		}
	}
	
	
	public static class MyThread extends Thread {
		
		private CountDownLatch countDownLatch;
		
		private CustomArrayBlockingQueue<String> queueDemo; 
		
		private String string;
		
		public MyThread( CountDownLatch countDownLatch , CustomArrayBlockingQueue<String > queueDemo , String string){
			this.countDownLatch = countDownLatch;
			this.queueDemo = queueDemo;
			this.string = string;
		}
		
		public void run() {
			try {
				countDownLatch.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			queueDemo.put(string);
		}
	}

}
