package com.wy.service.jedis;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import redis.clients.jedis.Jedis;

/**
 * @author wy
 * @version 创建时间：2018年2月7日 下午3:52:04
 */
public class RedLock  implements Lock{
	private Resource resource;
	private static final String LOCK = "";
	public RedLock(Resource resource){
		this.resource = resource;
	}
	public void lock(){
		Jedis jedis = null;
		try{
			String key = resource.key();
			jedis = JedisPoolUtils.getJedis();
			for(int i =0;jedis.setnx(key, LOCK)==0;i++){
//				waitRead(Math.min(i, 100));
			}
//			jedis.set(key, LOCK);
//			jedis.expire(key, 10);
			
		}finally{
			jedis.close();
		}
	}
	public void unlock(){
		Jedis jedis = null;
		try{
			String key = resource.key();
			jedis = JedisPoolUtils.getJedis();
			jedis.del(key);
		}finally{
			jedis.close();
		}
	}
	private void waitRead(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
	public void lockInterruptibly() throws InterruptedException {
		// TODO Auto-generated method stub
		
	}
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}
}
