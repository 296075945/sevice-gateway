package com.wy.service.gateway.web;

import java.util.Map;

import com.wy.nio.http.URL;

/**
 * @author wy
 * @version 创建时间：2018年2月27日 下午4:57:14
 */
public class TestController {
	@URL("/test")
	public String test(Map<String, Object> map){
		return "test";
	}
}
