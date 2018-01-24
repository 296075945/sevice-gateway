package com.wy.service.gateway.web;

import java.util.UUID;

import com.wy.nio.http.URL;
import com.wy.service.gateway.module.Auth;
import com.wy.service.gateway.module.AuthStatus;
import com.wy.service.jedis.JedisPoolUtils;

import redis.clients.jedis.Jedis;

/**
 * @author wy
 * @version 创建时间：2018年1月24日 下午1:42:47
 */
public class AuthController {
	@URL("/login")
	public Auth login(Auth auth){
		if("weiyang".equals(auth.getUserName())&&"123456".equals(auth.getPassword())){
			auth.setToken(UUID.randomUUID().toString());
			auth.setPassword("");
			auth.setStatus(AuthStatus.LOGIN_SUCCESS);
			Jedis jedis = JedisPoolUtils.getJedis();
			jedis.set(auth.getUserName(), auth.getToken());
			jedis.expire(auth.getUserName(), 60*30);//30分钟
			jedis.close();
			//写入redis
		}else{
			auth.setStatus(AuthStatus.LOGIN_FAILE);
		}
		return auth;
	}
	@URL("/logout")
	public Auth logout(Auth auth){
		Jedis jedis = JedisPoolUtils.getJedis();
		String token = jedis.get(auth.getUserName());
		if(token!=null){
			jedis.del(auth.getUserName());
		}
		jedis.close();
		return auth;
	}
	@URL("/check")
	public Auth check(Auth auth){
		Jedis jedis = JedisPoolUtils.getJedis();
		String token = jedis.get(auth.getUserName());
		if(token!=null&&token.equals(auth.getToken())){
			auth.setStatus(AuthStatus.CHECK_SUCEESS);
		}else{
			auth.setStatus(AuthStatus.CHECK_FAILE);
		}
		jedis.close();
		return auth;
	}
}
