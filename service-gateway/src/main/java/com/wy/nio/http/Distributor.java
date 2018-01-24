package com.wy.nio.http;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONObject;

import io.netty.handler.codec.http.HttpRequest;

/**
 * @author wy
 * @version 创建时间：2018年1月23日 下午4:39:23
 */
public class Distributor {
	private final static Distributor instace = new Distributor();
	private Map<String, Method> methods = new ConcurrentHashMap<String, Method>();
	private Map<String, Object> instaces = new ConcurrentHashMap<String, Object>();
	private Distributor(){
		UrlAnnotation.init(methods, instaces);
	}
	public static Distributor getInstace(){
		return instace;
	}
	public String run(HttpRequest request,String json){
		return JSONObject.toJSONString(getResult(request, json));
	}
	private Result getResult(HttpRequest request,String json){
		try{
			String uri=request.uri();
			Method method = methods.get(uri);
			Object instace = instaces.get(uri);
			if(method==null||instace==null){
				return new Result(ResultStatus.NOT_FOULD);
			}
			Class<?> clazz = method.getParameterTypes()[0];
			Object parameter = JSONObject.parseObject(json,clazz);
			Object rs = method.invoke(instace, parameter);
			return new Result(ResultStatus.SUCCESS,rs);
		}catch (Exception e) {
			e.printStackTrace();
			return new Result(ResultStatus.FAILE,e.getMessage());
		}
	}
	
}
