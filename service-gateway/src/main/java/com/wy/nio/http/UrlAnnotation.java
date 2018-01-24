package com.wy.nio.http;

import java.lang.reflect.Method;
import java.util.Map;


/**
 * @author wy
 * @version 创建时间：2018年1月24日 上午9:59:34
 */
public class UrlAnnotation {
	static String[] classNames = { "com.wy.service.gateway.web.AuthController" };


	public static void init(Map<String, Method> methods, Map<String, Object> instaces) {
		for (String className : classNames) {
			try {
				Class<?> clazz = Class.forName(className);
				for (Method method : clazz.getMethods()) {
					if (method.isAnnotationPresent(URL.class)) {
						String url = method.getAnnotation(URL.class).value();
						if (methods.get(url) == null) {
							methods.put(url, method);
							if (instaces.get(url) == null) {
								instaces.put(url, clazz.newInstance());
							}
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
