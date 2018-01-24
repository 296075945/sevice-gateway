package com.wy.service.gateway.module;

/**
 * @author wy
 * @version 创建时间：2018年1月24日 下午1:44:06
 */
public class Auth {
	String token;
	String userName;
	String password;
	int status;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
