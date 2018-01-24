package com.wy.nio.http;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wy
 * @version 创建时间：2018年1月24日 上午10:16:10
 */
public class Result {
	@JSONField(ordinal=1)
	int status;
	@JSONField(ordinal=2)
	Object data;
	public Result(){
		
	}
	public Result(int status){
		this.status = status;
	}
	public Result(int status,Object data){
		this.status = status;
		this.data = data;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
