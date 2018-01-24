package com.wy.service.jedis;

import com.wy.service.gateway.module.AuthStatus;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wy
 * @version 创建时间：2018年1月24日 下午2:18:22
 */
public class JedisPoolUtils {
	private static JedisPool pool;
	static {
		poolInit();
	}
	private static void createJedisPool() {

		// 建立连接池配置参数
		JedisPoolConfig config = new JedisPoolConfig();

		// 设置最大连接数
		// config.setMaxActive(100);
		config.setMaxIdle(10);

		// 设置最大阻塞时间，记住是毫秒数milliseconds
		// config.setMaxWait(1000);
		config.setMaxTotal(200);
		
		// 设置空间连接
		config.setMaxIdle(10);

		// 创建连接池
		pool = new JedisPool(config, "127.0.0.1", 6379);

	}

	private static synchronized void poolInit() {
		if (pool == null) {
			createJedisPool();
		}
	}

	public static Jedis getJedis() {

		return pool.getResource();
	}
	
	public static void main(String[] args) {
//		Jedis jedis = getJedis();
//		String token = jedis.get(auth.getUserName());
//		if(token!=null&&token.equals(auth.getToken())){
//			auth.setStatus(AuthStatus.CHECK_SUCEESS);
//		}else{
//			auth.setStatus(AuthStatus.CHECK_FAILE);
//		}
	}
}
