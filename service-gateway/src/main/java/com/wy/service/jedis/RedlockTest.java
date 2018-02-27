package com.wy.service.jedis;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wy
 * @version 创建时间：2018年2月7日 下午4:19:32
 */
public class RedlockTest implements Resource{

	static RedlockTest red = new RedlockTest();
	static Lock lock = new RedLock(red);
	static long start = System.currentTimeMillis();
	static{
//		lock = new ReentrantLock();
	}
	public static void main(String[] args) {
		
		new Thread(new Run()).start();
		new Thread(new Run()).start();
	}
	public String key() {
		return toString();
	}
	
	int i=0;
	
	static final class Run implements Runnable{
		
		public void run() {
			for(int i=0;i<50000;i++){
				lock.lock();
//				System.out.println(red.i++ + "\t\t"+Thread.currentThread().getName());
				red.i++;
				lock.unlock();
			}
			System.out.println("耗时"+(System.currentTimeMillis() - start)+"\t now:"+red.i);
		}
		
	}
}
